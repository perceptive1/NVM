package com.pcs.perpetualRents.util.mail;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;

import com.pcs.perpetualRents.business.bean.Address;
import com.pcs.perpetualRents.business.bean.Admin;
import com.pcs.perpetualRents.business.bean.ContactDetails;
import com.pcs.perpetualRents.business.bean.LandLord;
import com.pcs.perpetualRents.business.bean.MailEventConfiguration;
import com.pcs.perpetualRents.business.bean.Property;
import com.pcs.perpetualRents.business.bean.UserLogin;
import com.pcs.perpetualRents.common.bean.ApplicationSettings;
import com.pcs.perpetualRents.enm.MailEventActor;
import com.pcs.perpetualRents.enm.MailEventType;
import com.pcs.perpetualRents.enm.UserType;
import com.pcs.perpetualRents.manager.CommonManager;
import com.pcs.perpetualRents.manager.MailEventManager;

//http://ckeditor.com/demo
public class PropertyMailUtility implements Runnable {
	
	private static Logger logger = ApplicationSettings.getLogger(PropertyMailUtility.class.getName()); 
	
	private JavaMailSender mailSender;
	private Property propertyObj;
	private ApplicationSettings applicationSettings;
	private MailEventType mailEventType;
	private SpringTemplateEngine templateEngine;
	private MailEventManager eventManager;
	private CommonManager commonManager;
	private UserLogin loginObj;
	
	public PropertyMailUtility() {
		super();
	}
	
	public PropertyMailUtility(final Property propertyObj, final ApplicationSettings settingsObj, final JavaMailSender mailSender,
			MailEventType mailEventType, final MailEventManager eventManager, SpringTemplateEngine templateEngine, CommonManager commonManager,UserLogin loginObj) {
		this.propertyObj = propertyObj;
		this.applicationSettings = settingsObj;
		this.mailSender = mailSender;
		this.mailEventType = mailEventType;
		this.templateEngine = templateEngine;
		this.eventManager = eventManager;
		this.commonManager = commonManager;
		this.loginObj = loginObj;
	}
	
	@Override 
	public void run() {
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		try{
			logger.info("MAIL-THREAD has been started.");
			List<MailEventConfiguration> mailConfList = eventManager.loadMailEventByEventType(mailEventType, MailEventActor.PROPERTY);
			if(mailConfList != null && !mailConfList.isEmpty()){
				for (MailEventConfiguration _mailConfObj : mailConfList) {
					if(_mailConfObj.getMailEventTypeId() == MailEventType.REGISTER.id()){
						propertyRegisteration(MailEventActor.get(_mailConfObj.getActorTypeId()));
					}else if(_mailConfObj.getMailEventTypeId() == MailEventType.EDIT.id()){
						editPropertyProfile(MailEventActor.get(_mailConfObj.getActorTypeId()));
					}
				}
			}else {
				logger.severe("No configuration found for MAIL..");
			}
			
			logger.finest("MAIL THREAD COMPLETED.");
		
		}catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Some error has been occured" + e.getMessage());
		}
		
	}
	
	private void propertyRegisteration(MailEventActor actor){
		   try{
			   
				   	MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
					Address addressObj = null;
					LandLord landLordObj = null;
					 Context ctx = new Context();
					 logger.info("ACTOR IS " + actor.value() + " AND IS : " + actor.id());
					 
			   switch(actor){
			   		
			   case LANDLORD : 
					 
						 if(propertyObj != null){
							 ctx.setVariable("contactPerson", propertyObj.getContactPerson());
							 ctx.setVariable("reference", propertyObj.getUniqueReference());
							  addressObj = propertyObj.getAddressObj();
								  if(addressObj != null){
									  ctx.setVariable("postCode", addressObj.getPostCode());
							 }
							
							landLordObj = propertyObj.getLandLordObj();
							 String emailId = "";
							if(landLordObj != null){
								ctx.setVariable("receiverName", landLordObj.getFirstName() + " " + landLordObj.getLastName());
								 emailId = landLordObj.getPrimaryEmailId();
							}
							ctx.setVariable("landlordName", landLordObj.getFirstName() + " " + landLordObj.getLastName());
							ctx.setVariable("status", propertyObj.getStatusPercentage());
							
							ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
							  
							mimeMessageHelper.setTo(emailId);  
							  
							mimeMessageHelper.setSubject("Email From Perpetual");
							  
							  final String htmlContent = this.templateEngine.process("property/propertyRegisteration.html", ctx);
							  mimeMessageHelper.setText(htmlContent, true); // true = isHtml
							 
							  mailSender.send(mimeMessage);
							  
							  logger.info("LANDLORD : MAIL is sending to " + emailId);
						 }
				   break;
				   
			   case SUB_ADMIN: 
				   
				   if(propertyObj != null){
					   List<Admin> adminList  = commonManager.loadAdminByProperty(propertyObj.getId());
					   if(adminList != null){
						   for (Admin admin : adminList) {
							   ctx.setVariable("receiverName", admin.getFirstName() + " " + admin.getLastName());
							   landLordObj = propertyObj.getLandLordObj();
							   if(landLordObj != null){
								   ctx.setVariable("landlordName", landLordObj.getFirstName());   
							   }
								addressObj = propertyObj.getAddressObj();
									  if(addressObj != null){
										  ctx.setVariable("postCode", addressObj.getPostCode());
									  }
						  
						  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						  
						  ContactDetails detailsObj = admin.getContactDetailsObj();
						  String emailId = "";
						  if(detailsObj != null){
							  mimeMessageHelper.setTo(detailsObj.getEmailId());
							  emailId = detailsObj.getEmailId();
						  }
						  
						  mimeMessageHelper.setSubject("Email From Perpetual");
						  this.templateEngine.initialize();
						  final String htmlContent2 = this.templateEngine.process("property/propertyRegister-admin.html", ctx);
						  mimeMessageHelper.setText(htmlContent2, true); // true = isHtmlCompanyName()
						 			  
						  mailSender.send(mimeMessage);
						  logger.info("SUB ADMIN : MAIL is sending to " + emailId);
						   
						 }
					   }
				   }
				   break;
			   case SUPER_ADMIN:
				   
				   if(propertyObj != null){
					   Admin superAdminObj  = commonManager.loadsuperAdmin();
					   if(superAdminObj != null){
						   ctx.setVariable("receiverName", superAdminObj.getFirstName() + " " + superAdminObj.getLastName());
							   landLordObj = propertyObj.getLandLordObj();
							   if(landLordObj != null){
								   ctx.setVariable("landlordName", landLordObj.getFirstName());   
							   }
							   ctx.setVariable("status", propertyObj.getStatusPercentage());
								addressObj = propertyObj.getAddressObj();
									  if(addressObj != null){
										  ctx.setVariable("postCode", addressObj.getPostCode());
									  }
						  
						  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						  
						  ContactDetails detailsObj = superAdminObj.getContactDetailsObj();
						  String emailId = "";
						  if(detailsObj != null){
							  mimeMessageHelper.setTo(detailsObj.getEmailId());
							  emailId = detailsObj.getEmailId();
						  }
						  
						  mimeMessageHelper.setSubject("Email From Perpetual");
						  this.templateEngine.initialize();
						  final String htmlContent2 = this.templateEngine.process("property/propertyRegister-superadmin.html", ctx);
						  mimeMessageHelper.setText(htmlContent2, true); // true = isHtmlCompanyName()
						 			  
						  mailSender.send(mimeMessage);
						  
						  logger.info("SUPER ADMIN : MAIL is sending to " + emailId);
						   
						 }
				   }
				   break;
			default:
				break;
			   }
			   }catch(Exception e){
					e.printStackTrace();
			}
	   }
	   
	public void editPropertyProfile(MailEventActor actor){
		   try{
				   	MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
					Address addressObj = null;
					LandLord landLordObj = null;
					Context ctx = new Context();
					logger.info("ACTOR IS " + actor.value() + " AND ID IS : " + actor.id());
					Admin adminObj  = commonManager.loadsuperAdmin();
					
			   switch(actor){
			   		
			   case LANDLORD : 
				  
				   if(propertyObj != null){
					   
						  addressObj = propertyObj.getAddressObj();
							  if(addressObj != null){
								  ctx.setVariable("postCode", addressObj.getPostCode());
						   }
						
						landLordObj = propertyObj.getLandLordObj();
						 String emailId = "";
						if(landLordObj != null){
							ctx.setVariable("receiverName", landLordObj.getFirstName() + " " + landLordObj.getLastName());
							 emailId = landLordObj.getPrimaryEmailId();
						}
						ctx.setVariable("status", propertyObj.getStatusPercentage());
						ctx.setVariable("landlordName", landLordObj.getFirstName() + " " + landLordObj.getLastName());
						ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						ctx.setVariable("superAdminName", adminObj.getFirstName() + " " + adminObj.getLastName());
						  System.out.println(emailId);
						mimeMessageHelper.setTo(emailId);  
						  
						mimeMessageHelper.setSubject("Email From Perpetual");
						
						if(loginObj.getUserTypeId() != UserType.SUPER_ADMIN.id()) {
							 final String htmlContent = this.templateEngine.process("property/propertyEdit-landlord.html", ctx);
							  mimeMessageHelper.setText(htmlContent, true); // true = isHtml
						} else {
							 final String htmlContent = this.templateEngine.process("propertyEdit-landlord-updatedBySuperAdmin.html", ctx);
							  mimeMessageHelper.setText(htmlContent, true); // true = isHtml	
						}
						 
						 
						  mailSender.send(mimeMessage);
						  
						  logger.info("LANDLORD : MAIL is sending to " + emailId);
					 }
				   break;
				   
			   case SUB_ADMIN: 
				   
				   if(propertyObj != null){
					   List<Admin> adminList  = commonManager.loadAdminByProperty(propertyObj.getId());
					   if(adminList != null){
						   for (Admin admin : adminList) {
							   
							   ctx.setVariable("receiverName", admin.getFirstName() + " " + admin.getLastName());
							   addressObj = propertyObj.getAddressObj();
								  if(addressObj != null){
									  ctx.setVariable("posrCode", addressObj.getPostCode());
								  }
							   landLordObj = propertyObj.getLandLordObj();
							   if(landLordObj != null){
								   ctx.setVariable("landlordName", landLordObj.getFirstName() + " "+ landLordObj.getLastName());   
							   }
							   ctx.setVariable("status", propertyObj.getStatusPercentage());
							   ctx.setVariable("adminName", admin.getFirstName() + " " +  admin.getLastName() );
						  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						  ctx.setVariable("superAdminName", adminObj.getFirstName() + " " + adminObj.getLastName());
						  
						  ContactDetails detailsObj = admin.getContactDetailsObj();
						  String emailId = "";
						  if(detailsObj != null){
							  mimeMessageHelper.setTo(detailsObj.getEmailId());
							  emailId = detailsObj.getEmailId();
						  }
						 
						  mimeMessageHelper.setSubject("Email From Perpetual");
						  this.templateEngine.initialize();
						  if(loginObj.getUserTypeId() == UserType.SUPER_ADMIN.id()) {
							  final String htmlContent2 = this.templateEngine.process("property/propertyEdit-admin-updatedBySuperAdmin.html", ctx);
							  mimeMessageHelper.setText(htmlContent2, true); // true = isHtmlCompanyName()
							} else {
								 final String htmlContent2 = this.templateEngine.process("property/propertyEdit-admin.html", ctx);
								  mimeMessageHelper.setText(htmlContent2, true); // true = isHtmlCompanyName()
							}
						 
						 System.out.println("mailSender: " + mailSender);
						 System.out.println("mimeMessage: " + mimeMessage);
						  mailSender.send(mimeMessage);
						  logger.info("SUB ADMIN : MAIL is sending to " + emailId);
						   
						 }
					   }
				   }
				  
				   break;
				   
			   case SUPER_ADMIN:
				   
				   if(propertyObj != null){
					   if(adminObj != null){
						   ctx.setVariable("receiverName", adminObj.getFirstName() + " " + adminObj.getLastName());
						   	addressObj = propertyObj.getAddressObj();
							  if(addressObj != null){
								  ctx.setVariable("postCode", addressObj.getPostCode());
							  }
							   landLordObj = propertyObj.getLandLordObj();
							   if(landLordObj != null){
								   ctx.setVariable("landlordName", landLordObj.getFirstName() + " "+ landLordObj.getLastName());   
							   }
							   ctx.setVariable("status", propertyObj.getStatusPercentage());
							   ctx.setVariable("adminName", adminObj.getFirstName() + " " +  adminObj.getLastName());
							  
							   
						  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						  
						  ContactDetails detailsObj = adminObj.getContactDetailsObj();
						  String emailId = "";
						  if(detailsObj != null){
							  mimeMessageHelper.setTo(detailsObj.getEmailId());
							  emailId  = detailsObj.getEmailId();
						  }
						  
						  mimeMessageHelper.setSubject("Email From Perpetual");
						  this.templateEngine.initialize();
						  if(loginObj.getUserTypeId() == UserType.SUPER_ADMIN.id()) {
							  final String htmlContent2 = this.templateEngine.process("property/propertyEdit-superadmin-updatedBySuperAdmin.html", ctx);
							  mimeMessageHelper.setText(htmlContent2, true); // true = isHtmlCompanyName()
							} else {
								final String htmlContent2 = this.templateEngine.process("property/propertyEdit-superadmin.html", ctx);
								  mimeMessageHelper.setText(htmlContent2, true); // true = isHtmlCompanyName()
							}
						  
						  
						 			  
						  mailSender.send(mimeMessage);
						  
						  logger.info("SUPER ADMIN : MAIL is sending to " + emailId);
						   
						 }
				   }
				   break;
			default:
				break;
				 
			   }
		   }catch(Exception e){
				e.printStackTrace();
			}
	   }
	   
}
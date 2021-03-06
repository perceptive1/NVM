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
import com.pcs.perpetualRents.business.bean.Charity;
import com.pcs.perpetualRents.business.bean.ContactDetails;
import com.pcs.perpetualRents.business.bean.LandLord;
import com.pcs.perpetualRents.business.bean.Mail;
import com.pcs.perpetualRents.business.bean.MailEventConfiguration;
import com.pcs.perpetualRents.business.bean.Property;
import com.pcs.perpetualRents.business.bean.UserLogin;
import com.pcs.perpetualRents.common.bean.ApplicationSettings;
import com.pcs.perpetualRents.dao.MailDAO;
import com.pcs.perpetualRents.enm.MailEventActor;
import com.pcs.perpetualRents.enm.MailEventType;
import com.pcs.perpetualRents.enm.MailFlowType;
import com.pcs.perpetualRents.enm.UserType;
import com.pcs.perpetualRents.manager.CommonManager;
import com.pcs.perpetualRents.manager.MailEventManager;

//http://ckeditor.com/demo
public class PMCMailUtility implements Runnable {
	
	private static Logger logger = ApplicationSettings.getLogger(PMCMailUtility.class.getName()); 
	
	private JavaMailSender mailSender;
	private Charity charityObj;
	private ApplicationSettings applicationSettings;
	private MailEventType mailEventType;
	private SpringTemplateEngine templateEngine;
	private MailEventManager eventManager;
	private CommonManager commonManager;
	private Property propertyObj;
	private LandLord landlordObj;
	private MailDAO mailDAO;
	private UserLogin userLoginObj;
	
	public PMCMailUtility() {
		super();
	}
	
	public PMCMailUtility(final Charity charityObj, final ApplicationSettings settingsObj, final JavaMailSender mailSender,
			MailEventType mailEventType, final MailEventManager eventManager, SpringTemplateEngine templateEngine, CommonManager commonManager,UserLogin userLoginObj) {
		this.mailSender = mailSender;
		this.charityObj = charityObj;
		this.applicationSettings = settingsObj;
		this.mailEventType = mailEventType;
		this.templateEngine = templateEngine;
		this.eventManager = eventManager;
		this.commonManager = commonManager;
		this.userLoginObj = userLoginObj;
	}
	
	public PMCMailUtility(final LandLord landlordObj, final Charity charityObj, final ApplicationSettings settingsObj, final JavaMailSender mailSender,
			MailEventType mailEventType, SpringTemplateEngine templateEngine, Property propertyObj, MailDAO mailDAO) {
		this.mailSender = mailSender;
		this.charityObj = charityObj;
		this.applicationSettings = settingsObj;
		this.mailEventType = mailEventType;
		this.templateEngine = templateEngine;
		this.propertyObj = propertyObj;
		this.landlordObj = landlordObj;
		this.mailDAO = mailDAO;
	}
	
	@Override 
	public void run() {
		Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
		try{
			logger.info("MAIL-THREAD has been started.");
			if(mailEventType.id() == MailEventType.CONVERSATION.id()){
				pmcConversation();
			}
			else{
				List<MailEventConfiguration> mailConfList = eventManager.loadMailEventByEventType(mailEventType, MailEventActor.PMC_ADMIN);
				if(mailConfList != null && !mailConfList.isEmpty()){
					for (MailEventConfiguration _mailConfObj : mailConfList) {
						if(_mailConfObj.getMailEventTypeId() == MailEventType.REGISTER.id()){
							pmcRegisteration(MailEventActor.get(_mailConfObj.getActorTypeId()));
						}else if(_mailConfObj.getMailEventTypeId() == MailEventType.PROFILE_EDIT.id()){
							editPMCProfile(MailEventActor.get(_mailConfObj.getActorTypeId()));
						}else if(_mailConfObj.getMailEventTypeId() == MailEventType.PASSWORD_CHANGE.id()){
							changePMCPassword(MailEventActor.get(_mailConfObj.getActorTypeId()));
						}else if(_mailConfObj.getMailEventTypeId() == MailEventType.ENABLE.id()){
							changePMCPassword(MailEventActor.get(_mailConfObj.getActorTypeId()));
						}else if(_mailConfObj.getMailEventTypeId() == MailEventType.ADD_CITY.id()){
							pmcAddCity(MailEventActor.get(_mailConfObj.getActorTypeId()));
						}else if(_mailConfObj.getMailEventTypeId() == MailEventType.EDIT_CITY.id()){
							pmcEditCity(MailEventActor.get(_mailConfObj.getActorTypeId()));
						}
					}
			}else {
				logger.severe("No configuration found for MAIL..");
			}
			}
			logger.finest("MAIL THREAD COMPLETED.");
		
		}catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "Some error has been occured" + e.getMessage());
		}
	}
	
	private void pmcRegisteration(MailEventActor actor){
		   try{
				   	MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
					UserLogin loginObj = null;
					Context ctx = new Context();
					logger.info("ACTOR IS " + MailEventActor.get(actor.id()).value() + " ITS ID IS " + actor.id());
					
					
			   switch(actor){
			   		
			   case PMC_ADMIN : 
					 
						 if(charityObj != null){
							loginObj = charityObj.getLoginObj();
								  if(loginObj != null){
									  ctx.setVariable("username", charityObj.getLoginObj().getUsername());
									  ctx.setVariable("password", charityObj.getLoginObj().getPassword());
								  }
								
						 
						 	 ctx.setVariable("pmcName", charityObj.getCompanyName());
							 ctx.setVariable("reference", charityObj.getUniqueReference());
							 ctx.setVariable("cities", charityObj.getCitiesCovered());
							 if(userLoginObj != null){
								 ctx.setVariable("createdBy", userLoginObj.getUsername());
							 }
							 
							 ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
							 ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
							 ctx.setVariable("modifiedBy",userLoginObj.getUsername());
							
							  
							  ContactDetails detailsObj = charityObj.getContactDetailsObj();
							  String emailId = "";
							  if(detailsObj != null){
								  emailId = detailsObj.getEmailId();
								  mimeMessageHelper.setTo(detailsObj.getEmailId());  
							  }
							  
							  
							  mimeMessageHelper.setSubject("Email From Perpetual");
							  String htmlContent = "";
							  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
								  htmlContent = this.templateEngine.process("pmc/subpmc/pmcRegisteration.html", ctx);
							  }else{
								   htmlContent = this.templateEngine.process("pmc/pmcRegisteration.html", ctx);  
							  }
							  
							  mimeMessageHelper.setText(htmlContent, true); // true = isHtml
							 
							  mailSender.send(mimeMessage);
							  
							  logger.info("PMC-ADMIN : MAIL is sending to " + emailId);
						 }
				   break;
				   
			   case SUB_ADMIN: 
				   if(charityObj != null){
					   List<Admin> adminList = commonManager.loadAdminByPMC(charityObj.getId());
					   if(adminList != null && !adminList.isEmpty()){
						   for (Admin adminObj : adminList) {
							   loginObj = charityObj.getLoginObj();
								  if(loginObj != null){
									  ctx.setVariable("username", charityObj.getLoginObj().getUsername());
								  }
							 ctx.setVariable("pmcName", charityObj.getCompanyName());
							 ctx.setVariable("reference", charityObj.getUniqueReference());
							 ctx.setVariable("cities", charityObj.getCitiesCovered());
							 ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
							 ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
							 ctx.setVariable("subAdminName", adminObj.getFirstName());
							 ctx.setVariable("createdBy",userLoginObj.getUsername());
						  
						  ContactDetails details = adminObj.getContactDetailsObj();
						  String subEmailId = "";
						  if(details != null){
							  subEmailId = details.getEmailId();
							  mimeMessageHelper.setTo(details.getEmailId());  
						  }else{
							  subEmailId = applicationSettings.getAdminEmailId();
							  mimeMessageHelper.setTo(applicationSettings.getAdminEmailId());
						  }
						   	
						   	mimeMessageHelper.setSubject("Email From Perpetual");
						   	this.templateEngine.initialize();
						    String htmlContent1 = "";
							  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
								  htmlContent1 = this.templateEngine.process("pmc/subpmc/pmcRegister-admin.html", ctx);
							  }else{
								   htmlContent1 = this.templateEngine.process("pmc/pmcRegister-admin.html", ctx);  
							  }
						   	
						   	mimeMessageHelper.setText(htmlContent1, true); // true = isHtml
						 			  
						   	mailSender.send(mimeMessage);
						   	logger.info("ADMIN : MAIL is sending to " + subEmailId);
						}
					   }
				   }
				   break;
			   case SUPER_ADMIN:
				   if(charityObj != null){
					   Admin superAdmin = commonManager.loadsuperAdmin();
					   if(superAdmin != null){
						   ctx.setVariable("pmcName", charityObj.getCompanyName());
						   ctx.setVariable("reference", charityObj.getUniqueReference());
						   ctx.setVariable("adminName", superAdmin.getFirstName());
						   ctx.setVariable("createdBy",userLoginObj.getUsername());
						   UserLogin tLoginObj = charityObj.getLoginObj();
						   if(tLoginObj != null)
						   ctx.setVariable("username", charityObj.getCompanyName());

						   ctx.setVariable("cities", charityObj.getCitiesCovered());
						
						   ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						   ctx.setVariable("pmcName", charityObj.getCompanyName());
							 
						   ContactDetails details = superAdmin.getContactDetailsObj();
						   String superEmailId = "";
						   if(details != null){
							   superEmailId  = details.getEmailId();
							   mimeMessageHelper.setTo(superEmailId);
						   }
							
							mimeMessageHelper.setSubject("Email From Perpetual");
								this.templateEngine.initialize();
								String htmlContent2 = "";
								  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
									  htmlContent2 = this.templateEngine.process("pmc/subpmc/pmcRegister-superadmin.html", ctx);
								  }else{
									   htmlContent2 = this.templateEngine.process("pmc/pmcRegister-superadmin.html", ctx);  
								  }
							 mimeMessageHelper.setText(htmlContent2, true); // true = isHtml
							 			  
							  mailSender.send(mimeMessage);
							  logger.info("ADMIN : MAIL is sending to " + superEmailId);
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
	   
	private void pmcConversation(){ 
		   try{
				  MimeMessage mimeMessage = mailSender.createMimeMessage();
				  MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
				  //Conversation conversation = null;
				  final Context ctx = new Context();
						  String landlordMailId = landlordObj.getPrimaryEmailId();
						  mimeMessageHelper.setTo(landlordMailId);
						  //mimeMessageHelper.setSubject(""+conversation.getConversationIdentity());
						  
						  if(landlordObj != null){
							  ctx.setVariable("receiverName", landlordObj.getFirstName() + " " + landlordObj.getLastName());
							  ctx.setVariable("reference", landlordObj.getUniqueReference());
						  }
					  
						  Address addressObj = propertyObj.getAddressObj();
						  if(addressObj!=null){
							  ctx.setVariable("postcode", addressObj.getPostCode());
							  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());  
						  }
						  
										  
						  this.templateEngine.initialize();
						  final String htmlContent = this.templateEngine.process("landlord/contractor-quotation.html", ctx);
						  mimeMessageHelper.setText(htmlContent, true); 
						 			  
						  mailSender.send(mimeMessage);
						  
						 
							  Mail mail = new Mail();
							  
							  mail.setMailTo(landlordMailId);
							  mail.setMailFrom(landlordObj.getPrimaryEmailId());
							 // mail.setSubject(conversation.getConversationIdentity());
							  mail.setMailBody(htmlContent);
							  mail.setMailOrigin(applicationSettings.getControlEmailId());
							  mail.setMailFlowType((short)MailFlowType.SEND.id());
							  mail.setMailEventActor((short)MailEventActor.LANDLORD.id());
							  mail.setMailEventType((short)MailEventType.CONVERSATION.id());
							  mail.setAttachmentCount(0);
							  mail.setMailMessageId(null);
							  mail.setMailMessageReference(null);
							  //mail.setConversationId(conversation.getId());
							  mailDAO.saveMail(mail);
						  
						  
						  logger.info("MAIL is send to " + landlordMailId);
				  
				 			 
				 
			}catch(Exception e){
				e.printStackTrace();
			}
	   
	   }
	   
	public void editPMCProfile(MailEventActor actor){
		   try{
				  MimeMessage mimeMessage = mailSender.createMimeMessage();
				  MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
					
				 Context ctx = new Context();
				 logger.info("ACTOR IS " + MailEventActor.get(actor.id()).value() + " ITS ID IS " + actor.id());
					
			   switch(actor){
			   		
			   case PMC_ADMIN : 
				   if(charityObj != null){
					   	
					   		UserLogin loginObj = charityObj.getLoginObj();
					   		if(loginObj != null){
					   			ctx.setVariable("receiverName", loginObj.getUsername());
					   		}
						  
						  ctx.setVariable("pmcName", charityObj.getCompanyName());
						  ctx.setVariable("modifiedBy",userLoginObj.getUsername());
						  ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
						  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						  ctx.setVariable("cities", charityObj.getCitiesCovered());
						  
						  String emailId = "";
						  ContactDetails detailsObj = charityObj.getContactDetailsObj();
						  if(detailsObj != null){
							  emailId = detailsObj.getEmailId();
							  mimeMessageHelper.setTo(detailsObj.getEmailId());  
						  }else{
							  mimeMessageHelper.setTo(applicationSettings.getAdminEmailId());
						  }
						  mimeMessageHelper.setSubject("Email From Perpetual");
						  String htmlContent = "";
						  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
							  htmlContent = this.templateEngine.process("pmc/subpmc/pmcEdit.html", ctx);
						  }else{
							   htmlContent = this.templateEngine.process("pmc/pmcEdit.html", ctx);  
						  }
						  mimeMessageHelper.setText(htmlContent, true); // true = isHtml
						 
						  mailSender.send(mimeMessage);
						  logger.info("PMC : MAIL is sending to " + emailId);
				   }
				   break;
				   
			   case SUB_ADMIN: 
				   
				   if(charityObj != null){
					   List<Admin> adminList = commonManager.loadAdminByPMC(charityObj.getId());
					   if(adminList != null && !adminList.isEmpty()){
						   for (Admin admin : adminList) {
							   
							    ctx.setVariable("receiverName", admin.getFirstName());
							    ctx.setVariable("pmcName", charityObj.getCompanyName());
								ctx.setVariable("createdBy",userLoginObj.getUsername());
								ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
								 ctx.setVariable("cities", charityObj.getCitiesCovered());
							    
								UserLogin loginObj = admin.getLoginObj();
								if(loginObj != null){
									ctx.setVariable("username", loginObj.getUsername());
								}
								
								ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
								  ContactDetails details = admin.getContactDetailsObj();
								  String subAdminEmailId = "";
								  if(details != null){
									  subAdminEmailId = details.getEmailId();
									  mimeMessageHelper.setTo(details.getEmailId());  
								  }else{
									  subAdminEmailId = applicationSettings.getAdminEmailId();
									  mimeMessageHelper.setTo(applicationSettings.getAdminEmailId());
								  }
								   	
								   	mimeMessageHelper.setSubject("Email From Perpetual");
								   	this.templateEngine.initialize();
								    String htmlContent1 = "";
									  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
										  htmlContent1 = this.templateEngine.process("pmc/subpmc/pmcEdit-admin.html", ctx);
									  }else{
										   htmlContent1 = this.templateEngine.process("pmc/pmcEdit-admin.html", ctx);  
									  }
								   	mimeMessageHelper.setText(htmlContent1, true); // true = isHtml
								 			  
								   	mailSender.send(mimeMessage);
								   	logger.info("ADMIN : MAIL is sending to " + subAdminEmailId);
								}
					   }
				   }
				   
				   break;
			   case SUPER_ADMIN:
				   
				   if(charityObj != null){
					   Admin superAdmin = commonManager.loadsuperAdmin();
					   if(superAdmin != null){
						   ctx.setVariable("receiverName", superAdmin.getFirstName());
						   ctx.setVariable("pmcName", charityObj.getCompanyName());
						   ctx.setVariable("createdBy",userLoginObj.getUsername());
						   ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
						   ctx.setVariable("adminName", superAdmin.getFirstName());
						   ctx.setVariable("cities", charityObj.getCitiesCovered());
						   
						   ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
								  ContactDetails detailsObj = superAdmin.getContactDetailsObj();
								  String superEmailId = "";
								if(detailsObj != null){
									superEmailId = detailsObj.getEmailId();
									mimeMessageHelper.setTo(detailsObj.getEmailId());
								}
							mimeMessageHelper.setSubject("Email From Perpetual");
								this.templateEngine.initialize();
								String htmlContent2 = "";
								  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
									  htmlContent2 = this.templateEngine.process("pmc/subpmc/pmcEdit-superadmin.html", ctx);
								  }else{
									   htmlContent2 = this.templateEngine.process("pmc/pmcEdit-superadmin.html", ctx);  
								  }
							 mimeMessageHelper.setText(htmlContent2, true); // true = isHtml
							 			  
							  mailSender.send(mimeMessage);
							  logger.info("ADMIN : MAIL is sending to " + superEmailId);
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
	/*---------------------added to mail--------------------------------*/
   private void pmcAddCity(MailEventActor actor){
		   try{
				   	MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
					UserLogin loginObj = null;
					Context ctx = new Context();
					logger.info("ACTOR IS " + MailEventActor.get(actor.id()).value() + " ITS ID IS " + actor.id());
					
			   switch(actor){
			   		
			   case PMC_ADMIN : 
					 
						 if(charityObj != null){
							loginObj = charityObj.getLoginObj();
						 
						 	 ctx.setVariable("pmcName", charityObj.getCompanyName());
							 ctx.setVariable("reference", charityObj.getUniqueReference());
							 ctx.setVariable("cities", charityObj.getCitiesCovered());
							 if(userLoginObj != null){
								 ctx.setVariable("createdBy", userLoginObj.getUsername());
							 }
							 
							 ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
							 ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
							 ctx.setVariable("modifiedBy",userLoginObj.getUsername());
							
							  
							  ContactDetails detailsObj = charityObj.getContactDetailsObj();
							  String emailId = "";
							  if(detailsObj != null){
								  emailId = detailsObj.getEmailId();
								  mimeMessageHelper.setTo(detailsObj.getEmailId());  
							  }
							  
							  mimeMessageHelper.setSubject("Email From Perpetual");
							  String htmlContent = "";
							  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
								  htmlContent = this.templateEngine.process("pmc/subpmc/pmcAddCity.html", ctx);
							  }else{
								   htmlContent = this.templateEngine.process("pmc/pmcAddCity.html", ctx);  
							  }
							  
							  mimeMessageHelper.setText(htmlContent, true); // true = isHtml
							 
							  mailSender.send(mimeMessage);
							  
							  logger.info("PMC-ADMIN : MAIL is sending to " + emailId);
						 }
				   break;
				   
			   case SUB_ADMIN: 
				   if(charityObj != null){
					   List<Admin> adminList = commonManager.loadAdminByPMC(charityObj.getId());
					   if(adminList != null && !adminList.isEmpty()){
						   for (Admin adminObj : adminList) {
							   loginObj = charityObj.getLoginObj();
								  if(loginObj != null){
									  ctx.setVariable("username", charityObj.getLoginObj().getUsername());
									  ctx.setVariable("reciverName", adminObj.getFirstName());
								  }
							 ctx.setVariable("pmcName", charityObj.getCompanyName());
							 ctx.setVariable("reference", charityObj.getUniqueReference());
							 ctx.setVariable("cities", charityObj.getCitiesCovered());
							 ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
							 ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
							 ctx.setVariable("subAdminName", adminObj.getFirstName());
							 ctx.setVariable("createdBy",userLoginObj.getUsername());
						  
						  ContactDetails details = adminObj.getContactDetailsObj();
						  String subEmailId = "";
						  if(details != null){
							  subEmailId = details.getEmailId();
							  mimeMessageHelper.setTo(details.getEmailId());  
						  }else{
							  subEmailId = applicationSettings.getAdminEmailId();
							  mimeMessageHelper.setTo(applicationSettings.getAdminEmailId());
						  }
						   	
						   	mimeMessageHelper.setSubject("Email From Perpetual");
						   	this.templateEngine.initialize();
						    String htmlContent1 = "";
							  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
								  htmlContent1 = this.templateEngine.process("pmc/subpmc/pmcAddCity-admin.html", ctx);
							  }else{
								   htmlContent1 = this.templateEngine.process("pmc/pmcAddCity-admin.html", ctx);  
							  }
						   	
						   	mimeMessageHelper.setText(htmlContent1, true); // true = isHtml
						 			  
						   	mailSender.send(mimeMessage);
						   	logger.info("ADMIN : MAIL is sending to " + subEmailId);
						}
					   }
				   }
				   break;
			   case SUPER_ADMIN:
				   if(charityObj != null){
					   Admin superAdmin = commonManager.loadsuperAdmin();
					   if(superAdmin != null){
						   ctx.setVariable("pmcName", charityObj.getCompanyName());
						   ctx.setVariable("reference", charityObj.getUniqueReference());
						   ctx.setVariable("adminName", superAdmin.getFirstName());
						   ctx.setVariable("reciverName", superAdmin.getFirstName());
						   ctx.setVariable("createdBy",userLoginObj.getUsername());
						   UserLogin tLoginObj = charityObj.getLoginObj();
						   if(tLoginObj != null)
						   ctx.setVariable("username", charityObj.getCompanyName());

						   ctx.setVariable("cities", charityObj.getCitiesCovered());
						
						   ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						   ctx.setVariable("pmcName", charityObj.getCompanyName());
							 
						   ContactDetails details = superAdmin.getContactDetailsObj();
						   String superEmailId = "";
						   if(details != null){
							   superEmailId  = details.getEmailId();
							   mimeMessageHelper.setTo(superEmailId);
						   }
							
							mimeMessageHelper.setSubject("Email From Perpetual");
								this.templateEngine.initialize();
								String htmlContent2 = "";
								  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
									  htmlContent2 = this.templateEngine.process("pmc/subpmc/pmcAddCity-superadmin.html", ctx);
								  }else{
									   htmlContent2 = this.templateEngine.process("pmc/pmcAddCity-superadmin.html", ctx);  
								  }
							 mimeMessageHelper.setText(htmlContent2, true); // true = isHtml
							 			  
							  mailSender.send(mimeMessage);
							  logger.info("ADMIN : MAIL is sending to " + superEmailId);
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
   
   private void pmcEditCity(MailEventActor actor){
	   try{
			   	MimeMessage mimeMessage = mailSender.createMimeMessage();
				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
				UserLogin loginObj = null;
				Context ctx = new Context();
				logger.info("ACTOR IS " + MailEventActor.get(actor.id()).value() + " ITS ID IS " + actor.id());
				
		   switch(actor){
		   		
		   case PMC_ADMIN : 
				 
					 if(charityObj != null){
						loginObj = charityObj.getLoginObj();
					 
					 	 ctx.setVariable("pmcName", charityObj.getCompanyName());
						 ctx.setVariable("reference", charityObj.getUniqueReference());
						 ctx.setVariable("cities", charityObj.getCitiesCovered());
						 if(userLoginObj != null){
							 ctx.setVariable("createdBy", userLoginObj.getUsername());
						 }
						 
						 ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						 ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
						  
						  ContactDetails detailsObj = charityObj.getContactDetailsObj();
						  String emailId = "";
						  if(detailsObj != null){
							  emailId = detailsObj.getEmailId();
							  mimeMessageHelper.setTo(detailsObj.getEmailId());  
						  }
						  
						  mimeMessageHelper.setSubject("Email From Perpetual");
						  String htmlContent = "";
						  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
							  htmlContent = this.templateEngine.process("pmc/subpmc/pmcEditCity.html", ctx);
						  }else{
							   htmlContent = this.templateEngine.process("pmc/pmcEditCity.html", ctx);  
						  }
						  
						  mimeMessageHelper.setText(htmlContent, true); // true = isHtml
						 
						  mailSender.send(mimeMessage);
						  
						  logger.info("PMC-ADMIN : MAIL is sending to " + emailId);
					 }
			   break;
			   
		   case SUB_ADMIN: 
			   if(charityObj != null){
				   List<Admin> adminList = commonManager.loadAdminByPMC(charityObj.getId());
				   if(adminList != null && !adminList.isEmpty()){
					   for (Admin adminObj : adminList) {
						   loginObj = charityObj.getLoginObj();
							  if(loginObj != null){
								  ctx.setVariable("username", charityObj.getLoginObj().getUsername());
							  }
						 ctx.setVariable("pmcName", charityObj.getCompanyName());
						 ctx.setVariable("reference", charityObj.getUniqueReference());
						 ctx.setVariable("cities", charityObj.getCitiesCovered());
						 ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						 ctx.setVariable("contactName", charityObj.getAuthorisedPerson());
						 ctx.setVariable("receiverName", adminObj.getFirstName());
						 ctx.setVariable("createdBy",userLoginObj.getUsername());
					  
					  ContactDetails details = adminObj.getContactDetailsObj();
					  String subEmailId = "";
					  if(details != null){
						  subEmailId = details.getEmailId();
						  mimeMessageHelper.setTo(details.getEmailId());  
					  }else{
						  subEmailId = applicationSettings.getAdminEmailId();
						  mimeMessageHelper.setTo(applicationSettings.getAdminEmailId());
					  }
					   	
					   	mimeMessageHelper.setSubject("Email From Perpetual");
					   	this.templateEngine.initialize();
					    String htmlContent1 = "";
						  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
							  htmlContent1 = this.templateEngine.process("pmc/subpmc/pmcEditCity-admin.html", ctx);
						  }else{
							   htmlContent1 = this.templateEngine.process("pmc/pmcEdit-admin.html", ctx);  
						  }
					   	
					   	mimeMessageHelper.setText(htmlContent1, true); // true = isHtml
					 			  
					   	mailSender.send(mimeMessage);
					   	logger.info("ADMIN : MAIL is sending to " + subEmailId);
					}
				   }
			   }
			   break;
		   case SUPER_ADMIN:
			   if(charityObj != null){
				   Admin superAdmin = commonManager.loadsuperAdmin();
				   if(superAdmin != null){
					   ctx.setVariable("pmcName", charityObj.getCompanyName());
					   ctx.setVariable("reference", charityObj.getUniqueReference());
					   ctx.setVariable("receiverName", superAdmin.getFirstName());
					   ctx.setVariable("createdBy",userLoginObj.getUsername());
					   UserLogin tLoginObj = charityObj.getLoginObj();
					   if(tLoginObj != null)
					   ctx.setVariable("username", charityObj.getCompanyName());

					   ctx.setVariable("cities", charityObj.getCitiesCovered());
					
					   ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
					   ctx.setVariable("pmcName", charityObj.getCompanyName());
						 
					   ContactDetails details = superAdmin.getContactDetailsObj();
					   String superEmailId = "";
					   if(details != null){
						   superEmailId  = details.getEmailId();
						   mimeMessageHelper.setTo(superEmailId);
					   }
						
						mimeMessageHelper.setSubject("Email From Perpetual");
							this.templateEngine.initialize();
							String htmlContent2 = "";
							  if(userLoginObj.getUserTypeId() == UserType.SUPER_PMC.id()){
								  htmlContent2 = this.templateEngine.process("pmc/subpmc/pmcEditCity-superadmin.html", ctx);
							  }else{
								   htmlContent2 = this.templateEngine.process("pmc/pmcEdit-superadmin.html", ctx);  
							  }
						 mimeMessageHelper.setText(htmlContent2, true); // true = isHtml
						 			  
						  mailSender.send(mimeMessage);
						  logger.info("ADMIN : MAIL is sending to " + superEmailId);
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
	
	public void changePMCPassword(MailEventActor actor){
		   try{
				   	MimeMessage mimeMessage = mailSender.createMimeMessage();
					MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
					
					 Context ctx = new Context();
					  System.out.println("actor " + actor);
					  UserLogin loginObj = null;
			   switch(actor){
			   		
			   case PMC_ADMIN : 
				  
				   ctx.setVariable("name", charityObj.getCompanyName());
					loginObj = charityObj.getLoginObj();
					if(loginObj != null){
					ctx.setVariable("username", loginObj.getUsername());
					ctx.setVariable("password", loginObj.getPassword());
					}
						  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
						  
						  ContactDetails detailsObj = charityObj.getContactDetailsObj();
								  String emailId = "";
							  if(detailsObj != null){
								  emailId = detailsObj.getEmailId();
								  mimeMessageHelper.setTo(detailsObj.getEmailId());
							  }
						  
						  mimeMessageHelper.setSubject("Email From Perpetual.");
						  
						  final String htmlContent = this.templateEngine.process("pmc/pmcPasswordChange.html", ctx);
						  mimeMessageHelper.setText(htmlContent, true); // true = isHtml
						 
						  mailSender.send(mimeMessage);
						  logger.info("PMC : MAIL is sending to " + emailId);
					  
				   break;
				   
			   case SUB_ADMIN: 
				   
				   if(charityObj != null){
					   List<Admin> adminList = commonManager.loadAdminByPMC(charityObj.getId());
					   if(adminList != null && !adminList.isEmpty()){
						   for (Admin admin : adminList) {
							   	ctx.setVariable("name", charityObj.getCompanyName());
								ctx.setVariable("reference", charityObj.getUniqueReference());
								
								ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
								  ContactDetails details = admin.getContactDetailsObj();
								  String emailStr = "";
								  if(details != null){
									  mimeMessageHelper.setTo(details.getEmailId());
									  emailStr = details.getEmailId();
								  }
								   	
								   	mimeMessageHelper.setSubject("Email From Perpetual");
								   	this.templateEngine.initialize();
								   	final String htmlContent1 = this.templateEngine.process("pmc/pmcEdit-admin.html", ctx);
								   	mimeMessageHelper.setText(htmlContent1, true); // true = isHtml
								 			  
								   	mailSender.send(mimeMessage);
								   	logger.info("ADMIN : MAIL is sending to " + emailStr);
								}
					   }
				   }
				   
				   break;
			   case SUPER_ADMIN:
				   
				   ctx.setVariable("name", charityObj.getCompanyName());
				   loginObj = charityObj.getLoginObj();
					if(loginObj != null){
					ctx.setVariable("username", loginObj.getUsername());
					ctx.setVariable("password", loginObj.getPassword());
					}
				  
				  ctx.setVariable("senderName", applicationSettings.getCommonEmailSenderName());
				  
				  mimeMessageHelper.setTo(applicationSettings.getAdminEmailId());
				  mimeMessageHelper.setSubject("Email From Perpetual.");
				  this.templateEngine.initialize();
				  final String htmlContent2 = this.templateEngine.process("pmc/pmcPasswordChange-superadmin.html", ctx);
				  mimeMessageHelper.setText(htmlContent2, true); // true = isHtml
				 			  
				  mailSender.send(mimeMessage);
				  logger.info("ADMIN : MAIL is sending to " + applicationSettings.getAdminEmailId());
				  
				   break;
			default:
				break;
			   }
		   }catch(Exception e){
				e.printStackTrace();
			}
	   }
	   
}
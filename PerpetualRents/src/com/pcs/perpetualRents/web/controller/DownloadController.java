package com.pcs.perpetualRents.web.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pcs.perpetualRents.business.bean.File;
import com.pcs.perpetualRents.business.bean.LandLord;
import com.pcs.perpetualRents.business.bean.PropertyDeveloper;
import com.pcs.perpetualRents.business.bean.PropertyDocumentDownloadStatus;
import com.pcs.perpetualRents.business.bean.PropertyResponse;
import com.pcs.perpetualRents.business.bean.UserLogin;
import com.pcs.perpetualRents.commonFunction.CommonFunctions;
import com.pcs.perpetualRents.enm.ObjectType;
import com.pcs.perpetualRents.manager.FileManager;
import com.pcs.perpetualRents.manager.LandLordManager;
import com.pcs.perpetualRents.manager.PropertyDeveloperManager;
import com.pcs.perpetualRents.manager.PropertyManager;
import com.pcs.perpetualRents.web.delegates.AjaxJSONDelegate;

@Controller
public class DownloadController {
	
	@Autowired
	private PropertyManager propertyManager;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private LandLordManager landLordManager;
	@Autowired
	private AjaxJSONDelegate JSONDelegates;
	@Autowired
	private PropertyDeveloperManager propertyDeveloperManager;
	
	private HttpSession sessionObj;
	private Long propertyId;
	private UserLogin loginObj;
	
	@RequestMapping("download/uploadedDocuments")
	public void uploadedDocuments(HttpServletRequest request, HttpServletResponse response){               // method to download file
		File tFileObj = null;
		if(CommonFunctions.isLong(request.getParameter("fileId")))
			tFileObj = fileManager.loadFileById(Long.parseLong(request.getParameter("fileId")));
		
		if(tFileObj != null){
			try {

				ServletOutputStream oStream = response.getOutputStream();
		        response.setContentLength((int) tFileObj.getSize());

		        response.setHeader("Content-Disposition", "attachment; filename=\"" + tFileObj.getFileName() + "\"");     //fixed format to  download file
		        response.setHeader("Content-Type", "\"" + tFileObj.getContentType() + "\"");     //fixed format to  download file
		        
		        FileInputStream iStream = new FileInputStream(tFileObj.getFilePath());
		        byte[] byteBuffer = new byte[iStream.available()];
		        iStream.read(byteBuffer);
		        iStream.close();
		        oStream.write(byteBuffer);
		        oStream.flush();
		        oStream.close();
		       
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("DownloadController - download/uploadedDocuments : FILE NOT FOUND [" + request.getParameter("fileId") + "]");
		}
	}
	
	@RequestMapping("download/adpl1")
	public void adpl1(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sessionObj = request.getSession(false);
		if(sessionObj != null){
			loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		}

		File adpl1Obj = propertyManager.loadADPL1File();
		if(adpl1Obj != null){
			try {
				int length = (int)adpl1Obj.getSize();
				ServletOutputStream oStream = response.getOutputStream();
		        response.setContentLength(length);

		        response.setHeader("Content-Disposition", "attachment; filename=\"" + adpl1Obj.getFileName() + "\"");     //fixed format to  download file
		        response.setHeader("Content-Type", "\"" + adpl1Obj.getContentType() + "\"");     //fixed format to  download file
		        
		        FileInputStream iStream = new FileInputStream(adpl1Obj.getFilePath());
		        byte[] byteBuffer = new byte[iStream.available()];
		        iStream.read(byteBuffer);
		        iStream.close();
		        oStream.write(byteBuffer);
		        oStream.flush();
		        oStream.close();
		       
			} catch (Exception e) {e.printStackTrace();}
		}
	
			if(loginObj != null){
				LandLord landlordObj = landLordManager.loadLandLordByUserLogin(loginObj);
				if(landlordObj != null){
					PropertyDocumentDownloadStatus tempStatusObj = landLordManager.loadDocumentStatusByLandlordId(landlordObj.getId());
					if(tempStatusObj != null && tempStatusObj.getPropertyId() != null){
						landLordManager.updateDocStatus(PropertyDocumentDownloadStatus.TABLE_NAME, new String[]{PropertyDocumentDownloadStatus.IS_FIRSTFILE_DOWNLOADED},
								new String[]{""+1}, new String[]{PropertyDocumentDownloadStatus.OBJECT_TYPE_ID, PropertyDocumentDownloadStatus.OBJECT_ID}, new String[]{""+ObjectType.LANDLORD.id(),  ""+landlordObj.getId()});
					}else{
						PropertyDocumentDownloadStatus statusObj = new PropertyDocumentDownloadStatus(true, false, false, ObjectType.LANDLORD.id(), landlordObj.getId());
						landLordManager.createPropertyDocumentDownloadStatus(statusObj, loginObj.getId());
					}
				
				}
			}
		}
	
	
	
	@RequestMapping("download/adpl3")
	public void adpl3(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sessionObj = request.getSession(false);
		if(sessionObj != null){
			loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		}
				File adpl3Obj = propertyManager.loadADPL3File();
				if(adpl3Obj != null){
					try {
						int length = (int)adpl3Obj.getSize();
						ServletOutputStream oStream = response.getOutputStream();
				        response.setContentLength(length);
				        String fileName = adpl3Obj.getFileName();

				        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");     //fixed format to  download file
				        
				        byte[] byteBuffer = new byte[length];
				        InputStream iStream = new ByteArrayInputStream(adpl3Obj.getFileData());
				        iStream.read(byteBuffer);
				        oStream.write(byteBuffer,0,length);
				        
				        iStream.close();
				        oStream.flush();
				        oStream.close();
				       
					} catch (Exception e) {e.printStackTrace();}
				}
			
			
			if(loginObj != null){
				LandLord landlordObj = landLordManager.loadLandLordByUserLogin(loginObj);
				if(landlordObj != null){
					PropertyDocumentDownloadStatus tempStatusObj = landLordManager.loadDocumentStatusByLandlordId(landlordObj.getId());
					if(tempStatusObj != null && tempStatusObj.getPropertyId() != null){
						landLordManager.updateDocStatus(PropertyDocumentDownloadStatus.TABLE_NAME, new String[]{PropertyDocumentDownloadStatus.IS_SECONDFILE_DOWNLOADED},
								new String[]{""+1}, new String[]{PropertyDocumentDownloadStatus.OBJECT_TYPE_ID, PropertyDocumentDownloadStatus.OBJECT_ID}, new String[]{""+ObjectType.LANDLORD.id(),  ""+landlordObj.getId()});
					}else{
						PropertyDocumentDownloadStatus statusObj = new PropertyDocumentDownloadStatus(false, true, false, ObjectType.LANDLORD.id(), landlordObj.getId());
						landLordManager.createPropertyDocumentDownloadStatus(statusObj, loginObj.getId());
					}
				
				}
			}
		
		}
	
	@RequestMapping("download/landlordRegistration")
	public void landlordRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sessionObj = request.getSession(false);

		File landlordRegisterationFileObj = propertyManager.landLordRegistrationFile();
		if(landlordRegisterationFileObj != null){
			try {
				int length = (int)landlordRegisterationFileObj.getSize();
				ServletOutputStream oStream = response.getOutputStream();
		        response.setContentLength(length);
		        String fileName = landlordRegisterationFileObj.getFileName();

		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");     //fixed format to  download file
		        
		        byte[] byteBuffer = new byte[length];
		        InputStream iStream = new ByteArrayInputStream(landlordRegisterationFileObj.getFileData());
		        iStream.read(byteBuffer);
		        oStream.write(byteBuffer,0,length);
		        
		        iStream.close();
		        oStream.flush();
		        oStream.close();
		       
			} catch (Exception e) {e.printStackTrace();}
		}
	
			if(sessionObj != null){
				UserLogin loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
				if(loginObj != null){
					LandLord landlordObj = landLordManager.loadLandLordByUserLogin(loginObj);
					if(landlordObj != null){
						PropertyDocumentDownloadStatus tempStatusObj = landLordManager.loadDocumentStatusByLandlordId(landlordObj.getId());
						if(tempStatusObj != null && tempStatusObj.getPropertyId() != null){
							landLordManager.updateDocStatus(PropertyDocumentDownloadStatus.TABLE_NAME, new String[]{PropertyDocumentDownloadStatus.IS_THIRDFILE_REGISTERATION_DOWNLOADED},
									new String[]{""+1}, new String[]{PropertyDocumentDownloadStatus.OBJECT_TYPE_ID, PropertyDocumentDownloadStatus.OBJECT_ID}, new String[]{""+ObjectType.LANDLORD.id(),  ""+landlordObj.getId()});
						}else{
							PropertyDocumentDownloadStatus statusObj = new PropertyDocumentDownloadStatus(false, false, true, ObjectType.LANDLORD.id(), landlordObj.getId());
							landLordManager.createPropertyDocumentDownloadStatus(statusObj, loginObj.getId());
						}
					
					}
				}
			}
		}
	
	@RequestMapping("download/getPropertyDocumentsDownloadStatus")
	public void getPropertyDocumentsDownloadStatus(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		HttpSession sessionObj = request.getSession(false);
		JSONObject jObj = new JSONObject();
		if(sessionObj != null){
			loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
			LandLord landlordObj = landLordManager.loadLandLordByUserLogin(loginObj);
			if(landlordObj != null){
				PropertyDocumentDownloadStatus statusObj = landLordManager.loadDocumentStatusByLandlordId(landlordObj.getId());
				if(statusObj != null){
					if(!statusObj.isFirstFileDownloaded()){
						jObj.put("success", false);
						jObj.put("message", "Please go through the ADPL1 First.");
					}else if(!statusObj.isSecondFileDownloaded()){
						jObj.put("success", false);
						jObj.put("message", "Please go through the ADPL3 First.");
					}else if(!statusObj.isThirdFileDownloaded()){
						jObj.put("success", false);
						jObj.put("message", "Please go through the Landlord Registeration Document First.");
					}else{
						jObj.put("success", true);
						jObj.put("message", "Ready to go.");
					}
				}else{
					jObj.put("success", false);
					jObj.put("message", "Please go through the documents first.");
				}
			}else{
				jObj.put("success", false);
				jObj.put("message", "Please go through the documents first.");
			}
		}
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("download/nonDisclosureAgreement")
	public void nonDisclosureAgreement(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sessionObj = request.getSession(false);
		if(sessionObj != null){
			loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		}

		File nonDisclosureAgreementObj = propertyDeveloperManager.loadNonDisclosureAgreement();
		if(nonDisclosureAgreementObj != null){
			try {
				int length = (int)nonDisclosureAgreementObj.getSize();
				ServletOutputStream oStream = response.getOutputStream();
		        response.setContentLength(length);
		        String fileName = nonDisclosureAgreementObj.getFileName();

		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");     //fixed format to  download file
		        
		        byte[] byteBuffer = new byte[length];
		        InputStream iStream = new ByteArrayInputStream(nonDisclosureAgreementObj.getFileData());
		        iStream.read(byteBuffer);
		        oStream.write(byteBuffer,0,length);
		        
		        iStream.close();
		        oStream.flush();
		        oStream.close();
		       
			} catch (Exception e) {e.printStackTrace();}
		}
	
			if(loginObj != null){
				PropertyDeveloper propDeveloperObj = propertyDeveloperManager.loadPropertyDeveloperByUserLogin(loginObj);
				if(propDeveloperObj != null){
					PropertyDocumentDownloadStatus tempStatusObj = propertyDeveloperManager.loadDocumentStatusByPD(propDeveloperObj.getId());
					if(tempStatusObj != null && tempStatusObj.getPropertyId() != null){
						propertyDeveloperManager.updateDocStatus(PropertyDocumentDownloadStatus.TABLE_NAME, new String[]{PropertyDocumentDownloadStatus.IS_FIRSTFILE_DOWNLOADED},
								new String[]{""+1}, new String[]{PropertyDocumentDownloadStatus.OBJECT_TYPE_ID, PropertyDocumentDownloadStatus.OBJECT_ID}, new String[]{""+ObjectType.PROPERTY_DEVELOPER.id(),  ""+propDeveloperObj.getId()});
					}else{
						PropertyDocumentDownloadStatus statusObj = new PropertyDocumentDownloadStatus(true, false, false, ObjectType.PROPERTY_DEVELOPER.id(), propDeveloperObj.getId());
						propertyDeveloperManager.createPropertyDocumentDownloadStatus(statusObj, loginObj.getId());
					}
				
				}
			}
		}
	
	@RequestMapping("download/preConstructionAgreement")
	public void preConstructionAgreement(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sessionObj = request.getSession(false);
		if(sessionObj != null){
			loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		}

		File preConstructionAgreementObj = propertyDeveloperManager.loadPreConstructionAgreement();
		if(preConstructionAgreementObj != null){
			try {
				int length = (int)preConstructionAgreementObj.getSize();
				ServletOutputStream oStream = response.getOutputStream();
		        response.setContentLength(length);
		        String fileName = preConstructionAgreementObj.getFileName();

		        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");     //fixed format to  download file
		        
		        byte[] byteBuffer = new byte[length];
		        InputStream iStream = new ByteArrayInputStream(preConstructionAgreementObj.getFileData());
		        iStream.read(byteBuffer);
		        oStream.write(byteBuffer,0,length);
		        
		        iStream.close();
		        oStream.flush();
		        oStream.close();
		       
			} catch (Exception e) {e.printStackTrace();}
		}
	
			if(loginObj != null){
				PropertyDeveloper propDeveloperObj = propertyDeveloperManager.loadPropertyDeveloperByUserLogin(loginObj);
				if(propDeveloperObj != null){
					PropertyDocumentDownloadStatus tempStatusObj = propertyDeveloperManager.loadDocumentStatusByPD(propDeveloperObj.getId());
					if(tempStatusObj != null && tempStatusObj.getPropertyId() != null){
						propertyDeveloperManager.updateDocStatus(PropertyDocumentDownloadStatus.TABLE_NAME, new String[]{PropertyDocumentDownloadStatus.IS_SECONDFILE_DOWNLOADED},
								new String[]{""+1}, new String[]{PropertyDocumentDownloadStatus.OBJECT_TYPE_ID, PropertyDocumentDownloadStatus.OBJECT_ID}, new String[]{""+ObjectType.PROPERTY_DEVELOPER.id(),  ""+propDeveloperObj.getId()});
					}else{
						PropertyDocumentDownloadStatus statusObj = new PropertyDocumentDownloadStatus(false, true, false, ObjectType.PROPERTY_DEVELOPER.id(), propDeveloperObj.getId());
						propertyDeveloperManager.createPropertyDocumentDownloadStatus(statusObj, loginObj.getId());
					}
				
				}
			}
		}
	
	@RequestMapping("download/getPDDocumentsDownloadStatus")
	public void getPDDocumentsDownloadStatus(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		HttpSession sessionObj = request.getSession(false);
		JSONObject jObj = new JSONObject();
		if(sessionObj != null){
			loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
			PropertyDeveloper propDeveloperObj = propertyDeveloperManager.loadPropertyDeveloperByUserLogin(loginObj);
			if(propDeveloperObj != null){
				PropertyDocumentDownloadStatus statusObj = propertyDeveloperManager.loadDocumentStatusByPD(propDeveloperObj.getId());
				if(statusObj != null){
					if(!statusObj.isFirstFileDownloaded()){
						jObj.put("success", false);
						jObj.put("message", "Please go through the Non Disclosure Agreement First.");
					}else if(!statusObj.isSecondFileDownloaded()){
						jObj.put("success", false);
						jObj.put("message", "Please go through the Pre Contruction Agreement First.");
					}else{
						jObj.put("success", true);
						jObj.put("message", "Ready to go.");
					}
				}else{
					jObj.put("success", false);
					jObj.put("message", "Please go through the documents first.");
				}
			}else{
				jObj.put("success", false);
				jObj.put("message", "Please go through the documents first.");
			}
		}
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("download/certificates")
	public void certificates(HttpServletRequest request, HttpServletResponse response){
		
		String eCommand = request.getParameter("param");
		HttpSession sessionObj = request.getSession(false);
		
		Long propertyId = (Long) sessionObj.getAttribute("propertyId");
		if(eCommand != null && propertyId != null){
			PropertyResponse responseObj = propertyManager.loadPropertyResponseByFieldName(propertyId, eCommand);
			if(responseObj != null){
				File fileObj = responseObj.getFileObj();
				if(fileObj != null){
					try {
						int length = (int)fileObj.getSize();
						ServletOutputStream oStream = response.getOutputStream();
				        response.setContentLength(length);
				        String fileName = fileObj.getFileName();
		
				        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				        
				        byte[] byteBuffer = new byte[length];
				        InputStream iStream = new ByteArrayInputStream(fileObj.getFileData());
				        iStream.read(byteBuffer);
				        oStream.write(byteBuffer,0,length);
				        
				        iStream.close();
				        oStream.flush();
				        oStream.close();
				       
					} catch (Exception e) {}
				}
			}
		}
	}
	
	@RequestMapping("download/adplPDF")
	public void adplPDF(HttpServletRequest request, HttpServletResponse response){
		sessionObj = request.getSession(false);
		propertyId = (Long) sessionObj.getAttribute("propertyId");
		if(propertyId != null){
			String logoPath = request.getServletContext().getRealPath("/resource/images/logo.png");
			File fileObj = propertyManager.loadADPL2PdfByPropertyId(propertyId, logoPath);
			if(fileObj != null){
				try {
					int length = (int)fileObj.getSize();
					ServletOutputStream oStream = response.getOutputStream();
			        response.setContentLength(length);
			        String fileName = fileObj.getFileName();
	
			        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".pdf\"");
			        
			        byte[] byteBuffer = new byte[length];
			        InputStream iStream = new ByteArrayInputStream(fileObj.getFileData());
			        iStream.read(byteBuffer);
			        oStream.write(byteBuffer,0,length);
			        
			        iStream.close();
			        oStream.flush();
			        oStream.close();
			       
				} catch (Exception e) {e.printStackTrace();}
			}
		}
	}
	
	@RequestMapping("download/proposedPropertyPDF")
	public void proposedPropertyPDF(HttpServletRequest request, HttpServletResponse response){
		File tFileObj = null;
		if(CommonFunctions.isLong(request.getParameter("pdId")))
			tFileObj = propertyDeveloperManager.loadPDPropertyPdfByProposedPropertyId(Long.parseLong(request.getParameter("pdId")));
		
		if(tFileObj != null){
			try {
				
				InputStream iStream = new ByteArrayInputStream(tFileObj.getFileData());
				ServletOutputStream oStream = response.getOutputStream();
		        response.setContentLength(iStream.available());
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + tFileObj.getFileName() + "\"");
		        byte[] byteBuffer = new byte[iStream.available()];
		        iStream.read(byteBuffer);
		        oStream.write(byteBuffer);
		        iStream.close();
		        oStream.flush();
		        oStream.close();
		       
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
}

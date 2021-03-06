package com.pcs.perpetualRents.web.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.SwingUtilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring3.SpringTemplateEngine;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.perpetualRents.business.bean.CharityRegisteration;
import com.pcs.perpetualRents.business.bean.City;
import com.pcs.perpetualRents.business.bean.ContactDetails;
import com.pcs.perpetualRents.business.bean.Contractor;
import com.pcs.perpetualRents.business.bean.ContractorRegisterationStep2;
import com.pcs.perpetualRents.business.bean.ContractorRegisterationStep3;
import com.pcs.perpetualRents.business.bean.ContractorRegisterationStep4;
import com.pcs.perpetualRents.business.bean.Country;
import com.pcs.perpetualRents.business.bean.Expertise;
import com.pcs.perpetualRents.business.bean.File;
import com.pcs.perpetualRents.business.bean.LandLord;
import com.pcs.perpetualRents.business.bean.Lead;
import com.pcs.perpetualRents.business.bean.PerpetualCity;
import com.pcs.perpetualRents.business.bean.PropertyDeveloper;
import com.pcs.perpetualRents.business.bean.UserLogin;
import com.pcs.perpetualRents.common.bean.ApplicationSettings;
import com.pcs.perpetualRents.enm.CompanyType;
import com.pcs.perpetualRents.enm.ContractorFunctionality;
import com.pcs.perpetualRents.enm.DocumentType;
import com.pcs.perpetualRents.enm.ObjectType;
import com.pcs.perpetualRents.manager.CommonManager;
import com.pcs.perpetualRents.manager.ContractorManager;
import com.pcs.perpetualRents.manager.FileManager;
import com.pcs.perpetualRents.manager.LandLordManager;
import com.pcs.perpetualRents.manager.LeadManager;
import com.pcs.perpetualRents.manager.PropertyDeveloperManager;
import com.pcs.perpetualRents.manager.UserLoginManager;
import com.pcs.perpetualRents.util.mail.CharityRegisterationMailUtility;
import com.pcs.perpetualRents.web.delegates.AjaxJSONDelegate;

@Controller
public class RegisterController {

	@Autowired
	private LandLordManager landLordManager;
	@Autowired
	private AjaxJSONDelegate JSONDelegates;
	@Autowired
	private ContractorManager contractorManager;
	@Autowired
	private UserLoginManager loginManager;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private LeadManager leadManager;
	@Autowired
	private ApplicationSettings applicationSettings;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private CommonManager commonManager;
	@Autowired
	private SpringTemplateEngine templateEngine;
	@Autowired
	private PropertyDeveloperManager propertyDeveloperManager;
	
	private ObjectMapper mapper = new ObjectMapper();
	private boolean execStatus;
	HttpSession sessionObj;
	
	
	@RequestMapping("register/contractorGeneralInformation")
	public void contractorGeneralInformation(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		Contractor infoObj =  mapper.readValue(request.getInputStream(), Contractor.class);
		JSONObject jObj = new JSONObject();
		sessionObj = request.getSession(false); 
		if(infoObj != null){
			String contractorDocumentFolderPath = request.getServletContext().getRealPath("/resource/documentFolder/contractorDocs");
			applicationSettings.setContractorDocumentFolderPath(contractorDocumentFolderPath);
			
			ContactDetails detailsObj = infoObj.getContactDetailsObj();
			if(detailsObj != null){
				String emailId = detailsObj.getEmailId();
				if(emailId != null && emailId.length() > 0){
					execStatus = commonManager.validateEmailId(emailId.trim());
					if(execStatus){
						execStatus = contractorManager.createContractor(infoObj, new Long(0));
						if(execStatus){
							sessionObj.setAttribute("contractorId", infoObj.getId());
							jObj.put("success", true);
						}else{
							jObj.put("success", false);
						}
					}else{
						jObj.put("success", false);
						jObj.put("message", "This Email Id is already registered with us. Please try with different one.");
					}
				}
			}
			
		}
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/contractorRegisterationStep2")
	public ModelAndView contractorRegisterationStep2(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		try{
			ContractorRegisterationStep2 infoObj =  mapper.readValue(request.getInputStream(), ContractorRegisterationStep2.class);
			sessionObj = request.getSession(false);
			JSONObject jObj = new JSONObject();;
			if(infoObj != null){
				Long contractorId = (Long) sessionObj.getAttribute("contractorId");
				if(contractorId != null){
					infoObj.setContractorId(contractorId);
					execStatus = contractorManager.saveContractorRegisterationStep2(infoObj, new Long(0));
					if(execStatus){
						jObj.put("success", true);
					}else{
						jObj.put("success", false);
					}
				}else{
					return new ModelAndView("user/contractor/contractorGeneralInformation");
				}
			}
			JSONDelegates.setResponse(jObj, response);
		return null;
		}catch(Exception e){
			return new ModelAndView("user/contractor/contractorGeneralInformation");
		}
	}
	
	@RequestMapping("register/loadContractorFunctionalityForCost")
	public void loadContractorFunctionalityForCost(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		JSONObject jObj = new JSONObject();;
		JSONArray jArray = null;
		Long contractorId = (Long) sessionObj.getAttribute("contractorId");
		if(contractorId != null){
		Contractor tConObj = contractorManager.loadContractorById(contractorId);
		if(tConObj != null){
			jArray = new JSONArray();
			List<Expertise> lExpertises = tConObj.getExpertiseList();
			if(lExpertises != null){
				for (Expertise tExpObj : lExpertises) {
					if(tExpObj != null){
						jArray.put(ContractorFunctionality.get(tExpObj.getFunctionalityId()).toJSON());
						}
					}
				}
			jObj.put("success", true);
			jObj.put("data",jArray);
 			}else{
 				jObj.put("success",false);
 			}
		}else{
			jObj.put("success",false);
		}
			
		
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/contractorRegisterationStep3File")
	public ModelAndView contractorRegisterationStep3File(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
			sessionObj = request.getSession(false);
			JSONObject jObj = new JSONObject();;
			JSONObject _jObj = new JSONObject();
			Long contractorId = (Long) sessionObj.getAttribute("contractorId");
			if(contractorId != null){
				MultipartHttpServletRequest multiPartRequest = null;
				MultipartFile partObj = null;
				File fileObj = null;
				try{
					if(request instanceof MultipartHttpServletRequest){
						multiPartRequest = (MultipartHttpServletRequest) request;
						partObj = multiPartRequest.getFile("writtenFile");
						if(partObj != null && partObj.getSize() != 0){
							fileObj = new File(partObj.getOriginalFilename(), partObj.getSize(), ObjectType.CONTRACTOR.id(), contractorId, partObj.getBytes(), partObj.getContentType(), DocumentType.CERTIFICATE.id());
							if(fileObj != null){
								Long writtenPolicyFileId = fileManager.saveFile(fileObj, new Long(0));
								if(writtenPolicyFileId != null){
									_jObj.put("writtenPolicyFileId", writtenPolicyFileId);
								}
							}
						}
						
						partObj = multiPartRequest.getFile("equalityFile");
						if(partObj != null && partObj.getSize() != 0){
							fileObj = new File(partObj.getOriginalFilename(), partObj.getSize(), ObjectType.CONTRACTOR.id(), contractorId, partObj.getBytes(), partObj.getContentType(), DocumentType.CERTIFICATE.id());
							if(fileObj != null){
								Long equalityPolicyFileId = fileManager.saveFile(fileObj, new Long(0));
								if(equalityPolicyFileId != null){
									_jObj.put("equalityPolicyFileId", equalityPolicyFileId);
								}
							}
						}
						jObj.put("success", true);
						jObj.put("data", _jObj);
					}else{
						jObj.put("success", false);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				return new ModelAndView("user/contractor/contractorGeneralInformation");
			}
			
			JSONDelegates.setResponse(jObj, response);
			return null;
		}
	
	@RequestMapping("register/contractorRegisterationStep3")
	public ModelAndView contractorRegisterationStep3(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		try{
			ContractorRegisterationStep3 infoObj =  mapper.readValue(request.getInputStream(), ContractorRegisterationStep3.class);
			System.out.println(infoObj.toString());
			sessionObj = request.getSession(false);
			JSONObject jObj = new JSONObject();;
			Long contractorId = (Long) sessionObj.getAttribute("contractorId");
			if(infoObj != null && contractorId != null){
				infoObj.setContractorId(contractorId);
				
				UserLogin loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
				Long userId;
				if(loginObj != null)
					userId = loginObj.getId();
				else
					userId = 0l;
				
				execStatus = contractorManager.saveContractorRegisterationStep3(infoObj,userId);
				if(execStatus){
					jObj.put("success", true);
				}else{
					jObj.put("success", false);
				}
			}else{
				return new ModelAndView("user/contractor/contractorGeneralInformation");
			}
			JSONDelegates.setResponse(jObj, response);
		return null;
		}catch(Exception e){
			e.printStackTrace();
			return new ModelAndView("user/contractor/contractorGeneralInformation");
		}
	}
	
	@RequestMapping("register/contractorRegisterationStep4")
	public ModelAndView contractorRegisterationStep4(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		try{
			ContractorRegisterationStep4 infoObj =  mapper.readValue(request.getInputStream(), ContractorRegisterationStep4.class);
			sessionObj = request.getSession(false);
			JSONObject jObj = new JSONObject();;
			Long contractorId = (Long) sessionObj.getAttribute("contractorId");
			if(infoObj != null && contractorId != null){
				UserLogin loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
				Long userId;
				if(loginObj != null)
					userId = loginObj.getId();
				else
					userId = 0l;
				infoObj.setContractorId(contractorId);
				contractorManager.saveContractorRegisterationStep4(infoObj, userId);
				jObj.put("success", true);
			}else{
				jObj.put("success", false);
				return new ModelAndView("user/contractor/contractorGeneralInformation");
			}
			JSONDelegates.setResponse(jObj, response);
			return null;
		}catch(Exception e){
			return new ModelAndView("user/contractor/contractorGeneralInformation");
		}
	}
	
	@RequestMapping("register/contractorRegisterationSuccess")
	public ModelAndView contractorregisterSuccess(){
		return new ModelAndView("user/contractor/success");
	}
	
	@RequestMapping("register/loadCompanyType")
	public void loadCompanyType(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject jObj = new JSONObject();
			jObj.put("success", true);
			jObj.put("data", CompanyType.toJArray());
			
		JSONDelegates.setResponse(jObj, response);	
	}
	
	@RequestMapping("register/loadCitiesForLandlord")
	public void loadCitiesForLandlord(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONArray jArray = new JSONArray();
		JSONObject jObj = new JSONObject();;
		List<City> cityList = landLordManager.loadCities();
		if(cityList != null && !cityList.isEmpty()){
				
			for (City city : cityList) {
				jArray.put(city.toJSON(true));
			}
		}
		jObj.put("success", true);
		jObj.put("data", jArray);
		
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/validateUser")
	public void validateUser(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject jObj = new JSONObject();;
		String userName = request.getParameter("user");
		if(userName != null && userName.length() != 0){
			execStatus = loginManager.validateUser(userName);
			if(execStatus){
				jObj.put("success", true);
			}else{
				jObj.put("success", false);
			}
		}else{
			jObj.put("success", false);
		}
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/loadCitiesForContractor")
	public void loadCitiesForContractor(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONArray jArray = new JSONArray();
		JSONObject jObj = new JSONObject();;
		List<PerpetualCity> cityList = contractorManager.loadAllPerpetualCity();
		if(cityList != null && !cityList.isEmpty()){
				
			for (PerpetualCity city : cityList) {
				jArray.put(city.toJSON(true));
			}
		}
		jObj.put("success", true);
		jObj.put("data", jArray);
		
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/loadContractorFunctionality")
	public void loadContractorFunctionality(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		
		JSONObject jObj = new JSONObject();;
		JSONArray tJsonArray = ContractorFunctionality.toJArray();
		if(tJsonArray != null && tJsonArray.length() > 0){
			jObj.put("data", ContractorFunctionality.toJArray());
			jObj.put("success", true);
		}else{
			jObj.put("success", false);
		}
			
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/createLandlord")
	public void createLandlord(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		LandLord landLordObj = mapper.readValue(request.getInputStream(),LandLord.class);
		JSONObject jObj = new JSONObject();
		Country countryObj = new Country();
				countryObj.setId(1l);
				countryObj.setCountryAlias("UK");
				countryObj.setCountryName("United Kingdom");
				countryObj.setCurrency("GBP");
		landLordObj.getAddressObj().setCountryObj(countryObj);
		
		String emailId = landLordObj.getPrimaryEmailId();
		if(emailId != null && emailId.length() > 0){
			execStatus = commonManager.validateEmailId(emailId);
			if(execStatus){
				String secondaryEmailId = landLordObj.getSecondaryEmailId();
				if(secondaryEmailId != null && secondaryEmailId.length() > 0){
					execStatus = commonManager.validateEmailId(secondaryEmailId);
				}
			}
			if(execStatus){
				execStatus = landLordManager.createLandLord(landLordObj, null);
				if(execStatus){
					jObj.put("message", "LandLord has been successfully saved.");
					jObj.put("success", execStatus);
				}else{
					jObj.put("message", landLordObj.getErrorMessage());
					jObj.put("success", execStatus);
				}
			}else{
				jObj.put("success", false);
				jObj.put("message", "This Email Id is already registered with us. Please try with different one.");
			}
		}
		
		JSONDelegates.setResponse(jObj, response);
	}
	
	/*---------------------------PropertyDeveloper-----------------------------------------*/
	@RequestMapping("register/createPropertyDeveloper")
	public void createPropertyDeveloper(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
	 PropertyDeveloper propertyDeveloperObj = mapper.readValue(request.getInputStream(), PropertyDeveloper.class);	
	 	JSONObject jObj = new JSONObject();
	 		Country countryObj = new Country();
				countryObj.setId(1l);
				countryObj.setCountryAlias("UK");
				countryObj.setCountryName("United Kingdom");
				countryObj.setCurrency("GBP");
			propertyDeveloperObj.getAddressObj().setCountryObj(countryObj);
		String emailID = propertyDeveloperObj.getPrimaryEmailId();
		if(emailID.length()>0 && emailID != null){
			execStatus = commonManager.validateEmailId(emailID);
			if(execStatus){
				String secondaryEmailID = propertyDeveloperObj.getSecondaryEmailId();
				if(secondaryEmailID != null && secondaryEmailID.length()> 0){
					execStatus  = commonManager.validateEmailId(secondaryEmailID);
				}
			}
		  if(execStatus){
			  execStatus = propertyDeveloperManager.createPropertyDeveloper(propertyDeveloperObj, null);
			  if(execStatus){
					jObj.put("message", "Property Developer has been successfully saved.");
					jObj.put("success", execStatus);
				}else{
					jObj.put("message", propertyDeveloperObj.getErrorMessage());
					jObj.put("success", execStatus);
				}
		   }
		  else{
				jObj.put("success", false);
				jObj.put("message", "This Email Id is already registered with us. Please try with different one.");
		  	  }
		}
		JSONDelegates.setResponse(jObj, response);
	}
	/*-----------------------------------------------------------------------------------------------------------------*/
	
	@RequestMapping("register/createContractor")
	public void createContractor(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		JSONObject jObj = new JSONObject();;
		Contractor	contractorObj = mapper.readValue(request.getInputStream(),Contractor.class);
		execStatus = contractorManager.createContractor(contractorObj, null);
		if(execStatus){
			jObj.put("message", "LandLord has been successfully saved.");
			jObj.put("success", execStatus);
		}else{
			jObj.put("message", contractorObj.getErrorMessage());
			jObj.put("success", execStatus);
		}	
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/loadLandlordCities")
	public void loadLandlordCities(HttpServletResponse response) throws JSONException{
		JSONObject jObj = new JSONObject();;
		JSONArray jArray = new JSONArray();
		List<City> cityList = landLordManager.loadCityByObjectType(ObjectType.SUPER_PMC.id(), null);
		if(cityList != null && !cityList.isEmpty()){
			for (City city : cityList) {
				jArray.put(city.toJSON(true));
			}
		}
		jObj.put("success", true);
		jObj.put("data", jArray);
		
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/searchLandlordCities")
	public void searchCities(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String searchTerm = request.getParameter("term");
		
		JSONObject jObj = new JSONObject();;
		JSONArray jarray = new JSONArray();
		List<City> cityList = landLordManager.loadCityByObjectType(ObjectType.SUPER_PMC.id(), searchTerm);
		if(cityList != null && !cityList.isEmpty()){
			for (City city : cityList) {
				jarray.put(city.toJSON(false));
			}
		}
		jObj.put("success", true);
		jObj.put("data", jarray);
		
		JSONDelegates.setResponse(jarray, response);
	}
	
	@RequestMapping("register/registerLead")
	public void registerLead(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		Lead obj = mapper.readValue(request.getInputStream(), Lead.class);
		JSONObject jObj = new JSONObject();
		boolean status = false;
		if(obj != null){
			status = leadManager.createLead(obj);
			jObj.put("success", status);
		}
		JSONDelegates.setResponse(jObj, response);
	}
	
	@RequestMapping("register/charityRegisteration")
	public void charityRegisteration(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		try{
			JSONObject jObj = new JSONObject();;
				CharityRegisteration registerationObj = mapper.readValue(request.getInputStream(), CharityRegisteration.class);
			if(registerationObj != null){
				SwingUtilities.invokeLater(new CharityRegisterationMailUtility(registerationObj, applicationSettings, mailSender, templateEngine, commonManager));
				jObj.put("success", true);
			}else{
				jObj.put("success", true);
			}
			JSONDelegates.setResponse(jObj, response);
		}catch (Exception e) { }
	}
	
	@RequestMapping("register/charityRegisterationSuccess")
	public ModelAndView charityRegisterationSuccess(){
		return new ModelAndView("iframe/charityRegisterationSuccess");
	}
	
	@RequestMapping("register/evaluation")
	public void evaluation(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException, JSONException{
		
	}
	
	@RequestMapping("register/redirectcontractorRegisterationStep4")
	public ModelAndView redirectcontractorRegisterationStep4(){
		return new ModelAndView("user/contractor/contractorRegisterationStep4");
	}
	
	@RequestMapping("register/redirectcontractorRegisterationStep3")
	public ModelAndView redirectcontractorRegisterationStep3(){
		return new ModelAndView("user/contractor/contractorRegisterationStep3");
	}
	
	@RequestMapping("register/redirectcontractorRegisterationStep2")
	public ModelAndView redirectcontractorRegisterationStep2(){
		return new ModelAndView("user/contractor/contractorRegisterationStep2");
	}
	
	@RequestMapping("register/redirectGeneralInformation")
	public ModelAndView redirectGeneralInformation(){
		return new ModelAndView("user/contractor/contractorGeneralInformation");
	}
	
	@RequestMapping("register/redirectRegisterContractor")
	public ModelAndView redirectRegisterContractor(){
		return new ModelAndView("user/contractor/contractorGeneralInformation");
	}
	
	@RequestMapping("register/cookies")
	public ModelAndView cookies(){
		return new ModelAndView("user/home/cookies");
	}
	
	@RequestMapping("register/contactUs")
	public ModelAndView contactUs(){
		return new ModelAndView("user/home/contact-us");
	}
	
	@RequestMapping("register/aboutUs")
	public ModelAndView aboutUs(){
		return new ModelAndView("user/home/about-us");
	}
	
	@RequestMapping("register/redirectLandlordRegisteration")
	public ModelAndView redirectLandlordRegisteration(){
		return new ModelAndView("user/landlord/registerLandlord");
	}
	/*-----------------------------------------------------------------------*/
	@RequestMapping("register/redirectPropertyDeveloperRegistration")
	public ModelAndView redirectPropertyDeveloperRegistration (){
		return new ModelAndView("user/propertyDeveloper/registerPropertyDeveloper");
	}
	/*-----------------------------------------------------------------------*/
	
	@RequestMapping("register/redirectContractorRegisteration")
	public ModelAndView redirectContractorRegisteration(){
		return new ModelAndView("user/contractor/registerContractor");
	}
	
	@RequestMapping("/privacyPolicy")
	public ModelAndView privacyPolicy(){
		return new ModelAndView("user/home/privacyPolicy");
	}
	
	@RequestMapping("/acceptablePolicy")
	public ModelAndView acceptablePolicy(){
		return new ModelAndView("user/home/acceptablePolicy");
	}
	
	@RequestMapping("register/registerLandlordFrame")
	public ModelAndView registerLandlordFrame(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("iframe/registerLandlordFrame");
	}
	
	@RequestMapping("register/registerPropertyDeveloperFrame")
	public ModelAndView registerPropertyDeveloperFrame(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView("iframe/registerPropertyDeveloper");
	}

	@RequestMapping("register/registerContractorFrame")
	public ModelAndView registerContractorFrame(){
		return new ModelAndView("iframe/registerContractorFrame");
	}
	
	@RequestMapping("register/contactUsFrame")
	public ModelAndView registerContactUsFrame(){
		return new ModelAndView("iframe/contactUsFrame");
	}
	
	@RequestMapping("register/redirectCharityRegisteration")
	public ModelAndView redirectCharityRegisteration(){
		return new ModelAndView("iframe/registerCharityFrame");
	}
	

	@RequestMapping("register/redirectforgotPassword")
	public ModelAndView redirectforgotPassword(){
		return new ModelAndView("iframe/forgetpassword");
	}
	
	
	
}

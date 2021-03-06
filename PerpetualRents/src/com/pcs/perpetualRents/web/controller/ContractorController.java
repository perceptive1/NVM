package com.pcs.perpetualRents.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pcs.perpetualRents.business.bean.Address;
import com.pcs.perpetualRents.business.bean.AssignModel;
import com.pcs.perpetualRents.business.bean.City;
import com.pcs.perpetualRents.business.bean.ContactDetails;
import com.pcs.perpetualRents.business.bean.Contractor;
import com.pcs.perpetualRents.business.bean.ContractorRegisterationStep2;
import com.pcs.perpetualRents.business.bean.ContractorRegisterationStep3;
import com.pcs.perpetualRents.business.bean.ContractorRegisterationStep4;
import com.pcs.perpetualRents.business.bean.Conversation;
import com.pcs.perpetualRents.business.bean.Documents;
import com.pcs.perpetualRents.business.bean.EditPassword;
import com.pcs.perpetualRents.business.bean.Expertise;
import com.pcs.perpetualRents.business.bean.File;
import com.pcs.perpetualRents.business.bean.FunctionalityCostMatrix;
import com.pcs.perpetualRents.business.bean.Property;
import com.pcs.perpetualRents.business.bean.TurnOverDetails;
import com.pcs.perpetualRents.business.bean.UserLogin;
import com.pcs.perpetualRents.enm.ContractorFunctionality;
import com.pcs.perpetualRents.enm.ContractorView;
import com.pcs.perpetualRents.enm.DocumentType;
import com.pcs.perpetualRents.enm.ObjectType;
import com.pcs.perpetualRents.enm.PropertyType;
import com.pcs.perpetualRents.enm.Status;
import com.pcs.perpetualRents.enm.WebAction;
import com.pcs.perpetualRents.manager.AssignManager;
import com.pcs.perpetualRents.manager.ContractorManager;
import com.pcs.perpetualRents.manager.ConversationManager;
import com.pcs.perpetualRents.manager.FileManager;
import com.pcs.perpetualRents.manager.PropertyManager;
import com.pcs.perpetualRents.manager.UserLoginManager;
import com.pcs.perpetualRents.web.delegates.AjaxJSONDelegate;

@Controller
public class ContractorController {
	
	@Autowired
	private ContractorManager contractorManager;
	@Autowired
	private PropertyManager propertyManager;
	@Autowired
	private AjaxJSONDelegate JSONDelegate;
	@Autowired
	private UserLoginManager loginManager;
	@Autowired
	private ConversationManager conversationManager;
	@Autowired
	private AssignManager assignManager;
	@Autowired
	private FileManager fileManager;
	/*@Autowired
	private UserLoginManager userLoginManager;*/
	
	private Contractor contractorObj;
	private HttpSession sessionObj;
	private UserLogin loginObj;
	private boolean execStatus;
	private Long contractorId;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	
	@RequestMapping("/contractor")
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response){
		try{
			 JSONObject jObj = new JSONObject();
			 JSONArray jArray = new JSONArray();
			 
			 sessionObj = request.getSession(false);
			 loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
			
			WebAction action = WebAction.get(request.getParameter("action"));
			String  eCommand = request.getParameter("param");
			
			//boolean execStatus = false;
			Contractor contractorObj = null;
			if(action == null)
				action = WebAction.DEFAULT;
			
			switch(action){
			
			case FETCH :
				 if("EXPERTISE-DATA".equalsIgnoreCase(eCommand)){
					contractorObj = contractorManager.getContractorByUserLogin(loginObj);
					if(contractorObj != null){
						List<Expertise> expertiseList = contractorObj.getExpertiseList();
						for (Expertise _expObj : expertiseList) {
							jArray.put(new JSONObject().put("expertise", _expObj.toJSON(true)));
						}
						jObj.put("data", jArray);
						jObj.put("success", true);
					}else{
						jObj.put("success", false);
					}
				}else if("CONTRACTOR-BY-FUNCTIONALITY".equalsIgnoreCase(eCommand)){
					
				}
				JSONDelegate.setResponse(jObj, response);
				break;
				
			case CONTRACTOR_ASSIGNED_JOBS:
				String view1 = request.getParameter("view");
				if("ALL-ACCEPTED-JOBS".equalsIgnoreCase(view1)){
					return new ModelAndView("user/contractor/allAcceptedJobs");
				}else if("ALL-ACCEPTED-JOBS-DETAILS".equalsIgnoreCase(view1)){
					return new ModelAndView("user/contractor/allAcceptedJobsDetail");
				}else if("ALL-REJECTED-JOBS".equalsIgnoreCase(view1)){
					return new ModelAndView("user/contractor/allRejectedJobs");
				}else if("ALL-REJECTED-JOBS-DETAILS".equalsIgnoreCase(view1)){
					return new ModelAndView("user/contractor/allRejectedJobsDetail");
				}else if("ALL-COMPLETE-JOBS".equalsIgnoreCase(view1)){
					return new ModelAndView("user/contractor/allCompleteJobs");
				}else if("ALL-COMPLETE-JOBS-DETAILS".equalsIgnoreCase(view1)){
					return new ModelAndView("user/contractor/allCompleteJobsDetail");
				}
				
				break;
			case EXPERTISE_LIST:
				return new ModelAndView("user/contractor/expertiseList");
			case ADD_EXPERTISE: 
				return new ModelAndView("user/contractor/addExpertise");
			default:
				break;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	@RequestMapping("contractor/update")
	public void updateCompanyName(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		contractorId = (Long) sessionObj.getAttribute("contractorId");
		String isContractorEdit = (String) sessionObj.getAttribute("isContractorEdit");
		if(isContractorEdit == null){
			sessionObj.setAttribute("isContractorEdit", "true");
		}
		
		JSONObject jObj = new JSONObject(); 
		if(contractorId != null){
			String[] fieldsArr = request.getParameterValues("fieldName[]");
			String[] valueArr = request.getParameterValues("value[]");
			String page = request.getParameter("page");
			
			if("Page1".equalsIgnoreCase(page))
				execStatus = contractorManager.update(Contractor.TABLE_NAME, fieldsArr, valueArr, new String[]{Contractor.ID}, new String[]{""+contractorId}, contractorId);
			else if("Page2".equalsIgnoreCase(page))
				execStatus = contractorManager.update(ContractorRegisterationStep2.TABLE_NAME, fieldsArr, valueArr, new String[]{ContractorRegisterationStep2.CONTRACTOR_ID}, new String[]{""+contractorId}, contractorId);
			else if("Page3".equalsIgnoreCase(page))
				execStatus = contractorManager.update(ContractorRegisterationStep3.TABLE_NAME, fieldsArr, valueArr, new String[]{ContractorRegisterationStep3.CONTRACTOR_ID}, new String[]{""+contractorId}, contractorId);
			else if("Page4".equalsIgnoreCase(page))
				execStatus = contractorManager.update(ContractorRegisterationStep4.TABLE_NAME, fieldsArr, valueArr, new String[]{ContractorRegisterationStep4.CONTRACTOR_ID}, new String[]{""+contractorId}, contractorId);
			
			if(execStatus){
				jObj.put("success", true);
			}else{
				jObj.put("success", false);
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/updatecontactInfo")
	public void updatecontactInfo(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		String isContractorEdit = (String) sessionObj.getAttribute("isContractorEdit");
		if(isContractorEdit == null){
			sessionObj.setAttribute("isContractorEdit", "true");
		}
		contractorId = (Long) sessionObj.getAttribute("contractorId");
		JSONObject jObj = new JSONObject(); 
		if(contractorId != null){
			String value = request.getParameter("value");
			String fieldName = request.getParameter("fieldName");
			execStatus = contractorManager.update(ContactDetails.TABLE_NAME, new String[]{fieldName}, new String[]{value}, new String[]{ContactDetails.OBJECT_TYPE, ContactDetails.OBJECT_ID}, new String[]{""+ObjectType.CONTRACTOR.id(),""+contractorId}, contractorId);
			if(execStatus){
				jObj.put("success", true);
			}else{
				jObj.put("success", false);
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/updateAddress")
	public void updateAddress(HttpServletRequest request, HttpServletResponse response) throws JSONException, JsonParseException, JsonMappingException, IOException{
		sessionObj = request.getSession(false);
		String isContractorEdit = (String) sessionObj.getAttribute("isContractorEdit");
		if(isContractorEdit == null){
			sessionObj.setAttribute("isContractorEdit", "true");
		}
		contractorId = (Long) sessionObj.getAttribute("contractorId");
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj"); 
		JSONObject jObj = new JSONObject(); 
		Address addressObj = mapper.readValue(request.getInputStream(), Address.class);
		
		if(contractorId != null && addressObj != null){
			execStatus = contractorManager.updateAddress(addressObj, loginObj.getId(), contractorId);
			if(execStatus){
				jObj.put("success", true);
			}else{
				jObj.put("success", false);
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/updatefunctionalityCostMatrix")
	public void updatefunctionalityCostMatrix(HttpServletRequest request, HttpServletResponse response) throws JSONException, JsonParseException, JsonMappingException, IOException{
		sessionObj = request.getSession(false);
		String isContractorEdit = (String) sessionObj.getAttribute("isContractorEdit");
		if(isContractorEdit == null){
			sessionObj.setAttribute("isContractorEdit", "true");
		}
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		contractorId = (Long) sessionObj.getAttribute("contractorId");
		JSONObject jObj = new JSONObject();
		ContractorRegisterationStep3 conObj = mapper.readValue(request.getInputStream(), ContractorRegisterationStep3.class);
		if(conObj != null && contractorId != null){
			List<FunctionalityCostMatrix> costMatrixList = conObj.getCostSheet();
			if(costMatrixList != null){
				contractorManager.updateFunctionaltyCostMatrix(costMatrixList, contractorId);
				jObj.put("success", true);
			}else{
				jObj.put("success", false);
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/loadContractorInfo")
	public void loadContractorInfo(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		 JSONObject jObj = new JSONObject();
		if(loginObj != null){
			contractorObj = contractorManager.getContractorByUserLogin(loginObj);
			if(contractorObj != null){
				contractorObj = contractorManager.loadContractorById(contractorObj.getId());
				if(contractorObj != null){
					sessionObj.setAttribute("contractorId", contractorObj.getId());
					JSONObject _jObj = new JSONObject();
					JSONArray jArray = null;
						List<TurnOverDetails> turnOverList = contractorObj.getTurnoverDetails();
						if(turnOverList != null){
							jArray = new JSONArray();
							for (TurnOverDetails turnOverDetailsObj : turnOverList) {
								jArray.put(turnOverDetailsObj.toJSON(false));
							}
							_jObj.put("turnover", jArray);
						}
						ContactDetails detailsObj = contractorObj.getContactDetailsObj();
						if(detailsObj != null){
							_jObj.put("contactDetails", detailsObj.toJSON(true));
						}
						UserLogin tLoginObj = contractorObj.getLoginObj();
						if(tLoginObj != null){
							_jObj.put("tLoginObj", tLoginObj.toJSON(true));
						}
						List<Expertise> funList = contractorObj.getExpertiseList();
						if(funList != null){
							String functionalityStr = "";
							String delim = "";
							for (Expertise expertise : funList) {
								String funStr = ContractorFunctionality.get(expertise.getFunctionalityId()).value();
								functionalityStr += delim + funStr;
								delim = ", ";
							}
							_jObj.put("functionalityStr", functionalityStr);
						}
						List<City> cityList = contractorObj.getCityList();
						if(cityList != null){
							String cityStr = "";
							String cityDelim = "";
							for (City cityObj : cityList) {
								cityStr += cityDelim + cityObj.getCity();
								cityDelim = ", ";
							}
							_jObj.put("cityStr", cityStr);
						}
						_jObj.put("contractor",contractorObj.toJSON(true));
						jObj.put("data", _jObj);
						jObj.put("success", true);
				}else{
					jObj.put("success", false);
				}
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/loadContractorStep2Info")
	public void loadContractorStep1Info(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		contractorId = (Long) sessionObj.getAttribute("contractorId");
		JSONObject jObj = new JSONObject();
		if(contractorId != null){
			ContractorRegisterationStep2 conObj = contractorManager.loadContractorStep2Info(contractorId);
			if(conObj != null){
				JSONObject _jObj = null;
				if(conObj != null){
					_jObj = new JSONObject();
					Address officeAddObj = conObj.getOfficeAddObj();
						if(officeAddObj != null){
							_jObj.put("officeAddress", officeAddObj.toJSON(true));
						}
					Address cAddObj = conObj.getCorrespondenceAddObj();
						if(cAddObj != null){
							_jObj.put("correspondenceAddress", cAddObj.toJSON(true));
						}
						_jObj.put("contractor", conObj.toJSON(true));
					jObj.put("success", true);
					jObj.put("data", _jObj);
				}
			
			}
		}else{
			jObj.put("success", false);
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/loadContractorStep3Info")
	public void loadContractorStep3Info(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		contractorId = (Long) sessionObj.getAttribute("contractorId");
		JSONObject jObj = new JSONObject();
		if(contractorId != null){
			ContractorRegisterationStep3 conObj = contractorManager.loadContractorStep3Info(contractorId);
			JSONObject _jObj = null;
			if(conObj != null){
				_jObj = new JSONObject();
				List<FunctionalityCostMatrix> costSheet = conObj.getCostSheet();
				if(costSheet != null){
					JSONArray _jArray = new JSONArray();
					for (FunctionalityCostMatrix functionalityCostMatrix : costSheet) {
						_jArray.put(functionalityCostMatrix.toJSON());
					}
					_jObj.put("costsheet", _jArray);
				}
				
				
				List<Expertise> expertiseList = conObj.getExpertiseList();
				if(expertiseList != null){
					JSONArray _jArray = new JSONArray();
					for (Expertise tExpObj : expertiseList) {
						_jArray.put(ContractorFunctionality.get(tExpObj.getFunctionalityId()).toJSON());
					}
					_jObj.put("expertise", _jArray);
				}
				
					_jObj.put("contractor", conObj.toJSON(true));
				jObj.put("success", true);
				jObj.put("data", _jObj);
			}else{
				jObj.put("success", false);
			}
		}else{
			jObj.put("success", false);
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/loadContractorStep4Info")
	public void loadContractorStep4Info(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		contractorId = (Long) sessionObj.getAttribute("contractorId");
		JSONObject jObj = new JSONObject();
		if(contractorId != null){
			ContractorRegisterationStep4 conObj = contractorManager.loadContractorStep4Info(contractorId);
			JSONObject _jObj = null;
			if(conObj != null){
				_jObj = new JSONObject();
					_jObj.put("contractor", conObj.toJSON(true));
					
				jObj.put("success", true);
				jObj.put("data", _jObj);
			}
		}else{
			jObj.put("success", false);
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/updateWrittenHealthPolicyFiles")
	public ModelAndView updateWrittenHealthPolicyFiles(HttpServletRequest request, HttpServletResponse response)throws Exception{
		sessionObj = request.getSession(false);
		String isContractorEdit = (String) sessionObj.getAttribute("isContractorEdit");
		if(isContractorEdit == null){
			sessionObj.setAttribute("isContractorEdit", "true");
		}
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		JSONObject jObj = new JSONObject();
		if(loginObj != null){
			String writtenHealthPolicy = request.getParameter("writtenHealthPolicy");
				if(writtenHealthPolicy != null){
					MultipartHttpServletRequest multiPartRequest = null;
					try{
						if(request instanceof MultipartHttpServletRequest)
							multiPartRequest = (MultipartHttpServletRequest) request;
					}catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("user/contractor/contractorDetailStep3");
					}
					File fileObj = null;
					Contractor tConObj = contractorManager.getContractorByUserLogin(loginObj);
					if(tConObj != null){
						ContractorRegisterationStep3 regObj = contractorManager.loadContractorStep3Info(tConObj.getId());
						if(regObj == null){
							contractorManager.insertContractorData(ContractorRegisterationStep3.TABLE_NAME, null, null, "contractorId", tConObj.getId());
						}
						regObj = contractorManager.loadContractorStep3Info(tConObj.getId());
						if(regObj != null){
							if(multiPartRequest != null){
								MultipartFile multipartFile = multiPartRequest.getFile("writtenHealthPolicyFile");
								if(multipartFile != null){
									fileObj = new File(multipartFile.getOriginalFilename(), multipartFile.getSize(), ObjectType.CONTRACTOR.id(), tConObj.getId(), multipartFile.getBytes(), multipartFile.getContentType(), DocumentType.CERTIFICATE.id());
									execStatus = contractorManager.updateContractorFiles(regObj.getWrittenHealthPolicyFileId(), fileObj, loginObj.getId());
									if(execStatus && fileObj.getId() != null){
										execStatus = contractorManager.update(ContractorRegisterationStep3.TABLE_NAME, new String[]{ContractorRegisterationStep3.WRITTEN_HEALTH_POLICY, ContractorRegisterationStep3.WRITTEN_HEALTH_POLICY_FILE_ID}, new String[]{writtenHealthPolicy, ""+fileObj.getId()}, new String[]{ContractorRegisterationStep3.CONTRACTOR_ID}, new String[]{""+tConObj.getId()}, contractorId);
										if(execStatus){
											jObj.put("success", true);
										}else{
											jObj.put("success", false);
										}
									}
								}else{
									jObj.put("success", false);
								}
							}
						
						}
					}
				}
			JSONDelegate.setResponse(jObj, response);
		}
		return null;
	}
	
	@RequestMapping("contractor/updateEqualityPolicyFiles")
	public ModelAndView updateEqualityPolicyFiles(HttpServletRequest request, HttpServletResponse response)throws Exception{
		sessionObj = request.getSession(false);
		String isContractorEdit = (String) sessionObj.getAttribute("isContractorEdit");
		if(isContractorEdit == null){
			sessionObj.setAttribute("isContractorEdit", "true");
		}
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		JSONObject jObj = new JSONObject();
		if(loginObj != null){
			String equalityPolicy = request.getParameter("equalityPolicy");
				if(equalityPolicy != null){
					MultipartHttpServletRequest multiPartRequest = null;
					try{
						if(request instanceof MultipartHttpServletRequest)
							multiPartRequest = (MultipartHttpServletRequest) request;
					}catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("user/contractor/contractorDetailStep3");
					}
					File fileObj = null;
					Contractor tConObj = contractorManager.getContractorByUserLogin(loginObj);
					if(tConObj != null){
						ContractorRegisterationStep3 regObj = contractorManager.loadContractorStep3Info(tConObj.getId());
						if(regObj != null){
							if(multiPartRequest != null){
								MultipartFile multipartFile = multiPartRequest.getFile("equalityPolicyFile");
								if(multipartFile != null){
									fileObj = new File(multipartFile.getOriginalFilename(), multipartFile.getSize(), ObjectType.CONTRACTOR.id(), tConObj.getId(), multipartFile.getBytes(), multipartFile.getContentType(), DocumentType.CERTIFICATE.id());
									execStatus = contractorManager.updateContractorFiles(regObj.getWrittenHealthPolicyFileId(), fileObj, loginObj.getId());
									if(execStatus && fileObj.getId() != null){
										execStatus = contractorManager.update(ContractorRegisterationStep3.TABLE_NAME, new String[]{ContractorRegisterationStep3.EQUALITY_POLICY, ContractorRegisterationStep3.EQUALITY_POLICY_FILE_ID}, new String[]{equalityPolicy, ""+fileObj.getId()}, new String[]{ContractorRegisterationStep3.CONTRACTOR_ID}, new String[]{""+tConObj.getId()}, contractorId);
										if(execStatus){
											jObj.put("success", true);
										}else{
											jObj.put("success", false);
										}
									}
								}else{
									jObj.put("success", false);
								}
							}
						
						}
					}
				}
			JSONDelegate.setResponse(jObj, response);
		}
		return null;
	}
	
	@RequestMapping("contractor/loadContractorsByFunctionality")
	public void loadContractorsByFunctionality(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		Long propertyId = (Long) sessionObj.getAttribute("propertyId");
		String  functionality = request.getParameter("functionality");
		JSONObject jObj = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		ContractorFunctionality funObj = null;
		if("electricalInstallation".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.ELECTRICAL_INSTALLATION_TESTING;
		}else if("electricalTest".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.ELECTRICAL_TEST;
		}else if("gasCertificate".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.GAS_APPLIANCES;
		}else if("energyPerformance".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.ENERGY_PERFORMANCE;
		}else if("centralHeatingSystem".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.CENTRAL_HEATING_SYSTEM;
		}else if("buildingInsaurance".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.GENERAL_BUILDING_WORKS;
		}else if("asbestosSurvey".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.ASBESTOS_SURVEY;
		}else if("periodicInspectionOfWaterSupply".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.PERIODIC_INSPECTION;
		}else if("inventoryForProperty".equalsIgnoreCase(functionality)){
			funObj = ContractorFunctionality.INVENTORY_SERVICES;
		
		}
		
		
		if(propertyId != null){
			Property tPropertyObj = propertyManager.loadPropertyById(propertyId);
			if(tPropertyObj != null && funObj != null){
				List<Contractor> contractorList = contractorManager.loadContractorByExpertise(funObj, propertyId);
				if(contractorList != null && !contractorList.isEmpty()){
					JSONObject _jobj;
					for (Contractor conObj : contractorList) {
						_jobj = new JSONObject();
						jArray.put(_jobj.put("contractor", conObj.toJSON(true)));
						Expertise expObj = conObj.getExpertiseObj();
						if(expObj != null)
							_jobj.put("expObj", expObj.toJSON(true));
						}
					jObj.put("success", true);
					jObj.put("data", jArray);
				}else{
					jObj.put("success", false);
				}
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/loadProfile")
	public void loadProfile(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		sessionObj = request.getSession(false);
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		JSONObject jObj = new JSONObject();
		if(loginObj != null){
			contractorObj = contractorManager.getContractorByUserLogin(loginObj);
			JSONObject _jObj = new JSONObject();
			if(contractorObj != null){
				_jObj.put("contractor", contractorObj.toJSON(true));
				if(contractorObj.getOfficeAddressObj() != null)
					_jObj.put("address", contractorObj.getOfficeAddressObj().toJSON(true));
				
				if(contractorObj.getLoginObj() != null)
					_jObj.put("login", contractorObj.getLoginObj().toJSON(true));
				if(contractorObj.getContactDetailsObj() != null)
					_jObj.put("contactDetails", contractorObj.getContactDetailsObj().toJSON(true));
				
				List<Expertise> expList = contractorObj.getExpertiseList();
				if(expList != null && !expList.isEmpty()){
					String functionalityString = "";
					String cityServicedString = "";
					String fDelim = "";
					
					for(Expertise expObj : expList) {
						cityServicedString = "";
						functionalityString += fDelim + ContractorFunctionality.get(expObj.getFunctionalityId()).value();
						fDelim  = ", ";
						List<City> cityList = new ArrayList<City>(); //expObj.getCities();
						if(cityList != null && !cityList.isEmpty()){
							String cDelim = "";
							for (City city : cityList) {
								cityServicedString += cDelim + city.getCity();
								cDelim = ", ";
							}
						}
					}
					for(Expertise expObj : expList) {
						expObj.setFunctionality(functionalityString);
						expObj.setCityServiced(cityServicedString);
						_jObj.put("expertise", expObj.toJSON(false));
					}
				}
				
				jObj.put("data", _jObj);
				jObj.put("success", true);
			}else{
				jObj.put("success", false);
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/redirectJobDetails")
	public ModelAndView redirectJobDetails(HttpServletRequest request, HttpServletResponse response){
			ModelAndView mv = new ModelAndView("user/contractor/jobDetails");
				mv.addObject("assignModelId", request.getParameter("assignModelId"));
			return mv;
	}
	
	@RequestMapping("contractor/loadJobDetails")
	public void loadJobDetails(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject jObj = new JSONObject();
		JSONArray jArray = new JSONArray();
		String assignId = request.getParameter("assignModelId");
		if(assignId != null){
			AssignModel tAssignModelObj = assignManager.loadAssignModelById(Long.valueOf(assignId));
			if(tAssignModelObj != null){
				Property propertyObj = propertyManager.loadPropertyById(tAssignModelObj.getPropertyId());
				if(propertyObj != null){
					JSONObject _jObj = new JSONObject();
						_jObj.put("property", propertyObj.toJSON(true));
						
					Address addressObj = propertyObj.getAddressObj();
						if(addressObj != null)
							_jObj.put("address", addressObj.toJSON(false));
						
						_jObj.put("assignModel", tAssignModelObj.toJSON(true));
					
				 List<Status> statusList = 	contractorManager.loadStatus(tAssignModelObj.getId());
				 if(statusList != null && !statusList.isEmpty()){
					 for (Status status : statusList) {
						jArray.put(status.toJSON());
					 	}
					 _jObj.put("status", jArray);
				}
					jObj.put("success", true);
					jObj.put("data", _jObj);
				}
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/removeFile")
	public void removeFile(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String fileId = request.getParameter("fileId");
		JSONObject jObj = new JSONObject();
		if(fileId != null){
			fileManager.deleteFileById(Long.valueOf(fileId));
			jObj.put("success", true);
		}else{
			jObj.put("success", false);
		}
	
	}
	
	@RequestMapping("contractor/loadAllDocuments")
	public void loadAllDocuments(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject jObj = new JSONObject();
		JSONArray jArray = new JSONArray();
		String assignModelIdStr = request.getParameter("assignModelId");
		if(assignModelIdStr != null){
				List<Documents> documents = contractorManager.loadDocumentsByContractorsJob(Long.valueOf(assignModelIdStr));
				JSONObject _jObj  = null;
				if(documents != null && !documents.isEmpty()){
					for (Documents _docObj : documents) {
						_jObj = new JSONObject();
						
							_jObj.put("documents", _docObj.toJSON(true));
						File fileObj = _docObj.getFileObj();
							if(fileObj != null){
								_jObj.put("file",fileObj.toJSON(true));
							}
						jArray.put(_jObj);
					}
					jObj.put("success", true);
					jObj.put("data", jArray);
				}else{
					jObj.put("success", false);
				}
		}
	JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/updateStatus")
	public void updateStatus(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String status = request.getParameter("status");
		String remarks = request.getParameter("remarks");
		String assignModelIdStr = request.getParameter("assignModelId");
		JSONObject jObj = new JSONObject();
		JSONArray jArray = new JSONArray();
		if(loginObj != null){
			Contractor tConObj = contractorManager.getContractorByUserLogin(loginObj);
			if(status != null && tConObj != null && assignModelIdStr != null){
					Status tStatus = Status.get(status);
					if(tStatus.equals(Status.ACCEPTED) || tStatus.equals(Status.ON_WORK) || tStatus.equals(Status.PARTIAL_COMPLETE)){
						execStatus = contractorManager.updateAssignModelStatus(Long.valueOf(assignModelIdStr), tStatus.id(), loginObj.getId(), remarks);
						if(execStatus){
							List<Status> statusList = contractorManager.loadStatus(Long.valueOf(assignModelIdStr));
							if(statusList != null && !statusList.isEmpty()){
								for (Status statusObj : statusList) {
									jArray.put(statusObj.toJSON());
								}
								jObj.put("data", jArray);
								jObj.put("success", true);	
							}
						}else{
							jObj.put("success", false);
						}	
					}else{
						execStatus = contractorManager.updateAssignModelStatus(Long.valueOf(assignModelIdStr), tStatus.id(), loginObj.getId(), remarks);
						if(execStatus){
							jObj.put("success", true);
						}else{
							jObj.put("success", false);
						}
					}
			}
			JSONDelegate.setResponse(jObj, response);
		}
	}
	
	@RequestMapping("contractor/loadConversationIdByAssignModel")
	public void loadConversationIdByAssignModel(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String assignModelIdStr = request.getParameter("assignModelId");
		JSONObject jObj = new JSONObject();
		if(assignModelIdStr != null){
			AssignModel tAssignModelObj =  assignManager.loadAssignModelById(Long.valueOf(assignModelIdStr));
			if(tAssignModelObj != null){
				Conversation conversationObj = conversationManager.loadConversationByContractorPropertyAndFunctionality(tAssignModelObj.getAssignToId(), tAssignModelObj.getPropertyId(), PropertyType.CURRENT_DEVELOPMENT.id(), tAssignModelObj.getFunctionalityId());
				if(conversationObj != null){
					jObj.put("success", true);
					jObj.put("data", conversationObj.getId());
				}else{
					jObj.put("success", false);
				}
			}
		}
	JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/loadAllJobs")
	public void loadAllJobs(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		String eCommand = request.getParameter("param");
		ContractorView view = ContractorView.get(eCommand);
		
		sessionObj = request.getSession(false);
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		JSONObject jObj = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		switch (view) {
		
		case ALL_ASSIGNED_JOBS:
			contractorObj = contractorManager.getContractorByUserLogin(loginObj);
			List<Property> assignJobsList = contractorManager.loadJobsByStatus(contractorObj.getId(), Status.ASSIGNED.id(),
					Status.ACCEPTED.id(), Status.REJECTED.id(), Status.ON_WORK.id(), Status.PARTIAL_COMPLETE.id(), Status.COMPLETE.id());
			if(assignJobsList != null && !assignJobsList.isEmpty()){
				for (Property tPropObj : assignJobsList) {
					Property property =  propertyManager.loadPropertyById(tPropObj.getId());
					JSONObject _jObj = new JSONObject();
						_jObj.put("property", property.toJSON(true));
						
						Address addressObj = property.getAddressObj();
						if(addressObj != null)
							_jObj.put("address", addressObj.toJSON(false));
						
						AssignModel assignModelObj = tPropObj.getAssignModel();
						if(assignModelObj != null)
							_jObj.put("assignModel", assignModelObj.toJSON(true));
						
						List<File> fileList = propertyManager.loadPropertyImage(ObjectType.PROPERTY_MARKETING.id(), property.getId());
						JSONArray _jArr = new JSONArray();
						if(fileList != null){
							for (File tFile: fileList) {
								_jArr.put(tFile.getFilePath());
							}
							_jObj.put("files", _jArr);
						}
						jArray.put(_jObj);
				}
				jObj.put("success", true);
				jObj.put("data", jArray);
			}
		
			break;

		case ALL_ACCEPTED_JOBS:
			contractorObj = contractorManager.getContractorByUserLogin(loginObj);
			List<Property> acceptedJobsList = contractorManager.loadJobsByStatus(contractorObj.getId(), Status.ACCEPTED.id(), Status.ON_WORK.id(), Status.PARTIAL_COMPLETE.id(), Status.COMPLETE.id());
			if(acceptedJobsList != null && !acceptedJobsList.isEmpty()){
				for (Property tPropObj : acceptedJobsList) {
					Property property =  propertyManager.loadPropertyById(tPropObj.getId());
					JSONObject _jObj = new JSONObject();
						_jObj.put("property", property.toJSON(true));
						
						Address addressObj = property.getAddressObj();
						if(addressObj != null)
							_jObj.put("address", addressObj.toJSON(false));
						
						jArray.put(_jObj);
				}
				jObj.put("success", true);
				jObj.put("data", jArray);
			}
			break;
			
		case ALL_REJECTED_JOBS:
			contractorObj = contractorManager.getContractorByUserLogin(loginObj);
			List<Property> rejectedJobsList = contractorManager.loadJobsByStatus(contractorObj.getId(), Status.REJECTED.id());
			if(rejectedJobsList != null && !rejectedJobsList.isEmpty()){
				for (Property tPropObj : rejectedJobsList) {
					Property property =  propertyManager.loadPropertyById(tPropObj.getId());
					JSONObject _jObj = new JSONObject();
						_jObj.put("property", property.toJSON(true));
						
						Address addressObj = property.getAddressObj();
						if(addressObj != null)
							_jObj.put("address", addressObj.toJSON(false));
						
						jArray.put(_jObj);
				}
				jObj.put("success", true);
				jObj.put("data", jArray);
			}
			break;
			
		case ALL_COMPLETE_JOBS:
			contractorObj = contractorManager.getContractorByUserLogin(loginObj);
			List<Property> completeJobsList = contractorManager.loadJobsByStatus(contractorObj.getId(), Status.COMPLETE.id());
			if(completeJobsList != null && !completeJobsList.isEmpty()){
				for (Property tPropObj : completeJobsList) {
					Property property =  propertyManager.loadPropertyById(tPropObj.getId());
					JSONObject _jObj = new JSONObject();
						_jObj.put("property", property.toJSON(true));
						
						Address addressObj = property.getAddressObj();
						if(addressObj != null)
							_jObj.put("address", addressObj.toJSON(false));
						
						jArray.put(_jObj);
				}
				jObj.put("success", true);
				jObj.put("data", jArray);
			}
			break;
		default:
			break;
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/updatePassword")
	public void updatePassword(HttpServletRequest request, HttpServletResponse response)throws Exception{
		sessionObj = request.getSession(false);
		JSONObject jObj = new JSONObject();
		EditPassword pwdObj = mapper.readValue(request.getInputStream(), EditPassword.class);
		loginObj = loginManager.findLoginUserByName(loginObj.getUsername());
		if(pwdObj != null){
			pwdObj.setId(loginObj.getId());
			if(pwdObj.getCurrentPassword() == null || pwdObj.getCurrentPassword() == ""){
				jObj.put("success", false);
				jObj.put("message", "Current Password Cannot be blank.");
			}else if(pwdObj.getCurrentPassword() == null || !loginObj.getPassword().equalsIgnoreCase(pwdObj.getCurrentPassword())){
				jObj.put("success", false);
				jObj.put("message", "Invalid Current Password");
			}else if(pwdObj.getNewPassword() == null || pwdObj.getNewPassword() == ""){
				jObj.put("success", false);
				jObj.put("message", "New Password cannot be blank.");
			}else if(pwdObj.getConfirmPassword() == null || pwdObj.getConfirmPassword() == ""){
				jObj.put("success", false);
				jObj.put("message", "Confirm Password Cannot be blank.");
			}else if(!pwdObj.getNewPassword().equalsIgnoreCase(pwdObj.getConfirmPassword())){
				jObj.put("success", false);
				jObj.put("message", "Confirm Password doesnot match.");
			}
			else{
				execStatus = loginManager.updatePassword(pwdObj);
				
				if(execStatus){
					jObj.put("message", "Password has been successfully updated");
					jObj.put("success", true);
				}else{
					jObj.put("message", "Failed to update password");
					jObj.put("success", false);
				}
			}
		}else{
			jObj.put("success", false);
			jObj.put("message", "Current Password cannot be blank.");
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/updateProfile")
	public void updateProfile(HttpServletRequest request, HttpServletResponse response)throws Exception{
		sessionObj = request.getSession(false);
		loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		JSONObject jObj = new JSONObject();
		if(loginObj != null){
			contractorObj = contractorManager.getContractorByUserLogin(loginObj);
			if(contractorObj != null){
				Contractor tContractor = mapper.readValue(request.getInputStream(), Contractor.class);
				if(tContractor != null){
					tContractor.setId(contractorObj.getId());
					execStatus = contractorManager.updateContractorDetails(tContractor, loginObj.getId());
					if(execStatus){
						jObj.put("success", true);
						jObj.put("message", "Your profile has been successfully updated.");
					}else{
						jObj.put("success", false);
						jObj.put("message", "Update failed due to some internal errors.");
					}
				}
			}
		}
		JSONDelegate.setResponse(jObj, response);
	}
	
	@RequestMapping("contractor/redirectAllAssignedJobs")
	public ModelAndView redirectAllAssignedJobs(){
		ModelAndView mv = new ModelAndView("user/contractor/allAssignedJobs");
			mv.addObject("view", "1");
		return mv;
	}
	
	@RequestMapping("contractor/redirectChangePassword")
	public ModelAndView redirectChangePassword(){
		return new ModelAndView("user/contractor/changePassword");
	}
	
	@RequestMapping("contractor/redirectEditContractor")
	public ModelAndView redirectEditContractor(){
		return new ModelAndView("user/contractor/editContractor");
	}
	
	@RequestMapping("contractor/redirectProfileView")
	public ModelAndView redirectProfileView(){
		return new ModelAndView("user/contractor/contractorDetail");
	}
	
	@RequestMapping("contractor/redirectContractorProfileStep2")
	public ModelAndView redirectContractorProfileStep2(){
		return new ModelAndView("user/contractor/contractorDetailStep2");
	}
	
	@RequestMapping("contractor/redirectContractorProfileStep3")
	public ModelAndView redirectContractorProfileStep3(){
		return new ModelAndView("user/contractor/contractorDetailStep3");
	}
	
	@RequestMapping("contractor/redirectContractorProfileStep4")
	public ModelAndView redirectContractorProfileStep4(){
		return new ModelAndView("user/contractor/contractorDetailStep4");
	}
	
	@RequestMapping("contractor/redirectContractorProfileStep4Submit")
	public ModelAndView redirectContractorProfileStep4Submit(){
		return new ModelAndView("user/contractor/contractorUpdateSuccess");
	}
	
	
	
	@RequestMapping("contractor/uploadCertificates")
	public ModelAndView uploadCertificates(HttpServletRequest request, HttpServletResponse response)throws Exception{
		JSONObject jObj = new JSONObject();
		 sessionObj = request.getSession(false);
		 loginObj = (UserLogin) sessionObj.getAttribute("loginObj");
		String assignModelId = request.getParameter("assignModelId");
		if(assignModelId != null){
		AssignModel tAssignObj = assignManager.loadAssignModelById(Long.valueOf(assignModelId));
		if(tAssignObj != null){

			MultipartHttpServletRequest multiPartRequest = null;
			try{
				
				if(request instanceof MultipartHttpServletRequest)
					multiPartRequest = (MultipartHttpServletRequest) request;
			}catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("user/contractor/contractorManager");
			}
			File fileObj = null;
			Documents documentObj = null;
			Contractor tConObj = contractorManager.getContractorByUserLogin(loginObj);
			if(tConObj != null){
				if(multiPartRequest != null){
					List<MultipartFile> files = multiPartRequest.getFiles("uploadCertificate");
					if(files != null && !files.isEmpty()){
						for (MultipartFile multipartFile : files) {
							if(multipartFile != null){
								fileObj = new File(multipartFile.getOriginalFilename(), multipartFile.getSize(), ObjectType.CONTRACTOR.id(), tConObj.getId(), multipartFile.getBytes(), multipartFile.getContentType(), DocumentType.CERTIFICATE.id());
								documentObj = new Documents(tConObj.getId(), tAssignObj.getPropertyId(), DocumentType.CERTIFICATE.id(), fileObj, Status.NEWLY_UPLOADED.id());
								documentObj.setFunctionalityId(tAssignObj.getFunctionalityId());
								contractorManager.uploadFiles(documentObj, loginObj.getId());
							}
						}
						jObj.put("success", true);
					}else{
						jObj.put("success", false);
					}
				}
			}
		}
		}
		JSONDelegate.setResponse(jObj, response);
		return null;
	}
	
	@RequestMapping("contractor/uploadInvoice")
	public ModelAndView uploadInvoice(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String assignModelId = request.getParameter("assignModelId");
		JSONObject jObj = new JSONObject();
		if(assignModelId != null){
		AssignModel tAssignObj = assignManager.loadAssignModelById(Long.valueOf(assignModelId));
		if(tAssignObj != null){
			MultipartHttpServletRequest multiPartRequest = null;
			try{
				if(request instanceof MultipartHttpServletRequest)
					multiPartRequest = (MultipartHttpServletRequest) request;
			}catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("user/contractor/contractorManager");
			}
			File fileObj = null;
			Documents documentObj = null;
			Contractor tConObj = contractorManager.getContractorByUserLogin(loginObj);
			if(tConObj != null){
				if(multiPartRequest != null){
					List<MultipartFile> files = multiPartRequest.getFiles("uploadInvoice");
					if(files != null && !files.isEmpty()){
						for (MultipartFile multipartFile : files) {
							if(multipartFile != null){
								fileObj = new File(multipartFile.getOriginalFilename(), multipartFile.getSize(), ObjectType.CONTRACTOR.id(), tConObj.getId(), multipartFile.getBytes(), multipartFile.getContentType(), DocumentType.INVOICE.id());
								documentObj = new Documents(tConObj.getId(), tAssignObj.getPropertyId(), DocumentType.INVOICE.id(), fileObj, Status.NEWLY_UPLOADED.id());
								documentObj.setFunctionalityId(tAssignObj.getFunctionalityId());
								contractorManager.uploadFiles(documentObj, loginObj.getId());
							}
						}
						jObj.put("success", true);
					}else{
						jObj.put("success", false);
					}
				}
			}
		}
		}
		JSONDelegate.setResponse(jObj, response);
		return null;
	}
	
	@RequestMapping("contractor/redirectContractorMails")
	public ModelAndView redirectContractorMails(){
		return new ModelAndView("user/contractor/contractorMails");
	}
	
	
}

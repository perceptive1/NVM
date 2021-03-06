package com.pcs.perpetualRents.manager.impl;

import java.util.List;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring3.SpringTemplateEngine;

import com.pcs.perpetualRents.business.bean.Address;
import com.pcs.perpetualRents.business.bean.Admin;
import com.pcs.perpetualRents.business.bean.AdminUploadDocuments;
import com.pcs.perpetualRents.business.bean.AssignModel;
import com.pcs.perpetualRents.business.bean.Authorities;
import com.pcs.perpetualRents.business.bean.City;
import com.pcs.perpetualRents.business.bean.ContactDetails;
import com.pcs.perpetualRents.business.bean.Documents;
import com.pcs.perpetualRents.business.bean.File;
import com.pcs.perpetualRents.business.bean.PerpetualCity;
import com.pcs.perpetualRents.business.bean.UserLogin;
import com.pcs.perpetualRents.common.bean.ApplicationSettings;
import com.pcs.perpetualRents.dao.AddressDAO;
import com.pcs.perpetualRents.dao.AdminDocumentDAO;
import com.pcs.perpetualRents.dao.AssignDAO;
import com.pcs.perpetualRents.dao.CityDAO;
import com.pcs.perpetualRents.dao.ContactDetailsDAO;
import com.pcs.perpetualRents.dao.DocumentsDAO;
import com.pcs.perpetualRents.dao.IdentityGeneratorDAO;
import com.pcs.perpetualRents.dao.UserAdminDAO;
import com.pcs.perpetualRents.dao.UserLoginDAO;
import com.pcs.perpetualRents.enm.DocumentType;
import com.pcs.perpetualRents.enm.IdentityGeneratorObjective;
import com.pcs.perpetualRents.enm.MailEventType;
import com.pcs.perpetualRents.enm.ObjectType;
import com.pcs.perpetualRents.enm.UserType;
import com.pcs.perpetualRents.manager.CommonManager;
import com.pcs.perpetualRents.manager.FileManager;
import com.pcs.perpetualRents.manager.MailEventManager;
import com.pcs.perpetualRents.manager.UserAdminManager;
import com.pcs.perpetualRents.util.mail.SubAdminMailUtility;
import com.pcs.perpetualRents.util.mail.SuperAdminMailUtility;

@Service
@Transactional
public class UserAdminManagerImpl implements UserAdminManager{
	
	@Autowired
	private UserAdminDAO userAdminDAO;
	@Autowired
	private UserLoginDAO userLoginDAO;
	@Autowired
	private AddressDAO addressDAO;
	@Autowired
	private ContactDetailsDAO contactDetailsDAO;
	@Autowired
	private IdentityGeneratorDAO gereratorDAO;
	@Autowired
	private AssignDAO assignDAO;
	@Autowired
	private CityDAO cityDAO;
	@Autowired
	private DocumentsDAO documentsDAO;
	@Autowired
	private ApplicationSettings applicationSettings;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SpringTemplateEngine templateEngine;
	@Autowired
	private MailEventManager mailEventManager;
	@Autowired
	private CommonManager commonManager;
	@Autowired
	private FileManager fileManager;
	@Autowired
	private AdminDocumentDAO adminDocumentDAO;

	@Override
	public boolean createUserAdmin(Admin userObj, Long createdUserId) {
		try{
		if(userObj == null)
			return false;
		UserLogin loginObj = userObj.getLoginObj();
		Address addressObj = userObj.getAddressObj();
		if(loginObj == null || addressObj == null){
			System.out.println("Insufficient details");
			return false;
		}
		loginObj.setUserTypeId(UserType.ADMIN.id());
		loginObj.createPassword();
		String uniqueReference = gereratorDAO.getUniqueReference(IdentityGeneratorObjective.ADMIN.value());
		userObj.setUniqueReference(uniqueReference);
		Long loginId = userLoginDAO.createLoginUser(loginObj, createdUserId);
		userLoginDAO.createAuthority(new Authorities(loginObj.getUsername(), "ALL"));
		if(loginId != null){
			userObj.setLoginUserId(loginId);
			Long adminId = userAdminDAO.createAdmin(userObj, createdUserId);
			if(adminId != null){
				List<String> cityAllotedIdList = userObj.getCityAllotedList();
				if(cityAllotedIdList!=null && !cityAllotedIdList.isEmpty()){
					String cityAllotedStr = "";
					String delim = "";
					for (String _cityId : cityAllotedIdList) {
						PerpetualCity tCityObj = cityDAO.loadPerpetualCityById(Long.valueOf(_cityId));
						cityDAO.createCity(new City(tCityObj.getCity(), tCityObj.getId(), ObjectType.ADMIN.id(), adminId));
						cityAllotedStr += delim + tCityObj.getCity();
						delim = ", ";
					}
					userObj.setCityAlloted(cityAllotedStr);
				}
				
				addressObj.setObjectType(ObjectType.ADMIN.id());
				addressObj.setObjectId(adminId);
				Long addressId = addressDAO.createAddress(addressObj, createdUserId);
				if(addressId != null){
					ContactDetails detailsObj = userObj.getContactDetailsObj();
					detailsObj.setObjectType(ObjectType.ADMIN.id());
					detailsObj.setObjectId(adminId);
					contactDetailsDAO.createContactDetails(detailsObj, createdUserId);
					SwingUtilities.invokeLater(new SubAdminMailUtility(userObj, applicationSettings, mailSender, MailEventType.REGISTER, mailEventManager, templateEngine, commonManager));
				}
			}
		}
		return true;
		}catch(DuplicateKeyException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Admin> loadAll(Integer start, Integer limit) {
		List<Admin> adminList = userAdminDAO.loadAll(start, limit);
		if(adminList != null && !adminList.isEmpty()){
			for (Admin adminObj : adminList) {
				List<Address> addressList = addressDAO.loadAddressById(ObjectType.ADMIN.id(), adminObj.getId());
				if(addressList != null && !addressList.isEmpty()){
					adminObj.setAddressObj(addressList.get(0));
				}
				List<City> cityList = cityDAO.loadCityByObjectId(ObjectType.ADMIN.id(), adminObj.getId());
				if(cityList != null && !cityList.isEmpty()){
					String cityString = "";
					String delim = "";
					for (City city : cityList) {
						cityString += delim + city.getCity();
						delim = ", ";
					}
					adminObj.setCityAlloted(cityString);
				}
				List<ContactDetails> detailsList = contactDetailsDAO.loadById(ObjectType.ADMIN.id(), adminObj.getId());
				if(detailsList != null && !detailsList.isEmpty()){
					adminObj.setContactDetailsObj(detailsList.get(0));
				}
			}
		}
		return adminList;
	}
	
	@Override
	public Admin loadAdminById(Long id) {
		Admin adminObj = userAdminDAO.loadAdminById(id, null);
		if(adminObj != null){
			List<Address> addressList = addressDAO.loadAddressById(ObjectType.ADMIN.id(), adminObj.getId());
			if(addressList != null && !addressList.isEmpty()){
				adminObj.setAddressObj(addressList.get(0));
			}
			List<City> cityList = cityDAO.loadCityByObjectId(ObjectType.ADMIN.id(), adminObj.getId());
			if(cityList != null && !cityList.isEmpty()){
				String cityString = "";
				String cityIdStr = "";
				String delim = "";
				for (City city : cityList) {
					cityString += delim + city.getCity();
					cityIdStr += delim + city.getCityId();
					delim = ", ";
				}
				adminObj.setCityAlloted(cityString);
				adminObj.setCityAllotedId(cityIdStr);
			}
			List<ContactDetails> detailsList = contactDetailsDAO.loadById(ObjectType.ADMIN.id(), adminObj.getId());
			if(detailsList != null && !detailsList.isEmpty()){
				adminObj.setContactDetailsObj(detailsList.get(0));
			}
			
		}
		return adminObj;
	}
	
	@Override
	public boolean updateAdminUser(Admin adminObj, UserLogin loginObj) {
		try{
				if(adminObj == null){
					return false;
				}
				userAdminDAO.updateUser(adminObj, loginObj.getId());
				cityDAO.deleteCityByObjectType(ObjectType.ADMIN.id(), adminObj.getId());
				if(adminObj.getCityAllotedList() != null){
					String cityAllotedStr = "";
					String delim = "";
				for (String cityStr : adminObj.getCityAllotedList()) {
					PerpetualCity pcityObj = cityDAO.loadPerpetualCityById(Long.valueOf(cityStr));
					cityDAO.createCity(new City(pcityObj.getCity(), pcityObj.getId(), ObjectType.ADMIN.id(), adminObj.getId()));
					cityAllotedStr += delim + pcityObj.getCity();
					delim = ", ";
					adminObj.setCityAlloted(cityAllotedStr);
				}
				}
					Address addressObj = adminObj.getAddressObj();
					
					if(addressObj == null){
						System.out.println("Address not complete");
						return false;
					}
						addressObj.setObjectId(adminObj.getId());
						addressObj.setObjectType(ObjectType.ADMIN.id());
						addressDAO.updateAddress(addressObj, loginObj.getId());
						ContactDetails detailsObj = adminObj.getContactDetailsObj();
						if(detailsObj != null){
							detailsObj.setObjectId(adminObj.getId());
							detailsObj.setObjectType(ObjectType.ADMIN.id());
							contactDetailsDAO.updateContactDetails(detailsObj, loginObj.getId());
						}
						Admin tAdminObj = loadAdminById(adminObj.getId());
						if(tAdminObj != null){
							SwingUtilities.invokeLater(new SubAdminMailUtility(tAdminObj, applicationSettings, mailSender, MailEventType.PROFILE_EDIT, mailEventManager, templateEngine, commonManager));
						}
						return true;
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean updateAdmin(Admin adminObj, UserLogin loginObj) {
		try{
			if(loginObj.getUserTypeId() == UserType.SUPER_ADMIN.id()){
				if(adminObj == null){
					return false;
				}
				userAdminDAO.updateUser(adminObj, loginObj.getId());
					Address addressObj = adminObj.getAddressObj();
					
					if(addressObj == null)
						return false;
					
						addressObj.setObjectId(adminObj.getId());
						addressObj.setObjectType(ObjectType.SUPER_ADMIN.id());
						addressDAO.updateAddress(addressObj, loginObj.getId());
						ContactDetails detailsObj = adminObj.getContactDetailsObj();
						if(detailsObj != null){
							detailsObj.setObjectId(adminObj.getId());
							detailsObj.setObjectType(ObjectType.SUPER_ADMIN.id());
							contactDetailsDAO.updateContactDetails(detailsObj, loginObj.getId());
						}
						Admin tAdminObj = loadAdminById(adminObj.getId());
						if(tAdminObj != null){
							SwingUtilities.invokeLater(new SuperAdminMailUtility( applicationSettings, mailSender, MailEventType.PROFILE_EDIT, mailEventManager, templateEngine, commonManager));
						}
						return true;
			
			}else{
				if(adminObj == null){
					return false;
				}
				userAdminDAO.updateUser(adminObj, loginObj.getId());
				cityDAO.deleteCityByObjectType(ObjectType.ADMIN.id(), adminObj.getId());
				if(adminObj.getCityAllotedList() != null){
					String cityAllotedStr = "";
					String delim = "";
				for (String cityStr : adminObj.getCityAllotedList()) {
					PerpetualCity pcityObj = cityDAO.loadPerpetualCityById(Long.valueOf(cityStr));
					cityDAO.createCity(new City(pcityObj.getCity(), pcityObj.getId(), ObjectType.ADMIN.id(), adminObj.getId()));
					cityAllotedStr += delim + pcityObj.getCity();
					delim = ", ";
					adminObj.setCityAlloted(cityAllotedStr);
				}
				}
					Address addressObj = adminObj.getAddressObj();
					
					if(addressObj == null)
						return false;
					
					addressObj.setObjectId(adminObj.getId());
					addressObj.setObjectType(ObjectType.ADMIN.id());
					addressDAO.updateAddress(addressObj, loginObj.getId());
					ContactDetails detailsObj = adminObj.getContactDetailsObj();
					if(detailsObj != null){
						detailsObj.setObjectId(adminObj.getId());
						detailsObj.setObjectType(ObjectType.ADMIN.id());
						contactDetailsDAO.updateContactDetails(detailsObj, loginObj.getId());
					}
					SwingUtilities.invokeLater(new SubAdminMailUtility(adminObj, applicationSettings, mailSender, MailEventType.PROFILE_EDIT, mailEventManager, templateEngine, commonManager));
					return true;
			
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	

	@Override
	public List<Admin> loadUsersByAdminId(Long loginId) {
		List<Admin> adminList = userAdminDAO.loadUsersByAdminId(loginId);
		if(adminList != null && !adminList.isEmpty()){
			for (Admin adminObj : adminList) {
				List<Address> addressList = addressDAO.loadAddressById(ObjectType.ADMIN.id(), adminObj.getId());
				if(addressList != null && !addressList.isEmpty()){
					adminObj.setAddressObj(addressList.get(0));
				}
				List<City> cityList = cityDAO.loadCityByObjectId(ObjectType.ADMIN.id(), adminObj.getId());
				if(cityList != null && !cityList.isEmpty()){
					String cityString = "";
					String delim = "";
					for (City city : cityList) {
						cityString += delim + city.getCity();
						delim = ", ";
					}
					adminObj.setCityAlloted(cityString);
				}
				List<ContactDetails> detailsList = contactDetailsDAO.loadById(ObjectType.ADMIN.id(), adminObj.getId());
				if(detailsList != null && !detailsList.isEmpty()){
					adminObj.setContactDetailsObj(detailsList.get(0));
				}
			}
		}
		return adminList;
	}
	
	@Override
	public Admin loadUserByLoginObj(UserLogin loginObj) {
		if(loginObj != null){
			Admin adminObj = userAdminDAO.loadAdminByLoginObj(loginObj);
			if(loginObj.getUserTypeId() == UserType.SUPER_ADMIN.id()){
				if(adminObj != null){
						List<Address> addressList = addressDAO.loadAddressById(ObjectType.SUPER_ADMIN.id(), adminObj.getId());
						if(addressList != null && !addressList.isEmpty()){
							adminObj.setAddressObj(addressList.get(0));
						}
						List<ContactDetails> detailsList = contactDetailsDAO.loadById(ObjectType.SUPER_ADMIN.id(), adminObj.getId());
						if(detailsList != null && !detailsList.isEmpty()){
							adminObj.setContactDetailsObj(detailsList.get(0));
						}
				}
			}else{
				if(adminObj != null){
						List<Address> addressList = addressDAO.loadAddressById(ObjectType.ADMIN.id(), adminObj.getId());
						if(addressList != null && !addressList.isEmpty()){
							adminObj.setAddressObj(addressList.get(0));
						}
						List<City> cityList = cityDAO.loadCityByObjectId(ObjectType.ADMIN.id(), adminObj.getId());
						if(cityList != null && !cityList.isEmpty()){
							String cityString = "";
							String delim = "";
							for (City city : cityList) {
								cityString += delim + city.getCity();
								delim = ", ";
							}
							adminObj.setCityAlloted(cityString);
						}
						List<ContactDetails> detailsList = contactDetailsDAO.loadById(ObjectType.ADMIN.id(), adminObj.getId());
						if(detailsList != null && !detailsList.isEmpty()){
							adminObj.setContactDetailsObj(detailsList.get(0));
						}
				}
			}
			return adminObj;
		}
		return null;
	}
	
	@Override
	public List<Documents> loadDocumentsByAssignModel(final Long assignModelId){
		AssignModel assignModel = assignDAO.loadAssignModelById(assignModelId);
		if(assignModel != null){
			List<Documents> documents = documentsDAO.loadDocumentsByContractorFunctionalityAndPropertyId(assignModel.getAssignToId(), assignModel.getFunctionalityId(), assignModel.getPropertyId());
			if(documents != null && !documents.isEmpty()){
				for (Documents _docObj : documents) {
				 File fileObj = fileManager.loadFileById(_docObj.getFileId());
				 if(fileObj != null){
					 _docObj.setFileObj(fileObj);
				 }
				}
				return documents;
			}
		}
		return null;
	}
	
	@Override
	public boolean disableUser(Long adminId, Long userId) {
		Admin adminObj = userAdminDAO.loadAdminById(adminId, null);
		if(adminObj != null){
			 userLoginDAO.disableUser(adminObj.getLoginUserId(), userId);
			 List<ContactDetails> details = contactDetailsDAO.loadById(ObjectType.ADMIN.id(), adminObj.getId());
			 if(details != null && !details.isEmpty()){
				 adminObj.setContactDetailsObj(details.get(0));
			 }
			 SwingUtilities.invokeLater(new SubAdminMailUtility(adminObj, applicationSettings, mailSender, MailEventType.DISABLE, mailEventManager, templateEngine, commonManager));
			 return true;
		}
		return false;
		
	}

	@Override
	public boolean enableUser(Long adminId, Long userId) {
		Admin adminObj = userAdminDAO.loadAdminById(adminId, null);
		if(adminObj != null){
			 userLoginDAO.enableUser(adminObj.getLoginUserId(), userId);
			 List<ContactDetails> details = contactDetailsDAO.loadById(ObjectType.ADMIN.id(), adminObj.getId());
			 if(details != null && !details.isEmpty()){
				 adminObj.setContactDetailsObj(details.get(0));
			 }
			 SwingUtilities.invokeLater(new SubAdminMailUtility(adminObj, applicationSettings, mailSender, MailEventType.ENABLE, mailEventManager, templateEngine, commonManager));
			 return true;
		}
		return false;
	}
	
	@Override
	public boolean updateAdminCities(String[] cityIdArr , Long id) {
			boolean execStatus = true;
			Long tempId = null;
			cityDAO.deleteCityByObjectType(ObjectType.ADMIN.id(), id);
			if(cityIdArr != null && cityIdArr.length != 0){
				PerpetualCity pCityObj = null;
				for (String cityId : cityIdArr) {
					pCityObj = cityDAO.loadPerpetualCityById(Long.valueOf(cityId));
					if(pCityObj != null){
						tempId = cityDAO.createCity(new City(pCityObj.getCity(), pCityObj.getId(), ObjectType.ADMIN.id(), id));
						if(tempId == null){
							execStatus = false;
							break;
						}else{
							continue;
						}
					}
				}
			}
			return execStatus;
	}

	@Override
	public String resetPassword(Long id) {
		Admin adminObj = userAdminDAO.loadAdminById(id, null);
		String password = null;
		if(adminObj != null){
		UserLogin loginObj = adminObj.getLoginObj();
		if(loginObj != null){
			loginObj.createPassword();
			password = userAdminDAO.updatePassword(adminObj);
		}
		}
		return password;
	}

	@Override
	public List<PerpetualCity> loadAllCities(){
		List<PerpetualCity> cityList = cityDAO.loadAllPerpetualCity();
		if(cityList != null && !cityList.isEmpty()){
			
			return cityList;
		}
		return null;
	}
	
	@Override
	public boolean uploadFiles(File fileObj, Long userId, Integer adminDocumentId){

		AdminUploadDocuments tDocObj = adminDocumentDAO.loadByDocumentId(Long.valueOf(adminDocumentId));
		if(tDocObj != null){
			File tempFileObj = fileManager.loadFileById(tDocObj.getFileId());
			if(tempFileObj != null){
				fileManager.deleteFileById(tempFileObj.getId());
				adminDocumentDAO.deleteFileById(Long.valueOf(adminDocumentId));
			}
		}
		
		Long fileId = fileManager.saveAdminFile(fileObj, userId);
		if(fileId != null){
			AdminUploadDocuments docObj = new AdminUploadDocuments();
				docObj.setAdminDocumentId(adminDocumentId);
				docObj.setCreatedUserId(userId);
				docObj.setDocumentTypeId((int)DocumentType.DOCUMENT.id());
				docObj.setFileId(fileId);
				docObj.setFileName(fileObj.getFileName());
			Long docId = adminDocumentDAO.saveDocuments(docObj, userId);
			
			if(docId != null)
				return true;
		}
	  
		return false;
	}
	
	
	public AdminUploadDocuments loadByDocumentId(final Long documentId){
		AdminUploadDocuments obj = adminDocumentDAO.loadByDocumentId(documentId);
		if(obj != null)
			obj.setFileObj(fileManager.loadFileById(obj.getFileId()));
		
		return obj;
	}

	@Override
	public List<AdminUploadDocuments> loadAllUploadedDocuments() {
		return adminDocumentDAO.loadAllDocuments();
	}
	
}

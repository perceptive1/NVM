package com.pcs.perpetualRents.business.bean;

import java.io.Serializable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pcs.perpetualRents.common.bean.ApplicationSettings;
import com.pcs.perpetualRents.common.bean.BusinessObject;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Charity extends BusinessObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "tbl_" + ApplicationSettings.APP_NAME + "_" + Charity.class.getSimpleName().toLowerCase();
	
	public static final String UNIQUE_REFERENCE = "uniqueReference";
	public static final String COMPANY_NAME = "companyName";
	public static final String AUTHORISED_PERSON = "authorisedPerson";
	public static final String WEB_URL = "weburl";
	public static final String ENABLE = "enable";
	public static final String USER_LOGIN_ID = "userLoginId";
	public static final String CITIES_COVERED = "citiesCovered";
	
	private String uniqueReference;
	private String companyName;
	private String authorisedPerson;
	private String weburl;
	private boolean enable;
	
	private List<String> citiesCoveredList;
	private String citiesCovered;
	private String cityCoveredId;
	private Long userLoginId;
	
	private UserLogin loginObj;
	private Address addressObj;
	private ContactDetails contactDetailsObj;
	
	List<City> cityList;
	
	public List<City> getCityList() {
		return cityList;
	}
	public String getCityCoveredId() {
		return cityCoveredId;
	}
	public void setCityCoveredId(String cityCoveredId) {
		this.cityCoveredId = cityCoveredId;
	}
	public List<String> getCitiesCoveredList() {
		return citiesCoveredList;
	}
	public void setCitiesCoveredList(List<String> citiesCoveredList) {
		this.citiesCoveredList = citiesCoveredList;
	}
	public UserLogin getLoginObj() {
		return loginObj;
	}
	public void setLoginObj(UserLogin loginObj) {
		this.loginObj = loginObj;
	}
	public ContactDetails getContactDetailsObj() {
		return contactDetailsObj;
	}
	public void setContactDetailsObj(ContactDetails contactDetailsObj) {
		this.contactDetailsObj = contactDetailsObj;
	}
	public String getAuthorisedPerson() {
		return authorisedPerson;
	}
	public void setAuthorisedPerson(String authorisedPerson) {
		this.authorisedPerson = authorisedPerson;
	}
	public String getCitiesCovered() {
		return citiesCovered;
	}
	public void setCitiesCovered(String citiesCovered) {
		this.citiesCovered = citiesCovered;
	}
	public Address getAddressObj() {
		return addressObj;
	}
	public void setAddressObj(Address addressObj) {
		this.addressObj = addressObj;
	}
	public String getUniqueReference() {
		return uniqueReference;
	}
	public void setUniqueReference(String uniqueReference) {
		this.uniqueReference = uniqueReference;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getWeburl() {
		return weburl;
	}
	public void setWeburl(String weburl) {
		this.weburl = weburl;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Long getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(Long userLoginId) {
		this.userLoginId = userLoginId;
	}
	
	@Override
	public String toString() {
		return "Charity [uniqueReference=" + uniqueReference + ", companyName="
				+ companyName + ", authorisedPerson=" + authorisedPerson
				+ ", weburl=" + weburl + ", enable=" + enable
				+ ", citiesCoveredList=" + citiesCoveredList
				+ ", citiesCovered=" + citiesCovered + ", cityCoveredId="
				+ cityCoveredId + ", userLoginId=" + userLoginId
				+ ", loginObj=" + loginObj + ", addressObj=" + addressObj
				+ ", contactDetailsObj=" + contactDetailsObj + "]";
	}
	public JSONObject toJSON(boolean includeParent) throws JSONException{
		JSONObject jObj = new JSONObject();
		
			if(includeParent)
				jObj = super.toJSON();
			
			jObj.put(Charity.COMPANY_NAME, this.companyName);
			jObj.put(Charity.AUTHORISED_PERSON, this.authorisedPerson);
			jObj.put(Charity.UNIQUE_REFERENCE, this.uniqueReference);
			jObj.put(Charity.WEB_URL, this.weburl);
			jObj.put(Charity.CITIES_COVERED, this.citiesCovered);
			jObj.put("cityCoveredId", cityCoveredId);
			
		return jObj;
	}
	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}
}

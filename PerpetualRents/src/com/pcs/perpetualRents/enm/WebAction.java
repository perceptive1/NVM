package com.pcs.perpetualRents.enm;

import com.pcs.perpetualRents.commonFunction.CommonFunctions;

public enum WebAction {
	DEFAULT(0,"Default"),
	ADD(1, "Add"),
	FETCH(2 , "Fetch"),
	UPDATE(3 , "Update"),
	LIST(4 , "List"),
	DISABLE(5 , "Disable"),
	REMOVE(6 , "Remove"),
	DELETE(7, "Delete"),
	VIEW(8, "View"),
	ASSIGN(9, "Assign"),
	
	REGISTER_LANDLORD(101 , "Register Landlord"),
	EDIT_LANDLORD(102 , "Edit Landlord"),
	PROFILE_LANDLORD(103 , "Profile Landlord"),
	CHANGE_PASSWORD_LANDLORD(104 , "Change Landlord"),
	SUCCESSFULL_LANDLORD(105 , "Successfull Landlord"),
	PROPERTIES_LANDLORD(106 , "Landlord Properties"),
	CONVERSATION(107 , "View Conversation"),
	DOCUMENTS(108 , "View Documents"),
	
	REGISTER_PROPERTY(201 , "Register Property"),
	HMO_LICENSE(202 , "HMO License"),
	DHS_CRITERIA_A(203 , "DHS CRITERIA A"),
	DHS_CRITERIA_B(204 , "DHS CRITERIA B"),
	DHS_CRITERIA_C(205 , "DHS CRITERIA C"),
	DHS_CRITERIA_D(206 , "DHS CRITERIA D"),
	PROPERTY_DETAIL(207 , "Property Detail"),
	PROPERTY_DEFAULT(209, "Default"),
	PROCEED(210, "Proceed"),
	REVIEW_PROPERTY(211, "Review Property"),
	PROPERTY_MARKETING_INFORMATION(212, "Review Property"),
	
	PROFILE_CONTRACTOR(303 , "Profile Contractor"),
	CHANGE_PASSWORD_CONTRACTOR(304 , "Change Contractor"),
	CONTRACTOR_ASSIGNED_JOBS(306 , "Contractor Assigned Jobs"),
	SUCCESSFULL_CONTRACTOR(307 , "Successfull Contractor"),
	EXPERTISE_LIST(308 , "Expertise List"),
	ADD_EXPERTISE(309 , "Add Expertise"),
	CONTRACTOR_MAILS(310 , "Contractor Mails"),
	COMP_LANDLORD(500 , "Complete Landlord"),
	LANDLORD(401 , "Landlord"),
	CONTRACTOR(402, "Contractor"),
	PROPERTY(403, "Property"),
	PMC(404, "PMC"),
	SETTINGS(405, "Settings"),
	USERS(406, "Users"),
	COOKIES(503, "Cookies"),
	PRIVACY_POLICY(504, "Privacy Policy"),
	ACCEPTABLE_POLICY(505, "Acceptable Policy"),
	REVIEW_PDPROPERTY(600,"Review PDProperty");
	
	
	
	private WebAction(long id, String value) {
		this.id = id;
		this.value = value;
	}
	
	private long id;
	private String value;
	
	public long id() {
		return id;
	}

	public String value() {
		return value;
	}
	
	public static WebAction get(String action){
		if(CommonFunctions.isInteger(action)){
			for (WebAction termsObj : WebAction.values()) {
				if(termsObj.id == Integer.parseInt(action)){
					return termsObj;
				}
			}
		}else{
			for (WebAction termsObj : WebAction.values()) {
				if(termsObj.value.equalsIgnoreCase(action))
					return termsObj;
			}
		}
		return WebAction.DEFAULT;
	}
	
}

package com.pcs.perpetualRents.common.bean;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class BusinessObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final String ID = "id";
	public static final String CREATED_ON = "createdOn";
	public static final String MODIFIED_ON = "modifiedOn";
	public static final String CREATED_USER_ID = "createdUserId";
	public static final String MODIFIED_USER_ID = "modifiedUserId";
	
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	
	private Long id;
	
	private Date createdOn;
	private Date modifiedOn;
	
	private Long createdUserId;
	private Long modifiedUserId;
	
	private String createdBy;
	private String modifiedBy;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public Long getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}
	public Long getModifiedUserId() {
		return modifiedUserId;
	}
	public void setModifiedUserId(Long modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public JSONObject toJSON() throws JSONException{
		
		JSONObject jObj = new JSONObject();
			jObj.put("id", this.id);
			jObj.put("createdOn", this.createdOn);
			jObj.put("modifiedOn", this.modifiedOn == null ? "Not Yet Modified" : this.modifiedOn);
			jObj.put("createdUserId", this.createdUserId);
			jObj.put("modifiedUserId", this.modifiedUserId);
			jObj.put("createdBy", this.createdBy == null ? "Self Registered" : this.createdBy);
			jObj.put("modifiedBy", this.modifiedBy == null ? "Not Yet Modified" : this.modifiedBy);
			
		return jObj;
	}
	@Override
	public String toString() {
		return "BusinessObject [id=" + id + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", createdUserId="
				+ createdUserId + ", modifiedUserId=" + modifiedUserId
				+ ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ "]";
	}
	
}
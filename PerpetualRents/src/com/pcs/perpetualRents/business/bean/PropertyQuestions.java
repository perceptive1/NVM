package com.pcs.perpetualRents.business.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pcs.perpetualRents.common.bean.ApplicationSettings;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PropertyQuestions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String TABLE_NAME = "tbl_" + ApplicationSettings.APP_NAME + "_" + PropertyQuestions.class.getSimpleName().toLowerCase();
	
	public static final String ID = "id";
	public static final String GROUP_ID = "groupId";
	public static final String FIELD_NAME = "fieldName";
	public static final String DESCRIPTION = "description";
	public static final String QUESTION = "question";
	public static final String DOCUMENT_SUPPORTED = "documentSupported";
	public static final String AFFIRMATIVE_ANS = "affirmativeAns";
	public static final String CONSIDERABLE = "considerable";
	
	private Long id;
	private Long groupId;
	private String fieldName;
	private String description;
	private String question;
	private boolean documentSupported;
	private boolean affirmativeAns;
	private boolean considerable;
	
	public boolean isConsiderable() {
		return considerable;
	}
	public void setConsiderable(boolean considerable) {
		this.considerable = considerable;
	}
	public boolean isAffirmativeAns() {
		return affirmativeAns;
	}
	public void setAffirmativeAns(boolean affirmativeAns) {
		this.affirmativeAns = affirmativeAns;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public boolean isDocumentSupported() {
		return documentSupported;
	}
	public void setDocumentSupported(boolean documentSupported) {
		this.documentSupported = documentSupported;
	}
	
	@Override
	public String toString() {
		return "PropertyQuestions [id=" + id + ", groupId=" + groupId
				+ ", fieldName=" + fieldName + ", description=" + description
				+ ", question=" + question + ", documentSupported="
				+ documentSupported + ", affirmativeAns=" + affirmativeAns
				+ ", considerable=" + considerable + ", radioGroupMap="
				+ "]";
	}
	
	public JSONObject toJSON() throws JSONException{
		JSONObject jObj = new JSONObject();
			jObj.put(PropertyQuestions.AFFIRMATIVE_ANS, this.affirmativeAns);
			jObj.put(PropertyQuestions.DESCRIPTION, this.description);
			jObj.put(PropertyQuestions.FIELD_NAME, this.fieldName);
			jObj.put(PropertyQuestions.DOCUMENT_SUPPORTED, this.documentSupported);
		return jObj;
	}
	
}

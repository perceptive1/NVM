package com.pcs.perpetualRents.manager.impl;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pcs.perpetualRents.business.bean.File;
import com.pcs.perpetualRents.common.bean.ApplicationSettings;
import com.pcs.perpetualRents.commonFunction.CommonFunctions;
import com.pcs.perpetualRents.dao.FileDAO;
import com.pcs.perpetualRents.manager.FileManager;

@Service
public class FileManagerImpl implements FileManager {
	
	@Autowired
	private FileDAO fileDAO;
	@Autowired
	private ApplicationSettings applicationSettings;
	
	private void copyToSyncFolder(String applicationFolderPath, File tFileObj){
		try {
			
			String filePath = tFileObj.getFilePath();
			filePath = filePath.replaceAll(applicationSettings.getFolderName(), "");
			filePath = filePath.replaceAll("\\\\", "/");
			
			java.io.File tNewFileObj = new java.io.File(applicationSettings.getDocumentRepository() + java.io.File.separator + filePath.substring(0, filePath.lastIndexOf("/")));
			if(!tNewFileObj.exists())
				tNewFileObj.mkdirs();
			
			FileOutputStream tOStreamObj = new FileOutputStream(applicationSettings.getDocumentRepository() + java.io.File.separator + filePath);
				tOStreamObj.write(tFileObj.getFileData());
				tOStreamObj.flush();
				tOStreamObj.close();
		} catch(Exception io){
			io.printStackTrace();
		}
	}
	
	private void deleteFromSyncFolder(String filePath){
		filePath = filePath.replaceAll(applicationSettings.getFolderName(), "");
		filePath = filePath.replaceAll("\\\\", "/");
		FileUtils.deleteQuietly(new java.io.File(applicationSettings.getDocumentRepository() + java.io.File.separator + filePath));
	}
	
	@Override
	public Long saveFile(File fileObj, Long createdUserId) {
		String tPath = CommonFunctions.savePropertyFile(applicationSettings.getFolderPath(), applicationSettings.getFolderName(), fileObj);

		if(tPath != null){
			if(tPath.contains("\\")){
				tPath = tPath.replaceAll("\\\\", "/");
				fileObj.setFilePath(tPath);
				Long fileId = fileDAO.saveFile(fileObj, createdUserId);
				if(fileId != null){
					fileObj.setId(fileId);
					copyToSyncFolder(applicationSettings.getFolderPath(), fileObj);
					return fileId;
				}
			
			}else{
				fileObj.setFilePath(tPath);
				Long fileId = fileDAO.saveFile(fileObj, createdUserId);
				if(fileId != null){
					fileObj.setId(fileId);
					copyToSyncFolder(applicationSettings.getFolderPath(), fileObj);
					return fileId;
				}
			}
		}
		return null;
	}

	@Override
	public Long saveAdminFile(File fileObj, Long createdUserId) {
		String tPath = CommonFunctions.saveAdminFile(applicationSettings.getFolderPath(), applicationSettings.getFolderName(), fileObj);
		if(tPath != null){
			if(tPath.contains("\\")){
				tPath = tPath.replaceAll("\\\\", "/");
				fileObj.setFilePath(tPath);
				Long fileId = fileDAO.saveFile(fileObj, createdUserId);
				if(fileId != null){
					copyToSyncFolder(applicationSettings.getFolderPath(), fileObj);
					return fileId;
				}
			}else{
				fileObj.setFilePath(tPath);
				Long fileId = fileDAO.saveFile(fileObj, createdUserId);
				if(fileId != null){
					copyToSyncFolder(applicationSettings.getFolderPath(), fileObj);
					return fileId;
				}
			}
		}
		return null;
	}
	
	@Override
	public File loadFileById(Long fileId) {
		if(fileId == null)
			return null;
		File fileObj = fileDAO.loadFileById(fileId);
		if(fileObj != null){
			String realPath = applicationSettings.getFolderPath().replace(applicationSettings.getFolderName(), "");
			fileObj.setFilePath(realPath + java.io.File.separator + fileObj.getFilePath());
			CommonFunctions.loadfile(fileObj);
			return fileObj;
		}  
		return null;
	}

	@Override
	public List<File> loadFileByObjectType(Long objectType, Long objectId) {
		List<File>  files = fileDAO.loadFileByObjectType(objectType, objectId);
		if(files != null && !files.isEmpty()){
			for (File file : files)
				CommonFunctions.loadfile(file);
			
			return files;
		}
		
		return null;
	}
	
	@Override
	public void deleteFileById(Long fileId) {
		File tFile = fileDAO.loadFileById(fileId);
		if(tFile != null){
			String relPath = tFile.getFilePath();
			String realPath = applicationSettings.getFolderPath().replace(applicationSettings.getFolderName(), "");
			tFile.setFilePath(realPath + java.io.File.separator + tFile.getFilePath());
			boolean fileStatus = CommonFunctions.deleteFile(tFile);
			if(fileStatus){
				deleteFromSyncFolder(relPath);
				fileDAO.deleteFileById(fileId);
			}
		}
	}
}

package com.bunge.pocbungeeyspringboot.domain.dto;

import java.io.Serializable;

public class DocumentosDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String fileName;
	
	protected Long fileSize;
	
	protected String filePath;
	

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
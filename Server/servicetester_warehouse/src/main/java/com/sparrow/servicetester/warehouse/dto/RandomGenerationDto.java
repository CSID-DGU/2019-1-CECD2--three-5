package com.sparrow.servicetester.warehouse.dto;

public class RandomGenerationDto extends CommonDto{

	private int projectNo;
	private long methodNo;
	private int generateCount;
	private int ruleNo;
	private int generationNo;
	private int totalCnt;

	private String generationDivision;
	private String executionUuid;
	private String relationExecutionUuid;
	private String projectName;
	private String folderFilePath;
	private String fileName;
	private String methodName;
	private String projectOwner;

	private String generator;
	private String generateDate;
	private String amender;
	private String revisionDate;

	public int getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(int projectNo) {
		this.projectNo = projectNo;
	}
	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
	}
	public int getGenerateCount() {
		return generateCount;
	}
	public void setGenerateCount(int generateCount) {
		this.generateCount = generateCount;
	}
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getGenerationDivision() {
		return generationDivision;
	}
	public void setGenerationDivision(String generationDivision) {
		this.generationDivision = generationDivision;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public String getRelationExecutionUuid() {
		return relationExecutionUuid;
	}
	public void setRelationExecutionUuid(String relationExecutionUuid) {
		this.relationExecutionUuid = relationExecutionUuid;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getFolderFilePath() {
		return folderFilePath;
	}
	public void setFolderFilePath(String folderFilePath) {
		this.folderFilePath = folderFilePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}
	public String getAmender() {
		return amender;
	}
	public void setAmender(String amender) {
		this.amender = amender;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getProjectOwner() {
		return projectOwner;
	}
	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}
	public int getGenerationNo() {
		return generationNo;
	}
	public void setGenerationNo(int generationNo) {
		this.generationNo = generationNo;
	}
}

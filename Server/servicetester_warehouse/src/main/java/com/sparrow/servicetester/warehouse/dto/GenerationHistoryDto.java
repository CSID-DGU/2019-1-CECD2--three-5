package com.sparrow.servicetester.warehouse.dto;

import java.util.Date;


public class GenerationHistoryDto extends CommonDto{
	private String executionUuid;
	private String generateComment;
	private String compressFileName;
	private String compressFilePath;
	private String isReflection;
	private String generator;
	private Date generateDate;
	private int ruleNo;

	//검색 관련
	private int    executionProjectNo;
    private String executionCondition;
    private String executionNode     ;
    private String executorId       ;
    private String generateDateStr  ;
    private String executorName     ;
    private String executionDivision ;

    private String searchGenerateDateFrom;
    private String searchGenerateDateTo;
    private String searchExecutor;

	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public String getCompressFileName() {
		return compressFileName;
	}
	public void setCompressFileName(String compressFileName) {
		this.compressFileName = compressFileName;
	}
	public String getCompressFilePath() {
		return compressFilePath;
	}
	public void setCompressFilePath(String compressFilePath) {
		this.compressFilePath = compressFilePath;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public Date getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}
	public int getExecutionProjectNo() {
		return executionProjectNo;
	}
	public void setExecutionProjectNo(int executionProjectNo) {
		this.executionProjectNo = executionProjectNo;
	}
	public String getExecutionCondition() {
		return executionCondition;
	}
	public void setExecutionCondition(String executionCondition) {
		this.executionCondition = executionCondition;
	}
	public String getExecutionNode() {
		return executionNode;
	}
	public void setExecutionNode(String executionNode) {
		this.executionNode = executionNode;
	}
	public String getExecutorId() {
		return executorId;
	}
	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}
	public String getGenerateDateStr() {
		return generateDateStr;
	}
	public void setGenerateDateStr(String generateDateStr) {
		this.generateDateStr = generateDateStr;
	}
	public String getExecutorName() {
		return executorName;
	}
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	public String getSearchGenerateDateFrom() {
		return searchGenerateDateFrom;
	}
	public void setSearchGenerateDateFrom(String searchGenerateDateFrom) {
		this.searchGenerateDateFrom = searchGenerateDateFrom;
	}
	public String getSearchGenerateDateTo() {
		return searchGenerateDateTo;
	}
	public void setSearchGenerateDateTo(String searchGenerateDateTo) {
		this.searchGenerateDateTo = searchGenerateDateTo;
	}
	public String getSearchExecutor() {
		return searchExecutor;
	}
	public void setSearchExecutor(String searchExecutor) {
		this.searchExecutor = searchExecutor;
	}
	public String getIsReflection() {
		return isReflection;
	}
	public void setIsReflection(String isReflection) {
		this.isReflection = isReflection;
	}
	public String getExecutionDivision() {
		return executionDivision;
	}
	public void setExecutionDivision(String executionDivision) {
		this.executionDivision = executionDivision;
	}
	public String getGenerateComment() {
		return generateComment;
	}
	public void setGenerateComment(String generateComment) {
		this.generateComment = generateComment;
	}
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}

}
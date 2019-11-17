/*
 * 작성일 : 2019.07.16
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.dto;

import java.util.Date;

public class RandomGenerationHistoryDto extends CommonDto{
	private String executionUuid;
	private String folderFileName;
	private String folderFilePath;
	private long methodNo;
	private String methodName;
	private String executionDetailCondition;
	private int generatedTestcaseCount;
	private String generator;
	private String generateDate;
	private String amender;
	private String revisionDate;
	private String ruleName;
	private int generationNo;
	private String generateComment;

	private String compressFileName;
	private String compressFilePath;
	private int    executionProjectNo;
    private String executionCondition;
    private String executionNode     ;
    private String executorId       ;
    private String executorName     ;
    private String executionDivision ;
    
	//검색 관련
    private String searchGenerateDateFrom;
    private String searchGenerateDateTo;
    private String searchGenerator;
    private String searchGenerateRuleName;

    private int rowSeqNo;
	
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
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
	public String getSearchGenerator() {
		return searchGenerator;
	}
	public void setSearchGenerator(String searchGenerator) {
		this.searchGenerator = searchGenerator;
	}
	public int getGenerationNo() {
		return generationNo;
	}
	public void setGenerationNo(int generationNo) {
		this.generationNo = generationNo;
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
	public String getExecutorName() {
		return executorName;
	}
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
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
	public String getFolderFileName() {
		return folderFileName;
	}
	public void setFolderFileName(String folderFileName) {
		this.folderFileName = folderFileName;
	}
	public String getFolderFilePath() {
		return folderFilePath;
	}
	public void setFolderFilePath(String folderFilePath) {
		this.folderFilePath = folderFilePath;
	}
	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
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
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getSearchGenerateRuleName() {
		return searchGenerateRuleName;
	}
	public void setSearchGenerateRuleName(String searchGenerateRuleName) {
		this.searchGenerateRuleName = searchGenerateRuleName;
	}
	public int getRowSeqNo() {
		return rowSeqNo;
	}
	public void setRowSeqNo(int rowSeqNo) {
		this.rowSeqNo = rowSeqNo;
	}
	public int getGeneratedTestcaseCount() {
		return generatedTestcaseCount;
	}
	public void setGeneratedTestcaseCount(int generatedTestcaseCount) {
		this.generatedTestcaseCount = generatedTestcaseCount;
	}
	public String getExecutionDetailCondition() {
		return executionDetailCondition;
	}
	public void setExecutionDetailCondition(String executionDetailCondition) {
		this.executionDetailCondition = executionDetailCondition;
	}
}

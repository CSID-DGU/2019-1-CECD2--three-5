package com.sparrow.servicetester.warehouse.domain.pairwisegeneration;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewPairwiseGenerationHistoryListDomain extends CommonDomain{
	@StNotEmptyInt(inputZero=false)
	private int    executionProjectNo;
	
	@StNotEmptyInt(inputZero=false)
	private int pageCnt;

	@StNotEmptyInt(inputZero=false)
    private int pageNum;
	private int totalCnt;
	
	private String executionUuid;
	private String generateComment;
	private String folderFileName;
	private long methodNo;
	private String folderFilePath;
	private String ruleName;
	private String methodName;
	private int generatedCount;
	private int generationNo;
	
	private String generator;
	private String generateDate;
	private String revisionDate;
	private String amender;
	
    private String executionCondition;
    private String executionNode     ;
    private String executorId       ;
    private String executorName     ;
    private String executionDivision ;

    private String isTotalCnt;

    private String searchGenerateDateFrom;
    private String searchGenerateDateTo;
    private String searchGenerator;
    private String searchGenerateRuleName;
    private String searchGenerateComment;
    
	public int getExecutionProjectNo() {
		return executionProjectNo;
	}
	public void setExecutionProjectNo(int executionProjectNo) {
		this.executionProjectNo = executionProjectNo;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
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
	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
	}
	public String getFolderFilePath() {
		return folderFilePath;
	}
	public void setFolderFilePath(String folderFilePath) {
		this.folderFilePath = folderFilePath;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getGenerationNo() {
		return generationNo;
	}
	public void setGenerationNo(int generationNo) {
		this.generationNo = generationNo;
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
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getAmender() {
		return amender;
	}
	public void setAmender(String amender) {
		this.amender = amender;
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
	public String getIsTotalCnt() {
		return isTotalCnt;
	}
	public void setIsTotalCnt(String isTotalCnt) {
		this.isTotalCnt = isTotalCnt;
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
	public String getSearchGenerateRuleName() {
		return searchGenerateRuleName;
	}
	public void setSearchGenerateRuleName(String searchGenerateRuleName) {
		this.searchGenerateRuleName = searchGenerateRuleName;
	}
	public String getSearchGenerateComment() {
		return searchGenerateComment;
	}
	public void setSearchGenerateComment(String searchGenerateComment) {
		this.searchGenerateComment = searchGenerateComment;
	}
	public int getGeneratedCount() {
		return generatedCount;
	}
	public void setGeneratedCount(int generatedCount) {
		this.generatedCount = generatedCount;
	}
}

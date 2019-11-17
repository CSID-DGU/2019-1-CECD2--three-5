package com.sparrow.servicetester.warehouse.dto;

import java.util.Date;

public class RandomGenerationTestcaseDto extends CommonDto{
	//검색조건
	private String searchMessage;
	private String searchTestcaseName;
	private String searchTestcaseGroupName;
	private String searchResultCond;
	private String searchExpectedResultCond;
	private String searchExpectedDbResultCond;
	private String searchFolderFileName;

	private long searchTestcaseNo;

	// xml 데이터 값
	private String methodReturnXmlFile;
	private String methodInputXmlFile;
	private String methodOutputXmlFile;
	private String methodAreaXmlFile;


	//테스트실행고유값
	private int runResultNo;

	//실행이력 uuid
	private String executionUuid;

	//테스트케이스 실행결과
	private String executionFinalResult;
	private String resultMessage;
	private String isPredictionResultCompare;
	private String isDbPredictionResultCompare;

 	private String resultXmlFilePath;

	//파일이름
	private String folderFileName;

	private long   testcaseNo         ;
	private String testcaseName       ;
	private String testcaseDescription;
	private long 	testcaseGroupNo		;
	private int    executionCount      ;
	private int    successCount       ;
	private int    failCount          ;
	private String generator          ;
	private Date   generateDate       ;
	private String amender            ;
	private Date   revisionDate       ;

	//검색 관련
	private String testcaseGroupName  ;
	private String generateDateStr    ;

	private String serviceName;

	private long   methodNo            ;
    private String methodDivision      ;
    private String methodName          ;
    private long   fileNo              ;
    private int    lineCount           ;
    private String methodReturnXml     ;
    private String methodInputXml      ;
    private String methodOutputXml     ;
    private String methodAreaXml       ;
    
	public String getSearchMessage() {
		return searchMessage;
	}
	public void setSearchMessage(String searchMessage) {
		this.searchMessage = searchMessage;
	}
	public String getSearchTestcaseName() {
		return searchTestcaseName;
	}
	public void setSearchTestcaseName(String searchTestcaseName) {
		this.searchTestcaseName = searchTestcaseName;
	}
	public String getSearchTestcaseGroupName() {
		return searchTestcaseGroupName;
	}
	public void setSearchTestcaseGroupName(String searchTestcaseGroupName) {
		this.searchTestcaseGroupName = searchTestcaseGroupName;
	}
	public String getSearchResultCond() {
		return searchResultCond;
	}
	public void setSearchResultCond(String searchResultCond) {
		this.searchResultCond = searchResultCond;
	}
	public String getSearchExpectedResultCond() {
		return searchExpectedResultCond;
	}
	public void setSearchExpectedResultCond(String searchExpectedResultCond) {
		this.searchExpectedResultCond = searchExpectedResultCond;
	}
	public String getSearchExpectedDbResultCond() {
		return searchExpectedDbResultCond;
	}
	public void setSearchExpectedDbResultCond(String searchExpectedDbResultCond) {
		this.searchExpectedDbResultCond = searchExpectedDbResultCond;
	}
	public String getSearchFolderFileName() {
		return searchFolderFileName;
	}
	public void setSearchFolderFileName(String searchFolderFileName) {
		this.searchFolderFileName = searchFolderFileName;
	}
	public long getSearchTestcaseNo() {
		return searchTestcaseNo;
	}
	public void setSearchTestcaseNo(long searchTestcaseNo) {
		this.searchTestcaseNo = searchTestcaseNo;
	}
	public String getMethodReturnXmlFile() {
		return methodReturnXmlFile;
	}
	public void setMethodReturnXmlFile(String methodReturnXmlFile) {
		this.methodReturnXmlFile = methodReturnXmlFile;
	}
	public String getMethodInputXmlFile() {
		return methodInputXmlFile;
	}
	public void setMethodInputXmlFile(String methodInputXmlFile) {
		this.methodInputXmlFile = methodInputXmlFile;
	}
	public String getMethodOutputXmlFile() {
		return methodOutputXmlFile;
	}
	public void setMethodOutputXmlFile(String methodOutputXmlFile) {
		this.methodOutputXmlFile = methodOutputXmlFile;
	}
	public String getMethodAreaXmlFile() {
		return methodAreaXmlFile;
	}
	public void setMethodAreaXmlFile(String methodAreaXmlFile) {
		this.methodAreaXmlFile = methodAreaXmlFile;
	}
	public int getRunResultNo() {
		return runResultNo;
	}
	public void setRunResultNo(int runResultNo) {
		this.runResultNo = runResultNo;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public String getExecutionFinalResult() {
		return executionFinalResult;
	}
	public void setExecutionFinalResult(String executionFinalResult) {
		this.executionFinalResult = executionFinalResult;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getIsPredictionResultCompare() {
		return isPredictionResultCompare;
	}
	public void setIsPredictionResultCompare(String isPredictionResultCompare) {
		this.isPredictionResultCompare = isPredictionResultCompare;
	}
	public String getIsDbPredictionResultCompare() {
		return isDbPredictionResultCompare;
	}
	public void setIsDbPredictionResultCompare(String isDbPredictionResultCompare) {
		this.isDbPredictionResultCompare = isDbPredictionResultCompare;
	}
	public String getResultXmlFilePath() {
		return resultXmlFilePath;
	}
	public void setResultXmlFilePath(String resultXmlFilePath) {
		this.resultXmlFilePath = resultXmlFilePath;
	}
	public String getFolderFileName() {
		return folderFileName;
	}
	public void setFolderFileName(String folderFileName) {
		this.folderFileName = folderFileName;
	}
	public long getTestcaseNo() {
		return testcaseNo;
	}
	public void setTestcaseNo(long testcaseNo) {
		this.testcaseNo = testcaseNo;
	}
	public String getTestcaseName() {
		return testcaseName;
	}
	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}
	public String getTestcaseDescription() {
		return testcaseDescription;
	}
	public void setTestcaseDescription(String testcaseDescription) {
		this.testcaseDescription = testcaseDescription;
	}
	public long getTestcaseGroupNo() {
		return testcaseGroupNo;
	}
	public void setTestcaseGroupNo(long testcaseGroupNo) {
		this.testcaseGroupNo = testcaseGroupNo;
	}
	public int getExecutionCount() {
		return executionCount;
	}
	public void setExecutionCount(int executionCount) {
		this.executionCount = executionCount;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
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
	public String getAmender() {
		return amender;
	}
	public void setAmender(String amender) {
		this.amender = amender;
	}
	public Date getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getTestcaseGroupName() {
		return testcaseGroupName;
	}
	public void setTestcaseGroupName(String testcaseGroupName) {
		this.testcaseGroupName = testcaseGroupName;
	}
	public String getGenerateDateStr() {
		return generateDateStr;
	}
	public void setGenerateDateStr(String generateDateStr) {
		this.generateDateStr = generateDateStr;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
	}
	public String getMethodDivision() {
		return methodDivision;
	}
	public void setMethodDivision(String methodDivision) {
		this.methodDivision = methodDivision;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public long getFileNo() {
		return fileNo;
	}
	public void setFileNo(long fileNo) {
		this.fileNo = fileNo;
	}
	public int getLineCount() {
		return lineCount;
	}
	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
	public String getMethodReturnXml() {
		return methodReturnXml;
	}
	public void setMethodReturnXml(String methodReturnXml) {
		this.methodReturnXml = methodReturnXml;
	}
	public String getMethodInputXml() {
		return methodInputXml;
	}
	public void setMethodInputXml(String methodInputXml) {
		this.methodInputXml = methodInputXml;
	}
	public String getMethodOutputXml() {
		return methodOutputXml;
	}
	public void setMethodOutputXml(String methodOutputXml) {
		this.methodOutputXml = methodOutputXml;
	}
	public String getMethodAreaXml() {
		return methodAreaXml;
	}
	public void setMethodAreaXml(String methodAreaXml) {
		this.methodAreaXml = methodAreaXml;
	}
}

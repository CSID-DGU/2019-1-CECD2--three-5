package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyLong;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewRandomGenerationTestcaseDomain extends CommonDomain{
	@StNotEmptyLong(inputZero=false)
	private long   testcaseNo      ;

	private String testcaseName       ;
	private String testcaseDiscription;
	private long   testcaseGroupNo    ;
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

	//xml file
	private String methodReturnXmlFile;
	private String methodInputXmlFile;
	private String methodOutputXmlFile;
	private String methodAreaXmlFile;

	private long   methodNo            ;
    private String methodDivision      ;
    private String methodName          ;
    private long   fileNo              ;
    private int    lineCount           ;
    private String methodReturnXml     ;
    private String methodInputXml      ;
    private String methodOutputXml     ;
    private String methodAreaXml       ;
    private String executionUuid       ;
    
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
	public String getTestcaseDiscription() {
		return testcaseDiscription;
	}
	public void setTestcaseDiscription(String testcaseDiscription) {
		this.testcaseDiscription = testcaseDiscription;
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
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
}

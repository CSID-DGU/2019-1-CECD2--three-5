package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyLong;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewRandomGenerationTestcaseDetailDomain extends CommonDomain{
	@StNotEmptyLong(inputZero=false)
	private long   testcaseNo      ;
	private String executionUuid;
	
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
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
}

/*
 * 작성일 : 2019.07.30
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ExecuteRandomGenerationReflectionDomain extends CommonDomain{
	@NotEmpty(message = "")
	private String executionUuid;
	@NotEmpty(message = "")
	private List<ExecuteRandomGenerationReflectionDomain> reflectionList;

	private long   testcaseNo      ;
	int totalCnt;
	private String testcaseName       ;
	private String testcaseDescription;
	private long   testcaseGroupNo    ;
	private int    executionCount      ;
	private int    successCount       ;
	private int    failCount          ;
	private String testcaseGroupName  ;
	private String isReflection;
	private long methodNo;
    private String methodName;
    private String folderFileName;
    private String testcaseInputXml;

	private String compressFilePath;

	private String generator;
	private Date generateDate;
	private String generateDateStr;
	private String amender;
	private String revision_date;

	//검색 관련
	private String searchTestcaseGroupName;
	private String searchTestcaseName;
	private String searchGenerateDateFrom;
	private String searchGenerateDateTo;

	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getFolderFileName() {
		return folderFileName;
	}
	public void setFolderFileName(String folderFileName) {
		this.folderFileName = folderFileName;
	}
	public String getTestcaseInputXml() {
		return testcaseInputXml;
	}
	public void setTestcaseInputXml(String testcaseInputXml) {
		this.testcaseInputXml = testcaseInputXml;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getAmender() {
		return amender;
	}
	public void setAmender(String amender) {
		this.amender = amender;
	}
	public String getRevision_date() {
		return revision_date;
	}
	public void setRevision_date(String revision_date) {
		this.revision_date = revision_date;
	}
	public String getSearchTestcaseGroupName() {
		return searchTestcaseGroupName;
	}
	public void setSearchTestcaseGroupName(String searchTestcaseGroupName) {
		this.searchTestcaseGroupName = searchTestcaseGroupName;
	}
	public String getSearchTestcaseName() {
		return searchTestcaseName;
	}
	public void setSearchTestcaseName(String searchTestcaseName) {
		this.searchTestcaseName = searchTestcaseName;
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
	public String getTestcaseGroupName() {
		return testcaseGroupName;
	}
	public void setTestcaseGroupName(String testcaseGroupName) {
		this.testcaseGroupName = testcaseGroupName;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getTestcaseDescription() {
		return testcaseDescription;
	}
	public String getIsReflection() {
		return isReflection;
	}
	public void setIsReflection(String isReflection) {
		this.isReflection = isReflection;
	}
	public String getGenerateDateStr() {
		return generateDateStr;
	}
	public void setGenerateDateStr(String generateDateStr) {
		this.generateDateStr = generateDateStr;
	}
	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}
	public Date getGenerateDate() {
		return generateDate;
	}
	public List<ExecuteRandomGenerationReflectionDomain> getReflectionList() {
		return reflectionList;
	}
	public void setReflectionList(List<ExecuteRandomGenerationReflectionDomain> reflectionList) {
		this.reflectionList = reflectionList;
	}
	public String getCompressFilePath() {
		return compressFilePath;
	}
	public void setCompressFilePath(String compressFilePath) {
		this.compressFilePath = compressFilePath;
	}

}

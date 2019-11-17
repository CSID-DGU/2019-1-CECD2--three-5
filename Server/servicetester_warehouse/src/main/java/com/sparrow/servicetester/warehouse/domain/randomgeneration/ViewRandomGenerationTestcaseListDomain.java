/*
 * 작성일 : 2019.08.08
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewRandomGenerationTestcaseListDomain extends CommonDomain{
	private String folderFileName;
	private long testcaseGroupNo;
	private String testcaseGroupName;
	private String testcaseName;
	private long testcaseNo;
	private String generator;
	private String generateDate;
	private String revisionDate;
	private String isReflection;
	private String methodName;
	private String executionUuid;
	private int generatedCount;
	private int executionProjectNo;
	private long methodNo;
	
	private int totalCnt;
	private int pageCnt;
	private int pageNum;
	//검색관련
    private String searchReviseDateFrom;
    private String searchReviseDateTo;
    private String searchTestcaseGroupName;
    private String searchTestcaseName;
    private String searchGenerator;

	public String getTestcaseName() {
		return testcaseName;
	}
	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getIsReflection() {
		return isReflection;
	}
	public void setIsReflection(String isReflection) {
		this.isReflection = isReflection;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
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
	public String getSearchGenerator() {
		return searchGenerator;
	}
	public void setSearchGenerator(String searchGenerator) {
		this.searchGenerator = searchGenerator;
	}
	public String getSearchReviseDateFrom() {
		return searchReviseDateFrom;
	}
	public void setSearchReviseDateFrom(String searchReviseDateFrom) {
		this.searchReviseDateFrom = searchReviseDateFrom;
	}
	public String getSearchReviseDateTo() {
		return searchReviseDateTo;
	}
	public void setSearchReviseDateTo(String searchReviseDateTo) {
		this.searchReviseDateTo = searchReviseDateTo;
	}
	public int getGeneratedCount() {
		return generatedCount;
	}
	public void setGeneratedCount(int generatedCount) {
		this.generatedCount = generatedCount;
	}

	public String getFolderFileName() {
		return folderFileName;
	}
	public void setFolderFileName(String folderFileName) {
		this.folderFileName = folderFileName;
	}
	public long getTestcaseGroupNo() {
		return testcaseGroupNo;
	}
	public void setTestcaseGroupNo(long testcaseGroupNo) {
		this.testcaseGroupNo = testcaseGroupNo;
	}
	public long getTestcaseNo() {
		return testcaseNo;
	}
	public void setTestcaseNo(long testcaseNo) {
		this.testcaseNo = testcaseNo;
	}
	public int getExecutionProjectNo() {
		return executionProjectNo;
	}
	public void setExecutionProjectNo(int executionProjectNo) {
		this.executionProjectNo = executionProjectNo;
	}
	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
	}
	public String getTestcaseGroupName() {
		return testcaseGroupName;
	}
	public void setTestcaseGroupName(String testcaseGroupName) {
		this.testcaseGroupName = testcaseGroupName;
	}
	public String getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}
}

package com.sparrow.servicetester.warehouse.dto;

public class PairwiseGenerationRuleDto extends CommonDto{
	private String executionUuid;
	private long ruleNo;
	private String ruleName;
	private String ruleDescription;
	private String valueString;
	private String ruleFieldXml;
	
    private String generator;
    private String generateDate;
    private String amender;
    private String revisionDate;
    
    private String folderFileName;
    private String methodName;
    private int generatedTestcaseCount;

    private int methodNo;
    
    //쿼리 추가 컬럼
    private String generatorId;
    private String generateDateStr;

    //페이징 관련
    private int totalCnt;	//총 목록 갯수
    private int pageCnt;	//호출 페이지 번호
    private int pageNum;	//페이지당 목록 갯수

    //검색 관련
    private String searchGenerateDateFrom;
    private String searchGenerateDateTo;
    private String searchRevisionDateFrom;
    private String searchRevisionDateTo;
    private String searchGenerator;
    private String searchRuleName;
    private String searchRuleDescription;
    
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public long getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(long ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
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
	public String getFolderFileName() {
		return folderFileName;
	}
	public void setFolderFileName(String folderFileName) {
		this.folderFileName = folderFileName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getGeneratedTestcaseCount() {
		return generatedTestcaseCount;
	}
	public void setGeneratedTestcaseCount(int generatedTestcaseCount) {
		this.generatedTestcaseCount = generatedTestcaseCount;
	}
	public int getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(int methodNo) {
		this.methodNo = methodNo;
	}
	public String getGeneratorId() {
		return generatorId;
	}
	public void setGeneratorId(String generatorId) {
		this.generatorId = generatorId;
	}
	public String getGenerateDateStr() {
		return generateDateStr;
	}
	public void setGenerateDateStr(String generateDateStr) {
		this.generateDateStr = generateDateStr;
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
	public String getSearchRevisionDateFrom() {
		return searchRevisionDateFrom;
	}
	public void setSearchRevisionDateFrom(String searchRevisionDateFrom) {
		this.searchRevisionDateFrom = searchRevisionDateFrom;
	}
	public String getSearchRevisionDateTo() {
		return searchRevisionDateTo;
	}
	public void setSearchRevisionDateTo(String searchRevisionDateTo) {
		this.searchRevisionDateTo = searchRevisionDateTo;
	}
	public String getSearchGenerator() {
		return searchGenerator;
	}
	public void setSearchGenerator(String searchGenerator) {
		this.searchGenerator = searchGenerator;
	}
	public String getSearchRuleName() {
		return searchRuleName;
	}
	public void setSearchRuleName(String searchRuleName) {
		this.searchRuleName = searchRuleName;
	}
	public String getSearchRuleDescription() {
		return searchRuleDescription;
	}
	public void setSearchRuleDescription(String searchRuleDescription) {
		this.searchRuleDescription = searchRuleDescription;
	}
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}
	public String getRuleFieldXml() {
		return ruleFieldXml;
	}
	public void setRuleFieldXml(String ruleFieldXml) {
		this.ruleFieldXml = ruleFieldXml;
	}
}

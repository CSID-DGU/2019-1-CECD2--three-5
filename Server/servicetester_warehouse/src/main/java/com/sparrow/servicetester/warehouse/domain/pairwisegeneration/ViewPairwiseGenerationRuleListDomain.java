package com.sparrow.servicetester.warehouse.domain.pairwisegeneration;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewPairwiseGenerationRuleListDomain extends CommonDomain{
	private long ruleNo;
	private String ruleName;
	private String ruleDescription;
	private int generatedTestcaseCount;
	private int methodNo;
	private String ruleFieldXml;
	private String fileName;
	private String methodName;
	
	private int pageNum;
	private int pageCnt;
	private int totalCnt;
	
	private String searchRuleName;
	private String searchRuleDescription;
	private String searchGenerator;
	private String searchRevisionDateFrom;
	private String searchRevisionDateTo;
	
	private String generator;
	private String generateDate;
	private String amender;
	private String revisionDate;
	
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
	public String getRuleFieldXml() {
		return ruleFieldXml;
	}
	public void setRuleFieldXml(String ruleFieldXml) {
		this.ruleFieldXml = ruleFieldXml;
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
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
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
	public String getSearchGenerator() {
		return searchGenerator;
	}
	public void setSearchGenerator(String searchGenerator) {
		this.searchGenerator = searchGenerator;
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
}

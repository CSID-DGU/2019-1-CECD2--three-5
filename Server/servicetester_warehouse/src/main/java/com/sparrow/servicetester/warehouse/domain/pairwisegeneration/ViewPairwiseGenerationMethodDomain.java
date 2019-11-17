package com.sparrow.servicetester.warehouse.domain.pairwisegeneration;

import java.util.List;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationMethodDomain;

public class ViewPairwiseGenerationMethodDomain extends CommonDomain{

	private String methodInputXml;
	private List<ViewPairwiseGenerationMethodDomain> paramList;
	private long methodNo;
	private String methodName;
	private String executionUuid;
	private String ruleFieldXml;

	private String generator;
	private String generateDate;
	private String amender;
	private String revisionDate;
	private String folderFilePath   ;
	private String folderFileName   ;
	private int    ruleNo           ;
	private String ruleName         ;
	
	public String getMethodInputXml() {
		return methodInputXml;
	}
	public void setMethodInputXml(String methodInputXml) {
		this.methodInputXml = methodInputXml;
	}
	public List<ViewPairwiseGenerationMethodDomain> getParamList() {
		return paramList;
	}
	public void setParamList(List<ViewPairwiseGenerationMethodDomain> paramList) {
		this.paramList = paramList;
	}
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
	public String getFolderFilePath() {
		return folderFilePath;
	}
	public void setFolderFilePath(String folderFilePath) {
		this.folderFilePath = folderFilePath;
	}
	public String getFolderFileName() {
		return folderFileName;
	}
	public void setFolderFileName(String folderFileName) {
		this.folderFileName = folderFileName;
	}
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleFieldXml() {
		return ruleFieldXml;
	}
	public void setRuleFieldXml(String ruleFieldXml) {
		this.ruleFieldXml = ruleFieldXml;
	}
}

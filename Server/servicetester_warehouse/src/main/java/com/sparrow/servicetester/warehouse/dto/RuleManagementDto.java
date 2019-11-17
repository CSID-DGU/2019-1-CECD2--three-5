package com.sparrow.servicetester.warehouse.dto;

import java.util.Date;

/**
* 작성 개요 : 최영진(2019.8.21)
* 클래스 개요 : 랜덤 생성 Rest API 요청을 받기 위한 Controller 클래스
*/
public class RuleManagementDto extends CommonDto{
	private long   ruleNo                 ;
	private String ruleName               ;
	private String ruleDescription        ;
	private int    algorithmLevel         ;
	private int    generatedTestcaseCount ;
	private long   methodNo               ;
	private String ruleFieldXml           ;
	private String generator              ;
	private String amender                ;
	private Date   revisionDate           ;
	private Date   generateDate           ;

	/******* 추가 컬럼 *********/
	private String methodName             ;
	private long   file_no                ;
	private String fileName               ;



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
	public int getAlgorithmLevel() {
		return algorithmLevel;
	}
	public void setAlgorithmLevel(int algorithmLevel) {
		this.algorithmLevel = algorithmLevel;
	}
	public int getGeneratedTestcaseCount() {
		return generatedTestcaseCount;
	}
	public void setGeneratedTestcaseCount(int generatedTestcaseCount) {
		this.generatedTestcaseCount = generatedTestcaseCount;
	}
	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
	}
	public String getRuleFieldXml() {
		return ruleFieldXml;
	}
	public void setRuleFieldXml(String ruleFieldXml) {
		this.ruleFieldXml = ruleFieldXml;
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
	public Date getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public Date getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public long getFile_no() {
		return file_no;
	}
	public void setFile_no(long file_no) {
		this.file_no = file_no;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

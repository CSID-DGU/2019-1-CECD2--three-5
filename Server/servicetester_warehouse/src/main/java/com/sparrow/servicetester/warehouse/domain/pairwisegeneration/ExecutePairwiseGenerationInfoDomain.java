package com.sparrow.servicetester.warehouse.domain.pairwisegeneration;

import java.util.Date;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ExecutePairwiseGenerationInfoDomain extends CommonDomain{

	private long    ruleNo              	 ;
	private String ruleFieldXml					 ;
	private String ruleName;
	private String valueString				 ;
	private String generator                 ;
	private Date   generateDate              ;
	private String amender                   ;
	private Date   revisionDate              ;
	
	public long getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(long ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String valueString) {
		this.valueString = valueString;
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

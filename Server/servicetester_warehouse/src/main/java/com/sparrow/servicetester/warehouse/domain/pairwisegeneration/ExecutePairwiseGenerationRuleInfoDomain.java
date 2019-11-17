package com.sparrow.servicetester.warehouse.domain.pairwisegeneration;

import java.util.Date;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ExecutePairwiseGenerationRuleInfoDomain extends CommonDomain{

	private int    ruleNo               ;
	private int fieldNo;
	private String combination;
	private String generator                 ;
	private Date   generateDate              ;
	private String amender                   ;
	private Date   revisionDate              ;
	private String parameterExample          ;
	
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int l) {
		this.ruleNo = l;
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
	public String getParameterExample() {
		return parameterExample;
	}
	public void setParameterExample(String parameterExample) {
		this.parameterExample = parameterExample;
	}
	public String getCombination() {
		return combination;
	}
	public void setCombination(String combination) {
		this.combination = combination;
	}
	public int getFieldNo() {
		return fieldNo;
	}
	public void setFieldNo(int fieldNo) {
		this.fieldNo = fieldNo;
	}
}

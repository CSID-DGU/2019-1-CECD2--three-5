package com.sparrow.servicetester.warehouse.dto;

import java.util.Date;

public class PairwiseRuleDto extends CommonDto{
	private int    ruleNo					 ;
	private String combination				 ;
	private String generator                 ;
	private Date   generateDate              ;
	private String amender                   ;
	private Date   revisionDate              ;
	
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getCombination() {
		return combination;
	}
	public void setCombination(String combination) {
		this.combination = combination;
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

}

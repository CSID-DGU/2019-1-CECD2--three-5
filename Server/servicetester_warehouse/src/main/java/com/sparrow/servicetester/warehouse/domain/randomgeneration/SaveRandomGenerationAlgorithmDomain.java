/*
 * 작성일 : 2019.08.13
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class SaveRandomGenerationAlgorithmDomain extends CommonDomain{

	private String rowType;
    
	private String algorithmTypeName;
    private String algorithmReturnType;
    
    private int algorithmNo;
    
	private String generator;
    private Date generateDate;
    private String amender;
    private Date revisionDate;
    
    public String getRowType() {
		return rowType;
	}
	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	public int getAlgorithmNo() {
		return algorithmNo;
	}
	public void setAlgorithmNo(int algorithmNo) {
		this.algorithmNo = algorithmNo;
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
	public String getAlgorithmReturnType() {
		return algorithmReturnType;
	}
	public void setAlgorithmReturnType(String algorithmReturnType) {
		this.algorithmReturnType = algorithmReturnType;
	}
	public String getAlgorithmTypeName() {
		return algorithmTypeName;
	}
	public void setAlgorithmTypeName(String algorithmTypeName) {
		this.algorithmTypeName = algorithmTypeName;
	}
}

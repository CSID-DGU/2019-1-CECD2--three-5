/*
 * 작성일 : 2019.07.31
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.annotation.StSizeBytes;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class SaveRandomGenerationFieldTypeDomain extends CommonDomain{

	private String rowType;
	private int algorithmLevel;
    
	private String fieldTypeName;
    
    private String algorithmTypeName;
    
    private int algorithmNo;
    private String generator;
    private String generateDate;
    private String amender;
    private String revisionDate;
    
	public String getRowType() {
		return rowType;
	}
	public void setRowType(String rowType) {
		this.rowType = rowType;
	}
	public String getFieldTypeName() {
		return fieldTypeName;
	}
	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}
	public String getAlgorithmTypeName() {
		return algorithmTypeName;
	}
	public void setAlgorithmTypeName(String algorithmTypeName) {
		this.algorithmTypeName = algorithmTypeName;
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
	public String getAmender() {
		return amender;
	}
	public void setAmender(String amender) {
		this.amender = amender;
	}
	public int getAlgorithmLevel() {
		return algorithmLevel;
	}
	public void setAlgorithmLevel(int algorithmLevel) {
		this.algorithmLevel = algorithmLevel;
	}
	public String getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
}

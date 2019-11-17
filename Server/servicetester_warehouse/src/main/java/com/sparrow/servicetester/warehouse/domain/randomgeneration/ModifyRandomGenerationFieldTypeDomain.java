/*
 * 작성일 : 2019.08.10
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ModifyRandomGenerationFieldTypeDomain extends CommonDomain{
	
	private int algorithmLevel;
	private int fieldTypeNo;
	private int algorithmNo;
	private String fieldTypeName;
	private String algorithmTypeName;
	
	private String generator;
	private String generateDate;
	private String amender;
	private String reviseDate;
	
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
	public String getReviseDate() {
		return reviseDate;
	}
	public void setReviseDate(String reviseDate) {
		this.reviseDate = reviseDate;
	}
	public int getAlgorithmLevel() {
		return algorithmLevel;
	}
	public void setAlgorithmLevel(int algorithmLevel) {
		this.algorithmLevel = algorithmLevel;
	}
	public int getAlgorithmNo() {
		return algorithmNo;
	}
	public void setAlgorithmNo(int algorithmNo) {
		this.algorithmNo = algorithmNo;
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
	public int getFieldTypeNo() {
		return fieldTypeNo;
	}
	public void setFieldTypeNo(int fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}
}

package com.sparrow.servicetester.warehouse.dto;

import java.util.Date;

/**
* 작성 개요 : 최영진(2019.8.22)
* 클래스 개요 : 규칙 알고리즘 파라미터(st_algorithm_parameter) Dto 클래스
*/
public class RuleAlgorithmParameterDto extends CommonDto{
	private int    algorithmNo               ;
	private int    parameterNo               ;
	private String parameterName             ;
	private String parameterDescription      ;
	private String parameterType             ;
	private String parameterDefaultValue     ;
	private String generator                 ;
	private Date   generateDate              ;
	private String amender                   ;
	private Date   revisionDate              ;
	private String parameterExample          ;

	/*추가 컬럼*/
	private long   fieldTypeNo               ;

	public int getAlgorithmNo() {
		return algorithmNo;
	}

	public void setAlgorithmNo(int algorithmNo) {
		this.algorithmNo = algorithmNo;
	}

	public int getParameterNo() {
		return parameterNo;
	}

	public void setParameterNo(int parameterNo) {
		this.parameterNo = parameterNo;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterDescription() {
		return parameterDescription;
	}

	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public String getParameterDefaultValue() {
		return parameterDefaultValue;
	}

	public void setParameterDefaultValue(String parameterDefaultValue) {
		this.parameterDefaultValue = parameterDefaultValue;
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

	public long getFieldTypeNo() {
		return fieldTypeNo;
	}

	public void setFieldTypeNo(long fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}




}

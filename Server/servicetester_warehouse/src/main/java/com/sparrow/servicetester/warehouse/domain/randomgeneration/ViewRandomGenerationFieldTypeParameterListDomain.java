package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewRandomGenerationFieldTypeParameterListDomain extends CommonDomain{
	@StNotEmptyInt(inputZero=false)
	private int pageCnt;

	@StNotEmptyInt(inputZero=false)
    private int pageNum;
	private int totalCnt;
	private String executionUuid;
	private int parameterNo;
	private String fieldTypeName;
	private int fieldTypeLevel;
	private String algorithmReturnType;
	private String parameterName;
    private String parameterDescription;
	private String parameterType;
	private String parameterExample;
	private String parameterDefaultValue;
	private int fieldTypeNo;
	
    private String generator;
    private String generateDate;
    private String amender;
    private Date revisionDate;
    
    private String isTotalCnt;

	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
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
	public String getIsTotalCnt() {
		return isTotalCnt;
	}
	public void setIsTotalCnt(String isTotalCnt) {
		this.isTotalCnt = isTotalCnt;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public int getParameterNo() {
		return parameterNo;
	}
	public void setParameterNo(int parameterNo) {
		this.parameterNo = parameterNo;
	}
	public String getGenerateDate() {
		return generateDate;
	}
	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}
	public String getAlgorithmReturnType() {
		return algorithmReturnType;
	}
	public void setAlgorithmReturnType(String algorithmReturnType) {
		this.algorithmReturnType = algorithmReturnType;
	}
	public String getParameterDefaultValue() {
		return parameterDefaultValue;
	}
	public void setParameterDefaultValue(String parameterDefaultValue) {
		this.parameterDefaultValue = parameterDefaultValue;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getParameterDescription() {
		return parameterDescription;
	}
	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}
	public String getParameterExample() {
		return parameterExample;
	}
	public void setParameterExample(String parameterExample) {
		this.parameterExample = parameterExample;
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public int getFieldTypeNo() {
		return fieldTypeNo;
	}
	public void setFieldTypeNo(int fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}
	public String getFieldTypeName() {
		return fieldTypeName;
	}
	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}
	public int getFieldTypeLevel() {
		return fieldTypeLevel;
	}
	public void setFieldTypeLevel(int fieldTypeLevel) {
		this.fieldTypeLevel = fieldTypeLevel;
	}
}

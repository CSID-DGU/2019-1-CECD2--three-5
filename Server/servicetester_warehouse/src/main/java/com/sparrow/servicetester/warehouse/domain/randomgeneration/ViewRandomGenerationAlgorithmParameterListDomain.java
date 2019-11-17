package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.annotation.StSizeBytes;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewRandomGenerationAlgorithmParameterListDomain extends CommonDomain{
	@StNotEmptyInt(inputZero=false)
	private int pageCnt;

	@StNotEmptyInt(inputZero=false)
    private int pageNum;
	private int totalCnt;
	private String executionUuid;
	private int parameterNo;
	private String algorithmTypeName;
	private String algorithmReturnType;
	private String parameterName;
    private String parameterDescription;
	private String parameterType;
	private String parameterExample;
	private String parameterDefaultValue;
	private int algorithmNo;
	
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
	public String getAlgorithmTypeName() {
		return algorithmTypeName;
	}
	public void setAlgorithmTypeName(String algorithmTypeName) {
		this.algorithmTypeName = algorithmTypeName;
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
	public int getAlgorithmNo() {
		return algorithmNo;
	}
	public void setAlgorithmNo(int algorithmNo) {
		this.algorithmNo = algorithmNo;
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

}

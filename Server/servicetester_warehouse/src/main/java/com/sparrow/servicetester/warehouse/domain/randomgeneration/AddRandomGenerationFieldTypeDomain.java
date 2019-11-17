/*
 * 작성일 : 2019.08.01
 * 작성자 : 이장행
 * 랜덤 필드 유형 등록, 랜덤 알고리즘 등록 화면 총합 domain
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.annotation.StSizeBytes;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class AddRandomGenerationFieldTypeDomain extends CommonDomain{
	
	private int pageCnt;
    private int pageNum;
	
	private int algorithmLevel;
	private int fieldTypeNo;
    
	private String fieldTypeName;
    
    @StSizeBytes(max=1000, min=0, message = "" )
    private String algorithmTypeName;
    
    private int algorithmNo;
    private String returnType;
    private String generator;
    private String generateDate;
    private String amender;
    private Date revisionDate;
    
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
	public Date getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(Date revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
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
	public int getAlgorithmLevel() {
		return algorithmLevel;
	}
	public void setAlgorithmLevel(int algorithmLevel) {
		this.algorithmLevel = algorithmLevel;
	}
	public int getFieldTypeNo() {
		return fieldTypeNo;
	}
	public void setFieldTypeNo(int fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}


}

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.annotation.StSizeBytes;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewRandomGenerationFieldTypeListDomain extends CommonDomain{

	@StNotEmptyInt(inputZero=false)
	private int pageCnt;

	@StNotEmptyInt(inputZero=false)
    private int pageNum;

	private int algorithmLevel;
    
	private String fieldTypeName;   
    private String algorithmTypeName;  
    private int fieldTypeNo;
    private int algorithmNo;
    
    private String generator;
    private String generateDate;
    private String amender;
    private String revisionDate;

	private int totalCnt;

    //검색 관련 (레벨에 따른 검색, 유형 API에 따른 검색, 필드 유형 이름에 대한 검색으로 나눌까 고려중)
    private String searchGenerateDateFrom;
    private String searchGenerateDateTo;
    private String searchGenerator;
    private String searchAlgorithmLevel;
    private String searchAlgorithmTypeName;
    private String searchFieldTypeName;

	public String getAlgorithmTypeName() {
		return algorithmTypeName;
	}
	public void setAlgorithmTypeName(String algorithmTypeName) {
		this.algorithmTypeName = algorithmTypeName;
	}
	public String getFieldTypeName() {
		return fieldTypeName;
	}
	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
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
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public String getSearchAlgorithmTypeName() {
		return searchAlgorithmTypeName;
	}
	public void setSearchAlgorithmTypeName(String searchAlgorithmTypeName) {
		this.searchAlgorithmTypeName = searchAlgorithmTypeName;
	}
	public String getSearchFieldTypeName() {
		return searchFieldTypeName;
	}
	public void setSearchFieldTypeName(String searchFieldTypeName) {
		this.searchFieldTypeName = searchFieldTypeName;
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
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}
	public String getSearchGenerateDateFrom() {
		return searchGenerateDateFrom;
	}
	public void setSearchGenerateDateFrom(String searchGenerateDateFrom) {
		this.searchGenerateDateFrom = searchGenerateDateFrom;
	}
	public String getSearchGenerateDateTo() {
		return searchGenerateDateTo;
	}
	public void setSearchGenerateDateTo(String searchGenerateDateTo) {
		this.searchGenerateDateTo = searchGenerateDateTo;
	}
	public String getSearchGenerator() {
		return searchGenerator;
	}
	public void setSearchGenerator(String searchGenerator) {
		this.searchGenerator = searchGenerator;
	}
	public int getAlgorithmLevel() {
		return algorithmLevel;
	}
	public void setAlgorithmLevel(int algorithmLevel) {
		this.algorithmLevel = algorithmLevel;
	}
	public String getSearchAlgorithmLevel() {
		return searchAlgorithmLevel;
	}
	public void setSearchAlgorithmLevel(String searchAlgorithmLevel) {
		this.searchAlgorithmLevel = searchAlgorithmLevel;
	}
	public int getFieldTypeNo() {
		return fieldTypeNo;
	}
	public void setFieldTypeNo(int fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}
	public int getAlgorithmNo() {
		return algorithmNo;
	}
	public void setAlgorithmNo(int algorithmNo) {
		this.algorithmNo = algorithmNo;
	}
}

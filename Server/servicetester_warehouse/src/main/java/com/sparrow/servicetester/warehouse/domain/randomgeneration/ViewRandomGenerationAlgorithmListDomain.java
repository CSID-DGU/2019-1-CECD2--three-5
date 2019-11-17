package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ViewRandomGenerationAlgorithmListDomain extends CommonDomain{
	private int pageNum;
	private int pageCnt;
	private int totalCnt;
	
	private int algorithmNo;
	private String algorithmTypeName;
	private String algorithmReturnType;
	
	private String searchAlgorithmTypeName;
	private String searchAlgorithmReturnType;
	private String searchGenerateDateFrom;
	private String searchGenerateDateTo;
	
	private String generator;
	private String generateDate;
	private String amender;
	private String reviseDate;

	public String getAlgorithmTypeName() {
		return algorithmTypeName;
	}
	public void setAlgorithmTypeName(String algorithmTypeName) {
		this.algorithmTypeName = algorithmTypeName;
	}
	public String getSearchAlgorithmTypeName() {
		return searchAlgorithmTypeName;
	}
	public void setSearchAlgorithmTypeName(String searchAlgorithmTypeName) {
		this.searchAlgorithmTypeName = searchAlgorithmTypeName;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public void setPageCnt(int pageCnt) {
		this.pageCnt = pageCnt;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
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
	public String getSearchAlgorithmReturnType() {
		return searchAlgorithmReturnType;
	}
	public void setSearchAlgorithmReturnType(String searchAlgorithmReturnType) {
		this.searchAlgorithmReturnType = searchAlgorithmReturnType;
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
}

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class AddRandomGenerationAlgorithmDomain extends CommonDomain{
    
	private int algorithmNo;
	private String algorithmTypeName;
	private String algorithmReturnType;
	
	private String generator;
    private String generateDate;
    private String amender;
    private String revisionDate;
    
    private int totalCnt;
    private int pageCnt;
    private int pageNum;
    
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
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public int getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
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
	public String getAlgorithmTypeName() {
		return algorithmTypeName;
	}
	public void setAlgorithmTypeName(String algorithmTypeName) {
		this.algorithmTypeName = algorithmTypeName;
	}
	public String getAlgorithmReturnType() {
		return algorithmReturnType;
	}
	public void setAlgorithmReturnType(String algorithmReturnType) {
		this.algorithmReturnType = algorithmReturnType;
	}
}

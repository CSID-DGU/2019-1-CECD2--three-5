/*
 * 작성일 : 2019.08.13
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class ModifyRandomGenerationAlgorithmDomain extends CommonDomain{
	
	private int algorithmNo;
	private String algorithmTypeName;
	private String algorithmReturnType;
	
	private String generator;
    private String generateDate;
    private String amender;
    private String revisionDate;
    
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

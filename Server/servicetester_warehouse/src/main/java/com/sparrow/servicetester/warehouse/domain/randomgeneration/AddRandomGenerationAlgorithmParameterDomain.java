package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.sparrow.servicetester.warehouse.annotation.StSizeBytes;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class AddRandomGenerationAlgorithmParameterDomain extends CommonDomain{
	private String inputParameterName;
	
    @NotEmpty(message = "" )
    @StSizeBytes(max=100, min=0, message = "" )
	@Pattern(regexp="^[ㄱ-ㅎㅏ-ㅣ가-힣0-9a-zA-Z-_]*$", message="")
	private String description;
	private String typeDivision;
	private String valueExample;
	private String defaultValue;
    private String generator;
    private Date generateDate;
    private String amender;
    private Date revisionDate;
    
	public String getInputParameterName() {
		return inputParameterName;
	}
	public void setInputParameterName(String inputParameterName) {
		this.inputParameterName = inputParameterName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTypeDivision() {
		return typeDivision;
	}
	public void setTypeDivision(String typeDivision) {
		this.typeDivision = typeDivision;
	}
	public String getValueExample() {
		return valueExample;
	}
	public void setValueExample(String valueExample) {
		this.valueExample = valueExample;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
}
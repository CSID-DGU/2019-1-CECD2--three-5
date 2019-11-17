package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class SourceMethodDomain extends CommonDomain{

	private String domainXmlContent;
	private long methodNo;
	private String domainName;
	private String executionUuid;
	private long domainNo;

	private String generator;
	private String generateDate;
	private String amender;
	private String revisionDate;

	public String getDomainXmlContent() {
		return domainXmlContent;
	}
	public void setDomainXmlContent(String domainXmlContent) {
		this.domainXmlContent = domainXmlContent;
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
	public long getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(long methodNo) {
		this.methodNo = methodNo;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public long getDomainNo() {
		return domainNo;
	}
	public void setDomainNo(long domainNo) {
		this.domainNo = domainNo;
	}
}

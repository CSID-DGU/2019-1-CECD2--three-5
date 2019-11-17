/*
 * 작성일 : 2019.08.11
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.dto;

public class RandomGenerationAlgorithmDto extends CommonDto{
	private String executionUuid;
	private int algorithmLevel;
	private int fieldTypeLevel;
	private int algorithmNo;
	private int fieldTypeNo;
	private int ruleNo;
	private int parameterNo;
    private String algorithmTypeName; //유형 API
    private String fieldTypeName; //알고리즘 이름
	private String algorithmReturnType;
	private String parameterName;
    private String parameterDescription;
	private String parameterType;
	private String parameterExample;
	private String parameterDefaultValue;
	
	private String ruleName;
	private String ruleDescription;
	
    private String generator;
    private String generateDate;
    private String amender;
    private String revisionDate;
    
    private String folderFileName;
    private String methodName;
    private int generatedTestcaseCount;

    private int methodNo;
    
    //쿼리 추가 컬럼
    private String generatorId;
    private String generateDateStr;

    //페이징 관련
    private int totalCnt;	//총 목록 갯수
    private int pageCnt;	//호출 페이지 번호
    private int pageNum;	//페이지당 목록 갯수

    //검색 관련
    private String searchGenerateDateFrom;
    private String searchGenerateDateTo;
    private String searchRevisionDateFrom;
    private String searchRevisionDateTo;
    private String searchGenerator;
    private String searchRuleLevel;
    private String searchAlgorithmLevel;
    private String searchAlgorithmTypeName;
    private String searchFieldTypeName;
    private String searchRuleName;
    private String searchAlgorithmReturnType;
    private String searchRuleDescription;

    private String searchDescription;
      
	public int getAlgorithmNo() {
		return algorithmNo;
	}
	public void setAlgorithmNo(int algorithmNo) {
		this.algorithmNo = algorithmNo;
	}
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
	public String getGeneratorId() {
		return generatorId;
	}
	public void setGeneratorId(String generatorId) {
		this.generatorId = generatorId;
	}
	public String getGenerateDateStr() {
		return generateDateStr;
	}
	public void setGenerateDateStr(String generateDateStr) {
		this.generateDateStr = generateDateStr;
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
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public String getSearchGenerator() {
		return searchGenerator;
	}
	public void setSearchGenerator(String searchGenerator) {
		this.searchGenerator = searchGenerator;
	}
	public String getSearchDescription() {
		return searchDescription;
	}
	public void setSearchDescription(String searchDescription) {
		this.searchDescription = searchDescription;
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
	public String getSearchAlgorithmReturnType() {
		return searchAlgorithmReturnType;
	}
	public void setSearchAlgorithmReturnType(String searchAlgorithmReturnType) {
		this.searchAlgorithmReturnType = searchAlgorithmReturnType;
	}
	public String getAlgorithmReturnType() {
		return algorithmReturnType;
	}
	public void setAlgorithmReturnType(String algorithmReturnType) {
		this.algorithmReturnType = algorithmReturnType;
	}
	public String getRevisionDate() {
		return revisionDate;
	}
	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}
	public int getParameterNo() {
		return parameterNo;
	}
	public void setParameterNo(int parameterNo) {
		this.parameterNo = parameterNo;
	}
	public String getFolderFileName() {
		return folderFileName;
	}
	public void setFolderFileName(String folderFileName) {
		this.folderFileName = folderFileName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public int getGeneratedTestcaseCount() {
		return generatedTestcaseCount;
	}
	public void setGeneratedTestcaseCount(int generatedTestcaseCount) {
		this.generatedTestcaseCount = generatedTestcaseCount;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}
	public String getSearchRuleName() {
		return searchRuleName;
	}
	public void setSearchRuleName(String searchRuleName) {
		this.searchRuleName = searchRuleName;
	}
	public String getSearchRuleDescription() {
		return searchRuleDescription;
	}
	public void setSearchRuleDescription(String searchRuleDescription) {
		this.searchRuleDescription = searchRuleDescription;
	}
	public String getSearchRuleLevel() {
		return searchRuleLevel;
	}
	public void setSearchRuleLevel(String searchRuleLevel) {
		this.searchRuleLevel = searchRuleLevel;
	}
	public int getMethodNo() {
		return methodNo;
	}
	public void setMethodNo(int methodNo) {
		this.methodNo = methodNo;
	}
	public String getSearchRevisionDateFrom() {
		return searchRevisionDateFrom;
	}
	public void setSearchRevisionDateFrom(String searchRevisionDateFrom) {
		this.searchRevisionDateFrom = searchRevisionDateFrom;
	}
	public String getSearchRevisionDateTo() {
		return searchRevisionDateTo;
	}
	public void setSearchRevisionDateTo(String searchRevisionDateTo) {
		this.searchRevisionDateTo = searchRevisionDateTo;
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
	public int getFieldTypeNo() {
		return fieldTypeNo;
	}
	public void setFieldTypeNo(int fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}
	public int getFieldTypeLevel() {
		return fieldTypeLevel;
	}
	public void setFieldTypeLevel(int fieldTypeLevel) {
		this.fieldTypeLevel = fieldTypeLevel;
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

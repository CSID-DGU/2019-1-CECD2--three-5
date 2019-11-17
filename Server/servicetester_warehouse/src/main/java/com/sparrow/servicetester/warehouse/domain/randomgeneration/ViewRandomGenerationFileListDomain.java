/*
 * 작성일 : 2019.07.18
 * 작성자 : 이장행
 * 랜덤 생성 생성 이력, 테스트케이스 목록에 관한 Domain
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;

public class ViewRandomGenerationFileListDomain {
	@NotEmpty(message = "" )
	private String executionUuid;

	private int rowSeqNo;
	private String executorId;
	private String executorName;
	private String generateDateStr;
	private String revisionDateStr;
	private String filePath;
	private String fileName;
	private String testcaseGroupName;
	private String testcaseName;

	//검색 관련
	private String searchFileName;
	private String searchFilePath;

	//페이징 관련
	@StNotEmptyInt(inputZero=false)
	private int pageCnt;
	@StNotEmptyInt(inputZero=false)
	private int pageNum;

	
	public String getTestcaseGroupName() {
		return testcaseGroupName;
	}
	public void setTestcaseGroupName(String testcaseGroupName) {
		this.testcaseGroupName = testcaseGroupName;
	}public String getTestcaseName() {
		return testcaseName;
	}
	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}
	public String getExecutionUuid() {
		return executionUuid;
	}
	public void setExecutionUuid(String executionUuid) {
		this.executionUuid = executionUuid;
	}
	public String getExecutorId() {
		return executorId;
	}
	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}
	public String getExecutorName() {
		return executorName;
	}
	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}
	public String getGenerateDateStr() {
		return generateDateStr;
	}
	public void setGenerateDateStr(String generateDateStr) {
		this.generateDateStr = generateDateStr;
	}
	public String getRevisionDateStr() {
		return revisionDateStr;
	}
	public void setRevisionDateStr(String revisionDateStr) {
		this.revisionDateStr = revisionDateStr;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getSearchFileName() {
		return searchFileName;
	}
	public void setSearchFileName(String searchFileName) {
		this.searchFileName = searchFileName;
	}
	public int getRowSeqNo() {
		return rowSeqNo;
	}
	public void setRowSeqNo(int rowSeqNo) {
		this.rowSeqNo = rowSeqNo;
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
	public String getSearchFilePath() {
		return searchFilePath;
	}
	public void setSearchFilePath(String searchFilePath) {
		this.searchFilePath = searchFilePath;
	}
}

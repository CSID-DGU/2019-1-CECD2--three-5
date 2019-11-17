/*
 * 작성일 : 2019.07.31
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class SaveRandomGenerationFieldTypeListDomain extends CommonDomain{
	@NotEmpty(message = "")
	private List<SaveRandomGenerationFieldTypeDomain> saveList;

	public List<SaveRandomGenerationFieldTypeDomain> getSaveList() {
		return saveList;
	}

	public void setSaveList(List<SaveRandomGenerationFieldTypeDomain> saveList) {
		this.saveList = saveList;
	}
}

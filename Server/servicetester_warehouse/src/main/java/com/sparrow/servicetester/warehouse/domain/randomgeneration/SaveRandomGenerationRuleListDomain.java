/*
 * 작성일 : 2019.08.13
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class SaveRandomGenerationRuleListDomain extends CommonDomain{
	@NotEmpty(message = "")
	private List<SaveRandomGenerationRuleDomain> saveList;

	public List<SaveRandomGenerationRuleDomain> getSaveList() {
		return saveList;
	}
	public void setSaveList(List<SaveRandomGenerationRuleDomain> saveList) {
		this.saveList = saveList;
	}
}

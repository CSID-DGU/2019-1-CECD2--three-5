package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class SaveRandomGenerationAlgorithmListDomain extends CommonDomain{
	@NotEmpty(message = "")
	private List<SaveRandomGenerationAlgorithmDomain> saveList;

	public List<SaveRandomGenerationAlgorithmDomain> getSaveList() {
		return saveList;
	}

	public void setSaveList(List<SaveRandomGenerationAlgorithmDomain> saveList) {
		this.saveList = saveList;
	}
}

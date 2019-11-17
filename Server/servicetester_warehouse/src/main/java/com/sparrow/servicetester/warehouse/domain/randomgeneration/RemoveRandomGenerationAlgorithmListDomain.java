package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class RemoveRandomGenerationAlgorithmListDomain extends CommonDomain{
	@NotEmpty(message = "")
	private List<RemoveRandomGenerationAlgorithmDomain> removeList;

	public List<RemoveRandomGenerationAlgorithmDomain> getRemoveList() {
		return removeList;
	}

	public void setRemoveList(List<RemoveRandomGenerationAlgorithmDomain> removeList) {
		this.removeList = removeList;
	}
}

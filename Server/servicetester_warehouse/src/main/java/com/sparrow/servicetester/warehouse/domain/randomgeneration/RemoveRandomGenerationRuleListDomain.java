package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class RemoveRandomGenerationRuleListDomain extends CommonDomain{
	@NotEmpty(message = "")
	private List<RemoveRandomGenerationRuleDomain> removeList;

	public List<RemoveRandomGenerationRuleDomain> getRemoveList() {
		return removeList;
	}

	public void setRemoveList(List<RemoveRandomGenerationRuleDomain> removeList) {
		this.removeList = removeList;
	}
}

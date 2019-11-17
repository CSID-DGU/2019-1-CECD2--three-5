/*
 * 작성일 : 2019.07.31
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class RemoveRandomGenerationFieldTypeListDomain extends CommonDomain{
	@NotEmpty(message = "")
	private List<RemoveRandomGenerationFieldTypeDomain> removeList;

	public List<RemoveRandomGenerationFieldTypeDomain> getRemoveList() {
		return removeList;
	}

	public void setRemoveList(List<RemoveRandomGenerationFieldTypeDomain> removeList) {
		this.removeList = removeList;
	}
}

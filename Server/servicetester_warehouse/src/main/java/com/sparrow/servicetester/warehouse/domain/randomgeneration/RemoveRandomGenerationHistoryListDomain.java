/*
 * 작성일 : 2019.07.19
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class RemoveRandomGenerationHistoryListDomain extends CommonDomain{
	private List<RemoveRandomGenerationHistoryDomain> removeList;

	public List<RemoveRandomGenerationHistoryDomain> getRemoveList() {
		return removeList;
	}

	public void setRemoveList(List<RemoveRandomGenerationHistoryDomain> removeList) {
		this.removeList = removeList;
	}
}

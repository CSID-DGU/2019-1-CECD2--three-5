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

public class RemoveRandomGenerationFileListDomain extends CommonDomain{
	@NotEmpty(message = "")
	private List<@Valid RemoveRandomGenerationFileDomain> removeList;

	@NotEmpty(message = "")
	private String uuid;

	public List<RemoveRandomGenerationFileDomain> getRemoveList() {
		return removeList;
	}

	public void setRemoveList(List<RemoveRandomGenerationFileDomain> removeList) {
		this.removeList = removeList;
	}

	public String getuUuid() {
		return uuid;
	}

	public void setuUuid(String uuid) {
		this.uuid = uuid;
	}

}

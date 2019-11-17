/*
 * 작성일 : 2019.08.01
 * 작성자 : 이장행
 * 
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class RemoveRandomGenerationFieldTypeDomain extends CommonDomain{
	@StNotEmptyInt(inputZero=false)
	private int fieldTypeNo;

	public int getFieldTypeNo() {
		return fieldTypeNo;
	}

	public void setFieldTypeNo(int fieldTypeNo) {
		this.fieldTypeNo = fieldTypeNo;
	}
}

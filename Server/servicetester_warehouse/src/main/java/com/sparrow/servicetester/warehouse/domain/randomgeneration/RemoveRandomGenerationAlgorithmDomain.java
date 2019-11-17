package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class RemoveRandomGenerationAlgorithmDomain extends CommonDomain{
	@StNotEmptyInt(inputZero=false)
	private int algorithmNo;

	public int getAlgorithmNo() {
		return algorithmNo;
	}
	public void setAlgorithmNo(int algorithmNo) {
		this.algorithmNo = algorithmNo;
	}
}

/*
 * 작성일 : 2019.08.13
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.domain.randomgeneration;

import com.sparrow.servicetester.warehouse.annotation.StNotEmptyInt;
import com.sparrow.servicetester.warehouse.domain.CommonDomain;

public class RemoveRandomGenerationRuleDomain extends CommonDomain{
	@StNotEmptyInt(inputZero=false)
	private int ruleNo;

	public int getRuleNo() {
		return ruleNo;
	}
	public void setRuleNo(int ruleNo) {
		this.ruleNo = ruleNo;
	}
}

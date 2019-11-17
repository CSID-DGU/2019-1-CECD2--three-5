/*
 * 작성자 : 이장행
 */

package com.sparrow.servicetester.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sparrow.servicetester.warehouse.dto.RandomGenerationAlgorithmDto;
import com.sparrow.servicetester.warehouse.dto.RuleAlgorithmParameterDto;
import com.sparrow.servicetester.warehouse.dto.RuleManagementDto;

@Mapper
public interface RandomGenerationMngAlgorithmMapper {

	RandomGenerationAlgorithmDto selectRandomGenerationFieldType(RandomGenerationAlgorithmDto schDto);

	RandomGenerationAlgorithmDto selectRandomGenerationFieldTypeListCnt(RandomGenerationAlgorithmDto rgaDto);

	List<RandomGenerationAlgorithmDto> selectRandomGenerationFieldTypeList(RandomGenerationAlgorithmDto rgaDto);

	int deleteRandomGenerationFieldType(RandomGenerationAlgorithmDto algorithmDto);

	int insertRandomGenerationFieldType(RandomGenerationAlgorithmDto algorithmDto);

	int updateRandomGenerationFieldType(RandomGenerationAlgorithmDto algorithmDto);



	RandomGenerationAlgorithmDto selectRandomGenerationAlgorithmParameterListCnt(RandomGenerationAlgorithmDto rgaDto);

	List<RandomGenerationAlgorithmDto> selectRandomGenerationAlgorithmParameterList(RandomGenerationAlgorithmDto rgaDto);



	RandomGenerationAlgorithmDto selectRandomGenerationAlgorithmListCnt(RandomGenerationAlgorithmDto rgaDto);

	List<RandomGenerationAlgorithmDto> selectRandomGenerationAlgorithmList(RandomGenerationAlgorithmDto rgaDto);

	RandomGenerationAlgorithmDto selectRandomGenerationAlgorithm(RandomGenerationAlgorithmDto rgaDto);

	int insertRandomGenerationAlgorithm(RandomGenerationAlgorithmDto algorithmDto);

	int updateRandomGenerationAlgorithm(RandomGenerationAlgorithmDto algorithmDto);

	int deleteRandomGenerationAlgorithm(RandomGenerationAlgorithmDto algorithmDto);



	RandomGenerationAlgorithmDto selectRandomGenerationRuleListCnt(RandomGenerationAlgorithmDto rghDto);

	List<RandomGenerationAlgorithmDto> selectRandomGenerationRuleList(RandomGenerationAlgorithmDto rgaDto);

	RandomGenerationAlgorithmDto selectRandomGenerationRule(RandomGenerationAlgorithmDto rgaDto);

	int insertRandomGenerationRule(RandomGenerationAlgorithmDto algorithmDto);

	int updateRandomGenerationRule(RandomGenerationAlgorithmDto algorithmDto);

	int deleteRandomGenerationRule(RandomGenerationAlgorithmDto algorithmDto);

	RandomGenerationAlgorithmDto selectRandomGenerationFieldTypeParameterListCnt(RandomGenerationAlgorithmDto rghDto);

	List<RandomGenerationAlgorithmDto> selectRandomGenerationFieldTypeParameterList(RandomGenerationAlgorithmDto rghDto);

	RuleManagementDto selectRandomGenerationRuleDetail(RuleManagementDto rgaDto);

}

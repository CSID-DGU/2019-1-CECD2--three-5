package com.sparrow.servicetester.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sparrow.servicetester.warehouse.dto.PairwiseGenerationHistoryDto;
import com.sparrow.servicetester.warehouse.dto.PairwiseGenerationRuleDto;
import com.sparrow.servicetester.warehouse.dto.PairwiseGenerationTestcaseListDto;
import com.sparrow.servicetester.warehouse.dto.PairwiseRuleDto;
import com.sparrow.servicetester.warehouse.dto.SourceDomainDto;
import com.sparrow.servicetester.warehouse.dto.SourceMethodDto;

@Mapper
public interface PairwiseGenerationMapper {
	/***
	 * 조합 생성 이력 목록 조회 총 갯수
	 * @param rghDto
	 * @return
	 */
	PairwiseGenerationHistoryDto selectPairwiseGenerationHistoryListCnt(PairwiseGenerationHistoryDto rghDto);

	/***
	 * 조합 생성 이력 목록 조회
	 * @param rghDto
	 * @return
	 */
	List<PairwiseGenerationHistoryDto> selectPairwiseGenerationHistoryList(PairwiseGenerationHistoryDto rghDto);
	
	/**
	 * 조합 생성 테스트케이스 이름 조회
	 * @param tcDto
	 * @return
	 */
	PairwiseGenerationTestcaseListDto selectTestcaseName(PairwiseGenerationTestcaseListDto tcDto);

	/**
	 * 조합 생성 테스트케이스 그룹 이름 조회
	 * @param tcDto
	 * @return
	 */
	PairwiseGenerationTestcaseListDto selectTestcaseGroupName(PairwiseGenerationTestcaseListDto tcDto);

	/**
	 * 조합 생성 테스트케이스 규칙 목록 조회 총 개수
	 * @param rghDto
	 * @return
	 */
	PairwiseGenerationRuleDto selectPairwiseGenerationRuleListCnt(PairwiseGenerationRuleDto rghDto);

	/**
	 * 조합 생성 테스트케이스 규칙 목록 조회
	 * @param rgaDto
	 * @return
	 */
	List<PairwiseGenerationRuleDto> selectPairwiseGenerationRuleList(PairwiseGenerationRuleDto rgaDto);

	SourceMethodDto selectSourceMethodDomain(SourceMethodDto schDto);

	PairwiseGenerationRuleDto selectPairwiseListByRuleNo(PairwiseGenerationRuleDto pgrDto);
}

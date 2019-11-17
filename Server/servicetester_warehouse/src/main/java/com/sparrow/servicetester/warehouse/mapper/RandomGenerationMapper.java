/*
 * 작성일 : 2019.07.16
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sparrow.servicetester.warehouse.dto.ExecuteRandomGenerationReflectionDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationHistoryDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationTestcaseListDto;
import com.sparrow.servicetester.warehouse.dto.RuleAlgorithmParameterDto;
import com.sparrow.servicetester.warehouse.dto.SourceDomainDto;
import com.sparrow.servicetester.warehouse.dto.SourceMethodDto;

@Mapper
public interface RandomGenerationMapper {
	/***
	 * 랜덤 생성 이력 목록 조회 총 갯수
	 * @param rghDto
	 * @return
	 */
	RandomGenerationHistoryDto selectRandomGenerationHistoryListCnt(RandomGenerationHistoryDto rghDto);

	/***
	 * 랜덤 생성 이력 삭제
	 * @param rghDto
	 * @return
	 */
	int deleteRandomGenerationHistory(RandomGenerationHistoryDto rghDto);

	/***
	 * 랜덤 생성 이력 목록 조회
	 * @param rghDto
	 * @return
	 */
	List<RandomGenerationHistoryDto> selectRandomGenerationHistoryList(RandomGenerationHistoryDto rghDto);



	/***
	 * 랜덤 생성 실행 소스 도메인 조회
	 * @param rgtDto
	 * @return
	 */
	SourceDomainDto selectSourceDetailDomain(SourceDomainDto schDto);



	SourceMethodDto selectSourceMethodDomain(SourceMethodDto schDto);

	/***
	 * 랜덤 생성 실행 관련
	 * @param randomGenerationDto
	 * @return
	 */
	RandomGenerationDto selectRandomGenerationExecution(RandomGenerationDto dto);

	/***
	 * 랜덤 생성 이력 반영 수정
	 * @param searchDto
	 * @return
	 */
	ExecuteRandomGenerationReflectionDto updateRandomGenerationReflection(ExecuteRandomGenerationReflectionDto rgDto);

	
	/***
	 * 랜덤 생성 반영 xml 경로 확인
	 */
	ExecuteRandomGenerationReflectionDto selectRandomGenerationReflectionPath(ExecuteRandomGenerationReflectionDto dto);

	RandomGenerationTestcaseListDto selectTestcaseGroupName(RandomGenerationTestcaseListDto tcDto);

	RandomGenerationTestcaseListDto selectTestcaseName(RandomGenerationTestcaseListDto tcDto);


























	/**
	 * 규칙 알고리즘 파라미터 필드 유형 번호 조회
	 * @param rgaDto
	 * @return
	 */
	List<RuleAlgorithmParameterDto> selectRuleAlgorithmParameterByFieldTypeNo(RuleAlgorithmParameterDto rgaDto);


}

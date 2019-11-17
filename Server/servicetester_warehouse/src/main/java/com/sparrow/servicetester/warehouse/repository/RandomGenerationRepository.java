/*
 * 작성일 : 2019.07.16
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sparrow.servicetester.warehouse.controller.RandomGenerationController;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationAlgorithmInfoDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationReflectionDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationHistoryDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SourceMethodDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationMethodDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseListDomain;
import com.sparrow.servicetester.warehouse.domain.servicerun.ViewServiceHistorySearchListDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.SourceDomainDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.ViewTestcaseDomain;
import com.sparrow.servicetester.warehouse.dto.ExecuteRandomGenerationReflectionDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationAlgorithmDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationHistoryDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationTestcaseListDto;
import com.sparrow.servicetester.warehouse.dto.RuleAlgorithmParameterDto;
import com.sparrow.servicetester.warehouse.dto.ServiceRunDto;
import com.sparrow.servicetester.warehouse.dto.SourceDomainDto;
import com.sparrow.servicetester.warehouse.dto.SourceMethodDto;
import com.sparrow.servicetester.warehouse.dto.TestcaseDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationTestcaseDto;
import com.sparrow.servicetester.warehouse.mapper.RandomGenerationMapper;

@Repository
public class RandomGenerationRepository {
	@Inject
	private RandomGenerationMapper randomGenerationMapper;
	static final Logger logger = LoggerFactory.getLogger(RandomGenerationRepository.class);

	/***
	 * 랜덤 생성 이력 목록 조회 총 갯수
	 *
	 * @param rghDomain
	 * @return
	 */
	public ViewRandomGenerationHistoryListDomain selectRandomGenerationHistoryListCnt(ViewRandomGenerationHistoryListDomain rghDomain) {
		RandomGenerationHistoryDto rghDto = new RandomGenerationHistoryDto();

		rghDto.setExecutionUuid(rghDomain.getExecutionUuid());
		rghDto.setExecutionProjectNo(rghDomain.getExecutionProjectNo());
		rghDto.setExecutionDivision(rghDomain.getExecutionDivision());

		rghDto.setMethodNo(rghDomain.getMethodNo());
		rghDto.setSearchGenerateDateFrom(rghDomain.getSearchGenerateDateFrom());
		rghDto.setSearchGenerateDateTo(rghDomain.getSearchGenerateDateTo());
		rghDto.setSearchGenerator(rghDomain.getSearchGenerator());
		rghDto.setSearchGenerateRuleName(rghDomain.getSearchGenerateRuleName());

		RandomGenerationHistoryDto rstTotalCnt = randomGenerationMapper.selectRandomGenerationHistoryListCnt(rghDto);

		ViewRandomGenerationHistoryListDomain returnDomain = new ViewRandomGenerationHistoryListDomain();

		returnDomain.setTotalCnt(rstTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/***
	 * 랜덤 생성 이력 목록 조회
	 *
	 * @param schDomain
	 * @return
	 */
	public List<ViewRandomGenerationHistoryListDomain> selectRandomGenerationHistoryList(ViewRandomGenerationHistoryListDomain rghDomain) {
		List<ViewRandomGenerationHistoryListDomain> returnList = null;

		RandomGenerationHistoryDto rghDto = new RandomGenerationHistoryDto();

		rghDto.setPageCnt(rghDomain.getPageCnt());
		rghDto.setPageNum(rghDomain.getPageNum());
		rghDto.setTotalCnt(rghDomain.getTotalCnt());
		rghDto.setExecutionUuid(rghDomain.getExecutionUuid());
		rghDto.setExecutionProjectNo(rghDomain.getExecutionProjectNo());
		rghDto.setExecutionDivision(rghDomain.getExecutionDivision());
		rghDto.setMethodNo(rghDomain.getMethodNo());
		rghDto.setSearchGenerateDateFrom(rghDomain.getSearchGenerateDateFrom());
		rghDto.setSearchGenerateDateTo(rghDomain.getSearchGenerateDateTo());
		rghDto.setSearchGenerateRuleName(rghDomain.getSearchGenerateRuleName());
		rghDto.setSearchGenerator(rghDomain.getSearchGenerator());

		// 목록 조회
		List<RandomGenerationHistoryDto> rstDtoList = randomGenerationMapper.selectRandomGenerationHistoryList(rghDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewRandomGenerationHistoryListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewRandomGenerationHistoryListDomain returnDomain = new ViewRandomGenerationHistoryListDomain();

				returnDomain.setExecutionUuid(rstDtoList.get(i).getExecutionUuid());
				returnDomain.setExecutionDivision(rstDtoList.get(i).getExecutionDivision());
				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setPageCnt(rstDtoList.get(i).getPageCnt());
				returnDomain.setPageNum(rstDtoList.get(i).getPageNum());
				returnDomain.setGenerationNo(rstDtoList.get(i).getGenerationNo());
				returnDomain.setFolderFilePath(rstDtoList.get(i).getFolderFilePath());
				returnDomain.setFolderFileName(rstDtoList.get(i).getFolderFileName());
				returnDomain.setMethodName(rstDtoList.get(i).getMethodName());
				returnDomain.setRuleName(rstDtoList.get(i).getRuleName());
				returnDomain.setGenerator(rstDtoList.get(i).getGenerator());
				
				returnDomain.setGeneratedTestcaseCount(rstDtoList.get(i).getGeneratedTestcaseCount());

				returnDomain.setGenerateDate(rstDtoList.get(i).getGenerateDate());
				returnDomain.setGenerateComment(rstDtoList.get(i).getGenerateComment());

				returnDomain.setExecutionProjectNo(rstDtoList.get(i).getExecutionProjectNo());
			    returnDomain.setExecutionCondition(rstDtoList.get(i).getExecutionCondition());
			    returnDomain.setExecutionNode     (rstDtoList.get(i).getExecutionNode     ());
			    returnDomain.setExecutorId       (rstDtoList.get(i).getExecutorId       ());
			    returnDomain.setExecutorName     (rstDtoList.get(i).getExecutorName     ());

				returnList.add(returnDomain);
			}
		}
		return returnList;
	}

	/***
	 * 랜덤 생성 이력 삭제
	 *
	 * @param searchDto
	 * @return
	 */
	public int deleteRandomGenerationHistory(RemoveRandomGenerationHistoryDomain domain) {
		RandomGenerationHistoryDto schDto = new RandomGenerationHistoryDto();
		schDto.setExecutionUuid	(domain.getExecutionUuid());
		return randomGenerationMapper.deleteRandomGenerationHistory(schDto);
	}

	/***
	 * 랜덤 생성 실행 소스 도메인 조회
	 *
	 * @param dto
	 * @return
	 */
	public SourceMethodDomain selectObjectDetailDomain(SourceMethodDomain mddDomain) {
		//검색 값 셋팅
		SourceDomainDto schDto = new SourceDomainDto();
		schDto.setDomainName(mddDomain.getDomainName());
		schDto.setMethodNo(mddDomain.getMethodNo());
		schDto.setDomainNo(mddDomain.getDomainNo());

		//결과값 생성
		SourceMethodDomain resultDomain = null;

		SourceDomainDto resultDto = randomGenerationMapper.selectSourceDetailDomain(schDto);
		if(resultDto != null && resultDto.getDomainNo() != 0 ) {
			resultDomain = new SourceMethodDomain();

			resultDomain.setDomainNo        (resultDto.getDomainNo        ());
		    resultDomain.setDomainName      (resultDto.getDomainName      ());
		    resultDomain.setExecutionUuid   (resultDto.getExecutionUuid   ());
		    resultDomain.setDomainXmlContent(resultDto.getDomainXmlContent());
		    resultDomain.setGenerator       (resultDto.getGenerator        ());
		    resultDomain.setGenerateDate    (resultDto.getGenerateDate    ().toString());
		    resultDomain.setAmender         (resultDto.getAmender         ());
		}
		return resultDomain;
	}

	public ViewRandomGenerationMethodDomain selectMethod(ViewRandomGenerationMethodDomain rgInfoDomain) {
		//검색 값 셋팅
		SourceMethodDto schDto = new SourceMethodDto();
		schDto.setMethodNo(rgInfoDomain.getMethodNo());
		schDto.setRuleNo(rgInfoDomain.getRuleNo());

		//결과값 생성
		ViewRandomGenerationMethodDomain resultDomain = null;

		SourceMethodDto resultDto = randomGenerationMapper.selectSourceMethodDomain(schDto);
		if(resultDto != null) {
			resultDomain = new ViewRandomGenerationMethodDomain();

		    resultDomain.setFolderFilePath            (resultDto.getFolderFilePath ());
		    resultDomain.setFolderFileName            (resultDto.getFolderFileName ());
		    resultDomain.setMethodName                (resultDto.getMethodName     ());
		    resultDomain.setMethodNo                  (resultDto.getMethodNo       ());
		    resultDomain.setMethodInputXml            (resultDto.getMethodInputXml ());
		    resultDomain.setRuleNo                    (resultDto.getRuleNo         ());
		    resultDomain.setRuleName                  (resultDto.getRuleName       ());
		    resultDomain.setRuleFieldXml              (resultDto.getRuleFieldXml   ());
		}
		return resultDomain;
	}

	public ExecuteRandomGenerationDomain selectRandomGenerationExecution(ExecuteRandomGenerationDomain domain) {
		RandomGenerationDto rgDto = new RandomGenerationDto();

		rgDto.setProjectNo(domain.getProjectNo());
		rgDto.setMethodNo(domain.getMethodNo());
		rgDto.setGenerateCount(domain.getGenerateCount());
		rgDto.setRuleNo(domain.getRuleNo());
		rgDto.setGenerationDivision(domain.getGenerationDivision());
		rgDto.setExecutionUuid(domain.getExecutionUuid());
		rgDto.setRelationExecutionUuid(domain.getRelationExecutionUuid());

		ExecuteRandomGenerationDomain resultDomain = null;

		RandomGenerationDto resultDto = randomGenerationMapper.selectRandomGenerationExecution(rgDto);

		if(resultDto != null) {
			resultDomain = new ExecuteRandomGenerationDomain();

			resultDomain.setExecutionUuid(resultDto.getExecutionUuid());
			resultDomain.setTotalCnt(resultDto.getTotalCnt());
			resultDomain.setProjectName(resultDto.getProjectName());
			resultDomain.setProjectOwner(resultDto.getProjectOwner());
			resultDomain.setFolderFilePath(resultDto.getFolderFilePath());
			resultDomain.setFileName(resultDto.getFileName());
			resultDomain.setGenerationNo(resultDto.getGenerationNo());
			resultDomain.setGenerator(resultDto.getGenerator());
			resultDomain.setGenerateCount(resultDto.getGenerateCount());
			resultDomain.setMethodName(resultDto.getMethodName());
			resultDomain.setMethodNo(resultDto.getMethodNo());
		}
		return resultDomain;
	}

	/***
	 * 랜덤 생성 반영실행 -> 체크된 테스트케이스들을 데이터베이스에 저장한다.
	 * @param reflectionDomain
	 * @return
	 */
	public int executeRandomGenerationReflection(ExecuteRandomGenerationReflectionDomain reflectionDomain) {
		ExecuteRandomGenerationReflectionDto rgDto = new ExecuteRandomGenerationReflectionDto();

		rgDto.setTestcaseName(reflectionDomain.getTestcaseName());
		rgDto.setTestcaseDescription(reflectionDomain.getTestcaseDescription());
		rgDto.setTestcaseGroupNo(reflectionDomain.getTestcaseGroupNo());
		rgDto.setExecutionCount(reflectionDomain.getExecutionCount());
		rgDto.setSuccessCount(reflectionDomain.getSuccessCount());
		rgDto.setFailCount(reflectionDomain.getFailCount());
		rgDto.setMethodNo(reflectionDomain.getMethodNo());

		rgDto.setExecutionUuid(reflectionDomain.getExecutionUuid());

		ExecuteRandomGenerationReflectionDomain resultDomain = null;

		ExecuteRandomGenerationReflectionDto resultDto = randomGenerationMapper.updateRandomGenerationReflection(rgDto);

		if(resultDto != null) {
			resultDomain = new ExecuteRandomGenerationReflectionDomain();
			resultDomain.setTotalCnt(resultDto.getTotalCnt());
			//resultDomain.setIsReflection("1");
			resultDomain.setGenerator(resultDto.getGenerator());
			resultDomain.setGenerateDate(resultDto.getGenerateDate());
			resultDomain.setMethodName(resultDto.getMethodName());
			//resultDomain.setFolderFileName(resultDto.getFolderFileName());
			//resultDomain.setRuleName(resultDto.getRuleName());
			//resultDomain.setMethodNo(resultDto.getMethodNo());
		}

		return resultDomain.getTotalCnt();
	}


	/***
	 * 랜덤 생성 반영 xml 경로 찾기 -> 경로를 찾아 각각의 testcase들에 대해 createXml()
	 * @param domain
	 * @return
	 */
	public ExecuteRandomGenerationReflectionDomain selectRandomGenerationReflectionPath(
			ExecuteRandomGenerationReflectionDomain domain) {

		ExecuteRandomGenerationReflectionDto dto = new ExecuteRandomGenerationReflectionDto();

		dto.setExecutionUuid(domain.getExecutionUuid());
		dto.setTestcaseNo(domain.getTestcaseNo());

		ExecuteRandomGenerationReflectionDomain resultDomain = null;
		ExecuteRandomGenerationReflectionDto resultDto = randomGenerationMapper.selectRandomGenerationReflectionPath(dto);

		if(resultDto != null) {
			resultDomain = new ExecuteRandomGenerationReflectionDomain();

			resultDomain.setCompressFilePath(resultDto.getCompressFilePath());
		}
		return resultDomain;
	}

	public String selectTestcaseName(@Valid ViewRandomGenerationTestcaseListDomain domain) {
		RandomGenerationTestcaseListDto tcDto = new RandomGenerationTestcaseListDto();

		tcDto.setTestcaseNo(domain.getTestcaseNo());

		ViewRandomGenerationTestcaseListDomain resultDomain = null;
		RandomGenerationTestcaseListDto resultDto = randomGenerationMapper.selectTestcaseName(tcDto);

		if(resultDto != null) {
			resultDomain = new ViewRandomGenerationTestcaseListDomain();

			resultDomain.setTestcaseName(resultDto.getTestcaseName());
		}
		return resultDomain.getTestcaseName();
	}

	public String selectTestcaseGroupName(@Valid ViewRandomGenerationTestcaseListDomain domain) {
		RandomGenerationTestcaseListDto tcDto = new RandomGenerationTestcaseListDto();

		tcDto.setTestcaseGroupNo(domain.getTestcaseGroupNo());

		ViewRandomGenerationTestcaseListDomain resultDomain = null;
		RandomGenerationTestcaseListDto resultDto = randomGenerationMapper.selectTestcaseGroupName(tcDto);

		if(resultDto != null) {
			resultDomain = new ViewRandomGenerationTestcaseListDomain();

			resultDomain.setTestcaseGroupName(resultDto.getTestcaseGroupName());
		}
		return resultDomain.getTestcaseGroupName();
	}

	/**
	 * 규칙 알고리즘 파라미터 필드 유형 번호 조회
	 *
	 * @param domain
	 * @return
	 */
	public List<ExecuteRandomGenerationAlgorithmInfoDomain> selectRuleAlgorithmParameterByFieldTypeNo(ExecuteRandomGenerationAlgorithmInfoDomain domain) {
		List<ExecuteRandomGenerationAlgorithmInfoDomain> returnList = null;

		RuleAlgorithmParameterDto rgaDto = new RuleAlgorithmParameterDto();
		rgaDto.setFieldTypeNo(domain.getFieldTypeNo());

		// 목록 조회
		List<RuleAlgorithmParameterDto> rstDtoList = randomGenerationMapper.selectRuleAlgorithmParameterByFieldTypeNo(rgaDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ExecuteRandomGenerationAlgorithmInfoDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ExecuteRandomGenerationAlgorithmInfoDomain returnDomain = new ExecuteRandomGenerationAlgorithmInfoDomain();

				returnDomain.setAlgorithmNo            (rstDtoList.get(i).getAlgorithmNo            ());
				returnDomain.setParameterNo            (rstDtoList.get(i).getParameterNo            ());
				returnDomain.setParameterName          (rstDtoList.get(i).getParameterName          ());
				returnDomain.setParameterDescription   (rstDtoList.get(i).getParameterDescription   ());
				returnDomain.setParameterType          (rstDtoList.get(i).getParameterType          ());
				returnDomain.setParameterDefaultValue  (rstDtoList.get(i).getParameterDefaultValue  ());
				returnDomain.setGenerator              (rstDtoList.get(i).getGenerator              ());
				returnDomain.setGenerateDate           (rstDtoList.get(i).getGenerateDate           ());
				returnDomain.setAmender                (rstDtoList.get(i).getAmender                ());
				returnDomain.setRevisionDate           (rstDtoList.get(i).getRevisionDate           ());
				returnDomain.setParameterExample       (rstDtoList.get(i).getParameterExample       ());
				returnDomain.setFieldTypeNo            (rstDtoList.get(i).getFieldTypeNo            ());

				returnList.add(returnDomain);
			}
		}
		return returnList;
	}
}

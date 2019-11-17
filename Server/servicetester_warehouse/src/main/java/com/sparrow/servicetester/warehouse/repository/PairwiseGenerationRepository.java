package com.sparrow.servicetester.warehouse.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ExecutePairwiseGenerationInfoDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ExecutePairwiseGenerationRuleInfoDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationMethodDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationTestcaseListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationAlgorithmInfoDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SourceMethodDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationMethodDomain;
import com.sparrow.servicetester.warehouse.dto.PairwiseGenerationHistoryDto;
import com.sparrow.servicetester.warehouse.dto.PairwiseGenerationRuleDto;
import com.sparrow.servicetester.warehouse.dto.PairwiseGenerationTestcaseListDto;
import com.sparrow.servicetester.warehouse.dto.PairwiseRuleDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationAlgorithmDto;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationTestcaseListDto;
import com.sparrow.servicetester.warehouse.dto.RuleAlgorithmParameterDto;
import com.sparrow.servicetester.warehouse.dto.SourceDomainDto;
import com.sparrow.servicetester.warehouse.dto.SourceMethodDto;
import com.sparrow.servicetester.warehouse.mapper.PairwiseGenerationMapper;

@Repository
public class PairwiseGenerationRepository {
	@Inject
	private PairwiseGenerationMapper pairwiseGenerationMapper;
	static final Logger logger = LoggerFactory.getLogger(PairwiseGenerationRepository.class);
	/***
	 * 조합 생성 이력 목록 조회 총 갯수
	 *
	 * @param rghDomain
	 * @return
	 */
	public ViewPairwiseGenerationHistoryListDomain selectPairwiseGenerationHistoryListCnt(ViewPairwiseGenerationHistoryListDomain rghDomain) {
		PairwiseGenerationHistoryDto rghDto = new PairwiseGenerationHistoryDto();

		rghDto.setExecutionUuid(rghDomain.getExecutionUuid());
		rghDto.setExecutionProjectNo(rghDomain.getExecutionProjectNo());
		rghDto.setExecutionDivision(rghDomain.getExecutionDivision());

		rghDto.setMethodNo(rghDomain.getMethodNo());
		rghDto.setSearchGenerateDateFrom(rghDomain.getSearchGenerateDateFrom());
		rghDto.setSearchGenerateDateTo(rghDomain.getSearchGenerateDateTo());
		rghDto.setSearchGenerator(rghDomain.getSearchGenerator());
		rghDto.setSearchGenerateRuleName(rghDomain.getSearchGenerateRuleName());

		PairwiseGenerationHistoryDto rstTotalCnt = pairwiseGenerationMapper.selectPairwiseGenerationHistoryListCnt(rghDto);

		ViewPairwiseGenerationHistoryListDomain returnDomain = new ViewPairwiseGenerationHistoryListDomain();

		returnDomain.setTotalCnt(rstTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/***
	 * 조합 생성 이력 목록 조회
	 *
	 * @param schDomain
	 * @return
	 */
	public List<ViewPairwiseGenerationHistoryListDomain> selectPairwiseGenerationHistoryList(ViewPairwiseGenerationHistoryListDomain rghDomain) {
		List<ViewPairwiseGenerationHistoryListDomain> returnList = null;

		PairwiseGenerationHistoryDto rghDto = new PairwiseGenerationHistoryDto();

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
		List<PairwiseGenerationHistoryDto> rstDtoList = pairwiseGenerationMapper.selectPairwiseGenerationHistoryList(rghDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewPairwiseGenerationHistoryListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewPairwiseGenerationHistoryListDomain returnDomain = new ViewPairwiseGenerationHistoryListDomain();

				returnDomain.setExecutionUuid(rstDtoList.get(i).getExecutionUuid());
				returnDomain.setExecutionDivision(rstDtoList.get(i).getExecutionDivision());

				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setGenerationNo(rstDtoList.get(i).getGenerationNo());
				returnDomain.setFolderFilePath(rstDtoList.get(i).getFolderFilePath());
				returnDomain.setFolderFileName(rstDtoList.get(i).getFolderFileName());
				returnDomain.setMethodName(rstDtoList.get(i).getMethodName());
				returnDomain.setRuleName(rstDtoList.get(i).getRuleName());
				returnDomain.setGenerator(rstDtoList.get(i).getGenerator());
				
				
				returnDomain.setGeneratedCount(rstDtoList.get(i).getGeneratedTestcaseCount());
				
				returnDomain.setGenerateDate(rstDtoList.get(i).getGenerateDate());
				returnDomain.setGenerateComment(rstDtoList.get(i).getGenerateComment());
				returnDomain.setGenerateComment("예인이 바보");
				returnDomain.setGeneratedCount(1234);
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

	public String selectTestcaseName(@Valid ViewPairwiseGenerationTestcaseListDomain domain) {
		PairwiseGenerationTestcaseListDto tcDto = new PairwiseGenerationTestcaseListDto();

		tcDto.setTestcaseNo(domain.getTestcaseNo());

		ViewPairwiseGenerationTestcaseListDomain resultDomain = null;
		PairwiseGenerationTestcaseListDto resultDto = new PairwiseGenerationTestcaseListDto();
		resultDto.setTestcaseName(pairwiseGenerationMapper.selectTestcaseName(tcDto).getTestcaseName());

		if(resultDto != null) {
			resultDomain = new ViewPairwiseGenerationTestcaseListDomain();

			resultDomain.setTestcaseName(resultDto.getTestcaseName());
		}
		return resultDomain.getTestcaseName();
	}

	public String selectTestcaseGroupName(@Valid ViewPairwiseGenerationTestcaseListDomain domain) {
		PairwiseGenerationTestcaseListDto tcDto = new PairwiseGenerationTestcaseListDto();

		tcDto.setTestcaseGroupNo(domain.getTestcaseGroupNo());

		ViewPairwiseGenerationTestcaseListDomain resultDomain = null;
		PairwiseGenerationTestcaseListDto resultDto = new PairwiseGenerationTestcaseListDto();
		resultDto.setTestcaseGroupName(pairwiseGenerationMapper.selectTestcaseGroupName(tcDto).getTestcaseGroupName());

		if(resultDto != null) {
			resultDomain = new ViewPairwiseGenerationTestcaseListDomain();

			resultDomain.setTestcaseGroupName(resultDto.getTestcaseGroupName());
		}
		return resultDomain.getTestcaseGroupName();
	}
	
	/**
	 * 조합 생성 규칙 설정 목록 조회 총 개수
	 *
	 * @param rgrDomain
	 * @return
	 */
	public ViewPairwiseGenerationRuleListDomain selectPairwiseGenerationRuleListCnt(ViewPairwiseGenerationRuleListDomain rgrDomain) {
		PairwiseGenerationRuleDto rghDto = new PairwiseGenerationRuleDto();

		//rghDto.setExecutionUuid(rgrDomain.getUuid());
		rghDto.setMethodNo(rgrDomain.getMethodNo());

		PairwiseGenerationRuleDto rstTotalCnt = pairwiseGenerationMapper.selectPairwiseGenerationRuleListCnt(rghDto);
		ViewPairwiseGenerationRuleListDomain returnDomain = new ViewPairwiseGenerationRuleListDomain();

		returnDomain.setTotalCnt(rstTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/**
	 * 조합 생성 규칙 목록 조회
	 *
	 * @param rgrDomain
	 * @return
	 */
	public List<ViewPairwiseGenerationRuleListDomain> selectPairwiseGenerationRuleList(
			@Valid ViewPairwiseGenerationRuleListDomain rgrDomain) {
		List<ViewPairwiseGenerationRuleListDomain> returnList = null;

		PairwiseGenerationRuleDto rgaDto = new PairwiseGenerationRuleDto();

		rgaDto.setPageCnt(rgrDomain.getPageCnt());
		rgaDto.setPageNum(rgrDomain.getPageNum());
		rgaDto.setTotalCnt(rgrDomain.getTotalCnt());

		rgaDto.setMethodNo(rgrDomain.getMethodNo());
		rgaDto.setSearchRuleName(rgrDomain.getSearchRuleName());
		rgaDto.setSearchRuleDescription(rgrDomain.getSearchRuleDescription());
		rgaDto.setSearchRevisionDateFrom(rgrDomain.getSearchRevisionDateFrom());
		rgaDto.setSearchRevisionDateTo(rgrDomain.getSearchRevisionDateTo());
		rgaDto.setSearchGenerator(rgrDomain.getSearchGenerator());
		//rgaDto.setGenerator(rgaDomain.getGenerator());

		// 목록 조회
		List<PairwiseGenerationRuleDto> rstDtoList = pairwiseGenerationMapper.selectPairwiseGenerationRuleList(rgaDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewPairwiseGenerationRuleListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewPairwiseGenerationRuleListDomain returnDomain = new ViewPairwiseGenerationRuleListDomain();

				//returnDomain.setExecutionUuid(rstDtoList.get(i).getExecutionUuid());
				//returnDomain.setUuid(rstDtoList.get(i).getUuid());
				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setFileName(rstDtoList.get(i).getFolderFileName());
				returnDomain.setMethodName(rstDtoList.get(i).getMethodName());
				returnDomain.setRuleNo(rstDtoList.get(i).getRuleNo());
				returnDomain.setGeneratedTestcaseCount(rstDtoList.get(i).getGeneratedTestcaseCount());
				returnDomain.setRuleName(rstDtoList.get(i).getRuleName ());
				returnDomain.setRuleDescription(rstDtoList.get(i).getRuleDescription ());
				returnDomain.setGenerator(rstDtoList.get(i).getGenerator ());
				returnDomain.setRevisionDate(rstDtoList.get(i).getRevisionDate ());

				returnList.add(returnDomain);
			}
		}
		return returnList;
	}
	
	public ViewPairwiseGenerationMethodDomain selectMethod(ViewPairwiseGenerationMethodDomain rgInfoDomain) {
		//검색 값 셋팅
		SourceMethodDto schDto = new SourceMethodDto();
		schDto.setMethodNo(rgInfoDomain.getMethodNo());
		schDto.setRuleNo(rgInfoDomain.getRuleNo());

		//결과값 생성
		ViewPairwiseGenerationMethodDomain resultDomain = null;

		SourceMethodDto resultDto = pairwiseGenerationMapper.selectSourceMethodDomain(schDto);
		if(resultDto != null) {
			resultDomain = new ViewPairwiseGenerationMethodDomain();

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

	public ExecutePairwiseGenerationInfoDomain selectValueStringByRuleNo(
			ExecutePairwiseGenerationInfoDomain algoSchDomain) {
		PairwiseGenerationRuleDto pgrDto = new PairwiseGenerationRuleDto();
		pgrDto.setRuleNo(algoSchDomain.getRuleNo());
		
		ExecutePairwiseGenerationInfoDomain resultDomain = null;
		
		PairwiseGenerationRuleDto resultDto = pairwiseGenerationMapper.selectPairwiseListByRuleNo(pgrDto);
		
		if(resultDto != null) {
			resultDomain = new ExecutePairwiseGenerationInfoDomain();
			
			resultDomain.setValueString				  (resultDto.getValueString	   ());
		    resultDomain.setRuleNo                    (resultDto.getRuleNo         ());
		    resultDomain.setRuleName                  (resultDto.getRuleName       ());
		    resultDomain.setRuleFieldXml              	  (resultDto.getRuleFieldXml   ());
		}
		return resultDomain;
	}
}

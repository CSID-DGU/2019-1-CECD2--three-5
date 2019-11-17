package com.sparrow.servicetester.warehouse.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Repository;

import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationAlgorithmParameterDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationAlgorithmParameterDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationAlgorithmListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationAlgorithmParameterListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationFieldTypeParameterListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomRuleFieldMappingDomain;
import com.sparrow.servicetester.warehouse.dto.RandomGenerationAlgorithmDto;
import com.sparrow.servicetester.warehouse.dto.RuleManagementDto;
import com.sparrow.servicetester.warehouse.mapper.RandomGenerationMngAlgorithmMapper;

@Repository
public class RandomGenerationMngAlgorithmRepository {

	@Inject
	RandomGenerationMngAlgorithmMapper randomGenerationMngAlgorithmMapper;

	/**
	 * 랜덤 생성 필드 유형 목록 총 개수
	 *
	 * @param rgaDomain
	 * @return
	 */
	public ViewRandomGenerationFieldTypeListDomain selectRandomGenerationFieldTypeListCnt(ViewRandomGenerationFieldTypeListDomain rgaDomain) {
		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();

		rgaDto.setPageCnt(rgaDomain.getPageCnt());
		rgaDto.setPageNum(rgaDomain.getPageNum());
		rgaDto.setTotalCnt(rgaDomain.getTotalCnt());

		rgaDto.setSearchAlgorithmLevel(rgaDomain.getSearchAlgorithmLevel());
		rgaDto.setSearchGenerateDateFrom(rgaDomain.getSearchGenerateDateFrom());
		rgaDto.setSearchGenerateDateTo(rgaDomain.getSearchGenerateDateTo());
		//rgaDto.setGenerator(rgaDomain.getGenerator());
		rgaDto.setSearchAlgorithmTypeName(rgaDomain.getSearchAlgorithmTypeName());
		rgaDto.setSearchFieldTypeName(rgaDomain.getSearchFieldTypeName());
		// 총 갯수 구하기

		RandomGenerationAlgorithmDto rghTotalCnt = randomGenerationMngAlgorithmMapper.selectRandomGenerationFieldTypeListCnt(rgaDto);

		ViewRandomGenerationFieldTypeListDomain returnDomain = new ViewRandomGenerationFieldTypeListDomain();
		returnDomain.setTotalCnt(rghTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/**
	 * 랜덤 생성 필드 유형 목록
	 *
	 * @param rgaDomain
	 * @return
	 */
	public List<ViewRandomGenerationFieldTypeListDomain> selectRandomGenerationFieldTypeList
	(ViewRandomGenerationFieldTypeListDomain rgaDomain) {
		List<ViewRandomGenerationFieldTypeListDomain> returnList = null;

		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();

		rgaDto.setPageCnt(rgaDomain.getPageCnt());
		rgaDto.setPageNum(rgaDomain.getPageNum());
		rgaDto.setTotalCnt(rgaDomain.getTotalCnt());

		rgaDto.setSearchAlgorithmLevel(rgaDomain.getSearchAlgorithmLevel());
		rgaDto.setSearchFieldTypeName(rgaDomain.getSearchFieldTypeName());
		rgaDto.setSearchAlgorithmTypeName(rgaDomain.getSearchAlgorithmTypeName());
		rgaDto.setSearchGenerateDateFrom(rgaDomain.getSearchGenerateDateFrom());
		rgaDto.setSearchGenerateDateTo(rgaDomain.getSearchGenerateDateTo());
		//rgaDto.setGenerator(rgaDomain.getGenerator());

		// 목록 조회
		List<RandomGenerationAlgorithmDto> rstDtoList = randomGenerationMngAlgorithmMapper.selectRandomGenerationFieldTypeList(rgaDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewRandomGenerationFieldTypeListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewRandomGenerationFieldTypeListDomain returnDomain = new ViewRandomGenerationFieldTypeListDomain();

				//returnDomain.setExecutionUuid(rstDtoList.get(i).getExecutionUuid());
				//returnDomain.setUuid(rstDtoList.get(i).getUuid());
				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setTotalCnt(rstDtoList.get(i).getTotalCnt());
				returnDomain.setFieldTypeNo(rstDtoList.get(i).getFieldTypeNo());
				returnDomain.setAlgorithmNo(rstDtoList.get(i).getAlgorithmNo());
				returnDomain.setAlgorithmLevel (rstDtoList.get(i).getFieldTypeLevel ());
				returnDomain.setAlgorithmTypeName(rstDtoList.get(i).getAlgorithmTypeName ());
				returnDomain.setFieldTypeName(rstDtoList.get(i).getFieldTypeName ());
				returnDomain.setGenerator(rstDtoList.get(i).getGenerator ());
				returnDomain.setGenerateDate(rstDtoList.get(i).getGenerateDate ());

				returnList.add(returnDomain);
			}
		}
		return returnList;
	}

	/**
	 * 랜덤 생성 필드 유형 추가
	 *
	 * @param addDomain
	 * @return
	 */
	public int insertRandomGenerationFieldType(AddRandomGenerationFieldTypeDomain addDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

		algorithmDto.setAlgorithmTypeName(addDomain.getAlgorithmTypeName());
		algorithmDto.setFieldTypeName(addDomain.getFieldTypeName());
		algorithmDto.setGenerator(addDomain.getGenerator());

		return randomGenerationMngAlgorithmMapper.insertRandomGenerationFieldType(algorithmDto);
	}

	/**
	 * 랜덤 생성 필드 유형 추가
	 *
	 * @param addDomain
	 * @return
	 */
	public int insertRandomGenerationFieldType(SaveRandomGenerationFieldTypeDomain addDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

		algorithmDto.setAlgorithmTypeName(addDomain.getAlgorithmTypeName());
		algorithmDto.setFieldTypeName(addDomain.getFieldTypeName());
		algorithmDto.setGenerator(addDomain.getGenerator());

		return randomGenerationMngAlgorithmMapper.insertRandomGenerationFieldType(algorithmDto);
	}

	/**
	 * 랜덤 생성 필드 유형 수정
	 * @param modifyDomain
	 * @return
	 */
	public int updateRandomGenerationFieldType(ModifyRandomGenerationFieldTypeDomain modifyDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

	    algorithmDto.setAlgorithmLevel(modifyDomain.getAlgorithmLevel());
	    algorithmDto.setAlgorithmNo(modifyDomain.getAlgorithmNo());
	    algorithmDto.setFieldTypeNo(modifyDomain.getFieldTypeNo());
		algorithmDto.setFieldTypeName(modifyDomain.getFieldTypeName());
		algorithmDto.setAlgorithmTypeName(modifyDomain.getAlgorithmTypeName());
		algorithmDto.setAmender(modifyDomain.getAmender());

		return randomGenerationMngAlgorithmMapper.updateRandomGenerationFieldType(algorithmDto);
	}

	/***
	* 랜덤 생성 필드 유형 상세조회 -> 우선은 중복 검사하는 곳에만 쓰임
	*
	* @param domain
	* @return
	*/
	public AddRandomGenerationFieldTypeDomain selectRandomGenerationFieldType(AddRandomGenerationFieldTypeDomain rgaDomain) {
		AddRandomGenerationFieldTypeDomain returnDomain = null;

		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();
		// 레벨에 따른 검색, 유형 API에 따른 검색, 필드 유형 이름에 대한 검색으로 나눌까 고려중
		rgaDto.setAlgorithmLevel(rgaDomain.getAlgorithmLevel());
		rgaDto.setAlgorithmTypeName(rgaDomain.getAlgorithmTypeName());
		rgaDto.setFieldTypeName(rgaDomain.getFieldTypeName());

		RandomGenerationAlgorithmDto rstDto = randomGenerationMngAlgorithmMapper.selectRandomGenerationFieldType(rgaDto);

		if (rstDto != null) {
			returnDomain = new AddRandomGenerationFieldTypeDomain();
			returnDomain.setAlgorithmLevel(rstDto.getAlgorithmLevel());
			returnDomain.setAlgorithmTypeName(rstDto.getAlgorithmTypeName());
			returnDomain.setFieldTypeName(rstDto.getFieldTypeName());
			returnDomain.setGenerator(rstDto.getGenerator());
			returnDomain.setGenerateDate(rstDto.getGenerateDate());
		}
		return returnDomain;
	}

	/**
	 * 랜덤 생성 필드 유형 수정
	 * @param modifyDomain
	 * @return
	 */
	public int updateRandomGenerationFieldType(SaveRandomGenerationFieldTypeDomain modifyDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

	    algorithmDto.setAlgorithmLevel(modifyDomain.getAlgorithmLevel());
	    algorithmDto.setAlgorithmNo(modifyDomain.getAlgorithmNo());
		algorithmDto.setFieldTypeName(modifyDomain.getFieldTypeName());
		algorithmDto.setAlgorithmTypeName(modifyDomain.getFieldTypeName());
		algorithmDto.setAmender(modifyDomain.getAmender());

		return randomGenerationMngAlgorithmMapper.updateRandomGenerationFieldType(algorithmDto);
	}

	/**
	 * 랜덤 생성 필드 유형 삭제
	 *
	 * @param removeDomain
	 * @return
	 */
	public int deleteRandomGenerationFieldType(RemoveRandomGenerationFieldTypeDomain removeDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();
		algorithmDto.setFieldTypeNo(removeDomain.getFieldTypeNo());

		return randomGenerationMngAlgorithmMapper.deleteRandomGenerationFieldType(algorithmDto);
	}

	/**
	 * 랜덤 생성 알고리즘 기본 등록 목록 개수
	 *
	 * @param rgaDomain
	 * @return
	 */
	public ViewRandomGenerationAlgorithmListDomain selectRandomGenerationAlgorithmListCnt(
			@Valid ViewRandomGenerationAlgorithmListDomain rgaDomain) {
		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();

		//rgaDto.setExecutionUuid(rgaDomain.getExecutionUuid());
		rgaDto.setPageCnt(rgaDomain.getPageCnt());
		rgaDto.setPageNum(rgaDomain.getPageNum());

		rgaDto.setSearchAlgorithmReturnType(rgaDomain.getSearchAlgorithmReturnType());
		rgaDto.setSearchGenerateDateFrom(rgaDomain.getSearchGenerateDateFrom());
		rgaDto.setSearchGenerateDateTo(rgaDomain.getSearchGenerateDateTo());
		//rgaDto.setGenerator(rgaDomain.getGenerator());
		rgaDto.setSearchAlgorithmTypeName(rgaDomain.getSearchAlgorithmTypeName());
		// 총 갯수 구하기

		RandomGenerationAlgorithmDto rghTotalCnt = randomGenerationMngAlgorithmMapper.selectRandomGenerationAlgorithmListCnt(rgaDto);

		ViewRandomGenerationAlgorithmListDomain returnDomain = new ViewRandomGenerationAlgorithmListDomain();
		returnDomain.setTotalCnt(rghTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/**
	 * 랜덤 생성 알고리즘 기본 등록 목록
	 *
	 * @param domain
	 * @return
	 */
	public List<ViewRandomGenerationAlgorithmListDomain> selectRandomGenerationAlgorithmList(
			@Valid ViewRandomGenerationAlgorithmListDomain rgaDomain) {
		List<ViewRandomGenerationAlgorithmListDomain> returnList = null;

		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();

		//rgaDto.setExecutionUuid(rgaDomain.getExecutionUuid());
		rgaDto.setPageCnt(rgaDomain.getPageCnt());
		rgaDto.setPageNum(rgaDomain.getPageNum());
		rgaDto.setSearchAlgorithmReturnType(rgaDomain.getSearchAlgorithmReturnType());
		rgaDto.setSearchGenerateDateFrom(rgaDomain.getSearchGenerateDateFrom());
		rgaDto.setSearchGenerateDateTo(rgaDomain.getSearchGenerateDateTo());
		//rgaDto.setGenerator(rgaDomain.getGenerator());
		rgaDto.setSearchAlgorithmTypeName(rgaDomain.getSearchAlgorithmTypeName());

		// 목록 조회
		List<RandomGenerationAlgorithmDto> rstDtoList = randomGenerationMngAlgorithmMapper.selectRandomGenerationAlgorithmList(rgaDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewRandomGenerationAlgorithmListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewRandomGenerationAlgorithmListDomain returnDomain = new ViewRandomGenerationAlgorithmListDomain();

				//returnDomain.setExecutionUuid(rstDtoList.get(i).getExecutionUuid());
				returnDomain.setUuid(rstDtoList.get(i).getUuid());
				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setAlgorithmNo(rstDtoList.get(i).getAlgorithmNo());
				returnDomain.setAlgorithmTypeName(rstDtoList.get(i).getAlgorithmTypeName ());
				returnDomain.setAlgorithmReturnType(rstDtoList.get(i).getAlgorithmReturnType());
				returnDomain.setGenerator(rstDtoList.get(i).getGenerator ());
				returnDomain.setGenerateDate(rstDtoList.get(i).getGenerateDate ());

				returnList.add(returnDomain);
			}
		}
		return returnList;
	}

	/**
	 * 랜덤 생성 알고리즘 기본 등록 목록 추가
	 *
	 * @param saveDomain
	 * @return
	 */
	public int insertRandomGenerationAlgorithm(SaveRandomGenerationAlgorithmDomain groupDmn) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

		algorithmDto.setAlgorithmTypeName(groupDmn.getAlgorithmTypeName());
		algorithmDto.setAlgorithmReturnType(groupDmn.getAlgorithmReturnType());
		algorithmDto.setGenerator(groupDmn.getGenerator());

		return randomGenerationMngAlgorithmMapper.insertRandomGenerationAlgorithm(algorithmDto);
	}

	/**
	 * 랜덤 생성 알고리즘 기본 등록 목록 추가
	 *
	 * @param addDomain
	 * @return
	 */
	public int insertRandomGenerationAlgorithm(AddRandomGenerationAlgorithmDomain addDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

		algorithmDto.setAlgorithmTypeName(addDomain.getAlgorithmTypeName());
		algorithmDto.setAlgorithmReturnType(addDomain.getAlgorithmReturnType());
		algorithmDto.setGenerator(addDomain.getGenerator());

		return randomGenerationMngAlgorithmMapper.insertRandomGenerationAlgorithm(algorithmDto);
	}

	/**
	 * 랜덤 생성 알고리즘 기본 등록 수정
	 *
	 * @param modifyDomain
	 * @return
	 */
	public int updateRandomGenerationAlgorithm(ModifyRandomGenerationAlgorithmDomain modifyDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

	    algorithmDto.setAlgorithmTypeName(modifyDomain.getAlgorithmTypeName());
	    algorithmDto.setAlgorithmNo(modifyDomain.getAlgorithmNo());
		algorithmDto.setAmender(modifyDomain.getAmender());

		return randomGenerationMngAlgorithmMapper.updateRandomGenerationAlgorithm(algorithmDto);
	}

	/**
	 * 랜덤 생성 알고리즘 기본 등록 수정
	 *
	 * @param saveDomain
	 * @return
	 */
	public int updateRandomGenerationAlgorithm(SaveRandomGenerationAlgorithmDomain modifyDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

	    algorithmDto.setAlgorithmTypeName(modifyDomain.getAlgorithmTypeName());
	    algorithmDto.setAlgorithmNo(modifyDomain.getAlgorithmNo());
		algorithmDto.setAmender(modifyDomain.getAmender());

		return randomGenerationMngAlgorithmMapper.updateRandomGenerationAlgorithm(algorithmDto);
	}

	/**
	 * 랜덤 생성 알고리즘 기본 등록 목록 단건 삭제
	 *
	 * @param removeDomain
	 * @return
	 */
	public int deleteRandomGenerationAlgorithm(RemoveRandomGenerationAlgorithmDomain removeDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();
		algorithmDto.setAlgorithmNo(removeDomain.getAlgorithmNo());

		return randomGenerationMngAlgorithmMapper.deleteRandomGenerationAlgorithm(algorithmDto);
	}

	//상세조회 -> 중복체크 활용
	public AddRandomGenerationAlgorithmDomain selectRandomGenerationAlgorithm(AddRandomGenerationAlgorithmDomain domain) {
		AddRandomGenerationAlgorithmDomain returnDomain = null;

		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();
		// 레벨에 따른 검색, 유형 API에 따른 검색, 필드 유형 이름에 대한 검색으로 나눌까 고려중
		rgaDto.setAlgorithmTypeName(domain.getAlgorithmTypeName());
		rgaDto.setAlgorithmReturnType(domain.getAlgorithmReturnType());

		RandomGenerationAlgorithmDto rstDto = randomGenerationMngAlgorithmMapper.selectRandomGenerationAlgorithm(rgaDto);

		if (rstDto != null) {
			returnDomain = new AddRandomGenerationAlgorithmDomain();
			returnDomain.setAlgorithmTypeName(rstDto.getAlgorithmTypeName());
			returnDomain.setAlgorithmReturnType(rstDto.getAlgorithmReturnType());
			returnDomain.setGenerator(rstDto.getGenerator());
			returnDomain.setGenerateDate(rstDto.getGenerateDate());
		}
		return returnDomain;
	}


	/**
	 * 랜덤 생성 규칙 설정 목록 조회 총 개수
	 *
	 * @param rgrDomain
	 * @return
	 */
	public ViewRandomGenerationRuleListDomain selectRandomGenerationRuleCnt(ViewRandomGenerationRuleListDomain rgrDomain) {
		RandomGenerationAlgorithmDto rghDto = new RandomGenerationAlgorithmDto();

		//rghDto.setExecutionUuid(rgrDomain.getUuid());
		rghDto.setMethodNo(rgrDomain.getMethodNo());

		RandomGenerationAlgorithmDto rstTotalCnt = randomGenerationMngAlgorithmMapper.selectRandomGenerationRuleListCnt(rghDto);
		ViewRandomGenerationRuleListDomain returnDomain = new ViewRandomGenerationRuleListDomain();

		returnDomain.setTotalCnt(rstTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/**
	 * 랜덤 생성 규칙 목록 조회
	 *
	 * @param rgrDomain
	 * @return
	 */
	public List<ViewRandomGenerationRuleListDomain> selectRandomGenerationAlgorithmRuleList(
			@Valid ViewRandomGenerationRuleListDomain rgrDomain) {
		List<ViewRandomGenerationRuleListDomain> returnList = null;

		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();

		rgaDto.setPageCnt(rgrDomain.getPageCnt());
		rgaDto.setPageNum(rgrDomain.getPageNum());
		rgaDto.setTotalCnt(rgrDomain.getTotalCnt());

		rgaDto.setMethodNo(rgrDomain.getMethodNo());
		rgaDto.setFieldTypeNo(rgrDomain.getFieldTypeNo());
		rgaDto.setSearchAlgorithmLevel(rgrDomain.getSearchRuleName());
		rgaDto.setSearchRuleName(rgrDomain.getSearchRuleName());
		rgaDto.setSearchAlgorithmLevel(rgrDomain.getSearchRuleLevel());
		rgaDto.setSearchRuleDescription(rgrDomain.getSearchRuleDescription());
		rgaDto.setSearchFieldTypeName(rgrDomain.getSearchRuleDescription());
		rgaDto.setSearchAlgorithmTypeName(rgrDomain.getSearchRuleLevel());
		rgaDto.setSearchRevisionDateFrom(rgrDomain.getSearchRevisionDateFrom());
		rgaDto.setSearchRevisionDateTo(rgrDomain.getSearchRevisionDateTo());
		rgaDto.setSearchGenerator(rgrDomain.getSearchGenerator());
		//rgaDto.setGenerator(rgaDomain.getGenerator());

		// 목록 조회
		List<RandomGenerationAlgorithmDto> rstDtoList = randomGenerationMngAlgorithmMapper.selectRandomGenerationRuleList(rgaDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewRandomGenerationRuleListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewRandomGenerationRuleListDomain returnDomain = new ViewRandomGenerationRuleListDomain();

				//returnDomain.setExecutionUuid(rstDtoList.get(i).getExecutionUuid());
				//returnDomain.setUuid(rstDtoList.get(i).getUuid());
				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setFileName(rstDtoList.get(i).getFolderFileName());
				returnDomain.setMethodName(rstDtoList.get(i).getMethodName());
				returnDomain.setFieldTypeNo(rstDtoList.get(i).getFieldTypeNo());
				returnDomain.setRuleNo(rstDtoList.get(i).getRuleNo());
				returnDomain.setRuleLevel (rstDtoList.get(i).getAlgorithmLevel ());
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

	/***
	* 랜덤 생성 규칙 상세조회 -> 우선은 중복 검사하는 곳에만 쓰임
	*
	* @param domain
	* @return
	*/
	public AddRandomGenerationRuleDomain selectRandomGenerationRule(AddRandomGenerationRuleDomain rgrDomain) {
		AddRandomGenerationRuleDomain returnDomain = null;

		RandomGenerationAlgorithmDto rgaDto = new RandomGenerationAlgorithmDto();
		// 레벨에 따른 검색, 유형 API에 따른 검색, 필드 유형 이름에 대한 검색으로 나눌까 고려중
		rgaDto.setAlgorithmLevel(rgrDomain.getAlgorithmLevel());
		rgaDto.setRuleDescription(rgrDomain.getRuleDescription());
		rgaDto.setRuleName(rgrDomain.getRuleName());

		RandomGenerationAlgorithmDto rstDto = randomGenerationMngAlgorithmMapper.selectRandomGenerationRule(rgaDto);

		if (rstDto != null) {
			returnDomain = new AddRandomGenerationRuleDomain();
			returnDomain.setAlgorithmLevel(rstDto.getAlgorithmLevel());
			returnDomain.setRuleName(rstDto.getAlgorithmTypeName());
			returnDomain.setRuleDescription(rstDto.getFieldTypeName());
			returnDomain.setGenerator(rstDto.getGenerator());
			returnDomain.setGenerateDate(rstDto.getGenerateDate());
		}
		return returnDomain;
	}

	/**
	 * 랜덤 생성 규칙 추가
	 *
	 * @param addDomain
	 * @return
	 */
	public int insertRandomGenerationRule(SaveRandomGenerationRuleDomain groupDmn) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

		algorithmDto.setRuleName(groupDmn.getRuleName());
		algorithmDto.setRuleDescription(groupDmn.getRuleDescription());
		algorithmDto.setGenerator(groupDmn.getGenerator());

		return randomGenerationMngAlgorithmMapper.insertRandomGenerationRule(algorithmDto);
	}

	/**
	 * 랜덤 생성 규칙 추가
	 *
	 * @param addDomain
	 * @return
	 */
	public int insertRandomGenerationRule(AddRandomGenerationRuleDomain addDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

		algorithmDto.setRuleName(addDomain.getRuleName());
		algorithmDto.setRuleDescription(addDomain.getRuleName());
		algorithmDto.setGenerator(addDomain.getGenerator());

		return randomGenerationMngAlgorithmMapper.insertRandomGenerationRule(algorithmDto);
	}

	/**
	 * 랜덤 생성 규칙 수정
	 *
	 * @param modifyDomain
	 * @return
	 */
	public int updateRandomGenerationRule(ModifyRandomGenerationRuleDomain modifyDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

	    algorithmDto.setRuleNo(modifyDomain.getRuleNo());
	    algorithmDto.setAlgorithmLevel(modifyDomain.getAlgorithmLevel());
		algorithmDto.setRuleDescription(modifyDomain.getRuleDescription());
		algorithmDto.setGeneratedTestcaseCount(modifyDomain.getGeneratedTestcaseCount());
		algorithmDto.setAmender(modifyDomain.getAmender());

		return randomGenerationMngAlgorithmMapper.updateRandomGenerationRule(algorithmDto);
	}

	/**
	 * 랜덤 생성 규칙 수정
	 *
	 * @param addDomain
	 * @return
	 */
	public int updateRandomGenerationRule(SaveRandomGenerationRuleDomain modifyDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();

	    algorithmDto.setAlgorithmLevel(modifyDomain.getAlgorithmLevel());
	    algorithmDto.setRuleNo(modifyDomain.getRuleNo());
		algorithmDto.setRuleDescription(modifyDomain.getRuleDescription());
		algorithmDto.setRuleName(modifyDomain.getRuleName());
		algorithmDto.setGeneratedTestcaseCount(modifyDomain.getGeneratedTestcaseCount());
		algorithmDto.setAmender(modifyDomain.getAmender());

		return randomGenerationMngAlgorithmMapper.updateRandomGenerationRule(algorithmDto);
	}

	/**
	 * 랜덤 생성 규칙 삭제
	 *
	 * @param addDomain
	 * @return
	 */
	public int deleteRandomGenerationRule(RemoveRandomGenerationRuleDomain removeDomain) {
		RandomGenerationAlgorithmDto algorithmDto = new RandomGenerationAlgorithmDto();
		algorithmDto.setRuleNo(removeDomain.getRuleNo());

		return randomGenerationMngAlgorithmMapper.deleteRandomGenerationRule(algorithmDto);
	}

	/**
	 * 랜덤 생성 알고리즘 파라미터 총 개수
	 *
	 * @param domain
	 * @return
	 */
	public ViewRandomGenerationAlgorithmParameterListDomain selectRandomGenerationAlgorithmParameterListCnt(ViewRandomGenerationAlgorithmParameterListDomain domain) {
		RandomGenerationAlgorithmDto rghDto = new RandomGenerationAlgorithmDto();

		rghDto.setAlgorithmNo(domain.getAlgorithmNo());

		RandomGenerationAlgorithmDto rstTotalCnt = randomGenerationMngAlgorithmMapper.selectRandomGenerationAlgorithmParameterListCnt(rghDto);
		ViewRandomGenerationAlgorithmParameterListDomain returnDomain = new ViewRandomGenerationAlgorithmParameterListDomain();

		returnDomain.setTotalCnt(rstTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/**
	 * 랜덤 생성 알고리즘 파라미터 목록 조회
	 *
	 * @param rghDomain
	 * @return
	 */
	public List<ViewRandomGenerationAlgorithmParameterListDomain> selectRandomGenerationAlgorithmParameterList(
			ViewRandomGenerationAlgorithmParameterListDomain rghDomain) {
		List<ViewRandomGenerationAlgorithmParameterListDomain> returnList = null;

		RandomGenerationAlgorithmDto rghDto = new RandomGenerationAlgorithmDto();

		rghDto.setAlgorithmNo(rghDomain.getAlgorithmNo());

		rghDto.setPageCnt(rghDomain.getPageCnt());
		rghDto.setPageNum(rghDomain.getPageNum());
		rghDto.setTotalCnt(rghDomain.getTotalCnt());

		// 목록 조회
		List<RandomGenerationAlgorithmDto> rstDtoList = randomGenerationMngAlgorithmMapper.selectRandomGenerationAlgorithmParameterList(rghDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewRandomGenerationAlgorithmParameterListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewRandomGenerationAlgorithmParameterListDomain returnDomain = new ViewRandomGenerationAlgorithmParameterListDomain();

				//returnDomain.setExecutionUuid(rstDtoList.get(i).getExecutionUuid());
				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setParameterNo(rstDtoList.get(i).getParameterNo());
				returnDomain.setAlgorithmTypeName(rstDtoList.get(i).getAlgorithmTypeName());
				returnDomain.setAlgorithmReturnType(rstDtoList.get(i).getAlgorithmReturnType());
				returnDomain.setParameterDescription(rstDtoList.get(i).getParameterDescription());
				returnDomain.setParameterExample(rstDtoList.get(i).getParameterExample());
				returnDomain.setParameterDefaultValue(rstDtoList.get(i).getParameterDefaultValue());
				returnDomain.setParameterName(rstDtoList.get(i).getParameterName());
				returnDomain.setParameterType(rstDtoList.get(i).getParameterType());
				returnDomain.setGenerator(rstDtoList.get(i).getGenerator());
				returnDomain.setGenerateDate(rstDtoList.get(i).getGenerateDate());

				returnList.add(returnDomain);
			}
		}
		return returnList;
	}

	/**
	 * 랜덤 생성 알고리즘 파라미터 추가
	 *
	 * @param addDomain
	 * @return
	 */
	public int insertAlgorithmParameter(AddRandomGenerationAlgorithmParameterDomain addDomain) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 랜덤 생성 알고리즘 파라미터 삭제
	 *
	 * @param removeDomain
	 * @return
	 */
	public int deleteAlgorithmParameter(
			RemoveRandomGenerationAlgorithmParameterDomain removeDomain) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 랜덤 생성 필드유형 파라미터 총 개수
	 *
	 * @param removeDomain
	 * @return
	 */
	public ViewRandomGenerationFieldTypeParameterListDomain selectRandomGenerationFieldTypeParameterListCnt(
			@Valid ViewRandomGenerationFieldTypeParameterListDomain domain) {
		RandomGenerationAlgorithmDto rghDto = new RandomGenerationAlgorithmDto();

		rghDto.setFieldTypeNo(domain.getFieldTypeNo());

		RandomGenerationAlgorithmDto rstTotalCnt = randomGenerationMngAlgorithmMapper.selectRandomGenerationFieldTypeParameterListCnt(rghDto);
		ViewRandomGenerationFieldTypeParameterListDomain returnDomain = new ViewRandomGenerationFieldTypeParameterListDomain();

		returnDomain.setTotalCnt(rstTotalCnt.getTotalCnt());

		return returnDomain;
	}

	/**
	 * 랜덤 생성 필드유형 파라미터 목록
	 *
	 * @param removeDomain
	 * @return
	 */
	public List<ViewRandomGenerationFieldTypeParameterListDomain> selectRandomGenerationFieldTypeParameterList(
			@Valid ViewRandomGenerationFieldTypeParameterListDomain rghDomain) {
		List<ViewRandomGenerationFieldTypeParameterListDomain> returnList = null;

		RandomGenerationAlgorithmDto rghDto = new RandomGenerationAlgorithmDto();

		rghDto.setFieldTypeNo(rghDomain.getFieldTypeNo());

		rghDto.setPageCnt(rghDomain.getPageCnt());
		rghDto.setPageNum(rghDomain.getPageNum());
		rghDto.setTotalCnt(rghDomain.getTotalCnt());

		// 목록 조회
		List<RandomGenerationAlgorithmDto> rstDtoList = randomGenerationMngAlgorithmMapper.selectRandomGenerationFieldTypeParameterList(rghDto);

		if (rstDtoList != null && rstDtoList.size() > 0) {
			returnList = new ArrayList<ViewRandomGenerationFieldTypeParameterListDomain>();
			for (int i = 0; i < rstDtoList.size(); i++) {
				ViewRandomGenerationFieldTypeParameterListDomain returnDomain = new ViewRandomGenerationFieldTypeParameterListDomain();

				returnDomain.setRowSeqNo(rstDtoList.get(i).getRowSeqNo());
				returnDomain.setParameterNo(rstDtoList.get(i).getParameterNo());
				returnDomain.setFieldTypeName(rstDtoList.get(i).getFieldTypeName());
				returnDomain.setFieldTypeLevel(rstDtoList.get(i).getFieldTypeLevel());
				returnDomain.setParameterDescription(rstDtoList.get(i).getParameterDescription());
				returnDomain.setParameterExample(rstDtoList.get(i).getParameterExample());
				returnDomain.setParameterDefaultValue(rstDtoList.get(i).getParameterDefaultValue());
				returnDomain.setParameterName(rstDtoList.get(i).getParameterName());
				returnDomain.setParameterType(rstDtoList.get(i).getParameterType());

				returnList.add(returnDomain);
			}
		}
		return returnList;
	}

	/***
	* 랜덤 생성 규칙 상세조회 -> 우선은 중복 검사하는 곳에만 쓰임
	*
	* @param domain
	* @return
	*/
	public ViewRandomRuleFieldMappingDomain selectRandomGenerationRuleDetail(ViewRandomRuleFieldMappingDomain rgrDomain) {
		ViewRandomRuleFieldMappingDomain returnDomain = null;

		RuleManagementDto rgaDto = new RuleManagementDto();
		// 레벨에 따른 검색, 유형 API에 따른 검색, 필드 유형 이름에 대한 검색으로 나눌까 고려중
		rgaDto.setRuleNo(rgrDomain.getRuleNo());

		RuleManagementDto rstDto = randomGenerationMngAlgorithmMapper.selectRandomGenerationRuleDetail(rgaDto);

		if (rstDto != null) {
			returnDomain = new ViewRandomRuleFieldMappingDomain();

			returnDomain.setRuleNo                 (rstDto.getRuleNo                 ());
			returnDomain.setRuleName               (rstDto.getRuleName               ());
			returnDomain.setRuleDescription        (rstDto.getRuleDescription        ());
			returnDomain.setAlgorithmLevel         (rstDto.getAlgorithmLevel         ());
			returnDomain.setGeneratedTestcaseCount (rstDto.getGeneratedTestcaseCount ());
			returnDomain.setMethodNo               (rstDto.getMethodNo               ());
			returnDomain.setRuleFieldXml           (rstDto.getRuleFieldXml           ());
			returnDomain.setGenerator              (rstDto.getGenerator              ());
			returnDomain.setAmender                (rstDto.getAmender                ());
			returnDomain.setRevisionDate           (rstDto.getRevisionDate           ());
			returnDomain.setGenerateDate           (rstDto.getGenerateDate           ());
			returnDomain.setMethodName             (rstDto.getMethodName             ());
			returnDomain.setFile_no                (rstDto.getFile_no                ());
			returnDomain.setFileName               (rstDto.getFileName               ());

		}
		return returnDomain;
	}
}

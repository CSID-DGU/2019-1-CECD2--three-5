package com.sparrow.servicetester.warehouse.service;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationFieldTypeParameterListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationAlgorithmListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationAlgorithmListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationAlgorithmListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationAlgorithmParameterListDomain;
import com.sparrow.servicetester.warehouse.exception.SSTException;
import com.sparrow.servicetester.warehouse.repository.RandomGenerationMngAlgorithmRepository;
import com.sparrow.servicetester.warehouse.util.RowTypes;

@Service
public class RandomGenerationMngAlgorithmService {

	@Inject
	private RandomGenerationMngAlgorithmRepository randomGenerationMngAlgorithmRepository;

	/***
	 * 랜덤 생성 필드 유형 목록 조회 개수
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ViewRandomGenerationFieldTypeListDomain viewRandomGenerationFieldTypeListCnt(@Valid ViewRandomGenerationFieldTypeListDomain domain) {
		ViewRandomGenerationFieldTypeListDomain totalCntInfo = randomGenerationMngAlgorithmRepository.selectRandomGenerationFieldTypeListCnt(domain);
		return totalCntInfo;
	}

	/***
	 * 랜덤 생성 필드 유형 목록 조회
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ViewRandomGenerationFieldTypeListDomain> viewRandomGenerationFieldTypeList(
			@Valid ViewRandomGenerationFieldTypeListDomain domain) {
		List<ViewRandomGenerationFieldTypeListDomain> rgaList = randomGenerationMngAlgorithmRepository
				.selectRandomGenerationFieldTypeList(domain);
		return rgaList;
	}
	/***
	 * 랜덤 생성 필드 유형 추가
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int addRandomGenerationFieldType(AddRandomGenerationFieldTypeDomain domain) throws Exception {
		// 중복 체크
		AddRandomGenerationFieldTypeDomain duplicatedDomain 
		= randomGenerationMngAlgorithmRepository.selectRandomGenerationFieldType(domain);
		if (duplicatedDomain != null) {
			throw new SSTException("message.exception.duplicate"); // 이미 존재하는 정보 입니다.
		}

		// TODO 세션 으로 저장 처리
		domain.setGenerator("이장행 (testuser_01)");
		domain.setAmender("이장행 (testuser_01)");
		int resultCnt = randomGenerationMngAlgorithmRepository.insertRandomGenerationFieldType(domain);

		return resultCnt;
	}
	
    /***
     * 랜덤 생성 필드 유형 수정
     * @param addDomain
     * @return
     * @throws Exception
     */
    @Transactional
    public int modifyRandomGenerationFieldType(ModifyRandomGenerationFieldTypeDomain domain)  throws Exception {

    	//TODO 세션 으로 저장 처리
    	domain.setGenerator("최현석 (testuser_01)");
    	domain.setAmender("최현석 (testuser_01)");
    	int resultCnt = randomGenerationMngAlgorithmRepository.updateRandomGenerationFieldType(domain);

    	return resultCnt;
    }

	/***
	 * 랜덤 생성 필드 유형 단건 삭제
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationFieldType(@Valid RemoveRandomGenerationFieldTypeDomain domain) throws Exception {
    	int resultCnt = randomGenerationMngAlgorithmRepository.deleteRandomGenerationFieldType(domain);
    	return resultCnt;
	}
	
	/***
	 * 랜덤 생성 필드 유형 다건 삭제
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationFieldTypeList(@Valid RemoveRandomGenerationFieldTypeListDomain domain) {
		List<RemoveRandomGenerationFieldTypeDomain> removeList = domain.getRemoveList();
		int resultCnt = 0;
		for (int i = 0; i < removeList.size(); i++) {
			RemoveRandomGenerationFieldTypeDomain deleteDomain = new RemoveRandomGenerationFieldTypeDomain();
			deleteDomain.setFieldTypeNo(removeList.get(i).getFieldTypeNo());
			resultCnt += randomGenerationMngAlgorithmRepository.deleteRandomGenerationFieldType(deleteDomain);
		}
		return resultCnt;
	}

	/***
	 * 랜덤 생성 필드 유형 다건 저장
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int saveRandomGenerationFieldTypeList(SaveRandomGenerationFieldTypeListDomain domain) throws Exception {

		List<SaveRandomGenerationFieldTypeDomain> algorithmList = domain.getSaveList();

		int resultCnt = 0;
		for (int i = 0; i < algorithmList.size(); i++) {
			SaveRandomGenerationFieldTypeDomain groupDmn = algorithmList.get(i);
			// TODO 세션 으로 저장 처리
			groupDmn.setGenerator("이장행 (testuser_01)");
			groupDmn.setAmender("이장행 (testuser_01)");

    		//추가
			if(RowTypes.isRowInsert(groupDmn.getRowType())) {
				resultCnt += randomGenerationMngAlgorithmRepository.insertRandomGenerationFieldType(groupDmn);
			}
			//수정
			else if(RowTypes.isRowUpdate(groupDmn.getRowType())) {
				resultCnt += randomGenerationMngAlgorithmRepository.updateRandomGenerationFieldType(groupDmn);
			}
		}
		return resultCnt;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 총 개수
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	public ViewRandomGenerationAlgorithmListDomain viewRandomGenerationAlgorithmListCnt(
			@Valid ViewRandomGenerationAlgorithmListDomain domain) {
		ViewRandomGenerationAlgorithmListDomain totalCntInfo = randomGenerationMngAlgorithmRepository.selectRandomGenerationAlgorithmListCnt(domain);
		return totalCntInfo;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 조회
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	public List<ViewRandomGenerationAlgorithmListDomain> viewRandomGenerationAlgorithmList(
			@Valid ViewRandomGenerationAlgorithmListDomain domain) {
		List<ViewRandomGenerationAlgorithmListDomain> rgaList = randomGenerationMngAlgorithmRepository
				.selectRandomGenerationAlgorithmList(domain);
		return rgaList;
	}
	
	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 추가
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int addRandomGenerationAlgorithm(AddRandomGenerationAlgorithmDomain domain) throws Exception {
		// 중복 체크
		AddRandomGenerationAlgorithmDomain duplicatedDomain 
		= randomGenerationMngAlgorithmRepository.selectRandomGenerationAlgorithm(domain);
		if (duplicatedDomain != null) {
			throw new SSTException("message.exception.duplicate"); // 이미 존재하는 정보 입니다.
		}

		// TODO 세션 으로 저장 처리
		domain.setGenerator("이장행 (testuser_01)");
		domain.setAmender("이장행 (testuser_01)");
		int resultCnt = randomGenerationMngAlgorithmRepository.insertRandomGenerationAlgorithm(domain);

		return resultCnt;
	}
	
    /***
     * 랜덤 생성 알고리즘 기본 등록 수정
     * @param addDomain
     * @return
     * @throws Exception
     */
    @Transactional
    public int modifyRandomGenerationAlgorithm(ModifyRandomGenerationAlgorithmDomain domain)  throws Exception {

    	//TODO 세션 으로 저장 처리
    	domain.setGenerator("최현석 (testuser_01)");
    	domain.setAmender("최현석 (testuser_01)");
    	int resultCnt = randomGenerationMngAlgorithmRepository.updateRandomGenerationAlgorithm(domain);

    	return resultCnt;
    }
    
	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 단건 삭제
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationAlgorithm(@Valid RemoveRandomGenerationAlgorithmDomain domain) throws Exception {
    	int resultCnt = randomGenerationMngAlgorithmRepository.deleteRandomGenerationAlgorithm(domain);
    	return resultCnt;
	}
	
	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 다건 삭제
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationAlgorithmList(@Valid RemoveRandomGenerationAlgorithmListDomain domain) {
		List<RemoveRandomGenerationAlgorithmDomain> removeList = domain.getRemoveList();
		int resultCnt = 0;
		for (int i = 0; i < removeList.size(); i++) {
			RemoveRandomGenerationAlgorithmDomain deleteDomain = new RemoveRandomGenerationAlgorithmDomain();
			deleteDomain.setAlgorithmNo(removeList.get(i).getAlgorithmNo());
			resultCnt += randomGenerationMngAlgorithmRepository.deleteRandomGenerationAlgorithm(deleteDomain);
		}
		return resultCnt;
	}
	
	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 다건 저장
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int saveRandomGenerationAlgorithmList(SaveRandomGenerationAlgorithmListDomain domain) throws Exception {

		List<SaveRandomGenerationAlgorithmDomain> saveList = domain.getSaveList();

		int resultCnt = 0;
		for (int i = 0; i < saveList.size(); i++) {
			SaveRandomGenerationAlgorithmDomain groupDmn = saveList.get(i);
			// TODO 세션 으로 저장 처리
			groupDmn.setGenerator("이장행 (testuser_01)");
			groupDmn.setAmender("이장행 (testuser_01)");

    		//추가
			if(RowTypes.isRowInsert(groupDmn.getRowType())) {
				resultCnt += randomGenerationMngAlgorithmRepository.insertRandomGenerationAlgorithm(groupDmn);
			}
			//수정
			else if(RowTypes.isRowUpdate(groupDmn.getRowType())) {
				resultCnt += randomGenerationMngAlgorithmRepository.updateRandomGenerationAlgorithm(groupDmn);
			}
		}
		return resultCnt;
	}

	/***
	 * 랜덤 생성 알고리즘 파라미터 목록 조회 개수
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ViewRandomGenerationAlgorithmParameterListDomain viewRandomGenerationAlgorithmParameterListCnt(
			@Valid ViewRandomGenerationAlgorithmParameterListDomain domain) {
		ViewRandomGenerationAlgorithmParameterListDomain totalCntInfo = randomGenerationMngAlgorithmRepository.selectRandomGenerationAlgorithmParameterListCnt(domain);
		return totalCntInfo;
	}

	/***
	 * 랜덤 생성 알고리즘 파라미터 목록 조회
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ViewRandomGenerationAlgorithmParameterListDomain> viewRandomGenerationAlgorithmParameterList(
			@Valid ViewRandomGenerationAlgorithmParameterListDomain domain) {
		List<ViewRandomGenerationAlgorithmParameterListDomain> rgaList = randomGenerationMngAlgorithmRepository
				.selectRandomGenerationAlgorithmParameterList(domain);
		return rgaList;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 조회 개수
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ViewRandomGenerationRuleListDomain viewRandomGenerationRuleListCnt(
			@Valid ViewRandomGenerationRuleListDomain domain) {
		ViewRandomGenerationRuleListDomain totalCntInfo = randomGenerationMngAlgorithmRepository.selectRandomGenerationRuleCnt(domain);
		
		return totalCntInfo;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 조회
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ViewRandomGenerationRuleListDomain> viewRandomGenerationRuleList(
			@Valid ViewRandomGenerationRuleListDomain domain) {
		List<ViewRandomGenerationRuleListDomain> randomGenRuleList = randomGenerationMngAlgorithmRepository
				.selectRandomGenerationAlgorithmRuleList(domain);
		
		return randomGenRuleList;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 추가
	 * 
	 * @param domain
	 * @return
	 * @throws SSTException 
	 * @throws Exception
	 */
	@Transactional
	public int addRandomGenerationRule(@Valid AddRandomGenerationRuleDomain domain) throws SSTException {
		// 중복 체크
		AddRandomGenerationRuleDomain duplicatedDomain 
		= randomGenerationMngAlgorithmRepository.selectRandomGenerationRule(domain);
		if (duplicatedDomain != null) {
			throw new SSTException("message.exception.duplicate"); // 이미 존재하는 정보 입니다.
		}

		// TODO 세션 으로 저장 처리
		domain.setGenerator("이장행 (testuser_01)");
		domain.setAmender("이장행 (testuser_01)");
		int resultCnt = randomGenerationMngAlgorithmRepository.insertRandomGenerationRule(domain);

		return resultCnt;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 수정
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int modifyRandomGenerationRule(@Valid ModifyRandomGenerationRuleDomain domain) {
    	//TODO 세션 으로 저장 처리
    	domain.setGenerator("최현석 (testuser_01)");
    	domain.setAmender("최현석 (testuser_01)");
    	int resultCnt = randomGenerationMngAlgorithmRepository.updateRandomGenerationRule(domain);

    	return resultCnt;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 단건 삭제
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationRule(@Valid RemoveRandomGenerationRuleDomain domain) {
    	int resultCnt = randomGenerationMngAlgorithmRepository.deleteRandomGenerationRule(domain);
    	return resultCnt;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 다건 삭제
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationRuleList(@Valid RemoveRandomGenerationRuleListDomain domain) {
		List<RemoveRandomGenerationRuleDomain> removeList = domain.getRemoveList();
		int resultCnt = 0;
		for (int i = 0; i < removeList.size(); i++) {
			RemoveRandomGenerationRuleDomain deleteDomain = new RemoveRandomGenerationRuleDomain();
			deleteDomain.setRuleNo(removeList.get(i).getRuleNo());
			resultCnt += randomGenerationMngAlgorithmRepository.deleteRandomGenerationRule(deleteDomain);
		}
		return resultCnt;
	}

	/***
	 * 랜덤 생성 규칙 설정 다건 저장
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int saveRandomGenerationRuleList(@Valid SaveRandomGenerationRuleListDomain domain) {
		List<SaveRandomGenerationRuleDomain> saveList = domain.getSaveList();

		int resultCnt = 0;
		for (int i = 0; i < saveList.size(); i++) {
			SaveRandomGenerationRuleDomain groupDmn = saveList.get(i);
			// TODO 세션 으로 저장 처리
			groupDmn.setGenerator("이장행 (testuser_01)");
			groupDmn.setAmender("이장행 (testuser_01)");

    		//추가
			if(RowTypes.isRowInsert(groupDmn.getRowType())) {
				resultCnt += randomGenerationMngAlgorithmRepository.insertRandomGenerationRule(groupDmn);
			}
			//수정
			else if(RowTypes.isRowUpdate(groupDmn.getRowType())) {
				resultCnt += randomGenerationMngAlgorithmRepository.updateRandomGenerationRule(groupDmn);
			}
		}
		return resultCnt;
	}
	
	/***
	 * 랜덤 생성 규칙 파라미터 총 개수 조회
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */	
	@Transactional
	public ViewRandomGenerationFieldTypeParameterListDomain viewRandomGenerationFieldTypeParameterListCnt(@Valid ViewRandomGenerationFieldTypeParameterListDomain domain) {
		ViewRandomGenerationFieldTypeParameterListDomain totalCntInfo = randomGenerationMngAlgorithmRepository.selectRandomGenerationFieldTypeParameterListCnt(domain);
		return totalCntInfo;
	}
	
	/***
	 * 랜덤 생성 규칙 파라미터 목록 조회
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ViewRandomGenerationFieldTypeParameterListDomain> viewRandomGenerationFieldTypeParameterList(
			@Valid ViewRandomGenerationFieldTypeParameterListDomain domain) {
		List<ViewRandomGenerationFieldTypeParameterListDomain> rgaList = randomGenerationMngAlgorithmRepository.selectRandomGenerationFieldTypeParameterList(domain);
		
		return rgaList;
	}
}
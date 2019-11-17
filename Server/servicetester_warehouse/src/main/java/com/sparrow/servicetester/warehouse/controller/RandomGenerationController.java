/*
 * 작성일 : 2019.07.16
 * 작성자 : 이장행
 * 미완 상태
 */

package com.sparrow.servicetester.warehouse.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sparrow.servicetester.warehouse.domain.ResponseDataMap;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.AddRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationReflectionDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationFieldTypeParameterListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ModifyRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationAlgorithmDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationAlgorithmListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationFieldTypeDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationHistoryDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationRuleDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationAlgorithmListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SaveRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationAlgorithmListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationFieldTypeListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationAlgorithmParameterListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseDetailDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseListDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.ViewTestcaseDomain;
import com.sparrow.servicetester.warehouse.exception.SSTException;
import com.sparrow.servicetester.warehouse.service.RandomGenerationMngAlgorithmService;
import com.sparrow.servicetester.warehouse.service.RandomGenerationService;
import com.sparrow.servicetester.warehouse.util.SSTExceptionUtils;
import com.sparrow.servicetester.warehouse.util.STStateUtils;
import com.sparrow.servicetester.warehouse.util.ValidationUtils;

@RestController
@RequestMapping("/randomGeneration")
public class RandomGenerationController {
	/*********************** logger ************************************/
	static final Logger logger = LoggerFactory.getLogger(RandomGenerationController.class);

	/***********************
	 * environment config
	 ************************************/
	@Inject
	private Environment environment;

	/*********************** service ***********************************/
	@Autowired
	private RandomGenerationService randomGenerationService;
	@Autowired
	private RandomGenerationMngAlgorithmService randomGenerationMngAlgorithmService;

	/*********************** utils ************************************/
	@Autowired
	private ValidationUtils validationUtils;
	@Autowired
	private SSTExceptionUtils sstExceptionUtils;
	@Autowired
	private STStateUtils stStateUtils;

	// 랜덤 생성 실행
	@RequestMapping(value = "/executeRandomGeneration/{uuid}", method = RequestMethod.POST)
	public ResponseDataMap executeRandomGeneration(@PathVariable String uuid,
			@Valid @RequestBody ExecuteRandomGenerationDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************* 기본 데이터 생성 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************* 유효성 체크 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************* 로직 수행 **********************************************/
		int totalCnt = 0;
		try {
			domain.setExecutionUuid(domain.getUuid());
			totalCnt = randomGenerationService.executeRandomGeneration(domain);
		} catch (SSTException e) {
			sstExceptionUtils.setError(e, responseDataMap);
			try {
				// 오류 처리된 메시지를 가져와서 진행 상태 로그를 뿌려준다.
				stStateUtils.sendState(0, domain.getExecutionUuid(),
						((HashMap<String, Object>) responseDataMap.getTailStatus("errorInfo")).get("returnMessage")
								+ "");
			} catch (URISyntaxException | InterruptedException e1) {
				sstExceptionUtils.setError(e1, responseDataMap);
			}
		} catch (Exception e) {
			sstExceptionUtils.setError(e, responseDataMap);
			try {
				stStateUtils.sendState(0, domain.getExecutionUuid(), "오류 발생!!!! 랜덤 생성 중 에러가 발생하였습니다.");
			} catch (URISyntaxException | InterruptedException e1) {
				sstExceptionUtils.setError(e1, responseDataMap);
			}
		}

		/********************************* 결과값 셋팅 후 보냄 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤생성 이력 목록 총 갯수 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationHistoryCnt/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationHistoryCnt(@PathVariable String uuid,
			@Valid ViewRandomGenerationHistoryListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		ViewRandomGenerationHistoryListDomain totalCnt = null;
		try {
			totalCnt = randomGenerationService.viewRandomGenerationHistoryListCnt(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseMap.putData("countInfo", totalCnt);

		return responseMap;
	}

	/***
	 * 랜덤생성 이력 목록 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationHistoryList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationHistoryList(@PathVariable String uuid,
			@Valid ViewRandomGenerationHistoryListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Random Generation..");
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment); // 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		List<ViewRandomGenerationHistoryListDomain> randomGenHistList = null;
		int totalCnt = 0;
		try {
			// 총 갯수 요청이 있으면 조회
			totalCnt = randomGenerationService.viewRandomGenerationHistoryListCnt(domain).getTotalCnt();

			randomGenHistList = randomGenerationService.viewRandomGenerationHistoryList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("randomGenHistList", randomGenHistList);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 이력 단건 삭제
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationHistory/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationHistory(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationHistoryDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n\n\n" + domain.getExecutionUuid());

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationService.removeRandomGenerationHistory(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		// 로그인 정보 셋팅
		responseDataMap.putData("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 이력 다건 삭제
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationHistoryList/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationHistoryList(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationHistoryListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationService.removeRandomGenerationHistoryList(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		// 로그인 정보 셋팅
		responseDataMap.putData("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 테스트케이스 목록 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping(value = "/viewRandomGenerationTestcaseList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationTestcaseList(@PathVariable String uuid,
			@Valid ViewRandomGenerationTestcaseListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) throws Throwable {
		logger.info("Random Generation TestcaseList..");

		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment); // 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		List<ViewRandomGenerationTestcaseListDomain> testcaseListInfo = null;
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationService.viewRandomGenerationTestcaseList(domain).size();
			testcaseListInfo = randomGenerationService.viewRandomGenerationTestcaseList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("testcaseListInfo", testcaseListInfo);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 테스트케이스 목록 반영
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/executeRandomGenerationReflection/{uuid}", method = RequestMethod.POST)
	public ResponseDataMap executeRandomGenerationReflection(@PathVariable String uuid,
			@Valid ExecuteRandomGenerationReflectionDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Random Generation Reflection");

		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationService.executeRandomGenerationReflection(domain);
		} catch (Exception e) {
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 테스트케이스 단일 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewTestcase/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationTestcase(@PathVariable String uuid,
			@Valid ViewRandomGenerationTestcaseDetailDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Random Generation Testcase");

		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		Map<String, Object> overviewInfo = null ;
		try {
			overviewInfo = randomGenerationService.viewTestcaseEdit(domain);
		} catch (Exception e) {
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/******************************** 결과값 셋팅 후 보냄 ***************************************/
		responseDataMap.putData("testcaseInfo",overviewInfo);

        return responseDataMap;
	}
	
	/***
	 * 랜덤 생성 필드 유형 목록 총 개수
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationFieldTypeListCnt/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationFieldTypeListCnt(@PathVariable String uuid,
			@Valid ViewRandomGenerationFieldTypeListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationFieldTypeListCnt(domain).getTotalCnt();
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseMap.putData("totalCnt", totalCnt);

		return responseMap;
	}

	/***
	 * 랜덤 생성 필드 유형 목록 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationFieldTypeList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationFieldTypeList(@PathVariable String uuid,
			@Valid ViewRandomGenerationFieldTypeListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Random Generation Algorithm..");

		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment); // 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		List<ViewRandomGenerationFieldTypeListDomain> randomGenFieldTypeList = null;
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationFieldTypeListCnt(domain).getTotalCnt();
			randomGenFieldTypeList = randomGenerationMngAlgorithmService.viewRandomGenerationFieldTypeList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("randomGenFieldTypeList", randomGenFieldTypeList);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 필드 유형 추가
	 *
	 * @param uuid
	 * @param addRandomGenerationFieldTypeDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addRandomGenerationFieldType/{uuid}", method = RequestMethod.POST)
	public ResponseDataMap addRandomGenerationFieldType(@PathVariable String uuid,
			@Valid @RequestBody AddRandomGenerationFieldTypeDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.addRandomGenerationFieldType(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 필드 유형 수정
	 *
	 * @param uuid
	 * @param ModifyRandomGenerationFieldTypeDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/modifyRandomGenerationFieldType/{uuid}", method = RequestMethod.PUT)
	public ResponseDataMap modifyRandomGenerationFieldType(@PathVariable String uuid,
			@Valid @RequestBody ModifyRandomGenerationFieldTypeDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.modifyRandomGenerationFieldType(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		// 로그인 정보 셋팅
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 필드 유형 단건 삭제
	 *
	 * @param uuid
	 * @param RemoveRandomGenerationFieldTypeDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationFieldType/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationFieldType(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationFieldTypeDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);
		logger.info("called 랜덤 알고리즘 삭제");

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.removeRandomGenerationFieldType(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 필드 유형 다건 삭제
	 *
	 * @param uuid
	 * @param RemoveRandomGenerationFieldTypeListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationFieldTypeList/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationFieldTypeList(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationFieldTypeListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);
		logger.info("called 랜덤 알고리즘 삭제");

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.removeRandomGenerationFieldTypeList(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 필드 유형 다건 저장
	 *
	 * @param uuid
	 * @param SaveRandomGenerationFieldTypeListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveRandomGenerationFieldTypeList/{uuid}", method = RequestMethod.POST)
	public ResponseDataMap saveRandomGenerationFieldTypeList(@PathVariable String uuid,
			@Valid @RequestBody SaveRandomGenerationFieldTypeListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.saveRandomGenerationFieldTypeList(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 총 개수
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationAlgorithmListCnt/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationAlgorithmListCnt(@PathVariable String uuid,
			@Valid ViewRandomGenerationAlgorithmListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		ViewRandomGenerationAlgorithmListDomain totalCnt = null;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationAlgorithmListCnt(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseMap.putData("countInfo", totalCnt);

		return responseMap;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 목록 조회
	 *
	 * @param uuid
	 * @param ViewRandomGenerationAlgorithmListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationAlgorithmList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationAlgorithmList(@PathVariable String uuid,
			@Valid ViewRandomGenerationAlgorithmListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Random Generation Algorithm");

		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment); // 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		List<ViewRandomGenerationAlgorithmListDomain> randomGenAlgorithmList = null;
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationAlgorithmListCnt(domain)
					.getTotalCnt();
			randomGenAlgorithmList = randomGenerationMngAlgorithmService
					.viewRandomGenerationAlgorithmList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("randomGenAlgorithmList", randomGenAlgorithmList);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 추가
	 *
	 * @param uuid
	 * @param addRandomGenerationAlgorithmDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addRandomGenerationAlgorithm", method = RequestMethod.POST)
	public ResponseDataMap addRandomGenerationAlgorithm(@PathVariable String uuid,
			@Valid @RequestBody AddRandomGenerationAlgorithmDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.addRandomGenerationAlgorithm(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 수정
	 *
	 * @param uuid
	 * @param ModifyRandomGenerationAlgorithmDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/modifyRandomGenerationAlgorithm/{uuid}", method = RequestMethod.PUT)
	public ResponseDataMap modifyAgent(@PathVariable String uuid,
			@Valid @RequestBody ModifyRandomGenerationAlgorithmDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.modifyRandomGenerationAlgorithm(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		// 로그인 정보 셋팅
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 단건 삭제
	 *
	 * @param uuid
	 * @param RemoveRandomGenerationAlgorithmDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationAlgorithm/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationAlgorithm(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationAlgorithmDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);
		logger.info("called 랜덤 알고리즘 삭제");

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.removeRandomGenerationAlgorithm(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 다건 삭제
	 *
	 * @param uuid
	 * @param RemoveRandomGenerationAlgorithmListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationAlgorithmList/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationAlgorithmList(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationAlgorithmListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);
		logger.info("called 랜덤 알고리즘 삭제");

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.removeRandomGenerationAlgorithmList(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 알고리즘 기본 등록 다건 저장
	 *
	 * @param uuid
	 * @param SaveRandomGenerationAlgorithmListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveRandomGenerationAlgorithmList/{uuid}", method = RequestMethod.POST)
	public ResponseDataMap saveRandomGenerationAlgorithmList(@PathVariable String uuid,
			@Valid @RequestBody SaveRandomGenerationAlgorithmListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.saveRandomGenerationAlgorithmList(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 총 개수
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationRuleListCnt/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationRuleListCnt(@PathVariable String uuid,
			@Valid ViewRandomGenerationRuleListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationRuleListCnt(domain).getTotalCnt();
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseMap.putData("countInfo", totalCnt);

		return responseMap;
	}

	/***
	 * 랜덤 생성 규칙 설정 목록 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationRuleList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationRuleList(@PathVariable String uuid,
			@Valid ViewRandomGenerationRuleListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Random Generation Algorithm..");

		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment); // 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		List<ViewRandomGenerationRuleListDomain> randomGenRuleList = null;
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationRuleListCnt(domain).getTotalCnt();
			randomGenRuleList = randomGenerationMngAlgorithmService.viewRandomGenerationRuleList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("randomGenRuleList", randomGenRuleList);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 규칙 목록 추가
	 *
	 * @param uuid
	 * @param addRandomGenerationRuleDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addRandomGenerationRule", method = RequestMethod.POST)
	public ResponseDataMap addRandomGenerationRule(@PathVariable String uuid,
			@Valid @RequestBody AddRandomGenerationRuleDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.addRandomGenerationRule(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 규칙 목록 수정
	 *
	 * @param uuid
	 * @param ModifyRandomGenerationRuleDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/modifyRandomGenerationRule/{uuid}", method = RequestMethod.PUT)
	public ResponseDataMap modifyRandomGenerationRule(@PathVariable String uuid,
			@Valid @RequestBody ModifyRandomGenerationRuleDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.modifyRandomGenerationRule(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		// 로그인 정보 셋팅
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 규칙 목록 단건 삭제
	 *
	 * @param uuid
	 * @param RemoveRandomGenerationRuleDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationRule/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationRule(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationRuleDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);
		logger.info("called 랜덤 알고리즘 삭제");

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.removeRandomGenerationRule(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 규칙 목록 다건 삭제
	 *
	 * @param uuid
	 * @param RemoveRandomGenerationRuleListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/removeRandomGenerationRuleList/{uuid}", method = RequestMethod.DELETE)
	public ResponseDataMap removeRandomGenerationRuleList(@PathVariable String uuid,
			@Valid @RequestBody RemoveRandomGenerationRuleListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);
		logger.info("called 랜덤 알고리즘 삭제");

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.removeRandomGenerationRuleList(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 규칙 목록 다건 저장
	 *
	 * @param uuid
	 * @param SaveRandomGenerationRuleListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveRandomGenerationRuleList/{uuid}", method = RequestMethod.POST)
	public ResponseDataMap saveRandomGenerationRuleList(@PathVariable String uuid,
			@Valid @RequestBody SaveRandomGenerationRuleListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);
		// 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 *********************************************/
		int resultCnt = 0;
		try {
			resultCnt = randomGenerationMngAlgorithmService.saveRandomGenerationRuleList(domain);
		} catch (Exception e) {
			// 입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 **************************************/
		responseDataMap.put("resultCnt", resultCnt);

		return responseDataMap;
	}
	/***
	 * 랜덤 생성 알고리즘 파라미터 목록 조회
	 *
	 * @param uuid
	 * @param ViewRandomGenerationAlgorithmParameterListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationAlgorithmParameterList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewRandomGenerationAlgorithmParameterList(@PathVariable String uuid,
			@Valid ViewRandomGenerationAlgorithmParameterListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Random Generation..");

		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment); // 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		List<ViewRandomGenerationAlgorithmParameterListDomain> randomGenAlgorithmParamList = null;
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationAlgorithmParameterListCnt(domain)
					.getTotalCnt();
			randomGenAlgorithmParamList = randomGenerationMngAlgorithmService
					.viewRandomGenerationAlgorithmParameterList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("randomGenAlgorithmParamList", randomGenAlgorithmParamList);

		return responseDataMap;
	}

	/***
	 * 랜덤 생성 필드유형 파라미터 목록 조회
	 *
	 * @param uuid
	 * @param ViewRandomGenerationFieldTypeParameterListDomain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewRandomGenerationFieldTypeParameterList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap modifyRandomGenerationFieldTypeParameterList(@PathVariable String uuid,
			@Valid ViewRandomGenerationFieldTypeParameterListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		/********************************
		 * 기본 데이터 생성
		 ****************************************/
		// 리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment); // 사용 언어 및 uuid 세팅
		// domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/********************************
		 * 유효성 체크
		 *******************************************/
		// validation을 수행한다.
		if (!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			// 유효성을 통과 못하면 화면으로 리턴한다.
			// responseDataMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/********************************
		 * 로직 수행
		 **********************************************/
		List<ViewRandomGenerationFieldTypeParameterListDomain> randomGenFieldTypeParamList = null;
		int totalCnt = 0;
		try {
			totalCnt = randomGenerationMngAlgorithmService.viewRandomGenerationFieldTypeParameterListCnt(domain).getTotalCnt();
			randomGenFieldTypeParamList = randomGenerationMngAlgorithmService.viewRandomGenerationFieldTypeParameterList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("randomGenFieldTypeParamList", randomGenFieldTypeParamList);

		return responseDataMap;
	}
}

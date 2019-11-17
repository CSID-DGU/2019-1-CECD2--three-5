package com.sparrow.servicetester.warehouse.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sparrow.servicetester.warehouse.domain.ResponseDataMap;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ExecutePairwiseGenerationDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationTestcaseListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseListDomain;
import com.sparrow.servicetester.warehouse.exception.SSTException;
import com.sparrow.servicetester.warehouse.service.PairwiseGenerationService;
import com.sparrow.servicetester.warehouse.util.SSTExceptionUtils;
import com.sparrow.servicetester.warehouse.util.STStateUtils;
import com.sparrow.servicetester.warehouse.util.ValidationUtils;

@Controller
@RestController
@RequestMapping("/pairwiseGeneration")
public class PairwiseGenerationController {
	/*********************** logger ************************************/
	static final Logger logger = LoggerFactory.getLogger(PairwiseGenerationController.class);

	/***********************
	 * environment config
	 ************************************/
	@Inject
	private Environment environment;

	/*********************** service ***********************************/
	@Inject
	PairwiseGenerationService pairwiseGenerationService;
	
	/*********************** utils ************************************/
	@Autowired
	private ValidationUtils validationUtils;
	@Autowired
	private SSTExceptionUtils sstExceptionUtils;
	@Autowired
	private STStateUtils stStateUtils;
	
	// 조합 생성 실행
	@RequestMapping(value = "/executePairwiseGeneration/{uuid}", method = RequestMethod.POST)
	public ResponseDataMap executePairwiseGeneration(@PathVariable String uuid,
			@Valid @RequestBody ExecutePairwiseGenerationDomain domain, BindingResult bindingResult,
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
			totalCnt = pairwiseGenerationService.executePairwiseGeneration(domain);
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
				stStateUtils.sendState(0, domain.getExecutionUuid(), "오류 발생!!!! 조합 생성 중 에러가 발생하였습니다.");
			} catch (URISyntaxException | InterruptedException e1) {
				sstExceptionUtils.setError(e1, responseDataMap);
			}
		}

		/********************************* 결과값 셋팅 후 보냄 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);

		return responseDataMap;
	}
	
	
	/***
	 * 조합 생성 이력 목록 총 갯수 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewPairwiseGenerationHistoryListCnt/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewPairwiseGenerationHistoryListCnt(@PathVariable String uuid,
			@Valid ViewPairwiseGenerationHistoryListDomain domain, BindingResult bindingResult,
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
		ViewPairwiseGenerationHistoryListDomain totalCnt = null;
		try {
			totalCnt = pairwiseGenerationService.viewPairwiseGenerationHistoryListCnt(domain);
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
	 * 조합생성 이력 목록 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewPairwiseGenerationHistoryList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewPairwiseGenerationHistoryList(@PathVariable String uuid,
			@Valid ViewPairwiseGenerationHistoryListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Pairwise Generation..");
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
		List<ViewPairwiseGenerationHistoryListDomain> pairwiseGenHistList = null;
		int totalCnt = 0;
		try {
			// 총 갯수 요청이 있으면 조회
			totalCnt = pairwiseGenerationService.viewPairwiseGenerationHistoryListCnt(domain).getTotalCnt();

			pairwiseGenHistList = pairwiseGenerationService.viewPairwiseGenerationHistoryList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("pairwiseGenHistList", pairwiseGenHistList);

		return responseDataMap;
	}
	
	/***
	 * 조합 생성 테스트케이스 목록 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable 
	 */
	@RequestMapping(value = "/viewPairwiseGenerationTestcaseList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewPairwiseGenerationTestcaseList(@PathVariable String uuid,
			@Valid ViewPairwiseGenerationTestcaseListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) throws Throwable {
		logger.info("Pairwise Generation TestcaseList..");

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
		List<ViewPairwiseGenerationTestcaseListDomain> testcaseListInfo = null;
		int totalCnt = 0;
		try {
			totalCnt = pairwiseGenerationService.viewPairwiseGenerationTestcaseList(domain).size();
			testcaseListInfo = pairwiseGenerationService.viewPairwiseGenerationTestcaseList(domain);
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
	 * 조합 생성 규칙 설정 목록 총 개수
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewPairwiseGenerationRuleListCnt/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewPairwiseGenerationRuleListCnt(@PathVariable String uuid,
			@Valid ViewPairwiseGenerationRuleListDomain domain, BindingResult bindingResult,
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
			totalCnt = pairwiseGenerationService.viewPairwiseGenerationRuleListCnt(domain).getTotalCnt();
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
	 * 조합 생성 규칙 설정 목록 조회
	 *
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/viewPairwiseGenerationRuleList/{uuid}", method = RequestMethod.GET)
	public ResponseDataMap viewPairwiseGenerationRuleList(@PathVariable String uuid,
			@Valid ViewPairwiseGenerationRuleListDomain domain, BindingResult bindingResult,
			HttpServletRequest request, HttpServletRequest response) {
		logger.info("Pairwise Generation Algorithm..");

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
		List<ViewPairwiseGenerationRuleListDomain> pairwiseGenRuleList = null;
		int totalCnt = 0;
		try {
			totalCnt = pairwiseGenerationService.viewPairwiseGenerationRuleListCnt(domain).getTotalCnt();
			pairwiseGenRuleList = pairwiseGenerationService.viewPairwiseGenerationRuleList(domain);
		} catch (Exception e) {
			logger.error(e.toString());
			sstExceptionUtils.setError(e, responseDataMap);
		}

		/********************************
		 * 결과값 셋팅 후 보냄
		 ***************************************/
		responseDataMap.putData("totalCnt", totalCnt);
		responseDataMap.putData("pairwiseGenRuleList", pairwiseGenRuleList);

		return responseDataMap;
	}
}

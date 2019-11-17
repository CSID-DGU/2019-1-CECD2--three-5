package com.sparrow.servicetester.warehouse.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sparrow.servicetester.warehouse.domain.ResponseDataMap;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomRuleFieldMappingDomain;
import com.sparrow.servicetester.warehouse.service.RandomGenerateService;
import com.sparrow.servicetester.warehouse.util.SSTExceptionUtils;
import com.sparrow.servicetester.warehouse.util.ValidationUtils;

/**
* 작성 개요 : 최영진(2019.8.21)
* 클래스 개요 : 랜덤 생성 Rest API 요청을 받기 위한 Controller 클래스
*/
@RestController
@RequestMapping("/randomGenerate")
public class RandomGenerateController {
	/*********************** logger ************************************/
	static final Logger logger = LoggerFactory.getLogger(RandomGenerateController.class);

	/*********************** environment config ************************************/
	@Inject
    private Environment environment;

	/*********************** service ***********************************/
	@Autowired
	private RandomGenerateService RandomGenerateService;

	/*********************** validator *********************************/

	/*********************** utils ************************************/
	@Autowired
	private ValidationUtils validationUtils;
	@Autowired
	private SSTExceptionUtils sstExceptionUtils;

	/***
	 * 랜덤 규칙 필드 매칭 조회
	 * @param uuid
	 * @param domain
	 * @param bindingResult
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/viewRandomRuleFieldMapping/{uuid}", method=RequestMethod.GET)
	public ResponseDataMap viewRandomRuleFieldMapping(@PathVariable String uuid,  @Valid ViewRandomRuleFieldMappingDomain domain
			, BindingResult bindingResult
			, HttpServletRequest request
			, HttpServletRequest response) {
		/******************************** 기본 데이터 생성 ****************************************/
		//리턴 객체 생성 (생성을 안해주면 call by reference을 통해 데이터를 셋팅 할 수 없다.)
		ResponseDataMap responseDataMap = new ResponseDataMap(environment);

		//사용 언어 및 uuid 세팅
		//domain은 꼭 CommonDomain을 상속 받아야한다.
		validationUtils.setRequestData(request, domain, uuid, responseDataMap);

		/******************************** 유효성 체크 *******************************************/
		//validation을 수행한다.
		if(!validationUtils.excuteValidator(null, domain, bindingResult, responseDataMap)) {
			//유효성을 통과 못하면 화면으로 리턴한다.
			//responseMap에 유효성 정보를 담겨져 있다.
			return responseDataMap;
		}

		/******************************** 로직 수행 *********************************************/
		Map<String, Object> resultMap = null;
		try {
			resultMap = RandomGenerateService.viewRandomRuleFieldMapping(domain);
		} catch (Exception e) {
			//입셉션 구분하여 처리하기
			sstExceptionUtils.setError(e, responseDataMap);
		}
		/******************************** 결과값 셋팅 후 보냄 **************************************/
		responseDataMap.putData("randomRuleFieldMapping", resultMap);
		return responseDataMap;
	}

}
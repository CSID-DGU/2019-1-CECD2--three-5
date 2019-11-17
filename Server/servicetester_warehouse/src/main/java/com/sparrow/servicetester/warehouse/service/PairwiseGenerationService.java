package com.sparrow.servicetester.warehouse.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sparrow.servicetester.util.STDom;
import com.sparrow.servicetester.util.STZip;
import com.sparrow.servicetester.warehouse.domain.agentmng.AddAgentDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ExecutePairwiseGenerationDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ExecutePairwiseGenerationInfoDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ExecutePairwiseGenerationRuleInfoDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationMethodDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationRuleListDomain;
import com.sparrow.servicetester.warehouse.domain.pairwisegeneration.ViewPairwiseGenerationTestcaseListDomain;
import com.sparrow.servicetester.warehouse.domain.projectmng.ProjectMngViewProjectDetailDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationAlgorithmInfoDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.SourceDomainDomain;
import com.sparrow.servicetester.warehouse.domain.upload.UploadCollectorAddSourceDomain;
import com.sparrow.servicetester.warehouse.exception.SSTException;
import com.sparrow.servicetester.warehouse.repository.AgentMngRepository;
import com.sparrow.servicetester.warehouse.repository.PairwiseGenerationRepository;
import com.sparrow.servicetester.warehouse.repository.ProjectMngRepository;
import com.sparrow.servicetester.warehouse.repository.TestcaseRepository;
import com.sparrow.servicetester.warehouse.repository.UploadCollectorRepository;
import com.sparrow.servicetester.warehouse.util.STStateUtils;

@Service
public class PairwiseGenerationService {
	static final Logger logger = LoggerFactory.getLogger(SourceCollectionService.class);

	/*********************** utils ************************************/
	@Inject
	private STStateUtils stStateUtils;

	@Inject
	Environment environment;

	@Inject
	TestcaseRepository testcaseRepository;

	@Inject
	PairwiseGenerationRepository pairwiseGenerationRepository;

	@Inject
	private UploadCollectorRepository uploadCollectorRepository;

	@Inject
	private AgentMngRepository agentMngRepository;

	@Inject
	private TestcaseService testcaseService;

	@Inject
	private ProjectMngRepository projectMngRepository;

	/***
	 * 조합 생성 실행
	 *
	 * @param rghomain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int executePairwiseGeneration(@Valid ExecutePairwiseGenerationDomain rgDomain) throws Exception {
		int totalCnt = 0;

		// 프로젝트 정보 조회
		ProjectMngViewProjectDetailDomain projectSchDomain = new ProjectMngViewProjectDetailDomain();
		projectSchDomain.setProjectNo(rgDomain.getProjectNo());
		ProjectMngViewProjectDetailDomain projectInfo = projectMngRepository.selectProjectDetail(projectSchDomain);
		if (projectInfo == null) {
			// 대상 정보가 없습니다.
			throw new SSTException("message.exception.noTarget", new String[] { "" });
		}

		// 메소드 정보 조회
		ViewPairwiseGenerationMethodDomain genInfoDomain = new ViewPairwiseGenerationMethodDomain();
		genInfoDomain.setMethodNo(rgDomain.getMethodNo());
		genInfoDomain.setRuleNo(rgDomain.getRuleNo());
		ViewPairwiseGenerationMethodDomain rgInfoDomain = pairwiseGenerationRepository.selectMethod(genInfoDomain);
		if (rgInfoDomain == null) {
			// 대상 정보가 없습니다.
			throw new SSTException("message.exception.noTarget", new String[] { "" });
		}

		stStateUtils.sendState(1, rgDomain.getExecutionUuid(), "조합 생성 요청 수신 완료");

		UploadCollectorAddSourceDomain exeHist = new UploadCollectorAddSourceDomain();
		exeHist.setExecutionUuid(rgDomain.getExecutionUuid());
		exeHist.setExecutionDivision("06");
		exeHist.setExecutionProjectNo(rgDomain.getProjectNo());
		exeHist.setRelationExecutionUuid("");
		exeHist.setExecutionCondition("P");
		exeHist.setExecutionProcessCount(0);
		exeHist.setExecutionDetailCondition(rgDomain.getRuleNo() + "");
		// TODO 세션처리
		exeHist.setExecutorId("testuser_01");
		// TODO 세션처리
		exeHist.setGenerator("최현석(testuser_01");
		uploadCollectorRepository.insertExecutionHistory(exeHist);

		
		int testSuiteCnt = 0;
		// Agent에 전송 할 데이터를 생성한다.
		// xml 객체 생성

		Element el = STDom.loadXml("<generator></generator>");

		Element generatorInfo = STDom.getOrCreateElement(el, "generatorInfo");
		STDom.getOrCreateElement(generatorInfo, "logLevel", "");
		STDom.getOrCreateElement(generatorInfo, "logback", "");
		STDom.getOrCreateElement(generatorInfo, "storage", "");
		STDom.getOrCreateElement(generatorInfo, "notify", "");

		STDom.getOrCreateElement(generatorInfo, "runHistoryNo", "");
		STDom.getOrCreateElement(generatorInfo, "executionUuid", rgDomain.getExecutionUuid());
		STDom.getOrCreateElement(generatorInfo, "generatorDiv", "P"); // A(Auto), P(Pairwise), R(Random)
		STDom.getOrCreateElement(generatorInfo, "targetLanguage", "Java"); // C | Java

		STDom.getOrCreateElement(generatorInfo, "testDesc",
				rgInfoDomain.getFolderFileName() + "_" + rgInfoDomain.getMethodName() + rgInfoDomain.getRuleName()// 일단
																													// 주석처리
																													// +
																													// Integer.toString(domain.getGenerationNo())
		);

		Element testSuite = STDom.createElement(el, "testsuite");
		testSuiteCnt++;
		testSuite.setAttribute("id", Integer.toString(testSuiteCnt));
		testSuite.setAttribute("at", "local"); //

		Element tsInfo = STDom.createElement(testSuite, "tsInfo");

		STDom.createElement(tsInfo, "projectName", projectInfo.getProjectName());
		STDom.createElement(tsInfo, "projectOwner", projectInfo.getProjectOwner());
		STDom.createElement(tsInfo, "generateCount", rgDomain.getGenerateCount()); // 입력받음
		STDom.createElement(tsInfo, "folder", rgInfoDomain.getFolderFilePath()); // st_folder_file
		STDom.createElement(tsInfo, "filename", rgInfoDomain.getFolderFileName()); // st_folder_file_name
		STDom.createElement(tsInfo, "function", rgInfoDomain.getMethodName()); //
		STDom.createElement(tsInfo, "functionNo", rgInfoDomain.getMethodNo() + "");
		STDom.createElement(tsInfo, "desc", rgInfoDomain.getFolderFileName() + "_" + rgInfoDomain.getMethodName());

		/*
		 * STDom.createElement(testSuite, "transaction");
		 *
		 * Element connection = STDom.createElement(testSuite, "connection");
		 * STDom.createElement(connection, "driver", ""); //
		 * STDom.createElement(connection, "url", ""); //
		 * STDom.createElement(connection, "user"); // STDom.createElement(tsInfo,
		 * "password", "");
		 */

		Element structInfo = STDom.createElement(testSuite, "structInfo");
		Element structInfoHeader = STDom.createElement(structInfo, "header");

		Element structInfoHeaderField1 = STDom.createElement(structInfoHeader, "field");
		Element structInfoHeaderField2 = STDom.createElement(structInfoHeader, "field");
		structInfoHeaderField1.setAttribute("name", "length");
		structInfoHeaderField1.setAttribute("type", "int");
		structInfoHeaderField1.setAttribute("length", "8");
		structInfoHeaderField2.setAttribute("name", "command");
		structInfoHeaderField2.setAttribute("type", "char");
		structInfoHeaderField2.setAttribute("length", "2");

		Element structInfoBody = STDom.createElement(structInfo, "body");
		Element methodInputEl = STDom.loadXml(rgInfoDomain.getMethodInputXml());

		NodeList fieldList = methodInputEl.getChildNodes();

		if (fieldList != null && fieldList.getLength() > 0) {
			for (int i = 0; i < fieldList.getLength(); i++) {
				Node fieldNode = fieldList.item(i);
				setFieldNode(structInfoBody, fieldNode, rgInfoDomain.getMethodNo());
			}
		}

		Element structInfoFooter = STDom.createElement(structInfo, "footer");

		Element structInfoFooterField1 = STDom.createElement(structInfoFooter, "field");
		Element structInfoFooterField2 = STDom.createElement(structInfoFooter, "field");

		structInfoFooterField1.setAttribute("name", "checksum");
		structInfoFooterField1.setAttribute("type", "int");
		structInfoFooterField1.setAttribute("length", "8");

		structInfoFooterField2.setAttribute("name", "endMark");
		structInfoFooterField2.setAttribute("type", "char");
		structInfoFooterField2.setAttribute("length", "2");

		/*
		 * Element ruleInfo = STDom.createElement(testSuite, "rules"); //
		 * ruleInfo.appendChild(doc.createCDATASection("value")) Element ruleHeader =
		 * STDom.createElement(ruleInfo, "header"); STDom.createElement(ruleHeader,
		 * "length"); // 최종 전문 생성 후에 값 추가 STDom.createElement(ruleHeader, "command");
		 * 
		 * Element ruleBody = STDom.createElement(ruleInfo, "body"); NodeList tempNode =
		 * structInfoBody.getChildNodes(); setFieldNodeValue(ruleBody, tempNode);
		 * 
		 * Element ruleFooter = STDom.createElement(ruleInfo, "footer");
		 * STDom.createElement(ruleFooter, "checksum", "" );
		 * STDom.createElement(ruleFooter, "endMark", "@@");
		 */

		// rules 관련 데이터 처리
		Element ruleEl = STDom.loadXml(rgInfoDomain.getRuleFieldXml());

		// rules에 rule JSON 정보를 셋팅한다.
		setFieldNodeRuleNo(ruleEl);

		// rules 엘리먼트를 testsuite 엘리먼트에 추가한다.
		Node importOutputTcNode = el.getOwnerDocument().importNode(ruleEl, true);
		testSuite.appendChild(importOutputTcNode);

		// Agent 전송 시작
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StreamResult result = new StreamResult(new StringWriter());
		transformer.transform(new DOMSource(el), result);

		// xml 객체를 문자열로 저장한다.
		String requestXmlStr = result.getWriter().toString();
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "수행 고유 번호 : " + rgDomain.getExecutionUuid());
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "프로젝트 번호 : " + rgDomain.getProjectNo());
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "생성 구분 : " + "P");
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "생성 경로 : " + exeHist.getExecutionDetailCondition());
		stStateUtils.sendState(2, rgDomain.getExecutionUuid(), "조합 생성 요청 정보 생성 완료");

		// Agent에 요청한다.
		// 생성기 Agent 정보를 가져온다.
		AddAgentDomain rghAgent = new AddAgentDomain();
		rghAgent.setProjectNo(rgDomain.getProjectNo());
		rghAgent.setCliType("G");
		AddAgentDomain agentInfo = agentMngRepository.selectAgent(rghAgent);

		if (agentInfo == null || StringUtils.isEmpty(agentInfo.getAgentIp())) {
			throw new SSTException("message.exception.noGeneratorAgentInfo", null);
		}

		// 조합생성기 Agent 서버 주소 조합
		String agentURL = "http://" + agentInfo.getAgentIp() + ":" + agentInfo.getAgentPort();
		agentURL = agentURL + environment.getProperty("agent.generator.pairwise.path").replace("{uuid}",
				rgDomain.getExecutionUuid());
		logger.debug("agentURL : " + agentURL);
		// http://192.168.10.68:12880/request/generator/pairwisegenerator/1863a182-f13d-40c5-8bf3-3efbdffd1a35

		// 전송할 서버 url
		URL searchUrl = null;
		try {
			searchUrl = new URL(agentURL);
		} catch (MalformedURLException e) {
			stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "오류 발생!!!! Agent에 접속 할 수 없습니다. ");
			throw new SSTException("message.exception.agentConnect",
					new String[] { "(fail URL Object : " + agentURL + ")" });
		}
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) searchUrl.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", MediaType.APPLICATION_XML_VALUE);
			connection.setRequestProperty("Content-Length", Integer.toString(requestXmlStr.length()));

			OutputStream os = connection.getOutputStream();
			os.write(requestXmlStr.getBytes("utf-8"));
			os.flush();
			os.close();
		} catch (IOException e) {
			throw new SSTException("message.exception.agentConnect",
					new String[] { "(failed HttpURLConnection Object : " + agentURL + ")" });
		}

		stStateUtils.sendState(3, rgDomain.getExecutionUuid(), "Agent에 조합 생성 요청");

		// 결과값 수신
		int rc = connection.getResponseCode();
		// 정상코드이면 진행
		if (rc == 200) {
			InputStreamReader in = new InputStreamReader(connection.getInputStream(), "utf-8");
			BufferedReader br = new BufferedReader(in);
			String strLine;

			String returnString = "";
			while ((strLine = br.readLine()) != null) {
				returnString = returnString.concat(strLine);
			}

			// 결과값출력
			logger.debug("returnString : " + returnString);

			// PID 값을 가져온다.
			// JSON 파싱
			JSONParser jp = new JSONParser();
			JSONObject jsonObj = (JSONObject) jp.parse(returnString);

			stStateUtils.sendState(0, rgDomain.getExecutionUuid(),
					"Agent에 조합 생성 요청 완료 (PID : " + jsonObj.get("pid") + ")");
		} else {
			// 서버 에러가 발생 하였습니다.
			throw new SSTException("message.exception.ServerError", new String[] { "Agent Server not connected" });
		}
		return rgDomain.getGenerateCount();
	}

	/***
	 * 조합 생성 이력 목록 조회 총 갯수
	 *
	 * @param rghomain
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ViewPairwiseGenerationHistoryListDomain viewPairwiseGenerationHistoryListCnt(
			ViewPairwiseGenerationHistoryListDomain domain) throws Exception {
		ViewPairwiseGenerationHistoryListDomain totalCntInfo = pairwiseGenerationRepository
				.selectPairwiseGenerationHistoryListCnt(domain);
		return totalCntInfo;
	}

	/***
	 * 조합 생성 이력 목록 조회
	 *
	 * @param rghomain
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<ViewPairwiseGenerationHistoryListDomain> viewPairwiseGenerationHistoryList(
			ViewPairwiseGenerationHistoryListDomain domain) throws Exception {
		domain.setExecutionDivision("06"); // 공통코드 10000013 참조
		List<ViewPairwiseGenerationHistoryListDomain> rghList = pairwiseGenerationRepository
				.selectPairwiseGenerationHistoryList(domain);
		return rghList;
	}

	/***
	 * 조합 생성 테스트케이스 목록 조회
	 *
	 * @param domain
	 * @return
	 * @throws Throwable
	 * @throws Exception
	 */
	public List<ViewPairwiseGenerationTestcaseListDomain> viewPairwiseGenerationTestcaseList(
			@Valid ViewPairwiseGenerationTestcaseListDomain domain) throws Throwable {
		int testcaseCount = 0;

		System.out.println("서비스 진입");
		ViewPairwiseGenerationHistoryListDomain rghDomain = new ViewPairwiseGenerationHistoryListDomain();

		int executionProjectNo = domain.getExecutionProjectNo();
		long methodNo = domain.getMethodNo();
		String executionUuid = domain.getExecutionUuid();

		rghDomain.setExecutionProjectNo(executionProjectNo);
		rghDomain.setExecutionUuid(executionUuid);
		rghDomain.setMethodNo(methodNo);
		List<ViewPairwiseGenerationHistoryListDomain> pairwiseGenHistList = pairwiseGenerationRepository
				.selectPairwiseGenerationHistoryList(rghDomain);
		// 화면 to server input : methodNo, executionUuid
		List<ViewPairwiseGenerationTestcaseListDomain> rgaList = new ArrayList<ViewPairwiseGenerationTestcaseListDomain>();

		// 총 갯수와 결과를 다 보내야하므로 Map으로 넘겨준다.
		Map<String, Object> rstMap = new HashMap<String, Object>();
		rstMap.put("totalCnt", 0);
		rstMap.put("pairwiseGenTestcaseList", null);

		// 처리하는 기능 generator.pairwise.storage
		String what = "pairwise";

		// 조합 생성 테스트케이스 파일의 루트 경로
		String path = environment.getProperty("generator." + what + ".storage");
		// zip 파일이 다운로드 경로
		String zipDirPath = path + "/" + executionUuid;
		// 압축 해제될 경로
		// String upzipDirPath =
		// zipDirPath+"/collector-"+what+"-"+domain.getExecutionUuid();

		// 압축 파일 저장 경로
		File dir = new File(zipDirPath);
		System.out.println("zipDirPath:" + zipDirPath);
		// 파일 존재여부 확인
		if (!dir.exists()) {
			// 처리 할 압축 파일이 없습니다.
			// throw new SSTException("message.exception.noZipFile");
		}
		// “generator.pairwise.storage”+”/”+{uuid}+”/”+” generator-pairwise-“+{uuid}.zip
		// “generator.pairwise.storage”+”/”+{uuid}+”/”+”
		// generator-pairwise-“+{uuid}+”/”+”result-pairwise-“+{uuid}.xml
		STZip.unzip(zipDirPath + "/generator-pairwise-" + executionUuid + ".zip",
				zipDirPath + "/" + "generator-pairwise-" + executionUuid);
		// 경로의 파일들 가져오기
		File[] files = dir.listFiles();
		// 파일 존재여부 확인
		if (files == null || files.length == 0) {
			// 처리 할 압축 파일이 없습니다.
			throw new SSTException("message.exception.noZipFile");
		}

		// 검색 키워드 가져오기
		String searchTestcaseName = domain.getSearchTestcaseName();
		// 검색 여부 설정
		boolean isSearch = false;
		if (!StringUtils.isEmpty(searchTestcaseName)) {
			isSearch = true;
		}
		/*
		 * testcaseNo testcaseGroupNo(testcase 태그의 group attribute 값) -> xml에서 파싱해야 할
		 * 대상들
		 */
		// xml 파일에서 testcase id, group id 불러오는 로직, 불러와서 domain에 set해야 함
		Element el = STDom.loadXml(new File(zipDirPath + "/" + "generator-pairwise-" + executionUuid + "/"
				+ "result-pairwise-" + executionUuid + ".xml"));
		// zipDirPath + "/" + "generator-pairwise-" + executionUuid + "/" +
		// "result-pairwise-" + executionUuid + ".xml"
		NodeList elChildList = el.getChildNodes();

		for (int j = 0; j < elChildList.getLength(); j++) {

			if (elChildList.item(j).getNodeName().equals("testsuite")) {
				NodeList testsuiteChildList = elChildList.item(j).getChildNodes();
				for (int k = 0; k < testsuiteChildList.getLength(); k++) {
					if ("testcases".equals(testsuiteChildList.item(k).getNodeName())) {
						NodeList testcasesChildNodes = testsuiteChildList.item(k).getChildNodes();
						for (int m = 0; m < testcasesChildNodes.getLength(); m++) {
							ViewPairwiseGenerationTestcaseListDomain tcInfo = new ViewPairwiseGenerationTestcaseListDomain();
							if ("testcase".equals(testcasesChildNodes.item(m).getNodeName())) {
								testcaseCount++;

								tcInfo.setTestcaseGroupNo(
										Long.parseLong(STDom.getAttrubuteByNode(testcasesChildNodes.item(m), "group")));
								tcInfo.setTestcaseNo(
										Long.parseLong(STDom.getAttrubuteByNode(testcasesChildNodes.item(m), "id")));
								tcInfo.setGenerator(rghDomain.getGenerator());
								tcInfo.setRevisionDate(rghDomain.getGenerateDate());

							}
							// 검색 활성화 이고 검색 파일이 맞으면 리스트에 담는다.
							if (!isSearch || (isSearch && tcInfo.getTestcaseName().indexOf(searchTestcaseName) > -1)) {
								// 수집자
								tcInfo.setGenerator(pairwiseGenHistList.get(0).getGenerator());
								tcInfo.setGenerateDate(pairwiseGenHistList.get(0).getGenerateDate());

								// 결과 리스트 삽입
								rgaList.add(tcInfo);
							}
						}
					}
				}
			}

		}

		for (int i = 0; i < rgaList.size(); i++) {
			domain.setTestcaseNo(rgaList.get(i).getTestcaseNo());
			domain.setTestcaseGroupNo(rgaList.get(i).getTestcaseGroupNo());

			rgaList.get(i).setTestcaseName(pairwiseGenerationRepository.selectTestcaseName(domain));
			rgaList.get(i).setTestcaseGroupName(pairwiseGenerationRepository.selectTestcaseGroupName(domain));
		}

		// 페이징 처리
		int totalCnt = rgaList.size(); // 목록 총 갯수
		int pageCnt = domain.getPageCnt(); // 페이지당 갯수
		int pageNum = domain.getPageNum(); // 페이지 번호
		int fromIndex = 0; // 페이징 시작 목록 Index (리스트는 0 부터 시작이르로 고려하여 계산 요망)
		int toIndex = 0; // 페이징 끝 목록 Index (리스트는 0 부터 시작이르로 고려하여 계산 요망)

		// 호출 페이지 범위 보다 총갯수가 작으면 아무 데이터를 보내지 않는다.
		rstMap.put("totalCnt", totalCnt);
		if (pageNum > (totalCnt / pageCnt) + (totalCnt % pageCnt == 0 ? 0 : 1)) {
			rstMap.put("pairwiseGenTestcaseList", null);
		} else {
			fromIndex = (pageNum - 1) * pageCnt;
			toIndex = (pageNum * pageCnt) - 1;

			// 반복문을 돌며 파일 제외한다.(제거하는 것이므로 거꾸로 돌린다.)
			for (int i = totalCnt - 1; i > -1; i--) {
				if (fromIndex > i || toIndex < i) {
					rgaList.remove(i);
				}
			}

			// rowSeqNo를 넣어준다.
			int rewSeqNoStart = totalCnt - ((pageNum - 1) * pageCnt);
			for (int i = 0; i < rgaList.size(); i++) {
				rgaList.get(i).setRowSeqNo(rewSeqNoStart--);
			}

			rstMap.put("pairwiseGenTestcaseList", rgaList);
		}

		return rgaList;
	}

	/***
	 * 조합 생성 규칙 설정 목록 조회 개수
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public ViewPairwiseGenerationRuleListDomain viewPairwiseGenerationRuleListCnt(
			@Valid ViewPairwiseGenerationRuleListDomain domain) {
		ViewPairwiseGenerationRuleListDomain totalCntInfo = pairwiseGenerationRepository
				.selectPairwiseGenerationRuleListCnt(domain);

		return totalCntInfo;
	}

	/***
	 * 조합 생성 규칙 설정 목록 조회
	 * 
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<ViewPairwiseGenerationRuleListDomain> viewPairwiseGenerationRuleList(
			@Valid ViewPairwiseGenerationRuleListDomain domain) {
		List<ViewPairwiseGenerationRuleListDomain> pairwiseGenRuleList = pairwiseGenerationRepository
				.selectPairwiseGenerationRuleList(domain);

		return pairwiseGenRuleList;
	}

	/***
	 * Node 의 필드 속성에 맞게 소스 정보를 xml에 넣느다.
	 * 
	 * @param targetEl
	 * @param fieldNode
	 * @param testcaseNo
	 */
	private void setFieldNode(Element targetEl, Node fieldNode, long testcaseNo) {
		String nodeName = fieldNode.getNodeName();
		// 노드네임에 #이 있으면 제거
		if (nodeName.indexOf("#") < 0) {
			// field
			if ("field".equals(nodeName)) {
				Element targetFieldEl = STDom.createElement(targetEl, "field");
				NamedNodeMap attributeList = fieldNode.getAttributes();
				if (attributeList != null && attributeList.getLength() > 0) {
					for (int j = 0; j < attributeList.getLength(); j++) {
						Node attNode = attributeList.item(j);
						// return 일 경우 name 이 없어서 지정해준다.
						if ("name".equals(attNode.getNodeName()) && StringUtils.isEmpty(attNode.getNodeValue())) {
							targetFieldEl.setAttribute(attNode.getNodeName(),
									STDom.getAttrubuteByNode(attNode, "typedef"));
						} else {
							targetFieldEl.setAttribute(attNode.getNodeName(), attNode.getNodeValue());
						}
					}
				}
			}
			// fields
			else if ("fields".equals(nodeName)) {
				Element targetFieldEl = STDom.createElement(targetEl, "fields");
				// Attribute 생성
				NamedNodeMap attributeList = fieldNode.getAttributes();
				String domainName = "";
				if (attributeList != null && attributeList.getLength() > 0) {
					for (int j = 0; j < attributeList.getLength(); j++) {
						Node attNode = attributeList.item(j);
						// return 일 경우 name 이 없어서 지정해준다.
						if ("name".equals(attNode.getNodeName()) && StringUtils.isEmpty(attNode.getNodeValue())) {
							targetFieldEl.setAttribute(attNode.getNodeName(),
									STDom.getAttrubuteByNode(attNode, "typedef"));
						} else {
							targetFieldEl.setAttribute(attNode.getNodeName(), attNode.getNodeValue());
						}
						if ("typedef".equals(attNode.getNodeName())) {
							domainName = attNode.getNodeValue();
						}
					}
				}
				// 도메인 정보 셋팅
				SourceDomainDomain schDmnDomain = new SourceDomainDomain();
				schDmnDomain.setTestcaseNo(testcaseNo);
				schDmnDomain.setDomainName(domainName);
				SourceDomainDomain rstDmnDomain = testcaseRepository.selectSourceDomain(schDmnDomain);
				// 도메인 정보가 있을때만 수행한다.
				if (rstDmnDomain != null) {
					Element domainEl = STDom.loadXml(rstDmnDomain.getDomainXmlContent());

					NodeList fieldList = domainEl.getChildNodes();
					if (fieldList != null && fieldList.getLength() > 0) {
						for (int i = 0; i < fieldList.getLength(); i++) {
							Node domainNode = fieldList.item(i);
							/*
							 * //TODO header, footer 내용 들은 스킵한다.
							 * if("length".equals(STDom.getAttrubuteByNode(domainNode, "name"))
							 * ||"command".equals(STDom.getAttrubuteByNode(domainNode, "name"))
							 * ||"checksum".equals(STDom.getAttrubuteByNode(domainNode, "name"))
							 * ||"endMark".equals(STDom.getAttrubuteByNode(domainNode, "name"))) { continue;
							 * }
							 */
							setFieldNode(targetFieldEl, domainNode, testcaseNo);
						}
					}

				}
			}
			// fixedArray
			else if ("fixedArray".equals(nodeName)) {
				Element targetFieldEl = STDom.createElement(targetEl, "fixedArray");
				// Attribute 생성
				NamedNodeMap attributeList = fieldNode.getAttributes();
				if (attributeList != null && attributeList.getLength() > 0) {
					for (int j = 0; j < attributeList.getLength(); j++) {
						Node attNode = attributeList.item(j);
						// return 일 경우 name 이 없어서 지정해준다.
						if ("name".equals(attNode.getNodeName()) && StringUtils.isEmpty(attNode.getNodeValue())) {
							targetFieldEl.setAttribute(attNode.getNodeName(),
									STDom.getAttrubuteByNode(fieldNode, "typedef").replace("ArrayList<", "")
											.replace(">", ""));
						} else {
							targetFieldEl.setAttribute(attNode.getNodeName(), attNode.getNodeValue());
						}
					}
				}

				NodeList fixedArrayNodeList = fieldNode.getChildNodes();
				if (fixedArrayNodeList != null && fixedArrayNodeList.getLength() > 0) {
					for (int i = 0; i < fixedArrayNodeList.getLength(); i++) {
						Node fixedArrayNode = fixedArrayNodeList.item(i);
						setFieldNode(targetFieldEl, fixedArrayNode, testcaseNo);
					}
				}
			} else {
				logger.info("Not field type");
			}

		}
	}

	/***
	 * 소스 구조에 맞게 데이터 필드를 생성한다.
	 * 
	 * @param targetEl  xml을 넣은 대상
	 * @param fieldList 소스 구조의 Node 리스트
	 */
	private void setFieldNodeValue(Element targetEl, NodeList fieldList, boolean isNullValue) {
		if (fieldList != null && fieldList.getLength() > 0) {
			for (int i = 0; i < fieldList.getLength(); i++) {
				Node fieldNode = fieldList.item(i);
				String nodeName = fieldNode.getNodeName();
				// field
				if ("field".equals(nodeName)) {
					Element fieldValueEl = STDom.createElement(targetEl, STDom.getAttrubuteByNode(fieldNode, "name"));
					fieldValueEl.setAttribute("valueDiv", "VALUE");
					fieldValueEl.setAttribute("isBinary", "N");
					fieldValueEl.setAttribute("isCompare", "N");
					fieldValueEl.setAttribute("valLength", STDom.getAttrubuteByNode(fieldNode, "length"));
					// 노드의 값을 초기화 할지 확인한다.
					if (isNullValue) {
						fieldValueEl.setNodeValue("");
					}
				}
				// fields
				else if ("fields".equals(nodeName)) {
					Element fieldValueEl;
					String typedef[] = STDom.getAttrubuteByNode(fieldNode, "typedef").split("\\.");
					// typedef null 체크
					if (typedef != null && typedef.length > 0) {
						fieldValueEl = STDom.createElement(targetEl, typedef[typedef.length - 1]);
					} else {
						fieldValueEl = STDom.createElement(targetEl, STDom.getAttrubuteByNode(fieldNode, "typedef"));
					}
					setFieldNodeValue(fieldValueEl, fieldNode.getChildNodes(), isNullValue);
				}
				// fixedArray
				else if ("fixedArray".equals(nodeName)) {
					// fixedArray는 10회 반복해준다.
					for (int z = 0; z < 10; z++) {
						if (!StringUtils.isEmpty(STDom.getAttrubuteByNode(fieldNode, "name"))) {
							Element fieldValueEl = STDom.createElement(targetEl,
									STDom.getAttrubuteByNode(fieldNode, "typedef").replace("Array", "")
											.replace("List", "").replace("&lt;", "").replace("&gt;", "")
											.replace("<", "").replace(">", ""));
							// seq 설정
							fieldValueEl.setAttribute("seq", z + "");
							setFieldNodeValue(fieldValueEl, fieldNode.getChildNodes().item(0).getChildNodes(),
									isNullValue);
						}
					}
				} else {
					logger.info("Not field type");
				}

			}
		}
	}

	private void setFieldNodeRuleNo(Node targetEl) {
    	/*
		{
			"pairwiseValueList" : ["d", "abc"]
		}
		*/
    	Document doc = targetEl.getOwnerDocument();
    	String ruleNo = STDom.getAttrubuteByNode(targetEl, "ruleNo");

    	ExecutePairwiseGenerationInfoDomain algoSchDomain = new ExecutePairwiseGenerationInfoDomain();
		algoSchDomain.setRuleNo(Long.parseLong("2"));
		ExecutePairwiseGenerationInfoDomain ruleInfoList = pairwiseGenerationRepository.selectValueStringByRuleNo(algoSchDomain);
		
		//값이 존재 할때만 수행한다.
    	if(!StringUtils.isEmpty(ruleNo) && !"0".equals(ruleNo)) {    		
    		if(ruleInfoList != null) {
    			//전체 JSON 맵
    			Map<String, Object> fieldMap = new HashMap<String, Object>();
    			
    			String ruleString = ruleInfoList.getValueString();
    			String[] parsedRule = {"김지운", "이장행"}; 
    					//ruleString.split(",");
    			
    			fieldMap.put("pairwiseValueList", parsedRule);
        		//JSON 데이터 입력
    			JSONObject json = getJsonStringFromMap(fieldMap);
    			String jsonStr = json.toJSONString();
    			//CDATA로 json 값 삽입
    			targetEl.appendChild(doc.createCDATASection(jsonStr));
    		}
    	}

    	//하위노드를 검색하여 재귀 호출 한다.
    	NodeList nodeList = targetEl.getChildNodes();
    	if(nodeList != null && nodeList.getLength() > 0) {
    		for(int i = 0; i < nodeList.getLength() ; i++) {
    			setFieldNodeRuleNo(nodeList.item(i));
    		}
    	}
    }

	/**
	 * Map을 json으로 변환한다.
	 *
	 * @param map Map<String, Object>.
	 * @return JSONObject.
	 */
	public JSONObject getJsonStringFromMap(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			jsonObject.put(key, value);
		}

		return jsonObject;
	}

	public void splitFunc(String ktype) { // ktype을 받는다.
		String ktypeWhere = ""; // ktypeWhere는 공백상태

		String[] array = ktype.split(","); // 콤마 구분자로 배열에 ktype저장

		for (String cha : array) { // 배열 갯수만큼 포문이 돌아간다.

			/*
			 * ktypeWhere가 비어있다면 ktypeWhere 에 cha 값을 넣고 비어있지 않다면 기존 값에 ,를 추가하여 cha를 덧붙인다.
			 */
			ktypeWhere += (ktypeWhere.equals("")) ? "'" + cha + "'" : ",'" + cha + "'";

		}
	}
}

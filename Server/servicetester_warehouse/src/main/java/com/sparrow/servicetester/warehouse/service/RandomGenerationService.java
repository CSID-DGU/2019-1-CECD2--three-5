/*
 * 작성일 : 2019.07.16
 * 작성자 : 이장행
 * 미완 상태
 */

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
import java.nio.file.NoSuchFileException;
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

import com.sparrow.servicetester.util.STConst;
import com.sparrow.servicetester.util.STDom;
import com.sparrow.servicetester.util.STZip;
import com.sparrow.servicetester.warehouse.domain.agentmng.AddAgentDomain;
import com.sparrow.servicetester.warehouse.domain.projectmng.ProjectMngViewProjectDetailDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationAlgorithmInfoDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ExecuteRandomGenerationReflectionDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationFileDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationFileListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationHistoryDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.RemoveRandomGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.SourceMethodDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationHistoryListDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationMethodDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseDetailDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomGenerationTestcaseListDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.SourceDomainDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.ViewTestcaseDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.ViewTestcaseEditDomain;
import com.sparrow.servicetester.warehouse.domain.upload.UploadCollectorAddSourceDomain;
import com.sparrow.servicetester.warehouse.exception.SSTException;
import com.sparrow.servicetester.warehouse.repository.AgentMngRepository;
import com.sparrow.servicetester.warehouse.repository.ProjectMngRepository;
import com.sparrow.servicetester.warehouse.repository.RandomGenerationRepository;
import com.sparrow.servicetester.warehouse.repository.TestcaseRepository;
import com.sparrow.servicetester.warehouse.repository.UploadCollectorRepository;
import com.sparrow.servicetester.warehouse.util.STStateUtils;

@Service
public class RandomGenerationService {
	static final Logger logger = LoggerFactory.getLogger(SourceCollectionService.class);

	/*********************** utils ************************************/
	@Inject
	private STStateUtils stStateUtils;

	@Inject
	Environment environment;
	@Inject
	private RandomGenerationRepository randomGenerationRepository;
	@Inject
	private AgentMngRepository agentMngRepository;
	@Inject
	private UploadCollectorRepository uploadCollectorRepository;
	@Inject
	private TestcaseService testcaseService;
	@Inject
	private TestcaseRepository testcaseRepository;
	@Inject
	private ProjectMngRepository projectMngRepository;

	/***
	 * 랜덤 생성 실행
	 *
	 * @param rghomain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int executeRandomGeneration(ExecuteRandomGenerationDomain rgDomain) throws Exception {
		int totalCnt = 0;

		//프로젝트 정보 조회
    	ProjectMngViewProjectDetailDomain projectSchDomain = new ProjectMngViewProjectDetailDomain();
    	projectSchDomain.setProjectNo(rgDomain.getProjectNo());
    	ProjectMngViewProjectDetailDomain projectInfo = projectMngRepository.selectProjectDetail(projectSchDomain);
    	if(projectInfo == null) {
    		//대상 정보가 없습니다.
    		throw new SSTException("message.exception.noTarget",new String[] {""});
    	}

		//메소드 정보 조회
		ViewRandomGenerationMethodDomain genInfoDomain = new ViewRandomGenerationMethodDomain();
    	genInfoDomain.setMethodNo(rgDomain.getMethodNo());
    	genInfoDomain.setRuleNo(rgDomain.getRuleNo());
    	ViewRandomGenerationMethodDomain rgInfoDomain = randomGenerationRepository.selectMethod(genInfoDomain);
    	if(rgInfoDomain == null) {
    		//대상 정보가 없습니다.
    		throw new SSTException("message.exception.noTarget",new String[] {""});
    	}

    	stStateUtils.sendState(1, rgDomain.getExecutionUuid(), "랜덤 생성 요청 수신 완료");

		UploadCollectorAddSourceDomain exeHist = new UploadCollectorAddSourceDomain();
		exeHist.setExecutionUuid(rgDomain.getExecutionUuid());
		exeHist.setExecutionDivision("05");
		exeHist.setExecutionProjectNo(rgDomain.getProjectNo());
		exeHist.setRelationExecutionUuid("");
		exeHist.setExecutionCondition("R");
		exeHist.setExecutionProcessCount(0);
		exeHist.setExecutionDetailCondition(rgDomain.getRuleNo()+"");
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

		STDom.getOrCreateElement(generatorInfo, "runHistoryNo","");
		STDom.getOrCreateElement(generatorInfo, "executionUuid", rgDomain.getExecutionUuid());
		STDom.getOrCreateElement(generatorInfo, "generatorDiv", "R"); // A(Auto), P(Pairwise), R(Random)
		STDom.getOrCreateElement(generatorInfo, "targetLanguage", "Java"); // C | Java

		STDom.getOrCreateElement(generatorInfo, "testDesc", rgInfoDomain.getFolderFileName() + "_"
				+ rgInfoDomain.getMethodName() + rgInfoDomain.getRuleName()//일단 주석처리 + Integer.toString(domain.getGenerationNo())
				);

		Element testSuite = STDom.createElement(el, "testsuite");
		testSuiteCnt++;
		testSuite.setAttribute("id", Integer.toString(testSuiteCnt));
		testSuite.setAttribute("at", "local"); //

		Element tsInfo = STDom.createElement(testSuite, "tsInfo");

		STDom.createElement(tsInfo, "projectName", projectInfo.getProjectName());
		STDom.createElement(tsInfo, "projectOwner", projectInfo.getProjectOwner());
		STDom.createElement(tsInfo, "generateCount", rgDomain.getGenerateCount()); //입력받음
		STDom.createElement(tsInfo, "folder", rgInfoDomain.getFolderFilePath()); //st_folder_file
		STDom.createElement(tsInfo, "filename", rgInfoDomain.getFolderFileName()); //st_folder_file_name
		STDom.createElement(tsInfo, "function", rgInfoDomain.getMethodName()); //
		STDom.createElement(tsInfo, "functionNo", rgInfoDomain.getMethodNo()+"");
		STDom.createElement(tsInfo, "desc", rgInfoDomain.getFolderFileName()+"_"+rgInfoDomain.getMethodName());

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
		Element ruleInfo = STDom.createElement(testSuite, "rules");
		// ruleInfo.appendChild(doc.createCDATASection("value"))
		Element ruleHeader = STDom.createElement(ruleInfo, "header");
		STDom.createElement(ruleHeader, "length"); // 최종 전문 생성 후에 값 추가
		STDom.createElement(ruleHeader, "command");

		Element ruleBody = STDom.createElement(ruleInfo, "body");
		NodeList tempNode = structInfoBody.getChildNodes();
		setFieldNodeValue(ruleBody, tempNode);

		Element ruleFooter = STDom.createElement(ruleInfo, "footer");
		STDom.createElement(ruleFooter, "checksum", "" );
		STDom.createElement(ruleFooter, "endMark", "@@");
		*/

		//rules 관련 데이터 처리
		Element ruleEl = STDom.loadXml(rgInfoDomain.getRuleFieldXml());

		//rules에 rule JSON 정보를 셋팅한다.
		setFieldNodeRuleNo(ruleEl);

		//rules 엘리먼트를 testsuite 엘리먼트에 추가한다.
		Node importOutputTcNode = el.getOwnerDocument().importNode(ruleEl, true);
		testSuite.appendChild(importOutputTcNode);

		//Agent 전송 시작
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StreamResult result = new StreamResult(new StringWriter());
		transformer.transform(new DOMSource(el), result);

		// xml 객체를 문자열로 저장한다.
		String requestXmlStr = result.getWriter().toString();
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "수행 고유 번호 : " + rgDomain.getExecutionUuid());
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "프로젝트 번호 : " + rgDomain.getProjectNo());
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "생성 구분 : " + "R");
		stStateUtils.sendState(0, rgDomain.getExecutionUuid(), "생성 경로 : " + exeHist.getExecutionDetailCondition());
		stStateUtils.sendState(2, rgDomain.getExecutionUuid(), "랜덤 생성 요청 정보 생성 완료");

		// Agent에 요청한다.
		// 생성기 Agent 정보를 가져온다.
		AddAgentDomain rghAgent = new AddAgentDomain();
		rghAgent.setProjectNo(rgDomain.getProjectNo());
		rghAgent.setCliType("G");
		AddAgentDomain agentInfo = agentMngRepository.selectAgent(rghAgent);

		if (agentInfo == null || StringUtils.isEmpty(agentInfo.getAgentIp())) {
			throw new SSTException("message.exception.noGeneratorAgentInfo", null);
		}

		// 랜덤생성기 Agent 서버 주소 조합
		String agentURL = "http://" + agentInfo.getAgentIp() + ":" + agentInfo.getAgentPort();
		agentURL = agentURL + environment.getProperty("agent.generator.random.path").replace("{uuid}",rgDomain.getExecutionUuid());
		logger.debug("agentURL : " + agentURL);
		// http://192.168.10.68:12880/request/generator/randomgenerator/1863a182-f13d-40c5-8bf3-3efbdffd1a35

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

		stStateUtils.sendState(3, rgDomain.getExecutionUuid(), "Agent에 랜덤 생성 요청");

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
					"Agent에 랜덤 생성 요청 완료 (PID : " + jsonObj.get("pid") + ")");
		} else {
			// 서버 에러가 발생 하였습니다.
			throw new SSTException("message.exception.ServerError", new String[] { "Agent Server not connected" });
		}
		return rgDomain.getGenerateCount();
	}

	/***
	 * 랜덤 생성 이력 목록 조회 총 갯수
	 *
	 * @param rghomain
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ViewRandomGenerationHistoryListDomain viewRandomGenerationHistoryListCnt(
			ViewRandomGenerationHistoryListDomain domain) throws Exception {
		ViewRandomGenerationHistoryListDomain totalCntInfo = randomGenerationRepository
				.selectRandomGenerationHistoryListCnt(domain);
		return totalCntInfo;
	}

	/***
	 * 랜덤 생성 이력 목록 조회
	 *
	 * @param rghomain
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<ViewRandomGenerationHistoryListDomain> viewRandomGenerationHistoryList(
			ViewRandomGenerationHistoryListDomain domain) throws Exception {
		domain.setExecutionDivision("05"); // 공통코드 10000013 참조
		List<ViewRandomGenerationHistoryListDomain> rghList = randomGenerationRepository
				.selectRandomGenerationHistoryList(domain);
		return rghList;
	}

	/***
	 * 랜덤 생성 이력 단건 삭제
	 *
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationHistory(RemoveRandomGenerationHistoryDomain domain) throws Exception {
		int resultCnt = randomGenerationRepository.deleteRandomGenerationHistory(domain);
		return resultCnt;
	}

	/***
	 * 랜덤 생성 이력 다건 삭제
	 *
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationHistoryList(RemoveRandomGenerationHistoryListDomain domain) throws Exception {

		List<RemoveRandomGenerationHistoryDomain> delList = domain.getRemoveList();

		int resultCnt = 0;
		for (int i = 0; i < delList.size(); i++) {
			// 랜덤 생성 이력 삭제
			RemoveRandomGenerationHistoryDomain deleteDomain = new RemoveRandomGenerationHistoryDomain();
			deleteDomain.setExecutionUuid(delList.get(i).getExecutionUuid());

			randomGenerationRepository.deleteRandomGenerationHistory(deleteDomain);

			/*
			 * resultCnt += 1; // zip 파일 삭제 //랜덤 생성 파일의 루트 경로 String path =
			 * environment.getProperty("generator.randomgenerator.storage"); //zip 파일이 다운로드
			 * 경로 String zipDirPath = path +"/" + delList.get(i).getUuid();
			 *
			 * deleteSubFolder(zipDirPath);
			 * logger.info("delete source zip file : "+zipDirPath);
			 */

			// 카운트 증가
			resultCnt++;
		}
		return resultCnt;
	}

	private static void deleteSubFolder(String path) {
		File folder = new File(path);
		try {
			if (folder.exists()) {
				File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기
				if (folder_list != null && folder_list.length > 0) {
					for (int i = 0; i < folder_list.length; i++) {
						if (folder_list[i].isFile()) {
							folder_list[i].delete();
						} else {
							deleteSubFolder(folder_list[i].getPath()); // 재귀함수호출
						}
						folder_list[i].delete();
					}
				}
				folder.delete(); // 폴더 삭제
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/***
	 * 랜덤 생성 파일 삭제
	 *
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int removeRandomGenerationFile(RemoveRandomGenerationFileListDomain domain) throws Exception {
		int resultCnt = 0;

		List<RemoveRandomGenerationFileDomain> removeList = domain.getRemoveList();

		String[] removeArr = new String[removeList.size()];

		// 랜덤 생성 파일의 루트 경로
		String path = environment.getProperty("generator.randomgeneration.storage");
		// zip 파일이 존재하는 경로
		String zipDirPath = path + "/" + domain.getUuid() + "/" + "generator-randomgenaration-" + domain.getUuid()
				+ ".zip";

		// 반복문을 수행하여 삭제 대상을 배열에 저장한다.
		for (int i = 0; i < removeList.size(); i++) {
			removeArr[i] = removeList.get(i).getFilePath() + "/" + removeList.get(i).getFileName();
		}
		try {
			resultCnt = STZip.deleteFileFromZipFile(zipDirPath, removeArr);

		} catch (NoSuchFileException noSuchFileException) {
			// 압축 파일내에 해당 파일이 없습니다.
			throw new SSTException("message.exception.noFileInZipFile");
		}

		return resultCnt;
	}

	/***
	 * 랜덤 생성 테스트케이스 반영
	 *
	 * @param domain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int executeRandomGenerationReflection(ExecuteRandomGenerationReflectionDomain domain) throws Exception {
		//uuid로 생성된 이력 검색
		ViewRandomGenerationHistoryListByUuidDomain rghDomain = new ViewRandomGenerationHistoryListByUuidDomain();
		String executionUuid = domain.getExecutionUuid();
		
		rghDomain.setExecutionUuid();
		List<ViewRandomGenerationHistoryListByUuidDomain> rghList = new ArrayList<ViewRandomGenerationHistoryListByUuidDomain>();
		rghList = randomGenerationRepository.selectRandomGenerationHistoryByUuid(rghDomain);
		
		List<ViewRandomGenerationTestcaseListDomain> rgaList = new ArrayList<ViewRandomGenerationTestcaseListDomain>();


		//없으면 리턴
		if(rghList == null) {
			throw new SSTException("message.exception.noTarget",new String[] {""});
		}

		//xml 결과 파일 로딩
    	//처리하는 기능   generator.random.storage
    	String what = "random";

    	//랜덤 생성 테스트케이스 파일의 루트 경로
    	String path = environment.getProperty("generator."+what+".storage");
    	//zip 파일이 다운로드  경로
    	String zipDirPath =  path +"/" + executionUuid;
    	//압축 해제될 경로
    	//String upzipDirPath =  zipDirPath+"/collector-"+what+"-"+domain.getExecutionUuid();

    	//압축 파일 저장 경로
    	File dir = new File(zipDirPath);
    	System.out.println("zipDirPath:"+zipDirPath);
    	//파일 존재여부 확인
    	if(!dir.exists()){
    		//처리 할 압축 파일이 없습니다.
    		throw new SSTException("message.exception.noZipFile");
    	}
    	//“generator.random.storage”+”/”+{uuid}+”/”+” generator-random-“+{uuid}.zip
    	//“generator.random.storage”+”/”+{uuid}+”/”+” generator-random-“+{uuid}+”/”+”result-random-“+{uuid}.xml
    	STZip.unzip(zipDirPath+"/generator-random-" + executionUuid + ".zip", zipDirPath + "/" + "generator-random-" + executionUuid);
    	//경로의 파일들 가져오기
    	File[] files = dir.listFiles();
    	//파일 존재여부 확인
    	if(files == null || files.length == 0){
    		//처리 할 압축 파일이 없습니다.
    		throw new SSTException("message.exception.noZipFile");
    	}

    	Element el = STDom.loadXml         (new File(zipDirPath + "/" + "generator-random-" + executionUuid + "/" + "result-random-" + executionUuid + ".xml"));
    	//zipDirPath + "/" + "generator-random-" + executionUuid + "/" + "result-random-" + executionUuid + ".xml"
    	
    	List<ExecuteRandomGenerationReflectionDomain> reflectionList = domain.getReflectionList();
    	
    	NodeList elChildList = el.getChildNodes();
    	
		//테스트 케이스 목록 반복문 실행
    	for(int j = 0; j < elChildList.getLength(); j++) {

    		if(elChildList.item(j).getNodeName().equals("testsuite")) {
    			NodeList testsuiteChildList = elChildList.item(j).getChildNodes();
    			for(int k = 0; k < testsuiteChildList.getLength(); k++) {
    				if("testcases".equals(testsuiteChildList.item(k).getNodeName())) {
    					NodeList testcasesChildNodes = testsuiteChildList.item(k).getChildNodes();
    					for(int m = 0; m < testcasesChildNodes.getLength(); m++) {
    						ViewRandomGenerationTestcaseListDomain tcInfo = new ViewRandomGenerationTestcaseListDomain();
    						if("testcase".equals(testcasesChildNodes.item(m).getNodeName())) {
    							
    							tcInfo.setTestcaseGroupNo(Long.parseLong(STDom.getAttrubuteByNode(testcasesChildNodes.item(m), "group")));
    							tcInfo.setTestcaseNo(Long.parseLong(STDom.getAttrubuteByNode(testcasesChildNodes.item(m), "id")));
    							tcInfo.setGenerator(rghDomain.getGenerator());
    							tcInfo.setRevisionDate(rghDomain.getGenerateDate());
    						}
    					}
    				}
    			}
    		}

    	}

    	for(int i = 0; i < rgaList.size(); i++) {
    		domain.setTestcaseNo(rgaList.get(i).getTestcaseNo());
    		domain.setTestcaseGroupNo(rgaList.get(i).getTestcaseGroupNo());
    	}
			//반영 목록 반복문 실행
    		for(int j = 0; j < reflectionList.size(); j++) {
    			//반영 목록이 맞으면 testcase xml 생성
    			if() {
    				
    			}
    			
    		}
		// testcase DB 저장
		// method_no 매핑 처리

		//저장 카운트 집계
    	












		/*1. DB에 insert -> 생성기 xml에서 testcases 하위 태그들(testcase id = "" group = "" 찾아서 value값만 가져와서 띄워주면 됨)
		2. createXml(testcase 각각의 viewEdit xml) ->testcaseService 참고
		필요 : executionUuid, testcaseNo -> testcaseNo는 1번에서 수행한 testcase id = "value"에서 value를 testcaseNo에 매핑함,
		executionUuid를 통해 compress_file_path 찾아내고  그 경로에서 xml을 찾아 testcase 태그의
		id를 찾아내서 그 id로*/

		/*
		 * to Server input
		 */
		List<ExecuteRandomGenerationReflectionDomain> reflectionList = domain.getReflectionList();
		int totalCnt = 0;

		for(int i = 0; i < reflectionList.size(); i++) {
			ExecuteRandomGenerationReflectionDomain refDomain = new ExecuteRandomGenerationReflectionDomain();
			refDomain.setExecutionUuid(reflectionList.get(i).getExecutionUuid());

			//xml 파싱해서 testcaseName, testcaseDescription, testcaseGroupNo, executionCount 등을 구한다
			//구해서 refDomain.setTestcaseName() -> 매핑한 다음 repository에 넘겨준다.
			Element el = STDom.loadXml(refDomain.getTestcaseInputXml());
			NodeList elChildList = el.getChildNodes();



			//selectTestcaseNo ->st_seq_source 사용
			//randomGenerationRepository.selectTestcaseNo(domain);

			refDomain.setTestcaseNo(reflectionList.get(i).getTestcaseNo());
			//DB에 insert하는 part, executeRandomGenerationReflection -> insertRandomGenerationTestcase로 바꿀 것
			randomGenerationRepository.executeRandomGenerationReflection(domain);

			//경로 찾아오는 part
			String path = randomGenerationRepository.selectRandomGenerationReflectionPath(domain).getCompressFilePath();

			File testcaseXmlFile = new File(path);

	    	//없으면 파일 생성 후 File 객체 새로 생성
	    	if(!testcaseXmlFile.exists()) {
	    		String createFile = testcaseService.createTestcaseXmlFile(domain.getTestcaseNo());
	    		testcaseXmlFile = new File(createFile);
	    	}

	    	//생성된 xml file에 value를 넣는다?


	    	totalCnt++;
		}
		return totalCnt;
	}


	/***
	 * 랜덤 생성 테스트케이스 목록 조회
	 *
	 * @param domain
	 * @return
	 * @throws Throwable
	 * @throws Exception
	 */
	public List<ViewRandomGenerationTestcaseListDomain> viewRandomGenerationTestcaseList(
			@Valid ViewRandomGenerationTestcaseListDomain domain) throws Throwable {
		int testcaseCount = 0;

		System.out.println("서비스 진입");
		ViewRandomGenerationHistoryListDomain rghDomain = new ViewRandomGenerationHistoryListDomain();

		int executionProjectNo = domain.getExecutionProjectNo();
		long methodNo = domain.getMethodNo();
		String executionUuid = domain.getExecutionUuid();

		rghDomain.setExecutionProjectNo(executionProjectNo);
		rghDomain.setExecutionUuid(executionUuid);
		rghDomain.setMethodNo(methodNo);
		List<ViewRandomGenerationHistoryListDomain> randomGenHistList = randomGenerationRepository.selectRandomGenerationHistoryList(rghDomain);
		//화면 to server input : methodNo, executionUuid
		List<ViewRandomGenerationTestcaseListDomain> rgaList = new ArrayList<ViewRandomGenerationTestcaseListDomain>();

    	//총 갯수와 결과를 다 보내야하므로 Map으로 넘겨준다.
    	Map<String, Object> rstMap = new HashMap<String, Object>();
    	rstMap.put("totalCnt", 0);
   		rstMap.put("randomGenTestcaseList", null);

    	//처리하는 기능   generator.random.storage
    	String what = "random";

    	//랜덤 생성 테스트케이스 파일의 루트 경로
    	String path = environment.getProperty("generator."+what+".storage");
    	//zip 파일이 다운로드  경로
    	String zipDirPath =  path +"/" + executionUuid;
    	//압축 해제될 경로
    	//String upzipDirPath =  zipDirPath+"/collector-"+what+"-"+domain.getExecutionUuid();

    	//압축 파일 저장 경로
    	File dir = new File(zipDirPath);
    	System.out.println("zipDirPath:"+zipDirPath);
    	//파일 존재여부 확인
    	if(!dir.exists()){
    		//처리 할 압축 파일이 없습니다.
    		//throw new SSTException("message.exception.noZipFile");
    	}
    	//“generator.random.storage”+”/”+{uuid}+”/”+” generator-random-“+{uuid}.zip
    	//“generator.random.storage”+”/”+{uuid}+”/”+” generator-random-“+{uuid}+”/”+”result-random-“+{uuid}.xml
    	STZip.unzip(zipDirPath+"/generator-random-" + executionUuid + ".zip", zipDirPath + "/" + "generator-random-" + executionUuid);
    	//경로의 파일들 가져오기
    	File[] files = dir.listFiles();
    	//파일 존재여부 확인
    	if(files == null || files.length == 0){
    		//처리 할 압축 파일이 없습니다.
    		throw new SSTException("message.exception.noZipFile");
    	}

    	//검색 키워드 가져오기
    	String searchTestcaseName = domain.getSearchTestcaseName();
    	//검색 여부 설정
    	boolean isSearch = false;
    	if(!StringUtils.isEmpty(searchTestcaseName)) {
    		isSearch = true;
    	}
		/*
		   	testcaseNo
			testcaseGroupNo(testcase 태그의 group attribute 값)
			-> xml에서 파싱해야 할 대상들
		 */
		//xml 파일에서 testcase id, group id 불러오는 로직, 불러와서 domain에 set해야 함
    	Element el = STDom.loadXml         (new File(zipDirPath + "/" + "generator-random-" + executionUuid + "/" + "result-random-" + executionUuid + ".xml"));
    	//zipDirPath + "/" + "generator-random-" + executionUuid + "/" + "result-random-" + executionUuid + ".xml"
    	NodeList elChildList = el.getChildNodes();

    	for(int j = 0; j < elChildList.getLength(); j++) {

    		if(elChildList.item(j).getNodeName().equals("testsuite")) {
    			NodeList testsuiteChildList = elChildList.item(j).getChildNodes();
    			for(int k = 0; k < testsuiteChildList.getLength(); k++) {
    				if("testcases".equals(testsuiteChildList.item(k).getNodeName())) {
    					NodeList testcasesChildNodes = testsuiteChildList.item(k).getChildNodes();
    					for(int m = 0; m < testcasesChildNodes.getLength(); m++) {
    						ViewRandomGenerationTestcaseListDomain tcInfo = new ViewRandomGenerationTestcaseListDomain();
    						if("testcase".equals(testcasesChildNodes.item(m).getNodeName())) {
    							testcaseCount++;

    							tcInfo.setTestcaseGroupNo(Long.parseLong(STDom.getAttrubuteByNode(testcasesChildNodes.item(m), "group")));
    							tcInfo.setTestcaseNo(Long.parseLong(STDom.getAttrubuteByNode(testcasesChildNodes.item(m), "id")));
    							tcInfo.setGenerator(rghDomain.getGenerator());
    							tcInfo.setRevisionDate(rghDomain.getGenerateDate());

    						}
    						//검색 활성화 이고 검색 파일이 맞으면 리스트에 담는다.
    						if(!isSearch || (isSearch && tcInfo.getTestcaseName().indexOf(searchTestcaseName) > -1)) {
    							//수집자
    							tcInfo.setGenerator(randomGenHistList.get(0).getGenerator());
    							tcInfo.setGenerateDate(randomGenHistList.get(0).getGenerateDate());

    							//결과 리스트 삽입
    							rgaList.add(tcInfo);
    						}
    					}
    				}
    			}
    		}

    	}

    	for(int i = 0; i < rgaList.size(); i++) {
    		domain.setTestcaseNo(rgaList.get(i).getTestcaseNo());
    		domain.setTestcaseGroupNo(rgaList.get(i).getTestcaseGroupNo());
    		
    		rgaList.get(i).setTestcaseName(randomGenerationRepository.selectTestcaseName(domain));
    		rgaList.get(i).setTestcaseGroupName(randomGenerationRepository.selectTestcaseGroupName(domain));
    	}

    	//페이징 처리
    	int totalCnt = rgaList.size();	//목록 총 갯수
    	int pageCnt = domain.getPageCnt();	//페이지당 갯수
    	int pageNum = domain.getPageNum();	//페이지 번호
    	int fromIndex = 0;	//페이징 시작 목록 Index (리스트는 0 부터 시작이르로 고려하여 계산 요망)
    	int toIndex = 0;	//페이징 끝 목록 Index (리스트는 0 부터 시작이르로 고려하여 계산 요망)

    	//호출 페이지 범위 보다 총갯수가 작으면 아무 데이터를 보내지 않는다.
    	rstMap.put("totalCnt", totalCnt);
    	if(pageNum > (totalCnt/pageCnt)+(totalCnt%pageCnt == 0 ? 0 : 1)) {
    		rstMap.put("randomGenTestcaseList", null);
    	}else {
    		fromIndex = (pageNum - 1) * pageCnt;
    		toIndex   =  (pageNum * pageCnt) -1 ;

    		//반복문을 돌며 파일 제외한다.(제거하는 것이므로 거꾸로 돌린다.)
    		for(int i = totalCnt-1 ; i > -1 ; i--) {
        		if(fromIndex >  i || toIndex < i) {
        			rgaList.remove(i);
        		}
        	}

    		//rowSeqNo를 넣어준다.
    		int rewSeqNoStart = totalCnt - ((pageNum-1)*pageCnt);
        	for(int i = 0 ; i < rgaList.size() ; i++) {
        		rgaList.get(i).setRowSeqNo(rewSeqNoStart--);
        	}

        	rstMap.put("randomGenTestcaseList", rgaList);
    	}

		return rgaList;
	}

	
    /***
     * 랜덤 생성 테스트케이스 편집 조회
     * @param domain
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public Map<String, Object> viewTestcaseEdit(ViewRandomGenerationTestcaseDetailDomain domain)  throws Exception {
    	//테스트 케이스 정보 조회
    	ViewTestcaseDomain schTcDomain = new ViewTestcaseDomain();
    	schTcDomain.setTestcaseNo(domain.getTestcaseNo());
    	ViewTestcaseDomain tcInfoDomain = testcaseRepository.selectTestcaseMethod(schTcDomain);

    	//없으면 오류 처리
    	if(tcInfoDomain == null) {
    		//대상 정보가 없습니다.
    		throw new SSTException("message.exception.noTarget",new String[] {""});
    	}

    	//testcase xml 존재 여부 확인
    	String path = environment.getProperty("testcase.xml.storge");
    	path = path + "/"+domain.getTestcaseNo()+STConst.EXT_XML_OUT;
    	File testcaseXmlFile = new File(path);

    	//없으면 파일 생성 후 File 객체 새로 생성
    	if(!testcaseXmlFile.exists()) {
    		String createFile = createTestcaseXmlFile(domain.getTestcaseNo());
    		testcaseXmlFile = new File(createFile);
    	}

    	//마스터 데이터 넣기
    	Map<String, Object> testcaseNameMap = new HashMap<String, Object>();
    	testcaseNameMap.put("value",tcInfoDomain.getTestcaseName());
    	testcaseNameMap.put("status","R");
    	Map<String, Object> testcaseNoMap = new HashMap<String, Object>();
    	testcaseNoMap.put("value",tcInfoDomain.getTestcaseNo());
    	testcaseNoMap.put("status","R");
    	Map<String, Object> testcaseGroupNameMap = new HashMap<String, Object>();
    	testcaseGroupNameMap.put("value",tcInfoDomain.getTestcaseGroupName());
    	testcaseGroupNameMap.put("status","R");
    	Map<String, Object> testcaseGroupNoMap = new HashMap<String, Object>();
    	testcaseGroupNoMap.put("value",tcInfoDomain.getTestcaseGroupNo());
    	testcaseGroupNoMap.put("status","R");

    	Map<String, Object> masterMap = new HashMap<String, Object>();
    	masterMap.put("testcaseName",testcaseNameMap);
    	masterMap.put("testcaseNo",testcaseNoMap);
    	masterMap.put("testcaseGroupName",testcaseGroupNameMap);
    	masterMap.put("testcaseGroupNo",testcaseGroupNoMap);

    	//testcase 객체 로딩
    	Element rootEl = STDom.loadXml(testcaseXmlFile);

    	//인풋 노드리스트 가져오기
    	Element inputTcEl = STDom.getOrCreateElement(rootEl, "./testcase/testValue/inputTc/origInputTc");
    	Map<String, Object> detailInputMap = new HashMap<String,Object>();
    	setXmlToJson(detailInputMap, inputTcEl);

    	//아웃풋 노드리스트 가져오기
		
		/*
		 * Element outputTcEl = STDom.getOrCreateElement(rootEl,
		 * "./testcase/testValue/outputTc/origExpectTc"); Map<String, Object>
		 * detailOutputMap = new HashMap<String, Object>();
		 * setXmlToJson(detailOutputMap, outputTcEl);
		 */
		 

    	//JSON 셋팅 반환 Map
    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	//디테일 데이터 객체 셋팅
    	Map<String, Object> detailMap = new HashMap<String, Object>();
    	Map<String, Object> detailDataMap = new HashMap<String, Object>();
    	detailDataMap.put("varName", "testcase");
    	detailDataMap.put("valLength", "");
    	detailDataMap.put("varType", "");
    	detailDataMap.put("isBinary", "");
    	detailDataMap.put("value", "");
    	detailDataMap.put("isCompare", "");
    	detailDataMap.put("status", "R");
    	
    	
    	//디테일 본 내용 셋팅
    	List<Map<String, Object>> detailInOutoutList = new ArrayList<Map<String,Object>>();
    	detailInOutoutList.add(detailInputMap);
    	
    	//detailInOutoutList.add(detailOutputMap);

    	detailMap.put("data", detailDataMap);
    	detailMap.put("height", 32);
    	
    	detailMap.put("children", detailInOutoutList);

    	//배열형 처리를 위해 List 사용(한건 이지만 형식 맞추기 위해 사용)
    	List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
    	detailList.add(detailMap);

    	//마스터 데이터 객체에 셋팅
    	resultMap.put("master", masterMap);

    	//디테일 테이터 객체 셋팅
    	resultMap.put("detail", detailList);

    	return resultMap;
    }

    /***
     * 테스트케이즈 XML을 JSON 으로 변경한다.
     * @param saveMap JSON으로 변환 된 데이터가 들어가는 Map
     * @param contentNode 변환 대상이 되는 xml의 노드 객체
     */
    public void setXmlToJson(Map<String, Object> saveMap, Node contentNode) {
    	//해당 부분은 제외한다.
    	if("inputMessage".equals(contentNode.getNodeName())
    			||"normInputTc".equals(contentNode.getNodeName())
    			||"normExpectTc".equals(contentNode.getNodeName())
    			||"resultMessageTc".equals(contentNode.getNodeName())
    			||"resultMessage".equals(contentNode.getNodeName())
    			) {
    		return;
    	}
    	//xml의 attribute 저장
    	Map<String, Object> conMap = new HashMap<String, Object>();
    	conMap.put("varName",contentNode.getNodeName());
    	conMap.put("valLength", STDom.getAttrubuteByNode(contentNode, "valLength"));
    	conMap.put("varType", STDom.getAttrubuteByNode(contentNode, "valueDiv"));
    	conMap.put("isBinary", STDom.getAttrubuteByNode(contentNode, "isBinary"));
    	String nodeValue = STDom.getTextContentByAll(contentNode);
    	if(StringUtils.isEmpty(nodeValue)) {
    		nodeValue = contentNode.getNodeValue();
    	}
    	conMap.put("value",nodeValue);
    	//fixedArray 일경우 seq 변호를 셋팅한다.
		
		if (!StringUtils.isEmpty(STDom.getAttrubuteByNode(contentNode, "seq"))) {
			conMap.put("seq", STDom.getAttrubuteByNode(contentNode, "seq"));
		}
		conMap.put("isCompare", STDom.getAttrubuteByNode(contentNode, "isCompare"));
		conMap.put("status", "R"); // data: {"varName": "origInputTc"
		saveMap.put("data", conMap);

		NodeList conNL = contentNode.getChildNodes();
		if (conNL != null && conNL.getLength() > 0) {
			List<Map<String, Object>> childrenList = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < conNL.getLength(); i++) {
				if (conNL.item(i).getNodeName().indexOf("#") < 0) {
					childrenList.add(new HashMap<String, Object>());
					setXmlToJson(childrenList.get(childrenList.size() - 1), conNL.item(i));
				}
			}
			// 칠드런 넣기 
			saveMap.put("children", childrenList); 
		}
	}

    /***
     *
     * @param testcaseNo 생성할 테스트케이스 번호
     * @return
     * @throws Exception
     */
    public String createTestcaseXmlFile(long testcaseNo) throws Exception {
    	//테스트 케이스 정보 조회
    	ViewTestcaseDomain schTcDomain = new ViewTestcaseDomain();
    	schTcDomain.setTestcaseNo(testcaseNo);
    	ViewTestcaseDomain tcInfoDomain = testcaseRepository.selectTestcaseMethod(schTcDomain);

    	if(tcInfoDomain == null) {
    		//대상 정보가 없습니다.
    		throw new SSTException("message.exception.noTarget",new String[] {""});
    	}

    	Element el = STDom.loadXml("<testcaseContainer></testcaseContainer>");	//xml 생성

    	/** 1. master ******/
    	Element masterEl = STDom.getOrCreateElement(el, "master");
    	STDom.getOrCreateElement(masterEl,"testcaseName",tcInfoDomain.getTestcaseName());
    	STDom.getOrCreateElement(masterEl,"testcaseNo",tcInfoDomain.getTestcaseNo()+"");
    	STDom.getOrCreateElement(masterEl,"testcaseGroupName",tcInfoDomain.getTestcaseGroupName());
    	STDom.getOrCreateElement(masterEl,"testcaseGroupNo",tcInfoDomain.getTestcaseGroupNo()+"");

    	/** 2. testcase ******/
    	Element testcaseEl = STDom.getOrCreateElement(el, "testcase");

    	/** 2.1 structInfo ******/
    	Element testcase_structInfoEl = STDom.getOrCreateElement(testcaseEl, "structInfo");

    	/** 2.1.1 inputTc ******/
    	Element testcase_inputTcEl = STDom.getOrCreateElement(testcase_structInfoEl, "inputTc");
    	/** 2.1.1.1 header ******/
    	Element testcase_inputTc_headerEl = STDom.getOrCreateElement(testcase_inputTcEl, "header");
    	Element lengthField = STDom.createElement(testcase_inputTc_headerEl, "field");
    	lengthField.setAttribute("name", "length");
    	lengthField.setAttribute("type", "int");
    	lengthField.setAttribute("length", "8");
    	Element commandField = STDom.createElement(testcase_inputTc_headerEl, "field");
    	commandField.setAttribute("name", "command");
    	commandField.setAttribute("type", "char");
    	commandField.setAttribute("length", "2");

    	/** 2.1.1.2 body ******/
    	Element testcase_inputTc_bodyEl = STDom.getOrCreateElement(testcase_inputTcEl, "body");
    	Element methodInputEl = STDom.loadXml(tcInfoDomain.getMethodInputXml());	//xml 생성

    	NodeList fieldList = methodInputEl.getChildNodes();

    	if(fieldList != null && fieldList.getLength() > 0) {
    		for(int i = 0; i < fieldList.getLength() ; i++) {
    			Node fieldNode = fieldList.item(i);
    			setFieldNode(testcase_inputTc_bodyEl, fieldNode, tcInfoDomain.getTestcaseNo());
    		}
    	}

    	/** 2.1.1.3 footer ******/
    	Element testcase_inputTc_footerEl = STDom.getOrCreateElement(testcase_inputTcEl, "footer");
    	Element checksumField = STDom.createElement(testcase_inputTc_footerEl, "field");
    	checksumField.setAttribute("name", "checksum");
    	checksumField.setAttribute("type", "int");
    	checksumField.setAttribute("length", "8");
    	Element endMarkField = STDom.createElement(testcase_inputTc_footerEl, "field");
    	endMarkField.setAttribute("name", "endMark");
    	endMarkField.setAttribute("type", "char");
    	endMarkField.setAttribute("length", "2");

    	/** 2.1.2 outputTc ******/
    	Element testcase_outputTcEl = STDom.getOrCreateElement(testcase_structInfoEl, "outputTc");

    	/** 2.1.2.1 header ******/
    	Element testcase_outputTc_headerEl = STDom.getOrCreateElement(testcase_outputTcEl, "header");
    	Element outputLengthField = STDom.createElement(testcase_outputTc_headerEl, "field");
    	outputLengthField.setAttribute("name", "length");
    	outputLengthField.setAttribute("type", "int");
    	outputLengthField.setAttribute("length", "8");
    	Element outputCommandField = STDom.createElement(testcase_outputTc_headerEl, "field");
    	outputCommandField.setAttribute("name", "command");
    	outputCommandField.setAttribute("type", "char");
    	outputCommandField.setAttribute("length", "2");

    	/** 2.1.2.2 body ******/
    	Element testcase_outputTc_bodyEl = STDom.getOrCreateElement(testcase_outputTcEl, "body");
    	Element methodOutputEl = STDom.loadXml(tcInfoDomain.getMethodOutputXml());	//xml 생성

    	NodeList outputFieldList = methodOutputEl.getChildNodes();

    	if(outputFieldList != null && outputFieldList.getLength() > 0) {
    		for(int i = 0; i < outputFieldList.getLength() ; i++) {
    			Node fieldNode = outputFieldList.item(i);
    			setFieldNode(testcase_outputTc_bodyEl, fieldNode, tcInfoDomain.getTestcaseNo());
    		}
    	}

    	/** 2.1.2.3 footer ******/
    	Element testcase_outputTc_footerEl = STDom.getOrCreateElement(testcase_outputTcEl, "footer");
    	Element outputChecksumField = STDom.createElement(testcase_outputTc_footerEl, "field");
    	outputChecksumField.setAttribute("name", "checksum");
    	outputChecksumField.setAttribute("type", "int");
    	outputChecksumField.setAttribute("length", "8");
    	Element outputEndMarkField = STDom.createElement(testcase_outputTc_footerEl, "field");
    	outputEndMarkField.setAttribute("name", "endMark");
    	outputEndMarkField.setAttribute("type", "char");
    	outputEndMarkField.setAttribute("length", "2");

    	/** 2.1.3 dbSettings ******/
    	//TODO 아직 사용하지 않음
    	Element testcase_dbSettingsEl = STDom.getOrCreateElement(testcase_structInfoEl, "dbSettings");

    	/** 2.1.4 dbExpectResult ******/
    	//TODO 아직 사용하지 않음
    	Element testcase_dbExpectResultEl = STDom.getOrCreateElement(testcase_structInfoEl, "dbExpectResult");


    	/** 2.2 testValue ******/
    	Element testcase_testValueEl = STDom.getOrCreateElement(testcaseEl, "testValue");

    	/** 2.2.1 inputTc ******/
    	Element testcase_testValue_inputTcEl = STDom.getOrCreateElement(testcase_testValueEl, "inputTc");

    	/** 2.2.1.1 origInputTc ******/
    	Element testcase_testValue_inputTc_origInputTcEl = STDom.getOrCreateElement(testcase_testValue_inputTcEl, "origInputTc");

    	/** 2.2.1.1.1 header ******/
    	Element testcase_testValue_inputTc_origInputTc_headerEl = STDom.getOrCreateElement(testcase_testValue_inputTc_origInputTcEl, "header");
    	NodeList headerList = testcase_inputTc_headerEl.getChildNodes();
    	setFieldNodeValue(testcase_testValue_inputTc_origInputTc_headerEl, headerList, true);

    	/** 2.2.1.1.2 body ******/
    	Element testcase_testValue_inputTc_origInputTc_bodyEl = STDom.getOrCreateElement(testcase_testValue_inputTc_origInputTcEl, "body");
    	NodeList inputBodyList = testcase_inputTc_bodyEl.getChildNodes();
    	setFieldNodeValue(testcase_testValue_inputTc_origInputTc_bodyEl, inputBodyList, true);

    	/** 2.2.1.1.3 footer ******/
    	Element testcase_testValue_inputTc_origInputTc_footerEl = STDom.getOrCreateElement(testcase_testValue_inputTc_origInputTcEl, "footer");
    	NodeList footerList = testcase_inputTc_footerEl.getChildNodes();
    	setFieldNodeValue(testcase_testValue_inputTc_origInputTc_footerEl, footerList, true);

    	/** 2.2.1.2 normInputTc ******/
    	Element testcase_testValue_inputTc_normInputTcEl = STDom.getOrCreateElement(testcase_testValue_inputTcEl, "normInputTc");

    	/** 2.2.1.3 inputMessage ******/
    	Element testcase_testValue_inputTc_inputMessageEl = STDom.getOrCreateElement(testcase_testValue_inputTcEl, "inputMessage");


    	/** 2.2.2 outputTc ******/
    	Element testcase_testValue_outputTcEl = STDom.getOrCreateElement(testcase_testValueEl, "outputTc");

    	/** 2.2.2.1 origExpectTc ******/
    	Element testcase_testValue_outputTc_origExpectTcEl = STDom.getOrCreateElement(testcase_testValue_outputTcEl, "origExpectTc");
    	/** 2.2.2.1.1 header ******/
    	Element testcase_testValue_outputTc_origExpectTc_headerEl = STDom.getOrCreateElement(testcase_testValue_outputTc_origExpectTcEl, "header");
    	NodeList outputHeaderList = testcase_outputTc_headerEl.getChildNodes();
    	setFieldNodeValue(testcase_testValue_outputTc_origExpectTc_headerEl, outputHeaderList, true);
    	/** 2.2.2.1.2 body ******/
    	Element testcase_testValue_outputTc_origExpectTc_bodyEl = STDom.getOrCreateElement(testcase_testValue_outputTc_origExpectTcEl, "body");
    	NodeList outputBodyList = testcase_outputTc_bodyEl.getChildNodes();
    	setFieldNodeValue(testcase_testValue_outputTc_origExpectTc_bodyEl, outputBodyList, true);
    	/** 2.2.2.1.3 footer ******/
    	Element testcase_testValue_outputTc_origExpectTc_footerEl = STDom.getOrCreateElement(testcase_testValue_outputTc_origExpectTcEl, "footer");
    	NodeList outputFooterList = testcase_outputTc_footerEl.getChildNodes();
    	setFieldNodeValue(testcase_testValue_outputTc_origExpectTc_footerEl, outputFooterList, true);

    	/** 2.2.2.2 normExpectTc ******/
    	Element testcase_testValue_outputTc_normExpectTcEl = STDom.getOrCreateElement(testcase_testValue_outputTcEl, "normExpectTc");

    	/** 2.2.2.3 resultMessageTc ******/
    	Element testcase_testValue_outputTc_resultMessageTcEl = STDom.getOrCreateElement(testcase_testValue_outputTcEl, "resultMessageTc");

    	/** 2.2.2.4 resultMessage ******/
    	Element testcase_testValue_outputTc_resultMessageEl = STDom.getOrCreateElement(testcase_testValue_outputTcEl, "resultMessage");

    	String path = environment.getProperty("testcase.xml.storge");
    	path = path + "/"+tcInfoDomain.getTestcaseNo()+STConst.EXT_XML_OUT;
    	File xmlFile = new File(path);
		STDom.write(el, xmlFile);

    	return path;
    }

    /***
     * Node 의 필드 속성에 맞게 소스 정보를 xml에 넣느다.
     * @param targetEl
     * @param fieldNode
     * @param testcaseNo
     */
    private void setFieldNode(Element targetEl,  Node fieldNode , long testcaseNo) {
		String nodeName = fieldNode.getNodeName();
		//노드네임에 #이 있으면 제거
		if(nodeName.indexOf("#") < 0) {
			//field
			if("field".equals(nodeName)) {
				Element targetFieldEl = STDom.createElement(targetEl, "field");
				NamedNodeMap attributeList = fieldNode.getAttributes();
    			if(attributeList != null && attributeList.getLength() > 0) {
    				for(int j = 0; j < attributeList.getLength() ; j++) {
    					Node attNode = attributeList.item(j);
    					//return 일 경우 name 이 없어서 지정해준다.
    					if("name".equals(attNode.getNodeName()) && StringUtils.isEmpty(attNode.getNodeValue())) {
    						targetFieldEl.setAttribute(attNode.getNodeName(), STDom.getAttrubuteByNode(attNode, "typedef"));
    					}else {
    						targetFieldEl.setAttribute(attNode.getNodeName(), attNode.getNodeValue());
    					}
    				}
    			}
			}
			//fields
			else if("fields".equals(nodeName)) {
				Element targetFieldEl = STDom.createElement(targetEl, "fields");
				//Attribute 생성
				NamedNodeMap attributeList = fieldNode.getAttributes();
				String domainName = "";
    			if(attributeList != null && attributeList.getLength() > 0) {
    				for(int j = 0; j < attributeList.getLength() ; j++) {
    					Node attNode = attributeList.item(j);
    					//return 일 경우 name 이 없어서 지정해준다.
    					if("name".equals(attNode.getNodeName()) && StringUtils.isEmpty(attNode.getNodeValue())) {
    						targetFieldEl.setAttribute(attNode.getNodeName(), STDom.getAttrubuteByNode(attNode, "typedef"));
    					}else {
    						targetFieldEl.setAttribute(attNode.getNodeName(), attNode.getNodeValue());
    					}
    					if("typedef".equals(attNode.getNodeName())) {
    						domainName = attNode.getNodeValue();
    					}
    				}
    			}
    			//도메인 정보 셋팅
    			SourceDomainDomain schDmnDomain = new SourceDomainDomain();
    			schDmnDomain.setTestcaseNo(testcaseNo);
    			schDmnDomain.setDomainName(domainName);

    			SourceDomainDomain rstDmnDomain = testcaseRepository.selectSourceDomain(schDmnDomain);
    			//도메인 정보가 있을때만 수행한다.
    			if(rstDmnDomain != null) {
    				Element domainEl = STDom.loadXml(rstDmnDomain.getDomainXmlContent());

    				NodeList fieldList = domainEl.getChildNodes();
    		    	if(fieldList != null && fieldList.getLength() > 0) {
    		    		for(int i = 0; i < fieldList.getLength() ; i++) {
    		    			Node domainNode = fieldList.item(i);
    		    			/*
    		    			//TODO header, footer 내용 들은 스킵한다.
    		    			if("length".equals(STDom.getAttrubuteByNode(domainNode, "name"))
    		    					||"command".equals(STDom.getAttrubuteByNode(domainNode, "name"))
    		    					||"checksum".equals(STDom.getAttrubuteByNode(domainNode, "name"))
    		    					||"endMark".equals(STDom.getAttrubuteByNode(domainNode, "name"))) {
    		    				continue;
    		    			}*/
    		    			setFieldNode(targetFieldEl, domainNode, testcaseNo);
    		    		}
    		    	}

    			}
			}
			//fixedArray
			else if("fixedArray".equals(nodeName)) {
				Element targetFieldEl = STDom.createElement(targetEl, "fixedArray");
				//Attribute 생성
				NamedNodeMap attributeList = fieldNode.getAttributes();
    			if(attributeList != null && attributeList.getLength() > 0) {
    				for(int j = 0; j < attributeList.getLength() ; j++) {
    					Node attNode = attributeList.item(j);
    					//return 일 경우 name 이 없어서 지정해준다.
    					if("name".equals(attNode.getNodeName()) && StringUtils.isEmpty(attNode.getNodeValue())) {
    						targetFieldEl.setAttribute(attNode.getNodeName(), STDom.getAttrubuteByNode(fieldNode, "typedef").replace("ArrayList<", "").replace(">", ""));
    					}else {
    						targetFieldEl.setAttribute(attNode.getNodeName(), attNode.getNodeValue());
    					}
    				}
    			}

    			NodeList fixedArrayNodeList = fieldNode.getChildNodes();
		    	if(fixedArrayNodeList != null && fixedArrayNodeList.getLength() > 0) {
		    		for(int i = 0; i < fixedArrayNodeList.getLength() ; i++) {
		    			Node fixedArrayNode = fixedArrayNodeList.item(i);
		    			setFieldNode(targetFieldEl, fixedArrayNode, testcaseNo);
		    		}
		    	}
			}
			else {
				logger.info("Not field type");
			}

		}
    }

    /***
     * 소스 구조에 맞게 데이터 필드를 생성한다.
     * @param targetEl xml을 넣은 대상
     * @param fieldList 소스 구조의 Node 리스트
     */
    private void setFieldNodeValue(Element targetEl,  NodeList fieldList, boolean isNullValue) {
    	if(fieldList != null && fieldList.getLength() > 0) {
    		for(int i = 0; i < fieldList.getLength() ; i++) {
    			Node fieldNode = fieldList.item(i);
    			String nodeName = fieldNode.getNodeName();
    			//field
    			if("field".equals(nodeName)) {
    				Element fieldValueEl = STDom.createElement(targetEl, STDom.getAttrubuteByNode(fieldNode, "name"));
        	    	fieldValueEl.setAttribute("valueDiv", "VALUE");
        	    	fieldValueEl.setAttribute("isBinary", "N");
        	    	fieldValueEl.setAttribute("isCompare", "N");
        	    	fieldValueEl.setAttribute("valLength", STDom.getAttrubuteByNode(fieldNode, "length"));
        	    	//노드의 값을 초기화 할지 확인한다.
        	    	if(isNullValue) {
        	    		fieldValueEl.setNodeValue("");
        	    	}
    			}
    			//fields
    			else if("fields".equals(nodeName)) {
    				Element fieldValueEl;
    				String typedef[] = STDom.getAttrubuteByNode(fieldNode, "typedef").split("\\.");
    				//typedef null 체크
    				if(typedef != null && typedef.length > 0) {
    					fieldValueEl = STDom.createElement(targetEl, typedef[typedef.length-1]);
    				}else {
    					fieldValueEl = STDom.createElement(targetEl, STDom.getAttrubuteByNode(fieldNode, "typedef"));
    				}
    				setFieldNodeValue(fieldValueEl,fieldNode.getChildNodes(),isNullValue);
    			}
    			//fixedArray
    			else if("fixedArray".equals(nodeName)) {
    				//fixedArray는 10회 반복해준다.
    				for(int z = 0 ; z < 10 ; z++) {
    					if(!StringUtils.isEmpty(STDom.getAttrubuteByNode(fieldNode, "name"))) {
    						Element fieldValueEl = STDom.createElement(targetEl
    								, STDom.getAttrubuteByNode(fieldNode, "typedef").replace("Array","").replace("List","").replace("&lt;", "").replace("&gt;", "").replace("<", "").replace(">", ""));
    						//seq 설정
    						fieldValueEl.setAttribute("seq", z+"");
    						setFieldNodeValue(fieldValueEl,fieldNode.getChildNodes().item(0).getChildNodes(),isNullValue);
    					}
    				}
    			}
    			else {
    				logger.info("Not field type");
    			}

    		}
    	}
    }
    private void setFieldNodeRuleNo(Node targetEl) {
    	/*
		{
			APIIndex:1,
			parameters:
				{
					isNormal:true,
					length:8,
					type:""
				}
		}
		*/
    	Document doc = targetEl.getOwnerDocument();
    	String fieldTypeNo = STDom.getAttrubuteByNode(targetEl, "fieldTypeNo");

    	//값이 존재 할때만 수행한다.
    	if(!StringUtils.isEmpty(fieldTypeNo) && !"0".equals(fieldTypeNo)) {
    		//알고리즘 파라미터 조회(알고리즘 번호도 같이 조회 해온다.)
    		ExecuteRandomGenerationAlgorithmInfoDomain algoSchDomain = new ExecuteRandomGenerationAlgorithmInfoDomain();
    		algoSchDomain.setFieldTypeNo(Long.parseLong(fieldTypeNo));
    		List<ExecuteRandomGenerationAlgorithmInfoDomain> algoInfoList = randomGenerationRepository.selectRuleAlgorithmParameterByFieldTypeNo(algoSchDomain);

    		if(algoInfoList != null && algoInfoList.size() > 0) {
    			//전체 JSON 맵
    			Map<String, Object> fieldMap = new HashMap<String, Object>();

    			//APIIndex 셋팅
    			fieldMap.put("APIIndex",algoInfoList.get(0).getAlgorithmNo()+"");

        		//parameters 셋팅
    			Map<String, Object> parametersMap = new HashMap<String, Object>();
    			for(int i = 0 ; i < algoInfoList.size() ; i++) {
    				ExecuteRandomGenerationAlgorithmInfoDomain algoInfo = algoInfoList.get(i);
    				parametersMap.put(algoInfo.getParameterName(), algoInfo.getParameterDefaultValue());
    			}

    			fieldMap.put("parameters", parametersMap);

        		//JSON 데이터 입력
    			JSONObject json = getJsonStringFromMap(fieldMap);
    			String jsonStr = json.toJSONString();
    			//TODO 하드코딩 실행 테스트
//    			String jsonStr = "{\"APIindex\":\"1\", \"parameters\": {" +
//    					"        \"isNormal\": \"true\"," +
//    					"        \"length\": \"8\"," +
//    					"        \"type\": \"111000\"" +
//    					"    }}";
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
    public JSONObject getJsonStringFromMap( Map<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }

        return jsonObject;
    }
}

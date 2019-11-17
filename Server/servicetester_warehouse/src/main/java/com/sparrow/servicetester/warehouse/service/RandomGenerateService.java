package com.sparrow.servicetester.warehouse.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sparrow.servicetester.util.STDom;
import com.sparrow.servicetester.warehouse.domain.methodoverview.ViewMethodOverviewDomain;
import com.sparrow.servicetester.warehouse.domain.randomgeneration.ViewRandomRuleFieldMappingDomain;
import com.sparrow.servicetester.warehouse.domain.testcase.SourceDomainDomain;
import com.sparrow.servicetester.warehouse.exception.SSTException;
import com.sparrow.servicetester.warehouse.repository.MethodOverviewRepository;
import com.sparrow.servicetester.warehouse.repository.RandomGenerationMngAlgorithmRepository;
import com.sparrow.servicetester.warehouse.repository.TestcaseRepository;

/**
* 작성 개요 : 최영진(2019.5.24)
* 클래스 개요 : 테스트케이스 그룹의 로직을 수행하는 Service 클래스
*/
@Service
public class RandomGenerateService {
	/*********************** logger ************************************/
	static final Logger logger = LoggerFactory.getLogger(RandomGenerateService.class);

	/*********************** environment config ************************************/
	@Inject
    private Environment environment;

	/*********************** repository ************************************/
    @Inject
    private MethodOverviewRepository methodOverviewRepository;
	@Inject
    private TestcaseRepository testcaseRepository;
    @Inject
    private RandomGenerationMngAlgorithmRepository randomGenerationMngAlgorithmRepository;

    /***
     * 랜덤 규칙 필드 매칭 조회
     * @param domain
     * @return
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public Map<String, Object> viewRandomRuleFieldMapping(ViewRandomRuleFieldMappingDomain domain)  throws Exception {
    	//규칙 관리 정보 조회
    	ViewRandomRuleFieldMappingDomain ruleInfoDomain = randomGenerationMngAlgorithmRepository.selectRandomGenerationRuleDetail(domain);

    	//없으면 오류 처리
    	if(ruleInfoDomain == null) {
    		//대상 정보가 없습니다.
    		throw new SSTException("message.exception.noTarget",new String[] {""});
    	}

    	//마스터 데이터 넣기
    	Map<String, Object> fileNameMap = new HashMap<String, Object>();
    	fileNameMap.put("value",ruleInfoDomain.getFileName());
    	fileNameMap.put("status","R");
    	Map<String, Object> methodNameMap = new HashMap<String, Object>();
    	methodNameMap.put("value",ruleInfoDomain.getMethodName());
    	methodNameMap.put("status","R");
    	Map<String, Object> algorithmLevelMap = new HashMap<String, Object>();
    	algorithmLevelMap.put("value",ruleInfoDomain.getAlgorithmLevel());
    	algorithmLevelMap.put("status","R");
    	Map<String, Object> ruleNameMap = new HashMap<String, Object>();
    	ruleNameMap.put("value",ruleInfoDomain.getRuleName());
    	ruleNameMap.put("status","R");
    	Map<String, Object> masterMap = new HashMap<String, Object>();
    	masterMap.put("fileName",fileNameMap);
    	masterMap.put("methodName",methodNameMap);
    	masterMap.put("algorithmLevel",algorithmLevelMap);
    	masterMap.put("ruleName",ruleNameMap);

    	// 필드 xml 존재 여부 확인
    	String rulFieldXml = "";
    	if(StringUtils.isEmpty(ruleInfoDomain.getRuleFieldXml())) {
    		//규칙 필드 xml를 생성한다.
    		rulFieldXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+createRuleFieldXml(ruleInfoDomain.getMethodNo());
    	}else {
    		rulFieldXml = ruleInfoDomain.getRuleFieldXml();
    	}

    	//rule 필드 로딩 객체 로딩
    	Element rootEl = STDom.loadXml(rulFieldXml);

    	//노드리스트 가져오기
    	Map<String, Object> detailRuleMap = new HashMap<String,Object>();
    	setXmlToJson(detailRuleMap, rootEl);

    	//JSON 셋팅 반환 Map
    	Map<String, Object> resultMap = new HashMap<String, Object>();

    	//디테일 데이터 객체 셋팅
    	Map<String, Object> detailMap = new HashMap<String, Object>();
    	Map<String, Object> detailDataMap = new HashMap<String, Object>();
    	detailDataMap.put("varName", "rules");
    	detailDataMap.put("valLength", "");
    	detailDataMap.put("varType", "");
    	detailDataMap.put("fieldTypeNo", "");
    	detailDataMap.put("status", "R");

    	//디테일 본 내용 셋팅
    	List<Map<String, Object>> detailRuleList = new ArrayList<Map<String,Object>>();
    	detailRuleList.add(detailRuleMap);

    	detailMap.put("data", detailDataMap);
    	detailMap.put("height", 32);
    	detailMap.put("children", detailRuleList);

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
    *
    * @param testcaseNo 생성할 메소드 번호
    * @return
    * @throws Exception
    */
    public String createRuleFieldXml(long methodNo) throws Exception {
    	//테스트 케이스 정보 조회
    	ViewMethodOverviewDomain schTcDomain = new ViewMethodOverviewDomain();
    	schTcDomain.setMethodNo(methodNo);
    	ViewMethodOverviewDomain methodInfoDomain = methodOverviewRepository.selectMethodOverview(schTcDomain);

    	if(methodInfoDomain == null) {
    		//대상 정보가 없습니다.
    		throw new SSTException("message.exception.noTarget",new String[] {""});
    	}

    	//구조 정보를 뽑아온다.
    	Element el = STDom.loadXml("<structInfo></structInfo>");	//xml 생성

    	/** 1. header ******/
    	Element headerEl = STDom.getOrCreateElement(el, "header");
    	Element lengthField = STDom.createElement(headerEl, "field");
    	lengthField.setAttribute("name", "length");
    	lengthField.setAttribute("type", "int");
    	lengthField.setAttribute("length", "8");
    	Element commandField = STDom.createElement(headerEl, "field");
    	commandField.setAttribute("name", "command");
    	commandField.setAttribute("type", "char");
    	commandField.setAttribute("length", "2");

    	/** 2. body ******/
    	Element bodyEl = STDom.getOrCreateElement(el, "body");
    	Element methodInputEl = STDom.loadXml(methodInfoDomain.getMethodInputXml());	//xml 생성

    	NodeList fieldList = methodInputEl.getChildNodes();

    	if(fieldList != null && fieldList.getLength() > 0) {
    		for(int i = 0; i < fieldList.getLength() ; i++) {
    			Node fieldNode = fieldList.item(i);
    			setFieldNode(bodyEl, fieldNode, methodInfoDomain.getMethodNo());
    		}
    	}

    	/** 3. footer ******/
    	Element footerEl = STDom.getOrCreateElement(el, "footer");
    	Element checksumField = STDom.createElement(footerEl, "field");
    	checksumField.setAttribute("name", "checksum");
    	checksumField.setAttribute("type", "int");
    	checksumField.setAttribute("length", "8");
    	Element endMarkField = STDom.createElement(footerEl, "field");
    	endMarkField.setAttribute("name", "endMark");
    	endMarkField.setAttribute("type", "char");
    	endMarkField.setAttribute("length", "2");

    	//구조 정보를 기반으로 데이터 입력 형태로 변환한다.
    	Element valEl = STDom.loadXml("<rules></rules>");	//xml 생성
    	/** 2.2.1.1.1 header ******/
    	Element valHeaderEl = STDom.getOrCreateElement(valEl, "header");
    	NodeList headerList = headerEl.getChildNodes();
    	setFieldNodeValue(valHeaderEl, headerList);

    	/** 2.2.1.1.2 body ******/
    	Element valBodyEl = STDom.getOrCreateElement(valEl, "body");
    	NodeList bodyList = bodyEl.getChildNodes();
    	setFieldNodeValue(valBodyEl, bodyList);

    	/** 2.2.1.1.3 footer ******/
    	Element valFooterEl = STDom.getOrCreateElement(valEl, "footer");
    	NodeList footerList = footerEl.getChildNodes();
    	setFieldNodeValue(valFooterEl, footerList);

    	return STDom.getSourceFromNode(valEl);
    }

    /***
     * Node 의 필드 속성에 맞게 소스 정보를 xml에 넣느다.
     * @param targetEl
     * @param fieldNode
     * @param methodNo
     */
    private void setFieldNode(Element targetEl,  Node fieldNode , long methodNo) {
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
    			schDmnDomain.setMethodNo(methodNo);
    			schDmnDomain.setDomainName(domainName);
    			SourceDomainDomain rstDmnDomain = testcaseRepository.selectSourceDomain(schDmnDomain);
    			//도메인 정보가 있을때만 수행한다.
    			if(rstDmnDomain != null) {
    				Element domainEl = STDom.loadXml(rstDmnDomain.getDomainXmlContent());

    				NodeList fieldList = domainEl.getChildNodes();
    		    	if(fieldList != null && fieldList.getLength() > 0) {
    		    		for(int i = 0; i < fieldList.getLength() ; i++) {
    		    			Node domainNode = fieldList.item(i);
    		    			setFieldNode(targetFieldEl, domainNode, methodNo);
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
		    			setFieldNode(targetFieldEl, fixedArrayNode, methodNo);
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
    private void setFieldNodeValue(Element targetEl,  NodeList fieldList) {
    	if(fieldList != null && fieldList.getLength() > 0) {
    		for(int i = 0; i < fieldList.getLength() ; i++) {
    			Node fieldNode = fieldList.item(i);
    			String nodeName = fieldNode.getNodeName();
    			//field
    			if("field".equals(nodeName)) {
    				Element fieldValueEl = STDom.createElement(targetEl, STDom.getAttrubuteByNode(fieldNode, "name"));
        	    	fieldValueEl.setAttribute("valueDiv", "VALUE");
        	    	fieldValueEl.setAttribute("valLength", STDom.getAttrubuteByNode(fieldNode, "length"));
        	    	//필드 유형 번호 어트리뷰트 생성
        	    	fieldValueEl.setAttribute("fieldTypeNo", "0");
        	    	fieldValueEl.setNodeValue("");
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
    				setFieldNodeValue(fieldValueEl,fieldNode.getChildNodes());
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
    						setFieldNodeValue(fieldValueEl,fieldNode.getChildNodes().item(0).getChildNodes());
    					}
    				}
    			}
    			else {
    				logger.info("Not field type");
    			}
    		}
    	}
    }

    /***
     * 규칙 XML을 JSON 으로 변경한다.
     * @param saveMap JSON으로 변환 된 데이터가 들어가는 Map
     * @param contentNode 변환 대상이 되는 xml의 노드 객체
     */
    public void setXmlToJson(Map<String, Object> saveMap, Node contentNode) {
    	//xml의 attribute 저장
    	Map<String, Object> conMap = new HashMap<String, Object>();
    	conMap.put("varName",contentNode.getNodeName());
    	conMap.put("valLength", STDom.getAttrubuteByNode(contentNode, "valLength"));
    	conMap.put("varType", STDom.getAttrubuteByNode(contentNode, "valueDiv"));
    	conMap.put("fieldTypeNo", STDom.getAttrubuteByNode(contentNode, "fieldTypeNo"));
    	//fixedArray 일경우 seq 변호를 셋팅한다.
    	if(!StringUtils.isEmpty(STDom.getAttrubuteByNode(contentNode, "seq"))) {
    		conMap.put("seq",STDom.getAttrubuteByNode(contentNode, "seq"));
    	}
    	conMap.put("status","R");
    	saveMap.put("data", conMap);

    	NodeList conNL = contentNode.getChildNodes();
    	if(conNL != null && conNL.getLength() > 0) {
    		List<Map<String,Object>> childrenList = new ArrayList<Map<String,Object>>();
    		for(int i = 0 ; i < conNL.getLength() ; i++) {
    			if(conNL.item(i).getNodeName().indexOf("#") < 0) {
    				childrenList.add(new HashMap<String, Object>());
    				setXmlToJson(childrenList.get(childrenList.size()-1), conNL.item(i));
    			}
    		}
    		//칠드런 넣기
    		saveMap.put("children", childrenList);
    	}
    }

}
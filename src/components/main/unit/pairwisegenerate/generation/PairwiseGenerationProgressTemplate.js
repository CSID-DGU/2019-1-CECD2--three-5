/*
최영진 (20190522)
소스 수집 예제를 위해 만든 화면이므로 나중에 삭제해야함.

App.js
import 영역에
import {default as ServiceRunProgressTemplate} from 'pages/ServiceRunProgressTemplate';
추가
라우터 선언 부분에
<Route path="/SourceCollection" component={ServiceRunProgressTemplate}/>
추가
http://localhost:3000/SourceCollection 으로 접근하면 됨
*/
import React, { Component } from 'react';
import whaxios from 'lib/whaxios';
import ConnectSocket from 'lib/websocket';

import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

import {default as UUID} from "uuid";

class PairwiseGenerationProgressTemplate extends Component {
  uuid = "";  //웹소켓 연결 시 생성되는 UUID 변수
  /******* 웹소켓 관련 ******/
  socket = null;  //websocket 연결 객체
  isConnectWebSocket = false; //웹소켓 생존 여부 변수
  retryLimit = 5; //재접속 처리 제한
  retryCnt = 1; //현재 재접속한 수
  retryWaitMills = 1000;  //재접속 대시 시간 (단위:밀리초 2초->2000)
  /****** 객체 레퍼런스 관련 *****/
  projectNoText = null; //프로젝트 번호 텍스트 박스
  exeTypeSelect = null;  //소스 수집 타입 구분 콤보박스 객체
  sourcePathText = null;  //소스 수집 경로 텍스트 박스
  uuidReflectionText = null; //uuid 반영 텍스트 박스
  stateTextarea = null; //uuid 반영 텍스트 박스

  //생성자
  constructor() {
    super();
    this.state = {
      statelog:'',
    }
  }

  //실행 버튼 클릭 시
  executeSourceCollection = () =>{
    //이미 실행 중이면 실행하지 않는다.
    if(this.isConnectWebSocket){
      alert("실행 중입니다.");
      return false;
    }
    //프로젝트 번호 입력값 검색
    if(this.projectNoText.value == ''){
      alert("프로젝트번호를 입력해주십시오.");
      return false;
    }

    //프로젝트가 아니면 소스 경로 입력값 검색
    /*
    if(this.exeTypeSelect.value != 'project'){
      if(this.sourcePathText.value == ''){
        alert("폴더, 파일 수집 시 경로를 입력해주십시오.");
        return false;
      }
    }
    */
    //UUID 생성 후 state 저장
    this.uuid = UUID.v4();
    console.log("UUID 생성 : "+this.uuid);

    //웹소켓 연결
    this.connectWebSocket(this.uuid);
  }

  executeSourceCollectionReflection = () =>{
    //UUID 입력값 검색
    if(this.uuidReflectionText.value == ''){
      alert("반영 UUID를 입력해주십시오.");
      return false;
    }

    let ret = {
      message : "",
    }

    //전송 데이터 셋팅
    let requestInfo = {
      exectionUuid:this.uuidReflectionText.value
    };

    whaxios.post('/sourceCollection/executeSourceCollectionReflection/'+UUID.v4(), requestInfo)
    .then(function(response){
      //callback(ret);
      alert("반영완료");
    }).catch(function(error){
      console.log("catch error ====>");
      console.log(error);
      alert("요청을 수행할 수 없습니다.");
      ret.message = JSON.stringify(error);
      ret.finished = false;
      //callback(ret);
      alert("반영 중 에러!!!");
    }); //catch절 끝

  }



  //WebSocket 접속 실행
  connectWebSocket = (uuid) =>  {
    //객체가 생성
    //생성되면 바로 연결 시도한다.
    this.socket = ConnectSocket.getInstance(uuid);

    //성공적으로 연결되면 함수가 호출 된다.
    //연결 후 작업은 여기서 처리한다.
    this.socket.onopen = () => {
      this.setState({
        statelog: "테스트 케이스 랜덤 생성을 요청합니다. 고유 요청 번호 : "+uuid
      });
      //연결 처리
      this.isConnectWebSocket = true;

      let requestInfo = {
        projectNo:this.projectNoText.value,
        methodNo:this.nodeIdText.value,
        ruleNo:this.ruleNoText.value,
        generateCount:this.generatedCountText.value
      };

      //소스 수집 실행 요청
      this.executeSourceCollectionAxios(requestInfo, {}, (response) => {
        console.log("요청했음");
      });
    }

    //연결 실패 시 호출된다.
    this.socket.onerror = (response) => {
      const { statelog } = this.state;
      this.setState({
        statelog: statelog + (statelog !== "" ? "\r\n" : "") + "연결 시도 중입니다.("+this.retryCnt+"/"+this.retryLimit+")"
      });

      //재접속 연결 제한 확인
      if(this.retryCnt <= this.retryLimit){
        //10초 대기 시간후 재접속
        setTimeout(() => {
          console.log("wait and reconnect.("+this.retryCnt+"/"+this.retryLimit+")");
          this.retryCnt++;
          this.connectWebSocket();
        }, this.retryWaitMills);
      }else{
        this.setState({
          statelog: statelog + (statelog !== "" ? "\r\n" : "") + "연결을 할 수 없는 상태입니다. 관리자에게 문의하여 주십시오."
        });
      }
    }

    //서버에서 메시지 수신 시 호출된다.
    this.socket.onmessage = (response) => {
        let message = JSON.parse(response.data);
        const { statelog } = this.state;
        this.setState({
          statelog: statelog + (statelog !== "" ? "\r\n" : "") + message.content
        });
        this.stateTextarea.focus();
    }

    //화면에서 빠져 나올 시 호출된다.
    //WebSocket close를 호출한다.
    window.onbeforeunload = () => {
      console.log("WebSocket close.");
    }
  }

  //소스 수집 실행 API 호출
  executeSourceCollectionAxios (requestInfo, data, callback) {
    let ret = {
      message : "",
    }
    whaxios.post('/pairwiseGeneration/executePairwiseGeneration/'+this.uuid, requestInfo)
    .then(function(response){
      callback(ret);
    }).catch(function(error){
      console.log("catch error ====>");
      console.log(error);
      alert("요청을 수행할 수 없습니다.");
      ret.message = JSON.stringify(error);
      ret.finished = false;
      callback(ret);
    }); //catch절 끝
  }

  //웹소켓이 살아 있는지 확인한다.
  webSocketHealthCheck = () =>  {
    if(this.socket != null){
      let messageDto = JSON.stringify({ wsType: "AliveCheck", uuid: this.state.inputuuid, content:"Asdfasdfasdfadsf"  });
      this.socket.send(messageDto);
    }
  }

  render() {
    return (
      <Container fluid>
       <Row>
        <Col>
          프로젝트 번호 : <input type="text" name="projectNoText" value={this.props.projectNo} ref={ref => {this.projectNoText = ref;}}/>
        </Col>
       </Row>
       <Row>
        <Col>
          메소드 번호 : <input type="text" name="nodeIdText" value={this.props.nodeId} ref={ref => {this.nodeIdText = ref;}}/>
        </Col>
       </Row>
       <Row>
        <Col>
          렌덤 규칙 : <input type="text" name="ruleNoText" value={this.props.ruleNo} ref={ref => {this.ruleNoText = ref;}}/>
        </Col>
       </Row>
       <Row>
        <Col>
         생성할 개수 : <input type="text" name="noOfService" value={this.props.generatedCount} ref={ref => {this.generatedCountText = ref;}}/>
        </Col>
         <Col>
          <input type="button" onClick={this.executeSourceCollection} value="실행" />
         </Col>
       </Row>
       <Row>
         <Col>
          <textarea rows="20" cols="200" placeholder={this.state.statelog} readOnly="true" name="stateTextarea" ref={ref => {this.stateTextarea = ref;}}></textarea>
         </Col>
       </Row>
      </Container>
    );
  }
}

export default PairwiseGenerationProgressTemplate;

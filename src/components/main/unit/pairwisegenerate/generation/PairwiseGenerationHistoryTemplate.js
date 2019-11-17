/**
 * 작성자: 김지운, 원예인
 * 설명: 랜덤 생성 이력 탭 안에 있는 화면과 function들 정리
**/
import React, {Component} from 'react';
import Util from 'lib/Util'; //API 호출
import { withRouter } from 'react-router-dom';

import PairwiseGenerationHistoryTable from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationHistoryTable';
import PairwiseGenerationInputsTemplate from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationInputsTemplate';
import styles from'./PairwiseGenerationHistoryTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseGenerationHistoryTemplate extends Component {
  constructor(props) {
    super(props);
    
    this.state = {
      title: "랜덤 생성 이력", /*테이블 명*/
      totalCnt: 0, /*총 데이터 수(row개수)*/
      isTotalCnt: 1, /*totalCnt를 조회 할지 말지*/
      pairwiseGenHistList: [], /*테이블로 전달할 배열*/
      pageCnt: 10, /*한 번에 몇개 데이터 보여줄건지*/
      pageNum: 1,  /*현재페이지 번호*/
      from: 1, /*row의 시작번호, pageNum이나 pageCnt의 값이 변하면 재 계산*/
      to: 10,
      selected: []
      };

    this.remoteViewTableList = this.remoteViewTableList.bind(this); /*권한 테이블 목록 조회 또는 재조회*/
    this.localGoPage = this.localGoPage.bind(this); /*에이전트 테이블 한 페이지에 몇 개 보여줄 것인지 pageCnt 변경*/
    this.localOnChangeSelectValue = this.localOnChangeSelectValue.bind(this); /*페이지 이동*/
  }

  componentDidMount(){
    this.remoteViewTableList(this.state.pageNum, this.state.pageCnt, this.state.executionUuid);  //처음 조회시 pageNum:1, pageCnt:10
  }

  /* 1. 테이블 조회, 재조회 (검색조건과 재조회 / 삭제나 편집 후 재조회) */
  remoteViewTableList = (pageNum, pageCnt, searchGenerator, searchGenerateDateFrom, searchGenerateDateTo, searchRuleKind) => {
    const url = '/pairwiseGeneration/viewPairwiseGenerationHistoryList/1'; //나중에 executionUuid 수정

    console.log("projectNo", this.props.projectNo);
    console.log("type", this.props.type);
    console.log("nodeId", this.props.nodeId);


    const data = {
      params: {
        executionProjectNo: this.props.projectNo,
        methodNo: this.props.nodeId,
        pageNum: pageNum,
        pageCnt: pageCnt,
        searchGenerator: searchGenerator,
        searchGenerateDateFrom: searchGenerateDateFrom,
        searchGenerateDateTo: searchGenerateDateTo,
        searchRuleKind: searchRuleKind
       }
    };

    console.log("begin aaaaaaaa");

    Util.processAxios(url, data, (msg, response) =>{
      //받아온 데이터를 임시 변수에 저장
      let pairwiseGenHistList = [];

      if (response == null){
        console.log("aaaaaaaaaaaaaaaaaa");
        pairwiseGenHistList = [{
            'executionUuid': 1,
            'generateNo': 1,
            'generateComment': 1,
            'generator': 1,
            'generateDate': 1,
            'pageCnt':1,
            'pageNum':1,
            'generatedCount': 1,
            'ruleKind': 1
        }];

        this.setState({
          pairwiseGenHistList: pairwiseGenHistList,
          totalCnt: 0
        });

        alert('Error:msg=' + msg);
        alert('Error:response' + response);
      }
      else{
        console.log("baaaaaaaaaaaaaaaaa");
        pairwiseGenHistList = response.data.body.pairwiseGenHistList;

        pairwiseGenHistList.map((pairwiseGenHistListMap, i) => {
          pairwiseGenHistListMap = {...pairwiseGenHistListMap};
          pairwiseGenHistList[i] = pairwiseGenHistListMap;
          return true;
        })
        //setState 로 state에 다시 변경된 list 다시 저장
        this.setState({
          pairwiseGenHistList: pairwiseGenHistList,
          totalCnt: response.data.body.totalCnt,
          //rightTopSuccessCnt: response.data.body.countInfo.rightTopSuccessCnt,
          //rightTopFailCnt: response.data.body.countInfo.rightTopFailCnt
        });
      }
    });
  }

  /* 2.이동 할 PageNo 넘겨주고 테이블 재조회*/
  localGoPage = (e) => {
    const target = e.target;
    var pageNo = target.value;

    //<, > 버튼 클릭 시 이전 블럭 번호, 다음 블럭 번호를 pageNo으로 설정
    if(target.value==="<<"||target.value===">>"){
      pageNo = parseInt(target.name);
    }
    if(target.value==="<"||target.value===">"){
      pageNo = parseInt(target.name);
    }

    this.setState({pageNum:pageNo}); //원하는 시점에 값이 설정되지 않음 04.29수정
    //this.setState는 원하는 시점에 값이 설정되지 않음 (이유: setState는 효율적으로 마운트해주기 위해 비동기처리)
    //테이블 조회시 pageNo 값을 직접 파라미터로 넘겨줌

    this.remoteViewTableList(pageNo,this.state.pageCnt);

    //from to 계산
    const totalPageCnt = Math.floor(this.state.totalCnt/this.state.pageCnt)+(this.state.totalCnt%this.state.pageCnt>0?1:0);
    if(pageNo===totalPageCnt){ //pageNo가 마지막 페이지인 경우 ex) 총갯수 48개 41-48보임
      this.setState({
        from:(pageNo-1)*this.state.pageCnt +1,
        to: (pageNo-1)*this.state.pageCnt+(this.state.totalCnt%this.state.pageCnt)
      });
    }else{  //나머지 페이지
      this.setState({
        from:(pageNo-1)*this.state.pageCnt +1,
        to: pageNo*this.state.pageCnt
      });
    }
  }

  /* 3. Listbox값 변경 시, 테이블 재조회*/
  localOnChangeSelectValue = (e) => {
    //from to 계산
    const target = e.target;
    const value = target.value;
    const totalPageCnt = Math.floor(this.state.totalCnt/e.target.value)+(this.state.totalCnt%e.target.value>0?1:0);

    if(this.state.pageNum===totalPageCnt){ //pageNo가 마지막 페이지인 경우 ex) 총갯수 48개 41-48보임
        this.setState({
          from:(this.state.pageNum-1)*e.target.value +1,
          to: (this.state.pageNum-1)*e.target.value+(this.state.totalCnt%e.target.value),
        });
      }else{  //나머지 페이지
        this.setState({
          from:(this.state.pageNum-1)*e.target.value+1,
          to: this.state.pageNum*e.target.value,
        });
      }

      this.setState( //Listbox값 변경시 state의 pageCnt 변경
       {pageCnt:value}, () => console.log(this.state.pageCnt)
     );

    this.remoteViewTableList(this.state.pageNum,e.target.value);
  }

  render() {
    return(
      <div className = 'pairwise-generation-history-template'>
        <div>
          <PairwiseGenerationInputsTemplate {...this.state}
          testRun = {this.props.testRun}/>
        </div>
        {this.state.pairwiseGenHistList != null && <PairwiseGenerationHistoryTable {...this.state}
                        testRun = {this.props.testRun}
                        testResults = {this.props.testResults}
                        remoteViewTableList = {this.remoteViewTableList}
                        localGoPage = {this.localGoPage}
                        localOnChangeSelectValue = {this.localOnChangeSelectValue}
                        remoteBatchDelete = {this.remoteBatchDelete}
                        />
        }
      </div>
    )
  }
}

export default withRouter(PairwiseGenerationHistoryTemplate);

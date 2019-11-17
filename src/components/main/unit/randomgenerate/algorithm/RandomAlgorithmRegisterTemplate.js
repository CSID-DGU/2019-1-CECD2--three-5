/**
 * 작성자 남예진
 * 설명: 서비스 실행 테스트케이스별 목록 탭 안에 있는 화면과 function들 정리
**/
import React, {Component} from 'react';
import Util from 'lib/Util'; //API 호출
import { withRouter } from 'react-router-dom';

import RandomAlgorithmRegisterTable from 'components/main/unit/randomgenerate/algorithm/RandomAlgorithmRegisterTable';
import styles from'./RandomAlgorithmRegisterTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomAlgorithmRegisteroTemplate extends Component {
  constructor(props) {
    super(props);

    this.state = {
      title: "알고리즘 등록", /*테이블 명*/
      totalCnt: 0, /*총 데이터 수(row개수)*/
      isTotalCnt: 1, /*totalCnt를 조회 할지 말지*/
      randomGenAlgorithmList: [], /*테이블로 전달할 배열*/
      pageCnt: 10, /*한 번에 몇개 데이터 보여줄건지*/
      pageNum: 1,  /*현재페이지 번호*/
      executionUuid: this.props.executionUuid,
      from: 1, /*row의 시작번호, pageNum이나 pageCnt의 값이 변하면 재 계산*/
      to: 10,
      selected: []
      };

    this.remoteViewResultsList = this.remoteViewResultsList.bind(this); /*권한 테이블 목록 조회 또는 재조회*/
    this.localGoPage = this.localGoPage.bind(this); /*에이전트 테이블 한 페이지에 몇 개 보여줄 것인지 pageCnt 변경*/
    this.localOnChangeSelectValue = this.localOnChangeSelectValue.bind(this); /*페이지 이동*/
  }

  componentDidMount(){
    this.remoteViewResultsList(this.state.pageNum, this.state.pageCnt,this.state.executionUuid);  //처음 조회시 pageNum:1, pageCnt:10
  }

  /* 1. 테이블 조회, 재조회 (검색조건과 재조회 / 삭제나 편집 후 재조회) */
  remoteViewResultsList = (pageNum, pageCnt, searchAlgorithmTypeName, searchAlgorithmReturnType, searchGenerator, searchGenerateDateFrom, searchGenerateDateTo) => {
    const url = '/randomGeneration/viewRandomGenerationAlgorithmList/1';
    const data = {
      params: {
        //executionUuid: this.props.executionUuid,
        executionUuid: "11111111",
        pageCnt: pageCnt,
        pageNum: pageNum,
        searchAlgorithmTypeName: searchAlgorithmTypeName,
        searchAlgorithmReturnType: searchAlgorithmReturnType,
        searchGenerator: searchGenerator,
        searchGenerateDateFrom: searchGenerateDateFrom,
        searchGenerateDateTo: searchGenerateDateTo

        //searchExpectedResultCond:  searchExpectedResultCond,
        //searchExpectedDbResultCond: searchExpectedDbResultCond
       }
    };
    Util.processAxios(url, data, (msg, response) =>{
      //받아온 데이터를 임시 변수에 저장
      let randomGenAlgorithmList = [];

        if (response == null){
          randomGenAlgorithmList = [{
            'algorithmNo': 1,
            'algorithmTypeName': 1,
            'algorithmReturnType': 1,
            'generator': 1,
            'revisionDate': 1
          }];

          this.setState({
            randomGenAlgorithmList: randomGenAlgorithmList,
            totalCnt: 0
          });

          return(
            alert('No data to be shown'+msg)
          )
        }
        else{
          randomGenAlgorithmList = response.data.body.randomGenAlgorithmList;

          randomGenAlgorithmList.map((randomGenAlgorithmListMap, i) => {
            randomGenAlgorithmListMap = {...randomGenAlgorithmListMap, "rowType": "R","prevRowType":"R", "rowSeqNo": i};
            randomGenAlgorithmList[i] = randomGenAlgorithmListMap;
          return true;
        })
          //setState 로 state에 다시 변경된 list 다시 저장
          this.setState({
            randomGenAlgorithmList: randomGenAlgorithmList,
            totalCnt: response.data.body.totalCnt,

        });
        }
    });
  }

  /* 2. 이동 할 PageNo 넘겨주고 테이블 재조회*/
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

    this.remoteViewResultsList(pageNo,this.state.pageCnt);

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

    this.remoteViewResultsList(this.state.pageNum,e.target.value);
  }

  render() {
    return(
      <div className = 'random-algorithm-register-template'>
        {this.state.randomGenAlgorithmList != null && <RandomAlgorithmRegisterTable {...this.state}
                        parameterRegister = {this.props.parameterRegister}
                        remoteViewResultsList = {this.remoteViewResultsList}
                        localGoPage = {this.localGoPage}
                        localOnChangeSelectValue = {this.localOnChangeSelectValue}
                        remoteBatchDelete = {this.remoteBatchDelete}
                        />
        }
      </div>
    )
  }
}

export default withRouter(RandomAlgorithmRegisteroTemplate);

/**
 * 작성자 남예진
 * 설명: 서비스 실행 테스트케이스별 목록 탭 안에 있는 화면과 function들 정리
**/
import React, {Component} from 'react';
import BootstrapTable from 'react-bootstrap-table-next';
import update from 'react-addons-update'; //react-addons-update 라이브러리. 테이블의 배열 추가하거나 수정 시 사용
import Util from 'lib/Util'; //API 호출
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import BootstrapSwitchButton from 'bootstrap-switch-button-react';
import ToolkitProvider, {Search} from 'react-bootstrap-table2-toolkit';
import { withRouter } from 'react-router-dom';

import PairwiseTypeFieldMappingInformationTemplate from 'components/main/unit/pairwisegenerate/type/PairwiseTypeFieldMappingInformationTemplate';
import PairwiseTypeFieldMappingToolBar from 'components/main/unit/pairwisegenerate/type/PairwiseTypeFieldMappingToolBar';
import ListCountNavigation from 'components/common/policy/ListCountNavigation';
import TypeFieldMappingContainer from 'components/main/unit/pairwisegenerate/treetables/type/TypeFieldMappingContainer';
import styles from'./PairwiseTypeFieldMappingTemplate.scss';
import classNames from 'classnames/bind';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as pairwiseTypesActions from 'store/modules/pairwiseTypes';

const cx = classNames.bind(styles);



class PairwiseTypeFieldMappingTemplate extends Component {
  constructor(props) {
    super(props);

    this.state = {
      title: "테스트 실행 이력", /*테이블 명*/
      totalCnt: 0, /*총 데이터 수(row개수)*/
      isTotalCnt: 1, /*totalCnt를 조회 할지 말지*/
      testcaseListInfo: [], /*테이블로 전달할 배열*/
      pageCnt: 10, /*한 번에 몇개 데이터 보여줄건지*/
      pageNum: 1,  /*현재페이지 번호*/
      //executionUuid: this.props.executionUuid,
      from: 1, /*row의 시작번호, pageNum이나 pageCnt의 값이 변하면 재 계산*/
      to: 10,
      //rightTopSuccessCnt: 0,
      //rightTopFailCnt: 0,
      //fileName: "",
      //methodName: "",
      //generatedCount: 0,
      selected: []
      };

    this.localGoPage = this.localGoPage.bind(this); /*에이전트 테이블 한 페이지에 몇 개 보여줄 것인지 pageCnt 변경*/
    this.localOnChangeSelectValue = this.localOnChangeSelectValue.bind(this); /*페이지 이동*/

  }

  /* 정재웅 코드 부분 */
  initialize = async () => {
    const { pairwiseTypesActions } = this.props;
    try {
      //await RandomRulesActions.getRulesJSON(HARD_CODED_VALUE.TEMP_TESTCASE_NO);
      await pairwiseTypesActions.getTypesJSON(this.props.typeNo);

    } catch (e) {
      console.log(e);
    }
  }

  /* 정재웅 코드 부분 */
  componentDidMount(props) {
    this.initialize();

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
    console.log("파일이름",this.props.fileName);
    console.log("메소드이름",this.props.methodName);

    const{loading, typesData} = this.props;
    if(loading || loading==undefined){
      console.log("no loading...");
      return null;
    }


    return(
      <div className = 'random-register-result-template'>
        <div>
          <PairwiseTypeFieldMappingInformationTemplate {...this.state}
          fileName={this.props.fileName}
          methodName={this.props.methodName}
          level={this.props.ruleLevel}
          ruleName={this.props.ruleName}
          generatedCount={this.state.testcaseListInfo.generatedCount}
          />
        </div>
        <br></br>
        {this.state.testcaseListInfo != null && <TypeFieldMappingContainer {...this.state}
                        ruleLevel={this.props.ruleLevel}
                        testResultDetail = {this.props.testResultDetail}
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

/* 정재웅 코드 부분 */
/////////////////////임시 결과상세 JSON 로더/////////////////////////
export default connect(
(state) => ({
  typesData: state.pairwiseTypes.get('typesData'),
  loading: state.pender.pending['pairwiseTypes/GET_TYPES_DATA']
}),
(dispatch) => ({
  pairwiseTypesActions: bindActionCreators(pairwiseTypesActions, dispatch)
})
)(PairwiseTypeFieldMappingTemplate);
//export default withRouter(RandomRuleFieldMappingTemplate);

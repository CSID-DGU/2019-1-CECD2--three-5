/*
 * 작성자: 남예진
 * 설명: 서비스 실행의 탭 구조 - 탭 안의 각각의 컴포넌트들을 불러온다.
*/
import React, {Component} from 'react';
import CloseableTabs from 'react-closeable-tabs';
import Util from 'lib/Util'; //API 호출

import PairwiseTypeRegisterToolBar from 'components/main/unit/pairwisegenerate/type/PairwiseTypeRegisterToolBar';
import PairwiseTypeFieldMappingToolBar from 'components/main/unit/pairwisegenerate/type/PairwiseTypeFieldMappingToolBar';
import PairwiseTypeHeader from 'components/main/unit/pairwisegenerate/type/PairwiseTypeHeader';
import PairwiseTypeRegisterTemplate from 'components/main/unit/pairwisegenerate/type/PairwiseTypeRegisterTemplate';
import PairwiseTypeFieldMappingTemplate from 'components/main/unit/pairwisegenerate/type/PairwiseTypeFieldMappingTemplate';
import PairwiseTypeParameterEditTemplate from 'components/main/unit/pairwisegenerate/type/PairwiseTypeParameterEditTemplate';
import styles from './PairwiseTypeTabs.scss';


import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from '../../../../../store/modules/edit';
import * as HARD_CODED_VALUE from '../../../../../lib/Util';

class PairwiseTypeTabs extends Component {

  /* 1. 초기 화면이 서비스 실행 이력탭이 열린 상태이기 위해 state에 실행이력 템플릿을 넣어준다. */
  constructor(props) {
    super(props);

    this.testResults = this.testResults.bind();
    this.testResultDetail = this.testResultDetail.bind();

    this.state = {
      data: [
        {
          tab: '조합 유형 목록 등록',
          component: <div>
          <div className = "pairwise-type-register-table">
          <PairwiseTypeRegisterTemplate {...this.state}
                                testResults = {this.testResults}
                                remoteDeleteRow = {this.remoteDeleteRow}
                                localSelectRow = {this.localSelectRow}
                                localChangeRowType = {this.localChangeRowType}
                                localChangeIsUse = {this.localChangeIsUse}
                                checkVaildation = {this.checkVaildation}
                                localSelectAllRows = {this.localSelectAllRows}
                                type={this.props.type} nodeId={this.props.nodeId} projectNo={this.props.projectNo}/>

          </div>
      </div>,
          id: 0
        }
      ]
    }
  }

  /* 3. 테스트케이스별 결과 탭 */
  testResults = (row) => {
    const {executionUuid,ruleLevel,fileName, methodName,ruleName, ruleNo} = row;

    //const id = new Date().valueOf();
    const id = this.props.nodeId;
    console.log("화면 넘어오고 ㅡㅡㅡㅡ",ruleLevel);
    this.setState({
      data: this.state.data.concat({
        tab: '입력 값 구조',
        component: <div>
        <div className = "pairwise-type-field-mapping-table">
          <PairwiseTypeFieldMappingTemplate {...this.state}
                              executionUuid = {executionUuid}
                              ruleLevel = {ruleLevel}
                              fileName = {fileName}
                              methodName={methodName}
                              ruleName={ruleName}
                              ruleNo={ruleNo}
                              testResultDetail = {this.testResultDetail}
                              remoteDeleteRow = {this.remoteDeleteRow}
                              localSelectRow = {this.localSelectRow}
                              localChangeRowType = {this.localChangeRowType}
                              localChangeIsUse = {this.localChangeIsUse}
                              checkVaildation = {this.checkVaildation}
                              localSelectAllRows = {this.localSelectAllRows}/>
        </div>
    </div>,
        id: id,
        closeable: true
      }),
      activeIndex: this.state.data.length
    });
    console.log(this.state.data);
  }

  /* 4. 테스트케이스 결과 상세 탭 */
  testResultDetail = (value) => {
    const id = new Date().valueOf();
    console.log("tabs value", value);
    this.setState({
      data: this.state.data.concat({
        tab: '조합 값 편집',
        component: <div>
        <div className = "pairwise-type-parameter-edit-table">
          <PairwiseTypeParameterEditTemplate {...this.state}
                              value = {value}
                              remoteDeleteRow = {this.remoteDeleteRow}
                              localSelectRow = {this.localSelectRow}
                              localChangeRowType = {this.localChangeRowType}
                              localChangeIsUse = {this.localChangeIsUse}
                              checkVaildation = {this.checkVaildation}
                              localSelectAllRows = {this.localSelectAllRows}/>
        </div>
    </div>,
        id: id,
        closeable: true
      }),
      activeIndex: this.state.data.length
    });
    console.log(this.state.data);
  }

    render() {
      /* 탭 구조 보여주기 - closeabletabs 라는 라이브러리 이용 */
      return (
        <div>
            <CloseableTabs
              className = "closeable-tabs"
              tabPanelColor='white'
              data={this.state.data}
              onCloseTab={(id, newIndex) => {
                  this.setState({
                  data: this.state.data.filter(item => item.id !== id),
                  activeIndex: newIndex
                  });
              }}
              activeIndex={this.state.activeIndex}
            />
        </div>
      )
    }
  }

export default PairwiseTypeTabs;

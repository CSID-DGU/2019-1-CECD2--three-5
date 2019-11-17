/*
 * 작성자: 남예진
 * 설명: 서비스 실행의 탭 구조 - 탭 안의 각각의 컴포넌트들을 불러온다.
*/
import React, {Component} from 'react';
import CloseableTabs from 'react-closeable-tabs';
import Util from 'lib/Util'; //API 호출

import RandomRuleRegisterToolBar from 'components/main/unit/randomgenerate/rule/RandomRuleRegisterToolBar';
import RandomRuleFieldMappingToolBar from 'components/main/unit/randomgenerate/rule/RandomRuleFieldMappingToolBar';
import RandomRuleHeader from 'components/main/unit/randomgenerate/rule/RandomRuleHeader';
import RandomRuleRegisterTemplate from 'components/main/unit/randomgenerate/rule/RandomRuleRegisterTemplate';
import RandomRuleFieldMappingTemplate from 'components/main/unit/randomgenerate/rule/RandomRuleFieldMappingTemplate';
import RandomRuleParameterEditTemplate from 'components/main/unit/randomgenerate/rule/RandomRuleParameterEditTemplate';
import styles from './RandomRuleTabs.scss';


import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from '../../../../../store/modules/edit';
import * as HARD_CODED_VALUE from '../../../../../lib/Util';

class RandomRuleTabs extends Component {

  /* 1. 초기 화면이 서비스 실행 이력탭이 열린 상태이기 위해 state에 실행이력 템플릿을 넣어준다. */
  constructor(props) {
    super(props);

    this.testResults = this.testResults.bind();
    this.testResultDetail = this.testResultDetail.bind();

    this.state = {
      data: [
        {
          tab: '랜덤 규칙 등록',
          component: <div>
          <div className = "random-rule-register-table">
            <RandomRuleRegisterTemplate {...this.state}
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
        tab: '랜덤 규칙 필드 매핑',
        component: <div>
        <div className = "random-rule-field-mapping-table">
          <RandomRuleFieldMappingTemplate {...this.state}
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
  testResultDetail = (fieldTypeNo) => {
    const id = new Date().valueOf();
    console.log("tabs fieldTypeNo", fieldTypeNo);
    this.setState({
      data: this.state.data.concat({
        tab: '파라미터 값 편집',
        component: <div>
        <div className = "random-rule-parameter-edit-table">
          <RandomRuleParameterEditTemplate {...this.state}
                              fieldTypeNo = {fieldTypeNo}
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

export default RandomRuleTabs;

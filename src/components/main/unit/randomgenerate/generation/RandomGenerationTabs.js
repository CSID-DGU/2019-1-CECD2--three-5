/*
 * 작성자: 김지운, 원예인
 * 설명: 랜덤 생성 이력의 탭 구조 - 탭 안의 각각의 컴포넌트들을 불러온다.
*/
import React, {Component} from 'react';
import CloseableTabs from 'react-closeable-tabs';
import Util from 'lib/Util'; //API 호출

import RandomGenerationToolBar from 'components/main/unit/randomgenerate/generation/RandomGenerationToolBar';
import RandomGenerationResultsToolBar from 'components/main/unit/randomgenerate/generation/RandomGenerationResultsToolBar';
import RandomGenerationHeader from 'components/main/unit/randomgenerate/generation/RandomGenerationHeader';
import RandomGenerationHistoryTemplate from 'components/main/unit/randomgenerate/generation/RandomGenerationHistoryTemplate';
import RandomGenerationProgress from 'components/main/unit/randomgenerate/generation/RandomGenerationProgress';
import RandomGenerationProgressFooter from 'components/main/unit/randomgenerate/generation/RandomGenerationProgressFooter';
import RandomGenerationResultsTemplate from 'components/main/unit/randomgenerate/generation/RandomGenerationResultsTemplate';
import styles from './RandomGenerationTabs.scss';


import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from '../../../../../store/modules/edit';
import * as HARD_CODED_VALUE from '../../../../../lib/Util';
import GenerationDetailEditTemplate from 'components/main/unit/randomgenerate/treetables/generation/GenerationDetailEditTemplate';

class RandomGenerationTabs extends Component {

  /* 1. 초기 화면이 랜덤 생성 이력탭이 열린 상태이기 위해 state에 랜덤생성이력 템플릿을 넣어준다. */
  constructor(props) {
    super(props);

    this.testRun = this.testRun.bind();
    this.testResults = this.testResults.bind();
    this.testResultDetail = this.testResultDetail.bind();

    const {type,nodeId, projectNo} = this.props;

    this.state = {
      data: [
        {
          tab: '랜덤 생성 이력',
          component: <div>
          <div className = "random-generation-history-table">
            <RandomGenerationHistoryTemplate {...this.state}
                                type={type}
                                nodeId={nodeId}
                                projectNo={projectNo}
                                testRun = {this.testRun}
                                testResults = {this.testResults}
                                remoteDeleteRow = {this.remoteDeleteRow}
                                localSelectRow = {this.localSelectRow}
                                localChangeRowType = {this.localChangeRowType}
                                localChangeIsUse = {this.localChangeIsUse}
                                checkVaildation = {this.checkVaildation}
                                localSelectAllRows = {this.localSelectAllRows}/>
          </div>
      </div>,
          id: 0
        }
      ]
    }
  }

  /* 2. 랜덤 생성 실행 탭 */
  testRun = (projectNo, nodeId, ruleNo, generatedCount) => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
      data: this.state.data.concat({
        tab: '진행 창',
        component: <div>
          <RandomGenerationProgress projectNo={projectNo}  nodeId={nodeId}  ruleNo={ruleNo}  generatedCount={generatedCount} />
          <RandomGenerationProgressFooter/>
        </div>,
        id: id,
        closeable: true
      }),
      activeIndex: this.state.data.length
    });
    console.log(this.state.data);
  }

  /* 3. 테스트케이스 목록 반영 탭 */
  testResults = (row) => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
      data: this.state.data.concat({
        tab: '테스트케이스 목록 반영',
        component: <div>
        <div className = "random-generation-result-table">
          <RandomGenerationResultsTemplate {...this.state}
                              {...row}
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

  /* 4. 테스트케이스 상세편집 탭 */
  testResultDetail = (row) => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
      data: this.state.data.concat({
        tab: '테스트케이스 상세 편집',
        component: <GenerationDetailEditTemplate
                        {...this.state}
                        {...row}
        />,
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

  /* 정재웅 코드 부분 */
  /*
  /////////////////////임시 결과상세 JSON 로더/////////////////////////
  export default connect(
    (state) => ({
      testCaseData: state.edit.get('testCaseData'),
      loading: state.pender.pending['edit/GET_TC_DATA']
    }),
    (dispatch) => ({
      EditActions: bindActionCreators(editActions, dispatch)
    })
  )(RandomGenerationTabs);
*/
export default RandomGenerationTabs;

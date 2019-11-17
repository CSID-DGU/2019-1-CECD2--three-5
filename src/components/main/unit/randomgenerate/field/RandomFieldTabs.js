/*
 * 작성자: 김지운, 원예인
 * 설명: 필드유형등록의 탭 구조 - 탭 안의 각각의 컴포넌트들을 불러온다.
*/
import React, {Component} from 'react';
import CloseableTabs from 'react-closeable-tabs';
import Util from 'lib/Util'; //API 호출

import RandomFieldMappingTemplate from 'components/main/unit/randomgenerate/field/RandomFieldMappingTemplate';
import styles from './RandomFieldTabs.scss';


import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from '../../../../../store/modules/edit';
import * as HARD_CODED_VALUE from '../../../../../lib/Util';
import EditUnitTCTabList from '../../edit/main/body/EditUnitTCTabContainer';

class RandomAlgorithmTabs extends Component {

  /* 정재웅 코드 부분 */
  initialize = async () => {
    const { EditActions } = this.props;
    try {
      await EditActions.getTestCaseJSON(HARD_CODED_VALUE.TEMP_TESTCASE_NO);
    } catch (e) {
      console.log(e);
    }
  }
  
  /* 정재웅 코드 부분 */
  componentDidMount(props) {
    this.initialize();

  }

  /* 1. 초기 화면이 서비스 실행 이력탭이 열린 상태이기 위해 state에 실행이력 템플릿을 넣어준다. */
  constructor(props) {
    super(props);

    //this.testResultDetail = this.testResultDetail.bind();
    //this.testResults = this.testResults.bind();
    //this.testRun = this.testRun.bind();
    
    this.state = {
      data: [
        {
          tab: '랜덤 필드 유형 등록',
          component: <div>
          <div className = "random-field-mapping-table">
            <RandomFieldMappingTemplate {...this.state}
                                //testRun = {this.testRun}
                                //testResults = {this.testResults}
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

  /* 3. 테스트케이스별 결과 탭 */
 /* testResults = (executionUuid) => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
      data: this.state.data.concat({
        tab: '랜덤 API 기본등록',
        component: <div>
        <div className = "service-run-table">
          <RandomAlgorithmBasicInfoTemplate {...this.state}
                              executionUuid = {executionUuid}
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
  }*/

  /* 4. 테스트케이스 결과 상세 탭 */
  /*testResultDetail = () => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
      data: this.state.data.concat({
        tab: '테스트케이스 상세 편집',
        component: <EditUnitTCTabList/>,
        id: id,
        closeable: true
      }),
      activeIndex: this.state.data.length
    });
    console.log(this.state.data);
  }
*/
    render() {
      /* 정재웅 코드 부분 */
      const{loading, testCaseData} = this.props;
      if(loading || loading==undefined) {
        // console.log("its loading");
        return null;
      }

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
  /////////////////////임시 결과상세 JSON 로더/////////////////////////
  export default connect(
    (state) => ({
      testCaseData: state.edit.get('testCaseData'),
      loading: state.pender.pending['edit/GET_TC_DATA']
    }),
    (dispatch) => ({
      EditActions: bindActionCreators(editActions, dispatch)
    })
  )(RandomAlgorithmTabs);
  // export default ServiceRunTabs;

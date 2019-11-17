/*
 * 작성자: 남예진
 * 설명: 서비스 실행의 탭 구조 - 탭 안의 각각의 컴포넌트들을 불러온다.
*/
import React, {Component} from 'react';
import CloseableTabs from 'react-closeable-tabs';
import Util from 'lib/Util'; //API 호출

import RandomAlgorithmRegisterTemplate from 'components/main/unit/randomgenerate/algorithm/RandomAlgorithmRegisterTemplate';
import RandomAlgorithmParameterRegisterTemplate from 'components/main/unit/randomgenerate/algorithm/RandomAlgorithmParameterRegisterTemplate';
import styles from './RandomAlgorithmTabs.scss';


import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from '../../../../../store/modules/edit';
import * as HARD_CODED_VALUE from '../../../../../lib/Util';

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

    this.parameterRegister = this.parameterRegister.bind();
    this.state = {
      data: [
        {
          tab: '알고리즘 등록',
          component: <div>
          <div className = "random-algorithm-register-table">
            <RandomAlgorithmRegisterTemplate {...this.state}
                                parameterRegister = {this.parameterRegister}
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
  parameterRegister = (algorithmTypeName, algorithmReturnType, algorithmNo) => {
    const id = new Date().valueOf();
    console.log("algorithmNo", algorithmNo);
    this.setState({
      data: this.state.data.concat({
        tab: '파라미터 등록',
        component: <div>
        <div className = "random-algorithm-parameter-register-table">
          <RandomAlgorithmParameterRegisterTemplate {...this.state}
                              algorithmTypeName = {algorithmTypeName}
                              algorithmReturnType ={algorithmReturnType}
                              algorithmNo={algorithmNo}
                              parameterRegisterDetail = {this.parameterRegisterDetail}
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

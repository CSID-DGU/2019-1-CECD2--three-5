/**
 * 작성자: 김지운, 원예인
 * 설명: 테스트케이스 상세 편집 템플릿
 */
import React, { Component, Fragment } from 'react';

import GenerationDetailEditHeader from './GenerationDetailEditHeader';
import GenerationDetailEditContainer from './GenerationDetailEditContainer';
import Col from 'react-bootstrap/Col';

 import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from 'store/modules/edit';
import * as HARD_CODED_VALUE from 'lib/Util';


class GenerationDetailEditTemplate extends Component {
     initialize = async () => {
        const { EditActions } = this.props;
        try {
          await EditActions.getTestCaseJSON(HARD_CODED_VALUE.TEMP_TESTCASE_NO);
        } catch (e) {
          console.log(e);
        }
      }



      componentDidMount(props) {
        this.initialize();

      }
     render() {

        const{loading, testCaseData, colsize} = this.props;
        if(loading || loading==undefined) {
          // console.log("its loading");
          return null;
        }

        return (
            <Col xl={colsize} >
            <GenerationDetailEditHeader json={testCaseData.master}/>
            <GenerationDetailEditContainer/>
            </Col>
        );
      }
}

 export default connect(
    (state) => ({
      testCaseData: state.edit.get('testCaseData'),
      loading: state.pender.pending['edit/GET_TC_DATA']
    }),
    (dispatch) => ({
      EditActions: bindActionCreators(editActions, dispatch)
    })
  )(GenerationDetailEditTemplate);

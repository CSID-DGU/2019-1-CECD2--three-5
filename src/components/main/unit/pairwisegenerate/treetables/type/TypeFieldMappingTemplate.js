import React, { Component, Fragment } from 'react';

import Header from './TypeFieldMappingHeader';
import Content from './TypeFieldMappingContainer';
import Col from 'react-bootstrap/Col';

 import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from 'store/modules/edit';
import * as HARD_CODED_VALUE from 'lib/Util';


class TypeFieldMappingTemplate extends Component {
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
            <Header json={testCaseData.master}/>
            <Content/>
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
  )(TypeFieldMappingTemplate);

// export default EditUnitTCTemplate;
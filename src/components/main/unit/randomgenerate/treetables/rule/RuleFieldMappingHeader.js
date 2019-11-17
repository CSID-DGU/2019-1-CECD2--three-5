import React, { Component } from 'react';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import ButtonGroup from 'react-bootstrap/ButtonGroup';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as unitActions from 'store/modules/unit';

class RuleFieldMappingHeader extends Component {
  
    ///////////////임시 모달 trigger (새 테스트케이스 추가 모달)/////////////////
    handleShowTCGroupFinder = (e) => {
      const { UnitActions } = this.props;
      const value = true;
      const name = 'showTCGroupFinder';
      UnitActions.updateState({ name, value });
      e.preventDefault();
  
    }
    //////////////////////////////////////////////////////////////////////// 
  
  
  render() {
    const {json} = this.props;
    // console.log("json");
    // console.log(json);
    // const json = {
    //   testcaseName: "value",
    //   testcaseNo: "value",
    //   testcaseGroupName: "val",
    //   testcaseGroupNo: "val"
    // }


    return (

      <Form className="mt-5">
        <Form.Label className="display-4">테스트케이스 편집</Form.Label>
        <Form.Group as={Row} controlId="formHorizontalEmail">

          <Form.Label column xl={2}>
            테스트케이스 명
            </Form.Label>
          <Col xl={2}>
            <Form.Control placeholder=".." value={json.testcaseName.value} readOnly/>
          </Col>
          <Form.Label column xl={2}>
            테스트케이스 번호
            </Form.Label>
          <Col xl={2}>
            <Form.Control placeholder=".." value={json.testcaseNo.value} readOnly/>
          </Col>
        </Form.Group>

        <Form.Group as={Row} className="mb-4" controlId="formHorizontalEmail">
          <Form.Label column xl={2}>
            테스트케이스 그룹명
            </Form.Label>
          <Col xl={2}>
            <Form.Control placeholder=".." value={json.testcaseGroupName.value} readOnly/>
          </Col>
          <Form.Label column xl={2}>
            테스트케이스 그룹 번호
            </Form.Label>
          <Col xl={2} className="">
            <Form.Control placeholder=".." value={json.testcaseGroupNo.value} readOnly/>
          </Col>
          <Col xl={4}>
            <Button  onClick={this.handleShowTCGroupFinder} className="mr-2" variant="primary" type="submit">
              부모찾기
            </Button>
            <ButtonGroup aria-label="Basic example">
              <Button variant="secondary">저장</Button>
              <Button variant="secondary">삭제</Button>
            </ButtonGroup>
          </Col>

        </Form.Group>



      </Form>
    );
  }
}
    ///////////////임시 모달 trigger (새 테스트케이스 추가 모달)/////////////////

export default connect(
  (state) => ({
    }),
  (dispatch) => ({
     UnitActions: bindActionCreators(unitActions, dispatch)

  })
)(RuleFieldMappingHeader);
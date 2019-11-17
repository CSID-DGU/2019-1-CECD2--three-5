/**
 * 작성자: 김지운, 원예인
 * 설명: 테스트케이스 상세 편집 컨테이너
 */
import React, { Component, Fragment }  from 'react';
import GenerationDetailEditTab from './GenerationDetailEditTab';
import Col from 'react-bootstrap/Col';
 
class GenerationDetailEditContainer extends Component {
 

  render() {
 

  
    return (

      <Col className="px-0">
      <GenerationDetailEditTab text="입력값" tabColor="tab tab-1-color"/>
      </Col>
 
    );
  }
}
 

export default GenerationDetailEditContainer;
import React, { Component, Fragment }  from 'react';
import TypeFieldMappingTab from './TypeFieldMappingTab';
import Col from 'react-bootstrap/Col';
 
class TypeFieldMappingContainer extends Component {
  
  constructor(props){
    super(props);
    this.state={
    };
  }
  
  render() {
    return (

      <Col className="px-0">
      <TypeFieldMappingTab text="입력값" tabColor="tab tab-1-color" {...this.state}
        ruleLevel={this.props.ruleLevel}
        testResultDetail = {this.props.testResultDetail}
      />
      </Col>
 
    );
  }
}
 

export default TypeFieldMappingContainer;
import React, { Component, Fragment }  from 'react';
import RuleFieldMappingTab from './RuleFieldMappingTab';
import Col from 'react-bootstrap/Col';
 
class RuleFieldMappingContainer extends Component {
  
  constructor(props){
    super(props);
    this.state={
    };
  }
  
  render() {
    return (

      <Col className="px-0">
      <RuleFieldMappingTab text="입력값" tabColor="tab tab-1-color" {...this.state}
        ruleLevel={this.props.ruleLevel}
        testResultDetail = {this.props.testResultDetail}
      />
      </Col>
 
    );
  }
}
 

export default RuleFieldMappingContainer;
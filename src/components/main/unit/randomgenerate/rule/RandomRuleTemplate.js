/*
 * 작성자: 남예진
 * 설명: 서비스 실행의 template 컴포넌트 - App.js에서 그대로 불러옴
*/
import React, { Component, Fragment } from 'react';

import update from 'react-addons-update'; /*배열을 처리하기 위한 라이브러리*/
import { withRouter, Route } from 'react-router-dom';

import RandomRuleSideMenu from 'components/main/unit/randomgenerate/rule/RandomRuleSideMenu';
import RandomRuleHeader from 'components/main/unit/randomgenerate/rule/RandomRuleHeader';
import RandomRuleRegisterTemplate from 'components/main/unit/randomgenerate/rule/RandomRuleRegisterTemplate';
import RandomRuleTabs from 'components/main/unit/randomgenerate/rule/RandomRuleTabs';
import ProjectTreeTemplate from '../../../projecttree/ProjectTreeTemplate';

import styles from './RandomRuleTemplate.scss';
import classNames from 'classnames/bind';

import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

import queryString from 'query-string';

class RandomRuleTemplate extends Component {

  constructor(props) {
    super(props);

    this.state = {
      show: true
    }
  }

  render() {
    const params = queryString.parse(this.props.location.search);
    console.log("params==>");
    console.log(params);
    const type = params != null ? params.type : 0;
    console.log("Called::RandomRuleTemplate type=" + type);
    const nodeId = params != null ? params.id : 0;
    console.log("Called::RandomRuleTemplate nodeId=" + nodeId);
    const projectNo = params != null ? params.projectNo : 0;
    console.log("Called::RandomRuleTemplate projectNo=" + projectNo);
    return (
      <Fragment>
        <div className="header">
          <RandomRuleHeader {...this.state}/>
        </div>
        <div className = "tabs">
          <RandomRuleTabs type={type} nodeId={nodeId} projectNo={projectNo}/>
        </div>
      </Fragment>
    )
  }
}

export default RandomRuleTemplate;

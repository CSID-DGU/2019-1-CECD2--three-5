/*
 * 작성자: 김지운, 원예인
 * 설명: 랜덤 생성 관리 template
*/
import React, { Component, Fragment } from 'react';

import update from 'react-addons-update'; /*배열을 처리하기 위한 라이브러리*/
import { withRouter, Route } from 'react-router-dom';

import RandomGenerationSideMenu from 'components/main/unit/randomgenerate/generation/RandomGenerationSideMenu';
import RandomGenerationHeader from 'components/main/unit/randomgenerate/generation/RandomGenerationHeader';
import RandomGenerationHistoryTemplate from 'components/main/unit/randomgenerate/generation/RandomGenerationHistoryTemplate';
import RandomGenerationTabs from 'components/main/unit/randomgenerate/generation/RandomGenerationTabs';
import ProjectTreeTemplate from '../../../projecttree/ProjectTreeTemplate';

import styles from './RandomGenerationTemplate.scss';
import classNames from 'classnames/bind';

import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

import queryString from 'query-string';

class RandomGenerationTemplate extends Component {

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
    console.log("Called::RandomGenerationTemplate type=" + type);
    const nodeId = params != null ? params.id : 0;
    console.log("Called::RandomGenerationTemplate nodeId=" + nodeId);
    const projectNo = params != null ? params.projectNo : 0;
    console.log("Called::RandomGenerationTemplate projectNo=" + projectNo);
    return (
      <Fragment>
        <div className="header">
          <RandomGenerationHeader {...this.state}/>
        </div>
        <div className = "tabs">
          <RandomGenerationTabs type={type} nodeId={nodeId} projectNo={projectNo}/>
        </div>
      </Fragment>
    )
  }
}

export default RandomGenerationTemplate;

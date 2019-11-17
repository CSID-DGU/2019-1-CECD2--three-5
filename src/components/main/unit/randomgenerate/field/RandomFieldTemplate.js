/*
 * 작성자: 김지운, 원예인
 * 설명: 필드유형등록의 template 컴포넌트 - App.js에서 그대로 불러옴
*/
import React, { Component, Fragment } from 'react';

import update from 'react-addons-update'; /*배열을 처리하기 위한 라이브러리*/
import { withRouter, Route } from 'react-router-dom';

import RandomFieldHeader from 'components/main/unit/randomgenerate/field/RandomFieldHeader';
import RandomFieldTabs from 'components/main/unit/randomgenerate/field/RandomFieldTabs';
import ProjectTreeTemplate from '../../../projecttree/ProjectTreeTemplate';

import styles from './RandomFieldTemplate.scss';
import classNames from 'classnames/bind';

import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

import queryString from 'query-string';

class RandomFieldTemplate extends Component {

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
    console.log("Called::RandomFieldTemplate type=" + type);
    const nodeId = params != null ? params.id : 0;
    console.log("Called::RandomFieldTemplate nodeId=" + nodeId);
    const projectNo = params != null ? params.projectNo : 0;
    console.log("Called::RandomFieldTemplate projectNo=" + projectNo);
    return (
      <Fragment>
        <div className="header">
          <RandomFieldHeader {...this.state}/>
        </div>
        <div className = "tabs">
          <RandomFieldTabs type={type} nodeId={nodeId} projectNo={projectNo}/>
        </div>
      </Fragment>
    )
  }
}

export default RandomFieldTemplate;

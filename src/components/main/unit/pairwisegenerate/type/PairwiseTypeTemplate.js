/*
 * 작성자: 남예진
 * 설명: 서비스 실행의 template 컴포넌트 - App.js에서 그대로 불러옴
*/
import React, { Component, Fragment } from 'react';

import update from 'react-addons-update'; /*배열을 처리하기 위한 라이브러리*/
import { withRouter, Route } from 'react-router-dom';

import PairwiseTypeSideMenu from 'components/main/unit/pairwisegenerate/type/PairwiseTypeSideMenu';
import PairwiseTypeHeader from 'components/main/unit/pairwisegenerate/type/PairwiseTypeHeader';
import PairwiseTypeRegisterTemplate from 'components/main/unit/pairwisegenerate/type/PairwiseTypeRegisterTemplate';
import PairwiseTypeTabs from 'components/main/unit/pairwisegenerate/type/PairwiseTypeTabs';
import ProjectTreeTemplate from '../../../projecttree/ProjectTreeTemplate';

import styles from './PairwiseTypeTemplate.scss';
import classNames from 'classnames/bind';

import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Container from 'react-bootstrap/Container';

import queryString from 'query-string';

class PairwiseTypeTemplate extends Component {

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
    console.log("Called::PairwiseTypeTemplate type=" + type);
    const nodeId = params != null ? params.id : 0;
    console.log("Called::PairwiseTypeTemplate nodeId=" + nodeId);
    const projectNo = params != null ? params.projectNo : 0;
    console.log("Called::PairwiseTypeTemplate projectNo=" + projectNo);
    return (
      <Fragment>
        <div className="header">
          <PairwiseTypeHeader {...this.state}/>
        </div>
        <div className = "tabs">
          <PairwiseTypeTabs type={type} nodeId={nodeId} projectNo={projectNo}/>
        </div>
      </Fragment>
    )
  }
}

export default PairwiseTypeTemplate;

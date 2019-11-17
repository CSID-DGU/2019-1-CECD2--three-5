/**
 * 작성자: 김지운, 원예인
 * 설명: 화면 조정을 위한 임시 프로젝트 사이드바 - 지금은 사용하고 있지 않음
**/
import React, {Component} from 'react';
import whaxios from 'lib/whaxios';
import Col from 'react-bootstrap/Col';

import { Route, Link } from 'react-router-dom';

import './RandomFieldSideMenu.scss';

class RandomFieldSideMenu extends Component {
  constructor(props,context){
    super(props, context);

  }

  render() {
    return (
      <Col md={2} className="project-side-menu">
            <div className="menu-item">
                <span>
                    <i className="font-Awesome fa fa-folder-open-o"></i>
                    <Link className="link" to="/MainPage/Project">project tree goes here</Link>
                </span>
            </div>
      </Col>
    )
  }
}

export default RandomFieldSideMenu;

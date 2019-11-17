/**
 * 작성자: 김지운, 원예인
 * 설명: 필드 유형 등록 header 제목 --> template에서 불러온다.
**/
import React, {Component} from 'react';
import Button from 'react-bootstrap/Button';

import './RandomFieldHeader.scss';

class RandomFieldHeader extends Component {
  constructor(props,context){
    super(props, context);

  }

  render() {
    return (
      <div className="random-field-header">
        <div className="first-title">
          <span>단위 테스트 > 생성기 > 랜덤 생성 > 필드 유형 등록{this.props.title}</span>
        </div>
        <div className = "page-title">
          <span>
            필드 유형 등록
          </span>
        </div>
      </div>
    )
  }
}

export default RandomFieldHeader;

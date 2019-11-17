/**
 * 작성자: 김지운, 원예인
 * 설명: 랜덤생성관리 header 제목 --> template에서 불러온다.
**/
import React, {Component} from 'react';
import Button from 'react-bootstrap/Button';

import './RandomGenerationHeader.scss';

class RandomGenerationHeader extends Component {
  constructor(props,context){
    super(props, context);

  }

  render() {
    return (
      <div className="random-generation-header">
        <div className="first-title">
          <span>단위 테스트 > 생성기 > 랜덤 생성 > 랜덤 생성 관리{this.props.title}</span>
        </div>
        <div className = "page-title">
          <span>
            랜덤 생성 관리
          </span>
        </div>
      </div>
    )
  }
}

export default RandomGenerationHeader;

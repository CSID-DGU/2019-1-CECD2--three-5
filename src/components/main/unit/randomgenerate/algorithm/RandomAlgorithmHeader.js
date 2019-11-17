/**
 * 작성자 남예진
 * 설명: 서비스 실행 header 제목 --> template에서 불러온다.
**/
import React, {Component} from 'react';
import Button from 'react-bootstrap/Button';

import './RandomAlgorithmHeader.scss';

class RandomAlgorithmHeader extends Component {
  constructor(props,context){
    super(props, context);

  }

  render() {
    return (
      <div className="random-algorithm-header">
        <div className="first-title">
          <span>단위 테스트 > 생성기 > 랜덤 생성 > 랜덤 알고리즘 등록{this.props.title}</span>
        </div>
        <div className = "page-title">
          <span>
            랜덤 알고리즘 등록
          </span>
        </div>
      </div>
    )
  }
}

export default RandomAlgorithmHeader;

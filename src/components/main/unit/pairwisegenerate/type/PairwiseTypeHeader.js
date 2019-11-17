/**
 * 작성자 남예진
 * 설명: 서비스 실행 header 제목 --> template에서 불러온다.
**/
import React, {Component} from 'react';
import Button from 'react-bootstrap/Button';

import './PairwiseTypeHeader.scss';

class PairwiseTypeHeader extends Component {
  constructor(props,context){
    super(props, context);

  }

  render() {
    return (
      <div className="pairwise-type-header">
        <div className="first-title">
          <span>단위 테스트 > 생성기 > 조합 생성 > 조합 유형 관리{this.props.title}</span>
        </div>
        <div className = "page-title">
          <span>
            조합 유형 관리
          </span>
        </div>
      </div>
    )
  }
}

export default PairwiseTypeHeader;

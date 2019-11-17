/**
 * 작성자: 김지운, 원예인
 * 설명: 랜덤생성관리 header 제목 --> template에서 불러온다.
**/
import React, {Component} from 'react';

import './PairwiseGenerationHeader.scss';

class PairwiseGenerationHeader extends Component {
  constructor(props,context){
    super(props, context);

  }

  render() {
    return (
      <div className="pairwise-generation-header">
        <div className="first-title">
          <span>단위 테스트 > 생성기 > 조합 생성 > 조합 생성 관리{this.props.title}</span>
        </div>
        <div className = "page-title">
          <span>
            조합 생성 관리
          </span>
        </div>
      </div>
    )
  }
}

export default PairwiseGenerationHeader;

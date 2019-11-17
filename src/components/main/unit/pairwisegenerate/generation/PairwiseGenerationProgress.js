/**
 * 작성자: 김지운, 원예인
 * 설명: 랜덤 생성 시작 탭에 들어갈 큰 컴포넌트
**/
import React, {Component} from 'react';
import { withRouter } from 'react-router-dom';

import PairwiseGenerationProgressTemplate from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationProgressTemplate';
import styles from'./PairwiseGenerationProgress.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseGenerationProgress extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <div className = "progress-wrapper">
        <PairwiseGenerationProgressTemplate projectNo={this.props.projectNo} nodeId={this.props.nodeId}  ruleNo={this.props.ruleNo}  generatedCount={this.props.generatedCount}/>
      </div>
    )
  }
}

export default withRouter(PairwiseGenerationProgress);

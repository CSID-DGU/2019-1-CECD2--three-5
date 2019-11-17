/**
 * 작성자: 김지운, 원예인
 * 설명: 랜덤 생성 시작 탭에 들어갈 큰 컴포넌트
**/
import React, {Component} from 'react';
import { withRouter } from 'react-router-dom';

import RandomGenerationProgressTemplate from 'components/main/unit/randomgenerate/generation/RandomGenerationProgressTemplate';
import styles from'./RandomGenerationProgress.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomGenerationProgress extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    console.log("RandomGenerationProgress:: projectNo=["+this.props.projectNo+"]")
    console.log("RandomGenerationProgress:: nodeId=["+this.props.nodeId+"]")
    console.log("RandomGenerationProgress:: ruleNo=["+this.props.ruleNo+"]")
    console.log("RandomGenerationProgress:: generatedCount=["+this.props.generatedCount+"]")
    return(
      <div className = "progress-wrapper">
        <RandomGenerationProgressTemplate projectNo={this.props.projectNo} nodeId={this.props.nodeId}  ruleNo={this.props.ruleNo}  generatedCount={this.props.generatedCount}/>
      </div>
    )
  }
}

export default withRouter(RandomGenerationProgress);

/**
 * 작성자: 김지운, 원예인
 * 설명: 아직 도입 안된 부분 - 랜덤생성 시작하고 나서 테스트 계속/정지/중단 및 실행 상태 보여주는 footer
**/
import React, {Component} from 'react';
import { withRouter } from 'react-router-dom';
import ProgressBar from 'react-bootstrap/ProgressBar';

import styles from'./RandomGenerationProgressFooter.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomGenerationProgressFooter extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return(
      <div className="random-generation-progress-footer">
            <div className = "progress-bar">
                <ProgressBar animated now={100}/>
            </div>
            
            <div className = "progress-button-group">
                <button className = "continue"><i className = 'fa fa-play'/>계속</button>
                <button className = "pause"><i className = 'fa fa-pause'/>정지</button>
                <button className = "stop"><i className = 'fa fa-stop'/>중단</button>
            </div>
        </div>
    )
  }
}

export default withRouter(RandomGenerationProgressFooter);

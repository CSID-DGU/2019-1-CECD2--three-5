/*
 * 작성자: 김지운, 원예인
 * 설명: 랜덤 생성 관리 template
*/
import React, { Component, Fragment } from 'react';

import PairwiseGenerationHeader from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationHeader';
import PairwiseGenerationTabs from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationTabs';

import queryString from 'query-string';

class PairwiseGenerationTemplate extends Component {

  constructor(props) {
    super(props);

    this.state = {
      show: true
    }
  }

  render() {
    const params = queryString.parse(this.props.location.search);
    console.log(params);
    const type = params != null ? params.type : 0;
    const nodeId = params != null ? params.id : 0;
    const projectNo = params != null ? params.projectNo : 0;
    return (
      <Fragment>
        <div className="header">
          <PairwiseGenerationHeader {...this.state}/>
        </div>
        <div className = "tabs">
          <PairwiseGenerationTabs type={type} nodeId={nodeId} projectNo={projectNo}/>
        </div>
      </Fragment>
    )
  }
}

export default PairwiseGenerationTemplate;

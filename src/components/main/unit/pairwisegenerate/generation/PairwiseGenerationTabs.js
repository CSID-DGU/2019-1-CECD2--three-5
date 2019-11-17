/*
 * 작성자: 김지운, 원예인
 * 설명: 조합 생성 이력의 탭 구조 - 탭 안의 각각의 컴포넌트들을 불러온다.
*/
import React, {Component} from 'react';
import CloseableTabs from 'react-closeable-tabs';
import PairwiseGenerationHistoryTemplate from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationHistoryTemplate';
import PairwiseGenerationProgress from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationProgress';
import PairwiseGenerationProgressFooter from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationProgressFooter';
import PairwiseGenerationResultsTemplate from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationResultsTemplate';
import GenerationDetailEditTemplate from 'components/main/unit/pairwisegenerate/treetables/generation/GenerationDetailEditTemplate';

class PairwiseGenerationTabs extends Component {

  /* 1. 초기 화면이 조합 생성 이력탭이 열린 상태이기 위해 state에 랜덤생성이력 템플릿을 넣어준다. */
  constructor(props) {
    super(props);

    this.testRun = this.testRun.bind();
    this.testResults = this.testResults.bind();
    this.testResultDetail = this.testResultDetail.bind();

    const {type,nodeId, projectNo} = this.props;

    this.state = {
      data: [
        {
          tab: '조합 생성 이력',
          component: <div>
          <div className = "pairwise-generation-history-table">
            <PairwiseGenerationHistoryTemplate {...this.state}
                                type={type}
                                nodeId={nodeId}
                                projectNo={projectNo}
                                testRun = {this.testRun}
                                testResults = {this.testResults}
                                remoteDeleteRow = {this.remoteDeleteRow}
                                localSelectRow = {this.localSelectRow}
                                localChangeRowType = {this.localChangeRowType}
                                localChangeIsUse = {this.localChangeIsUse}
                                checkVaildation = {this.checkVaildation}
                                localSelectAllRows = {this.localSelectAllRows}/>
          </div>
      </div>,
          id: 0
        }
      ]
    }
  }

  /* 2. 랜덤 생성 실행 탭 */
  testRun = (projectNo, nodeId, ruleNo, generatedCount) => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
      data: this.state.data.concat({
        tab: '진행 창',
        component: <div>
          <PairwiseGenerationProgress projectNo={projectNo}  nodeId={nodeId}  ruleNo={ruleNo}  generatedCount={generatedCount} />
          <PairwiseGenerationProgressFooter/>
        </div>,
        id: id,
        closeable: true
      }),
      activeIndex: this.state.data.length
    });
    console.log(this.state.data);
  }

  /* 3. 테스트케이스 목록 반영 탭 */
  testResults = (row) => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
      data: this.state.data.concat({
        tab: '테스트케이스 목록 반영',
        component: <div>
        <div className = "pairwise-generation-result-table">
          <PairwiseGenerationResultsTemplate {...this.state}
                              {...row}
                              testResultDetail = {this.testResultDetail}
                              remoteDeleteRow = {this.remoteDeleteRow}
                              localSelectRow = {this.localSelectRow}
                              localChangeRowType = {this.localChangeRowType}
                              localChangeIsUse = {this.localChangeIsUse}
                              checkVaildation = {this.checkVaildation}
                              localSelectAllRows = {this.localSelectAllRows}/>
        </div>
    </div>,
        id: id,
        closeable: true
      }),
      activeIndex: this.state.data.length
    });
    console.log(this.state.data);
  }

  /* 4. 테스트케이스 상세편집 탭 */
  testResultDetail = (row) => {
    const id = new Date().valueOf();
    console.log(id);
    this.setState({
        data: this.state.data.concat({
        tab: '테스트케이스 상세 편집',
        component: <GenerationDetailEditTemplate
                        {...this.state}
                        {...row}
        />,
        id: id,
        closeable: true
      }),
      activeIndex: this.state.data.length
    });
    console.log(this.state.data);
  }

    render() {
      /* 탭 구조 보여주기 - closeabletabs 라는 라이브러리 이용 */
      return (
        <div>
            <CloseableTabs
              className = "closeable-tabs"
              tabPanelColor='white'
              data={this.state.data}
              onCloseTab={(id, newIndex) => {
                  this.setState({
                  data: this.state.data.filter(item => item.id !== id),
                  activeIndex: newIndex
                  });
              }}
              activeIndex={this.state.activeIndex}
            />
        </div>
      )
    }
  }
export default PairwiseGenerationTabs;

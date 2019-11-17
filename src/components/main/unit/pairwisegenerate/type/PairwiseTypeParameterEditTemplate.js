/**
 * 작성자 남예진
 * 설명: 서비스 실행 테스트 실행 이력 탭 안에 있는 화면과 function들 정리
**/
import React, {Component} from 'react';
import Util from 'lib/Util'; //API 호출
import { withRouter } from 'react-router-dom';

import PairwiseTypeParametersInformationTemplate from 'components/main/unit/pairwisegenerate/type/PairwiseTypeParametersInformationTemplate';
import PairwiseTypeParameterEditToolBar from 'components/main/unit/pairwisegenerate/type/PairwiseTypeParameterEditToolBar';
import styles from'./PairwiseTypeParameterEditTemplate.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseTypeParameterEditTemplate extends Component {
  constructor(props) {
    super(props);

    this.state = {
      title: " 생성 이력", /*테이블 명*/
      totalCnt: 0, /*총 데이터 수(row개수)*/
      isTotalCnt: 1, /*totalCnt를 조회 할지 말지*/
      randomGenFieldTypeParamList: [], /*테이블로 전달할 배열*/
      pageCnt: 10, /*한 번에 몇개 데이터 보여줄건지*/
      pageNum: 1,  /*현재페이지 번호*/
      from: 1, /*row의 시작번호, pageNum이나 pageCnt의 값이 변하면 재 계산*/
      to: 10,
      selected: [],
      value: this.props.value
      };

      this.remoteBatchSave=this.remoteBatchSave.bind(this);
  }
  remoteBatchSave(e){
    var randomGenAlgorithmHistList = this.state.randomGenAlgorithmHistList;
    randomGenAlgorithmHistList = randomGenAlgorithmHistList.filter(function(item){
      return item.rowType === "U" || item.rowType === "I";
    });

    if(randomGenAlgorithmHistList.length === 0){
      alert("저장 할 내용이 없습니다.");
    }else{
      const url = '/randomGeneration/saveRandomGenerationAlgorithmList/1';
      const data = { algorithmList: randomGenAlgorithmHistList };
      Util.postAxios(url, data, (msg, response) => {
        this.props.remoteViewTableList(this.state.pageNum, this.state.pageCnt);
      });
    }
  }
  render() {
    return(
      <div className = 'pairwise-type-parameter-edit-template'>
        <PairwiseTypeParameterEditToolBar {...this.state}
                                //testRun={this.props.testRun}
                                pageCnt={this.props.pageCnt}
                                from={this.props.from}
                                to={this.props.to}
                                totalCnt={this.props.totalCnt}
                                localOnChangeSelectValue={this.props.localOnChangeSelectValue}
                                localGoPage={this.props.localGoPage}
                                remoteBatchSave = {this.remoteBatchSave}
                                className='pairwise-type-parameter-edit-tool-bar' />
        <div className="pairwise-type-parameters-information-template" >
          <PairwiseTypeParametersInformationTemplate {...this.state}/>
        </div>
      </div>
    )
  }
}

export default withRouter(PairwiseTypeParameterEditTemplate);

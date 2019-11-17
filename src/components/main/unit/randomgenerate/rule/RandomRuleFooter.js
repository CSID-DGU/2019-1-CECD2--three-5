/**
 * 작성자 남예진
 * 설명: 서비스 실행 react-bootstrap-table들을 이용하는 테이블들의 하단 페이징 처리
**/
import React, {Component} from 'react';

import ProjectListPagenation from 'components/common/project/ProjectPagenation';

import styles from './RandomRuleFooter.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomRuleFooter extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    
    return (
      <div className = {cx('random-rule-footer')}>

            <div className = {cx('count-of-list')}>
              <select name = "countOfList"  value = {(this.props.pageCnt)} onChange={this.props.localOnChangeSelectValue}>
                 <option value = "10">10</option>
                 <option value = "20">20</option>
                 <option value = "50">50</option>
                 <option value = "100">100</option>
                 <option value = "200">200</option>
                 <option value = "500">500</option>
              </select>
            </div>

            <div className = {cx('display-range')}>
              <span>전체 {this.props.totalCnt}개 {this.props.from} - {this.props.to} 보임</span>
            </div>

          <div className = {cx('pagenation')}>
          <ProjectListPagenation total={this.props.totalCnt} 
            pageCnt={this.props.pageCnt} 
            pageNum={this.props.pageNum} 
            goPage={this.props.localGoPage}/>
          </div>

        </div>
    )
  }
}

export default RandomRuleFooter;

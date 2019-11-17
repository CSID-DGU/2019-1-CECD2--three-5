/**
 * 작성자 남예진
 * 설명: 필드 유형 매핑 테이블 위 추가, 삭제, 저장 버튼들, 그리고 상단 페이징 처리 기능
**/
import React, { Component } from 'react';
import { Modal } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';

import styles from './RandomAlgorithmParameterRegisterToolBar.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomAlgorithmParameterRegisterToolBar extends Component {

  closeChild = () => {
    this.setState({
      showChild: false
    });
  };

  constructor(props) {
    super(props);
    this.state = {
      showChild: true
    }
  }

  render() {
    return (
      <div className ={cx('random-algorithm-parameter-register-tool-bar')}>
      <div className={cx('tool-bar')}>
        <div className={cx('bottom-tool-bar')}>
          <div className="top-pagenation">
            <div className={cx('count-of-list')}>
              <select name="countOfList" value={(this.props.pageCnt)} onChange={this.props.localOnChangeSelectValue}>
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="50">50</option>
                <option value="100">100</option>
                <option value="200">200</option>
                <option value="500">500</option>
              </select>
            </div>
            <div className={cx('display-range')}>
              <span>전체 {this.props.totalCnt}개 {this.props.from} - {this.props.to} 보임</span>
            </div>
          </div>
          <div className={cx('toolbar-group')}>
            <div className={cx('button-group')}>
              <Button className={cx('batchDelete-button')} onClick={(e) => this.props.remoteBatchDelete(e)} >
                <i className={cx("fa fa-minus")} />
                <span className={cx('batchDelete-font')}>삭제</span>
              </Button>
            </div>
            <div className = {cx('button-group')}>
              <Button className = {cx('add-button')} onClick = {(e) => this.props.localAddRow(e)}>
                <i className = {cx("fa fa-plus")}/>
                <span className = {cx('add-font')}>추가</span>
                </Button>
            </div>
            <div className = {cx('button-group')}>
              <Button className = {cx('add-button')} onClick = {(e) => this.props.localAddRow(e)}>
                <span className = {cx('add-font')}>Up</span>
                </Button>
            </div>
            <div className = {cx('button-group')}>
              <Button className = {cx('add-button')} onClick = {(e) => this.props.localAddRow(e)}>
                <span className = {cx('add-font')}>Dn</span>
                </Button>
            </div>
            <div className={cx('button-group')}>
              <Button className={cx('save-button')} onClick={(e) => this.props.remoteBatchSave(e)}>
                <i className={cx("fa fa-save")} />
                <span className={cx('save-font')}>저장</span>
              </Button>
            </div>
            
          </div>

        </div>
      </div>
    </div>
    )
  }
}
export default RandomAlgorithmParameterRegisterToolBar;

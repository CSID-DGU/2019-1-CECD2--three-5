import React, {Component} from 'react';
import {Modal} from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';

import styles from './RandomRuleFieldMappingToolBar.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class RandomRuleFieldMappingToolBar extends Component {

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
      <div className={cx('random-rule-field-mapping-tool-bar')}>
          <div className = {cx('bottom-tool-bar')}>
            <div className =  "top-pagenation">
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
            </div>

              <div className = {cx('toolbar-group')}>
                  <div className = {cx('button-group')}>
                  <Button className = {cx('export-button')} onClick = {(e) => this.props.handleClickExport(e)}>
                      <i className = {cx("fa fa-share-square-o")}/>
                      <span className = {cx('export-font')}>내보내기</span>
                      </Button>
                  </div>
                  <div className = {cx('button-group')}>
                  <Button className = {cx('batchDelete-button')} onClick = {(e) => this.props.batchDelete(e)} >
                      <i className = {cx("fa fa-trash")}/>
                      <span className = {cx('batchDelete-font')}>반영</span>
                      </Button>
                  </div>
                  <div className = {cx('button-group')}>
                  <Button className = {cx('batchDelete-button')} onClick = {(e) => this.props.batchDelete(e)} >
                      <i className = {cx("fa fa-trash")}/>
                      <span className = {cx('batchDelete-font')}>삭제</span>
                      </Button>
                  </div>
            </div>

        </div>
      </div>
      
    )
  }
}
export default RandomRuleFieldMappingToolBar;

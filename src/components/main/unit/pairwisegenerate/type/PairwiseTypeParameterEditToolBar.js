/**
 * 작성자 남예진
 * 설명: 필드 유형 매핑 테이블 위 추가, 삭제, 저장 버튼들, 그리고 상단 페이징 처리 기능
**/
import React, { Component } from 'react';
import { Modal } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';

import styles from './PairwiseTypeParameterEditToolBar.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseTypeParameterEditToolBar extends Component {

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
      <div className ={cx('pairwise-type-parameter-edit-tool-bar')}>
      <div className={cx('tool-bar')}>
        <div className={cx('bottom-tool-bar')}>
          <div className={cx('toolbar-group')}>
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
export default PairwiseTypeParameterEditToolBar;

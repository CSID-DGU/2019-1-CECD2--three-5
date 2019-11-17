/**
 * 작성자: 김지운, 원예인
 * 설명: 테스트케이스 상세 편집 테이블
 */
import React, { Component, Fragment } from 'react';
import './GenerationDetailEditTable.scss';
import { TreeTable, TreeState } from 'cp-react-tree-table';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import Modal from "react-bootstrap/Modal";
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Button from 'react-bootstrap/Button';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearchPlus } from '@fortawesome/free-solid-svg-icons'

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as editActions from 'store/modules/edit';
import * as unitActions from 'store/modules/unit';

import classNames from "classnames/bind";
import styles from "./GenerationDetailEditTable.scss";

import mockData from '../dummydata/MOCK2';
const cx = classNames.bind(styles);



class GenerationDetailEditTable extends Component {

  constructor(props) {
    super(props);

    this.state={
      show: false,
      testcaseValue: this.props.testcaseValue
    }

    this.handleShow = this.handleShow.bind(this);
    this.handleClose = this.handleClose.bind(this);

    const { testCaseData, EditActions } = this.props;

      //const testCaseDetail = testCaseData.detail.map((data) => {
      const testCaseDetail = mockData.detail.map((data) => {

      return ({
        data: data.data,
        height: data.height,
        children: data.children
      })
    })

    EditActions.updateState({ testCaseDetail });

    this.state = {
      treeValue: TreeState.create(testCaseDetail)
    };
    this.state = {
      treeValue: TreeState.expandAll(this.state.treeValue)
    }

  }



  render() {
    const { treeValue } = this.state;

    return (
      <div className="generation-detail-edit-table">
      <Fragment>
        <Row className="my-1">
          <Col>
            <ButtonGroup  >
              <Button variant="light" size="sm" onClick={this.handleOnExpandAll}>Expand all</Button>
              <Button variant="light" size="sm" onClick={this.handleOnCollapseAll}>Collapse all</Button>
            </ButtonGroup>
          </Col>
        </Row>

        <Row>
          <Col>
            <TreeTable
              value={treeValue}
              onChange={this.handleOnChange}
              bodyStyle={{overflow: 'overlay'}}>
              <TreeTable.Column basis="380px" grow="0"
                renderCell={this.renderIndexCell}
                renderHeaderCell={() => <span>변수 명</span>} />
              <TreeTable.Column basis="180px" grow="0"
                renderCell={this.renderValLength}
                renderHeaderCell={() => <span>값 길이</span>} />
              <TreeTable.Column basis="280px" grow="0"
                renderCell={this.renderVarType}
                renderHeaderCell={() => <span>구분 </span>} />
              <TreeTable.Column basis="180px" grow="0"
                renderCell={this.renderIsBinary}
                renderHeaderCell={() => <span>바이너리 여부 </span>} />
              <TreeTable.Column basis="380px" grow="0"
                renderCell={this.renderValue}
                renderHeaderCell={() => <span>값</span>} />
              <TreeTable.Column basis="180px" grow="0.6" className="mx-auto d-block"
                renderCell={this.renderMagnifier}
                renderHeaderCell={() => <span>값 편집</span>}
              />
            </TreeTable>
          </Col>
        </Row>
        <Modal className="myModal" show={this.state.show} onHide={this.handleClose} centered>
          <Modal.Header className="modal-header" closeButton>
            <Modal.Title>테스트케이스 편집</Modal.Title>
          </Modal.Header>
          <Modal.Body className="modal-body">
            <div className={cx("myModal-content")}>
                <label className="testcase-value">테스트 케이스 값</label>
                    <input type="text" id="testcaseValue" placeholder="ddd"
                      defaultValue={ this.state.testcaseValue }
                      onChange={e => this.setState({ testcaseValue: e.target.value })}/>
              </div>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleClose}>
              닫기
            </Button>
            <Button variant="secondary" onClick={this.handleSave}>
              저장
            </Button>
          </Modal.Footer>
        </Modal>
        <Row>
          <Col >
            <div className="text-right my-2">
              <Button onClick={this.handleClear} className="mr-1" variant="primary" type="submit">값 초기화</Button>
              <Button onClick={this.handleAddTC} className="mr-1" variant="outline-secondary">전문조회</Button>
              <Button onClick={this.handleUpdate} variant="primary">저장</Button>
            </div>
          </Col>
        </Row>

      </Fragment>
      </div>
    );
  }



  /* 3.3 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
  handleClose() {
    this.setState({ show: false });
  }

/* 3.4 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
  handleShow() {
    this.setState({ show: true });
  }

  handleSave() {
    //this.setState({ show: true });
  }

  handleOnChange = (newValue) => {
    this.setState({ treeValue: newValue });
  }


  handleOnExpandAll = () => {
    this.setState((state) => {
      return {
        treeValue: TreeState.expandAll(state.treeValue),
      };
    });
  }

  handleOnCollapseAll = () => {
    this.setState((state) => {
      return {
        treeValue: TreeState.collapseAll(state.treeValue)
      };
    });
  }


  renderIndexCell = (row) => {
    return (
      <div style={{ paddingLeft: (row.metadata.depth * 15) + 'px' }}
        className={row.metadata.hasChildren ? 'with-children' : 'without-children'}>

        {(row.metadata.hasChildren)
          ? (
            <button className="toggle-button" onClick={row.toggleChildren}></button>
          )
          : ''
        }
        <span>{row.data.varName}</span>
      </div>
    );
  }

  // renderEmployeesCell = (row) => {
  //   return (
  //     <span className="employees-cell">{row.data.employees}</span>
  //   );
  // }

  // renderExpensesCell = (row) => {
  //   return (
  //     <span className="expenses-cell">{row.data.expenses}</span>
  //   );
  // }

  renderValLength = (row) => {
    return (
      (row.metadata.hasChildren)
        ? ('')
        : <input type="text" value={row.data.valLength}

        onChange={(event) => {
          row.updateData({
            ...row.data,
            valLength: event.target.value,
          });
        }} />
    );
  }

  renderVarType = (row) => {
    return (
      (row.metadata.hasChildren)
        ? ('')
        : <input type="text" value={row.data.varType}
        onChange={(event) => {
          row.updateData({
            ...row.data,
            varType: event.target.value,
          });
        }} />
    );
  }

  renderIsBinary = (row) => {
    // console.log(row);
    return (
      (row.metadata.hasChildren)
        ? ('')
        : <input type="text" value={row.data.isBinary}
        onChange={(event) => {
          row.updateData({
            ...row.data,
            isBinary: event.target.value,
          });
        }} />
    );
  }

  renderValue = (row) => {
    return (
      (row.metadata.hasChildren)
        ? ('')
        : <input type="text" value={row.data.value}

        onChange={(event) => {
          row.updateData({
            ...row.data,
            value: event.target.value,
          });
        }} />
    );
  }

  renderMagnifier = (row) => {
    return (
      (row.metadata.hasChildren)
        ? ('')
        :<Button className="ml-4" variant="light" size="sm" onClick={this.handleShow}>
          <FontAwesomeIcon icon={faSearchPlus} size="1x" />
        </Button>
    );
  }

  ///////////////임시 모달 trigger (새 테스트케이스 추가 모달)/////////////////
  handleAddTC = (e) => {
    const { UnitActions } = this.props;
    const value = true;
    const name = 'show';
    UnitActions.updateState({ name, value });
    e.preventDefault();

  }
  ////////////////////////////////////////////////////////////////////////

  handleClear = () => {

    const { testCaseDetail, EditActions } = this.props;

    this.emptyValues(testCaseDetail[0]);
    EditActions.updateState({ testCaseDetail });

    this.setState(() => {
      return {
        treeValue: TreeState.create(testCaseDetail)
      };
    });

    this.handleOnExpandAll();

  }

  emptyValues = (vo) => {
    if (vo.children != null) {
      vo.children.map(this.emptyValues);
    }
    vo.data.value = "";
  }

  handleUpdate = () => {
    const { treeValue } = this.state;
    const { testCaseDetail } = this.props;
     console.log("update init");
    console.log(treeValue);
    console.log(testCaseDetail);
    const rows = treeValue.data;

    var indexer = {
      i: 0,
      last: rows.length - 1,
      up: function() {
        this.i++;
        console.log("increment" + this.i);
        return this;
      }
    }
     const updatedJSON = [];
    this.buildJSON(indexer, rows, updatedJSON);

    console.log("update completed");
    console.log(updatedJSON);
  }


  buildJSON(indexer, src, rslt) {

    while (indexer.i <= indexer.last) {

      if (src[indexer.i].metadata.hasChildren) {
        console.log(indexer.i+": has children calling recursion");
        // indexer.i++;
        // this.buildJSON(indexer, src, rslt);
        var newChildren = [];
        var newNode = {
          data: src[indexer.i].data,
          children: this.buildJSON(indexer.up(), src, newChildren)
        }

      } else {
        console.log(indexer.i+": has no child");
        var newNode = {
          data: src[indexer.i].data
        }
      }
      console.log(newNode.data);
      rslt.push(newNode);
      indexer.i++;


    }
    return rslt;
  }

  /*
    Arr = [1,2,2,3,4,2,3];
    var i = 0
    var lastI = arr.length -1
    result = [];

    buildTree(i, Arr, lastI )
      while(i <= lastI) {
        if(Arr[i].hasChildren) {
          var newNode = {
            data: Arr[i].data,
            children: buildTree(i+1)
          }
        } else {
          var newNode = {
            data: Arr[i].data
          }
        }
        rslt.push(newNode);
        i++;
      }
    }

  */

  // buildTree(currNode, lastNode, index) {

  //   if(currNode.hasChildren) {
  //     return {
  //       data: currNode.data,
  //       children: buildTree(nextNode, currNode, lastIndex)
  //     }
  //   } else {
  //     return {
  //       data: currNode.data
  //     }
  //   }

  // }


  // buildJS = (node, parent, rows) => {
  //   if(node.metadata.hasChildren) {

  //     this.buildJS( rows[node.metadata.index + 1], )
  //   }

  //   parent.push(node.data);
  // }

}


export default connect(
  (state) => ({
    testCaseData: state.edit.get('testCaseData'),
    testCaseDetail: state.edit.get('testCaseDetail'),
  }),
  (dispatch) => ({
    EditActions: bindActionCreators(editActions, dispatch),
    UnitActions: bindActionCreators(unitActions, dispatch)

  })
)(GenerationDetailEditTable);

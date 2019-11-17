import React, { Component, Fragment } from 'react';
import './RuleFieldMappingTable.scss';
import { TreeTable, TreeState } from 'cp-react-tree-table';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import ButtonGroup from 'react-bootstrap/ButtonGroup';
import Button from 'react-bootstrap/Button';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearchPlus } from '@fortawesome/free-solid-svg-icons'
import Util from 'lib/Util';

import { connect } from 'react-redux';
import { bindActionCreators } from 'redux';
import * as randomRulesActions from 'store/modules/randomRules';

import mockData from '../dummydata/MOCK2';


class RuleFieldMappingTable extends Component {

  constructor(props) {
    super(props);

    this.state={
      testResultDetail: this.props.testResultDetail,
      fieldTypeNameList:[],
      pageNum: 1,
      pageCnt: 10
    };

    const { rulesData, RandomRulesActions } = this.props;
    console.log("cccc",rulesData);
    console.log("cccc:detail",rulesData.detail);
    const rulesDataDetail = rulesData.detail.map((data) => {
      //const testCaseDetail = mockData.detail.map((data) => {

      return ({
        data: data.data,
        height: data.height,
        children: data.children
      })
    });

    RandomRulesActions.updateState({ rulesDataDetail });

    this.state = {
      treeValue: TreeState.create(rulesDataDetail)
    };
    this.state = {
      treeValue: TreeState.expandAll(this.state.treeValue)
    };

  }
  componentDidMount(props) {
    this.getFieldTypeList();

  }
  getFieldTypeList= () =>{
    const url = '/randomGeneration/viewRandomGenerationFieldTypeList/1'; //나중에 executionUuid 수정
    const data = {
      params: {
        pageNum: 1,
        pageCnt: 10
       }
    };
    Util.processAxios(url, data, (msg, response) =>{
      //받아온 데이터를 임시 변수에 저장
      let  fieldTypeNameList = [];

      if (response == null){
        fieldTypeNameList = [{
          'fieldTypeName': ""
        }];

        this.setState({
          fieldTypeNameList:  fieldTypeNameList,
          totalCnt: 0,
          //rightTopSuccessCnt:0,
          //rightTopFailCnt: 0
        });

        return(
          alert('No data to be shown'+msg)
        )
      }
      else{
        fieldTypeNameList = response.data.body.randomGenFieldTypeList;

        //fieldTypeNameList = fieldTypeNameList.map(item => item.fieldTypeName); // [ "RandomInt" ]
        fieldTypeNameList.map(( fieldTypeNameList, i) => {
          fieldTypeNameList = {...fieldTypeNameList};
          fieldTypeNameList[i] =  fieldTypeNameList;
          return true;
        })

        //setState 로 state에 다시 변경된 list 다시 저장
        this.setState({
          fieldTypeNameList: fieldTypeNameList.filter(name => name.algorithmLevel == this.props.ruleLevel),
          totalCnt: response.data.body.totalCnt,
        });
      }
    });
  }

  render() {
    console.log("row",this.state.fieldTypeNameList);
    const { treeValue } = this.state;
    return (
      <div className="rule-field-mapping-table">
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
              bodyStyle={{overflow: 'overlay'}}
              height={450}

            >
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
              <TreeTable.Column basis="350px" grow="0"
                renderCell={this.renderValue}
                renderHeaderCell={() => <span>필드 유형</span>} />
              <TreeTable.Column basis="200px" grow="0.6" className="mx-auto d-block"
                renderCell={this.renderMagnifier}
                renderHeaderCell={() => <span>파라미터 값편집</span>}
              />
            </TreeTable>
          </Col>
        </Row>

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

    console.log("row",this.state.fieldTypeNameList);
    if(this.state.fieldTypeNameList==null){
      return((row.metadata.hasChildren)
      ? ('')
      :<select id = "select-fieldTypeName" defaultValue={row.data.fieldTypeNo} onChange={(e) => {row.data.fieldTypeNo = parseInt(e.target.value); }}>
              <option value={0}>string</option>
       </select>);
    }

    console.log("fieldTypeName:", row.data.fieldTypeNo)
    return (
      (row.metadata.hasChildren)
        ? ('')
        :<select id = "select-fieldTypeName" defaultValue={8} onChange={(e) => {row.data.fieldTypeNo = parseInt(e.target.value); console.log(e.target.value);}}>

                {this.state.fieldTypeNameList.map((name) => <option value={name.fieldTypeNo}>{name.fieldTypeName}</option>)}

         </select>

    );
  }



  renderMagnifier = (row) => {

    return (
      (row.metadata.hasChildren)
        ? ('')
        :<Button className="ml-4" variant="light" size="sm" onClick={() => {console.log("클릭시", this.props.testResultDetail(row.data.fieldTypeNo)); this.props.testResultDetail(8)}}>
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


/* 정재웅 코드 부분 */
/////////////////////임시 결과상세 JSON 로더/////////////////////////
export default connect(
(state) => ({
  rulesData: state.randomRules.get('rulesData'),
  rulesDataDetail: state.randomRules.get('rulesDataDetail'),
}),
(dispatch) => ({
  RandomRulesActions: bindActionCreators(randomRulesActions, dispatch)
})
)(RuleFieldMappingTable);

/*
export default connect(
  (state) => ({
    testCaseData: state.edit.get('testCaseData'),
    testCaseDetail: state.edit.get('testCaseDetail'),
  }),
  (dispatch) => ({
    EditActions: bindActionCreators(editActions, dispatch),
    UnitActions: bindActionCreators(unitActions, dispatch)

  })
)(RuleFieldMappingTable);
*/

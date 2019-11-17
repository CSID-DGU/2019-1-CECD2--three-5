/**
 * 작성자: 김지운, 원예인
 * 설명: 컬럼 및 버튼을 정의하고 react-bootstrap-table을 이용하여 랜덤 테스트케이스별 목록을 표시한다.
 **/
import React, { Component } from "react";
import BootstrapTable from "react-bootstrap-table-next";
import update from "react-addons-update"; //react-addons-update 라이브러리. 테이블의 배열 추가하거나 수정 시 사용
import Util from "lib/Util"; //API 호출
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Modal from "react-bootstrap/Modal";
import BootstrapSwitchButton from "bootstrap-switch-button-react";
import ToolkitProvider, { Search } from "react-bootstrap-table2-toolkit";
import { withRouter } from "react-router-dom";

import RandomGenerationFooter from "components/main/unit/randomgenerate/generation/RandomGenerationFooter";
import RandomGenerationResultsToolBar from "components/main/unit/randomgenerate/generation/RandomGenerationResultsToolBar";
import styles from "./RandomGenerationResultsTable.scss";
import classNames from "classnames/bind";
const cx = classNames.bind(styles);

class RandomGenerationResultsTable extends Component {
  constructor(props) {
    super(props);

    this.state = {
      //successCount: 0,
      //failCount: 0,
      totalCnt: this.props.totalCnt,
      pageCnt: this.props.pageCnt,
      pageNum: this.props.pageNum,
      //executionUuid: this.props.executionUuid,
      from: this.props.from,
      to: this.props.to,
      selected: [],
      selectedList,
      show: false,
      searchTestcaseGroupName : null,
      searchTestcaseName : null,
      searchGenerator : null,
      searchReviseDateFrom : null,
      searchReviseDateTo : null,
      searchArray: [{
        searchTestcaseGroupName : null,
        searchTestcaseName : null,
        searchGenerator : null,
        searchReviseDateFrom : null,
        searchReviseDateTo : null
      }]
    };

    this.handleShow = this.handleShow.bind(this);
    this.handleClose = this.handleClose.bind(this);

    const testcaseListInfo = this.props.testcaseListInfo;
    const selectedList = [];

    this.batchDelete = this.batchDelete.bind(this);
  }

  /****************************************
   * 1. checkbox를 이용한 row 선택
   ****************************************/
  /* 1.1 선택 - Row 하나를 선택해준다 - selected라는 임시 array에
   * 선택해논 row executionUuid를 넣어놔 준다 (다건 삭제시 이용)*/
  handleOnSelect = (row, isSelect) => {
    if (isSelect) {
      this.setState(() => ({
        selected: [...this.state.selected, row.executionUuid]
      }));
    } else {
      this.setState(() => ({
        selected: this.state.selected.filter(x => x !== row.executionUuid)
      }));
    }
  };

  /* 1.2 전체 선택 - Row 전체를 헤더에서 선택해준다*/
  handleOnSelectAll = (isSelect, rows) => {
    const ids = rows.map(r => r.executionUuid);
    if (isSelect) {
      this.setState(() => ({
        selected: ids
      }));
    } else {
      this.setState(() => ({
        selected: []
      }));
    }
  };

  /****************************************
   * 2. 이력 삭제
   ****************************************/
  /* 2.1 삭제 - 테이블 목록에서 체크한 row들을 삭제하고 테이블을 재조회 한다.*/
  batchDelete = () => {
    let selectedList = this.state.selectedList;
    var jsonArray = [];

    for (let i = 0; i < this.state.selected.length; i++) {
      jsonArray[i] = { "executionUuid": this.state.selected[i] };
    }

    if (this.state.selected.length === 0) {
      alert("삭제 할 레코드를 선택해 주시기 바랍니다.");
    } else {
      const url = "/serviceRun/removeTestcaseResultList/1";
      const data = { "removeList": jsonArray };
      //const data = selectedList;
      let body = { data: data };

      Util.deleteAxios(url, body, (msg, response) => {
        this.props.remoteViewResultsList(
          this.state.pageNum,
          this.state.pageCnt
        );
      });
    }
  };

  /*********************************************
   * 3. 검색 조건 (검색, 상세검색, 검색값 초기화)
   *********************************************/
  /* 3.1 상세검색 - 상세 모달에서 선택한 검색 조건을 테이블에 넘겨줘서 테이블을 재조회 해준다.*/
  searchDetailCondition = (searchTestcaseGroupName, searchTestcaseName, searchGenerator, searchReviseDateFrom, searchReviseDateTo) => {
    const url = "/randomGeneration/viewRandomGenerationTestcaseList/1";
    const data = {
      params: {
        searchTestcaseGroupName : searchTestcaseGroupName,
        searchTestcaseName : searchTestcaseName,
        searchGenerator : searchGenerator,
        searchReviseDateFrom : searchReviseDateFrom,
        searchReviseDateTo : searchReviseDateTo,
        pageCnt: this.state.pageCnt,
        pageNum: this.state.pageNum,
        //executionUuid: "11111111"
      }
    };

    Util.processAxios(url, data, (msg, response) => {
      this.props.remoteViewResultsList( this.state.pageNum, this.state.pageCnt, searchTestcaseGroupName, searchTestcaseName, searchGenerator, searchReviseDateFrom, searchReviseDateTo);
    });
  }

  /* 3.2 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
  searchDetailClear = () => {
    this.searchDetailCondition(null, null, null, null, null);
    this.setState({
      searchTestcaseGroupName : null,
      searchTestcaseName : null,
      searchGenerator : null,
      searchReviseDateFrom : null,
      searchReviseDateTo : null
    });
    document.getElementById("searchTestcaseGroupNameField").value="";
    document.getElementById("searchTestcaseNameField").value="";
    document.getElementById("searchGeneratorField").value="";
    document.getElementById("searchReviseDateFromField").value="";
    document.getElementById("searchReviseDateToField").value="";
  };

  searchClear = (props) => {
    document.getElementsByClassName("my-search").value="";
    props.searchProps.onSearch('');

  }

  /* 3.3 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
  handleClose() {
    this.setState({ show: false });
  }

  /* 3.4 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
  handleShow() {
    this.setState({ show: true });
  }

  /*******************************************
   * 4. 컬럼 formatter 및 컬럼 정의
   *******************************************/
  /* 4.1 테스트케이스별 결과 아이콘 포매터 */
  formatWithIcon = (cell, row) => {
    return (
      <i
        className="fa fa-search-plus"
        idname="details"
        onClick={() => {
          this.props.testResultDetail(row);
        }}
      />
    );
  };

  /* 4.4 컬럼 정의 */
  columns = [
    {
      dataField: "rowSeqNo",
      text: "순번",
      editable: false,
      headerStyle: { width: "10%" },
      align: "center",
      style: {
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden"
      }
    },
    {
      dataField: "testcaseGroupName",
      text: "테스트케이스 그룹 명",
      editable: false,
      headerStyle: { width: "25%" },
      align: "left",
      style: {
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden"
      }
    },
    {
      dataField: "testcaseName",
      text: "테스트케이스 명",
      editable: false,
      headerStyle: { width: "25%" },
      align: "left",
      style: {
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden"
      }
    },
    {
      dataField: "generator",
      text: "생성자",
      editable: false,
      headerStyle: { width: "15%" },
      align: "center",
      formatter: this.successFormatter,
      style: {
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden"
      }
    },
    {
      dataField: "revisionDate",
      text: "최근 변경 일시",
      editable: false,
      headerStyle: { width: "15%" },
      align: "center",
      style: {
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden"
      }
    },
    {
      dataField: "serviceResultsDetails",
      text: "상세 편집",
      editable: false,
      headerStyle: { width: "10%" },
      align: "center",
      formatter: this.formatWithIcon,
      style: {
        whiteSpace: "nowrap",
        textOverflow: "ellipsis",
        overflow: "hidden"
      }
    }
  ];

  render() {
    const testcaseListInfo = this.props.testcaseListInfo;
    const newPageCnt = this.props.newPageCnt;

    console.log("=======testcaseListInfo=>", testcaseListInfo);
    
    /* row 선택값 react bootstrap table에 넣어주기 */
    const selectRow = {
      mode: "checkbox",
      clickToSelect: true,
      selected: this.state.selected,
      onSelect: this.handleOnSelect,
      onSelectAll: this.handleOnSelectAll
    };

    /* react bootstrap table의 커스텀 검색창 */
    const MySearch = props => {
      let input;
      const handleClick = () => {
        props.onSearch(input.value);
        console.log(input.value.length);
        if (input.value.length == 0) {
          return alert("검색어를 입력하세요.");
        }
      };
      return (
        <div className="random-generation-result-table">
        <div className="search-bar">
          <input
            className="form-control"
            style={{ backgroundColor: "white" }}
            ref={n => (input = n)}
            type="text"
            placeholder="실행 결과 메세지를 검색하세요."
          />
          <Button
            key="simple-search"
            className={cx("icon-button")}
            onClick={handleClick}
          >
            <i className={cx("fa fa-search")} />
          </Button>
        </div></div>
      );
    };


    const { ClearSearchButton } = Search;

    return (
      <div className="random-generation-result-table">
          <div className="details-table">
            <ToolkitProvider keyField="fieldOne" data={testcaseListInfo} columns={this.columns} search>
              {props => (
                <div>
                  <div className="toolbar-row">
                    <MySearch {...props.searchProps} className="my-search"/>
                    <div className="detail-search">
                    <Button onClick={ ()=> this.searchClear(props) } className="clear-button">
                      Clear
                    </Button>
                      <Button key="detail-search" className="detail-button" onClick={this.handleShow}>
                        상세
                      </Button>
                      <Modal className="myModal" show={this.state.show} onHide={this.handleClose} centered>
                            <Modal.Header className="modal-header" closeButton>
                              <Modal.Title>상세 검색 조건</Modal.Title>
                            </Modal.Header>
                            <Modal.Body className="modal-body">
                              <div className={cx("myModal-content")}>
                                <div className={cx("testcasegroup-search")}>
                                  <label className="search-label">테스트케이스 그룹명</label>
                                  <input type="text" id="searchTestcaseGroupNameField" placeholder="테스트케이스 그룹명을 입력하세요."
                                    defaultValue={ this.state.searchTestcaseGroupName }
                                    onChange={e => this.setState({ searchTestcaseGroupName: e.target.value })}/>
                                </div>
                                <div className={cx("testcase-search")}>
                                  <label className="search-label">테스트케이스</label>
                                  <input type="text" id="searchTestcaseNameField" placeholder="테스트케이스명을 입력하세요."
                                    defaultValue={ this.state.searchTestcaseName }
                                    onChange={e => this.setState({ searchTestcaseName: e.target.value })}/>
                                </div>
                                <div className={cx("generator-search")}>
                                  <label className="search-label">생성자</label>
                                  <input type="text" id="searchGeneratorField" placeholder="생성자를 입력하세요."
                                    defaultValue={ this.state.searchGenerator }
                                    onChange={e => this.setState({ searchGenerator: e.target.value })}/>
                                </div>
                                <div className="generateDate-search">
                                  <label>생성 날짜</label>
                                  <input type="text" id="searchReviseDateFromField" placeholder="시작일을 입력하세요."
                                    defaultValue={this.state.searchReviseDateFrom}
                                    onChange={e => this.setState({searchReviseDateFrom: e.target.value})}/>
                                  &nbsp;~&nbsp;
                                  <input type="text" id="searchReviseDateToField" placeholder="종료일을 입력하세요."
                                    defaultValue={this.state.searchReviseDateTo}
                                    onChange={e => this.setState({ searchReviseDateTo: e.target.value})}/>
                                </div>
                              </div>
                            </Modal.Body>
                            <Modal.Footer>
                              <Button variant="secondary" onClick={this.handleClose}>
                                닫기
                              </Button>
                              <Button variant="secondary" onClick={() => this.searchDetailClear(this.state.searchTestcaseGroupNameField,
                                    this.state.searchTestcaseNameField,
                                    this.state.searchGeneratorField,
                                    this.state.searchReviseDateFromField,
                                    this.state.searchReviseDateToField)}>
                                상세 검색 Clear
                                <i className={cx("fa fa-clear")} />
                              </Button>
                              <Button variant="primary"
                                onClick={e => this.searchDetailCondition(
                                    this.state.searchTestcaseGroupName,
                                    this.state.searchTestcaseName,
                                    this.state.searchGenerator,
                                    this.state.searchReviseDateFrom,
                                    this.state.searchReviseDateTo
                                  )}>
                                검색
                              </Button>
                            </Modal.Footer>
                          </Modal>
                    </div>
                    <div className="search-condition">
                      현재 검색 기준 |
                      <span className="search-criteria">
                        {this.state.searchTestcaseGroupName !== null
                          ? "테스트 케이스 그룹 명: " +
                            this.state.searchTestcaseGroupName
                          : null}
                      </span>
                      <span className="search-criteria">
                        {this.state.searchTestcaseName !== null
                          ? "테스트 케이스 명: " + this.state.searchTestcaseName
                          : null}
                      </span>
                      <span className="search-criteria">
                            {this.state.searchGenerator !== null
                              ? "생성자: " + this.state.searchGenerator
                              : null}
                          </span>
                          <span className="search-criteria">
                            {this.state.searchReviseDateFrom !== null
                              ? "시작일: " +
                                this.state.searchReviseDateFrom
                              : null}
                          </span>
                          <span className="search-criteria">
                            {this.state.searchReviseDateTo !== null
                              ? "종료일: " + this.state.searchReviseDateTo
                              : null}
                          </span>
                    </div>
                  </div>
                  <RandomGenerationResultsToolBar
                    {...this.state}
                    testRun={this.props.testRun}
                    pageCnt={this.props.pageCnt}
                    from={this.props.from}
                    to={this.props.to}
                    totalCnt={this.props.totalCnt}
                    //rightTopFailCnt = {this.props.rightTopFailCnt}
                    //rightTopSuccessCnt = {this.props.rightTopSuccessCnt}
                    localOnChangeSelectValue={
                      this.props.localOnChangeSelectValue
                    }
                    localGoPage={this.props.localGoPage}
                    batchDelete={this.batchDelete}
                    className="random-generation-results-tool-bar"
                  />
                  <BootstrapTable
                    {...props.baseProps}
                    {...this.state}
                    keyField="executionUuid"
                    data={testcaseListInfo}
                    bordered={false} //세로 줄 구분 삭제
                    columns={this.columns}
                    //pagination = {paginationFactory(options)}
                    selectRow={selectRow} //checkbox
                    defaultSorted={this.defaultSorted}
                    rowStyle={(row, rowIndex) =>
                      rowIndex % 2 === 0
                        ? { backgroundColor: "rgba(245, 245, 245, 1)" }
                        : { backgroundColor: "rgba(250, 250, 250, 1)" }
                    }
                  />
                </div>
              )}
            </ToolkitProvider>
            <div className={cx("footer")}>
              <RandomGenerationFooter
                {...this.state}
                totalCnt={this.props.totalCnt}
                pageCnt={this.props.pageCnt}
                pageNum={this.props.pageNum}
                from={this.props.from}
                to={this.props.to}
                remoteChangePageNum={this.props.remoteChangePageNum}
                remoteChangePageCnt={this.props.remoteChangePageCnt}
                localOnChangeSelectValue={this.props.localOnChangeSelectValue}
                localGoPage={this.props.localGoPage}
              />
            </div>
          </div>
        </div>
    );
  }
}

export default withRouter(RandomGenerationResultsTable);

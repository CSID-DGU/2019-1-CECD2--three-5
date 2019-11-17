/**
 * 작성자: 김지운, 원예인
 * 설명: 컬럼 및 버튼을 정의하고 react-bootstrap-table을 이용하여 랜덤 생성 이력을 표시한다.
**/
import React, { Component } from 'react';
import BootstrapTable from 'react-bootstrap-table-next';
import Util from 'lib/Util'; //API 호출
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import { withRouter } from 'react-router-dom';

import PairwiseGenerationFooter from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationFooter';
import PairwiseGenerationToolBar from 'components/main/unit/pairwisegenerate/generation/PairwiseGenerationToolBar';
import styles from './PairwiseGenerationHistoryTable.scss';
import classNames from 'classnames/bind';
const cx = classNames.bind(styles);

class PairwiseGenerationHistoryTable extends Component {
    constructor(props) {
        super(props);

        this.state = {
            //successCount: 0,
            //failCount: 0,
            totalCnt: this.props.totalCnt,
            pageCnt: this.props.pageCnt,
            pageNum: this.props.pageNum,
            from: this.props.from,
            to: this.props.to,
            selected: [],
            selectedList,
            show: false,
            searchIsUse: null,
            searchGenerator: null,
            searchGenerateDateFrom: null,
            searchGenerateDateTo: null,
            searchRuleKind: null,
            searchArray: [{
                searchGenerator: null,
                searchGenerateDateFrom: null,
                searchGenerateDateTo: null,
                searchRuleKind: null
            }]
        }

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);

        const pairwiseGenHistList = this.props.pairwiseGenHistList;
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
    }

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
    }


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
            const url = '/randomGeneration/removeRandomGenerationHistoryList/1';
            const data = { "removeList": jsonArray };
            //const data = selectedList;
            let body = { "data": data }

            Util.deleteAxios(url, body, (msg, response) => {
                this.props.remoteViewTableList(this.state.pageNum, this.state.pageCnt);
            });
        }
    }

    /*********************************************
    * 3. 검색 조건 (검색, 상세검색, 검색값 초기화)
    *********************************************/
    /* 3.1 상세검색 - 상세 모달에서 선택한 검색 조건을 테이블에 넘겨줘서 테이블을 재조회 해준다.*/
    searchDetailCondition = (searchGenerator, searchGenerateDateFrom, searchGenerateDateTo, searchRuleKind) => {
        const url = '/randomGeneration/viewRandomGenerationHistoryList/1';
        const data = {
            params: {
                searchGenerator: searchGenerator,
                searchGenerateDateFrom: searchGenerateDateFrom,
                searchGenerateDateTo: searchGenerateDateTo,
                searchRuleKind: searchRuleKind,
                pageCnt: this.state.pageCnt,
                pageNum: this.state.pageNum,
                //executionProjectNo: 10000002
            }
        };

        Util.processAxios(url, data, (msg, response) => {
            this.props.remoteViewTableList(this.state.pageNum, this.state.pageCnt, searchGenerator, searchGenerateDateFrom, searchGenerateDateTo, searchRuleKind);
        });
    }

    /* 3.2 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
    searchClear = () => {
        this.searchDetailCondition(null, null, null, null);
        this.setState({
            searchGenerator: null,
            searchGenerateDateFrom: null,
            searchGenerateDateTo: null,
            searchRuleKind: null
        });
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
            <i className="fa fa-search-plus" idname="details" onClick={() => { this.props.testResults(row) }} />
        )
    }

    /* 4.4 컬럼 정의 */
    columns = [
        {
            dataField: 'rowSeqNo',
            text: '순번',
            editable: false,
            headerStyle: { width: '5%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'generateComment',
            text: '조합 생성 조건',
            editable: false,
            headerStyle: { width: '30%' },
            align: 'left',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'generatedCount',
            text: '테스트케이스 개수',
            editable: false,
            headerStyle: { width: '12%' },
            align: 'right',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'generateDate',
            text: '생성일자',
            editable: false,
            headerStyle: { width: '20%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden',
            }
        },
        {
            dataField: 'generator',
            text: '생성자',
            editable: false,
            headerStyle: { width: '10%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'clickToNext',
            text: '목록 반영',
            editable: false,
            headerStyle: { width: '15%' },
            align: 'center',
            formatter: this.formatWithIcon,
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden',
            }
        }
    ];

    render() {

        const pairwiseGenHistList = this.props.pairwiseGenHistList;
        const newPageCnt = this.props.newPageCnt;
        let testRun = this.props.testRun;

        /* row 선택값 react bootstrap table에 넣어주기 */
        const selectRow = {
            mode: 'checkbox',
            clickToSelect: true,
            selected: this.state.selected,
            onSelect: this.handleOnSelect,
            onSelectAll: this.handleOnSelectAll
        };

        /* react bootstrap table의 커스텀 검색창 */
        const MySearch = (props) => {
            let input;
            const handleClick = () => {
                props.onSearch(input.value);
                console.log(input.value.length);
                if (input.value.length == 0) {
                    return (
                        alert('검색어를 입력하세요.')
                    )
                }
            };
            return (
              <div className="pairwise-generation-history-table">
                <div className='search-bar'>
                    <input
                        className="form-control"
                        style={{ backgroundColor: 'white' }}
                        ref={n => input = n}
                        type="text"
                        placeholder="검색어를 입력하세요."
                    />
                    <Button key="simple-search" className={cx('icon-button')} onClick={handleClick}>
                        <i className={cx("fa fa-search")} />
                    </Button>
                </div>
                </div>
            );
        };

        const { ClearSearchButton } = Search;

        return (
            <div className="pairwise-generation-history-table">
              <div className="details-table">
                <ToolkitProvider
                  keyField="executionUuid"
                  data={pairwiseGenHistList}
                  columns={this.columns}
                  search
                >
                  {props => (
                    <div>
                      <div className="toolbar-row">
                        <MySearch {...props.searchProps} />

                        <div className="detail-search">
                        <ClearSearchButton { ...props.searchProps } className="clear-button"/>
                          <Button key="detail-search" className="detail-button" onClick={this.handleShow}>
                            상세
                          </Button>
                          <Modal className="myModal" show={this.state.show} onHide={this.handleClose} centered>
                            <Modal.Header className="modal-header" closeButton>
                              <Modal.Title>상세 검색 조건</Modal.Title>
                            </Modal.Header>
                            <Modal.Body className="modal-body">
                              <div className="myModal-content">
                                <div className="generator-search">
                                  <label>생성자</label>
                                  <input type="text" id="searchGenerator" placeholder="생성자를 입력하세요."
                                    defaultValue={ this.state.searchGenerator }
                                    onChange={e => this.setState({ searchGenerator: e.target.value })}/>
                                </div>
                                <div className="generateDate-search">
                                  <label>생성 날짜</label>
                                  <input type="text" id="searchGenerateDateFrom" placeholder="시작일을 입력하세요."
                                    defaultValue={this.state.searchGenerateDateFrom}
                                    onChange={e => this.setState({searchGenerateDateFrom: e.target.value})}/>
                                  &nbsp;~&nbsp;
                                  <input type="text" id="searchGenerateDateTo" placeholder="종료일을 입력하세요."
                                    defaultValue={this.state.searchGenerateDateTo}
                                    onChange={e => this.setState({ searchGenerateDateTo: e.target.value})}/>
                                </div>
                                <div className="ruleKind-search">
                                  <label>생성 유형</label>
                                  <input type="text" id="searchRuleKind" placeholder="생성유형을 입력하세요."
                                    defaultValue={this.state.searchRuleKind}
                                    onChange={e => this.setState({searchRuleKind: e.target.value})}/>
                                </div>
                              </div>
                            </Modal.Body>
                            <Modal.Footer>
                              <Button variant="secondary" onClick={this.handleClose}>
                                닫기
                              </Button>
                              <Button variant="secondary" onClick={() => this.searchClear()}>
                                상세 검색 Clear
                                <i className={cx("fa fa-clear")} />
                              </Button>
                              <Button variant="primary"
                                onClick={e => this.searchDetailCondition(
                                    this.state.searchGenerator,
                                    this.state.searchGenerateDateFrom,
                                    this.state.searchGenerateDateTo,
                                    this.state.searchRuleKind
                                  )}>
                                검색
                              </Button>
                            </Modal.Footer>
                          </Modal>
                        </div>
                        <div className="search-condition">
                          현재 검색 기준 |
                          <span className="search-criteria">
                            {this.state.searchGenerator !== null
                              ? "생성자: " + this.state.searchGenerator
                              : null}
                          </span>
                          <span className="search-criteria">
                            {this.state.searchGenerateDateFrom !== null
                              ? "시작일: " +
                                this.state.searchGenerateDateFrom
                              : null}
                          </span>
                          <span className="search-criteria">
                            {this.state.searchGenerateDateTo !== null
                              ? "종료일: " + this.state.searchGenerateDateTo
                              : null}
                          </span>
                          <span className="search-criteria">
                            {this.state.searchRuleKind !== null
                              ? "생성 유형: " + this.state.searchRuleKind
                              : null}
                          </span>

                        </div>
                      </div>
                      <PairwiseGenerationToolBar
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
                        className="pairwise-generation-tool-bar"
                      />
                      <BootstrapTable
                        {...props.baseProps}
                        {...this.state}
                        keyField="executionUuid"
                        data={pairwiseGenHistList}
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
                        bodyStyle={{overflow: 'overlay'}}
                      />
                    </div>
                  )}
                </ToolkitProvider>
                <div className={cx("footer")}>
                  <PairwiseGenerationFooter
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

export default PairwiseGenerationHistoryTable;

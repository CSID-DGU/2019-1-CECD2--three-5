/**
 * 작성자 남예진
 * 설명: 컬럼 및 버튼을 정의하고 react-bootstrap-table을 이용하여 서비스 실행 이력을 표시한다.
**/
import React, { Component } from 'react';
import BootstrapTable from 'react-bootstrap-table-next';
import Util from 'lib/Util'; //API 호출
import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Modal from 'react-bootstrap/Modal';
import cellEditFactory,{ Type } from 'react-bootstrap-table2-editor';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import { withRouter } from 'react-router-dom';
import update from 'react-addons-update';
import RandomAlgorithmFooter from 'components/main/unit/randomgenerate/algorithm/RandomAlgorithmFooter';
import RandomAlgorithmParameterRegisterToolBar from 'components/main/unit/randomgenerate/algorithm/RandomAlgorithmParameterRegisterToolBar';
import styles from './RandomAlgorithmParameterRegisterTable.scss';
import classNames from 'classnames/bind';
import { random } from 'node-forge';
const cx = classNames.bind(styles);
  
class RandomAlgorithmParameterRegisterTable extends Component {
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
            randomGenAlgorithmParamList: this.props.randomGenAlgorithmParamList,
            show: false,
            searchIsUse: null,
            searchNum: null,
            searchMessage: null,
            searchArray: [{
                searchAlgorithmLevel: null,
                searchAlgorithmTypeComment: null,
                searchAlgorithmTypeName: null,
                searchGenerateDateFrom: null,
                searchGenerateDateTo: null,
                searchGenerator: null
            }]
        }
        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        const selectedList = [];
        this.localAddRow = this.localAddRow.bind(this);
        //this.batchDelete = this.batchDelete.bind(this);
        this.remoteBatchDelete=this.remoteBatchDelete.bind(this);
        this.remoteBatchSave=this.remoteBatchSave.bind(this);
    }

    localChangeRowType(row){
        if(row.rowType === "R"){
          row.prevRowType="R";
          row.rowType="U";
        }
      }
    checkVaildation (oldValue, newValue, row, column) {
        var length = (function(s,b,i,c){
          for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
          return b
        })(newValue);
        var pattern = /[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣-_.]*/;
        if(column.dataField === "systemDivisionName"){
          row.systemDivisionName = newValue;
          row.systemDivision = String(row.systemDivisionName).charAt(0);//System 구분 Name으로 선택하지만 systemDivision도 같이 바뀌어줘야 합니다.
          return true;
        }
        else if(column.dataField === "authComment"){ //설명의 길이를 체크한다.
          if(length < 1001){ //권한 설명 길이 1000미만 체크
            row.authComment = newValue;
            return true;
          }else{
            row.authComment = oldValue;
            return false;
          }
        }
      }
    /****************************************
    * 1. checkbox를 이용한 row 선택 
    ****************************************/
    /* 1.1 선택 - Row 하나를 선택해준다 - selected라는 임시 array에
    * 선택해논 row executionUuid를 넣어놔 준다 (다건 삭제시 이용)*/
    handleOnSelect = (row, isSelect) => {
        if (isSelect) {
            row.prevRowType=row.rowType;
            row.rowType="D";
            this.setState(() => ({
                selected: [...this.state.selected, row.parameterNo]
            }));
            
        } else {
            row.rowType=row.prevRowType;
            row.prevRowType="D";
            this.setState(() => ({
                selected: this.state.selected.filter(x => x !== row.parameterNo)
            }));
        }
        console.log("selected", this.state.selected);
    }

    /* 1.2 전체 선택 - Row 전체를 헤더에서 선택해준다*/
    handleOnSelectAll = (isSelect, rows) => {
        const ids = rows.map(r => r.parameterNo);
        
        if (isSelect) {
            rows.map((row,i) => {
                rows[i].prevRowType = row.rowType;
                rows[i].rowType = "D";
                return true;
              })
            this.setState(() => ({
                selected: ids
            }));
        } else {
            rows.map((row,i) => {
                rows[i].rowType = row.prevRowType;
                rows[i].prevRowType = "D";
                return true;
              })

            this.setState(() => ({
                selected: []
            }));
        }
    }

    
    /****************************************
    * 2. 이력 삭제 
    ****************************************/
    /* 2.1 삭제 - 테이블 목록에서 체크한 row들을 삭제하고 테이블을 재조회 한다.*/
    remoteBatchDelete(e){
        var randomGenAlgorithmHistList = this.state.randomGenAlgorithmHistList;
        var jsonArray =[];
        randomGenAlgorithmHistList = randomGenAlgorithmHistList.filter(function(item){
          return  (item.rowType === "D");
        });
        console.log("삭제;;;;;;",this.state.selected);
        
        for(let i = 0; i < this.state.selected.length; i++){
            jsonArray[i] = {"algorithmNo": this.state.selected[i]};
        }

        if(randomGenAlgorithmHistList.length === 0){
          alert("삭제 할 레코드를 선택해 주시기 바랍니다.");
        }else{
          const url = '/randomGeneration/removeRandomGenerationAlgorithmList/1';
          const data = { "removeList" : jsonArray };
          let body = {data : data};
          Util.deleteAxios(url, body, (msg, response) => {
            this.props.remoteViewTableList(this.state.pageNum, this.state.pageCnt);
          });
        }
        this.setState({selected: []})
    }
  
  
      /* 4.6 rowType: "U" 또는 rowType: "I"인 row를 authList에 담아준 뒤 Axios의 파라미터로 줌*/
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

    componentWillReceiveProps(nextProps) {
        console.log("algorithmNo", this.props.algorithmNo);
        this.setState({randomGenAlgorithmParamList: nextProps.randomGenAlgorithmParamList})
    }

    localAddRow(e){
        var seqNo;  //추가할 객체의 seqNo계산
        if(this.state.randomGenAlgorithmParamList.length <= 0) {
            seqNo = 1;
          } else {
            seqNo = this.state.randomGenAlgorithmParamList[this.state.randomGenAlgorithmParamList.length-1].rowSeqNo + 1;
        }
        const a = update(
            this.state.randomGenAlgorithmParamList,{
              $push: [{ 
                        level: 1,
                        algorithmTypeComment: null,
                        algorithmTypeName: "RandomInt",
                        generateDate: null,
                        generator: null,
                        rowType: "I",
                        prevRowType: "R",
                        rowSeqNo: seqNo
              }]
            }
          )
        this.setState({
            randomGenAlgorithmParamList: a
        });
        
      }
    /*********************************************
    * 3. 검색 조건 (검색, 상세검색, 검색값 초기화)
    *********************************************/
    /* 3.1 상세검색 - 상세 모달에서 선택한 검색 조건을 테이블에 넘겨줘서 테이블을 재조회 해준다.*/
    searchDetailCondition = (searchNum, searchResultCond, searchMessage, searchExpectedResultCond, searchExpectedDbResultCond) => {
        const url = '/randomGeneration//viewRandomGenerationAlgorithmList/11111111';
        const data = {
            params: {
                searchResultCond: searchResultCond,
                searchExpectedResultCond: searchExpectedResultCond,
                searchExpectedDbResultCond: searchExpectedDbResultCond,
                searchNum: searchNum,
                searchMessage: searchMessage,
                pageCnt: this.state.pageCnt,
                pageNum: this.state.pageNum,
                executionProjectNo: 10000002
            }
        };

        Util.processAxios(url, data, (msg, response) => {
            this.props.remoteViewTableList(this.state.pageNum, this.state.pageCnt, searchResultCond, searchExpectedResultCond, searchExpectedDbResultCond, searchNum, searchMessage);
        });
    }

    /* 3.2 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
    searchClear = () => {
        this.searchDetailCondition(null, null, null, null, null);
        this.setState({
          searchAlgorithmLevel: null,
          searchAlgorithmTypeComment: null,
          searchAlgorithmTypeName: null,
          searchGenerateDateFrom: null,
          searchGenerateDateTo: null,
          searchGenerator: null
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
            <i className="fa fa-search-plus" idname="details" onClick={() => { this.props.testResults(row.executionUuid)} } />
        )
    }

    levelDropdown =(cell, row) =>{
        return(
            <div>
                <select defaultValue={cell} onChange={(e) => {row.algorithmLevel = parseInt(e.target.value); this.localChangeRowType(row); }}>
                    <option value={1}>level 1</option>
                    <option value={2}>level 2</option>
                    <option value={3}>level 3</option>
                </select>
            </div>
        )
    }
    
    algorithmDropdown =(cell,row) =>{
        return(
            <div>
                <select defaultValue={cell} onChange={(e) => {row.algorithmTypeName = e.target.value; this.localChangeRowType(row);}}>
                    <option value={"RandomInt"}>RandomInt</option>
                    <option value={"RandomString"}>RandomString</option>
                    <option value={"RandomDouble"}>RandomDouble</option>
                    <option value={"TypeDefinedString"}>TypeDefinedString</option>
                    <option value={"SpecificSting"}>SpecificSting</option>
                    <option value={"RandomDate"}>RandomDate</option>
                    <option value={"DBReferencedNumber"}>DBReferencedNumber</option>
                </select>
            </div>
        )
    }
    /* 4.4 컬럼 정의 */
    columns = [
        {
            dataField: 'parameterName',
            text: 'Input 파라미터 명',
            editable: true,
            headerStyle: { width: '15%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'parameterDescription',
            text: '설명',
            editable: true,
            headerStyle: { width: '20%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'parameterType',
            text: '구분',
            editable: true,
            headerStyle: { width: '15%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'parameterExample',
            text: '예시',
            editable: true,
            headerStyle: { width: '15%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        
        {
            dataField: 'parameterDefaultValue',
            text: 'Default 값',
            editable: true,
            headerStyle: { width: '15%' },
            align: 'center',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        }
    ];

    render() {
        const randomGenAlgorithmParamList = this.state.randomGenAlgorithmParamList;
        console.log("render props",this.props.randomGenAlgorithmParamList);
        console.log("render state",this.state.randomGenAlgorithmParamList);

        /* row 선택값 react bootstrap table에 넣어주기 */
        const selectRow = {
            mode: 'checkbox',
            clickToSelect: false,
            selected: this.state.selected,
            onSelect:this.handleOnSelect,
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
                        alert('검색창에 실행 결과 메세지를 입력하세요.')
                    )
                }
            };
            return (
                <div className='search-bar'>
                    <input
                        className="form-control"
                        style={{ backgroundColor: 'white' }}
                        ref={n => input = n}
                        type="text"
                        placeholder="실행 결과 메세지를 검색하세요."
                    />
                    <Button key="simple-search" className={cx('icon-button')} onClick={handleClick}>
                        <i className={cx("fa fa-search")} />
                    </Button>
                </div>
            );
        };

        const { ClearSearchButton } = Search;
        //const listSuccess = [{id: '성공'}, {id: '실패'}]

        return (
            <div className="random-algorithm-parameter-register-table">
                <ToolkitProvider keyField="executionUuid"
                    data={randomGenAlgorithmParamList}
                    columns={this.columns}
                    search>
                    {props => (
                        <div className ="random-algorithm-parameter-register-table">
                            <div className="toolbar-row">
                                <MySearch {...props.searchProps} />
                                <div className='detail-search'>
                                    <Button key="detail-search" className='detail-button' onClick={this.handleShow}>상세</Button>
                                    <Modal className='myModal' show={this.state.show} onHide={this.handleClose} centered>
                                        <div className="modal-content">
                                        <Modal.Header className='modal-header' closeButton>
                                            <Modal.Title>상세 검색 조건</Modal.Title>
                                        </Modal.Header>
                                        <Modal.Body className='modal-body'>
                                            <div className="myModal-content">
                                                <div className='level-search'>
                                                    <label>레벨</label>
                                                    <select value={this.state.searchAlgorithmLevel} onChange={(e) => this.setState({ searchAlgorithmLevel: e.target.value })}>
                                                      <option value={1}>level 1</option>
                                                      <option value={2}>level 2</option>
                                                      <option value={3}>level 3</option>
                                                    </select>
                                                </div>
                                                <div className='fieldName-search'>
                                                    <label>필드유형이름</label>
                                                    <input type="text" id="searchAlgorithmTypeComment" placeholder="필드유형이름을 입력하세요."
                                                        defaultValue={this.state.searchAlgorithmTypeComment}
                                                        onChange={(e) => this.setState({ searchAlgorithmTypeComment: e.target.value })} />
                                                </div>
                                                <div className='level-search'>
                                                    <label>알고리즘</label>
                                                    <select value={this.state.searchAlgorithmTypeName} onChange={(e) => this.setState({ searchAlgorithmTypeName: e.target.value })}>
                                                    <option value={"RandomInt"}>RandomInt</option>
                                                    <option value={"RandomString"}>RandomString</option>
                                                    <option value={"RandomDouble"}>RandomDouble</option>
                                                    <option value={"TypeDefinedString"}>TypeDefinedString</option>
                                                    <option value={"SpecificSting"}>SpecificSting</option>
                                                    <option value={"RandomDate"}>RandomDate</option>
                                                    <option value={"DBReferencedNumber"}>DBReferencedNumber</option>
                                                    </select>
                                                </div>
                                                <div className='fieldName-search'>
                                                    <label>생성일자</label>
                                                    <input type="text" id="searchGenerateDateFrom" placeholder="시작일자를 입력하세요"
                                                        defaultValue={this.state.searchGenerator}
                                                        onChange={(e) => this.setState({ searchGenerator: e.target.value })} />
                                                    <input type="text" id="searchGenerateDateTo" placeholder="종료일자를 입력하세요"
                                                        defaultValue={this.state.searchGenerator}
                                                        onChange={(e) => this.setState({ searchGenerator: e.target.value })} />
                                                </div>
                                                <div className='fieldName-search'>
                                                    <label>생성자</label>
                                                    <input type="text" id="searchAlgorithmTypeGenerator" placeholder="생성자를 입력하세요"
                                                        defaultValue={this.state.searchGenerator}
                                                        onChange={(e) => this.setState({ searchGenerator: e.target.value })} />
                                                </div>
                                            </div>
                                        </Modal.Body>
                                        <Modal.Footer>
                                            <Button variant="secondary" onClick={this.handleClose}>
                                                닫기
                                    </Button>
                                            <Button variant="primary" onClick={(e) => this.searchDetailCondition(this.state.searchNum,
                                                this.state.searchResultCond,
                                                this.state.searchMessage,
                                                this.state.searchExpectedResultCond,
                                                this.state.searchExpectedDbResultCond)}>
                                                검색
                                    </Button>
                                        </Modal.Footer>
                                        </div>
                                    </Modal>
                                </div>
                                <div className="clear-search">
                                    <Button className='close-button' onClick={() => this.searchClear()}>
                                        Clear<i className={cx("fa fa-clear")} />
                                    </Button>
                                </div>
                                <div className='search-condition'>
                                    현재 검색 기준 |
                            <span className='search-criteria'>
                                        {this.state.searchAlgorithmLevel !== null ? "레벨: " + this.state.searchAlgorithmLevel : null}
                                    </span>
                                    <span className='search-criteria'>
                                        {this.state.searchAlgorithmTypeComment !== null ? "필드 유형: " + this.state.searchAlgorithmTypeComment: null}
                                    </span>
                                </div>
                            </div>
                            <RandomAlgorithmParameterRegisterToolBar {...this.state}
                                //testRun={this.props.testRun}
                                pageCnt={this.props.pageCnt}
                                from={this.props.from}
                                to={this.props.to}
                                totalCnt={this.props.totalCnt}
                                localOnChangeSelectValue={this.props.localOnChangeSelectValue}
                                localGoPage={this.props.localGoPage}
                                remoteBatchDelete = {this.remoteBatchDelete}
                                localAddRow = {this.localAddRow}
                                remoteBatchSave = {this.remoteBatchSave}
                                
                                //handleClickExport = {this.handleClickExport}
                                className='random-algorithm-parameter-register-toolbar' />
                            <BootstrapTable
                                //{...props.baseProps}
                                //{...this.state}
                                keyField='parameterNo'
                                data={randomGenAlgorithmParamList}
                                bordered={false}  //세로 줄 구분 삭제
                                columns={this.columns}
                                //pagination = {paginationFactory(options)}
                                selectRow={selectRow} //checkbox
                                //defaultSorted={this.defaultSorted}
                                rowStyle={(row, rowIndex) => (rowIndex % 2 === 0 ? { backgroundColor: 'rgba(245, 245, 245, 1)' } : { backgroundColor: 'rgba(250, 250, 250, 1)' })}
                                cellEdit = { cellEditFactory({  mode: 'click',  //클릭 시 cellEdit
                                blurToSave: true, //수정한 셀 내용 셀에 저장
                                afterSaveCell: (oldValue, newValue, row, column) =>{
                                  let valid = true;  //유효성 검사 여부
                                  if(oldValue !== newValue){ //이전 값과 현재 값이 바뀐 경우 유효성 검사 수행
                                    //valid = this.checkVaildation(oldValue, newValue, row, column);
                                    // if(!valid){
                                    //   alert("입력한 값이 유효하지 않습니다");
                                     //}
                                  }
                                   /*유효성 검사 후 수정 시 rowType==="U"로 바꿔줌 */
                                  /*새로 추가한 row는 seqNo를 비교해서 rowType변경하지 않음*/
                                  const seqNo = row.rowSeqNo; //row.rowSeqNo는 props여서 이벤트로 넘겨주려면 변수에 담아줌
                                  console.log(randomGenAlgorithmParamList[randomGenAlgorithmParamList.length-1]);
                                  if(oldValue !== newValue && seqNo < randomGenAlgorithmParamList[randomGenAlgorithmParamList.length-1].rowSeqNo && valid){
                                    console.log("이벤트 확인");
                                     this.localChangeRowType(seqNo);
                                  }
                                }
                            })}
                                />
                                
                        </div>
                    )}
                </ToolkitProvider>
                <div className={cx('footer')}>
                    <RandomAlgorithmFooter {...this.state} totalCnt={this.props.totalCnt}
                        pageCnt={this.props.pageCnt}
                        pageNum={this.props.pageNum}
                        from={this.props.from}
                        to={this.props.to}
                        remoteChangePageNum={this.props.remoteChangePageNum}
                        remoteChangePageCnt={this.props.remoteChangePageCnt}
                        localOnChangeSelectValue={this.props.localOnChangeSelectValue}
                        localGoPage={this.props.localGoPage} />
                </div>
            </div>
        )
    }
}

export default RandomAlgorithmParameterRegisterTable;

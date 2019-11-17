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
import RandomRuleFooter from 'components/main/unit/randomgenerate/rule/RandomRuleFooter';
import RandomRuleParameterEditToolBar from 'components/main/unit/randomgenerate/rule/RandomRuleParameterEditToolBar';
import styles from './RandomRuleParameterEditTable.scss';
import classNames from 'classnames/bind';
import { random } from 'node-forge';
const cx = classNames.bind(styles);
  
class RandomRuleParameterEditTable extends Component {
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
            randomGenFieldTypeParamList: this.props.randomGenFieldTypeParamList,
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
        const selectedList = [];
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
        this.setState({randomGenFieldTypeParamList: nextProps.randomGenFieldTypeParamList})
    }

    

    /* 3.3 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
    handleClose() {
        this.setState({ show: false });
    }

    /* 3.4 검색조건 초기화 - 테이블 목록에서 선택한 검색 조건들을 Clear 버튼을 눌러 초기화 시켜준다.*/
    handleShow() {
        this.setState({ show: true });
    }

    /* 4.4 컬럼 정의 */
    columns = [
        {
            dataField: 'parameterNo',
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
            dataField: 'parameterName',
            text: '파라미터 명',
            editable: false,
            headerStyle: { width: '10%' },
            align: 'left',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'parameterDescription',
            text: '설명',
            editable: false,
            headerStyle: { width: '20%' },
            align: 'left',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden'
            }
        },
        {
            dataField: 'parameterType',
            text: '구분',
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
            dataField: 'parameterExample',
            text: '예시',
            editable: false,
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
            text: '값',
            editable: true,
            headerStyle: { width: '15%' },
            align: 'left',
            style: {
                whiteSpace: 'nowrap',
                textOverflow: 'ellipsis',
                overflow: 'hidden',
            }
        }
    ];

    render() {
        const randomGenFieldTypeParamList = this.state.randomGenFieldTypeParamList;
        console.log("render props",this.props.randomGenFieldTypeParamList);
        console.log("render state",this.state.randomGenFieldTypeParamList);

        /* row 선택값 react bootstrap table에 넣어주기 */

        return (
            <div className="random-rule-parameter-edit-table">
                <ToolkitProvider keyField="executionUuid"
                    data={randomGenFieldTypeParamList}
                    columns={this.columns}
                    search>
                    {props => (
                        <div className ="random-rule-parameter-edit-table">
                            <br></br>
                            <RandomRuleParameterEditToolBar {...this.state}
                                //testRun={this.props.testRun}
                                pageCnt={this.props.pageCnt}
                                from={this.props.from}
                                to={this.props.to}
                                totalCnt={this.props.totalCnt}
                                localOnChangeSelectValue={this.props.localOnChangeSelectValue}
                                localGoPage={this.props.localGoPage}
                                remoteBatchSave = {this.remoteBatchSave}
                                
                                //handleClickExport = {this.handleClickExport}
                                className='random-rule-parameter-edit-tool-bar' />
                            <BootstrapTable
                                //{...props.baseProps}
                                //{...this.state}
                                keyField='parameterNo'
                                data={randomGenFieldTypeParamList}
                                bordered={false}  //세로 줄 구분 삭제
                                columns={this.columns}
                                //pagination = {paginationFactory(options)}
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
                                  console.log(randomGenFieldTypeParamList[randomGenFieldTypeParamList.length-1]);
                                  if(oldValue !== newValue && seqNo < randomGenFieldTypeParamList[randomGenFieldTypeParamList.length-1].rowSeqNo && valid){
                                    console.log("이벤트 확인");
                                     this.localChangeRowType(seqNo);
                                  }
                                  
                                },
                                  nonEditableRows: function() { //rowType=="D, 해당 row는 editable:false"
                                  return randomGenFieldTypeParamList.filter(p => p.rowType=== "D").map(p => p.rowSeqNo);
                                  }
                            })}
                                />
                                
                        </div>
                    )}
                </ToolkitProvider>
                <div className={cx('footer')}>
                    <RandomRuleFooter {...this.state} totalCnt={this.props.totalCnt}
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

export default RandomRuleParameterEditTable;

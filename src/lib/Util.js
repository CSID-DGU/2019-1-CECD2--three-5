/*
* 작성자 : 권오승 (2019.05.22)
* 내용 : axios 유틸리티 API
*/

import whaxios, {BASE_URL} from './whaxios';
import axios from 'axios';

export default {
  /* deprecated when 2019/09/01 by KOS */
  processAxios(url, data, callback) {
    this.getAxios(url, data, callback);
  },
  getAxios(url, data, callback) {
    whaxios.get(url, data).then(function(response){
      if(response.data.validationYN==='0'){
        callback("Validation Error.", response);
      } else if(response.data.serverLogicYN==='0'){
        callback("Server Logic Error", response);
      } else{
        callback(null, response);
      }
    }).catch(function(error){
      console.log("DEBUG::getAxios::response::")
      console.log(error);
      callback(JSON.stringify(error), null);
    }); //catch절 끝
  },
  postAxios(url, data, callback) {
    whaxios.post(url, data).then(function(response){
      if(response.data.validationYN==='0'){
        callback("Validation Error.", response);
      } else if(response.data.serverLogicYN==='0'){
        callback("Server Logic Error", response);
      } else{
        callback(null, response);
      }
    }).catch(function(error){
      console.log("DEBUG::postAxios::response::")
      console.log(error);
      callback(JSON.stringify(error), null);
    }); //catch절 끝
  },
  deleteAxios(url, data, callback) {
    whaxios.delete(url, data).then(function(response){
      if(response.data.validationYN==='0'){
        callback("Validation Error.", response);
      } else if(response.data.serverLogicYN==='0'){
        callback("Server Logic Error", response);
      } else{
        callback(null, response);
      }
    }).catch(function(error){
      console.log("DEBUG::deleteAxios::response::")
      console.log(error);
      callback(JSON.stringify(error), null);
    }); //catch절 끝
  },
}


export const insertUnitTestCase = ({testcaseName, testcaseGroupNo, testcaseDescription, generator, methodNo}) => axios.post(BASE_URL+'/testcase/addTestcase/1', {testcaseName, testcaseGroupNo, testcaseDescription, generator, methodNo} );
//export const getTestCaseJSON = (id) => axios.get(BASE_URL +'/testcase/modifyTestcase2/1?testcaseNo='+ id);
export const getTestCaseJSON = (id) => axios.get(BASE_URL +'/testcase/viewTestcaseEdit/1?testcaseNo='+ id);
export const updateTestCaseJSON = (json) => axios.put(BASE_URL +'/testcase/modifyTestcaseEdit/1', json);
export const getProjectTreeJSON = (id) => axios.get(BASE_URL + '/projectMng/viewProjectTreeList/1?projectNo='+ id);
export const TEMP_PROJECT_NO = 45;
export const TEMP_TESTCASE_NO = 13604;

//Random Rules
export const getRulesJSON = (id) => axios.get(BASE_URL +'/randomGenerate/viewRandomRuleFieldMapping/1?ruleNo='+ id);
export const getTypesJSON = (id) => axios.get(BASE_URL +'/pairwiseGenerate/viewPairwiseTypesFieldMapping/1?typesNo='+ id);

//export const URL_TESTCASE_EDIT = '/MainPage/TestCaseEdit';
export const URL_RANDOM_GENERATION = '/MainPage/UnitMainBody/RandomGenerate/Generation';
export const URL_RANDOM_ALGORITHM='/MainPage/UnitMainBody/RandomGenerate/algorithm';
export const URL_RANDOM_RULE='/MainPage/UnitMainBody/RandomGenerate/rule';
export const URL_RANDOM_FIELD='/MainPage/UnitMainBody/RandomGenerate/field';

/****페어와이즈 추가부분***** */
export const URL_PAIRWISE_GENERATION = '/MainPage/UnitMainBody/PairwiseGenerate/Generation';
export const URL_PAIRWISE_TYPE = '/MainPage/UnitMainBody/PairwiseGenerate/Type';
/****************/
export const URL_FILES_COLLECT = "/MainPage/UnitMainBody/FilesCollection";
export const URL_STRUCTS_COLLECT = "/MainPage/UnitMainBody/StructsCollection";
export const URL_SERVICE_RUN = '/MainPage/UnitMainBody/ServiceRun';
export const URL_TESTCASE_EDIT = '/MainPage/UnitMainBody/EditTCTemplate';

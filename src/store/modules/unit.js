import { Map, fromJS } from 'immutable';
import {handleActions, createAction} from 'redux-actions';
// import * as api from 'lib/api';
import * as api from 'lib/Util';
import { pender } from 'redux-pender/lib/utils';
import Util from '../../lib/Util';



const INITIALIZE = 'unit/INITIALIZE';
export const initialize = createAction(INITIALIZE);

const UPDATE_STATE = 'unit/UPDATE_STATE';
export const updateState = createAction(UPDATE_STATE);

const INSERT_UNIT_TEST_CASE = 'unit/INSERT_UNIT_TEST_CASE'
export const insertUnitTestCase = createAction(INSERT_UNIT_TEST_CASE, api.insertUnitTestCase);

const GET_PROJECT_TREE = "unit/GET_PROJECT_TREE";
export const getProjectTreeJSON = createAction(GET_PROJECT_TREE, api.getProjectTreeJSON);

const initialState = Map({
    testcaseName: '',
    testcaseGroupNo: 1234,
    testcaseDescription: '',
    generator: '',
    testcaseNo: 0,
    methodNo: 13203,

    show: false,
    showTCGroupFinder: false

    // projectTreeJSON: Map({}),
    // projectTree:Map({})

});

export default handleActions({
    [INITIALIZE]: (state, action) => initialState,
    [UPDATE_STATE]: (state, action) => {
        const {name, value} = action.payload;
        return state.set(name, value);
    },
    ...pender({
        type: INSERT_UNIT_TEST_CASE
    })
    // ,
    // ...pender({
    //     type: GET_PROJECT_TREE,
    //     onSuccess: (state, action) => {
    //         const { data: projectTreeJSON } = action.payload;
    //         // console.log("projecttree @pender")
    //         // console.log(projectTree);

    //         return state.set('projectTreeJSON', fromJS(projectTreeJSON));
    //     }
    // })

}, initialState);

// const AddUnitTestCase = ({handleSubmit, setTestCaseDescription, setTestCaseName, setGenerator,}) => {


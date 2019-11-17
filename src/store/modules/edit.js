import { Map, fromJS } from 'immutable';
import { handleActions, createAction } from 'redux-actions';
import * as api from 'lib/Util';
import { pender } from 'redux-pender/lib/utils';




const GET_TC_DATA = 'edit/GET_TC_DATA';
export const getTestCaseJSON = createAction(GET_TC_DATA, api.getTestCaseJSON);

const UPDATE_STATE = 'edit/UPDATE_STATE';
export const updateState = createAction(UPDATE_STATE, api.updateTestCaseJSON);


const initialState = Map({
    testCaseData: {},
    testCaseDetail: {}
});

export default handleActions({
    [UPDATE_STATE]: (state, action) => {
        const {testCaseDetail} = action.payload;

        return state.set("testCaseDetail",testCaseDetail);
    },
    ...pender({
        type: GET_TC_DATA,
        onSuccess: (state, action) => {
            const { data: testCaseData } = action.payload;
            console.log("edit.js:testCaseData=>");
            console.log(testCaseData);
            return state.set('testCaseData', testCaseData.body.testcaseEdit);
        }
    }),


}, initialState);

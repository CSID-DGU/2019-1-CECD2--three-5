import { Map, fromJS } from 'immutable';
import { handleActions, createAction } from 'redux-actions';
import * as api from 'lib/Util';
import { pender } from 'redux-pender/lib/utils';




const GET_RULES_DATA = 'randomRules/GET_RULES_DATA';
export const getRulesJSON = createAction(GET_RULES_DATA, api.getRulesJSON);

const UPDATE_RULES_STATE = 'randomRules/UPDATE_RULES_STATE';
export const updateState = createAction(UPDATE_RULES_STATE);


const initialState = Map({
    rulesData: {},
    rulesDataDetail: {}
});

export default handleActions({
    [UPDATE_RULES_STATE]: (state, action) => {
        const {rulesDataDetail} = action.payload;
        console.log("randomrules.js:rulesData=>");
        return state.set("rulesDataDetail",rulesDataDetail);
    },
    ...pender({
        type: GET_RULES_DATA,
        onSuccess: (state, action) => {
            const { data: rulesData } = action.payload;
            console.log("randomRules.js:rulesData=>");
            console.log(rulesData);
            return state.set('rulesData', rulesData.body.randomRuleFieldMapping);
        }
    }),


}, initialState);

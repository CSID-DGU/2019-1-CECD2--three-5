import { Map, fromJS } from 'immutable';
import { handleActions, createAction } from 'redux-actions';
import * as api from 'lib/Util';
import { pender } from 'redux-pender/lib/utils';




const GET_TYPES_DATA = 'pairwiseTypes/GET_TYPES_DATA';
export const getTypesJSON = createAction(GET_TYPES_DATA, api.getTypesJSON);

const UPDATE_TYPES_STATE = 'pairwiseTypes/UPDATE_TYPES_STATE';
export const updateState = createAction(UPDATE_TYPES_STATE);


const initialState = Map({
    typesData: {},
    typesDataDetail: {}
});

export default handleActions({
    [UPDATE_TYPES_STATE]: (state, action) => {
        const {typesDataDetail} = action.payload;
        return state.set("typesDataDetail",typesDataDetail);
    },
    ...pender({
        type: GET_TYPES_DATA,
        onSuccess: (state, action) => {
            const { data: typesData } = action.payload;
            return state.set('typesData', typesData.body.pairwiseTypeFieldMapping);
        }
    }),


}, initialState);

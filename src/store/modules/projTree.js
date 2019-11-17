import { Map, fromJS } from 'immutable';
import {handleActions, createAction} from 'redux-actions';
import * as api from 'lib/Util';
import { pender } from 'redux-pender/lib/utils';


const UPDATE_NODE_PATH = 'projTree/UPDATE_NODE_PATH';
export const updateNodePath = createAction(UPDATE_NODE_PATH);

const UPDATE_TREE = 'projTree/UPDATE_TREE';
export const updateTree =  createAction(UPDATE_TREE);


const GET_PROJECT_TREE = "projTree/GET_PROJECT_TREE";
export const getProjectTreeJSON = createAction(GET_PROJECT_TREE, api.getProjectTreeJSON);


const initialState = Map({
 
    projectTreeJSON: Map({}),
    nodeToUpdate: ""

 
});


export default handleActions({
    [UPDATE_NODE_PATH]: (state, action) => {
        const { nodeToUpdate} = action.payload;
        return state.set("nodeToUpdate", nodeToUpdate);
    },
    [UPDATE_TREE]: (state, action) => {
        const projectTreeJSON = action.payload;
        return state.set("projectTreeJSON", projectTreeJSON);
    },
    ...pender({
        type: GET_PROJECT_TREE,
        onSuccess: (state, action) => {
            const { data: projectTreeJSON } = action.payload;
            // console.log("projecttree @pender")
            // console.log(projectTreeJSON);

            return state.set('projectTreeJSON', fromJS(projectTreeJSON.body.projectTreeList));
        }
    })

}, initialState);

// const AddUnitTestCase = ({handleSubmit, setTestCaseDescription, setTestCaseName, setGenerator,}) => {


import { createAction, handleActions } from 'redux-actions';

import { Map } from 'immutable';
import { pender } from 'redux-pender';

const STORE_USERINFO = 'login/STORE_USERINFO';

export const storeUserInfo = createAction(STORE_USERINFO);

const initialState= Map({
  userName: '',
  userRole: ''
});

export default handleActions({
  [STORE_USERINFO]: (state, action) => {
    const {name, value} = action.payload;
    return state.set(name,value);
  }
}, initialState)

import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import App from './App';
import {Provider} from 'react-redux';
import configure from 'store/configure';

const store = configure();

const Root = () =>{
  return (
    <Provider store={store}>
      <Router>
        <App />
      </Router>
    </Provider>
  )
}

export default Root;

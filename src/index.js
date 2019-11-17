import React from 'react';
import ReactDOM from 'react-dom';

import * as serviceWorker from './serviceWorker';
import Root from './Root';

ReactDOM.render(
  <Root />,
   document.getElementById('servietester-root')
 );

serviceWorker.unregister();

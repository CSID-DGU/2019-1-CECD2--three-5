import axios from 'axios';

export const BASE_URL = 'http://10.70.3.133:9000';
//export const BASE_URL = 'http://localhost:9000';

var whaxios = axios.create({
  baseURL: BASE_URL,
  /* other custom settings */
});

export default  whaxios;

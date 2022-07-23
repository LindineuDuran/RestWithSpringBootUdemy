import axios from 'axios';

const api = axios.create({
    baseURL: 'http://15.228.236.7:8080'
});

export default api;
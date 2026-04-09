import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/bank',
});

let authToken = null;

export function setAuthToken(token) {
    authToken = token;
}

api.interceptors.request.use((config) => {
    if (authToken) {
        config.headers.Authorization = `Bearer ${authToken}`;
    }
    return config;
});

export default api;
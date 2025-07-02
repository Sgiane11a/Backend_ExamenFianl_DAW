// src/services/api.js
import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api', // La URL base de tu API
    headers: {
        'Content-Type': 'application/json'
    }
});

// Interceptor para añadir el token a cada solicitud
apiClient.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

export default apiClient;
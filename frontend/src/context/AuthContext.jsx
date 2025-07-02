// src/context/AuthContext.jsx
import React, { createContext, useState, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';
import apiClient from '../services/api';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem('token'));

    useEffect(() => {
        if (token) {
            try {
                const decodedToken = jwtDecode(token);
                // Extraer el rol del token (asumiendo que está en 'roles')
                const roles = decodedToken.roles.split(',');
                setUser({
                    username: decodedToken.sub,
                    roles: roles
                });
            } catch (error) {
                console.error("Token inválido:", error);
                logout();
            }
        }
    }, [token]);

    const login = async (username, password) => {
        try {
            const response = await apiClient.post('/auth/login', { username, password });
            const newToken = response.data.token;
            localStorage.setItem('token', newToken);
            setToken(newToken);
            return true;
        } catch (error) {
            console.error('Error de inicio de sesión:', error);
            return false;
        }
    };

    const logout = () => {
        localStorage.removeItem('token');
        setUser(null);
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContext;
// src/components/ProtectedRoute.jsx
import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import AuthContext from '../context/AuthContext';

const ProtectedRoute = ({ children, adminOnly = false }) => {
    const { user } = useContext(AuthContext);

    if (!user) {
        return <Navigate to="/login" />;
    }

    if (adminOnly && !user.roles.includes('ROLE_ADMIN')) {
        return <Navigate to="/peliculas" />; // O a una página de "no autorizado"
    }

    return children;
};

export default ProtectedRoute;
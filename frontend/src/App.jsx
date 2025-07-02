// src/App.jsx
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';
import LoginPage from './pages/LoginPage';
import PeliculasPage from './pages/PeliculasPage';
import GenerosPage from './pages/GenerosPage';

function App() {
    return (
        <AuthProvider>
            <Router>
                <Navbar />
                <Routes>
                    <Route path="/login" element={<LoginPage />} />

                    <Route
                        path="/peliculas"
                        element={
                            <ProtectedRoute>
                                <PeliculasPage />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/generos"
                        element={
                            <ProtectedRoute adminOnly={true}>
                                <GenerosPage />
                            </ProtectedRoute>
                        }
                    />

                    {/* Redirige la ruta raíz a /peliculas */}
                    <Route path="/" element={<Navigate to="/peliculas" />} />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;
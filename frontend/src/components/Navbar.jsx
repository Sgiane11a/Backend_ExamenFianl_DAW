// src/components/Navbar.jsx
import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import AuthContext from '../context/AuthContext';

const Navbar = () => {
    const { user, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container-fluid">
                <Link className="navbar-brand" to="/">PelisTecsup</Link>
                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        {user && (
                            <>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/peliculas">Películas</Link>
                                </li>
                                {user.roles.includes('ROLE_ADMIN') && (
                                    <li className="nav-item">
                                        <Link className="nav-link" to="/generos">Gestionar Géneros</Link>
                                    </li>
                                )}
                            </>
                        )}
                    </ul>
                    {user ? (
                        <div className="d-flex align-items-center">
                            <span className="navbar-text me-3">
                                Hola, {user.username} ({user.roles.includes('ROLE_ADMIN') ? 'Admin' : 'User'})
                            </span>
                            <button className="btn btn-outline-light" onClick={handleLogout}>Logout</button>
                        </div>
                    ) : (
                        <Link className="btn btn-outline-light" to="/login">Login</Link>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
// src/pages/GenerosPage.jsx
import React, { useState, useEffect } from 'react';
import apiClient from '../services/api';

const GenerosPage = () => {
    const [generos, setGeneros] = useState([]);
    const [nuevoGenero, setNuevoGenero] = useState({ nombre: '' });

    useEffect(() => {
        fetchGeneros();
    }, []);

    const fetchGeneros = async () => {
        try {
            const response = await apiClient.get('/generos');
            setGeneros(response.data);
        } catch (error) {
            console.error("Error fetching generos:", error);
        }
    };

    const handleInputChange = (e) => {
        setNuevoGenero({ ...nuevoGenero, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await apiClient.post('/generos', nuevoGenero);
            fetchGeneros();
            setNuevoGenero({ nombre: '' });
        } catch (error) {
            console.error("Error creating genero:", error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('¿Estás seguro de que quieres borrar este género? Esto puede afectar a las películas asociadas.')) {
            try {
                await apiClient.delete(`/generos/${id}`);
                fetchGeneros();
            } catch (error) {
                alert("No se puede borrar el género porque está siendo usado por una o más películas.");
                console.error("Error deleting genero:", error);
            }
        }
    };

    return (
        <div className="container mt-4">
            <h2>Gestionar Géneros</h2>
            <div className="card my-4">
                <div className="card-header"><h3>Añadir Nuevo Género</h3></div>
                <div className="card-body">
                    <form onSubmit={handleSubmit}>
                        <div className="input-group">
                            <input
                                type="text"
                                name="nombre"
                                value={nuevoGenero.nombre}
                                onChange={handleInputChange}
                                className="form-control"
                                placeholder="Nombre del género"
                                required
                            />
                            <button type="submit" className="btn btn-primary">Añadir</button>
                        </div>
                    </form>
                </div>
            </div>

            <ul className="list-group">
                {generos.map(genero => (
                    <li key={genero.id} className="list-group-item d-flex justify-content-between align-items-center">
                        {genero.nombre}
                        <button onClick={() => handleDelete(genero.id)} className="btn btn-danger btn-sm">Borrar</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default GenerosPage;
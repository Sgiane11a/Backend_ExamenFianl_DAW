// src/pages/PeliculasPage.jsx
import React, { useState, useEffect, useContext } from 'react';
import apiClient from '../services/api';
import AuthContext from '../context/AuthContext';

const PeliculasPage = () => {
    const [peliculas, setPeliculas] = useState([]);
    const [generos, setGeneros] = useState([]);
    const [nuevaPelicula, setNuevaPelicula] = useState({ titulo: '', director: '', fechaLanzamiento: '', genero: { id: '' } });
    const { user } = useContext(AuthContext);
    const isAdmin = user && user.roles.includes('ROLE_ADMIN');

    useEffect(() => {
        fetchPeliculas();
        if (isAdmin) {
            fetchGeneros();
        }
    }, [isAdmin]);

    const fetchPeliculas = async () => {
        try {
            const response = await apiClient.get('/peliculas');
            setPeliculas(response.data);
        } catch (error) {
            console.error('Error fetching peliculas:', error);
        }
    };

    const fetchGeneros = async () => {
        try {
            const response = await apiClient.get('/generos');
            setGeneros(response.data);
        } catch (error) {
            console.error('Error fetching generos:', error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        if (name === 'generoId') {
            setNuevaPelicula({ ...nuevaPelicula, genero: { id: value } });
        } else {
            setNuevaPelicula({ ...nuevaPelicula, [name]: value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await apiClient.post('/peliculas', nuevaPelicula);
            fetchPeliculas();
            setNuevaPelicula({ titulo: '', director: '', fechaLanzamiento: '', genero: { id: '' } });
        } catch (error) {
            console.error('Error creating pelicula:', error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('¿Estás seguro de que quieres borrar esta película?')) {
            try {
                await apiClient.delete(`/peliculas/${id}`);
                fetchPeliculas();
            } catch (error) {
                console.error('Error deleting pelicula:', error);
            }
        }
    };

    return (
        <div className="container mt-4">
            <h2>Lista de Películas</h2>
            {isAdmin && (
                <div className="card my-4">
                    <div className="card-header">
                        <h3>Añadir Nueva Película</h3>
                    </div>
                    <div className="card-body">
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <input type="text" name="titulo" value={nuevaPelicula.titulo} onChange={handleInputChange} placeholder="Título" className="form-control" required />
                            </div>
                            <div className="mb-3">
                                <input type="text" name="director" value={nuevaPelicula.director} onChange={handleInputChange} placeholder="Director" className="form-control" required />
                            </div>
                            <div className="mb-3">
                                <input type="date" name="fechaLanzamiento" value={nuevaPelicula.fechaLanzamiento} onChange={handleInputChange} className="form-control" required />
                            </div>
                            <div className="mb-3">
                                <select name="generoId" value={nuevaPelicula.genero.id} onChange={handleInputChange} className="form-select" required>
                                    <option value="">Selecciona un Género</option>
                                    {generos.map(g => (
                                        <option key={g.id} value={g.id}>{g.nombre}</option>
                                    ))}
                                </select>
                            </div>
                            <button type="submit" className="btn btn-primary">Añadir Película</button>
                        </form>
                    </div>
                </div>
            )}
            <table className="table table-striped">
                <thead>
                <tr>
                    <th>Título</th>
                    <th>Director</th>
                    <th>Fecha de Lanzamiento</th>
                    <th>Género</th>
                    {isAdmin && <th>Acciones</th>}
                </tr>
                </thead>
                <tbody>
                {peliculas.map(pelicula => (
                    <tr key={pelicula.id}>
                        <td>{pelicula.titulo}</td>
                        <td>{pelicula.director}</td>
                        <td>{pelicula.fechaLanzamiento}</td>
                        <td>{pelicula.genero ? pelicula.genero.nombre : 'N/A'}</td>
                        {isAdmin && (
                            <td>
                                <button onClick={() => handleDelete(pelicula.id)} className="btn btn-danger btn-sm">Borrar</button>
                            </td>
                        )}
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default PeliculasPage;
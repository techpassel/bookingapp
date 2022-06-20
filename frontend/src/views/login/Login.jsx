import axios from 'axios';
import React from 'react'
import { useContext } from 'react';
import { useEffect } from 'react';
import { useState } from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { queryStringToObject } from '../../utils/CommonUtils';
import './Login.scss'

const Login = () => {
    const baseApiUrl = process.env.REACT_APP_BASE_API_URL;
    const [credentials, setCredentials] = useState({
        username: undefined,
        password: undefined
    });
    const { user, loading, error, dispatch } = useContext(AuthContext);
    const navigate = useNavigate();
    const location = useLocation();
    const queryStringData = queryStringToObject(location.search);

    useEffect(() => {
        if (user && user.name) {
            navigate(queryStringData.redirect ? '/' + queryStringData.redirect : '/')
        }
    }, [user])

    const handleChange = (e) => {
        setCredentials(prev => ({ ...prev, [e.target.id]: e.target.value }))
    }

    const handleLogin = async (e) => {
        e.preventDefault();
        dispatch({ type: 'LOGIN_START' })
        try {
            const res = await axios.post(baseApiUrl + '/auth/login', credentials);
            dispatch({ type: 'LOGIN_SUCCESS', payload: res.data })
            // navigate(queryStringData.redirect ? '/' + queryStringData.redirect : '/')
        } catch (err) {
            dispatch({ type: 'LOGIN_FAILURE', payload: err.response.data })
        }
    }

    return (
        <div className="login">
            <div className="lContainer">
                <input type="text" placeholder='Username' id="username" onChange={handleChange} className="lInput" />
                <input type="password" placeholder='Password' id="password" onChange={handleChange} className="lInput" />
                <button type='submit' disabled={loading} onClick={handleLogin} className="lButton">Login</button>
                {error && <span className='errorMsg'>{error}</span>}
            </div>
        </div>
    )
}

export default Login
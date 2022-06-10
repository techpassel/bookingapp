/* eslint-disable react-hooks/exhaustive-deps */
import { useState, useEffect } from 'react';
import axios from 'axios';

const useFetch = (url) => {
    const baseApiUrl = process.env.REACT_APP_BASE_API_URL;
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);
    const token = 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhc2F1cmFiaDAwMUBnbWFpbC5jb20iLCJpYXQiOjE2NTQ4ODMwNzgsImV4cCI6MTY1NDk3MzA3OH0.Jh7uJ5Dzg-XoN_ohus8ra_A7tbFGBX0dqzmjmQpWYW0va2LIL9WILah8OdYl0ZrmnQbOOHsfOEpxHgBKdzVZlRe89IZoI3l3UpGr-aXVaJY2iM6YVsM34C69G6UD5EYOxeZB6f39Oo1HIFAGQOBWUqlgONfZoFc0X4ZthER1t5BmTGGwxe_UiaH3et7ynYU1dA8gLWO8Jd2fykhNepVzT75Qr3Q7GORXOZQicASF9ltONYX4tU9XkUbv1diE05CRQ5XdGDy8p2SgTISzW-DEkDTcL4YPlC6v1JoDyxIXqnnolw-OnW8P7xbMW8cqisPrfvdbddWwf3rcRgkbufVXxQ';
    const header = { Authorization: `Bearer ${token}` }

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const res = await axios.get(baseApiUrl + url, { headers: header });
                setData(res.data);
            } catch (error) {
                setError(error)
            }
            setLoading(false);
        }
        //Calling the above defined function. 
        fetchData();
    }, [baseApiUrl, url])

    const reFetch = async () => {
        setLoading(true);
        try {
            const res = await axios.get(baseApiUrl + url, { headers: header });
            setData(res.data);
        } catch (error) {
            setError(error)
        }
        setLoading(false);
    }
    return { data, loading, error, reFetch }
}

export default useFetch;
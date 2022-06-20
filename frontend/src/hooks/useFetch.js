/* eslint-disable react-hooks/exhaustive-deps */
import { useState, useEffect } from 'react';
import axios from 'axios';

const useFetch = (url, requestData = {}, method = 'GET') => {
    const baseApiUrl = process.env.REACT_APP_BASE_API_URL;
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);
    const token = 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhc2F1cmFiaDAwMUBnbWFpbC5jb20iLCJpYXQiOjE2NTQ5NzM5MjAsImV4cCI6MTY2Mzk3MzkyMH0.KHh8aPX3LqpMj57z97UmhBt85Tz2xKtNxDvwwmbQfEV75jNo35P9-06jPgPZnEKik1f25ku_B6YXUO-9186nx88ljcmIuRHQ4oCAUK4kmoojUS3T_xabWgV-ti2ahCvxz1bJExhRuqWcuxG0TOzFnvHqwxvS15btI2gkOTtbU7VMUcwJm1DsMC7yu100pVNydBf0-9Y0BhXzPJ_GlvDfwJauQJRGjX3fRoqdF4uTwrtgpNcmBswm4JXwofBMJVeXc2OXfE6kiZYQMUy1e4Eg8r98yw5D8LZWSrC8pL-XjmMrH7gFye-8Kk1AHpnEiYcQ0-eMFWFbAobWbgVtC4UjvQ';
    const header = { Authorization: `Bearer ${token}` }

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                let res;
                if (Object.keys(requestData).length > 0) {
                    res = await axios(baseApiUrl + url, { headers: header, data: requestData, method: method });
                } else {
                    res = await axios(baseApiUrl + url, { headers: header, method: method });
                }
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
            let res;
            if (Object.keys(requestData).length > 0) {
                res = await axios(baseApiUrl + url, { headers: header, data: requestData, method: method });
            } else {
                res = await axios(baseApiUrl + url, { headers: header, method: method });
            }
            setData(res.data);
        } catch (error) {
            setError(error)
        }
        setLoading(false);
    }
    return { data, loading, error, reFetch }
}

export default useFetch;
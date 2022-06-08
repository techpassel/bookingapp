import { useState, useEffect } from 'react';
import axios from 'axios';

const useFetch = (url) => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);

    const token = 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhc2F1cmFiaDAwMUBnbWFpbC5jb20iLCJpYXQiOjE2NTQ3MDg0MTcsImV4cCI6MTY1NDc5ODQxN30.aDOzNCw_9bX7Imi38hJMwNTZamTKhFVN_uki9VjrlV_7vsfE2L0Vfy6AQdp70MFyoqZsSEdxUrKxFp8WGZs1Xe7BljgnFfU6TG5__Fgj8BrbHSS2w7jqJzV7OqegdAzX4wE29FuTEImjhDAFvAVBolCyCnSfSxxSoud7J3hhewx3NULfkjiimAGM92VdZxBz3emkJISfPhgAxiHdX8m2WVVRhoxxoFIYQQgfTzYCzr7X2-fFlvJji2TPMav8XcyexYY-kHpaEqVDZ0iG0lNi86Vwmo7mC1kXTl3b-EmX2hg4rnM9zWPXy7IbqVGsCD5e-pXhGQSNqa6fNH5SSGRHpw';

    const header = { Authorization: `Bearer ${token}` }

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const res = await axios.get(url, { headers: header });
                setData(res.data);
            } catch (error) {
                setError(error)
            }
            setLoading(false);
        }
        //Calling the above defined function. 
        fetchData();
    }, [url])

    const reFetch = async () => {
        setLoading(true);
        try {
            const res = await axios.get(url, { headers: header });
            setData(res.data);
        } catch (error) {
            setError(error)
        }
        setLoading(false);
    }

    return { data, loading, error, reFetch }
}

export default useFetch;
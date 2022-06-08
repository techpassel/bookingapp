import React from 'react'
import useFetch from '../../hooks/useFetch'
import './Featured.scss'

const Featured = () => {
    const { data, loading, error, reFetch } = useFetch("/hotel/count-by-city?cities=Noida, Bangalore, Patna");
    return (
        <div className="featured">
            {false ? "Loading please wait..." : <>
                <div className="featuredItem">
                    <img src="https://cf.bstatic.com/xdata/images/city/max500/957801.webp?k=a969e39bcd40cdcc21786ba92826063e3cb09bf307bcfeac2aa392b838e9b7a5&o="
                        alt="" className="featuredImg" />
                    <div className="featuredTitles">
                        <h1>Noida</h1>
                        <h2>{data['Noida']} properties</h2>
                    </div>
                </div>
                <div className="featuredItem">
                    <img
                        src="https://cf.bstatic.com/xdata/images/city/max500/690334.webp?k=b99df435f06a15a1568ddd5f55d239507c0156985577681ab91274f917af6dbb&o="
                        alt=""
                        className="featuredImg"
                    />
                    <div className="featuredTitles">
                        <h1>Bangalore</h1>
                        <h2>{data['Bangalore']} properties</h2>
                    </div>
                </div>
                <div className="featuredItem">
                    <img
                        src="https://cf.bstatic.com/xdata/images/city/max500/689422.webp?k=2595c93e7e067b9ba95f90713f80ba6e5fa88a66e6e55600bd27a5128808fdf2&o="
                        alt=""
                        className="featuredImg"
                    />
                    <div className="featuredTitles">
                        <h1>Patna</h1>
                        <h2>{data['Patna']} properties</h2>
                    </div>
                </div>
            </>}
        </div>
    )
}

export default Featured
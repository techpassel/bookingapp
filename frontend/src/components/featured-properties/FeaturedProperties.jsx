import React from 'react'
import useFetch from '../../hooks/useFetch'
import { formatCurrency, getRatingRemark } from '../../utils/CommonUtils';
import './FeaturedProperties.scss'

const FeaturedProperties = () => {
    const { data, loading, error } = useFetch("/hotel/featured/5");
    const imageBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;
    const defaultImage = "https://cf.bstatic.com/xdata/images/hotel/square600/13125860.webp?k=e148feeb802ac3d28d1391dad9e4cf1e12d9231f897d0b53ca067bde8a9d3355&o=&s=1";
    return (
        <div className="fp">
            {loading ? "Loading please wait..." : <>
                {data.length > 0 && data.map((item, i) => (
                    <div className="fpItem" key={i}>
                        <img src={item.images.length > 0 ? imageBaseUrl + item.images[0] : defaultImage} alt="" className="fpImg" />
                        <span className="fpName">{item.name}</span>
                        <span className="fpCity">{item.city}</span>
                        <span className="fpPrice">Starting from {formatCurrency(item.minPrice)}</span>
                        {item.rating && (<div className="fpRating">
                            <button>{item.rating}</button>
                            <span>{getRatingRemark(item.rating)}</span>
                        </div>)}
                    </div>
                ))}
            </>}
        </div>
    )
}

export default FeaturedProperties
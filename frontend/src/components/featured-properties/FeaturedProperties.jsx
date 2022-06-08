import React from 'react'
import useFetch from '../../hooks/useFetch'
import { formatCurrency, getRatingRemark } from '../../utils/CommonUtils';
import './FeaturedProperties.scss'

const FeaturedProperties = () => {
    const { data, loading, error } = useFetch("/hotel/featured/4");
    return (
        <div className="fp">
            {loading ? "loading please wait..." : <>
                {data.length > 0 && data.map((item, i) => (
                    <div className="fpItem">
                        <img src="https://cf.bstatic.com/xdata/images/hotel/square600/13125860.webp?k=e148feeb802ac3d28d1391dad9e4cf1e12d9231f897d0b53ca067bde8a9d3355&o=&s=1" alt="" className="fpImg" />
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
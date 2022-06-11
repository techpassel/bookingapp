import React from 'react'
import { Link } from 'react-router-dom';
import { formatCurrency, getRatingRemark } from '../../utils/CommonUtils';
import './SearchItem.scss'

const SearchItem = ({ item }) => {
    const defaultImage = "https://cf.bstatic.com/xdata/images/hotel/square600/261707778.webp?k=fa6b6128468ec15e81f7d076b6f2473fa3a80c255582f155cae35f9edbffdd78&o=&s=1";
    const imageBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;

    return (
        <div className="searchItem">
            <img src={item.images.length > 0 ? imageBaseUrl + item.images[0] : defaultImage} alt="" className="siImg" />
            <div className='siInfo'>
                <div className="siDesc">
                    <h1 className="siName">{item.name}</h1>
                    <span className="siDistance">{item.distance}</span>
                    <span className="siTaxiOp">Free airport taxi</span>
                    <span className="siTitle">
                        {item.title}
                    </span>
                    <span className="siFeatures">
                        {item.description}
                    </span>
                    <span className="siCancelOp">Free cancellation </span>
                    <span className="siCancelOpSubtitle">
                        You can cancel later, so lock in this great price today!
                    </span>
                </div>
                <div className="siDetails">
                    {item.rating && (<div className="siRating">
                        <span>{getRatingRemark(item.rating)}</span>
                        <button>{item.rating}</button>
                    </div>)}
                    <div className="siDetailTexts">
                        <span className="siPrice">{formatCurrency(item.minPrice)}</span>
                        <span className="siTaxOp">Includes taxes and fees</span>
                        <Link to={`/hotel/${item.id}`}><button className="siCheckButton">See availability</button></Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SearchItem
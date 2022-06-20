import React from 'react'
import Navbar from '../../components/navbar/Navbar'
import Header from '../../components/header/Header'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCircleArrowLeft, faCircleArrowRight, faCircleXmark, faLocationDot } from '@fortawesome/free-solid-svg-icons'
import './Hotel.scss'
import MailList from '../../components/mail-list/MailList'
import Footer from '../../components/footer/Footer'
import { useState } from 'react'
import useFetch from '../../hooks/useFetch'
import { useLocation } from 'react-router-dom'
import { formatCurrency } from '../../utils/CommonUtils'
// Temporary setup for testing
import img1 from '../../images/101.jpg';
import img2 from '../../images/102.jpg';
import img3 from '../../images/103.jpg';
import img4 from '../../images/104.jpg';
import img5 from '../../images/105.jpg';
import img6 from '../../images/106.jpg';
import { useEffect } from 'react'
import { useContext } from 'react'
import { SearchContext } from '../../context/SearchContext'
import moment from 'moment'

const Hotel = () => {
  const [photos, setPhotos] = useState([]);
  const [slideNumber, setSlideNumber] = useState(0);
  const [openSlider, setOpenSlider] = useState(false);
  const imageBaseUrl = process.env.REACT_APP_IMAGE_BASE_URL;

  const handleSlideOpen = (index) => {
    if (!openSlider) setOpenSlider(true);
    setSlideNumber(index);
  }

  const handleSliderMove = (arg) => {
    switch (arg) {
      case "l":
        if (slideNumber > 0) {
          setSlideNumber(slideNumber - 1);
        }
        break;
      case "r":
        if (slideNumber < (photos.length - 1)) {
          setSlideNumber(slideNumber + 1);
        }
        break;
      default:
        break;
    }
  }
  const location = useLocation();
  const id = location.pathname.split("/")[2];
  const { data, loading, error, reFetch } = useFetch(`/hotel/${id}`);
  const { dates, options } = useContext(SearchContext);
  const numDays = moment(dates[0]?.endDate).diff(moment(dates[0]?.startDate), 'days');
  useEffect(() => {
    if (data.images) {
      if (data.images.length > 1) {
        setPhotos(data.images.map(e => imageBaseUrl + e));
      } else {
        setPhotos([img1, img2, img3, img4, img5, img6]);
      }
    }
  }, [data.images, imageBaseUrl])

  const handleClick = () => {
    console.log("handle click called");
  }

  return (
    <div>
      <Navbar />
      <Header type="list" />
      {loading ? "Loading. Please wait..." : (<div className="hotelContainer">
        {openSlider && <div className="slider">
          <FontAwesomeIcon icon={faCircleXmark} className="close" onClick={() => setOpenSlider(false)} />
          <FontAwesomeIcon icon={faCircleArrowLeft} className={slideNumber !== 0 ? 'arrow' : 'arrowDisabled'} onClick={() => handleSliderMove("l")} />
          <div className="sliderWrapper">
            <img src={photos[slideNumber]} alt="" className="sliderImg" />
          </div>
          <FontAwesomeIcon icon={faCircleArrowRight} className={slideNumber !== (photos.length - 1) ? 'arrow' : 'arrowDisabled'} onClick={() => handleSliderMove("r")} />
        </div>}
        <div className="hotelWrapper">
          <button className="hotelBookNow">Reserve or Book Now!</button>
          <h1 className="hotelTitle">{data.name}</h1>
          <div className="hotelAddress">
            <FontAwesomeIcon icon={faLocationDot} />
            <span>{data.address}</span>
          </div>
          <span className="hotelDistance">
            Excellent location - {data.distance}
          </span>
          <span className="hotelPriceHighlight">
            Book a stay over {formatCurrency(data.minPrice)} at this property and get a free airport taxi
          </span>
          <div className="hotelImages">
            {photos.map((photo, i) => (
              <div className="hotelImgWrapper" key={i}>
                <img src={photo} alt="" onClick={() => handleSlideOpen(i)} className="hotelImg" />
              </div>
            ))}
          </div>
          <div className="hotelDetails">
            <div className="hotelDetailsText">
              <h1 className="hotelTitle">{data.title}</h1>
              <p className="hotelDesc">{data.description}</p>
            </div>
            <div className="hotelDetailsPrice">
              <h1>Perfect for a {numDays}-night stay!</h1>
              <span>
                Located in the real heart of Krakow, this property has an
                excellent location score of 9.8!
              </span>
              <h2>
                {/* <b>${days * data.cheapestPrice * options.room}</b> ({days}{" "}
                nights) */}
                <b>{formatCurrency(numDays * data.minPrice * options.room)}</b>
              </h2>
              <div className='stayDays'>(For {options.room} rooms, {numDays} nights)</div>
              <button onClick={handleClick}>Reserve or Book Now!</button>
            </div>
          </div>
        </div>
        <MailList />
        <Footer />
      </div>)}
    </div>
  )
}

export default Hotel
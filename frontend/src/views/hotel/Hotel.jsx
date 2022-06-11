import React from 'react'
import Navbar from '../../components/navbar/Navbar'
import Header from '../../components/header/Header'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCircleArrowLeft, faCircleArrowRight, faCircleXmark, faLocationDot } from '@fortawesome/free-solid-svg-icons'
import './Hotel.scss'
import img1 from '../../images/101.jpg';
import img2 from '../../images/102.jpg';
import img3 from '../../images/103.jpg';
import img4 from '../../images/104.jpg';
import img5 from '../../images/105.jpg';
import img6 from '../../images/106.jpg';
import MailList from '../../components/mail-list/MailList'
import Footer from '../../components/footer/Footer'
import { useState } from 'react'

const photos = [img1, img2, img3, img4, img5, img6];

const Hotel = () => {
  const [slideNumber, setSlideNumber] = useState(0);
  const [openSlider, setOpenSlider] = useState(false);

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

  const handleClick = () => {
    console.log("handle click called");
  }

  return (
    <div>
      <Navbar />
      <Header type="list" />
      <div className="hotelContainer">
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
          <h1 className="hotelTitle">Grand Hotel</h1>
          <div className="hotelAddress">
            <FontAwesomeIcon icon={faLocationDot} />
            <span>Elton St New York</span>
          </div>
          <span className="hotelDistance">
            Excellent location - 500m from center
          </span>
          <span className="hotelPriceHighlight">
            Book a stay over $ 112 at this property and get free airport taxi
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
              <h1 className="hotelTitle">Yamla Pagla Diwana</h1>
              <p className="hotelDesc">A hotel is an establishment that provides paid lodging on a short-term basis. Facilities provided inside a hotel room may range from a modest-quality mattress in a small room to large suites with bigger, higher-quality beds, a dresser, a refrigerator and other kitchen facilities, upholstered chairs, a flat screen television, and en-suite bathrooms. Small, lower-priced hotels may offer only the most basic guest services and facilities. Larger, higher-priced hotels may provide additional guest facilities such as a swimming pool, business centre (with computers, printers, and other office equipment), childcare, conference and event facilities, tennis or basketball courts, gymnasium, restaurants, day spa, and social function services.</p>
            </div>
            <div className="hotelDetailsPrice">
              <h1>Perfect for a 5-night stay!</h1>
              <span>
                Located in the real heart of Krakow, this property has an
                excellent location score of 9.8!
              </span>
              <h2>
                {/* <b>${days * data.cheapestPrice * options.room}</b> ({days}{" "}
                nights) */}
                <b>$495</b> (9 nights)
              </h2>
              <button onClick={handleClick}>Reserve or Book Now!</button>
            </div>
          </div>
        </div>
        <MailList />
        <Footer />
      </div>
    </div>
  )
}

export default Hotel
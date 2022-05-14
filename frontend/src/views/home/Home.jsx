import React from 'react'
import FeaturedHomes from '../../components/featured-homes/FeaturedHomes'
import Featured from '../../components/featured/Featured'
import Footer from '../../components/footer/Footer'
import Header from '../../components/header/Header'
import MailList from '../../components/mail-list/MailList'
import Navbar from '../../components/navbar/Navbar'
import PropertyTypes from '../../components/property-types/PropertyTypes'
import './Home.scss'

const Home = () => {
  return (
    <div>
        <Navbar />
        <Header />
        <div className="homeContainer">
          <Featured />
          <h1 className="homeTitle">Browse by property type</h1>
          <PropertyTypes />
          <h1 className="homeTitle">Homes guests love</h1>
          <FeaturedHomes />
          <MailList />
          <Footer />
        </div>
    </div>
  )
}

export default Home
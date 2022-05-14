import React, { useState } from 'react'
import { useLocation } from 'react-router-dom'
import Header from '../../components/header/Header'
import Navbar from '../../components/navbar/Navbar'
import './HotelList.scss'
import { format } from 'date-fns';
import { DateRange } from 'react-date-range'
import SearchItem from '../../components/search-item/SearchItem'
import { useOnOutsideClick } from '../../custom-hook/useOnOutsideClick'

const HotelList = () => {
  const location = useLocation();
  const [destination, setDestination] = useState(location.state ? location.state.destination : "");
  const [date, setDate] = useState(location.state ? location.state.date : [
    {
      startDate: new Date(),
      endDate: new Date(Date.now() + (24 * 60 * 60 * 1000)),
      key: 'selection'
    }
  ]);
  const [options, setOptions] = useState(location.state ? location.state.options : {
    adult: 1,
    children: 0,
    room: 1
  });
  const [openDate, setOpenDate] = useState(false);
  const { innerBorderRef } = useOnOutsideClick(() => setOpenDate(false));

  const handleOptionChange = (optionName, val) => {
    setOptions({ ...options, [optionName]: val })
  }

  return (
    <div>
      <Navbar />
      <Header type="list" />
      <div className="listContainer">
        <div className="listWrapper">
          <div className="listSearch">
            <h1 className="lsTitle">Search</h1>
            <div className="lsItem">
              <label>Destination</label>
              <input type="text" value={destination} onChange={e => setDestination(e.target.value)} />
            </div>
            <div className="lsItem">
              <label>Check-in date</label>
              <div ref={innerBorderRef} className="dateRangeContainer">
                <span onClick={() => setOpenDate(!openDate)}>{`${format(date[0].startDate, 'dd/MM/yy')} to ${format(date[0].endDate, 'dd/MM/yy')}`}</span>
                {openDate && <DateRange
                  className='dateRange'
                  onChange={item => setDate([item.selection])}
                  ranges={date}
                  minDate={new Date()}
                />}
              </div>
            </div>
            <div className="lsItem">
              <label>Options</label>
              <div className="lsOptions">
                <div className="lsOptionItem">
                  <span className="lsOptionText">
                    Min price <small>per night</small>
                  </span>
                  <input type="number" className="lsOptionInput" />
                </div>
                <div className="lsOptionItem">
                  <span className="lsOptionText">
                    Max price <small>per night</small>
                  </span>
                  <input type="number" className="lsOptionInput" />
                </div>
                <div className="lsOptionItem">
                  <span className="lsOptionText">Adult</span>
                  <input
                    type="number"
                    min={1}
                    className="lsOptionInput"
                    value={options.adult}
                    onChange={e => handleOptionChange('adult', e.target.value)}
                  />
                </div>
                <div className="lsOptionItem">
                  <span className="lsOptionText">Children</span>
                  <input
                    type="number"
                    min={0}
                    className="lsOptionInput"
                    value={options.children}
                    onChange={e => handleOptionChange('children', e.target.value)}
                  />
                </div>
                <div className="lsOptionItem">
                  <span className="lsOptionText">Room</span>
                  <input
                    type="number"
                    min={1}
                    className="lsOptionInput"
                    value={options.room}
                    onChange={e => handleOptionChange('room', e.target.value)}
                  />
                </div>
              </div>
            </div>
            <button>Search</button>
          </div>
          <div className="listResult">
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
            <SearchItem />
          </div>
        </div>
      </div>
    </div>
  )
}

export default HotelList
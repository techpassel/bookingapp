import { faBed, faCalendar, faCar, faMountainSun, faPerson, faPlane, faTaxi } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import React, { useState } from 'react'
import './Header.scss'
import 'react-date-range/dist/styles.css'; // main css file
import 'react-date-range/dist/theme/default.css'; // theme css file
import { DateRange } from 'react-date-range';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useOnOutsideClick } from '../../custom-hook/useOnOutsideClick';

const Header = ({ type }) => {
    const [destination, setDestination] = useState("");
    const [openDate, setOpenDate] = useState(false);
    const [date, setDate] = useState([
        {
            startDate: new Date(),
            endDate: new Date(Date.now() + (24 * 60 * 60 * 1000)),
            key: 'selection'
        }
    ]);
    const [openOptions, setOpenOptions] = useState(false);
    const [options, setOptions] = useState(
        {
            adult: 1,
            children: 0,
            room: 1
        }
    );
    const navigate = useNavigate();

    const handleSearch = () => {
        navigate("/hotels", { state: { destination, date, options } })
    }

    const handleOption = (type, counterType) => {
        setOptions(prev => {
            return {
                ...prev, [type]: (counterType === 'd' ? options[type] - 1 : options[type] + 1),
            };
        })
    }

    const { innerBorderRef } = useOnOutsideClick(() => setOpenDate(false));

    return (
        <div className='header'>
            <div className={type === "list" ? "headerContainer listMode" : "headerContainer"}>
                <div className="headerList">
                    <div className="headerListItem active">
                        <FontAwesomeIcon icon={faBed} />
                        <span>Stays</span>
                    </div>
                    <div className="headerListItem">
                        <FontAwesomeIcon icon={faPlane} />
                        <span>Flights</span>
                    </div>
                    <div className="headerListItem">
                        <FontAwesomeIcon icon={faCar} />
                        <span>Car rentals</span>
                    </div>
                    <div className="headerListItem">
                        <FontAwesomeIcon icon={faMountainSun} />
                        <span>Attractions</span>
                    </div>
                    <div className="headerListItem">
                        <FontAwesomeIcon icon={faTaxi} />
                        <span>Airport taxis</span>
                    </div>
                </div>
                {type !== "list" && <>
                    <h1 className="headerTitle">
                        A lifetime of discounts? It's Genius.
                    </h1>
                    <p className="headerDesc">
                        Get rewarded for your travels – unlock instant savings of 10% or
                        more with a free Lamabooking account
                    </p>
                    <button className="headerBtn">Sign in / Register</button>
                    <div className="headerSearch">
                        <div className="headerSearchItem">
                            <FontAwesomeIcon icon={faBed} className="headerIcon" />
                            <input type='text' placeholder='Where are you going?' className='headerSearchInput' onChange={e => setDestination(e.target.value)} />
                        </div>
                        <div ref={innerBorderRef} className="headerSearchItem">
                            <FontAwesomeIcon icon={faCalendar} className="headerIcon" />
                            <span onClick={() => setOpenDate(!openDate)} className='headerSearchText'>{`${format(date[0].startDate, 'dd/MM/yy')} to ${format(date[0].endDate, 'dd/MM/yy')}`}</span>
                            {openDate && <DateRange
                                
                                editableDateInputs={true}
                                onChange={item => setDate([item.selection])}
                                moveRangeOnFirstSelection={false}
                                ranges={date}
                                className='date'
                                minDate={new Date()}
                            />}
                        </div>
                        <div className="headerSearchItem">
                            <FontAwesomeIcon icon={faPerson} className="headerIcon" />
                            <span className='headerSearchText' onClick={() => setOpenOptions(!openOptions)}>
                                {`${options.adult} adults . ${options.children} children . ${options.room} room`}
                            </span>
                            {openOptions && <div className="options">
                                <div className="optionItem">
                                    <span className="optionText">Adult</span>
                                    <div className="optionCounter">
                                        <button className="optionCounterBtn" onClick={() => handleOption("adult", "d")} disabled={options.adult <= 1}>-</button>
                                        <span className="optionCounterNumber">{options.adult}</span>
                                        <button className="optionCounterBtn" onClick={() => handleOption("adult", "i")}>+</button>
                                    </div>
                                </div>
                                <div className="optionItem">
                                    <span className="optionText">Children</span>
                                    <div className="optionCounter">
                                        <button className="optionCounterBtn" onClick={() => handleOption("children", "d")} disabled={options.children < 1}>-</button>
                                        <span className="optionCounterNumber">{options.children}</span>
                                        <button className="optionCounterBtn" onClick={() => handleOption("children", "i")}>+</button>
                                    </div>
                                </div>
                                <div className="optionItem">
                                    <span className="optionText">Room</span>
                                    <div className="optionCounter">
                                        <button className="optionCounterBtn" onClick={() => handleOption("room", "d")} disabled={options.room <= 1}>-</button>
                                        <span className="optionCounterNumber">{options.room}</span>
                                        <button className="optionCounterBtn" onClick={() => handleOption("room", "i")}>+</button>
                                    </div>
                                </div>
                            </div>}
                        </div>
                        <div className="headerSearchItem">
                            <button className='headerBtn' onClick={handleSearch}>Search</button>
                        </div>
                    </div>
                </>}
            </div>
        </div >
    )
}

export default Header
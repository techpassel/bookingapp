import React from 'react'
import { useContext } from 'react'
import { Link } from 'react-router-dom'
import { AuthContext } from '../../context/AuthContext'
import './Navbar.scss'

const Navbar = () => {
  const { user } = useContext(AuthContext);
  const { dispatch } = useContext(AuthContext);
  const handleLogout = () => {
    dispatch({ type: "LOGOUT" })
  }

  return (
    <div className='navbar'>
      <div className="navContainer">
        <Link to="/">
          <span className="logo">BookingApp</span>
        </Link>
        <div className="navItems">
          {user ? (<><span>{user.name}</span>
            <button onClick={handleLogout} className="navButton">Logout</button></>) :
            (<>
              <Link to="/register"><button className="navButton">Register</button></Link>
              <Link to="/login"><button className="navButton">Login</button></Link>
            </>)}
        </div>
      </div>
    </div>
  )
}

export default Navbar
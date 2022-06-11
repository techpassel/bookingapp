import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  Routes
} from "react-router-dom";
import Home from './views/home/Home';
import HotelList from './views/hotel-list/HotelList';
import Hotel from "./views/hotel/Hotel";

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/hotels' element={<HotelList />} />
        <Route path='/hotel/:id' element={<Hotel />} />
      </Routes>
    </Router>
  );
}

export default App;

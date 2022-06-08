import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.scss';
import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // <React.StrictMode>
    <App />
  // </React.StrictMode>
);
//If you enable <React.StrictMode> you will see components render twice or multiple times.
//StrictMode is a tool for highlighting potential problems in an application. 
//Like Fragment, StrictMode does not render any visible UI. It activates additional checks and 
//warnings for its descendants. 
//React will soon provide a new Concurrent Mode which will break render work into multiple parts. 
//Pausing and resuming the work between this parts should avoid the blocking of the browsers main thread.
//So if not necessary(i.e if not creating a huge performance problem) then we should not remove 
//<React.StrictMode> from our code.


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

import React from 'react'
import ReactDOM from 'react-dom'
import App from './ui/app'
import Home from './ui/home'
import { Link } from 'react-router-dom'
import { BrowserRouter as Router, Route } from 'react-router-dom'


function example5 () {
  ReactDOM.render(
    <div> <header>
    <nav>
    <Router> 
      <ul>
        <li><Link to='/sadsad' component={Home}/></li>
      </ul>
      </Router>
    </nav>
  </header></div>,
    document.getElementById('root')
  )
}

example5()
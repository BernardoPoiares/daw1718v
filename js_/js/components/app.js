import React from 'react'
import { BrowserRouter, Route,Link } from "react-router-dom";

import checkItems from './checkItems';
import checkLists from './checkLists'
import Login from './login';

import Home from './home';

const Sep = () => <span> | </span>;

export default class App extends React.Component{
render(){
    return (
        <BrowserRouter>
        <div>
        <div>
            <Link to="/">Home</Link> <Sep />
            <Link to="/login">Login</Link> <Sep />
            <Link to="/checkItems">CheckItems</Link> <Sep />            
            <Link to="/checkLists">CheckLists</Link> <Sep />
            <hr/>
            </div>
          <Route exact path="/" component={Home} />
          <Route path="/login" component={Login} />
          <Route path="/checkItems" component={checkItems} />
          <Route path="/checkLists" component={checkLists} />
        </div>
      </BrowserRouter>
    )
}

}
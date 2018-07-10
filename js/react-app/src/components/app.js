import React from 'react'
import { BrowserRouter, Route,Link } from "react-router-dom";

import checkItem from './checkItem';
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
            <Link to="/checkItem">CheckItem</Link> <Sep />
            <hr/>
            </div>
          <Route exact path="/" component={Home} />
          <Route path="/login" component={Login} />
          <Route path="/checkItem" component={checkItem} />
        </div>
      </BrowserRouter>
    )
}

}
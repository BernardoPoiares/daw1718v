import React from 'react'

import CheckItem from './checkItem'
import Login from './login'



//import Home from './home'
import { BrowserRouter as  Route } from 'react-router-dom'


const Home = ()=> <h2>Home</h2>

export default class extends React.Component{
    constructor(props){
        super(props)
    }

    render(){
        return(<div>
            <Route exact path='/' component={Home}></Route>
            <Route exact path='/login' component={Login}></Route>
            <Route exact path='/checkItem' component={CheckItem}></Route>
          </div>)
    }
}
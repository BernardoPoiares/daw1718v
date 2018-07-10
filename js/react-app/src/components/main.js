import React from 'react'

import CheckItem from './checkItem'
import Login from './login'



//import Home from './home'
import { BrowserRouter as  Route,Link,Switch } from 'react-router-dom'


const Home = ()=> <div><h1>Welcome home</h1><Link to='/about'>Go to about</Link></div>

export default class extends React.Component{
    constructor(props){
        super(props)
    }

    render(){
        return(<Switch>
            <Route path='/' component={Home}></Route>
            <Route exact path='/login' component={Login}></Route>
            <Route exact path='/checkItem' component={CheckItem}></Route>
          </Switch>)
    }
}
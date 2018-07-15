import React from 'react'
import { BrowserRouter, Route,Link,Redirect } from "react-router-dom";


import checkItems from './checkItems';
import checkLists from './checkLists'
import Login from './login';


import Home from './home';
import Session  from './session';

const Sep = () => <span> | </span>

const Logout = () => {
    Session.logout()
    return <Redirect to='/'  />
}

const LoadStates = {
  loading: 'loading',
  loaded: 'loaded',
  error: 'error'
}


export default class App extends React.Component{
  constructor(props){
    super(props)
    this.renderize=this.renderize.bind(this)
    this.state={
      loadState:LoadStates.loading
    }
  }
  
  renderLoginLogout(){
    console.log(Session.isLogggedIn())
    if(Session.isLogggedIn())
      return (<Link to="/login">Login</Link>)
    else  
      return (<Link to="/logout">Logout</Link> )
  }

  componentDidMount () {
    this.loadIfNeeded()
  }

  componentDidUpdate () {
    this.loadIfNeeded()
  }

  loadIfNeeded () {
    if (this.state.loadState !== LoadStates.loading) return
    this.setState={
      loadState:LoadStates.loaded
    }
  }

  renderize(){
    this.setState={
      loadState:LoadStates.loading
    }
  }

render(){
    return (
        <BrowserRouter>
        <div>
          <div>
            <Link to="/">Home</Link> <Sep />
            {this.renderLoginLogout()}  <Sep />
            <Link to="/checkItems">CheckItems</Link> <Sep />            
            <Link to="/checkLists">CheckLists</Link> <Sep />
            <hr/>
          </div>
          <Route exact path="/" component={Home} />
          <Route path="/login" component={Login} renderize={this.renderize}/>
          <Route path="/logout" component={Logout} />
          <Route path="/checkItems" component={checkItems} />
          <Route path="/checkLists" component={checkLists} />
        </div>
      </BrowserRouter>
    )
}

}
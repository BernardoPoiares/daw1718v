import React from 'react'
import { BrowserRouter, Route,Link,Redirect } from "react-router-dom";


import checkItems from './checkItems';
import checkLists from './checkLists'
import Login from './login';


import Home from './home';
import Session  from './session';

const Sep = () => <span> | </span>


const LoadStates = {
  loading: 'loading',
  loaded: 'loaded',
  error: 'error'
}


export default class App extends React.Component{
  constructor(props){
    super(props)
    this.renderize=this.renderize.bind(this)
    this.Logout=this.Logout.bind(this)
    this.state={
      loadState:LoadStates.loading
    }
  }
  

  Logout(){
    Session.logout()
    console.log('login:'+Session.isLogggedIn())
    VER ESTA MERDA!!!
    this.props.history.push('/')
  }

  renderLoginLogout(){
    console.log(Session.isLogggedIn())
    if(!Session.isLogggedIn())
      return (<Link to="/login">Login</Link>)
    else  
      return (<Link to="/logout" onClick={this.Logout()}>Logout</Link> )
  }

  componentDidMount () {
    this.loadIfNeeded()
  }

  componentDidUpdate () {
    this.loadIfNeeded()
  }

  loadIfNeeded () {
    if (this.state.loadState !== LoadStates.loading) return
    this.setState({
      loadState:LoadStates.loaded
    })
  }

  renderize(){
    this.setState({
      loadState:LoadStates.loading
    })
  }
  shouldComponentUpdate(nextProps, nextState){
    return this.propsloadState !== nextProps.loadState
}

render(){
  
  console.log(4)
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
    <Route path="/login" render={()=><Login renderize={this.renderize}/>} />
          <Route path="/checkItems" component={checkItems} />
          <Route path="/checkLists" component={checkLists} />
        </div>
      </BrowserRouter>
    )
}

}
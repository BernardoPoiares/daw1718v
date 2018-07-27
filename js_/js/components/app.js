import React from 'react'
import { BrowserRouter, Route,Link,Redirect } from 'react-router-dom'

import checkItems from './checkItemsComp'
import checkItem from './checkItemComp'
import checkLists from './checkListsComp'
import checkList from './checkListComp'
import checkListTemplates from './checkListTemplatesComp'
import checkListTemplate from './checkListTemplateComp'

import Login from './login'
import Home from './home'
import Session  from './session'

import ErrorBoundary from './errorBoundary'

import oidc from './oidc'

import styles from '../css/styles.css'

const Sep = () => <span> | </span>


const LoadStates = {
  loading: 'loading',
  loaded: 'loaded',
  error: 'error'
}


export default class App extends React.Component{
  constructor(props){
    super(props)
    this.redirectHome=this.redirectHome.bind(this)
    this.Logout=this.Logout.bind(this)
    this.state={
      loadState:LoadStates.loading,
      red_path:null
    }
  }
  

  Logout(){
    return()=>{
    Session.logout()
    this.setState({
      loadState:LoadStates.loading
    })
  }
}

  renderLoginLogout(){    
    if(!Session.isLogggedIn())
      return (<Link to="/login">Login</Link>)
    else  
      return (<Link to="/" onClick={this.Logout()}>Logout</Link> )
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

  redirectHome(){
    this.setState({
      loadState:LoadStates.loading

    })
  }

render(){
  if(this.state.loadState==LoadStates.loaded){
    return (
        <BrowserRouter>
        <div>
          <div>
            <Link to="/">Home</Link> <Sep />
            {this.renderLoginLogout()}  <Sep />
            <Link to="/checkItems">CheckItems</Link> <Sep />            
            <Link to="/checkLists">CheckLists</Link> <Sep />
            <Link to="/checkListsTemplates">CheckListsTemplates</Link> <Sep />
            <hr/>
          </div>
          
          <ErrorBoundary>
            <Route path="/" exact={true} component={Home} />
            <Route path="/callback" render={()=>oidc.loginCallback()}/>
            <Route path="/login" render={()=><Login redirectHome={this.redirectHome}/>} />
            <Route path="/checkItems" exact={true} component={checkItems} />
            <Route path="/checkItems/:id" exact={true} component={checkItem} />
            <Route path="/checkLists" exact={true} component={checkLists} />
            <Route path="/checkLists/:id" exact={true} component={checkList} />
            <Route path="/checkListsTemplates/" exact={true} component={checkListTemplates} />
            <Route path="/checkListsTemplates/:id" exact={true} component={checkListTemplate} />
          </ErrorBoundary>
        </div>
      </BrowserRouter>
    )
  }else{
    return(<div>Loading...</div>)
  }
}

}

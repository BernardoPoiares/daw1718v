import React from 'react'
import request from './request'
import session from './session'
import User from '../model/User'
import ErrorComp from './errorComponent'
import { Redirect } from 'react-router-dom'

import oidc from './oidc'




export default class Login extends React.Component {
    constructor(props) {
      super(props);
      this.re=props.redirectHome
 // this.changeHandler = this.changeHandler.bind(this)
 // this.submitHandler = this.submitHandler.bind(this)

      this.state = {
        username: "",
        password: "",
        redirect:false
      };
    }
  /*
      componentDidMount(){
        this.loadifneed()
      }
   
      loadifneed(){
        if(this.state.done==true) return
        this.state.done=true
        return fetch('http://localhost:8080/openid-connect-server-webapp/authorize?client_id=845ff6ce-019e-47ea-90cf-c0d2af01ed04&response_type=id_token&scope=openid',{
          method:'GET'
        }).then(resp=>{
           console.log(resp)
          })
        
      }

    changeHandler( event){
      this.setState({
        [event.target.name]: event.target.value
      });
    }
    submitHandler(event){
      request('/user/login','POST',{
        username:this.state.username,
        password:this.state.password
      }).then(json=>{
          session.saveLoginToken(new User(json))
          this.setState({redirect:true})
          this.re()
      }).catch(error=>{
        this.setState({error:error,done:true})
    })
    }*/


    render(){
      oidc.login()
      return <div/>
    }
    render2() {
      if(this.state.error!=null)
      return (<ErrorComp error={this.state.error}/>)
      if(this.state.redirect==true)
        return <Redirect to="/"/>
    return (
      <div>
        Username:
      <input type='text'
        name='username'
        onChange={this.changeHandler}
        value={this.state.username}/>
        Password:
      <input name='password'
      type='password'
        onChange={this.changeHandler}
        value={this.state.password}/>
        <br/>
        <input type="button" value="LogIn" onClick={this.submitHandler}/>
      </div>
    );
  }


  
}
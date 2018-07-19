import React from 'react'
import request from './request'
import session from './session'
import User from '../model/User'


export default class Login extends React.Component {
    constructor(props) {
      super(props);
      this.redirectHome=props.redirectHome

  this.changeHandler = this.changeHandler.bind(this)
  this.submitHandler = this.submitHandler.bind(this)

      this.state = {
        username: "",
        password: ""
      };
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
      }).then(resp=>{
        return resp.json().then(json=>{
          session.saveLoginToken(new User(json))
          this.redirectHome()
        })
      })
    }

    render() {
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
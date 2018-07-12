import React from 'react'


export default class Login extends React.Component {
    constructor(props) {
      super(props);
   

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
      fetch('http://localhost:8081/user/login',{
        method:'POST',
        headers:{
          Accept:'application/json',
          'Content-type':'application/json',
          'Access-Control-Allow-Origin':'*'
        },body:JSON.stringify({
          username:this.state.username,
          password:this.state.password
        })
      }).then(resp=>{
        return resp.json().then(json=>{
          console.log(json)
        })
      })
    }

    validateInputs(){
      return this.state.username.length>0 && this.state.password.length>0
    }

    render() {
    return (
      <div>
        Username:
      <input type='text'
        name='username'
        autofocus
        onChange={this.changeHandler}
        value={this.state.username}/>
        Password:
      <input name='password'
      type='password'
        onChange={this.changeHandler}
        value={this.state.password}/>
        <br/>
        <button type="submit" disable={!this.validateInputs} onClick={this.submitHandler}>Submit</button>
      </div>
    );
  }
}
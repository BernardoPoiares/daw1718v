import React from 'react'
import {FormControl,FormGroup,Button,ControlLabel} from 'react-bootstrap'


export default class Login extends React.Component {
    constructor(props) {
      super(props);
  
      this.state = {
        email: "",
        password: ""
      };
    }
  
    render() {
      return (
        <div className="Login">
          <form >
            <FormGroup controlId="email" bsSize="large">
              <ControlLabel>Email</ControlLabel>
              <FormControl
                autoFocus
                type="email"
                value={this.state.email}
              />
            </FormGroup>
            <FormGroup controlId="password" bsSize="large">
              <ControlLabel>Password</ControlLabel>
              <FormControl
                value={this.state.password}
                type="password"
              />
            </FormGroup>
            <Button
              block
              bsSize="large"
              type="submit"
            >
              Login
            </Button>
          </form>
        </div>
      );
    }
  }
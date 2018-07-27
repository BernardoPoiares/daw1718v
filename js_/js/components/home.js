import React from 'react'
import Session from './session'
import oidc from './oidc'

export default class extends React.Component{
    constructor(props){
        super(props)
    }

render(){
    if(!Session.isLogggedIn()){
        return (
            <div className='home'>
            <h1>Welcome to CheckLists!</h1>
            <p>Please Log In to use this app. </p>
        </div>
        )
    } else{
        return <div className='home'>
        <h1>Hello There!</h1>
        <p>Don't forget to check your CheckItems and CheckLists ;)</p>
    </div>
    }
}

}
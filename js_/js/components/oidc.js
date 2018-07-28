import oidc from 'oidc-client'
import Session from './session'
import React from 'react'
import {Redirect} from 'react-router-dom'

module.exports = {
    login,logout,getUser,loginCallback
    }

const config = {
    authority: "http://localhost:8080/openid-connect-server-webapp/",
   client_id: "aec0ac91-e839-40dc-b197-38f8bbbbfa0c",
   // client_id: "client",
    
    redirect_uri: "http://localhost:9000/callback",
    response_type: "id_token token",
    scope:"openid"
}

const usermanager=new oidc.UserManager(config)

let user;

function getUser()
{
    usermanager.getUser().then(function(user)
    {
        return user
    })
}
usermanager.events.addUserLoaded(function (loadedUser) {
    user = loadedUser;
    console.log(user)
});

function login() {
    return usermanager.signinRedirect()
}

function loginCallback(){
    usermanager.signinRedirectCallback().then(resp=>{
       // Session.saveLoginToken(resp.access_token)
       localStorage.setItem('Bearer','Bearer '+resp.access_token)

    })
    return (<Redirect to="/"/>)
}

function logout() {
    usermanager.removeUser()
}
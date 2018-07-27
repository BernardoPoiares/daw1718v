import oidc from 'oidc-client'
import Session from './session'
import React from 'react'
import {Redirect} from 'react-router-dom'

module.exports = {
    login,logout,getUser,loginCallback
    }

const config = {
    authority: "http://localhost:8080/openid-connect-server-webapp/",
    client_id: "client",
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
    console.log('add user loaded'+user)
});

function login() {
    return usermanager.signinRedirect()
}

function loginCallback(){
    usermanager.signinRedirectCallback().then(resp=>{
        Session.setBearerToken(resp.access_token)
    })
    return (<Redirect to="/"/>)
}

function logout() {
    usermanager.removeUser()
}
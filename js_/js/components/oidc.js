import oidc from 'oidc-client'

module.exports = {
    login,logout,getUser,loginCallback
    }

const config = {
    authority: "http://localhost:8080/openid-connect-server-webapp/",
    client_id: "client",
    redirect_uri: "http://localhost:8080/",
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

    return usermanager.signinRedirectCallback().then(resp=>{
        console.log(resp)
    })
}

function logout() {
    usermanager.removeUser()
}
import oidc from './oidc'

module.exports={
    saveLoginToken,
    isLogggedIn,
    logout,
    getLoginToken
}

/* //BASIC AUTHENTICATION

function saveLoginToken(user){
    localStorage.setItem('token','Basic '+user.token)
}

function isLogggedIn(){
    return localStorage.getItem('token')!=null;
}

function logout(){
    localStorage.removeItem('token')
}*/


//OPEN ID
function logout(){
    removeBearerToken()
    oidc.logout()
}


function saveLoginToken(token){
    localStorage.setItem('Bearer','Bearer '+token)
}

function getLoginToken(){
    return localStorage.getItem('Bearer')
}

function isLogggedIn(){
    return localStorage.getItem('Bearer')!=null;
}

function removeBearerToken(){
    localStorage.removeItem('Bearer')
}


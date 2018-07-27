

module.exports={
    saveLoginToken,
    isLogggedIn,
    logout,
    setBearerToken,
    getBearerToken,
    removeBearerToken
}

function saveLoginToken(user){
    localStorage.setItem('token','Basic '+user.token)
}

function isLogggedIn(){
    return localStorage.getItem('token')!=null;
}

function logout(){
    localStorage.removeItem('token')
}


function setBearerToken(token){
    localStorage.setItem('Bearer','Bearer '+token)
}

function getBearerToken(){
    return localStorage.getItem('Bearer')
}

function removeBearerToken(){
    localStorage.removeItem('Bearer')
}
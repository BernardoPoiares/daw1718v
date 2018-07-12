

module.exports={
    saveLoginToken,
    isLogggedIn,
    logout
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
import session from './session'
const host='http://localhost:8081'

export default function(path,method,json){
    const myheaders=new Headers()
    myheaders.append('Access-Control-Allow-Origin','*')
    if(session.isLogggedIn)
    myheaders.append('Authorization',localStorage.getItem('token'))
    console.log(method=='POST')
    if(method=='POST'){
      myheaders.append('Content-type','application/json')
      myheaders.append('Accept','application/json')
      myheaders.append('Access-Control-Allow-Methods','POST')
    }
    else{
      myheaders.append('Access-Control-Allow-Methods','GET')
    }
    return fetch(host+path,{
        method:method,
        headers:myheaders,
        body:JSON.stringify(json)
      })
}
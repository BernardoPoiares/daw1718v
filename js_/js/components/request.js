import session from './session'
const host='http://localhost:8081'

export default function(path,method,json){
    const myheaders=new Headers()
    myheaders.append('Access-Control-Allow-Origin','*')
    if(session.isLogggedIn)
      myheaders.append('Authorization',localStorage.getItem('token'))
    myheaders.append('Access-Control-Allow-Methods',method)
    if(method!='GET')
      myheaders.append('Content-type','application/json')
    return fetch(host+path,{
        method:method,
        headers:myheaders,
        body:JSON.stringify(json)
      }).then(resp=>{
        console.log(resp)
          if (resp.status >= 400) 
            throw new Error("InternalServerError")
          return resp.json().then()
        })
}
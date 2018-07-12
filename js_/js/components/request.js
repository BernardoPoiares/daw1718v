
const host='http://localhost:8081'

export default function(path,method,json){

    return fetch(host+path,{
        method:method,
        headers:{
          'Accept':'application/json',
          'Content-type':'application/json',
          'Access-Control-Allow-Origin':'*',
          'Authorization':localStorage.getItem('token')
        },body:JSON.stringify(json)
      })
}
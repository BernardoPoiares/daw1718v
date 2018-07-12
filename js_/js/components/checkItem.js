import React from 'react'
import  request  from './request';
import  CheckItem  from '../Model/CheckItem';

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={done:false}
    }


    componentDidMount () {
        this.loadIfNeeded()
      }
    
      componentDidUpdate () {
        this.loadIfNeeded()
      }
    
      loadIfNeeded () {
          console.log(localStorage.getItem('token'))
          request('/checkItem/all','GET')
          .then(resp=>{
            return resp.json().then(json=>{
                const checkitemsarray=[]
            json.properties.map(checkitem=>{
                checkitemsarray.push(new CheckItem(checkitem))
            })
              this.setState({done:true,checkitems:checkitemsarray})
            })
          })
      }




    render(){
        if(this.state.done==true){
            
        return(<div>
            <h2>CheckItems</h2>
              <div>
                <table style="width:100%">
                    <tr>
                        <th>Name</th>
                        <th>Description</th> 
                    </tr>
                    {this.state.checkitems.map(checkitem=>(
                        <tr>
                            <td>{checkitem.name}</td>
                            <td>{checkitem.Description}</td>
                        </tr>
                    ))
                    }
                </table>
                </div>  
            </div>)
        }
        else return(<div/>)
    }
}
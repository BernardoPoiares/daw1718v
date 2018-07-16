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
          if(this.state.done===true)return
          request('/checkItem/all','GET')
          .then(resp=>{
            return resp.json().then(json=>{
            const checkitemsarray=[]
            json.properties.map(checkitem=>{
                checkitemsarray.push(new CheckItem(checkitem))
            })
            this.setState({checkitems:checkitemsarray,done:true})
            })
          })
      }




    render(){
        if(this.state.done===true){
        return(<div>
            <h2>CheckItems</h2>
              <div>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Description</th> 
                    </tr>
                    {this.state.checkitems.map(checkitem=>(
                        <tr key={checkitem.id}>
                            <td>{checkitem.name}</td>
                            <td>{checkitem.description}</td>
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
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
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th> 
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.checkitems.map(checkitem=>(
                            <tr key={checkitem.id}>
                                <td><a href={"/checkItems/"+checkitem.id}>{checkitem.name}</a></td>
                                <td>{checkitem.description}</td>
                            </tr>
                        ))
                        }
                    </tbody>
                </table>
                </div>  
            </div>)
        }
        else return(<div/>)
    }
}
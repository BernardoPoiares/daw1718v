import React from 'react'
import  request  from './request';
import  CheckList  from '../Model/CheckList';

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
          request('/checkList/all','GET')
          .then(resp=>{
            return resp.json().then(json=>{
            const checklistsarray=[]
            json.properties.map(checkitem=>{
                checklistsarray.push(new CheckList(checkitem))
            })
            this.setState({checklists:checklistsarray,done:true})
            })
          })
      }




    render(){
        if(this.state.done===true){
        return(<div>
            <h2>CheckLists</h2>
              <div>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Description</th> 
                    </tr>
                    {this.state.checklistsarray.map(checklist=>(
                        <tr key={checklist.id}>
                            <td>{checklist.name}</td>
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
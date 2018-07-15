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
            json.properties.map(checklist=>{
                checklistsarray.push(new CheckList(checklist))
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
                        <th>Id</th>
                        <th>Description</th>
                        <th>CompletionDate</th> 
                    </tr>
                    {this.state.checklists.map(checklist=>(
                        <tr key={checklist.id}>
                            <td>{checklist.name}</td>                            
                            <td>{checklist.completionDate}</td>
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
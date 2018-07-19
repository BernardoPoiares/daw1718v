import React from 'react'
import ServerRequests from './serverRequests'
import TableCell from './tableCell'
import DateCell from './dateCell'
import CheckList from '../model/CheckList'


export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={
            done:false,
            id:props.match.params.id
        }
    }
    componentDidMount () {
        this.loadIfNeeded()
      }
    
      componentDidUpdate () {
        this.loadIfNeeded()
      }
    
      loadIfNeeded () {
        if(this.state.done===true)  return
        ServerRequests.GetCheckList(this.state.id).then(resp=>{
            return resp.json().then(json=>{
            const checklist=new CheckList(json.properties)
            this.setState({checklist:checklist,done:true})
            })
        })
    }
    
    update(checkitem){
        return ServerRequests.UpdateCheckList(checkitem)
      }

    render(){
        if(this.state.done==true)
        return(<div><h3>Checklist : {this.state.checklist.id}</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>CompletionDate</th>
                        </tr>
                    </thead>
                    <tbody key={this.state.checklist.id} >
                        <tr>
                            <TableCell id={this.state.checklist.id} value={this.state.checklist.name}  name="name" update={this.update}/>
                            <DateCell id={this.state.checklist.id} value={this.state.checklist.completionDate}  name="completionDate" update={this.update}/>
                        </tr>
                    </tbody>
                </table>
            </div>
        )
        else 
            return(<div>Loading...</div>)
            
    }
}
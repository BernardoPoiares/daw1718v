import React from 'react'

import CheckListTemplate from '../model/CheckListTemplate'
import ServerRequests from './ServerRequests'

const CHECKLISTTEMPLATE_PATH='/checkListsTemplates'

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={
            done:false,
            newCKLT_name:""
        }
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
    }

    componentDidMount(){
        this.loadIfNeeded()

    }

    changeHandler( event){
        this.setState({
            [event.target.name]: event.target.value
        });
      }

    componentDidUpdate(){
        this.loadIfNeeded()
    }

    loadIfNeeded(){
        if(this.state.done==true) return
        ServerRequests.GetAllCheckListTemplates().then(resp=>{
            return resp.json().then(json=>{
            const checkliststempsarray=[]
            if(json.properties!=null){
                json.properties.map(checklisttemp=>{
                    checkliststempsarray.push(new CheckListTemplate(checklisttemp))
                })
            }
            this.setState({checkliststemplates:checkliststempsarray,done:true})
            })
        })
    }

    submitHandler(){
        ServerRequests.CreateCheckListTemplate(this.state.newCKLT_name)
    }

    render(){
        if(this.state.done==true)
        return(<div><h1>CheckListTemplates</h1>
            <table>
                    <thead>
                        <tr>
                            <th/>
                            <th>Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.checkliststemplates.map(checklisttemp=>
                        <tr key={checklisttemp.id}>
                            <td><input id={checklisttemp.id} type="checkbox" onChange={this.addSelected}/></td>
                            <td><a href={CHECKLISTTEMPLATE_PATH+"/"+checklisttemp.id}>{checklisttemp.name}</a></td>
                        </tr>
                        )}
                    </tbody>
            </table>
            <button type="submit" onClick={this.submitDelete}>Delete</button>
            <div> 
            <fieldset>
                <legend>Create New CheckListTemplate:</legend>
                Name:<br/>
                <input type="text" name="newCKLT_name" onChange={this.changeHandler}/><br/><br/>
                <input type="submit" value="Submit" onClick={this.submitHandler}/>
            </fieldset>
        </div> 
        </div>)
        return null
    }
}
import React from 'react'

import CheckListTemplate from '../model/CheckListTemplate'
import ServerRequests from './serverRequests'
import Search from './searchComponent'
const CHECKLISTTEMPLATE_PATH='/checkListsTemplates'

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={
            done:false,selectedCLT:[],
            newCKLT_name:""
        }
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
        this.submitDeleteHandler=this.submitDeleteHandler.bind(this)
        this.addSelected=this.addSelected.bind(this)
        this.submitSearch=this.submitSearch.bind(this)
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
        ServerRequests.CreateCheckListTemplate(this.state.newCKLT_name).then(resp=>
            resp.json().then(json=>{
            const checkliststemplates=this.state.checkliststemplates
            console.log(json)
            checkliststemplates.push(new CheckListTemplate(json.properties))
            this.setState({checkliststemplates:checkliststemplates})
        })
    )
    }

    submitSearch(search){
        ServerRequests.SearchCheckListsTemplates(search).then(resp=>{
            return resp.json().then(json=>{
            const checklisttempssarray=[]
            if(json.properties!=null){
                json.properties.map(checklistemp=>{
                    checklisttempssarray.push(new CheckListTemplate(checklistemp))
                })
            }
            this.setState({checkliststemplates:checklisttempssarray,done:true})
            })  
        })
      }
  
    
    submitDeleteHandler(){
        ServerRequests.DeleteCheckListsTemplates(this.state.selectedCLT).then(resp=>{
            return resp.json().then(
                this.setState({done:false}))
        })
    }


    addSelected(ev){
        let sel_array=this.state.selectedCLT
        if(!ev.target.checked)
            sel_array=sel_array.slice(sel_array.indexOf(ev.target.id),1)
        else
            sel_array.push(ev.target.id)
        this.setState({selectedCLT:sel_array})
      }

    render(){
        if(this.state.done==true)
        return(<div><h1>CheckListTemplates</h1>
            <Search func={this.submitSearch} />
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
            <button type="submit" onClick={this.submitDeleteHandler}>Delete</button>
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
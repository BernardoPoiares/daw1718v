import React from 'react'
import  request  from './request';
import  CheckList  from '../Model/CheckList';

import ServerRequests from './serverRequests'
import Search from './searchComponent'
import CheckListTable from './tables/CheckListTable';
import ErrorComp from './errorComponent'

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={done:false,state:"",newCL_name:"",newCL_completionDate:"",selectedCL:[]}
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
        this.addSelected=this.addSelected.bind(this)
        this.submitDeleteHandler=this.submitDeleteHandler.bind(this)
        this.submitSearch=this.submitSearch.bind(this)
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
          .then(json=>{
            const checklistsarray=[]
            json.properties.map(checklist=>{
                checklistsarray.push(new CheckList(checklist))
            })
            this.setState({checklists:checklistsarray,done:true})
          }).catch(error=>{
            this.setState({error:error,done:true})
        })
      }

      
      changeHandler( event){
        this.setState({
          [event.target.name]: event.target.value
        });
      }

      submitHandler(event){
        ServerRequests.CreateCheckList({
                name:this.state.newCL_name,
                completionDate:this.state.newCL_completionDate
        }).then(
            this.setState({
                done:false
        })).catch(error=>{
            this.setState({error:error,done:true})
        })
       }

        
      addSelected(ev){
        let sel_array=this.state.selectedCL
        if(!ev.target.checked)
            sel_array=sel_array.slice(sel_array.indexOf(ev.target.id),1)
        else
            sel_array.push(ev.target.id)
        this.setState({selectedCL:sel_array})
      }


    submitDeleteHandler(checklists){
        ServerRequests.DeleteCheckLists(checklists).then(
                this.setState({done:false})
        ).catch(error=>{
            this.setState({error:error,done:true})
        })
    }

    submitSearch(search){
        ServerRequests.SearchCheckLists(search).then(json=>{
            const checklistssarray=[]
            if(json.properties!=null){
                json.properties.map(checklist=>{
                    checklistssarray.push(new CheckList(checklist))
                })
            }
            this.setState({checklists:checklistssarray,done:true})            
        }).catch(error=>{
            this.setState({error:error,done:true})
        })
    }

      getDate(){
          return new Date().toISOString().slice(0,-5)
      }


    render(){
        if(this.state.error!=null)
        return (<ErrorComp error={this.state.error}/>)
        if(this.state.done===true){
            const datestring=this.getDate()
        return(<div>
            <h2>CheckLists</h2>
            <Search func={this.submitSearch} />
            <CheckListTable checklists={this.state.checklists} checkboxfunc={this.submitDeleteHandler} buttonName='Delete'/>
                <div> 
                    <fieldset>
                        <legend>Create New CheckList:</legend>
                        Name:<br/>
                        <input type="text" name="newCL_name" onChange={this.changeHandler}/><br/>
                        CompletionDate:<br/>
                        <input type="datetime-local" min={datestring} name="newCL_completionDate" onChange={this.changeHandler}/><br/><br/>
                        <input type="submit" value="Submit" onClick={this.submitHandler}/>
                    </fieldset>
                </div> 
            </div>)
        }
        else return(<div>Loading...</div>)
    }
}
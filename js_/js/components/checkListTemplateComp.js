import React from 'react'
import {Redirect } from 'react-router-dom'


import CheckListTemplate from '../model/CheckListTemplate'
import ServerRequests from './ServerRequests'
import TableCell from './tableCell';
import CheckItemTempalteTable from './tables/CheckItemTemplateTable'
import CheckItemTemplate from '../model/CheckItemTemplate'
import ErrorComp from './errorComponent'


export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={
            name:"",
            id:props.match.params.id,
            redirect:false,
            cltp_req:{done:false,finished:false},
            cis_req:{done:false,finished:false}
        }
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
        this.update=this.update.bind(this)
        this.submitNewListHandler=this.submitNewListHandler.bind(this)
        this.submitDeleteItemsTemp=this.submitDeleteItemsTemp.bind(this)
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
        const ctlp_req_aux=this.state.cltp_req
        const cis_req_aux=this.state.cis_req

        if(ctlp_req_aux.done!=true){
            ctlp_req_aux.done=true
            this.setState({cltp_req:ctlp_req_aux})
            ServerRequests.GetCheckListTemplate(this.state.id).then(json=>{
                    ctlp_req_aux.finished=true
                this.setState({checkliststemplate:new CheckListTemplate(json.properties),ctlp_req:ctlp_req_aux})
            }).catch(error=>{
                this.setState({error:error,done:true})
            })
        }
              
        if(cis_req_aux.done!=true){
            cis_req_aux.done=true
            this.setState({cis_req_aux:cis_req_aux})
                ServerRequests.GetCheckItemsTempFromCheckkListTemp(this.state.id).then(json=>{
                    const checkitemstemparray=[]
                    if(json.properties!=null){
                        json.properties.map(checkitem=>{
                            checkitemstemparray.push(new CheckItemTemplate(checkitem))
                        })
                    }
                    cis_req_aux.finished=true
                    this.setState({checkitemstemplates:checkitemstemparray,cis_req:cis_req_aux})
                    }).catch(error=>{
                        this.setState({error:error,done:true})
                    })
        }
    }

    submitHandler(){
        ServerRequests.CreateCheckItemTemp_AddTempList(this.state.id,this.state.newCIT_name,this.state.newCIT_description).then(json=>{
                const checkitemstemps=this.state.checkitemstemplates
                checkitemstemps.push(new CheckItemTemplate(json.properties))
                this.setState({checkitemstemplates:checkitemstemps})
            }).catch(error=>{
                this.setState({error:error,done:true})
            })
    }

    update(checklisttemp){
        return ServerRequests.UpdateCheckListTemplate(this.state.id,checklisttemp.name).catch(error=>{
            this.setState({error:error,done:true})
        })
    }

    submitDeleteItemsTemp(selectedCI){
        ServerRequests.DeleteCheckItemsTemplates(selectedCI)
        .then(()=>{
            let array=this.state.checkitemstemplates
            selectedCI.map(id=>{array=array.filter(ci=>ci.id!=id)})
            this.setState({checkitemstemplates:array,done:true})
        }).catch(error=>{
            this.setState({error:error,done:true})
        })

    }

    renderCheckItemsTemplates(){
        if(this.state.cis_req.finished==true)
            return(<CheckItemTempalteTable checkitemstemps={this.state.checkitemstemplates}
                checkboxfunc={this.submitDeleteItemsTemp}
                buttonName='Delete'/>
            )
    }

    submitNewListHandler(){
        ServerRequests.CreateCheckListFromListTemplate(this.state.id,this.state.newCL_name,this.state.newCL_completionDate).then(json=>{
            if(json.properties!=null){
               this.setState({newCL_id:json.properties.id,redirect:true})
            }
    }).catch(error=>{
        this.setState({error:error,done:true})
    })
    }

    render(){
        if(this.state.error!=null)
        return (<ErrorComp error={this.state.error}/>)
        if(this.state.redirect==true)
            return <Redirect to={"/checkLists/"+this.state.newCL_id}/>
        if(this.state.cltp_req.finished==true){
            const datestring=new Date().toISOString().slice(0,-5)
        return(<div><h1>CheckListTemplate:  {this.state.checkliststemplate.id}</h1>
        <div>
            <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr key={this.state.id}>
                            <TableCell  id={this.state.id} value={this.state.checkliststemplate.name}  name="name" update={this.update} />
                        </tr>
                    </tbody>
            </table>
            </div>
            {this.renderCheckItemsTemplates()}
            <div> 
            <fieldset>
                <legend>Create and Add New CheckItemTemplate:</legend>
                Name:<br/>
                <input type="text" name="newCIT_name" onChange={this.changeHandler}/><br/>
                Description:<br/>
                <input type="text" name="newCIT_description" onChange={this.changeHandler}/><br/><br/>
                <input type="submit" value="Submit" onClick={this.submitHandler}/>
            </fieldset>
        </div>
        <div> 
            <fieldset>
                <legend>Create CheckList From This CheckListTemplate:</legend>
                Name:<br/>
                <input type="text" name="newCL_name" onChange={this.changeHandler}/><br/>
                CompletionDate:<br/>
                <input type="datetime-local" min={datestring} name="newCL_completionDate" onChange={this.changeHandler}/><br/><br/>
                <input type="submit" value="Submit" onClick={this.submitNewListHandler}/>
            </fieldset>
        </div>
        </div>)
        }
        return null
    }
}
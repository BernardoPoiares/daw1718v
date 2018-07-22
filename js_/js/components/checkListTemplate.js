import React from 'react'

import CheckListTemplate from '../model/CheckListTemplate'
import ServerRequests from './ServerRequests'
import TableCell from './tableCell';
import CheckItemTempalteTable from './tables/CheckItemTemplateTable'
import CheckItemTemplate from '../model/CheckItemTemplate'


export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={
            done:false,
            name:"",
            id:props.match.params.id
        }
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
        this.update=this.update.bind(this)
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
        ServerRequests.GetCheckListTemplate(this.state.id).then(resp=>{
            return resp.json().then(json=>{
            this.setState({checkliststemplate:new CheckListTemplate(json.properties),done:true})
            })
        })
              
        if(this.state.cis_done!=true){
            ServerRequests.GetCheckItemsTempFromCheckkListTemp(this.state.id).then(resp=>{
                return resp.json().then(json=>{
                const checkitemstemparray=[]
                if(json.properties!=null){
                    json.properties.map(checkitem=>{
                        checkitemstemparray.push(new CheckItemTemplate(checkitem))
                    })
                }
                this.setState({checkitemstemplates:checkitemstemparray,cis_done:true})
                })
                })
        }
    }

    submitHandler(){
        ServerRequests.CreateCheckItemTemp_AddTempList(this.state.id,this.state.newCIT_name,this.state.newCIT_description)
    }

    update(checklisttemp){
        ServerRequests.UpdateCheckListTemplate(this.state.id,checklisttemp.name)
    }

    renderCheckItemsTemplates(){
        if(this.state.cis_done==true)
            return(<CheckItemTempalteTable checkitemstemps={this.state.checkitemstemplates}
                checkboxfunc={this.submitRemoveItems}
                buttonName='Remove'/>
            )
    }


    render(){
        if(this.state.done==true)
        return(<div>
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
            {this.renderCheckItemsTemplates()}
            <div> 
            <fieldset>
                <legend>Create New CheckItemTemplate:</legend>
                Name:<br/>
                <input type="text" name="newCIT_name" onChange={this.changeHandler}/><br/>
                Description:<br/>
                <input type="text" name="newCIT_description" onChange={this.changeHandler}/><br/><br/>
                <input type="submit" value="Submit" onClick={this.submitHandler}/>
            </fieldset>
        </div>
        </div>)
        return null
    }
}
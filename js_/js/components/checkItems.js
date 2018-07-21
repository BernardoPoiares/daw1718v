import React from 'react'
import  request  from './request';
import  CheckItem  from '../Model/CheckItem';

import  TableCell  from './tableCell';
import StateCell from './stateCell';
import  Search from './searchComponent'

import ServerRequests from './serverRequests'

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={done:false,search:"",newCI_name:"",newCI_description:"",selectedCI:[]}
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

      changeHandler( event){
        this.setState({
          [event.target.name]: event.target.value
        });
      }

      submitHandler(event){
        request('/checkItemTemplate_checkitem/create','POST',{
            checkitemtemplate : {
                name: this.state.newCI_name,
                description: this.state.newCI_description
            }
        }).then(resp=>{
            this.setState({
                done:false
            })
          })
        }

      refresh(){
            this.setState({
                done:false
            })
      }

      update(checkitem){
        return ServerRequests.UpdateCheckItem(checkitem)
      }

      submitDeleteHandler(){
        ServerRequests.DeleteCheckItems(this.state.selectedCI).then(
            this.setState({done:false,selectedCI:[]})
        )
      }

      submitSearch(search){
        ServerRequests.SearchCheckItems(search).then(resp=>{
            return resp.json().then(json=>{
            const checkitemsarray=[]
            if(json.properties!=null){
                json.properties.map(checkitem=>{
                    checkitemsarray.push(new CheckItem(checkitem))
                })
            }
            this.setState({checkitems:checkitemsarray,done:true})
            })
            
        })
      }

      addSelected(ev){
        let sel_array=this.state.selectedCI
        if(!ev.target.checked)
            sel_array=sel_array.slice(sel_array.indexOf(ev.target.id),1)
        else
            sel_array.push(ev.target.id)
        this.setState({selectedCI:sel_array})
      }

    render(){
        if(this.state.done===true){
        return(<div>
            <h2>CheckItems</h2>
            <Search func={this.submitSearch} />
            <div>
                <table>
                    <thead>
                        <tr>
                            <th/>
                            <th>Name</th>
                            <th>Description</th>                             
                            <th>State</th> 
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.checkitems.map(checkitem=>(
                            <tr key={checkitem.id}>
                                <td><input id={checkitem.id} type="checkbox" onChange={this.addSelected}/></td>
                                <TableCell id={checkitem.id} value={checkitem.name}  name="name" update={this.update}/>
                                <TableCell id={checkitem.id} value={checkitem.description}  name="description" update={this.update}/>
                                <StateCell id={checkitem.id} value={checkitem.state}  name="state" update={this.update}/>
                            </tr>
                        ))
                        }
                    </tbody>
                </table>
                <button type="submit" onClick={this.submitDeleteHandler}>Delete</button>
                </div>
                <div> 
                    <fieldset>
                        <legend>Create New CheckItem:</legend>
                        Name:<br/>
                        <input type="text" name="newCI_name" onChange={this.changeHandler}/><br/>
                        Description:<br/>
                        <input type="text" name="newCI_description" onChange={this.changeHandler}/><br/><br/>
                        <input type="submit" value="Submit" onClick={this.submitHandler}/>
                    </fieldset>
                </div> 
            </div>)
        }
        else return(<div>Loading...</div>)
    }
}
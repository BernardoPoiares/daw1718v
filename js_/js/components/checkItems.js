import React from 'react'
import  request  from './request';
import  CheckItem  from '../Model/CheckItem';

import  TableCell  from './tableCell';

import ServerRequests from './serverRequests'

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={done:false,newCI_name:"",newCI_description:""}
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
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
                                <TableCell id={checkitem.id} value={checkitem.description}  name="description" update={this.update}/>
                            </tr>
                        ))
                        }
                    </tbody>
                </table>
                </div>
                <div> 
                    <form>
                        <fieldset>
                            <legend>Create New CheckItem:</legend>
                            Name:<br/>
                            <input type="text" name="newCI_name" onChange={this.changeHandler}/><br/>
                            Description:<br/>
                            <input type="text" name="newCI_description" onChange={this.changeHandler}/><br/><br/>
                            <input type="submit" value="Submit" onClick={this.submitHandler}/>
                        </fieldset>
                    </form>
                </div> 
            </div>)
        }
        else return(<div/>)
    }
}
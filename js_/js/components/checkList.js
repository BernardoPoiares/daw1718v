import React from 'react'
import ServerRequests from './serverRequests'
import TableCell from './tableCell'
import StateCell from './tableCell'
import DateCell from './dateCell'
import CheckList from '../model/CheckList'
import CheckItem from '../model/CheckItem'


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
        if(this.state.list_done!=true){
            ServerRequests.GetCheckList(this.state.id).then(resp=>{
                return resp.json().then(json=>{
                const checklist=new CheckList(json.properties)
                this.setState({checklist:checklist,list_done:true})
                })
            })
        }
            
        if(this.state.cis_done!=true){
            console.log(34)
            ServerRequests.GetCheckItemsFromList(this.state.id).then(resp=>{
                return resp.json().then(json=>{
                const checkitemsarray=[]
                if(json.properties!=null){
                    json.properties.map(checkitem=>{
                        checkitemsarray.push(new CheckItem(checkitem))
                    })
                }
                this.setState({checkitems:checkitemsarray,cis_done:true})
                })
                })
        }
    }
    
    update(checkitem){
        return ServerRequests.UpdateCheckList(checkitem)
      }


      renderCheckItems(){
          if(this.state.cis_done==true){
              return( <div>
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
                <button type="submit">Remove</button>
                </div>)
          }
      }

    render(){
        if(this.state.list_done==true)
            return(<div>
                <h3>Checklist : {this.state.checklist.id}</h3>
                    <div>
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
                {this.renderCheckItems()}
            </div>
            )
        else 
            return(<div>Loading...</div>)
            
    }
}
import React from 'react'
import  request  from './request';
import  CheckItem  from '../Model/CheckItem';
import CheckList from '../Model/CheckList'
import TableCell from './tableCell'
import StateCell from './stateCell'
import ServerRequests from './serverRequests';
import CheckListTable from './tables/CheckListTable'
import ListCheckLists from './ListCheckLists'
export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={done:false,id:props.match.params.id,checkitem_done:false,checklists_done:false}
        this.renderCheckLists=this.renderCheckLists.bind(this)
        this.update=this.update.bind(this)
        this.addList=this.addList.bind(this)
        this.submitDeleteHandler=this.submitDeleteHandler.bind(this)
    }


    componentDidMount () {
        this.loadIfNeeded()
      }
    
      componentDidUpdate () {
        this.loadIfNeeded()
      }
    
      loadIfNeeded () {
          if(this.state.checkitem_done===false)
            request('/checkItem/'+this.state.id,'GET')
            .then(resp=>{
                return resp.json().then(json=>{
                this.setState({checkitem: new CheckItem(json.properties),checkitem_done:true})
                })
            })
            if(this.state.checklists_done===false)
                ServerRequests.GetCheckListsFromCheckItem(this.state.id).then(resp=>{
                    return resp.json().then(json=>{
                    const checklistssarray=[]
                    if(json.properties!=null){
                        json.properties.map(checklist=>{
                            checklistssarray.push(new CheckList(checklist))
                        })
                    }
                    this.setState({checklists:checklistssarray,checklists_done:true})
                    })
                    
                })
      }


      
      update(checkitem){
        return ServerRequests.UpdateCheckItem(checkitem)
      }

      renderCheckLists(){
          if(this.state.checklists_done)
            return(<CheckListTable checklists={this.state.checklists} checkboxfunc={this.submitDeleteHandler} buttonName='Remove'/>
        )
      }
      addList(checklist){
          ServerRequests.AddCheckItemToCheckList(this.state.id,checklist)
      }

      submitDeleteHandler(checkLists){
          ServerRequests.RemoveCheckItemFromCheckLists(this.state.id,checkLists)
      }

    render(){
        if(this.state.checkitem_done===true){
        return(<div>
            <h2>CheckItem:{this.state.checkitem.name}</h2>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>State</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr key={this.state.checkitem.id}>
                            <TableCell id={this.state.checkitem.id} value={this.state.checkitem.name}  name="name" update={this.update}/>
                            <TableCell id={this.state.checkitem.id} value={this.state.checkitem.description}  name="description" update={this.update}/>
                            <StateCell id={this.state.checkitem.id} value={this.state.checkitem.state}  name="state" update={this.update}/>
                        </tr>
                    </tbody>
                </table>
            </div>
            {this.renderCheckLists()}
            <ListCheckLists func={this.addList}/>
            </div>)
        }
        else return(<div/>)
    }
}

import React from 'react'
import  request  from './request';
import  CheckItem  from '../model/CheckItem';
import CheckList from '../model/CheckList'
import TableCell from './tableCell'
import StateCell from './stateCell'
import ServerRequests from './serverRequests';
import CheckListTable from './tables/CheckListTable'
import ListCheckLists from './ListCheckLists'
import ErrorComp from './errorComponent'

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={
            done:false,
            id:props.match.params.id,
            checkitem_done:false,
            checklists_done:false,        
            cl_req:{done:false,finished:false},
            cis_req:{done:false,finished:false}
        }
        this.renderCheckLists=this.renderCheckLists.bind(this)
        this.update=this.update.bind(this)
        this.addList=this.addList.bind(this)
        this.submitRemoveHandler=this.submitRemoveHandler.bind(this)
    }


    componentDidMount () {
        this.loadIfNeeded()
      }
    
      componentDidUpdate () {
        this.loadIfNeeded()
      }
    
      loadIfNeeded () {
        const ci_req_aux=this.state.ci_req
        const cls_req_aux=this.state.cls_req 

        if(ci_req_aux.done!=true){
            ci_req_aux.done=true
            request('/checkItem/'+this.state.id,'GET')
            .then(json=>{
                ci_req_aux.finished=true
                this.setState({checkitem: new CheckItem(json.properties),ci_req:ci_req_aux})
            }).catch(error=>{
                this.setState({error:error,done:true})
            })
        }
        if(cls_req_aux.done===false){
            cls_req_aux.done=true
                ServerRequests.GetCheckListsFromCheckItem(this.state.id).then(json=>{
                    const checklistssarray=[]
                    if(json.properties!=null){
                        json.properties.map(checklist=>{
                            checklistssarray.push(new CheckList(checklist))
                        })
                    }
                    cls_req_aux.finished=true
                    this.setState({checklists:checklistssarray,cls_req:cls_req_aux})               
                }).catch(error=>{
                    this.setState({error:error,done:true})
                })
        }
      }


      
      update(checkitem){
        return ServerRequests.UpdateCheckItem(checkitem).catch(error=>{
            this.setState({error:error,done:true})
        })
      }

      renderCheckLists(){
          if(this.state.checklists_done)
            return(<CheckListTable checklists={this.state.checklists} checkboxfunc={this.submitRemoveHandler} buttonName='Remove'/>
        )
      }
      addList(checklist){
          ServerRequests.AddCheckItemToCheckList(this.state.id,checklist)
          .then(json=>{
            const checklistsarray=this.state.checklists
            if(json.properties!=null)
                checklistsarray.push(new CheckList(json.properties))
            this.setState({checkitems:checklistsarray,done:true})  
          })
          .catch(error=>{
            this.setState({error:error,done:true})
        })
      }

      submitRemoveHandler(checkLists){
          ServerRequests.RemoveCheckItemFromCheckLists(this.state.id,checkLists)
          .then(()=>{
            let array=this.state.checklists
            checkLists.map(id=>{array=array.filter(ci=>ci.id!=id)})
            this.setState({checklists:array,done:true})
            })
          .catch(error=>{
            this.setState({error:error,done:true})
        })
      }

    render(){
        if(this.state.error!=null)
        return (<ErrorComp error={this.state.error}/>)
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

import React from 'react'
import ServerRequests from './serverRequests'
import TableCell from './tableCell'
import StateCell from './tableCell'
import DateCell from './dateCell'
import CheckList from '../model/CheckList'
import CheckItem from '../model/CheckItem'
import CheckItemsTable from './tables/CheckItemsTable'
import CreateCheckItem from './creates/createCheckItem'
import ErrorComp from './errorComponent'



export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={
            done:false,
            id:props.match.params.id,
            cl_req:{done:false,finished:false},
            cis_req:{done:false,finished:false}
        }
        this.submitHandler=this.submitHandler.bind(this)
        this.submitRemoveItems=this.submitRemoveItems.bind(this)
    }
    componentDidMount () {
        this.loadIfNeeded()
      }
    
      componentDidUpdate () {
        this.loadIfNeeded()
      }
    
      loadIfNeeded () {
            const cl_req_aux=this.state.cl_req
            const cis_req_aux=this.state.cis_req  

            if(cl_req_aux.done!=true){
                cl_req_aux.done=true
                this.setState({cl_req:cl_req_aux})
            ServerRequests.GetCheckList(this.state.id).then(json=>{
                const checklist=new CheckList(json.properties)
                cl_req_aux.finished=true
                this.setState({checklist:checklist,cl_req:cl_req_aux})
            }).catch(error=>{
                this.setState({error:error})
            })
        }

        if(cis_req_aux.done!=true){
            cis_req_aux.done=true
            this.setState({cis_req:cis_req_aux})
            ServerRequests.GetCheckItemsFromList(this.state.id).then(json=>{
                const checkitemsarray=[]
                if(json.properties!=null){
                    json.properties.map(checkitem=>{
                        checkitemsarray.push(new CheckItem(checkitem))
                    })
                }
                cis_req_aux.finished=true
                this.setState({checkitems:checkitemsarray,cis_req:cis_req_aux})
                }).catch(error=>{
                    this.setState({error:error})
                })
        }
    }
    
    update(checkitem){
        return ServerRequests.UpdateCheckList(checkitem).catch(error=>{
            this.setState({error:error,done:true})
        })
      }

    submitRemoveItems(checkitems){
        return ServerRequests.RemoveCheckItemsFromCheckList(this.state.id,checkitems)
        .then(json=>{
            let array=this.state.checkitems
            checkitems.map(id=>{array=array.filter(ci=>ci.id!=id)})
            this.setState({checkitems:array,done:true})
        }).catch(error=>{
            this.setState({error:error,done:true})
        })
    }

    renderCheckItems(){
        if(this.state.cis_req.finished==true){
            return(<CheckItemsTable checkitems={this.state.checkitems}
                checkboxfunc={this.submitRemoveItems}
                buttonName='Remove'/>
            )
        }
    }

    submitHandler(checkitem){
        ServerRequests.CreateCheckItem_AddCheckList(this.state.id,checkitem).then(json=>{
            const array=this.state.checkitems
            array.push(new CheckItem(json.properties))
            this.setState({checkitems:array,done:true})
          }).catch(error=>{
                this.setState({error:error,done:true})
            })
    }


    render(){
        if(this.state.error!=null)
            return (<ErrorComp error={this.state.error}/>)
        if(this.state.cl_req.finished==true)
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
                <CreateCheckItem func={this.submitHandler}/>
            </div>
            )
        else 
            return(<div>Loading...</div>)
            
    }
}
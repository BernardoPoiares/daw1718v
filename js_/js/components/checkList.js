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
            id:props.match.params.id
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
        if(this.state.list_done!=true){
            ServerRequests.GetCheckList(this.state.id).then(json=>{
                const checklist=new CheckList(json.properties)
                this.setState({checklist:checklist,list_done:true})
            }).catch(error=>{
                this.setState({error:error,done:true})
            })
        }
            
        if(this.state.cis_done!=true){
            ServerRequests.GetCheckItemsFromList(this.state.id).then(json=>{
                const checkitemsarray=[]
                if(json.properties!=null){
                    json.properties.map(checkitem=>{
                        checkitemsarray.push(new CheckItem(checkitem))
                    })
                }
                this.setState({checkitems:checkitemsarray,cis_done:true})
                }).catch(error=>{
                    this.setState({error:error,done:true})
                })
        }
    }
    
    update(checkitem){
        return ServerRequests.UpdateCheckList(checkitem).catch(error=>{
            this.setState({error:error,done:true})
        })
      }

    submitRemoveItems(checkitems){
        ServerRequests.RemoveCheckItemsFromCheckList(this.state.id,checkitems).catch(error=>{
            this.setState({error:error,done:true})
        })
    }

    renderCheckItems(){
        if(this.state.cis_done==true)
            return(<CheckItemsTable checkitems={this.state.checkitems}
                checkboxfunc={this.submitRemoveItems}
                buttonName='Remove'/>
            )
    }

    submitHandler(checkitem){
        ServerRequests.CreateCheckItem_AddCheckList(this.state.id,checkitem).then(
            this.setState({cis_done:false}).catch(error=>{
                this.setState({error:error,done:true})
            })
        )
    }


    render(){
        if(this.state.error!=null)
        return (<ErrorComp error={this.state.error}/>)
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
                <CreateCheckItem func={this.submitHandler}/>
            </div>
            )
        else 
            return(<div>Loading...</div>)
            
    }
}
import React from 'react'
import ServerRequests from './serverRequests'
import CheckList from '../model/CheckList.js'
import ErrorComp from './errorComponent'


export default class extends React.Component{
    constructor(props){
        super(props);
        this.state={
            done:false
        }
        this.retFunc=props.func
        this.onChangeHandler=this.onChangeHandler.bind(this)
        this.onClinkHandler=this.onClinkHandler.bind(this)
    }


    componentDidMount () {
        this.loadIfNeeded()
      }
    
      componentDidUpdate () {
        this.loadIfNeeded()
      }
    
      loadIfNeeded () {
          if(this.state.done===true) return 
          ServerRequests.GetAllCheckLists().then(json=>{
            const checklistsarray=[]
            json.properties.map(checklist=>{
                checklistsarray.push(new CheckList(checklist))
            })
            this.setState({checklists:checklistsarray,done:true})
          }).catch(error=>{
            this.setState({error:error,done:true})
        })
        }

          onChangeHandler(ev){
              this.setState({
                  listToAdd:ev.target.value
              })
          }

          onClinkHandler(){
              this.retFunc(this.state.listToAdd)
          }

    render(){
        if(this.state.error!=null)
        return (<ErrorComp error={this.state.error}/>)
        if(this.state.done===true) 
        return(<div>
            <select onChange={this.onChangeHandler}>
            {this.state.checklists.map(checklist=>
            <option key={checklist.id} value={checklist.id}>{checklist.name}</option>
            )}
            </select>
            <button type="submit" onClick={this.onClinkHandler}>Add List</button>
            </div>)
        else
        return null
    }
}
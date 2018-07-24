import React from 'react'
import  request  from './request';
import  CheckItem  from '../Model/CheckItem';
import  Search from './searchComponent'

import ServerRequests from './serverRequests'
import CheckItemsTable from './tables/CheckItemsTable'
import CreateCheckItem from './creates/createCheckItem'
import ErrorComp from './errorComponent'

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={done:false,search:"",newCI_name:"",newCI_description:""}
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
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
            .then(json=>{
                const checkitemsarray=[]
                json.properties.map(checkitem=>{
                    checkitemsarray.push(new CheckItem(checkitem))
                })
                this.setState({checkitems:checkitemsarray,done:true})
                })
            .catch(error=>{
                this.setState({error:error,done:true})
            })
      }

      changeHandler( event){
        this.setState({
          [event.target.name]: event.target.value
        });
      }

      submitHandler(checkitem){
        ServerRequests.CreateCheckItem(checkitem).then(json=>{
            const checkitems=this.state.checkitems
            checkitems.push(new CheckItem(json.properties))
            this.setState({checkitems:checkitems,done:true})
          }).catch(error=>{
            this.setState({error:error,done:true})
        })
        }

      refresh(){
            this.setState({
                done:false
            })
      }


      submitDeleteHandler(selectedCI){
        ServerRequests.DeleteCheckItems(selectedCI).then(()=>{
            let array=this.state.checkitems
            selectedCI.map(id=>{array=array.filter(ci=>ci.id!=id)})
            this.setState({checkitems:array,done:true})
        }
        ).catch(error=>{
            this.setState({error:error,done:true})
        })
      }

      submitSearch(search){
        ServerRequests.SearchCheckItems(search).then(json=>{
            const checkitemsarray=[]
            if(json.properties!=null){
                json.properties.map(checkitem=>{
                    checkitemsarray.push(new CheckItem(checkitem))
                })
            }
            this.setState({checkitems:checkitemsarray,done:true})  
        }).catch(error=>{
            this.setState({error:error,done:true})
        })
      }
  

    render(){
        if(this.state.error!=null)
            return (<ErrorComp error={this.state.error}/>)
        if(this.state.done===true)
            return(
                <div>
                    <h2>CheckItems</h2>
                    <Search func={this.submitSearch} />
                    <CheckItemsTable checkitems={this.state.checkitems} checkboxfunc={this.submitDeleteHandler} buttonName='Delete'/>
                    <CreateCheckItem func={this.submitHandler}/> 
                </div>
            )
        else return null
    }
}
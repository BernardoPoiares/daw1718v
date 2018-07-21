import React from 'react'

export default class extends React.Component{

    constructor(props){
        super(props)
        this.changeHandler=this.changeHandler.bind(this)
        
        this.submitSearch=this.submitSearch.bind(this)
        this.state={
            search:"",
            func:props.func
        }
    }

    
      
    changeHandler( event){
        this.setState({
            search: event.target.value
        });
      }

      submitSearch(){
          this.state.func(this.state.search)
      }
    

    render(){
        return( <div class="search-container">
        <fieldset>
            <input type="text" placeholder="Search.." name="search" onChange={this.changeHandler}/>
            <button type="submit" onClick={this.submitSearch}>Search</button>
            </fieldset>    
            </div>)
    }
}
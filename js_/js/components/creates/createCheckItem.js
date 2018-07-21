import React from 'react'

export default class extends React.Component{

    constructor(props){
        super(props)
        this.onClick=this.onClick.bind(this)
        this.func=props.func
        this.changeHandler=this.changeHandler.bind(this)
        this.submitHandler=this.submitHandler.bind(this)
        this.state={
            name:"",description:"",func:props.func
        }
    }

    onClick(){
        this.setState({updateView:true})
    }

    changeHandler(event){
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    submitHandler(){
        this.func({name:this.state.name,description:this.state.description})
    }

    render(){
        return( <div> 
            <fieldset>
                <legend>Create New CheckItem:</legend>
                Name:<br/>
                <input type="text" name="name" onChange={this.changeHandler}/><br/>
                Description:<br/>
                <input type="text" name="description" onChange={this.changeHandler}/><br/><br/>
                <input type="submit" value="Submit" onClick={this.submitHandler}/>
            </fieldset>
        </div> )
    }
}
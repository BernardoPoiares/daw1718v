import React from 'react'
import ServerRequests from './serverRequests'

export default class extends React.Component{

    constructor(props){
        super(props)
        this.onClick=this.onClick.bind(this)
        this.update=props.update
        this.changeHandler=this.changeHandler.bind(this)
        
        this.submitHandler=this.submitHandler.bind(this)
        this.state={
            updateView:false,
            name:props.name,
            value:props.value,
            id:props.id
        }
    }

    onClick(){
        this.setState({updateView:true})
    }

    changeHandler(event){
        console.log(event.target)
        this.setState({
            value: event.target.value
        });
    }

    submitHandler(){
        this.update({[this.state.name]:this.state.value,id:this.state.id}).then(
            this.setState({
                updateView:false
            })
        )
    }

    render(){
        if(this.state.updateView==false)
            return(<td onClick={this.onClick}>{this.state.value}</td>)
        else
            return(<td>
                <input type="text" name={this.state.name} onChange={this.changeHandler} value={this.state.value}/>
                <button type="submit" onClick={this.submitHandler}>Submit</button>
                </td>
        )
    }
}
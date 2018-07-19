import React from 'react'

export default class extends React.Component{

    constructor(props){
        super(props)
        this.onClick=this.onClick.bind(this)
        this.update=props.update
        this.changeHandler=this.changeHandler.bind(this)
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
        this.update({[this.state.name]:event.target.value,id:this.state.id}).then(
            this.setState({
                value:new Date(event.target.value),
                updateView:false
            })
        )
    }

    render(){
        if(this.state.updateView==false)
            return(<td onClick={this.onClick}>{this.state.value.toLocaleString()}</td>)
        else{
            const date=this.state.value.toISOString().slice(0,-5)
            return(<td>
                 <input type="datetime-local" value={date} min={date} name={this.state.name} onChange={this.changeHandler}/><br/><br/>
                </td>)
        }
    }
}
import React from 'react'
import ServerRequests from './serverRequests'

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
                value:event.target.value,
                updateView:false
            })
        )
    }

    listBody(){
        if(this.state.value=="completed"){
            return(<select onChange={this.changeHandler}>
                <option value="completed" selected>completed</option>
                <option value="uncompleted">uncompleted</option>
            </select>)
        }else{
            return(<select onChange={this.changeHandler}>
                <option value="completed">completed</option>
                <option value="uncompleted" selected>uncompleted</option>
            </select>)
        }
    }

    render(){
        if(this.state.updateView==false)
            return(<td onClick={this.onClick}>{this.state.value}</td>)
        else
            return(<td>
                    {this.listBody()}
                </td>
        )
    }
}
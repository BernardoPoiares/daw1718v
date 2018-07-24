import React from 'react'

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

        this.setState({
            value: new Date(event.target.value)
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
        if(this.state.updateView==false){
            return(<td onClick={this.onClick}>{this.state.value.toLocaleString()}</td>)
        }else{
            const date=this.state.value.toISOString().slice(0,-5)
            const actualdate=new Date().toISOString().slice(0,-5)
            return(<td>
                 <input type="datetime-local" value={date} min={actualdate} name={this.state.name} onChange={this.changeHandler}/><br/><br/>
                 <button type="submit" value="Submit" onClick={this.submitHandler}>Submit</button>
                </td>)
        }
    }
}
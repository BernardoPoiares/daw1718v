import React from 'react'

export default class extends React.Component{

    constructor(props){
        super(props)
        this.onClick=this.onClick.bind(this)
        this.state={
            updateView:false,
            value:props.value
        }
    }

    onClick(){
        this.setState({updateView:true})
    }

    render(){
        if(this.state.updateView==false)
            return(<td onClick={this.onClick}>{this.state.value}</td>)
        else
            return(<td>
                <input type="text" value={this.state.value}/>
                </td>
        )
    }
}
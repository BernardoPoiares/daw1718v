import React from 'react'
import  request  from './request';
import  CheckItem  from '../Model/CheckItem';

export default class extends React.Component{
    constructor(props){
        super(props)
        this.state={done:false,id:props.match.params.id}
    }


    componentDidMount () {
        this.loadIfNeeded()
      }
    
      componentDidUpdate () {
        this.loadIfNeeded()
      }
    
      loadIfNeeded () {
          if(this.state.done===true)return
          request('/checkItem/'+this.state.id,'GET')
          .then(resp=>{
            return resp.json().then(json=>{
            this.setState({checkitem: new CheckItem(json.properties),done:true})
            })
          })
      }




    render(){
        if(this.state.done===true){
            console.log(3)
        return(<div>
            <h2>CheckItem</h2>
                <div>
                    <ul>
                        <li value="Name = ">{this.state.checkitem.name}</li>
                        <li value="Description = ">{this.state.checkitem.description}</li>
                    </ul>
                </div>  
            </div>)
        }
        else return(<div/>)
    }
}
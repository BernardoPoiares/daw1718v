import React from 'react'

import TableCell from '../tableCell'
import StateCell from '../stateCell'


export default class extends React.Component{

    constructor(props){
        super(props)
        this.state={
            checkitems:props.checkitems,
            checkboxfunc:props.checkboxfunc,
            buttonName:props.buttonName,
            selectedCI:[]
        }
        this.addSelected=this.addSelected.bind(this)
        this.submitSelecteds=this.submitSelecteds.bind(this)
    }
    

    addSelected(ev){
        let sel_array=this.state.selectedCI
        if(!ev.target.checked)
            sel_array=sel_array.slice(sel_array.indexOf(ev.target.id),1)
        else
            sel_array.push(ev.target.id)
        this.setState({selectedCI:sel_array})
      }

      submitSelecteds(){
          this.state.checkboxfunc(this.state.selectedCI)
      } 


    render(){
        return(
                <div>
                    <table>
                        <thead>
                            <tr>
                                <th/>
                                <th>Name</th>
                                <th>Description</th>                             
                                <th>State</th> 
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.checkitems.map(checkitem=>(
                                <tr key={checkitem.id}>
                                    <td><input id={checkitem.id} type="checkbox" onChange={this.addSelected}/></td>
                                    <TableCell id={checkitem.id} value={checkitem.name}  name="name" update={this.update}/>
                                    <TableCell id={checkitem.id} value={checkitem.description}  name="description" update={this.update}/>
                                    <StateCell id={checkitem.id} value={checkitem.state}  name="state" update={this.update}/>
                                </tr>
                            ))
                            }
                        </tbody>
                    </table>
                    <button type="submit" onClick={this.submitSelecteds}>{this.state.buttonName}</button>
                </div>
        )
    }
}
import React from 'react'


const CHECKITEM_PATH="/checkItems/"
export default class extends React.Component{

    constructor(props){
        super(props)
        this.checkboxfunc=props.checkboxfunc
        this.state={
            checkitems:props.checkitems,
            buttonName:props.buttonName,
            updateView:props.updateView,
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
          this.checkboxfunc(this.state.selectedCI)
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
                                    <td><a href={CHECKITEM_PATH+checkitem.id}>{checkitem.name}</a></td>
                                    <td>{checkitem.description}</td>
                                    <td>{checkitem.state}</td>
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
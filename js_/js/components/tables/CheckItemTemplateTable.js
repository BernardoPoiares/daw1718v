import React from 'react'


export default class extends React.Component{

    constructor(props){
        super(props)
        this.checkboxfunc=props.checkboxfunc
        this.state={
            checkitemstemps:props.checkitemstemps,
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
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.checkitemstemps.map(checkitemtemp=>(
                                <tr key={checkitemtemp.id}>
                                    <td><input id={checkitemtemp.id} type="checkbox" onChange={this.addSelected}/></td>
                                    <td>{checkitemtemp.name}</td>
                                    <td>{checkitemtemp.description}</td>
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
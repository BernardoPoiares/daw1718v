import React from 'react'



const CHECKLIST_PATH="/checkLists/"

export default class extends React.Component{

    constructor(props){
        super(props)
        this.checkboxfunc=props.checkboxfunc
        this.state={
            checklists:props.checklists,
            buttonName:props.buttonName,
            updateView:props.updateView,
            selectedCL:[]
        }
        this.addSelected=this.addSelected.bind(this)
        this.submitSelecteds=this.submitSelecteds.bind(this)
    }
    

    addSelected(ev){
        let sel_array=this.state.selectedCL
        if(!ev.target.checked)
            sel_array=sel_array.slice(sel_array.indexOf(ev.target.id),1)
        else
            sel_array.push(ev.target.id)
        this.setState({selectedCL:sel_array})
      }

      submitSelecteds(){
          this.checkboxfunc(this.state.selectedCL)
      } 


    render(){
        return(
                <div>
                    <table>
                    <thead>
                        <tr>
                            <th/>
                            <th>Name</th>
                            <th>CompletionDate</th> 
                        </tr>
                    </thead>
                    <tbody>
                    {this.state.checklists.map(checklist=>(
                        <tr key={checklist.id}>
                            <td><input id={checklist.id} type="checkbox" onChange={this.addSelected}/></td>
                                <td><a href={CHECKLIST_PATH+checklist.id}>{checklist.name}</a></td>
                                <td >{checklist.completionDate.toLocaleString()}</td>
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
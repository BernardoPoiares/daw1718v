import React from 'react'
import TableCell from '../tableCell';
import ServerRequests from '../serverRequests'
import ErrorComp from '../errorComponent'


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
        this.update=this.update.bind()
    }
    
    static getDerivedStateFromProps(nextProps, prevState){
        if (prevState.checkitemstemps !== nextProps.checkitemstemps) {
            return {
                checkitemstemps: nextProps.checkitemstemps,
            }
          }
        return null;

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

      update(checkitemtemp){
        return ServerRequests.UpdateCheckItemTemplate(checkitemtemp).catch(error=>{
            this.setState({error:error,done:true})
        })
      }

    render(){
        if(this.state.error!=null)
            return (<ErrorComp error={this.state.error}/>)
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
                                    <TableCell id={checkitemtemp.id} value={checkitemtemp.name}  name="name" update={this.update}/>
                                    <TableCell id={checkitemtemp.id} value={checkitemtemp.description}  name="description" update={this.update} />
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
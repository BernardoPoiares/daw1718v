import React from 'react'
import { NavLink } from 'react-router-dom'

export default class extends React.Component{
    constructor(props){
        super(props)
    }

    render(){
        return(<nav>
            <ul>
              <li><NavLink to='/'>Home</NavLink></li>
            </ul>
          </nav>)
    }
}
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
              <li><NavLink to='/login'>login</NavLink></li>
              <li><NavLink to='/checkItem'>checkItem</NavLink></li>
            </ul>
          </nav>)
    }
}
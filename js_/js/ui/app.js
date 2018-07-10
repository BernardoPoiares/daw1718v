import React from 'react'
import Navigation from './navigation'

import Main from './main'

export default class App extends React.Component{
render(){
    return (
        <div className='app'>
            <h1>React Router Demo</h1>
            <Navigation />
            <Main />
      </div>
    )
}

}
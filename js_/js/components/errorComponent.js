import React from 'react'


export default class extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        error: props.error,
      };
    }

  
    render() {
      if (this.state.error) {
        return (
          <div>
            <h2>{"Oh-no! Something went wrong"}</h2>
            <p className="red">
              {this.state.error && this.state.error.toString()}
            </p>
          </div>
        );
      }
      return null;
    }
  }
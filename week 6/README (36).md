import React, { Component } from "react";

let nextTransactionId = 1;

class ComplaintRegister extends Component {
  constructor(props) {
    super(props);
    this.state = {
      ename: "",
      complaint: "",
      NumberHolder: 0
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  // Generic change handler: works for every controlled input because it
  // uses event.target.name to decide which piece of state to update
  handleChange(event) {
    this.setState({ [event.target.name]: event.target.value });
  }

  handleSubmit(event) {
    // Generate a transaction / reference id for this complaint
    const transactionId = nextTransactionId++;
    this.setState({ NumberHolder: transactionId });

    const msg =
      "Thanks " +
      this.state.ename +
      "!\nYour Complaint was Submitted. Transaction ID is: " +
      transactionId;
    alert(msg);
    event.preventDefault();

    // Reset the form after submission
    this.setState({ ename: "", complaint: "" });
  }

  render() {
    return (
      <div>
        <h1>Register your complaints here!!!</h1>
        <form onSubmit={this.handleSubmit}>
          <label>
            Name:
            <input
              type="text"
              name="ename"
              value={this.state.ename}
              onChange={this.handleChange}
            />
          </label>
          <br />
          <label>
            Complaint:
            <textarea
              name="complaint"
              value={this.state.complaint}
              onChange={this.handleChange}
            />
          </label>
          <br />
          <button type="submit">Submit</button>
        </form>
      </div>
    );
  }
}

export default ComplaintRegister;

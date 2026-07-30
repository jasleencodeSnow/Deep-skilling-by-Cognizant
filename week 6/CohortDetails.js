import React, { Component } from "react";

class Register extends Component {
  constructor(props) {
    super(props);
    this.state = {
      fullName: "",
      email: "",
      password: "",
      errors: {
        fullName: "",
        email: "",
        password: ""
      }
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.validateForm = this.validateForm.bind(this);
  }

  // Validates a single field as the user types and stores the message
  // (or an empty string when valid) in state.errors
  handleChange(event) {
    const { name, value } = event.target;
    let errors = { ...this.state.errors };

    switch (name) {
      case "fullName":
        errors.fullName =
          value.length < 5 ? "Full Name must be 5 characters long!" : "";
        break;
      case "email":
        // Simple RFC-lite email pattern: text@text.text
        errors.email = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)
          ? ""
          : "Email is not valid!";
        break;
      case "password":
        errors.password =
          value.length < 8 ? "Password must be 8 characters long!" : "";
        break;
      default:
        break;
    }

    this.setState({ errors, [name]: value });
  }

  // Returns true only when every field is both non-empty and error-free
  validateForm(errors) {
    let valid = true;
    Object.values(errors).forEach((val) => {
      val.length > 0 && (valid = false);
    });
    if (
      this.state.fullName === "" ||
      this.state.email === "" ||
      this.state.password === ""
    ) {
      valid = false;
    }
    return valid;
  }

  handleSubmit = (event) => {
    event.preventDefault();
    if (this.validateForm(this.state.errors)) {
      alert("Valid Form");
    } else {
      if (this.state.errors.fullName !== "") {
        alert(this.state.errors.fullName);
      }
      if (this.state.errors.email !== "") {
        alert(this.state.errors.email);
      }
      if (this.state.errors.password !== "") {
        alert(this.state.errors.password);
      }
    }
  };

  render() {
    const { errors } = this.state;

    return (
      <div>
        <h1>Register Here!!!</h1>
        <form onSubmit={this.handleSubmit} noValidate>
          <label>
            Name:
            <input
              type="text"
              name="fullName"
              value={this.state.fullName}
              onChange={this.handleChange}
            />
          </label>
          <br />
          <label>
            Email:
            <input
              type="text"
              name="email"
              value={this.state.email}
              onChange={this.handleChange}
            />
          </label>
          <br />
          <label>
            Password:
            <input
              type="password"
              name="password"
              value={this.state.password}
              onChange={this.handleChange}
            />
          </label>
          <br />
          <button type="submit">Submit</button>

          {(errors.fullName || errors.email || errors.password) && (
            <div className="error-summary">
              {errors.fullName && <p>{errors.fullName}</p>}
              {errors.email && <p>{errors.email}</p>}
              {errors.password && <p>{errors.password}</p>}
            </div>
          )}
        </form>
      </div>
    );
  }
}

export default Register;

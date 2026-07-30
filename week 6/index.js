import React, { Component } from "react";
import EmployeesList from "./EmployeesList";
import ThemeContext from "./ThemeContext";

const employees = [
  { id: 1, name: "Ann Kumar", designation: "Software Engineer" },
  { id: 2, name: "Ravi Shah", designation: "QA Analyst" },
  { id: 3, name: "Priya Nair", designation: "Project Manager" }
];

class App extends Component {
  constructor(props) {
    super(props);
    this.state = { theme: "light" };
    this.toggleTheme = this.toggleTheme.bind(this);
  }

  toggleTheme() {
    this.setState((prevState) => ({
      theme: prevState.theme === "light" ? "dark" : "light"
    }));
  }

  render() {
    return (
      // Step 6b/6c: theme provider wraps the entire JSX of App,
      // its value comes from component state
      <ThemeContext.Provider value={this.state.theme}>
        <div className={`App ${this.state.theme}`}>
          <h1>Employee Management</h1>
          <button onClick={this.toggleTheme}>Toggle Theme</button>
          {/* Step 6d: theme is no longer passed down as a prop */}
          <EmployeesList employees={employees} />
        </div>
      </ThemeContext.Provider>
    );
  }
}

export default App;

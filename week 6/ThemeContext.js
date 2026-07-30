import React, { useContext } from "react";
import ThemeContext from "./ThemeContext";

function EmployeeCard(props) {
  // Step 8a/8b: import ThemeContext and read its current value with useContext()
  const theme = useContext(ThemeContext);

  return (
    <div className={`employee-card ${theme}`}>
      <h3>{props.employee.name}</h3>
      <p>{props.employee.designation}</p>
      {/* Step 8c: use the context value to set the button's className */}
      <button className={`btn-${theme}`}>View Profile</button>
    </div>
  );
}

export default EmployeeCard;

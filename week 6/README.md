import React from "react";
import EmployeeCard from "./EmployeeCard";

// Step 7: theme name is NOT passed explicitly to EmployeeCard anymore -
// EmployeeCard reads it directly from ThemeContext via useContext()
function EmployeesList(props) {
  return (
    <div className="employee-list">
      {props.employees.map((employee) => (
        <EmployeeCard key={employee.id} employee={employee} />
      ))}
    </div>
  );
}

export default EmployeesList;

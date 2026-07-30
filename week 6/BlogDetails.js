import React from "react";
import BookDetails from "./BookDetails";
import BlogDetails from "./BlogDetails";
import CourseDetails from "./CourseDetails";
import "./App.css";

function App() {
  return (
    <div>
      <div className="row">
        <div className="mystyle1">
          <CourseDetails />
        </div>
        <div className="st2">
          <BookDetails />
        </div>
        <div className="v1">
          <BlogDetails />
        </div>
      </div>
    </div>
  );
}

export default App;

import React from "react";
import { blogs } from "./data";

// Conditional rendering using the ternary operator ( condition ? a : b )
function BlogDetails(props) {
  const blogList = props.blogs || blogs;

  const content =
    blogList.length > 0 ? (
      blogList.map((blog) => (
        <div key={blog.id}>
          <h3>{blog.title}</h3>
          <p>
            <strong>{blog.author}</strong>
          </p>
          <p>{blog.content}</p>
        </div>
      ))
    ) : (
      <p>No blog posts yet.</p>
    );

  return (
    <div className="v1">
      <h1>Blog Details</h1>
      {content}
    </div>
  );
}

export default BlogDetails;

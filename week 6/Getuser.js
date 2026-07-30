import React from "react";
import { books } from "./data";

// props.books is passed down from App; if none are supplied fall back to
// the sample data imported from data.js
function BookDetails(props) {
  const bookList = props.books || books;

  // List rendering with map(), each list item needs a unique "key" prop
  const bookdet = (
    <ul>
      {bookList.map((book) => (
        <div key={book.id}>
          <h3>{book.bname}</h3>
          <h4>{book.price}</h4>
        </div>
      ))}
    </ul>
  );

  return (
    <div className="st2">
      <h1>Book Details</h1>
      {/* Conditional rendering using the && operator:
          only render the list when there is at least one book */}
      {bookList.length > 0 && bookdet}
      {bookList.length === 0 && <p>No books available.</p>}
    </div>
  );
}

export default BookDetails;

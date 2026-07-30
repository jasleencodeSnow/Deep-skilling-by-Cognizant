# 13 — List & Conditional Rendering (`bloggerapp`)

## What this demonstrates
Three different ways of doing conditional rendering, one per component, plus
list rendering with `map()` and the `key` prop:

| Component | Conditional-rendering style |
|---|---|
| `BookDetails.js` | `&&` operator (`bookList.length > 0 && bookdet`) |
| `BlogDetails.js` | Ternary operator (`condition ? <A/> : <B/>`) |
| `CourseDetails.js` | `if / else` building an element variable (`coursedet`) |

Each component also demonstrates **list rendering**: `array.map(item => ...)`
with a unique `key` (`book.id`, `blog.id`, `course.id`) on the outermost
element of each iteration, exactly as shown in the lab hint.

## How to run
```bash
npx create-react-app bloggerapp
# replace the generated src/ files with the ones in this folder
cd bloggerapp
npm start
```

## Key solution points
- `data.js` holds the sample `books`, `blogs`, and `courses` arrays (matching
  the hint's `books` array of `{id, bname, price}`).
- `App.js` lays the three components out side by side to match the expected
  output screenshot (Course Details | Book Details | Blog Details).

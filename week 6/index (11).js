# 17 — Fetching Data from a REST API (`fetchuserapp`)

## What this demonstrates
Calling an external REST API (`https://api.randomuser.me/`) from inside
the `componentDidMount` lifecycle method using `async/await`:

```js
async componentDidMount() {
  const url = "https://api.randomuser.me/";
  const response = await fetch(url);
  const data = await response.json();
  this.setState({ person: data.results[0], loading: false });
  console.log(data.results[0]);
}
```

`componentDidMount` runs once, right after the component is first rendered
to the DOM — the right place to kick off a network request in a class
component.

## How to run
```bash
npx create-react-app fetchuserapp
# replace the generated src/ files with the ones in this folder
cd fetchuserapp
npm start
```

## Key solution points
- `state.loading` starts `true` so the UI shows "Loading..." until the
  fetch resolves, avoiding a crash from trying to read `person.name`
  before data has arrived.
- Once loaded, the user's title/first/last name is rendered in an `<h1>`
  and their photo (`person.picture.large`) in an `<img>`, matching the
  "Mr Donato Nunes" example in the expected output.

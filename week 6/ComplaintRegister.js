# 19 — Jest Mocking (`gitclientapp`)

## What this demonstrates
Testing a module **in isolation** by mocking its dependency (`axios`) with Jest.

### `GitClient.js`
```js
import axios from "axios";
class GitClient {
  static getRepositories(userName) {
    const url = `https://api.github.com/users/${userName}/repos`;
    return axios.get(url);
  }
}
export default GitClient;
```

### `App.js`
Uses `useEffect` + `useState` to call `GitClient.getRepositories('techiesyed')`
on mount and renders each repository name in a `<p>`.

### `GitClient.test.js`
- `jest.mock("axios")` auto-mocks the entire axios module so no real HTTP
  request is made.
- `axios.get.mockResolvedValue(dummyRepos)` makes the mocked `get` resolve
  with dummy data instead of hitting `api.github.com`.
- The suite is named **"Git Client Tests"** (`describe`), containing the
  test **"should return repository names for techiesyed"** which invokes
  `GitClient.getRepositories()` and asserts it returns the mocked data.

## How to run
```bash
npx create-react-app gitclientapp
# replace the generated src/ files and package.json with the ones in this folder
cd gitclientapp
npm install
npm start   # run the app
npm test    # run the mocked unit test
```

## Key solution points
- Mocking `axios` means the test is fast, deterministic, and works offline
  — it verifies GitClient's *logic* (building the URL, returning the
  response) without depending on the real GitHub API being reachable.

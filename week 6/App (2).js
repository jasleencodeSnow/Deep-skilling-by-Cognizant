# 14 — Context API (`employeeapp`)

## What this demonstrates
Replacing prop-drilling with the Context API:
1. `ThemeContext.js` — `React.createContext("light")`, default value `"light"`.
2. `App.js` — wraps its JSX in `<ThemeContext.Provider value={this.state.theme}>`
   so every descendant can read the current theme without it being passed
   down as a prop through `EmployeesList`.
3. `EmployeesList.js` — passes only `employee` data to each `EmployeeCard`;
   it does **not** forward `theme` as a prop anymore.
4. `EmployeeCard.js` — reads the theme directly with
   `useContext(ThemeContext)` and uses it to set its own className.

## How to run
```bash
npx create-react-app employeeapp
# replace the generated src/ files with the ones in this folder
cd employeeapp
npm install
npm start
```

## Key solution points
- Clicking **Toggle Theme** flips `state.theme` between `"light"` and
  `"dark"` in `App`; because `EmployeeCard` subscribes to `ThemeContext`
  directly, every card re-themes instantly with no manual prop passing
  through the intermediate `EmployeesList` component — this is the core
  benefit of Context over prop drilling.

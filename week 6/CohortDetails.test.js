# 16 — Form Validation (`mailregisterapp`)

## What this demonstrates
Field-level validation on a registration form (`Register.js`) using a
`switch` statement inside `handleChange`, matching the lab's hints exactly:

```js
switch (name) {
  case 'fullName':
    errors.fullName =
      value.length < 5 ? 'Full Name must be 5 characters long!' : '';
    break;
  ...
}
```

- **Full Name** — must be at least 5 characters long.
- **Email** — must match a basic `text@text.text` pattern.
- **Password** — must be at least 8 characters long.

`validateForm(errors)` returns `true` only when none of the `errors` fields
contain a message. `handleSubmit` then either alerts `"Valid Form"`, or
alerts each individual error message, exactly as shown in the hint:

```js
if (validateForm(this.state.errors)) {
  alert('Valid Form')
} else {
  if (this.state.errors.fullName !== '') { alert(this.state.errors.fullName) }
  if (this.state.errors.email !== '')    { alert(this.state.errors.email) }
  if (this.state.errors.password !== '') { alert(this.state.errors.password) }
}
```

## How to run
```bash
npx create-react-app mailregisterapp
# replace the generated src/ files with the ones in this folder
cd mailregisterapp
npm start
```

## Key solution points
- Validation runs on every keystroke (`onChange`) as well as on submit, so
  the errors displayed under the form update live, and the submit-time
  alerts always reflect the latest values.

# ReactJS Hands-On Lab — Full Solutions (Labs 1–10)

> Cognizant DN5 Java Placement Program — ReactJS Module
> This file contains complete, working solutions for all 10 ReactJS Hands-On Labs.
> Each section is self-contained: create the app, add the files exactly as shown, and run `npm start`.

---

## Table of Contents

1. [Lab 1 – First React App (myfirstreact)](#lab-1--first-react-app-myfirstreact)
2. [Lab 2 – Class Components (StudentApp)](#lab-2--class-components-studentapp)
3. [Lab 3 – Function Components + Styling (scorecalculatorapp)](#lab-3--function-components--styling-scorecalculatorapp)
4. [Lab 4 – Component Lifecycle (blogapp)](#lab-4--component-lifecycle-blogapp)
5. [Lab 5 – CSS Modules (CohortDetails)](#lab-5--css-modules-cohortdetails)
6. [Lab 6 – React Router (TrainersApp)](#lab-6--react-router-trainersapp)
7. [Lab 7 – Props (shoppingapp)](#lab-7--props-shoppingapp)
8. [Lab 8 – React State (counterapp)](#lab-8--react-state-counterapp)
9. [Lab 9 – ES6 Features (cricketapp)](#lab-9--es6-features-cricketapp)
10. [Lab 10 – JSX (officespacerentalapp)](#lab-10--jsx-officespacerentalapp)

---

## Lab 1 – First React App (myfirstreact)

**Goal:** Create a React app named `myfirstreact` that prints *"welcome to the first session of React"* as a heading.

### Setup

```bash
npx create-react-app myfirstreact
cd myfirstreact
```

### `src/App.js`

```jsx
import React from 'react';

function App() {
  return (
    <div className="App">
      <h1>welcome to the first session of React</h1>
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

Open `http://localhost:3000` — the page displays the heading.

---

## Lab 2 – Class Components (StudentApp)

**Goal:** Create `StudentApp` with three class components — `Home`, `About`, `Contact` — each showing a welcome message, and render all three from `App.js`.

### Setup

```bash
npx create-react-app StudentApp
cd StudentApp
mkdir src/Components
```

### `src/Components/Home.js`

```jsx
import React, { Component } from 'react';

class Home extends Component {
  render() {
    return (
      <div>
        <h2>Welcome to the Home page of Student Management Portal</h2>
      </div>
    );
  }
}

export default Home;
```

### `src/Components/About.js`

```jsx
import React, { Component } from 'react';

class About extends Component {
  render() {
    return (
      <div>
        <h2>Welcome to the About page of the Student Management Portal</h2>
      </div>
    );
  }
}

export default About;
```

### `src/Components/Contact.js`

```jsx
import React, { Component } from 'react';

class Contact extends Component {
  render() {
    return (
      <div>
        <h2>Welcome to the Contact page of the Student Management Portal</h2>
      </div>
    );
  }
}

export default Contact;
```

### `src/App.js`

```jsx
import React from 'react';
import Home from './Components/Home';
import About from './Components/About';
import Contact from './Components/Contact';

function App() {
  return (
    <div className="App">
      <Home />
      <About />
      <Contact />
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

---

## Lab 3 – Function Components + Styling (scorecalculatorapp)

**Goal:** Create `scorecalculatorapp` with a function component `CalculateScore` that accepts Name, School, Total, and goal (number of subjects), calculates and displays the average score, and is styled via an external stylesheet.

### Setup

```bash
npx create-react-app scorecalculatorapp
cd scorecalculatorapp
mkdir src/Components src/Stylesheets
```

### `src/Stylesheets/mystyle.css`

```css
.score-card {
  width: 320px;
  margin: 30px auto;
  padding: 20px;
  border: 1px solid #444;
  border-radius: 10px;
  background-color: #f4f8ff;
  font-family: Arial, sans-serif;
  text-align: center;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, 0.15);
}

.score-card h2 {
  color: #2b4c7e;
}

.score-card p {
  font-size: 16px;
  margin: 6px 0;
}

.score-value {
  font-weight: bold;
  color: #1a7a3c;
  font-size: 20px;
}
```

### `src/Components/CalculateScore.js`

```jsx
import React from 'react';
import '../Stylesheets/mystyle.css';

function CalculateScore(props) {
  const { name, school, total, goal } = props;
  const average = (total / goal).toFixed(2);

  return (
    <div className="score-card">
      <h2>Student Score Card</h2>
      <p><strong>Name:</strong> {name}</p>
      <p><strong>School:</strong> {school}</p>
      <p><strong>Total Marks:</strong> {total}</p>
      <p><strong>Number of Subjects:</strong> {goal}</p>
      <p>
        <strong>Average Score:</strong>{' '}
        <span className="score-value">{average}</span>
      </p>
    </div>
  );
}

export default CalculateScore;
```

### `src/App.js`

```jsx
import React from 'react';
import CalculateScore from './Components/CalculateScore';

function App() {
  return (
    <div className="App">
      <CalculateScore
        name="Ravi Kumar"
        school="Cognizant Public School"
        total={430}
        goal={5}
      />
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

---

## Lab 4 – Component Lifecycle (blogapp)

**Goal:** Create `blogapp`, a class component `Posts` that fetches data from `https://jsonplaceholder.typicode.com/posts` inside `componentDidMount()`, stores it in state via a `loadPosts()` method, renders the titles/bodies, and defines `componentDidCatch()` to alert on rendering errors.

### Setup

```bash
npx create-react-app blogapp
cd blogapp
```

### `src/Post.js`

```jsx
import React, { Component } from 'react';

class Post extends Component {
  render() {
    const { title, body } = this.props;
    return (
      <div className="post">
        <h3>{title}</h3>
        <p>{body}</p>
        <hr />
      </div>
    );
  }
}

export default Post;
```

### `src/Posts.js`

```jsx
import React, { Component } from 'react';
import Post from './Post';

class Posts extends Component {
  constructor(props) {
    super(props);
    this.state = {
      posts: [],
      hasError: false
    };
  }

  loadPosts = () => {
    fetch('https://jsonplaceholder.typicode.com/posts')
      .then((response) => response.json())
      .then((data) => {
        this.setState({ posts: data.slice(0, 10) });
      })
      .catch((error) => {
        console.error('Error fetching posts:', error);
      });
  };

  componentDidMount() {
    this.loadPosts();
  }

  componentDidCatch(error, info) {
    this.setState({ hasError: true });
    alert('Something went wrong while rendering the posts: ' + error.message);
  }

  render() {
    if (this.state.hasError) {
      return <h3>Something went wrong.</h3>;
    }
    return (
      <div className="posts">
        <h1>Blog Posts</h1>
        {this.state.posts.map((post) => (
          <Post key={post.id} title={post.title} body={post.body} />
        ))}
      </div>
    );
  }
}

export default Posts;
```

### `src/App.js`

```jsx
import React from 'react';
import Posts from './Posts';

function App() {
  return (
    <div className="App">
      <Posts />
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

---

## Lab 5 – CSS Modules (CohortDetails)

**Goal:** Style a `CohortDetails` component using a CSS Module: a `.box` class with the specified dimensions/padding/border, a tag selector for `<dt>`, and conditional `<h3>` color (green for "ongoing", blue otherwise).

### `src/CohortDetails.module.css`

```css
.box {
  width: 300px;
  display: inline-block;
  margin: 10px;
  padding: 10px 20px;
  border: 1px solid black;
  border-radius: 10px;
}

dt {
  font-weight: 500;
}

.ongoing {
  color: green;
}

.completed {
  color: blue;
}
```

### `src/CohortDetails.js`

```jsx
import React from 'react';
import styles from './CohortDetails.module.css';

function CohortDetails(props) {
  const { name, status, startDate, endDate, mentor } = props;
  const statusClass = status === 'ongoing' ? styles.ongoing : styles.completed;

  return (
    <div className={styles.box}>
      <h3 className={statusClass}>{name}</h3>
      <dl>
        <dt>Status</dt>
        <dd>{status}</dd>
        <dt>Start Date</dt>
        <dd>{startDate}</dd>
        <dt>End Date</dt>
        <dd>{endDate}</dd>
        <dt>Mentor</dt>
        <dd>{mentor}</dd>
      </dl>
    </div>
  );
}

export default CohortDetails;
```

### `src/App.js`

```jsx
import React from 'react';
import CohortDetails from './CohortDetails';

const cohorts = [
  { name: 'DN5 Java', status: 'ongoing', startDate: '01-Jun-2026', endDate: '30-Sep-2026', mentor: 'A. Sharma' },
  { name: 'DN4 .NET', status: 'completed', startDate: '01-Jan-2026', endDate: '31-Mar-2026', mentor: 'R. Iyer' },
  { name: 'DN5 Full Stack', status: 'ongoing', startDate: '15-May-2026', endDate: '15-Sep-2026', mentor: 'K. Nair' }
];

function App() {
  return (
    <div className="App">
      <h2>Academy Cohorts Dashboard</h2>
      {cohorts.map((c, i) => (
        <CohortDetails key={i} {...c} />
      ))}
    </div>
  );
}

export default App;
```

### Run

```bash
npm install
npm start
```

---

## Lab 6 – React Router (TrainersApp)

**Goal:** Build `TrainersApp` — a trainer directory with routing: `/` shows Home, `/trainers` shows a clickable list of trainers, and `/trainers/:id` shows trainer details via `useParams`.

### Setup

```bash
npx create-react-app TrainersApp
cd TrainersApp
npm install react-router-dom
```

### `src/trainer.js`

```jsx
class Trainer {
  constructor(trainerId, name, email, phone, technology, skills) {
    this.trainerId = trainerId;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.technology = technology;
    this.skills = skills;
  }
}

export default Trainer;
```

### `src/TrainersMock.js`

```jsx
import Trainer from './trainer';

const TrainersMock = [
  new Trainer(1, 'Anita Sharma', 'anita.sharma@cognizant.com', '9876543210', 'Java', ['Core Java', 'Spring Boot', 'Hibernate']),
  new Trainer(2, 'Rahul Iyer', 'rahul.iyer@cognizant.com', '9876543211', '.NET', ['C#', 'ASP.NET Core', 'Azure']),
  new Trainer(3, 'Kavya Nair', 'kavya.nair@cognizant.com', '9876543212', 'Full Stack', ['React', 'Node.js', 'MongoDB']),
  new Trainer(4, 'Suresh Menon', 'suresh.menon@cognizant.com', '9876543213', 'Python', ['Django', 'Flask', 'Pandas']),
  new Trainer(5, 'Divya Rao', 'divya.rao@cognizant.com', '9876543214', 'DevOps', ['Docker', 'Kubernetes', 'Jenkins'])
];

export default TrainersMock;
```

### `src/Trainerlist.js`

```jsx
import React from 'react';
import { Link } from 'react-router-dom';

function TrainersList({ trainers }) {
  return (
    <div>
      <h2>Trainers List</h2>
      <ul>
        {trainers.map((trainer) => (
          <li key={trainer.trainerId}>
            <Link to={`/trainers/${trainer.trainerId}`}>{trainer.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TrainersList;
```

### `src/Home.js`

```jsx
import React from 'react';

function Home() {
  return (
    <div>
      <h2>Welcome to Cognizant Academy Trainers Directory</h2>
    </div>
  );
}

export default Home;
```

### `src/TrainerDetails.js`

```jsx
import React from 'react';
import { useParams } from 'react-router-dom';
import TrainersMock from './TrainersMock';

function TrainerDetail() {
  const { id } = useParams();
  const trainer = TrainersMock.find((t) => t.trainerId === parseInt(id, 10));

  if (!trainer) {
    return <h3>Trainer not found</h3>;
  }

  return (
    <div>
      <h2>Trainer Detail</h2>
      <p><strong>ID:</strong> {trainer.trainerId}</p>
      <p><strong>Name:</strong> {trainer.name}</p>
      <p><strong>Email:</strong> {trainer.email}</p>
      <p><strong>Phone:</strong> {trainer.phone}</p>
      <p><strong>Technology:</strong> {trainer.technology}</p>
      <p><strong>Skills:</strong> {trainer.skills.join(', ')}</p>
    </div>
  );
}

export default TrainerDetail;
```

### `src/App.js`

```jsx
import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import Home from './Home';
import TrainersList from './Trainerlist';
import TrainerDetail from './TrainerDetails';
import TrainersMock from './TrainersMock';

function App() {
  return (
    <BrowserRouter>
      <nav>
        <Link to="/">Home</Link> | <Link to="/trainers">Trainers</Link>
      </nav>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/trainers" element={<TrainersList trainers={TrainersMock} />} />
        <Route path="/trainers/:id" element={<TrainerDetail />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
```

### Run

```bash
npm start
```

---

## Lab 7 – Props (shoppingapp)

**Goal:** Create `shoppingapp` with class components `Cart` (Itemname, Price) and `OnlineShopping`, which holds an array of 5 `Cart` items and loops through them using props.

### Setup

```bash
npx create-react-app shoppingapp
cd shoppingapp
```

### `src/Cart.js`

```jsx
import React, { Component } from 'react';

class Cart extends Component {
  render() {
    const { itemname, price } = this.props;
    return (
      <tr>
        <td>{itemname}</td>
        <td>{price}</td>
      </tr>
    );
  }
}

export default Cart;
```

### `src/OnlineShopping.js`

```jsx
import React, { Component } from 'react';
import Cart from './Cart';

class OnlineShopping extends Component {
  constructor(props) {
    super(props);
    this.state = {
      cartItems: [
        { itemname: 'Laptop', price: 55000 },
        { itemname: 'Headphones', price: 2000 },
        { itemname: 'Keyboard', price: 1200 },
        { itemname: 'Mouse', price: 600 },
        { itemname: 'Monitor', price: 9000 }
      ]
    };
  }

  render() {
    return (
      <div>
        <h2>Online Shopping Cart</h2>
        <table border="1" cellPadding="8">
          <thead>
            <tr>
              <th>Item Name</th>
              <th>Price</th>
            </tr>
          </thead>
          <tbody>
            {this.state.cartItems.map((item, index) => (
              <Cart key={index} itemname={item.itemname} price={item.price} />
            ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default OnlineShopping;
```

### `src/App.js`

```jsx
import React from 'react';
import OnlineShopping from './OnlineShopping';

function App() {
  return (
    <div className="App">
      <OnlineShopping />
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

---

## Lab 8 – React State (counterapp)

**Goal:** Create `counterapp` with a `CountPeople` component tracking `entrycount` and `exitcount` in state, with `Login` and `Exit` buttons calling `UpdateEntry()` and `UpdateExit()`.

### Setup

```bash
npx create-react-app counterapp
cd counterapp
```

### `src/CountPeople.js`

```jsx
import React, { Component } from 'react';

class CountPeople extends Component {
  constructor(props) {
    super(props);
    this.state = {
      entrycount: 0,
      exitcount: 0
    };
  }

  UpdateEntry = () => {
    this.setState((prevState) => ({ entrycount: prevState.entrycount + 1 }));
  };

  UpdateExit = () => {
    this.setState((prevState) => ({ exitcount: prevState.exitcount + 1 }));
  };

  render() {
    return (
      <div>
        <h2>Mall People Counter</h2>
        <p>Number of people entered: {this.state.entrycount}</p>
        <p>Number of people exited: {this.state.exitcount}</p>
        <button onClick={this.UpdateEntry}>Login</button>
        <button onClick={this.UpdateExit}>Exit</button>
      </div>
    );
  }
}

export default CountPeople;
```

### `src/App.js`

```jsx
import React from 'react';
import CountPeople from './CountPeople';

function App() {
  return (
    <div className="App">
      <CountPeople />
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

---

## Lab 9 – ES6 Features (cricketapp)

**Goal:** Create `cricketapp` with two components — `ListofPlayers` (uses `map()` to list 11 players and an arrow function to filter players scoring below 70) and `IndianPlayers` (uses destructuring to show Odd/Even team players and the spread operator to merge `T20players` and `RanjiTrophyPlayers`). Toggle between the two using a `flag` variable.

### Setup

```bash
npx create-react-app cricketapp
cd cricketapp
mkdir src/Components
```

### `src/Components/ListofPlayers.js`

```jsx
import React from 'react';

const players = [
  { name: 'Rohit Sharma', score: 85 },
  { name: 'Virat Kohli', score: 92 },
  { name: 'Shubman Gill', score: 65 },
  { name: 'KL Rahul', score: 55 },
  { name: 'Suryakumar Yadav', score: 78 },
  { name: 'Hardik Pandya', score: 45 },
  { name: 'Ravindra Jadeja', score: 60 },
  { name: 'Axar Patel', score: 30 },
  { name: 'Jasprit Bumrah', score: 15 },
  { name: 'Mohammed Shami', score: 10 },
  { name: 'Kuldeep Yadav', score: 20 }
];

function ListofPlayers() {
  // map() -> render each player
  const allPlayers = players.map((player, index) => (
    <li key={index}>{player.name} - {player.score}</li>
  ));

  // arrow function + filter() -> players scoring below 70
  const lowScorers = players.filter((player) => player.score < 70);

  return (
    <div>
      <h2>List of Players</h2>
      <ul>{allPlayers}</ul>

      <h3>Players with score below 70</h3>
      <ul>
        {lowScorers.map((player, index) => (
          <li key={index}>{player.name} - {player.score}</li>
        ))}
      </ul>
    </div>
  );
}

export default ListofPlayers;
```

### `src/Components/IndianPlayers.js`

```jsx
import React from 'react';

function IndianPlayers() {
  // Destructuring: Odd and Even team players
  const oddTeam = ['Rohit Sharma', 'Shubman Gill', 'Suryakumar Yadav', 'Ravindra Jadeja', 'Jasprit Bumrah', 'Kuldeep Yadav'];
  const evenTeam = ['Virat Kohli', 'KL Rahul', 'Hardik Pandya', 'Axar Patel', 'Mohammed Shami'];

  const [player1, player2, player3, ...restOdd] = oddTeam;
  const [player4, player5, ...restEven] = evenTeam;

  // Merge feature of ES6 (spread operator)
  const T20players = ['Rohit Sharma', 'Virat Kohli', 'Hardik Pandya'];
  const RanjiTrophyPlayers = ['Shubman Gill', 'Axar Patel', 'Kuldeep Yadav'];
  const allFormatPlayers = [...T20players, ...RanjiTrophyPlayers];

  return (
    <div>
      <h2>Indian Players</h2>

      <h3>Odd Team Players (Destructured)</h3>
      <p>{player1}, {player2}, {player3}, and {restOdd.length} more: {restOdd.join(', ')}</p>

      <h3>Even Team Players (Destructured)</h3>
      <p>{player4}, {player5}, and {restEven.length} more: {restEven.join(', ')}</p>

      <h3>T20 + Ranji Trophy Players (Merged using Spread)</h3>
      <ul>
        {allFormatPlayers.map((p, i) => (
          <li key={i}>{p}</li>
        ))}
      </ul>
    </div>
  );
}

export default IndianPlayers;
```

### `src/App.js`

```jsx
import React from 'react';
import ListofPlayers from './Components/ListofPlayers';
import IndianPlayers from './Components/IndianPlayers';

function App() {
  const flag = true; // toggle to false to view IndianPlayers component

  return (
    <div className="App">
      <h1>Cricket App</h1>
      {flag ? <ListofPlayers /> : <IndianPlayers />}
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

Set `flag = true` to view `ListofPlayers`, or `flag = false` to view `IndianPlayers`.

---

## Lab 10 – JSX (officespacerentalapp)

**Goal:** Create `officespacerentalapp` using JSX to render a heading, an image, an office details object, a list of office objects, and inline CSS that colors the rent red if below 60000 and green if 60000 or above.

### Setup

```bash
npx create-react-app officespacerentalapp
cd officespacerentalapp
```

### `src/App.js`

```jsx
import React from 'react';

const office = {
  name: 'Cognizant Business Hub',
  rent: 55000,
  address: 'MIDC, Pune, Maharashtra'
};

const officeSpaces = [
  { id: 1, name: 'Skyline Towers', rent: 45000, address: 'Whitefield, Bangalore' },
  { id: 2, name: 'Tech Park One', rent: 72000, address: 'Hinjewadi, Pune' },
  { id: 3, name: 'Business Bay', rent: 58000, address: 'Andheri East, Mumbai' },
  { id: 4, name: 'Cyber Towers', rent: 81000, address: 'Gachibowli, Hyderabad' }
];

function App() {
  return (
    <div className="App">
      {/* Element to display the heading of the page */}
      <h1>Office Space Rental App</h1>

      {/* Attribute to display the image of the office space */}
      <img
        src="https://via.placeholder.com/400x200.png?text=Office+Space"
        alt="Office Space"
        width="400"
        height="200"
      />

      {/* Object to display office details */}
      <h2>Featured Office</h2>
      <p>Name: {office.name}</p>
      <p>Address: {office.address}</p>
      <p style={{ color: office.rent < 60000 ? 'red' : 'green' }}>
        Rent: {office.rent}
      </p>

      {/* List of objects looped to display more data */}
      <h2>All Available Office Spaces</h2>
      <ul>
        {officeSpaces.map((space) => (
          <li key={space.id}>
            <strong>{space.name}</strong> — {space.address} —{' '}
            <span style={{ color: space.rent < 60000 ? 'red' : 'green' }}>
              Rent: {space.rent}
            </span>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
```

### Run

```bash
npm start
```

Rent values below 60000 render in **red**; values 60000 and above render in **green**.

---

## Repository Structure Suggestion

If you'd like to push all 10 labs into a single GitHub repository, a clean layout is:

```
reactjs-hands-on-labs/
├── README.md                  <- this file
├── lab1-myfirstreact/
├── lab2-studentapp/
├── lab3-scorecalculatorapp/
├── lab4-blogapp/
├── lab5-cohortdetails/
├── lab6-trainersapp/
├── lab7-shoppingapp/
├── lab8-counterapp/
├── lab9-cricketapp/
└── lab10-officespacerentalapp/
```

Create each app in its own subfolder with `create-react-app`, drop in the files shown above, then:

```bash
git init
git add .
git commit -m "Add ReactJS Hands-On Lab solutions 1-10"
git branch -M main
git remote add origin https://github.com/jasleencodeSnow/reactjs-hands-on-labs.git
git push -u origin main
```

*(Remember to add a `.gitignore` with `node_modules/` and `build/` in each app folder before committing.)*

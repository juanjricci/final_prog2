//import logo from './logo.svg';
import 'bootstrap/dist/css/bootstrap.min.css'
import './App.css';
import { BrowserRouter as Router, Route} from 'react-router-dom'

import Navigation from './components/Navigation';
import ProductsList from './components/ProductsList';
import AddProduct from './components/AddProduct';
import CreateUser from './components/CreateUser';


function App() {
  return (
    <Router>
      <Navigation/>
      <Route exact path="/" component={ProductsList}/>
      <Route path="/edit/:id" component={AddProduct}/>
      <Route path="/create" component={AddProduct}/>
      <Route path="/user" component={CreateUser}/>
    </Router>
  );
}

export default App;

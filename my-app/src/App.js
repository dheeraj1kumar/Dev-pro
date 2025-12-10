import { BrowserRouter, Switch, Route } from 'react-router-dom';
import EmployeeList from './components/EmployeesList';
import NotFound from './components/NotFound';
import 'bootstrap/dist/css/bootstrap.min.css';
import AddEmployee from './components/AddEmployee';

function App() {
  return (
    <BrowserRouter>
  <Switch>
    <Route exact path="/" component={EmployeeList} />
    <Route path="/add" component={AddEmployee} />
    <Route path="/employees/edit/:id" component={AddEmployee} />
    <Route path="*" component={NotFound} />
  </Switch>
</BrowserRouter>

  );
}


export default App;

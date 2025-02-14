// Copyright (c) 2021 Microsoft
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import * as React from "react";
import { initializeIcons, loadTheme } from "@fluentui/react";
import { createBrowserHistory } from "history";
import { observer } from "mobx-react";
import { Redirect, Route, Router, Switch } from "react-router-dom";
import "./styles/newApp.css";
import Home from "./components/home";
import Registrationoneid from "./components/registrationoneid";
import Registration from "./components/registration";
import Authinfo from "./components/authinfo";
import { AppState } from "./stores/appstate";
import Login from "./components/login";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Landing from "./components/landing";
import RegistrationCax from "./components/cax-registration";
import Finish from "./components/finish"
const history = createBrowserHistory();

@observer
export default class App extends React.Component {
  private static first = true;
  constructor(props: any) {
    super(props);
    if (App.first) {
      initializeIcons();
      loadTheme({
        palette: { themePrimary: "#BAC938", themeDarkAlt: "#E6AA1E" },
      });
    }

    AppState.state = new AppState();

    App.first = false;
  }

  public render() {
    const ProtectedHome = Home;
    return (
      <Router history={history}>
        <Switch>
          <Redirect path="/" exact to="/landing" />
          <Route path="/landing" render={(props) => <Landing {...props} />} />
          <Route
            path="/registration"
            render={(props) => <RegistrationCax {...props} />}
          />
          <Route path="/home" render={(props) => <ProtectedHome />} />
          <Route
            path="/registrationoneid"
            component={(props) => <Registrationoneid {...props} />}
          />
          <Route
            path="/register"
            component={(props) => <Registration {...props} />}
          />
           <Route
            path="/finish"
            component={(props) => <Finish {...props} />}
          />
          <Route path="/login" component={(props) => <Login {...props} />} />
          <Route path="/authinfo" component={(props) => <Authinfo />} />
        </Switch>
      </Router>
    );
  }
}

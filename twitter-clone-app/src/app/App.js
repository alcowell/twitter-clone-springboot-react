/* eslint-disable react/prop-types */
/* eslint-disable react/jsx-props-no-spreading */
import React, { Component } from 'react';
import { Route, withRouter, Switch } from 'react-router-dom';
import './App.css';
import { Layout, notification } from 'antd';
// import { getCurrentUser } from '../util/APIUtils';
// import { ACCESS_TOKEN } from '../constants';

import TweetList from '../tweet/TweetList';
// import NewTweet from '../tweet/NewTweet';
// import Signup from '../user/signup/Signup';
// import Login from '../user/login/Login';
// import Profile from '../user/profile/Profile';
// import AppHeader from '../common/AppHeader';
// import NotFound from '../common/NotFound.js';
import LoadingIndicator from '../common/LoadingIndicator';
// import PrivateRoute from '../common/PrivateRoute';

const { Content } = Layout;

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthentication: false,
      isLoading: false,
    };
    // this.handleLogout = this.handleLogout.bind(this);
    // this.loadCurrentUser = this.loadCurrentUser.bind(this);
    // this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });
  }

  // componentDidMount() {
  //   this.loadCurrentUser();
  // }

  // loadCurrentUser() {
  //   this.setState({
  //     isLoading: true,
  //   });
  //   getCurrentUser()
  //     .then((response) => {
  //       this.setState({
  //         currentUser: response,
  //         isAuthentication: true,
  //         isLoading: false,
  //       });
  //     })
  //     // eslint-disable-next-line no-unused-vars
  //     .catch((error) => {
  //       this.setState({
  //         isLoading: false,
  //       });
  //     });
  // }

  handleLogout(
    redirectTo = '/',
    notificationType = 'success',
    description = "You're successfully logged out."
  ) {
    this.setState({
      currentUser: null,
      isAuthentication: false,
    });
    const { history } = this.props;
    history.push(redirectTo);

    notification[notificationType]({
      message: 'Tweet App',
      description,
    });
  }

  /*
  This method is called by the Login component after successful
  login so that we can load logged-in user details and set the currentUser
  & isAuthenticated state, which other components will use to render their JSX
  */
  handleLogin() {
    notification.success({
      message: 'Tweet App',
      description: "You're successfully logged in.",
    });
    this.loadCurrentUser();
    const { history } = this.props;
    history.push('/');
  }

  render() {
    const { isLoading, isAuthentication, currentUser } = this.state;

    if (isLoading) {
      return <LoadingIndicator />;
    }
    return (
      <Layout className="app-container">
        {/* <AppHeader
          isAuthentication={isAuthentication}
          currentUser={currentUser}
          onLogout={this.handleLogout}
        /> */}
        <Content className="app-content">
          <div className="container">
            <Switch>
              <Route
                exact
                path="/"
                render={(props) => (
                  <TweetList
                    isAuthentication={isAuthentication}
                    currentUser={currentUser}
                    onLogout={this.handleLogout}
                    {...props}
                  />
                )}
              />
              {/* <Route
                path="/login"
                render={(props) => (
                  <Login onLogin={this.handleLogin} {...props} />
                )}
              />
              <Route path="/signup" component={Signup} /> */}
              {/* <Route
                path="/users/:username"
                render={(props) => (
                  <Profile
                    isAuthentication={isAuthentication}
                    currentUser={currentUser}
                    {...props}
                  />
                )}
              /> */}
              {/* <PrivateRoute
                authentication={isAuthentication}
                path="/tweet/new"
                component={NewTweet}
                handleLogout={this.handleLogout}
              /> */}
              {/* <Route component={NotFound} /> */}
            </Switch>
          </div>
        </Content>
      </Layout>
    );
  }
}

export default withRouter(App);

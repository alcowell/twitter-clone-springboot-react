import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import './Appheader.css';
import { Layout, Menu, Dropdown, Icon } from 'antd';
import tweetIcon from '../tweet.svg';

const { Header } = Layout;

class AppHeader extends Component {
  constructor(props) {
    super(props);
    this.handleMenuClick = this.handleMenuClick.bind(this);
  }

  handleMenuClick({ key }) {
    if (key === 'logout') {
      const { onLogout } = this.props;
      onLogout();
    }
  }

  render() {
    let menuItems;
    const { currentUser } = this.props;
    if (currentUser) {
      menuItems = [
        <Menu.Item key="/">
          <Link to="/">
            <Icon type="home" className="nav-icon" />
          </Link>
        </Menu.Item>,
      ];
    }
  }
}
export default withRouter(AppHeader);

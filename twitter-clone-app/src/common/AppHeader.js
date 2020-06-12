/* eslint-disable jsx-a11y/anchor-is-valid */
import React, { Component } from 'react';
import Proptypes from 'prop-types';
import { Link, withRouter } from 'react-router-dom';
import './AppHeader.css';
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
        <Menu.Item key="/tweet/new">
          <Link to="/tweet/new">
            <img src={tweetIcon} alt="tweet" className="tweet-icon" />
          </Link>
        </Menu.Item>,
        <Menu.Item key="/profile" className="profile-icon">
          <ProfileDropdownMenu
            currentUser={currentUser}
            handleMenuClick={this.handleMenuClick}
          />
        </Menu.Item>,
      ];
    } else {
      menuItems = [
        <Menu.Item key="/login">
          <Link to="/login">Login</Link>
        </Menu.Item>,
        <Menu.Item key="/signup">
          <Link to="/signup">Signup</Link>
        </Menu.Item>,
      ];
    }
    const { location } = this.props;
    return (
      <Header className="app-header">
        <div className="container">
          <div className="app-title">
            <Link to="/">Tweet App</Link>
          </div>
          <Menu
            className="app-menu"
            mode="horizontal"
            selectedKeys={[location.pathname]}
            style={{ lineHeight: '64px' }}
          >
            {menuItems}
          </Menu>
        </div>
      </Header>
    );
  }
}

// AppHeader.propTypes = {
//   currentUser: Proptypes.object.isRequired,
//   location: Proptypes.object.isRequired,
// };

function ProfileDropdownMenu(props) {
  const { handleMenuClick, currentUser } = props;
  const dropdownMenu = (
    <Menu onClick={handleMenuClick} className="profile-dropdown-menu">
      <Menu.Item key="user-info" className="dropdown-item" disabled>
        <div className="userid-info">{currentUser.userid}</div>
        <div className="username-info">{currentUser.name}</div>
      </Menu.Item>
      <Menu.Divider />
      <Menu.Item key="profile" className="dropdown-item">
        <Link to={`/user/${currentUser.userid}`}>Profile</Link>
      </Menu.Item>
      <Menu.Item key="logout" className="dropdown-item">
        Logout
      </Menu.Item>
    </Menu>
  );

  return (
    <Dropdown
      overlay={dropdownMenu}
      trigger={[`click`]}
      getPopupContainer={() => document.getElementById(`profile-menu`)[0]}
    >
      <a className="ant-dropdown-link">
        <Icon type="user" className="nav-icon" style={{ marginRight: 0 }} />
        <Icon type="down" />
      </a>
    </Dropdown>
  );
}

export default withRouter(AppHeader);

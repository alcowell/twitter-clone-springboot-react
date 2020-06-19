import React, { Component } from 'react';
import { Tabs } from 'antd';
import TweetList from '../../tweet/TweetList';

const { TabPane } = Tabs;

class Profile extends Component {
  render() {
    const { match, isAuthenticated, currentUser } = this.props;
    return (
      <Tabs defaultActiveKey="1">
        <TabPane tab="Tweet" key="1">
          <TweetList
            isAuthenticated={isAuthenticated}
            currentUser={currentUser}
            type="SPECIFIC"
            id={match.params.id}
            onLogout={this.handleLogout}
          />
        </TabPane>
        <TabPane tab="Media" key="2">
          Contents of Media
        </TabPane>
      </Tabs>
    );
  }
}

export default Profile;

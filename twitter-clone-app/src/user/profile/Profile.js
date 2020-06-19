import React, { Component } from 'react';
import { Tabs } from 'antd';

const { TabPane } = Tabs;

class Profile extends Component {
  render() {
    return (
      <Tabs defaultActiveKey="1">
        <TabPane tab="Tweet" key="1">
          Content of Tweets
        </TabPane>
        <TabPane tab="Media" key="2">
          Contents of Media
        </TabPane>
      </Tabs>
    );
  }
}

export default Profile;

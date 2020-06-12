/* eslint-disable react/prop-types */
/* eslint-disable react/prefer-stateless-function */
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './tweet.css';
import { Link } from 'react-router-dom';
import { Avatar } from 'antd';

class Tweet extends Component {
  render() {
    const { tweet } = this.props;
    const { createdBy, text, createdAt } = tweet;
    return (
      <div className="tweet-content">
        <div className="tweet-header">
          <Link className="creator-link" to={`/user/${createdBy.userid}`}>
            <Avatar
              className="tweet-creator-avatar"
              style={{ backgroundColor: '#F44336' }}
            >
              {createdBy.username[0]}
            </Avatar>
            <span className="tweet-creator-id">@{createdBy.userid}</span>
            <span className="tweet-creator-id">{createdBy.username}</span>
            <span className="tweet-creator-date">{createdAt}</span>
          </Link>
        </div>
        <div className="tweet-text">{text}</div>
      </div>
    );
  }
}

Tweet.propTyps = {
  tweet: PropTypes.shape({
    createdBy: PropTypes.shape({
      id: PropTypes.number,
      username: PropTypes.string,
      userid: PropTypes.string,
    }),
    text: PropTypes.string,
    createdAt: PropTypes.string,
  }),
};
export default Tweet;

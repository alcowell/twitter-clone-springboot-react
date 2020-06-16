/* eslint-disable react/prop-types */
/* eslint-disable react/prefer-stateless-function */
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './Tweet.css';
import { Link } from 'react-router-dom';
import { Avatar } from 'antd';

class Tweet extends Component {
  constructor(props) {
    super(props);
    this.Like = this.Like.bind(this);
  }

  Like() {}

  render() {
    const { tweet } = this.props;
    const { createdBy, text, creationDateTime, isLiked } = tweet;
    return (
      <div className="tweet-content">
        <div className="creator-info">
          <Link className="creator-link" to={`/user/${createdBy.userid}`}>
            <Avatar
              className="tweet-creator-avatar"
              style={{ backgroundColor: '#F44336' }}
            >
              {createdBy.username[0]}
            </Avatar>
            <span className="tweet-creator-id">@{createdBy.userId}</span>
            <span className="tweet-creator-name">{createdBy.username}</span>
            <span className="tweet-creator-date">{creationDateTime}</span>
          </Link>
        </div>
        <div className="tweet-text">{text}</div>
        <hr width="80%" />
        <div className="tweet-footer" />
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

/* eslint-disable react/prop-types */
/* eslint-disable react/prefer-stateless-function */
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import './Tweet.css';
import { Link } from 'react-router-dom';
import { Avatar } from 'antd';
import { HeartFilled } from '@ant-design/icons';
import getAvatarColor from '../util/Colors';

class Tweet extends Component {
  render() {
    const { tweet, handleLikeSubmit, handleLikeDetach } = this.props;
    const {
      createdBy,
      text,
      creationDateTime,
      likedUsers,
      isLikedByCurrentUser,
    } = tweet;
    return (
      <div className="tweet-content">
        <div className="creator-info">
          <Link className="creator-link" to={`/users/${createdBy.id}`}>
            <Avatar
              className="tweet-creator-avatar"
              style={{ backgroundColor: getAvatarColor(createdBy.username) }}
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
        <div className="tweet-footer">
          <span>
            <HeartFilled
              className={[
                'like',
                isLikedByCurrentUser ? 'after' : 'before',
              ].join('-')}
              onClick={
                isLikedByCurrentUser ? handleLikeDetach : handleLikeSubmit
              }
            />
          </span>
          <span>{likedUsers !== null ? likedUsers.length : null}</span>
        </div>
      </div>
    );
  }
}

Tweet.propTypes = {
  tweet: PropTypes.shape({
    createdBy: PropTypes.shape({
      id: PropTypes.number,
      username: PropTypes.string,
      userId: PropTypes.string,
    }),
    likedUsers: PropTypes.arrayOf(
      PropTypes.shape({
        id: PropTypes.number,
        username: PropTypes.string,
        userId: PropTypes.string,
      })
    ),
    text: PropTypes.string,
    createdAt: PropTypes.string,
    isLikedByCurrentUser: PropTypes.bool,
  }),
};
export default Tweet;

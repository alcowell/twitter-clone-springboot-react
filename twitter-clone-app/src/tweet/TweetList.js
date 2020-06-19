/* eslint-disable react/no-unused-state */
import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { notification } from 'antd';
import Tweet from './Tweet';
import { getAllTweet, castLike, uncastLike } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator';
import { TWEET_LIST_SIZE } from '../constants';

class TweetList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tweets: [],
      isLoading: false,
    };
    this.loadTweetList = this.loadTweetList.bind(this);
  }

  componentDidMount() {
    this.loadTweetList();
  }

  // componentDidUpdate(nextProps) {
  //   const { isAuthenticated } = this.props;
  //   if (isAuthenticated !== nextProps.isAuthenticated) {
  //     // Reset State
  //     this.setState({
  //       tweets: [],
  //       isLoading: false,
  //     });
  //     this.loadTweetList();
  //   }
  // }

  loadTweetList(type,) {
    switch(type){
      case 'ALL':
        const promise = getAllTweet();
        break
      
      case 'SELF':
        const promise = 
    }

    const promise = getAllTweet();
    const { tweets } = this.state;
    if (!promise) {
      return;
    }
    this.setState({
      isLoading: false,
    });

    promise
      .then((response) => {
        const stateTweets = tweets.slice();
        this.setState({
          tweets: stateTweets.concat(response.contents),
          isLoading: false,
        });
      })
      // eslint-disable-next-line no-unused-vars
      .catch((error) => {
        this.setState({
          isLoading: false,
        });
      });
  }

  handleLikeSubmit(event, tweetIndex) {
    event.preventDefault();
    const { isAuthenticated, history } = this.props;
    const { tweets } = this.state;
    const tweet = tweets[tweetIndex];

    if (!isAuthenticated) {
      history.push('/login');
      notification.info({
        message: 'Tweet App clone',
        description: 'Please login to like',
      });
      return;
    }
    castLike(tweet.id)
      .then((response) => {
        const tweetsSlice = tweets.slice();
        tweetsSlice[tweetIndex] = response;
        this.setState({
          tweets: tweetsSlice,
        });
      })
      .catch((error) => {
        notification.error({
          message: 'Tweet App clone',
          description: 'Sorry! Something went wrong. Please try again!',
        });
      });
  }

  handleLikeDetach(event, tweetIndex) {
    event.preventDefault();
    const { isAuthenticated, history } = this.props;
    const { tweets } = this.state;
    const tweet = tweets[tweetIndex];

    uncastLike(tweet.id)
      .then((response) => {
        const tweetsSlice = tweets.slice();
        tweetsSlice[tweetIndex] = response;
        this.setState({
          tweets: tweetsSlice,
        });
      })
      .catch((error) => {
        notification.error({
          message: 'Tweet App clone',
          description: 'Sorry! Something went wrong. Please try again!',
        });
      });
  }

  render() {
    const tweetView = [];
    const { isAuthenticated, history } = this.props;
    const { tweets, isLoading } = this.state;
    if (tweets.length !== 0) {
      tweets.forEach((tweet, tweetIndex) => {
        tweetView.push(
          <Tweet
            key={tweet.id}
            tweet={tweet}
            handleLikeSubmit={(event) =>
              this.handleLikeSubmit(event, tweetIndex)
            }
            handleLikeDetach={(event) =>
              this.handleLikeDetach(event, tweetIndex)
            }
          />
        );
      });
    }

    return (
      <div className="tweet-container">
        {tweetView}
        {!isLoading && tweets.length === 0 ? (
          <div className="no-tweet-found">
            <span>No tweets found.</span>
          </div>
        ) : null}
        {isLoading ? <LoadingIndicator /> : null}
      </div>
    );
  }
}

export default withRouter(TweetList);

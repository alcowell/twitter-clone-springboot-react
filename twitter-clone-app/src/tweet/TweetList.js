/* eslint-disable react/no-unused-state */
import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import Tweet from './Tweet';
import { getAllTweet } from '../util/APIUtils';
import LoadingIndicator from '../common/LoadingIndicator';
import { TWEET_LIST_SIZE } from '../constants';

class TweetList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tweets: [],
      paze: 0,
      size: 10,
      totalElements: 0,
      last: true,
      isLoading: false,
    };
  }

  loadTweetList(page = 0, size = TWEET_LIST_SIZE) {
    const promise = getAllTweet(page, size);
    const { stateTweets } = this.state;
    if (!promise) {
      return;
    }
    this.setState({
      isLoading: false,
    });

    promise
      .then((response) => {
        const tweets = stateTweets.slice();
        this.setState({
          tweets: tweets.concat(response.content),
          paze: response.page,
          size: response.size,
          totalElements: response.totalElements,
          last: response.last,
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

  render() {
    const tweetView = [];
    const { tweets, isLoading } = this.state;
    tweets.map((tweet) => {
      tweetView.push(<Tweet key={tweet.id} tweet={tweet} />);
      return null;
    });
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

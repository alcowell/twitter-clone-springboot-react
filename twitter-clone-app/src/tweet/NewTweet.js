/* eslint-disable react/prop-types */
import React, { Component } from 'react';
import { Form, Input, Button, notification } from 'antd';
import { TWEET_TEXT_MAX_LENGTH } from '../constants';
import { createTweet } from '../util/APIUtils';
import './NewTweet.css';

const FormItem = Form.Item;
const { TextArea } = Input;

class NewTweet extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tweet: {
        text: '',
      },
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.isFormInvalid = this.isFormInvalid.bind(this);
    this.handleTextChange = this.handleTextChange.bind(this);
  }

  validateText = (text) => {
    if (text.length === 0) {
      return {
        validateStatus: 'error',
        errorMsg: 'Please enter tweet text.',
      };
    }
    if (text.length > TWEET_TEXT_MAX_LENGTH) {
      return {
        validateStatus: 'error',
        errorMsg: `Tweet is too long (Maximum ${TWEET_TEXT_MAX_LENGTH} character allowed)`,
      };
    }
    return {
      validateStatus: 'success',
      errorMsg: null,
    };
  };

  handleTextChange(event) {
    const text = event.target.value;
    this.setState({
      tweet: {
        text,
        ...this.validateText(text),
      },
    });
  }

  isFormInvalid() {
    const { tweet } = this.state;
    if (tweet.validateStatus !== 'success') {
      return true;
    }
    return false;
  }

  handleSubmit(event) {
    event.preventDefault();
    const { tweet } = this.state;
    const { history, handleLogout } = this.props;
    const tweetData = {
      text: tweet.text,
    };

    createTweet(tweetData)
      // eslint-disable-next-line no-unused-vars
      .then((response) => {
        history.push('/');
      })
      .catch((error) => {
        if (error.status === 401) {
          handleLogout(
            '/login',
            'error',
            'You have been logged out. Please login to post tweet'
          );
        } else {
          notification.error({
            message: 'Tweet clone App',
            description: 'Sorry! Something went wrong.',
          });
        }
      });
  }

  render() {
    const { tweet } = this.state;
    return (
      <div className="new-tweet-container">
        <h1>Create Tweet</h1>
        <div className="new-tweet-content">
          <Form onSubmit={this.handleSubmit} className="create-tweet-form">
            <FormItem
              validateStatus={tweet.validateStatus}
              help={tweet.errorMsg}
              className="tweet-form-row"
            >
              <TextArea
                placeholder="What are you doing now ?"
                style={{ fontSize: '16px' }}
                autosize={{ minRow: 3, maxRows: 6 }}
                value={tweet.text}
                onChange={this.handleTextChange}
              />
            </FormItem>
            <FormItem className="tweet-form-row">
              <Button
                type="primary"
                htmlType="submit"
                size="large"
                disable={this.isFormInvalid}
                className="create-tweet-form-button"
              >
                Tweet
              </Button>
            </FormItem>
          </Form>
        </div>
      </div>
    );
  }
}

export default NewTweet;

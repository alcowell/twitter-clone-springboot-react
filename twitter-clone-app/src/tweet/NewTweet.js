/* eslint-disable react/prop-types */
import React, { Component } from 'react';
import { Form, Input, Button, notification } from 'antd';
import { TWEET_TEXT_MAX_LENGTH } from '../constants';
import { createTweet } from '../util/APIUtils';

const FormItem = Form.Item;
const { TextArea } = Input;

class NewTweet extends Component {
  constructor(props) {
    super(props);
    this.state = {
      text: '',
    };
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
    const { value } = event.target;
    this.setState({
      text: {
        value,
        ...this.validateText(value),
      },
    });
  }

  isFormInvalid() {
    const { text } = this.state;
    if (text.validateStatus !== 'success') {
      return true;
    }
    return false;
  }

  handleSubmit(event) {
    event.preventDefault();
    const { text } = this.state;
    const { history, handleLogout } = this.props;
    const tweetData = {
      text,
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
    const { text } = this.state;
    return (
      <div className="new-tweet-container">
        <h1>Create Tweet</h1>
        <div className="new-tweet-content">
          <Form onSubmit={this.handleSubmit} className="create-tweet-form">
            <FormItem
              validateStatus={text.validateStatus}
              help={text.errorMsg}
              className="tweet-form-row"
            >
              <TextArea
                placeholder="What are you doing now ?"
                style={{ fontSize: '16px' }}
                autosize={{ minRow: 3, maxRows: 6 }}
                value={text.value}
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

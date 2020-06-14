/* eslint-disable react/prop-types */
/* eslint-disable react/prefer-stateless-function */
/* eslint-disable max-classes-per-file */
import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { Form, Input, Button, Icon, notification } from 'antd';
import { login } from '../../util/APIUtils';
import './Login.css';
import { ACCESS_TOKEN } from '../../constants';

const FormItem = Form.Item;

class LoginForm extends Component {
  constructor(props) {
    super(props);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    const { form } = this.props;
    const { onLogin } = this.props;
    form.validateFields((err, values) => {
      if (!err) {
        const loginRequest = { ...values };
        login(loginRequest)
          .then((response) => {
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            onLogin();
          })
          .catch((error) => {
            if (error.status === 401) {
              notification.error({
                message: 'Tweet clone App',
                description: 'Your Usename or Password is incorrect.',
              });
            }
          });
      } else {
        notification.error({
          message: 'Tweet clone App',
          description: 'Sorry! Something went wrong. Please try again.',
        });
      }
    });
  }

  render() {
    const { form } = this.props;
    const { getFieldDecorator } = form;
    return (
      <Form onSubmit={this.handleSubmit} className="login-form">
        <FormItem>
          {getFieldDecorator('usernameOrUserId', {
            rules: [
              {
                required: true,
                message: 'Please input your username or userid!',
              },
            ],
          })(
            <Input
              prefix={<Icon type="user" />}
              size="large"
              name="usernameOrUserId"
              placeholder="Username or UserId"
            />
          )}
        </FormItem>
        <FormItem>
          {getFieldDecorator('password', {
            rules: [
              {
                required: true,
                message: 'Please input your password!',
              },
            ],
          })(
            <Input
              prefix={<Icon type="lock" />}
              size="large"
              name="password"
              placeholder="password"
            />
          )}
        </FormItem>
        <FormItem>
          <Button
            type="primary"
            htmlType="submit"
            size="large"
            className="login-form-button"
          >
            Login
          </Button>
          Or<Link to="/signup">register now!</Link>
        </FormItem>
      </Form>
    );
  }
}

class Login extends Component {
  render() {
    const AntWrappedLoginForm = Form.create()(LoginForm);
    const { onLogin } = this.props;
    return (
      <div className="login-container">
        <h1 className="page-title">LoginForm</h1>
        <div className="login-content">
          <AntWrappedLoginForm onLogin={onLogin} />
        </div>
      </div>
    );
  }
}

export default Login;

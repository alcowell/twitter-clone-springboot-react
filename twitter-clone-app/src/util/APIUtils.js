import { API_BASE_URL, ACCESS_TOKEN, TWEET_LIST_SIZE } from '../constants';

const request = (options) => {
  // eslint-disable-next-line no-undef
  const headers = new Headers({
    'Content-Type': 'application/json',
  });

  if (localStorage.getItem(ACCESS_TOKEN)) {
    headers.append(
      'Authorization',
      `Bearer ${localStorage.getItem(ACCESS_TOKEN)}`
    );
  }

  const defaults = { headers };
  options = { ...defaults, ...options };

  return fetch(options.url, options).then((response) =>
    response.json().then((json) => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
  );
};

// eslint-disable-next-line no-unused-vars
export function getAllTweet(page, size) {
  page = page || 0;
  size = size || TWEET_LIST_SIZE;

  return request({
    // url: `${API_BASE_URL}/tweets/`,
    url: 'http://localhost:8080/api/tweets/',
    method: 'GET',
  });
}

export function login(loginRequest) {
  return request({
    url: 'http://localhost:8080/api/auth/signin',
    method: 'POST',
    body: JSON.stringify(loginRequest),
  });
}

export function createTweet(tweetData) {
  return request({
    url: 'http://localhost:8080/api/tweets/',
    method: 'POST',
    body: JSON.stringify(tweetData),
  });
}

export function getCurrentUser() {
  if (!localStorage.getItem(ACCESS_TOKEN)) {
    return Promise.reject(new Error('No access token set.'));
  }

  return request({
    url: `http://localhost:8080/api/user/me`,
    method: 'GET',
  });
}

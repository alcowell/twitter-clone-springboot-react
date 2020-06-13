import { API_BASE_URL, ACCESS_TOKEN, TWEET_LIST_SIZE } from '../constants';

const request = (options) => {
  // eslint-disable-next-line no-undef
  const headers = new Headers({
    'Content-Type': 'application',
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

export function getAllTweet(page, size) {
  page = page || 0;
  size = size || TWEET_LIST_SIZE;

  return request({
    url: `${API_BASE_URL}/tweet?page=${page}&size=${size}`,
    method: 'GET',
  });
}

export function login(loginRequest) {
  return request({
    url: `${API_BASE_URL}/auth/signin`,
    method: 'POST',
    body: JSON.stringify(loginRequest),
  });
}

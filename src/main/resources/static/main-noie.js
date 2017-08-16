/**
 * CANNOT use `import` to import `react` or `react-dom`,
 * because `import` will run `react` before `require('es5-shim')`.
 */
// import React from 'react';
// import ReactDOM from 'react-dom';

require('uxcore/assets/blue.css');

const React = require('react');
const ReactDOM = require('react-dom');
const Request = require('superagent');
/*
 * self define components require field
 */
require('./components/login');

/**
 * CANNOT use `import` to import `es5-shim`,
 * because `import` will be transformed to `Object.defineProperty` by babel,
 * `Object.defineProperty` doesn't exists in IE8,
 * (but will be polyfilled after `require('es5-shim')` executed).
 */

require('es5-shim');
require('es5-shim/es5-sham');
require('console-polyfill');
require('es6-promise');
require('fetch-ie8');

/**
 * CANNOT use `import` to import `react` or `react-dom`,
 * because `import` will run `react` before `require('es5-shim')`.
 */
// import React from 'react';
// import ReactDOM from 'react-dom';

require('./style/style.scss');
require('uxcore/assets/blue.css');
require('uxcore/assets/iconfont.css');


const React = require('react');
const ReactDOM = require('react-dom');
/*
 * self define components require field
 */
let leftSideWidth = 190;
let MainLayout = require('./components/layout');
require('./components/side-menu');
require('./components/crud');

// ReactDOM.render(<MainLayout leftSideWidth={leftSideWidth}/>, document.getElementById('main-body'));
// require('./style/layout');

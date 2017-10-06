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

require('uxcore/assets/blue.css');

const React = require('react');
const ReactDOM = require('react-dom');

function addCententPath(url) {
    if (url.indexOf("http://") === 0 || url.indexOf("https://") === 0) {
        return url;
    }

    let contentPath = ctp;
    if (url.indexOf(contentPath) == 0) {
        contentPath = '';
    }

    return contentPath + (url.indexOf("/") === 0 ? url : ('/' + url));
}

$.ajaxSetup({
    global: true,
    headers: {
        'X-CSRF-TOKEN': csrf
    },
    beforeSend: function (xhr, req) {
        req.url = addCententPath(req.url);
        console.log(xhr, req);
    },
    error: function (request, textStatus, errorThrown) {
        /* ajax 请求跳转 */
        console.error(errorThrown, textStatus);
    },
    complete: function (XMLHttpRequest, textStatus) {
        let redirect = XMLHttpRequest.getResponseHeader('redirect');
        if (redirect) {
            if (redirect.startsWith("http")) {
                window.location = redirect;
            } else {
                window.location = ctp + redirect;
            }
        }
    }
});
require('./components/login');

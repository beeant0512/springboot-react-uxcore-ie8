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
require('uxcore/assets/iconfont.css');
require('uxcore/assets/blue.css');
require('animate.css');

const React = require('react');
const ReactDOM = require('react-dom');
/*
 * self define components require field
 */
let leftSideWidth = 190;
let MainLayout = require('./components/layout');
let LeftSideMenu = require('./components/side-menu');

const Dropdown = require('uxcore-dropdown');
const Menu = require('uxcore-menu');
const Button = require('uxcore-button');

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

let menu = <Menu>
    <Menu.Item>
        <a href="http://www.alipay.com/">详情</a>
    </Menu.Item>
    <Menu.Item>
        <a href="http://www.taobao.com/">修改密码</a>
    </Menu.Item>
    <Menu.Item>
        <a href={ctp + "/logout"}>注销</a>
    </Menu.Item>
</Menu>;

ReactDOM.render(
    <Dropdown overlay={menu}>
        <Button>{userName}</Button>
    </Dropdown>,
    document.getElementsByClassName('top-user-info')[0]
);
ReactDOM.render(<MainLayout/>, document.getElementById('main-body'));
ReactDOM.render(<LeftSideMenu url={ctp + '/menu/tree'} text={'menuName'}
                              id={'menuId'}/>, document.getElementsByClassName('left-side-menu')[0]);
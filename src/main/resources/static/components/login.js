const React = require('react');
const ReactDOM = require('react-dom');
const Request = require('superagent');

require('uxcore/assets/blue.css');


let Button = require('uxcore-button');
let Form = require('uxcore-form');
let {
    FormRowTitle,
    FormRow: Row,
    InputFormField: Input,
    OtherFormField: Other,
    Validators
} = Form;


class LoginForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};

        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        let _this = this;
        let formValues = this.refs.form.getValues();
        if (formValues.pass) {
            Request
                .post(ctp + '/login')
                .type('form')
                .send(formValues.values)
                .set('X-CSRF-TOKEN', csrf)
                .end(function (err, res) {
                    console.log(res);
                    console.log(_this);
                    if (err) {
                        //do something
                    } else {
                        if (1 == res.body.code) {

                        }
                    }
                })
        }
    }

    render() {
        let me = this;
        return (
            <div className="kuma-container-full">
                <div className="login">
                    <style>
                        {".required {font-family:Simsun} " +
                        ".login-form {width: 532px;width: 532px;margin: auto;top: 100px;position: relative;}"}
                    </style>
                    <Form ref="form" className="login-form">
                        <Input jsxname="usr" jsxlabel="用户名" autoTrim="true" jsxplaceholder="请输入用户名"
                               required={true}
                               jsxrules={[
                                   {validator: Validators.isNotEmpty, errMsg: "用户名不能为空"}]}/>
                        <Input jsxname="pwd" jsxlabel="密码" inputType="password" autoTrim="true"
                               jsxplaceholder="请输入密码"
                               required={true}
                               jsxrules={[
                                   {validator: Validators.isNotEmpty, errMsg: "密码不能为空"}]}/>
                        <Other>
                            <Button style={{marginLeft: '88px'}} onClick={this.handleClick}>登录</Button>
                        </Other>
                    </Form>
                </div>
            </div>
        )

    }
};

ReactDOM.render(<LoginForm/>, document.getElementById('login'));
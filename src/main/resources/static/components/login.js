const React = require('react');
const ReactDOM = require('react-dom');
require('uxcore/assets/blue.css');

let Button = require('uxcore-button');
let Form = require('uxcore-form');
let {
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

    saveRef(refName) {
        const me = this;
        return (c) => {
            me[refName] = c;
        };
    }

    handleClick() {

        let _this = this;
        let formValues = this.form.getValues();
        if (formValues.pass) {
            $.post({
                headers: {
                    'X-CSRF-TOKEN': csrf
                },
                url: ctp + '/login',
                data: formValues.values,
                success: function (res) {
                    if (res.success) {
                        window.location.href = res.data;
                    } else {
                        let codeToField = {
                            2: 'usr',
                            1: 'pwd'
                        };
                        _this.setState({
                            errMsg: res.msg,
                            isDirty: true,
                            field: codeToField[res.code],
                            submit: true,
                        }, () => {
                            _this.form.doValidate();
                        });

                        _this.setState({
                            errMsg: '',
                            isDirty: false,
                            field: codeToField[res.code],
                            submit: false,
                        });
                    }
                }
            });
        }
    }

    render() {
        let _this = this;
        return (
            <div className="kuma-container-full">
                <div className="login">
                    <style>
                        {".required {font-family:Simsun} " +
                        ".login-form {width: 532px;width: 532px;margin: auto;top: 100px;position: relative;}"}
                    </style>
                    <Form ref={this.saveRef('form')} className="login-form" jsxonChange={function (values, name, pass) {
                        _this.form.doValidate();
                    }}>
                        <Input jsxname="usr" jsxlabel="用户名" autoTrim={true} jsxplaceholder="请输入用户名"
                               required={true}
                               jsxrules={[
                                   {validator: Validators.isNotEmpty, errMsg: "用户名不能为空"},
                                   {
                                       validator: (value) => {
                                           return !(this.state.field === 'usr' && this.state.isDirty)
                                       }, errMsg: this.state.errMsg
                                   }]}/>
                        <Input jsxname="pwd" jsxlabel="密码" inputType="password" autoTrim={true}
                               jsxplaceholder="请输入密码"
                               required={true}
                               jsxrules={[
                                   {validator: Validators.isNotEmpty, errMsg: "密码不能为空"},
                                   {
                                       validator: (value) => {
                                           return !(this.state.field === 'pwd' && this.state.isDirty)
                                       }, errMsg: this.state.errMsg
                                   }]}/>
                        <Other>
                            <Button style={{marginLeft: '88px'}} onClick={this.handleClick}>登录</Button>
                        </Other>
                    </Form>
                </div>
            </div>
        )

    }
}

ReactDOM.render(<LoginForm/>, document.getElementById('login'));
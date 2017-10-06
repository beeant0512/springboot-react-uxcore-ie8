let Table = require("uxcore-table");
let Button = require('uxcore-button');
let Form = require('uxcore-form');
let Dialog = require('uxcore-dialog');
let classnames = require("classnames");
let DeleteDialog = require("./delete-dialog");
const Formatter = require('uxcore-formatter');
/*
 * 讲解：从 Form 中取出 Form 的零件用以配置生成一个完整的 Form。
 * Form 的使用文档见：http://uxco.re/components/form/
 */
let {FormRow, InputFormField, OtherFormField, DateFormField, Validators, SelectFormField, TableFormField} = Form;

class UserEditForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        let me = this;
        return <Form className="" jsxvalues={me.props.value} ref="form">
            <FormRow>
                <InputFormField jsxlabel="用户名" jsxname="userName"
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
                <InputFormField jsxname="userId" jsxshow={false}/>
            </FormRow>
            <FormRow>
                <SelectFormField jsxlabel="角色" jsxname="role"
                                 multiple
                                 onSelect={(...args) => {
                                     console.log(...args);
                                 }}
                                 allowClear
                                 jsxdata={me.props.value.roleItems}
                                 jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
            </FormRow>
        </Form>;
    }
}

class UserTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: {
                num: 2,
                action: 'link',
                style: 'border',
                size: 'small',
            },
            showAdSearch: false,
            fetchParams: {},
            editShow: false,
            newShow: false,
            resetPasswordShow: false,
            delShow: false,
            editValues: null,
        }
    }

    handleSearchAdClick() {
        let me = this;
        me.setState({
            showAdSearch: !me.state.showAdSearch
        });
    }

    handleSearch() {
        let me = this;
        let data = me.refs.searchForm.getValues();
        me.setState({
            fetchParams: data.values
        }, function () {
            me.refs.table.fetchData();
        })
    }

    handleAdSearch() {
        let me = this;
        let data = me.refs.searchAdForm.getValues();
        me.setState({
            fetchParams: data.values
        }, function () {
            me.refs.table.fetchData();
        })
    }

    handleResetClick() {
        let me = this;
        me.refs.searchAdForm.resetValues();
    }

    toggleShow(key) {
        let me = this;
        let obj = {};
        obj[key] = !me.state[key];
        me.setState(obj);
    }

    handleResetPasswordOk() {
        let me = this;
        let data = me.refs.resetPwdForm.getValues();
        if (data.pass) {
            $.ajax({
                url: ctp + '/user/resetpwd/' + me.state.editValues.userId,
                method: 'put',
                traditional: true,
                data: data.values,
                success: function (result) {
                    if (result.success) {
                        me.toggleShow('resetPasswordShow');
                        me.refs.table.fetchData();
                    }
                }
            })
        }
    }

    handleResetPasswordCancel() {
        let me = this;
        me.toggleShow('resetPasswordShow');
    }

    handleEditOk() {
        let me = this;
        let data = me.refs.editUserForm.refs.form.getValues();
        if (data.pass) {
            let roles = [];
            data.values.role.forEach(function (value) {
                roles.push(value);
            });
            delete data.values.role;
            data.values.roles = roles;
            $.ajax({
                url: ctp + '/user/' + data.values.userId,
                method: 'put',
                traditional: true,
                data: data.values,
                success: function (result) {
                    if (result.success) {
                        me.toggleShow('editShow');
                        me.refs.editUserForm.refs.form.resetValues();
                        me.refs.table.fetchData();
                    }
                }
            })
        }
    }

    handleEditCancel() {
        let me = this;
        me.toggleShow('editShow');
        me.refs.editUserForm.refs.form.resetValues();
    }

    handleNewOk() {
        let me = this;
        let data = me.refs.newForm.getValues();
        if (data.pass) {
            let roles = [];
            data.values.role.forEach(function (value) {
                roles.push(value.key);
            });
            delete data.values.role;
            data.values.roles = roles;
            $.ajax({
                url: ctp + '/user',
                method: 'post',
                traditional: true,
                data: data.values,
                success: function (result) {
                    if (result.success) {
                        me.toggleShow('newShow');
                        me.refs.table.fetchData();
                        me.refs.newForm.resetValues();
                    }
                }
            })
        }
    }

    handleNewCancel() {
        let me = this;
        me.toggleShow('newShow');
        me.refs.newForm.resetValues();
    }

    showDialog(key, editValues) {
        let me = this;
        let obj = {};
        obj[key] = !me.state[key];
        obj['editValues'] = editValues || {};
        me.setState(obj);
    }

    formatDate(value, format) {
        if (isNaN(value) || value === null) {
            return value;
        }
        return Formatter.date(value, format);
    }

    render() {
        let me = this;

        let columns = [
            {dataKey: 'userId', title: 'ID', width: 50, hidden: true},
            {dataKey: 'userName', title: '用户名', width: 200, ordered: true},
            {dataKey: 'lastLoginAt', title: '最后登录时间', width: 150, ordered: true},
            {
                dataKey: 'action', title: '操作', width: 200, type: 'action',
                actionType: this.state.value.action,
                collapseNum: this.state.value.num || 3, // 超过 3 个将开始折叠
                actions: {
                    '编辑': function (rowData, actions) {
                        $.ajax({
                            url: ctp + '/role/userRole/' + rowData.userId,
                            success: function (obj) {
                                let selected = [], items = {};
                                obj.selected.forEach((item) => {
                                    // selected[item.roleId] = item.roleName;
                                    selected.push(item.roleId);
                                    items[item.roleId.toString()] = item.roleName;
                                });
                                obj.unselected.forEach((item) => {
                                    items[item.roleId.toString()] = item.roleName;
                                });
                                rowData.role = selected;
                                rowData.roleItems = items;
                                me.showDialog('editShow', rowData);
                            }
                        });
                    },
                    '重置密码': function (rowData) {
                        me.selected = [rowData];
                        me.showDialog('resetPasswordShow', rowData);
                    },
                    '删除': function (rowData) {
                        me.refs.deleteDialog.show([rowData]);
                    }
                }
            }
        ];

        let tableProps = {
            fetchUrl: ctp + "/user/table",
            jsxcolumns: columns,
            fetchParams: me.state.fetchParams,
            actionBar: {
                '新增': function () {
                    me.showDialog('newShow');
                },
                '删除': function () {
                    me.refs.deleteDialog.show();
                }
            },
            beforeFetch: function (data, from) {
                if (data.lastLoginAt) {
                    data.lastLoginAtBegin = me.formatDate(data.lastLoginAt[0], 'yyyy-MM-DD');
                    data.lastLoginAtEnd = me.formatDate(data.lastLoginAt[1], 'yyyy-MM-DD');
                    delete data.lastLoginAt;
                }
                return data;
            },
            rowSelection: {
                onSelect: function (record, selected, selectedRows) {
                    me.selected = selectedRows;
                },
                onSelectAll: function (selected, selectedRows) {
                    me.selected = selectedRows;
                }
            }
        };

        let form = <Form className="" jsxvalues={me.state.editValues} ref="newForm">
            <FormRow>
                <InputFormField jsxlabel="用户名" jsxname="userName"
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
                <InputFormField jsxname="userId" jsxshow={false}/>
            </FormRow>
            <FormRow>
                <InputFormField jsxlabel="密码" jsxname="userPassword" inputType={'password'}
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
            </FormRow>
            <FormRow>
                <SelectFormField jsxlabel="角色" jsxname="role"
                                 multiple
                                 jsxfetchUrl={ctp + '/role/userRole/' + (me.state.editValues ? me.state.editValues.userId ? me.state.editValues.userId : 0 : 0)}
                                 onSelect={(...args) => {
                                     console.log(...args);
                                 }}
                                 beforeFetch={function (data) {
                                     console.log(data);
                                     if (data.q === undefined) {
                                         data.q = 'a';
                                     }
                                     return data;
                                 }}
                                 afterFetch={(obj) => {
                                     let data = {};
                                     obj.unselected.forEach((item) => {
                                         data[item.roleId] = item.roleName;
                                     });
                                     return data;
                                 }}
                                 jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
            </FormRow>
        </Form>;
        let resetPwdForm = <Form className="" jsxvalues={me.state.editValues} ref="resetPwdForm">
            <FormRow>
                <InputFormField jsxlabel="新密码" jsxname="password" inputType={'password'}
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
            </FormRow>
        </Form>;
        return (
            <div className="site-content-body">
                <h2>用户管理</h2>
                <Form ref="searchForm" className="search-form">
                    <FormRow>
                        <InputFormField jsxname="userName" jsxshowLabel={false} jsxplaceholder="输入用户名进行查询"/>
                        <OtherFormField className="search-container">
                            <Button onClick={me.handleSearch.bind(me)}>查询</Button>
                            <div className="updown" onClick={me.handleSearchAdClick.bind(me)}>
                                <a href="javascript:;">高级查询</a><i className={classnames({
                                "kuma-icon": true,
                                "kuma-icon-title-up": me.state.showAdSearch,
                                "kuma-icon-title-down": !me.state.showAdSearch
                            })}></i>
                            </div>
                        </OtherFormField>
                    </FormRow>
                </Form>
                <Form ref="searchAdForm" className="search-form">
                    <FormRow className={classnames({"hidden": !me.state.showAdSearch, "show": me.state.showAdSearch})}>
                        <InputFormField jsxlabel="用户名" jsxname="userName"/>
                        <DateFormField jsxlabel="登录时间" jsxname="lastLoginAt" autoMatchWidth={true} jsxtype="cascade"
                                       format={'yyyy-MM-DD'}/>
                        <OtherFormField className="searchButton">
                            <Button type="primary" onClick={me.handleAdSearch.bind(me)}>提交</Button>
                            <Button type="secondary" onClick={me.handleResetClick.bind(me)}>重置</Button>
                        </OtherFormField>
                    </FormRow>
                </Form>
                <Table {...tableProps} ref="table"/>
                <Dialog ref="editDialog" width={500} visible={me.state.editShow} title="编辑用户"
                        onOk={me.handleEditOk.bind(me)} onCancel={me.handleEditCancel.bind(me)}>
                    <UserEditForm value={me.state.editValues} ref="editUserForm"/>
                </Dialog>
                <Dialog ref="resetPasswordDialog" width={500} visible={me.state.resetPasswordShow} title="重置密码"
                        onOk={me.handleResetPasswordOk.bind(me)} onCancel={me.handleResetPasswordCancel.bind(me)}>
                    {resetPwdForm}
                </Dialog>
                <Dialog ref="newDialog" width={500} visible={me.state.newShow} title="新增用户"
                        onOk={me.handleNewOk.bind(me)} onCancel={me.handleNewCancel.bind(me)}>
                    {form}
                </Dialog>
                <DeleteDialog ref="deleteDialog" {... {
                    url: "/user/delete",
                    id: 'userId',
                    text: 'userName',
                    table: me,
                    tableName: 'table'
                }} />
            </div>
        )
    }

}

export default UserTable;
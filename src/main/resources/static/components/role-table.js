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
let {FormRow, InputFormField, OtherFormField, DateFormField, Validators, NumberInputFormField, SelectFormField} = Form;


class RoleTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: {
                style: 'border',
                size: 'small',
            },
            showAdSearch: false,
            fetchParams: {},
            editShow: false,
            newShow: false,
            memberShow: false,
            delMemberShow: false,
            deleteRoleUser: null,
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

    handleEditOk() {
        let me = this;
        let data = me.refs.editForm.getValues();
        if (data.pass) {
            $.ajax({
                url: ctp + '/role/' + data.values.roleId,
                method: 'put',
                data: data.values,
                success: function (result) {
                    if (result.success) {
                        me.toggleShow('editShow');
                        me.refs.table.fetchData();
                    }
                }
            })
        }
    }

    handleEditCancel() {
        let me = this;
        me.refs.editForm.resetValues();
        me.toggleShow('editShow');
    }

    handleNewOk() {
        let me = this;
        let data = me.refs.editForm.getValues();
        if (data.pass) {
            $.ajax({
                url: ctp + '/role',
                method: 'post',
                data: data.values,
                success: function (result) {
                    if (result.success) {
                        me.toggleShow('newShow');
                        me.refs.table.fetchData();
                        me.refs.editForm.resetValues();
                    }
                }
            })
        }
    }

    handleNewCancel() {
        let me = this;
        me.toggleShow('newShow');
        me.refs.editForm.resetValues();
    }

    showEditDialog(rowData) {
        this.setState({
            editShow: true,
            editValues: rowData
        });
    }

    showDialog(key) {
        let me = this;
        let obj = {};
        obj[key] = !me.state[key];
        obj['editValues'] = {};
        me.setState(obj);
    }

    formatDate(value, format) {
        if (isNaN(value) || value === null) {
            return value;
        }
        return Formatter.date(value, format);
    }

    showDeleteUserDialog(rowData) {
        let me = this;
        me.setState({
            delMemberShow: true,
            deleteRoleUser: rowData
        });
    }

    showMemberDialog(rowData) {
        let me = this;
        me.setState({
            memberShow: true,
            editValues: rowData
        });
    }

    handleHideMemberDialog() {
        let me = this;
        me.setState({
            memberShow: false,
            editValues: null
        });
    }

    handleMemberDeleteCancel(){
        let me = this;
        me.setState({
            delMemberShow: false
        })
    }

    handleMemberDeleteOk() {
        let me = this;
        let selectedItems = me.state.deleteRoleUser || [];
        let keys = [];
        selectedItems.forEach(function (value, index, array) {
            keys.push(value.userId);
        });
        $.ajax({
            url: ctp + "/role/members/" + me.state.editValues.roleId,
            method: 'post',
            traditional: true,
            data: {id: keys},
            success: function (res) {
                if (res.success) {
                    selectedItems.forEach(function (value) {
                        me.refs.memberTable.delRow(value);
                    });
                    me.setState({
                        delMemberShow: false
                    })
                }
            }
        })
    }

    render() {
        let me = this;

        let columns = [
            {dataKey: 'roleId', title: 'ID', width: 50, hidden: true},
            {dataKey: 'roleName', title: '角色名称', width: 200, ordered: true},
            {dataKey: 'roleKey', title: '角色编码', width: 200, ordered: true},
            {
                dataKey: 'action', title: '操作', width: 250, type: 'action', rightFixed: true,
                actions: {
                    '编辑': function (rowData, actions) {
                        me.showEditDialog(rowData);
                    },
                    '成员管理': function (rowData, actions) {
                        me.showMemberDialog(rowData);
                    },
                    '删除': function (rowData) {
                        me.refs.deleteDialog.show([rowData]);
                    }
                }
            }
        ];

        let tableProps = {
            fetchUrl: ctp + "/role/table",
            jsxcolumns: columns,
            renderModel: 'tree',
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
                if (data.createAt) {
                    data.createAtBegin = me.formatDate(data.createAt[0], 'yyyy-MM-DD');
                    data.createAtEnd = me.formatDate(data.createAt[1], 'yyyy-MM-DD');
                    delete data.createAt;
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

        let form = <Form className="" jsxvalues={me.state.editValues} ref="editForm">
            <FormRow>
                <InputFormField jsxlabel="角色名称" jsxname="roleName"
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
                <InputFormField jsxname="roleId" jsxshow={false}/>
            </FormRow>
            <FormRow>
                <InputFormField jsxlabel="角色编码" jsxname="roleKey"/>
            </FormRow>
        </Form>;

        let memberTableProps = {
            fetchUrl: ctp + "/role/members/" + (me.state.editValues ? me.state.editValues.roleId : 0),
            jsxcolumns: [
                {dataKey: 'userId', title: 'ID', width: 50, hidden: true},
                {dataKey: 'userName', title: '用户名', width: 200, ordered: true},
                {dataKey: 'lastLoginAt', title: '最后登录时间', width: 150, ordered: true},
                {
                    dataKey: 'action', title: '操作', width: 200, type: 'action',
                    actionType: this.state.value.action,
                    collapseNum: this.state.value.num || 3, // 超过 3 个将开始折叠
                    actions: {
                        '删除': function (rowData) {
                            me.showDeleteUserDialog([rowData]);
                        }
                    }
                }
            ],
            fetchParams: me.state.fetchParams,
            actionBar: {
                '删除': function () {
                    me.showDeleteUserDialog(me.selectedUser);
                }
            },
            rowSelection: {
                onSelect: function (record, selected, selectedRows) {
                    me.selectedUser = selectedRows;
                },
                onSelectAll: function (selected, selectedRows) {
                    me.selectedUser = selectedRows;
                }
            }
        };
        let memberTable = <Table {...memberTableProps} ref="memberTable"/>;

        return (
            <div className="site-content-body">
                <h2>角色管理</h2>
                <Form ref="searchForm" className="search-form">
                    <FormRow>
                        <InputFormField jsxname="roleName" jsxshowLabel={false} jsxplaceholder="输入角色名称字进行查询"/>
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
                        <InputFormField jsxlabel="角色名称" jsxname="roleName"/>
                        <InputFormField jsxlabel="角色编码" jsxname="roleKey"/>
                    </FormRow>
                    <FormRow className={classnames({"hidden": !me.state.showAdSearch, "show": me.state.showAdSearch})}>
                        <DateFormField jsxlabel="创建日期" jsxname="createAt" jsxtype="cascade" autoMatchWidth={true}
                                       format={'yyyy-MM-DD'}/>
                        <OtherFormField className="searchButton">
                            <Button type="primary" onClick={me.handleAdSearch.bind(me)}>提交</Button>
                            <Button type="secondary" onClick={me.handleResetClick.bind(me)}>重置</Button>
                        </OtherFormField>
                    </FormRow>
                </Form>
                <Table {...tableProps} ref="table"/>
                <Dialog ref="editDialog" width={500} visible={me.state.editShow} title="角色编辑"
                        onOk={me.handleEditOk.bind(me)} onCancel={me.handleEditCancel.bind(me)}>
                    {form}
                </Dialog>
                <Dialog ref="memberDialog" width={500} visible={me.state.memberShow} title="成员管理"
                        onOk={me.handleHideMemberDialog.bind(me)} onCancel={me.handleHideMemberDialog.bind(me)}>
                    {memberTable}
                </Dialog>
                <Dialog ref="newDialog" width={500} visible={me.state.newShow} title="角色新增"
                        onOk={me.handleNewOk.bind(me)} onCancel={me.handleNewCancel.bind(me)}>
                    {form}
                </Dialog>
                <DeleteDialog ref="deleteDialog" {... {
                    url: "/role/delete",
                    id: 'roleId',
                    text: 'roleName',
                    table: me,
                    tableName: 'table'
                }} />
                <Dialog ref="delMemberDialog" width={200} visible={me.state.delMemberShow} title="确认删除？"
                        onOk={me.handleMemberDeleteOk.bind(me)} onCancel={me.handleMemberDeleteCancel.bind(me)}>
                </Dialog>
            </div>
        )
    }

}

export default RoleTable;
let Table = require("uxcore-table");
let Button = require('uxcore-button');
let Form = require('uxcore-form');
let Dialog = require('uxcore-dialog');
let classnames = require("classnames");
const Formatter = require('uxcore-formatter');
/*
 * 讲解：从 Form 中取出 Form 的零件用以配置生成一个完整的 Form。
 * Form 的使用文档见：http://uxco.re/components/form/
 */
let {FormRow, InputFormField, OtherFormField, DateFormField, Validators, NumberInputFormField, SelectFormField} = Form;

/*
 * 讲解：object-assign 是一个非常实用的用于对象拷贝和扩展的函数
 * 详细说明见 https://www.npmjs.com/package/object-assign
 */

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

    showMemberDialog(rowData) {
        let me = this;
        $.ajax({
            url: ctp + '/role/members/' + rowData.roleId,
            method: 'get',
            async: false,
            success: function (members) {
                me.setState({
                    memberShow: true,
                    members: members
                });
            }
        })
    }

    handleDeleteOk() {
        let me = this;
        let selectedItems = me.selected;
        let keys = [];
        selectedItems.forEach(function (value, index, array) {
            keys.push(value.roleId);
        });
        $.ajax({
            url: ctp + "/role/delete",
            method: 'post',
            traditional: true,
            data: {id: keys},
            success: function (res) {
                if (res.success) {
                    selectedItems.forEach(function (value) {
                        me.refs.table.delRow(value);
                    });
                    me.toggleShow('delShow');
                }
            }
        })
    }

    handleDeleteCancel() {
        let me = this;
        me.toggleShow('delShow');
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
                        me.selected = [rowData];
                        me.showDialog('delShow');
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
                    me.showDialog('delShow');
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
                <Dialog ref="editDialog" width={500} visible={me.state.editShow} title="数据编辑"
                        onOk={me.handleEditOk.bind(me)} onCancel={me.handleEditCancel.bind(me)}>
                    {form}
                </Dialog>
                <Dialog ref="newDialog" width={500} visible={me.state.newShow} title="数据新增"
                        onOk={me.handleNewOk.bind(me)} onCancel={me.handleNewCancel.bind(me)}>
                    {form}
                </Dialog>
                <Dialog ref="delDialog" width={200} visible={me.state.delShow} title="确认删除？"
                        onOk={me.handleDeleteOk.bind(me)} onCancel={me.handleDeleteCancel.bind(me)}>
                </Dialog>
            </div>
        )
    }

}

export default RoleTable;
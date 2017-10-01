let Table = require("uxcore-table");
let Button = require('uxcore-button');
let Form = require('uxcore-form');
let Dialog = require('uxcore-dialog');
/*
 * 讲解：从 Form 中取出 Form 的零件用以配置生成一个完整的 Form。
 * Form 的使用文档见：http://uxco.re/components/form/
 */
let {FormRow, InputFormField, OtherFormField, Validators, ButtonGroupFormField, TableFormField} = Form;

/*
 * 讲解：object-assign 是一个非常实用的用于对象拷贝和扩展的函数
 * 详细说明见 https://www.npmjs.com/package/object-assign
 */
let assign = require('object-assign');

class MenuTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            fetchParams: {},
            editShow: false,
            newShow: false,
            delShow: false,
            editValues: null,
        }
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
                url: ctp + '/menu/' + data.values.menuId,
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
                url: ctp + '/menu',
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

    handleDeleteOk() {
        let me = this;
        let selectedItems = me.selected;
        let keys = [];
        selectedItems.forEach(function (value, index, array) {
            keys.push(value.menuId);
        });
        $.ajax({
            url: ctp + "/menu/delete",
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

    render() {
        let me = this;

        let columns = [
            {dataKey: 'menuId', title: 'ID', width: 50, hidden: true},
            {dataKey: 'menuName', title: '菜单名称', width: 200, ordered: true},
            {dataKey: 'menuUrl', title: 'URL地址', width: 150, ordered: true},
            {
                dataKey: 'menuPerm', title: '权限', width: 100, type: 'action',
                actions: {
                    '编辑': function (rowData, actions) {
                        me.showEditDialog(rowData);
                    }
                }
            }
        ];

        let tableProps = {
            fetchUrl: ctp + "/menu/treeTable",
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
                <InputFormField jsxlabel="菜单名称" jsxname="menuName"
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
                <InputFormField jsxname="menuId" jsxshow={false}/>
            </FormRow>
            <FormRow>
                <InputFormField jsxlabel="URL地址" jsxname="menuUrl"
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
                <InputFormField jsxlabel="权限" jsxname="menuPerm"
                                jsxrules={{validator: Validators.isNotEmpty, errMsg: "非空"}}/>
            </FormRow>
        </Form>;

        return (
            <div className="site-content-body">
                <h2>菜单管理</h2>
                <Form ref="searchForm" className="searchForm">
                    <FormRow>
                        <InputFormField jsxname="menuName" jsxshowLabel={false} jsxplaceholder="输入菜单名称字进行查询"/>
                        <OtherFormField className="searchButton">
                            <Button onClick={me.handleSearch.bind(me)}>查询</Button>
                        </OtherFormField>
                    </FormRow>
                </Form>
                <Table {...tableProps} ref="table"/>
                <Dialog ref="editDialog" width={1000} visible={me.state.editShow} title="数据编辑"
                        onOk={me.handleEditOk.bind(me)} onCancel={me.handleEditCancel.bind(me)}>
                    {form}
                </Dialog>
                <Dialog ref="newDialog" width={1000} visible={me.state.newShow} title="数据新增"
                        onOk={me.handleNewOk.bind(me)} onCancel={me.handleNewCancel.bind(me)}>
                    {form}
                </Dialog>
                <Dialog ref="delDialog" width={1000} visible={me.state.delShow} title="确认删除？"
                        onOk={me.handleDeleteOk.bind(me)} onCancel={me.handleDeleteCancel.bind(me)}>
                </Dialog>
            </div>
        )
    }

}

export default MenuTable;
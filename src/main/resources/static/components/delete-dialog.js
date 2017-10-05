let Dialog = require('uxcore-dialog');

class DeleteDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            data: null
        }
    }

    show(selected) {
        let me = this;
        me.setState({
            data: selected || me.props.table.selected ,
            show: true
        })
    }

    hide() {
        this.setState({
            show: false
        });
    }

    handleDeleteOk() {
        let me = this;
        let selectedItems = me.state.data;
        let keys = [];
        selectedItems.forEach(function (value, index, array) {
            keys.push(value[me.props.id]);
        });
        $.ajax({
            url: ctp + me.props.url,
            method: 'post',
            traditional: true,
            data: {id: keys},
            success: function (res) {
                if (res.success) {
                    selectedItems.forEach(function (value) {
                        me.props.table.refs[me.props.tableName].delRow(value);
                    });
                    me.hide();
                }
            }
        })
    }

    render() {
        let me = this;
        return <Dialog ref="delDialog" width={200} visible={me.state.show} title="确认删除？"
                       onOk={me.handleDeleteOk.bind(me)} onCancel={me.hide.bind(me)}>
        </Dialog>
    }
}

export default DeleteDialog;
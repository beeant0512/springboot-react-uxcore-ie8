let Menu = require('uxcore-menu');
let SubMenu = Menu.SubMenu;
let MenuItem = Menu.Item;

class LeftSideMenu extends React.Component {

    constructor(props) {
        super(props);
        let _this = this;
        _this.state = {
            current: '1',
            openKeys: []
        };
        _this.data = [];
        $.get({
            url: _this.props.url,
            async: false,
            success: function (res) {
                if (res.success) {
                    _this.data = res.data;
                }
            }
        });
    }

    handleClick(e) {
        this.setState({
            current: e.key,
            openKeys: e.keyPath.slice(1)
        });
    }

    onToggle(info) {
        this.setState({
            openKeys: info.openKeys
        });
    }

    build_menu(list) {
        let _this = this;
        let items = [];
        list.map(function (item) {
                items.push(_this.createMenuItem(item));
            }
        );

        return items;
    }

    createMenuItem(item) {
        let _this = this;
        if (item.child !== undefined && item.child.length > 0) {
            return <SubMenu key={item[_this.props.id]} title={
                <span><i className="kuma-icon kuma-icon-email"></i><span>{item[_this.props.text]}</span></span>}>
                {_this.build_menu(item.child)}
            </SubMenu>;
        }
        return <Menu.Item key={item[_this.props.id]}>
            <i className="kuma-icon kuma-icon-email"></i>{item[_this.props.text]}</Menu.Item>;
    }

    render() {
        let _this = this;
        return (<Menu onClick={_this.handleClick.bind(_this)}
                      style={{border: 'none'}}
                      openKeys={_this.state.openKeys}
                      onOpen={_this.onToggle.bind(_this)}
                      onClose={_this.onToggle.bind(_this)}
                      selectedKeys={[_this.state.current]}
                      mode="inline">
            {
                _this.build_menu(_this.data)
            }
        </Menu>)
    }
}

ReactDOM.render(<LeftSideMenu url={ctp + '/menu/tree'} text={'menuName'}
                              id={'menuId'}/>, document.getElementsByClassName('left-side-menu')[0]);

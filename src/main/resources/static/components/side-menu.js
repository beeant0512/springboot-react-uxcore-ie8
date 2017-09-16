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
            url: ctp + '/menu/tree',
            async: false,
            success: function (res) {
                if (res.success) {
                    _this.data = res.data;
                }
            }
        });
    }

    handleClick(e) {
        console.info('click ', e);
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
        let menu = [];
        list.map(function (item) {
                menu.push(_this._create_menu_item(item));
            }
        );

        return menu;
    }

    _create_menu_item(menu) {
        let _this = this;
        if (menu.child != undefined && menu.child.length > 0) {
            return <SubMenu key={menu.menuId} title={<span><i
                className="kuma-icon kuma-icon-email"></i><span>{menu.menuName}</span></span>}>
                {_this.build_menu(menu.child)}
            </SubMenu>;
        }
        return <Menu.Item key={menu.menuId}>
            <i className="kuma-icon kuma-icon-email"></i>{menu.menuName}</Menu.Item>;
    }

    render() {
        let _this = this;
        return (<Menu onClick={_this.handleClick.bind(_this)}
                      style={{width: 240}}
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

ReactDOM.render(<LeftSideMenu/>, document.getElementsByClassName('left-side-menu')[0]);

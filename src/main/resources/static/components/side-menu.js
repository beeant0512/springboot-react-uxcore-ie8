let Menu = require('uxcore-menu');
let SubMenu = Menu.SubMenu;
let MenuItem = Menu.Item;

class LeftSideMenu extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            current: '1',
            openKeys: []
        };
    }

    handleClick(e) {
        console.info('click ', e);
        this.setState({
            current: e.key,
            openKeys: e.keyPath.slice(1)
        });
    }

    onToggle(info) {
        console.info(info);
        this.setState({
            openKeys: info.openKeys
        });
    }

    _create_menu_item(menu) {
        return <Menu.Item key={menu.menuId} >
            <i className="kuma-icon kuma-icon-email"></i>{menu.menuName}</Menu.Item>;
    }

    render() {
        let _this = this;
        let _render;
        $.get({
            url: ctp + '/menu/list',
            async: false,
            success: function (res) {
                if (res.success) {
                    _render = <Menu onClick={_this.handleClick.bind(_this)}
                                    style={{width: 240}}
                                    openKeys={_this.state.openKeys}
                                    onOpen={_this.onToggle.bind(_this)}
                                    onClose={_this.onToggle.bind(_this)}
                                    selectedKeys={[_this.state.current]}
                                    mode="inline">
                        {
                            res.data.map((e, index) =>
                                _this._create_menu_item(e)
                            )
                        }
                    </Menu>;
                }
            }
        });

        return (
            _render
        );
    }
}

ReactDOM.render(<LeftSideMenu/>, document.getElementsByClassName('left-side-menu')[0]);

let Menu = require('uxcore-menu');
let SubMenu = Menu.SubMenu;
let MenuItem = Menu.Item;
const Request = require('superagent');

class LeftSideMenu extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            current: '1',
            openKeys: []
        };
    }

    handleClick(e) {
        console.log('click ', e);
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

    render() {
        return (
            <Menu onClick={this.handleClick.bind(this)}
                  style={{width: 240}}
                  openKeys={this.state.openKeys}
                  onOpen={this.onToggle.bind(this)}
                  onClose={this.onToggle.bind(this)}
                  selectedKeys={[this.state.current]}
                  mode="inline">
            </Menu>
        );
    }
}
ReactDOM.render(<LeftSideMenu/>, document.getElementsByClassName('left-side-menu'));

let Layout = require('uxcore-layout');
let {Left, Right} = Layout;
let classnames = require('classnames');

class MainLayout extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return <div>
            <Layout className="layoutDemo">
                <Left width={190} className="left">左</Left>
                <Right adaptive={true} className="right">右</Right>
            </Layout>
        </div>

    }
}

ReactDOM.render(<MainLayout/>, document.getElementById('main-body'));
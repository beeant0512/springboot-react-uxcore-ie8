let webpack = require('webpack');
let path = require('path');

let LodashModuleReplacementPlugin = require('lodash-webpack-plugin');
let ExtractTextPlugin = require('extract-text-webpack-plugin');
const extractSass = new ExtractTextPlugin("./css/style.css");
const extractCSS = new ExtractTextPlugin("./css/styles.css");

let TransferWebpackPlugin = require('transfer-webpack-plugin');
let staticFilesPath = path.resolve(__dirname, "src/main/resources/static");
let buildPath = staticFilesPath + "/build";
let nodeModulesPath = path.resolve(__dirname, 'node_modules');
module.exports = {
    entry: {
        main: staticFilesPath + '/main.js',
        login: staticFilesPath + '/login.js'
    },
    output: {
        path: buildPath,
        filename: "js/[name].js",
        chunkFilename: "js/[name].js"
    },
    // 此项配置可以将某些库设置为外部引用，内部不会打包合并进去。
    externals: {
        'react': 'React',
        'react-dom': 'ReactDOM',
        // 'lodash': '_',
        'babel-polyfill': 'window',
    },
    debug: true,
    devtool: 'source-map',
    plugins: [
        new webpack.optimize.CommonsChunkPlugin("common.js", ["main", "login"]),
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
        }),
        extractCSS,
        extractSass,
        new LodashModuleReplacementPlugin,
        // 把指定文件夹下的文件复制到指定的目录
        new TransferWebpackPlugin([
            {from: nodeModulesPath + '/react/dist/', to: "../lib/react"},
            {from: nodeModulesPath + '/react-dom/dist/', to: "../lib/react"},
            {from: nodeModulesPath + '/es5-shim/', to: "../lib/es5-shim"}
        ])
    ],
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: [nodeModulesPath],
                loaders: ['babel-loader'],
            },
            {
                test: /\.(scss)$/,
                loader: extractSass.extract("style-loader", "css-loader!sass-loader")
            },
            {
                test: /\.(css)$/,
                loader: extractCSS.extract("style-loader", "css-loader")
            },
        ],
        postLoaders: [
            {
                test: /\.js$/,
                loaders: ['es3ify-loader'],
            },
        ],
    }
};
let webpack = require('webpack');
let path = require('path');

let LodashModuleReplacementPlugin = require('lodash-webpack-plugin');
let ExtractTextPlugin = require('extract-text-webpack-plugin');
let TransferWebpackPlugin = require('transfer-webpack-plugin');
let staticFilesPath = path.resolve(__dirname, "src/main/resources/static");
let buildPath = staticFilesPath + "/build";
let nodeModulesPath = path.resolve(__dirname, 'node_modules');
let OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');

module.exports = {
    entry: staticFilesPath + '/main-noie.js',
    output: {
        path: buildPath,
        filename: './bundle.js'
    },
    // 此项配置可以将某些库设置为外部引用，内部不会打包合并进去。
    externals: {
        'react': 'React',
        'react-dom': 'ReactDOM',
        // 'lodash': '_',
        'babel-polyfill': 'window'
    },
    debug: true,
    devtool: 'source-map',
    plugins:[
        //压缩打包的文件
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                //supresses warnings, usually from module minification
                warnings: false
            }
        }),
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
        }),
        new ExtractTextPlugin("../css/styles.css"),
        // css压缩
        new OptimizeCssAssetsPlugin({
            assetNameRegExp: /\.optimize\.css$/g,
            cssProcessor: require('cssnano'),
            cssProcessorOptions: { discardComments: {removeAll: true } },
            canPrint: true
        }),
        new LodashModuleReplacementPlugin,
        // 把指定文件夹下的文件复制到指定的目录
        new TransferWebpackPlugin([
            {from: nodeModulesPath + '/react/dist/', to: "../lib/react"},
            {from: nodeModulesPath + '/react-dom/dist/', to: "../lib/react"}
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
                test: /\.less/,
                loader: 'style-loader!css-loader!less-loader'
            },
            {
                test: /\.(css)$/,
                loader: ExtractTextPlugin.extract("style-loader", "css-loader")
            },
        ],
        postLoaders: [
            {
                test: /\.js$/,
                loaders: ['es3ify-loader'],
            },
        ],
    },
};
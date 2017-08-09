var webpack = require('webpack');

module.exports = {
    entry: './src/main/resources/static/main.js',
    output: {
        path: './src/main/resources/static/build',
        filename: './bundle.js'
    },
    debug: true,
    devtool: 'source-map',
    plugins:[
        new webpack.ProvidePlugin({
            $: "jquery",
            jQuery: "jquery",
            "window.jQuery": "jquery"
        })
    ],
    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loaders: ['babel-loader'],
            },
            {
                test: /\.less/,
                loader: 'style-loader!css-loader!less-loader'
            }, {
                test: /\.(css)$/,
                loader: 'style-loader!css-loader'
            }
        ],
        postLoaders: [
            {
                test: /\.js$/,
                loaders: ['es3ify-loader'],
            },
        ],
    },
};
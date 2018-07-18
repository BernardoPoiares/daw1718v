
var path = require('path')

module.exports = {
  entry: './js/index.js',
   output: { // Compile into dist/build.js
      path: path.resolve(__dirname, 'build'),
      filename: './dist/bundle.js',
	  publicPath:'/'
    },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /(node_modules)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['env', 'react']
          }
		}
	  }
      ,
	  { test: /\.css$/, use:[ 'style-loader', 'css-loader'] }
	]
  },
  devServer: {    
  historyApiFallback: true,
    port: 9000
  }
}
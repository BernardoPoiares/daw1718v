
var path = require('path')

module.exports = {
  entry: './js/index.js',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist')
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
    port: 8080
  }
}

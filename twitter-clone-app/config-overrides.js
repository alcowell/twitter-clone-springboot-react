const { override, addLessLoader, fixBabelImports } = require('customize-cra');

module.exports = override(
  fixBabelImports('import', {
    libraryName: 'antd',
    libraryDirectory: 'es',
    style: true,
  }),
  addLessLoader({
    modifyVar: {
      '@layout-body-background': '#FFFFFF',
      '@layout-header-background': '#ffffff',
      '@layout-footer-background': '#ffffff',
    },
    javascriptEnabled: true,
  })
);

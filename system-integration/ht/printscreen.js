/**
 * How to call this script on command line:
 *
 * $ phantomjs printscreen.js
 *
 * alias chrome="/Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome"
 * chrome --headless --disable-gpu --screenshot --window-size=1440,900 http://denner.localhost:8080/
 *
 */
var page = require('webpage').create();
page.viewportSize = {
    // http://phantomjs.org/api/webpage/property/viewport-size.html
    width: 1440,
    height: 900
};
page.open('http://denner.localhost:8080/', function () {
    page.render('printscreen.png');
    phantom.exit();
});

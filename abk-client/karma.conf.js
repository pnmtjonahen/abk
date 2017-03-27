
module.exports = function (config) {
   "use strict";

   config.set({
      // enable / disable watching file and executing tests whenever any file changes
      autoWatch: true,
      // base path, that will be used to resolve files and exclude
      basePath: "app",
      // testing framework to use (jasmine/mocha/qunit/...)
      frameworks: ["jasmine"],
      // list of files / patterns to load in the browser
      files: [
         "bower_components/jquery/dist/jquery.js",
         "bower_components/angular/angular.js",
         "bower_components/bootstrap/dist/js/bootstrap.js",
         "bower_components/angular-cookies/angular-cookies.js",
         "bower_components/angular-sanitize/angular-sanitize.js",
         "bower_components/angular-animate/angular-animate.js",
         "bower_components/angular-touch/angular-touch.js",
         "bower_components/d3/d3.js",
         "bower_components/angular-resource/angular-resource.js",
         "bower_components/angular-bindonce/bindonce.js",
         "bower_components/angular-file-upload/dist/angular-file-upload.min.js",
         "bower_components/ngDialog/js/ngDialog.js",
         "bower_components/angular-ui-router/release/angular-ui-router.js",
         "bower_components/angular-mocks/angular-mocks.js",
         "views/*.html",
         "env.js",
         "scripts/**/*.js",
         "scripts/**/*.spec.js"

      ],
      // list of files / patterns to exclude
      exclude: [],
      // web server port
      port: 8080,
      // Start these browsers, currently available:
      // - Chrome
      // - ChromeCanary
      // - Firefox
      // - Opera
      // - Safari (only Mac)
      // - PhantomJS
      // - IE (only Windows)
      browsers: [
         "PhantomJS"
      ],
      // Which plugins to enable
      plugins: [
         "karma-phantomjs-launcher",
         "karma-jasmine",
         "karma-coverage",
         "karma-junit-reporter",
         "karma-ng-html2js-preprocessor"
      ],
      // Continuous Integration mode
      // if true, it capture browsers, run tests and exit
      singleRun: false,
      colors: true,
      // level of logging
      // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
      logLevel: config.LOG_INFO,
      // Uncomment the following lines if you are using grunt"s server to run the tests
      // proxies: {
      //   "/": "http://localhost:9000/"
      // },
      // URL root prevent conflicts with the site root
      // urlRoot: "_karma_"

      // coverage reporter generates the coverage
      reporters: ["progress", "junit", "coverage"],
      preprocessors: {
         // source files, that you wanna generate coverage for
         // do not include tests or libraries
         // (these files will be instrumented by Istanbul)
         "js/**/*.js": ["coverage"],
         "partials/**/*.html": "ng-html2js"
      },
      junitReporter: {
         outputFile: "TESTS-xunit.xml",
         outputDir: "../reports/junit"
      },
      // optionally, configure the reporter
      coverageReporter: {
         reporters: [
            {
               type: "html",
               dir: "../coverage"
            },
            {
               type: "lcovonly",
               dir: "../reports",
               subdir: "coverage"
            }
         ]
      }
   });
};

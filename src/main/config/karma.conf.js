module.exports = function(config) {
    config.set({
        basePath: '../',
        files: [
            'webapp/vendor/jquery/jquery-2.0.3.js',
            'webapp/vendor/jquery-ui-1.10.3/ui/jquery-ui.js',
            'webapp/vendor/bootstrap/js/bootstrap.js',
            'webapp/vendor/angular/angular.js',
            'test/lib/angular/angular-mocks.js',
            'webapp/vendor/angular/angular-*.js',
            'webapp/vendor/jquery/jquery.fileupload.js',
            'webapp/js/**/*.js',
            'test/unit/**/*.js'
        ],
        exclude: [
            'webapp/vendor/angular/angular-loader.js',
            'webapp/vendor/angular/angular-scenario.js',
            'webapp/vendor/angular/angular-animate.js'
        ],
        autoWatch: true,
        frameworks: ['jasmine'],
        browsers: ['Chrome'],
        plugins: [
            'karma-junit-reporter',
            'karma-coverage',
            'karma-chrome-launcher',
            'karma-firefox-launcher',
            'karma-phantomjs-launcher',
            'karma-script-launcher',
            'karma-jasmine'
        ],
        preprocessors: {
            '**/app/lib/js/**/*.js': 'coverage'
        },
        reporters: ['progress','coverage', 'junit'], /* add progess reporter else nothing is reported on commandline karma. */
        junitReporter: {
            outputFile: 'test_out/unit.xml'
        },
        coverageReporter: {
            type: 'html',
            dir: 'test_out/js-coverage/'
        }
    }
    );
};

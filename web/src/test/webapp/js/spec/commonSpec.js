describe('Class Common', function () {
    'use strict';
    it('function isNotEmptyValue', function () {
        expect(common.isNotEmptyValue('')).toBeFalsy();
        expect(common.isNotEmptyValue(null)).toBeFalsy();
        expect(common.isNotEmptyValue(undefined)).toBeFalsy();
        expect(common.isNotEmptyValue("test")).toBeTruthy();
    });
    describe('function isValidDouble', function () {
        it('function isValidDouble', function () {
            expect(common.isValidDouble("2.0")).toBeTruthy();
            expect(common.isValidDouble("200")).toBeTruthy();
            expect(common.isValidDouble("200.123")).toBeTruthy();
            expect(common.isValidDouble("200.123a")).toBeFalsy();
        });
        it('null or empty value', function () {
            expect(common.isValidDouble('')).toBeFalsy();
            expect(common.isValidDouble(undefined)).toBeFalsy();
            expect(common.isValidDouble(null)).toBeFalsy();
        });
    });
    describe('function isValidDate', function () {
        it('non empty values', function () {
            expect(common.isValidDate("20-Apr-2000")).toBeTruthy();
            expect(common.isValidDate("20-Dec-2000")).toBeTruthy();
            expect(common.isValidDate("1-Dec-2000")).toBeTruthy();
            expect(common.isValidDate("1a-Dec-2000")).toBeFalsy();
        });
        it('null or empty value', function () {
            expect(common.isValidDate(null)).toBeTruthy();
            expect(common.isValidDate("")).toBeTruthy();
            expect(common.isValidDate(undefined)).toBeTruthy();
        })
    });
    describe('function successHandler', function () {
        it('empty handlers', function () {
            var jqXHR = {
                responseText: '{ "viewName": "view" }'
            };
            var context = {
                viewName: 'view'
            };
            spyOn(common, "loadTemplate");
            common.successHandler(null, null, jqXHR, null, null, 'element');
            expect(common.loadTemplate).toHaveBeenCalledWith(context.viewName, context, 'element');
        });
        it('nonEmpty initialization', function () {
            var jqXHR = {
                responseText: '{ "viewName": "view" }'
            };
            var context = {
                viewName: 'view'
            };
            var preInitialization = jasmine.createSpy("preInitialization");
            var handleAfterInitialization = jasmine.createSpy("handleAfterInitialization");
            spyOn(common, "loadTemplate");
            common.successHandler(null, null, jqXHR, handleAfterInitialization, preInitialization, 'element');
            expect(common.loadTemplate).toHaveBeenCalledWith(context.viewName, context, 'element');
            expect(preInitialization.calls.any()).toBeTruthy();
            expect(handleAfterInitialization.calls.any()).toBeTruthy();
        });
    });
    describe('function loadHandleBarPage', function () {
        var request;
        var RESPONSE = '{"viewName" : "view"}';
        var testResponse = {
            success: {
                status: 200,
                responseText: RESPONSE
            },
            error: {
                status: 404,
                responseText: "Not Found"
            }
        };
        beforeEach(function () {
            jasmine.Ajax.install();
        });
        afterEach(function () {
            jasmine.Ajax.uninstall();
        });
        it('successful call', function () {
            spyOn(common,'successHandler');
            common.loadHandleBarPage('/login', 'element', null, null);
            request = jasmine.Ajax.requests.mostRecent();
            request.respondWith(testResponse.success);
            expect(common.successHandler).toHaveBeenCalled();
            expect(request.url).toContain("/login");
            expect(request.method).toEqual("GET");
            expect(request.data()).toEqual({});
        });
        it('error call', function() {
            spyOn(common, 'errorHandler');
            common.loadHandleBarPage('/login', 'element', null, null);
            request = jasmine.Ajax.requests.mostRecent();
            request.respondWith(testResponse.error);
            expect(common.errorHandler).toHaveBeenCalled();
            expect(request.url).toContain("/login");
            expect(request.method).toEqual("GET");
            expect(request.data()).toEqual({});
        });
    });
    it('function errorHandler', function() {
        var element = $('<div id="element"></div>');
        $(document.body).append(element);
        var xhr = {
            statusText: "Failed to open file"
        };
        common.errorHandler(xhr, 'element');
        expect(element.html()).toEqual('The application has encountered an error. If this problem persists please contact the Administrator. Failed to open file');
        element.remove();
    })

});
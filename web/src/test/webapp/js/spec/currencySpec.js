describe('Class Currency', function () {
    it('function loadForm', function () {
        spyOn(common, 'loadHandleBarPage');
        currency.loadForm();
        expect(common.loadHandleBarPage).toHaveBeenCalledWith("currency.html", "formContainer", currency.handleAfterInitialization, null);
    });
    it('function loadHistory', function() {
       spyOn(common, 'loadHandleBarPage');
       currency.loadHistory();
       expect(common.loadHandleBarPage).toHaveBeenCalledWith("history.html", "historyContainer", null, null);
    });
    it('function handleAfterInitialization', function() {
       spyOn(currency, 'setupAjaxForm');
       spyOn(common, 'dateFieldPicker');
       currency.handleAfterInitialization();
       expect(currency.setupAjaxForm).toHaveBeenCalled();
       expect(common.dateFieldPicker).toHaveBeenCalledWith('asOfdate');
    });
    describe('function showProgress', function() {
        var resultProgress;
        var submitContainer;
        var container;
        beforeEach(function() {
            container = $('<div></div>');
            resultProgress = $('<div id="resultProgress" style="display:none"></div>');
            submitContainer = $('<div id="submitContainer"></div>');
            $(container).append(resultProgress);
            $(container).append(submitContainer);
            $(document.body).append(container);
        });
        afterEach(function() {
            container.remove();
        });
        it('enable', function() {
            currency.showProgress(true);
            expect(submitContainer).not.toBeVisible();
            expect(resultProgress).toBeVisible();
        });
        it('disable', function() {
            currency.showProgress(false);
            expect(submitContainer).toBeVisible();
            expect(resultProgress).not.toBeVisible();
        });
    });
});
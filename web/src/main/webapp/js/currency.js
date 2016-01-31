var currency = new Currency();

function Currency() {

    this.initialize = function() {
        common.loadHandleBarPage("currency.html", "formContainer", currency.handleAfterInitialization, null);
        currency.loadHistory();
    };

    this.loadHistory = function() {
        common.loadHandleBarPage("history.html", "historyContainer", null, null);
    };

    this.handleAfterInitialization = function(context) {
        currency.setupAjaxForm();
    };

    this.showProgress = function(enable) {
        if(enable) {
            $('#resultProgress').show();
            $('#submitContainer').hide();
        } else {
            $('#resultProgress').hide();
            $('#submitContainer').show();
        }
    };

    this.handleBeforeSubmit = function() {
        currency.showProgress(true);
        return true;
    };

    this.handleError = function(xhr, status, error, $form) {
        currency.showProgress(false);
        return false;
    };

    this.handleSuccess = function (response, textStatus, jqXHR) {
        currency.showProgress(false);
        var context = JSON.parse(jqXHR.responseText);
        if(context.success === true) {
            common.loadTemplate("currencyResult", context, "resultContainer");
            currency.loadHistory();
        } else {
            $('#error').html(context.error);
        }
        return true;
    };

    this.setupAjaxForm = function () {
        jQuery.support.cors = true;
        $('#convertCurrencyForm').ajaxForm({
            dataType: 'json',
            beforeSubmit: currency.handleBeforeSubmit,
            error: currency.handleError,
            success: currency.handleSuccess
        });
    };

}
var currency = new Currency();

function Currency() {

    this.initialize = function() {
        common.loadHandleBarPage("/currency.html", "")
    };

    this.showProgress = function(enable) {
        if(enable) {
            $('#resultProgress').show();
        } else {
            $('#resultProgress').hide();
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
        if(context.status === true) {
            common.loadTemplate("currencyResult", context, result);
        } else {
            $('#result').html(context.error);
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
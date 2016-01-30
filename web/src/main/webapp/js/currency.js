var currency = new Currency();

function Currency() {

    this.convert = function() {
        var fromCurrency = $('#fromCurrency').val();
        var toCurrency = $('#toCurrency').val();
        var amount = $('#amount').val();
    };

    this.showProgress = function(enable) {

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
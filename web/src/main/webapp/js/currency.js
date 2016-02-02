var currency = new Currency();

function Currency() {

    this.initialize = function() {
        currency.loadForm();
        currency.loadHistory();
    };

    this.validateInputs = function() {
        var status = true;
        var currencyFrom = $('#from').val();
        var currencyTo = $('#to').val();
        var asOfDate = $('#asOfdate').val();
        var errorMsg = '';
        if(currencyFrom === currencyTo) {
            status = false;
            errorMsg = 'Please select different from and to currencies.<br/>';
        }
        if(!common.isValidDouble($('#amount').val())) {
            status = false;
            errorMsg += 'Please provide a numeric amount.<br/>';
        }
        if(asOfDate != '' && !common.isValidDate(asOfDate)) {
            status = false;
            errorMsg += 'Please provide date in valid format dd-MMM-yyyy';
        }
        if(status === false) {
            $('#error').html(errorMsg);
        }
        return status;
    };

    this.loadForm = function() {
        common.loadHandleBarPage("currency.html", "formContainer", currency.handleAfterInitialization, null);
    };

    this.loadHistory = function() {
        common.loadHandleBarPage("history.html", "historyContainer", null, null);
    };

    this.handleAfterInitialization = function(context) {
        currency.setupAjaxForm();
        common.dateFieldPicker('asOfdate');
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
        $('#error').html("");
        if(!currency.validateInputs()) {
            return false;
        }
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
        if(context.success === "true") {
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
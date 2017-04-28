var common = {};

common.isValidDouble = function (value) {
    if (value === '' || value === undefined) {
        return false;
    }
    return value.match(/^-?\d*(\.\d+)?$/);
};

common.isValidDate = function (value) {
    if (value === null || value === '') {
        return true;
    }
    return value.toLowerCase().match(/^(\d{1,2})(\/|-)(?:(\d{1,2})|(jan)|(feb)|(mar)|(apr)|(may)|(jun)|(jul)|(aug)|(sep)|(oct)|(nov)|(dec))(\/|-)(\d{4})$/i);
};

common.loadTemplate = function (templateName, context, element) {
    var htmlContents = Handlebars.templates[templateName](context);
    $('#' + element).html(htmlContents);
};

common.successHandler = function (url, textStatus, jqXHR, handleAfterInitialization, handlePreInitialization, element) {
    var context = JSON.parse(jqXHR.responseText);
    if (handlePreInitialization !== null) {
        handlePreInitialization(context);
    }
    common.loadTemplate(context.viewName, context, element);
    if (handleAfterInitialization !== null) {
        handleAfterInitialization(context);
    }
};

common.errorHandler = function (xhr, element) {
    var msg = "The application has encountered an error. If this problem persists please contact the Administrator";
    $('#' + element).html(msg + xhr.statusText);
    return false;
};

common.loadHandleBarPage = function (url, element, handleAfterInitialization, handlePreInitialization) {
    jQuery.support.cors = true;
    $.ajax(
        {
            cache: false,
            url: url,
            type: 'GET',
            dataType: 'json',
            error: function (xhr) {
                common.errorHandler(xhr, element);
            },
            success: function (response, textStatus, jqXHR) {
                common.successHandler(response, textStatus, jqXHR, handleAfterInitialization, handlePreInitialization, element);
            }
        });

};

common.dateFieldPicker = function (elementId) {
    $("#" + elementId).datepicker({
        autoSize: true,
        dateFormat: 'd-M-yy',
        showOn: "button",
        buttonImage: "/images/calendar.gif",
        buttonImageOnly: true
    });
};

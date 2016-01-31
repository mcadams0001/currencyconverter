var common = new Common();

function Common() {
    this.loadTemplate = function (templateName, context, element) {
        var htmlContents = Handlebars.templates[templateName](context, null);
        $('#' + element).html(htmlContents);
    };

    this.loadHandleBarPage = function (url, element, handleAfterInitialization, handlePreInitialization) {
        jQuery.support.cors = true;
        $.ajax(
            {
                cache: false,
                url: url,
                type: 'GET',
                dataType: 'json',
                error: function (xhr) {
                    var msg = "The application has encountered an error. If this problem persists please contact the Administrator";
                    $('#' + element).html(msg + xhr.statusText);
                    return false;
                },
                success: function (response, textStatus, jqXHR) {
                    var context = JSON.parse(jqXHR.responseText);
                    if (handlePreInitialization != null) {
                        handlePreInitialization(context);
                    }
                    var html = Handlebars.templates[context.viewName](context, null);
                    $('#' + element).html(html);
                    if (handleAfterInitialization != null) {
                        handleAfterInitialization(context);
                    }
                }
            });

    };

}
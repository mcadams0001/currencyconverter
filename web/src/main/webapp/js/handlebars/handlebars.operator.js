Handlebars.registerHelper('eq', function (a, b) {
    return (a === b) ? arguments[arguments.length - 1].fn() : "";
});

Handlebars.registerHelper('ge', function (a, b) {
    return (a >= b) ? arguments[arguments.length - 1].fn() : "";
});

Handlebars.registerHelper('lt', function (a, b) {
    return (a < b) ? arguments[arguments.length - 1].fn() : "";
});

Handlebars.registerHelper('ne', function (a, b) {
    return (a !== b) ? arguments[arguments.length - 1].fn(this) : "";
});

Handlebars.registerHelper('select', function (value, options) {
    var element = jqApp('<select />').html(options.fn(this));
    element.find('[value=' + value + ']').attr({'selected': 'selected'});
    return element.html();
});

Handlebars.registerHelper('double', function (context, options) {
    var fixed = options.hash.fixed || 2;
    return  parseFloat(context).toFixed(fixed);
});

Handlebars.registerHelper('exrate', function (context, options) {
    var fixed = options.hash.fixed || 6;
    return  parseFloat(context).toFixed(fixed);
});
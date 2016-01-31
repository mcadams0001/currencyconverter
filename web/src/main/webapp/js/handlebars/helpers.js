(function () {
// Source: E:\currency\web\src\main\templates\currencyForm.hbs

  var template = Handlebars.template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1;

  return "<form id=\"convertCurrencyForm\" action=\"/convertCurrency.html\" method=\"post\">\r\n    <input id=\"amount\" name=\"amount\" value=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.command : depth0)) != null ? stack1.amount : stack1), depth0))
    + "\" class=\"form-control\"/>\r\n    <select id=\"from\" name=\"from\" class=\"form-control\">\r\n"
    + ((stack1 = helpers.each.call(depth0 != null ? depth0 : {},(depth0 != null ? depth0.currencies : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    </select>\r\n    <select id=\"to\" name=\"to\" class=\"form-control\">\r\n"
    + ((stack1 = helpers.each.call(depth0 != null ? depth0 : {},(depth0 != null ? depth0.currencies : depth0),{"name":"each","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "    </select>\r\n    <input type=\"hidden\" name=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0._csrf : depth0)) != null ? stack1.parameterName : stack1), depth0))
    + "\" value=\""
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0._csrf : depth0)) != null ? stack1.token : stack1), depth0))
    + "\"/>\r\n</form>";
},"1":function(container,depth0,helpers,partials,data) {
    var helper;

  return "        <option value=\""
    + container.escapeExpression(((helper = (helper = helpers.code || (depth0 != null ? depth0.code : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"code","hash":{},"data":data}) : helper)))
    + "\">"
    + container.escapeExpression(((helper = (helper = helpers.name || (depth0 != null ? depth0.name : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"name","hash":{},"data":data}) : helper)))
    + "</option>\r\n";
},"useData":true});
  var templates = Handlebars.templates = Handlebars.templates || {};
  templates['currencyForm'] = template;
  var partials = Handlebars.partials = Handlebars.partials || {};
  partials['currencyForm'] = template;


// Source: E:\currency\web\src\main\templates\currencyResult.hbs

  var template = Handlebars.template({"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper;

  return container.escapeExpression(((helper = (helper = helpers.amount || (depth0 != null ? depth0.amount : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"amount","hash":{},"data":data}) : helper)))
    + " "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.currencyFrom : depth0)) != null ? stack1.code : stack1), depth0))
    + " = "
    + container.escapeExpression(((helper = (helper = helpers.result || (depth0 != null ? depth0.result : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"result","hash":{},"data":data}) : helper)))
    + " "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.currencyTo : depth0)) != null ? stack1.code : stack1), depth0))
    + "\r\n(Current exchange rate 1 "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.currencyFrom : depth0)) != null ? stack1.code : stack1), depth0))
    + " = "
    + container.escapeExpression(((helper = (helper = helpers.quote || (depth0 != null ? depth0.quote : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"quote","hash":{},"data":data}) : helper)))
    + " "
    + container.escapeExpression(container.lambda(((stack1 = (depth0 != null ? depth0.currencyTo : depth0)) != null ? stack1.code : stack1), depth0))
    + " as of "
    + container.escapeExpression(((helper = (helper = helpers.timestamp || (depth0 != null ? depth0.timestamp : depth0)) != null ? helper : helpers.helperMissing),(typeof helper === "function" ? helper.call(depth0 != null ? depth0 : {},{"name":"timestamp","hash":{},"data":data}) : helper)))
    + ")";
},"useData":true});
  var templates = Handlebars.templates = Handlebars.templates || {};
  templates['currencyResult'] = template;
  var partials = Handlebars.partials = Handlebars.partials || {};
  partials['currencyResult'] = template;



})();
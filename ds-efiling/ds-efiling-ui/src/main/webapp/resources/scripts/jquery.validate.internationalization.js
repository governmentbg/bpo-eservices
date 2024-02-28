$.validator.prototype.defaultMessage = function(element, method) {   
    var message = $("#error_jquery_validation_" + method).html();
    
    return this.findDefined(
            this.customMessage( element.name, method ),
            this.customMetaMessage( element, method ),
            // title is never undefined, so handle empty string as undefined
            !this.settings.ignoreTitle && element.title || undefined,
            message,
            "<strong>Warning: No message defined for " + element.name + "</strong>"
        );
};
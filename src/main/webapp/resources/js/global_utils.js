jQuery.fn.tag = function() {
    var tag = this.prop('tagName');

    if (tag) {
        return tag.toLowerCase();
    }

    return tag;
};

jQuery.fn.type = function() {
    var type = this.attr('type');

    if (type) {
        return type.toLowerCase();
    }

    return type;
};

jQuery.fn.name = function() {
    return this.attr('name');
};

jQuery.fn.subm = function(callback){
    callback = callback && typeof callback == 'function' ? callback : function(){};

    $(this).on('submit', function(e){
        e.preventDefault();

        callback($(this));
    });

    return this;
};

function showErrorMessage(error) {
    if (typeof error == 'string') {
        alertify.error(error);
    } else {
        alertify.error(error.message);
    }
}

function showSuccessMessage(message) {
    alertify.success(message);
}

function buildValidationErrors(errors) {
    var message = '';
    for (var key in errors) {
        if (errors.hasOwnProperty(key)) {
            var val = errors[key];
            for (var i = 0; i < val.length; ++i) {
                message += val[i] + '<br />';
            }
        }
    }

    return message;
}
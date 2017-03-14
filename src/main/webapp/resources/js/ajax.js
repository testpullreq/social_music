(function(exports){

    exports.get = function(options) {
        $.ajax({
            url: options.url,
            type: 'GET',
            success: options.success,
            error: options.error
        });
    };

    exports.delete = function(options) {
        $.ajax({
            url: options.url,
            type: 'DELETE',
            success: options.success,
            error: options.error
        });
    };

    exports.put = function(options) {
        putAndPost(options, 'PUT');
    };

    exports.post = function(options) {
        putAndPost(options, 'POST');
    };

    exports.method = function(condition) {
        return condition ? exports.post : exports.put;
    };

    function putAndPost(options, type) {
        $.ajax({
            url: options.url,
            type: type,
            data: JSON.stringify(options.data),
            dataType: 'json',
            beforeSend: function(xhr){
                xhr.setRequestHeader('Accept', 'application/json');
                xhr.setRequestHeader('Content-Type', 'application/json');
            },
            success: options.success,
            error: options.error
        });
    }

})(window.Ajax = {});
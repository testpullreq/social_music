(function(exports){

    exports.get = function($form) {
        var data = {};
        $form.find('input, select, textarea').each(function(i, input){
            input = $(input);

            var type = input.type();
            var tag = input.tag();

            if (tag == 'input') {
                if (type == 'checkbox' || type == 'radio') {
                    if (input.is(':checked')) {
                        data[input.name()] = input.val();
                    }
                } else {
                    data[input.name()] = input.val();
                }
            }
        });

        return data;
    }

})(window.FormDataUtil = {});
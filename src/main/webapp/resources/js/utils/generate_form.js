(function(exports){

    var ELEMENT_TEMPLATES = {
        select: new EJS({url: '/resources/template/generate_form/select_element.ejs'}),
        text: new EJS({url: '/resources/template/generate_form/text_element.ejs'}),
        checkbox: new EJS({url: '/resources/template/generate_form/checkbox_element.ejs'}),
        radio: new EJS({url: '/resources/template/generate_form/radio_element.ejs'}),
        textarea: new EJS({url: '/resources/template/generate_form/textarea_element.ejs'})
    };

    exports.generate = function(json_template, html_template, selector_form, selector_template) {
        selector_form = $(selector_form);
        selector_form.html('');

        json_template.forEach(function(e){
            var template = ELEMENT_TEMPLATES[e.element];

            if (template) {
                var html = $(template.render(e));
                bindFormToTemplate(html);
                selector_form.append(html);
            }
        });

        $(selector_template).html('').append(replaceValuesInTemplate(html_template));
    };

    function replaceValuesInTemplate(html_template) {
        if (html_template) {
            var m;
            while (m = REGEX_ELEMENT.exec(html_template)) {
                html_template = html_template.replace(m[0], '<span data-bind="' + m[1] + '"></span>');
            }
        }

        return html_template;
    }

    function bindFormToTemplate(formElement) {
        formElement.find('[name]').on('change keyup keydown click', function(){
            var $self = $(this),
                name = $self.attr('name'),
                $bindTo = $('[data-bind=' + name + ']');

            if ($self.type() == 'checkbox') {
                var $checked = $('[name=' + name + ']:checked');
                var result = '';

                $checked.each(function (i, input) {
                    input = $(input);

                    result += input.val() + ', ';
                });

                var index = result.lastIndexOf(',');
                if (index > -1) {
                    result = result.substr(0, index);
                }

                $bindTo.text(result);
            } else if ($self.tag() == 'textarea') {
                var val = $self.val();
                val = val.replace(/\n/g, '<br />');
                $bindTo.html(val);
            } else {
                $bindTo.text($self.val());
            }
        });
    }

    const REGEX_ELEMENT = /{{([a-zA-Z\-_0-9]+)}}/g

})(window.GenerateFormUtil = {});
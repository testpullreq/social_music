(function(){

    var template = new EJS({url: '/resources/template/common/sign_in.ejs'});

    $(function(){
        $('#sign_in').click(function(){
            var $html = $(template.render({}));

            $html.find('form').submit(function(e){
                e.preventDefault();

                var formData = FormDataUtil.get($(this));
                formData.password = md5(formData.password);

                $.ajax({
                    url: '/api/users/sign_in',
                    type: 'POST',
                    data: JSON.stringify(formData),
                    dataType: 'json',
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader('Content-type', 'application/json');
                        xhr.setRequestHeader('Accept', 'application/json');
                    },
                    success: function(response){
                       if (response.result) {
                           location.reload(true);
                       } else {
                           showErrorMessage(response.error);
                       }
                    }
                })
            });

            $html.find('.close').click(function(){
                $html.remove();
            });

            $('body').append($html);
        });

        $('#logout').click(function(){
            $.ajax({
                url: '/api/users/logout',
                type: 'POST',
                success: function(){
                    location.reload(true);
                }
            })
        });
    });

})();
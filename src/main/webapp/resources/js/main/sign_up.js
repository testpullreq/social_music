$(function(){
    $('#registration_form').subm(function($form){
        var form_data = FormDataUtil.get($form);
        if (form_data.email && form_data.password) {
            form_data.password = md5(form_data.password);

            Ajax.put({
                url: '/api/users/',
                data: form_data,
                success: function(response) {
                    if (response.error) {
                        showErrorMessage('error in console');
                        console.log(response.error);
                    } else {
                        if (response.result) {
                            showErrorMessage('registered');
                            location.href = '/';
                        } else {
                            showErrorMessage('problems with service');
                        }
                    }
                }
            })
        } else {
            showErrorMessage('email and password are required');
        }
    });
});
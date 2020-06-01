$('.ui.form').form({
    fields:{
        username:{
            identifier:'username',
            rules:[{
                type:'empty',
                prompt:'请输入用户名'
            }]
        },
        password:{
            identifier:'password',
            rules:[{
                type:'empty',
                prompt:'请输入密码'
            }]
        }

    }
});


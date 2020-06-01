$('.ui.form').form({
    fields:{
        name:{
            identifier:'name',
            rules:[{
                type:'empty',
                prompt:'请输入分类名称'
            }]
        }
    }
});
$('.message .close')
    .on('click', function() {
        $(this)
            .closest('.message')
            .transition('fade');
    });


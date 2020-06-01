
// 表单验证
$('.ui.form').form({
    fields:{
        title:{
            identifier:'title',
            rules:[{
                type:'empty',
                prompt:'请输入博客标题'
            }]
        },
        content:{
            identifier:'content',
            rules:[{
                type:'empty',
                prompt:'请输入博客内容'
            }]
        },
        typeId:{
            identifier:'typeId',
            rules:[{
                type:'empty',
                prompt:'请输入博客分类'
            }]
        },
        firstPicture:{
            identifier:'firstPicture',
            rules:[{
                type:'empty',
                prompt:'请输首图链接地址'
            }]
        },
        description:{
            identifier:'description',
            rules:[{
                type:'empty',
                prompt:'请输入博客博客描述'
            }]
        }
    }
});

// 关闭提示信息
$('.message .close')
    .on('click', function() {
        $(this)
            .closest('.message')
            .transition('fade');
    });
//初始化Markdown编辑器
var contentEditor;
$(function() {
    contentEditor = editormd("md_content", {
        width   : "100%",
        height  : 640,
        syncScrolling : "single",
       /* path    : "../../static/lib/editor_md/lib/"*/
       path     : "/lib/editor_md/lib/"
    });
});
$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

$('.ui.dropdown').dropdown({
    on : 'hover'
});

// 点击保存按钮
$('#save_btn').click(function () {
    $('input[name="published"]').val(false);
    $('#blog_form').submit();
});

// 点击发布按钮
$('#published_btn').click(function () {
    $('input[name="published"]').val(true)
    $('#blog_form').submit();
});

$('.ui.tag.dropdown')
    .dropdown({
        allowAdditions:true
    });




$('.ui.dropdown').dropdown();

/*消息提示框关闭*/
$('.message .close')
    .on('click', function() {
        $(this)
            .closest('.message')
            .transition('fade')
        ;
    })
;

/*切换上一页*/
$('#previousPageBtn').on('click',function(){
    pageSwitch(this);
});
/*切换下一页*/
$('#nextPageBtn').on('click',function(){
    pageSwitch(this);
});
$('#search_btn').on('click',function(){
    loaddata();
})
function pageSwitch(dom) {
    $("input[name='page']").val($(dom).data('page'));
    loaddata();
}

function loaddata() {
    $('#blog_table').load("/admin/blogs/search",{
        title:$('input[name="title"]').val(),
        typeId : $('input[name="type"]').val(),
        recommend : $('input[name="recommend"]').prop('checked'),
        page : $('input[name="page"]').val()
    })
}

$('#payButton').popup({
    popup:$('.payQR.popup')
    ,on:'click'
    ,position:'bottom center'
});

tocbot.init({
    // Where to render the table of contents.
    tocSelector: '.js-toc',
    // Where to grab the headings to build the table of contents.
    contentSelector: '.js-toc-content',
    // Which headings to grab inside of the contentSelector element.
    headingSelector: 'h1, h2, h3',
});

$('#tocButton').popup({
    popup:$('.toc-container.popup')
    ,on:'click'
    ,position:'left center'
});

$('#wechat').popup({
    popup:$('.wechat-qr')
    ,position:'left center'
});

// 生成二维码
var url = window.document.location.href;
console.log(url);
var qrcode = new QRCode("qrcode", {
    text: url,
    width: 110,

    height: 110,
    colorDark : "#000000",
    colorLight : "#ffffff",
    correctLevel : QRCode.CorrectLevel.H
});

$('#toTop-button').click(function () {
    $(window).scrollTo(0,500);
});

var waypoint = new Waypoint({
    element: document.getElementById('waypoint'),
    handler: function(direction) {
        if (direction == 'down') {
            $('#toolbar').show(100);
        } else {
            $('#toolbar').hide(500);
        }
    }
});

/* 初始化留言数据 */
$(function () {
    var commentUrl = $('#commentUrl').val();
    $('#comment-container').load(commentUrl);
});

/* 表单验证 */
$('.ui.form')
    .form({
        fields: {
            content     : 'empty',
            nickname   : 'empty',
            email : 'email'
        }
    });

/* 提交留言 */
$('#comment_btn').click(function(){
    var flag = $('.ui.form').form('validate form'); /* 触发表单验证 */
    if (flag){
        postData();
    }else{
        console.log("信息验证未成功");
    }

});
/* 重新加载留言列表*/
function postData(){
    $('#comment-container').load("/comments",{
        'blog.id':$('[name="blog.id"]').val()
        , 'parentComment.id':$('[name="parentComment.id"]').val()
        , 'content':$('[name="content"]').val()
        , 'nickname':$('[name="nickname"]').val()
        , 'email':$('[name="email"]').val()
        }
        ,function(responseTxt,statusTxt,xhr){
           /* $(window).scrollTo($('#comment-container'),500);*/
            clearContent();
    })
}

/* 获取dom下面的所有输入域name与value */
function resFormData(dom){
    return new FormData(dom)
}

/* 评论提交后进行清空 */
function clearContent(){
    $("[name='content']").val('');
    $("[name='content']").attr("placeholder","请输入评论信息...").focus();
    $("[name='parentComment.id']").val(-1);
}

/*$('.reply').click(function(){
    var commentId = $(this).data("commentid");
    var commentNickname = $(this).data("commentnickname");
    $('[name="content"]').attr("placeholder","@"+commentNickname).focus();
    $("[name='parentComment.id']").val(commentId);
    $(window).scrollTo($('#comment_form'),500);
});*/

function reply(dom){
    var commentId = $(dom).data("commentid");
    var commentNickname = $(dom).data("commentnickname");
    $('[name="content"]').attr("placeholder","@"+commentNickname).focus();
    $("[name='parentComment.id']").val(commentId);
    $(window).scrollTo($('#comment_form'),500);
}



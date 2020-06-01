$('.menu.toggle').on('click',function(){
    /*$('.m-item').removeAttr('m-mobile-hide');*/
    $('.m-item').toggleClass('m-mobile-hide') /*新方法记得学习*/

});
$(function () {
    $("#newblog-container").load("/footer/newblog")
});

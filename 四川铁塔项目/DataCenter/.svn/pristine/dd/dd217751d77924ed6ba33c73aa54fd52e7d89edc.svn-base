$(document).ready(function() { //页面加载完后执行
    　　
    contentH(); //计算content高度
});
$(window).resize(function() { //当浏览器大小变化时
    contentH(); //计算content高度
});
/*自定义select*/
$(function() {
    $(".sel_wrap").on("change", function() {
        var o;
        var opt = $(this).find('option');
        opt.each(function(i) {
            if (opt[i].selected == true) {
                o = opt[i].innerHTML;
            }
        })
        $(this).find('label').html(o);
    }).trigger('change');
})
/* ============================================================
 * 计算content的高度。
 * ============================================================ */
var contentH = function() {
    var h = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
    $("#content").css({
        "height": h - 136
    });
    $('.sidermenu').css({
        "height": h - 136
    });
    $('.content-step').css({
        "height": h - 20
    });
    $('.menuh').css({
        "height": h
    });
};

/* ============================================================
 * tab点击激活
 * ============================================================ */
$(function() {
    $('.navsecond').children('li').click(function(event) {
        $(this).siblings('li').removeClass('active');
        $(this).addClass('active');
    });
    $('.nav-menu').children('li').click(function(event) {
        $(this).siblings('li').removeClass('active');
        $(this).addClass('active');
    });
})


$('#chart-tab a').click(function (e) {
  e.preventDefault();
  $(this).tab('show');
})

/* ============================================================
 * 列表页面左侧菜单动作
 * ============================================================ */
$(document).ready(function(){     
  $('.nav-list li a').click(function(){
      $(this).parent('li').siblings().removeClass('open');
      $(this).parent('li').siblings().children('.submenu').slideUp();
      $(this).siblings('.submenu').slideToggle();
      $(this).parent('li').toggleClass('open');
      $(this).siblings('.submenu').children('li').click(function(){
          $(this).siblings('li').removeClass('active');
          $(this).addClass('active');
          });
      })
}); 
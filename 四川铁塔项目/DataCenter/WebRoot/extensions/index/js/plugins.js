/* ============================================================
 * 登录页面焦点图切换。
 * ============================================================ */
(function($) {
    $.fn.extend({
        yx_rotaion: function(options) {
            //默认参数
            var defaults = {
                /**轮换间隔时间，单位毫秒*/
                during: 4000,
                /**是否显示左右按钮*/
                btn: false,
                /**是否显示焦点按钮*/
                focus: false,
                /**是否显示标题*/
                title: false,
                /**是否自动播放*/
                auto: true
            }
            var options = $.extend(defaults, options);
            return this.each(function() {
                var o = options;
                var curr_index = 0;
                var $this = $(this);
                var $li = $this.find("li");
                var li_count = $li.length;
                $this.css({
                    position: 'relative',
                    width: $li.find("img").width(),
                    height: $li.find("img").height()
                });
                $this.find("li").css({
                    position: 'absolute',
                    left: 0,
                    top: 0
                }).hide();
                $li.first().show();
                $this.append('<div class="yx-rotaion-btn"><span class="left_btn"><\/span><span class="right_btn"></span><\/div>');
                if (!o.btn) $(".yx-rotaion-btn").css({
                    visibility: 'hidden'
                });
                if (o.title) $this.append(' <div class="yx-rotation-title"><\/div><a href="" class="yx-rotation-t"><\/a>');
                if (o.focus) $this.append('<div class="yx-rotation-focus"><\/div>');
                var $btn = $(".yx-rotaion-btn span"),
                    $title = $(".yx-rotation-t"),
                    $title_bg = $(".yx-rotation-title"),
                    $focus = $(".yx-rotation-focus");
                //如果自动播放，设置定时器
                if (o.auto) var t = setInterval(function() {
                    $btn.last().click()
                }, o.during);
                $title.text($li.first().find("img").attr("alt"));
                $title.attr("href", $li.first().find("a").attr("href"));

                // 输出焦点按钮
                for (i = 1; i <= li_count; i++) {
                    $focus.append('<span>' + i + '</span>');
                }
                // 兼容IE6透明图片
                // if($.browser.msie && $.browser.version == "6.0" ){
                //    $btn.add($focus.children("span")).css({backgroundImage:'url(img/ico.gif)'});
                // }
                var $f = $focus.children("span");
                $f.first().addClass("hover");
                // 鼠标覆盖左右按钮设置透明度
                $btn.hover(function() {
                    $(this).addClass("hover");
                }, function() {
                    $(this).removeClass("hover");
                });
                //鼠标覆盖元素，清除计时器
                $btn.add($li).add($f).hover(function() {
                    if (t) clearInterval(t);
                }, function() {
                    if (o.auto) t = setInterval(function() {
                        $btn.last().click()
                    }, o.during);
                });
                //鼠标覆盖焦点按钮效果
                $f.bind("mouseover", function() {
                    var i = $(this).index();
                    $(this).addClass("hover");
                    $focus.children("span").not($(this)).removeClass("hover");
                    $li.eq(i).fadeIn(300);
                    $li.not($li.eq(i)).fadeOut(300);
                    $title.text($li.eq(i).find("img").attr("alt"));
                    curr_index = i;
                });
                //鼠标点击左右按钮效果
                $btn.bind("click", function() {
                    $(this).index() == 1 ? curr_index++ : curr_index--;
                    if (curr_index >= li_count) curr_index = 0;
                    if (curr_index < 0) curr_index = li_count - 1;
                    $li.eq(curr_index).fadeIn(300);
                    $li.not($li.eq(curr_index)).fadeOut(300);
                    $f.eq(curr_index).addClass("hover");
                    $f.not($f.eq(curr_index)).removeClass("hover");
                    $title.text($li.eq(curr_index).find("img").attr("alt"));
                    $title.attr("href", $li.eq(curr_index).find("a").attr("href"));
                });

            });
        }
    });

})(jQuery);
$(".yx-rotaion").yx_rotaion({
    auto: true
});



/* ============================================================
 * 导航条菜单点选效果
 * ============================================================ */
$('.navsecond > li').click(function() {
    $(this).addClass('active').siblings().removeClass('active');
});


/* ============================================================
 * 计算content的高度。
 * ============================================================ */
var contentH = function() {
    var h = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
    $("#content").css({
        "height": h - 84
    });
    $("#indexcontent").css({
        "height": h - 25
    });
};

/* ============================================================
 * iframe自动适应高度。
 * ============================================================ */
function iFrameHeight() {
    var ifm = document.getElementById("iframepage");
    if (document.frames["iframepage"].document || ifm.contentDocument) {
        var subWeb = document.frames ? document.frames["iframepage"].document : ifm.contentDocument;
    }
    if (ifm != null && subWeb != null) {
        ifm.height = subWeb.body.scrollHeight;
    }
}

/* ============================================================
 * tab标签切换
 * ============================================================ */
$('#myTab a').click(function(e) {
    e.preventDefault();
    $(this).tab('show');
})
$('#myTabone a').click(function(e) {
        e.preventDefault();
        $(this).tab('show');
    })
    /* ============================================================
     * 计算模块的高度。
     * ============================================================ */
function setboxHeight() {
        var h = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
        var viewHeight = h - 25;
        $('#js-box1-2').css({
            "height": viewHeight - $('#js-box1-1').outerHeight(true) - 33
        });
        $('#js-box3-2').css({
            "height": viewHeight - $('#js-box3-1').outerHeight(true) - 33
        });
        //报表统计部分高度
        $('#js-box2-1').css({
            "height": viewHeight - 2
        });
        $('#j-test-Result').css({
            "height": viewHeight - $('#js-top-box').outerHeight(true) - 2
        });

        /*$('#js-box2-3').css({"height":(viewHeight-$('#js-box2-1').outerHeight(true)-33)*0.4-20});
          $('#js-Repsta-data').css({"height":(viewHeight-$('#js-box2-1').outerHeight(true)-33)*0.4-61});
          $('#js-Repsta-list').css({"height":(viewHeight-$('#js-box2-1').outerHeight(true)-33)*0.4-20});
              //服务开通，资源配置，施工调度部分高度
          $('#js-box2-2').css({"height":(viewHeight-$('#js-box2-1').outerHeight(true)-33)*0.6-64});
          $('.js-box2-2-block').css({"height":(viewHeight-$('#js-box2-1').outerHeight(true)-33)*0.6-63});*/
    }
    /*var tablescroll=function(){
      document.getElementById("gvFinancial").parentNode.className = "fakeContainer"; //给table的父加上样式
      var screenw = (window.innerWidth || (window.document.documentElement.clientWidth || window.document.body.clientWidth));
      var screenh = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
      var tablew=screenw-570-20;
      var tableh=(screenh-$('#js-box2-1').outerHeight(true)-33)*0.6-78;
      $('.fakeContainer').css(
        {
        "width":tablew,
        "height":tableh
        }
        );
        //var start = new Date();
        superTable("gvFinancial", {
            headerRows: 1,
            fixedCols: 0, //固定列的数量
            onFinish: function() {
                //隐藏 等待  显示table完成事件
                try {
                    window.parent.hideLoading();
                } catch (err) {
                    //如果父中没有 隐藏等待图片的方法，不抛出错误
                }
            }
        });
    }*/
contentH();
$(document).ready(function() { //页面加载完后执行
    setboxHeight();
    /* ============================================================
     * 列表页面左侧菜单动作
     * ============================================================ */
    $('.nav-list  li  a').click(function() {
        /*$(this).parent('li').siblings().removeClass('open');*/
        /*$(this).parent('li').siblings().children('.submenu').slideUp();*/
        if ($(this).siblings('.submenu').is(":hidden")) {
            $(this).siblings('.submenu').slideDown();
            $(this).parent('li').addClass('open');
        } else {
            $(this).siblings('.submenu').slideUp();
            $(this).parent('li').removeClass('open');
        }

        /*$(this).siblings('.submenu').children('li').click(function(){
      $(this).siblings('li').removeClass('active');
      $(this).addClass('active');
      });*/
    })
    $('.js-title').click(function(event) {
        if ($(this).siblings('.js-con').is(':hidden')) {
            $(this).siblings('.js-con').slideDown();
            $(this).addClass('opened');
        } else {
            $(this).siblings('.js-con').slideUp();
            $(this).removeClass('opened')
        }
    });
});
$(window).resize(function() { //当浏览器大小变化时
    contentH();
    setboxHeight();
});

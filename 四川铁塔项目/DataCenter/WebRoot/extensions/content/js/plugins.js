/* ============================================================
 * 计算content的高度。
 * ============================================================ */
var contentH = function() {
    var h = (window.innerHeight || (window.document.documentElement.clientHeight || window.document.body.clientHeight));
    $(".L-main").css({
        "height": h
    });
    $(".L-logo").css({
        "height": h
    });
};
$(document).ready(contentH);
$(window).resize(contentH);
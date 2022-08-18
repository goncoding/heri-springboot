
var Media =(function(){

    function init(){
    	addEvents();
    }

    function addEvents(){
    	
    	$("#Gnb .gnb-dep01").children().eq(4).find("a").eq(0).addClass("current");
    	$("#Gnb .gnb-dep01").children().eq(4).find("div").find("li").eq(1).find("a").addClass("current");

    	var swiper_arr = new Array();
    	$('.swiper-container').each(function(idx) {
    		var swiper_obj = $(this);
    		var swiper_util = $(this).siblings(".swiper-util");
    		$(this).addClass("instance-" + idx);

    		swiper_arr[idx] = new Swiper(".instance-"+idx, {
    			pagination: {
    				el: swiper_util.find('.swiper-pagination'),
    				type: 'progressbar',
    			},
    			navigation: {
    				nextEl: swiper_util.find('.swiper-button-next'),
    				prevEl: swiper_util.find('.swiper-button-prev'),
    			},
    			on: {
    				slideChangeTransitionStart: function () {
    					setPage(swiper_util, swiper_arr[idx]);
    				}
    			}
    		});
    		swiper_util.find(".swiper-pager .total").text(swiper_obj.find(".swiper-slide").length);
    	});

    	function setPage(obj, swiper) {
    		obj.find(".swiper-pager .current").text(swiper.realIndex+1);
    	}
    }
    
    init();

})()

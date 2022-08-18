var Solutions =(function(){

    function init(){
    	addEvents();
    }

    function addEvents(){
		$(window).on("scroll", function() {
			var scroll = $(window).scrollTop();

			if(scroll>=$(document).height()-$(window).height()-$("#Footer").height()) {
				$(".intro-btm-slogan").addClass("end");
			}
		});

		//solutions: intro
		(function(window, $){
			var win = $(window);
		
			win.on('load', function(){
				eventBinding.bind();       
			})
		
			var eventBinding = {
				bind: function(){
					oneUseComponent = new OneUseComponent();
					oneUseComponent.init(true);
		
					scrollCtrCall();
				}
			}
			
			function scrollCtrCall(){
				if(!$('.article').length){
					return;
				}
				var scrollCtr = new ScrollMagic.Controller();
		
				$('.article').find('.tit-area, .txt-area').each(function(i, e) {
					var tl = new TimelineMax();
					tl.fromTo($(e),0.5, {opacity: 0, y: "+50px" },{ opacity: 1, y: "0px" })
		
					var scene = new ScrollMagic.Scene({
						triggerElement: e,
						offset: $(e).outerHeight()/2-50,
						triggerHook: 'onEnter',
					}).setTween(tl).addTo(scrollCtr);
				});
			}
		
			function OneUseComponent(){
				var _this = this;
		
				var publicFunctions = {
					init: _this.init = function(flag){
						if(!flag){
							return;                    
						}
						$('.solutions-intro-wrap').each(function(){
							_this.productNavi($(this));
						});
					},
		
					productNavi : _this.productNavi = function( t ){
						var windowH = window.innerHeight;
						var priTop = [];
						var moving = false;
						try{
							var controlloer = new ScrollMagic.Controller({globalSceneOptions:{triggerHook:0.5}});
						}catch(exception){
		
						}
		
						t.each(function(i, it){
							var $it = $(it);
							var introH =$it.outerHeight();
							var introScroll = $it.find('.intro-scroll');
							var introArticle = $it.find('.article');
							var scOffTop = introScroll.offset().top+(introScroll.outerHeight()/3);
							introArticle.each(function(){
								priTop.push($(this).offset().top);
							})
		
							introScroll.find('.item li').each(function(){
								$(this).on('click' , function(e){
									var i = $(this).index();
									moving = true;
									e.preventDefault();
									$('html, body').animate({scrollTop : priTop[i]-50}, 500).promise().then(function(){
										moving = false;
										introScroll.find('.item li').eq(i).addClass('on').siblings('li').removeClass('on');
									});
								});
							});
		
							introArticle.each(function(idx, itm){
								var ofs = 0;
								var dr = $(itm).outerHeight(true);
		
								if(itm===introArticle.first()[0]){
									ofs = -500;
									dr +=500;
								}
								if(itm===introArticle.last()[0]){
									dr +=500;
								}
		
								new ScrollMagic.Scene({triggerElement: itm, offset:ofs, duration:dr}).on('enter', function(){
									if(!moving){
										introScroll.find('.item li').eq(idx).addClass('on').siblings('li').removeClass('on');
									}
								}).addTo(controlloer);
							})
		
							win.scroll(function() {
								var scrollTop = win.scrollTop();
								
								if( scrollTop == 0){
									new TimelineMax().to(introScroll.find('.scroll .bar'),1,{css:{height:0+'%'}, ease: Expo.easeOut});
								}else{
									navibarHandler(introH,introScroll,scrollTop-$it.offset().top);
								}
								
								if(scrollTop+(windowH/2)>=scOffTop && scrollTop+(windowH/2)<introArticle.last().offset().top+introScroll.outerHeight()/2){
									if(!introScroll.hasClass('fixed')){
										introScroll.addClass('fixed');
										introScroll.css({'margin-top': ''});
									}
								}else{
									introScroll.removeClass('fixed');
								}
								if(scrollTop+(windowH/2)>=introArticle.last().offset().top+introScroll.outerHeight()/2){
									introScroll.removeClass('fixed');
									introScroll.css({
										'margin-top': introArticle.last().offset().top- introArticle.first().offset().top});
								}
							})
						})
		
						function navibarHandler(introH,scr,p){
							var summaryH = $(".sub-contents .summary-area").height();
							var per = ((p+windowH)/introH)*100;
		
							if(per>=100){
								per = 100;
							} else{
								per = per - 26;
							}
							new TimelineMax().to(scr.find('.scroll .bar'),1,{css:{height:per.toFixed(1)+'%'}, ease: Expo.easeOut});
						}
					}
				}
			}
		})(window, jQuery)
	}
    
    init();
})()

$(document).ready(function(e) {
	$('img[usemap]').rwdImageMaps();
});
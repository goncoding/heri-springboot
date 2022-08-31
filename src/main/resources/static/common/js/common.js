$(function(){


    /* ===== IE SUPPORT TAG ===== */
    document.createElement('header');
    document.createElement('footer');
    document.createElement('section');
    document.createElement('aside');
    document.createElement('nav');
    document.createElement('article');
    document.createElement('main');



    /* ===== BTN LANGUAGE ===== */
    $(".btn-hd-language").on('click',function(){
        $(this).parent().toggleClass("on");
    });

    $(document).click(function(e){
        if (!$(".btn-hd-language").is(e.target) && $(".btn-hd-language").has(e.target).length === 0){
            $(".btn-hd-language").parent().removeClass("on");
        }
    });



    /* ===== BTN GO TOP ===== */
    $(".topbtn").on("click", function() {
        $(window).scrollTop(0);
        if($('body').hasClass('index')){
            $('#header').removeAttr('class');
            $('body').removeClass('scr-down scr-up');
        }else{
            $('body').removeClass('scr-down scr-up');
        }
    });
    
    

    /* ===== SELECTBOX STYLE ===== */
    if($('select').length > 0){
        $('select').niceSelect();
    }



    /* ===== GSAP COMMON SETTING ===== */
    gsap.registerPlugin(ScrollToPlugin, ScrollTrigger);
    ScrollTrigger.config( {
        limitCallbacks:true
    });



    /*===== SMOOTH SCORLL FUNCTION ===== */
    function smoothScroll(){
        if(passiveChk()){
            window.addEventListener('wheel', smoothWheelFunc, {
                passive: false
            });
        }else{
            $(window).on('mousewheel DOMMouseScroll', smoothWheelFunc);
        }
    }smoothScroll()



    function smoothScrollStop(){
        if(passiveChk()){
            window.removeEventListener('wheel', smoothWheelFunc);
        }else{
            $(window).off('mousewheel DOMMouseScroll', smoothWheelFunc);
        }
    }



    /*===== PASSIVE OPT CHECK ===== */
    function passiveChk(){
        var passiveSupported = false;
        try {
            var options = {
                get passive() {
                    passiveSupported = true;
                }
            };
            window.addEventListener('test', null, options);
        } catch(err) {
            passiveSupported = false;
        }
        return passiveSupported;
    }



    /*===== SMOOTH SCROLL WHEEL FUNCTION ===== */
    // 동작참고: https://gist.github.com/muzafferkoluman/
    function smoothWheelFunc(event){
        event.preventDefault();

        var scrollTime = 1;
        var delta = 0;

        if(passiveChk()){
            var scrollDistance = $(window).height() / 2.25;
            delta = event.wheelDelta/120 || -event.originalEvent.detail/3;
        }else{
            var scrollDistance = $(window).height() / 2.25;
            if(typeof event.originalEvent.deltaY != 'undefined'){
                delta = -event.originalEvent.deltaY/120;
            }else{
                delta = event.originalEvent.wheelDelta/120 || -event.originalEvent.detail/3;
            }
        }

        var scrollTop = $(window).scrollTop();
        var finalScroll = scrollTop - parseInt(delta*scrollDistance);

        gsap.to(
            $(window), scrollTime, {
            scrollTo : { y: finalScroll, autoKill:true },
            ease: Power2.easeOut,
            overwrite: 5
        });


        
        /* ===== SCROLL ANIMATION - HEADER ===== */
        function headerFunc(){
            if(!$('.intro-wrap').length > 0 && !$('.loading-wrap').length > 0){ // 인트로&로딩 없으면
                if(delta < 0) {
                    if(!$('body').hasClass('scr-down')) {
                        $('body').removeClass('scr-up');
                        $('body').addClass('scr-down');
                    } 
                }
                if(delta > 0) {
                    if(!$('body').hasClass('scr-up')) {
                        $('body').removeClass('scr-down');
                        $('body').addClass('scr-up');
                    }
                }
            }
        }headerFunc()



        /* ===== SCROLL ANIMATION ===== */
        function scrollAniFunc() {
            var subScrollbtm = finalScroll + $(window).height();

            /* con ani */
            $(".con-ani").each(function(){
                if(subScrollbtm > $(this).offset().top){ 
                    $(this).addClass("on");
                }
            });

            /* footer top btn */
            if(subScrollbtm > $('#footer').offset().top){ 
                $('.topbtn').addClass("fixed");
            }else{
                $('.topbtn').removeClass("fixed");
            }
        }scrollAniFunc() 



        /* ===== MAIN - HEADER BG ===== */
        if($('body').hasClass('index') == true){
            if(finalScroll < $(window).innerHeight() / 2) {
                $('#header').removeClass('st-white st-black');
            }

            if(finalScroll >= $(window).innerHeight() / 2) {
                if(!$('#header').hasClass('st-black')){
                    $('#header').removeClass('st-white');
                    $('#header').addClass('st-black');
                }
            }

            if(finalScroll >= $('.collect').offset().top - $(window).innerHeight()*0.7) {
                if(!$('#header').hasClass('st-white')){
                    $('#header').removeClass('st-black');
                    $('#header').addClass('st-white');
                }
            }

            if(finalScroll >= $('.promotion').offset().top - $(window).innerHeight()*0.7) {
                if(!$('#header').hasClass('st-black')){
                    $('#header').removeClass('st-white');
                    $('#header').addClass('st-black');
                }
            }
        }
    }



    /* ===== CONANI - NON SCROLL ===== */
    $(".con-ani").each(function(){
        if($(window).scrollTop() + $(window).height() > $(this).offset().top){ 
            $(this).addClass("on");
        }
    });



    /* ===== FOOTER TOP BTN SETTING ===== */
    if($(window).scrollTop() + $(window).height() > $('#footer').offset().top){ 
        $('.topbtn').addClass("fixed");
    }else{
        $('.topbtn').removeClass("fixed");
    }
    
    
    
    /* ===== 팝업창 ===== */
    $('.btn-pop-open').on('click',function(e){
        e.preventDefault();

        // 스크롤 막기
        $('body').addClass('prevent-scroll');
        smoothScrollStop();


        // 팝업창 열기
        var popId = $(this).attr('data-popid');

        if($('.collect').length > 0){ // 전시 및 소장품의 경우
            var copyCon = $(this).parent().find('.pop-copy').html();
            $('#' + popId).find('.pop-con').html(copyCon);
        }

        $('#' + popId).fadeIn('fast');
    });



    $('.btn-pop-close').on('click',function(e){
        e.preventDefault();

        // 스크롤 실행
        $('body').removeClass('prevent-scroll');
        smoothScroll();


        // 팝업창 닫기
        if($('.collect').length > 0){ // 전시 및 소장품의 경우
            $('.pop-wrap').find('pop-con').html();
        }
        $(this).parents('.pop-wrap').fadeOut('fast');
    });



    /* ===== OVER SCROLL ===== */
    $('.over-scroll, .nice-select .list').on('mouseenter',function(){
        smoothScrollStop();
    });


    
    $('.over-scroll, .nice-select').on('mouseleave',function(){
        smoothScroll();
    });



    /*===== DEVICE CHECK ===== */
    function deviceChk(){
        var $html = $('html');
        var w = $(window).width();
        if( w < 768 ) $html.addClass('mobile');
        else if( w < 1024 ) $html.addClass('tablet');
        else if( w < 1281 ) $html.addClass('laptop');
        else $html.addClass('desktop');
    }deviceChk()



    /*===== ON RESIZE ===== */
    function onResize(){
        ScrollTrigger.update();
    }onResize()



    /* ===== RESIZE / REFRESH ===== */
    ScrollTrigger.refresh();

    $(window).on('resize',function(){
        $('html').removeClass('desktop laptop tablet mobile');
        deviceChk();
        requestAnimationFrame(onResize);
    });

});

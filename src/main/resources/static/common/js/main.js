/* ===== MAIN FUNCTION ===== */
$(function(){
    $('body').addClass('load-prevent-scroll'); // body 스크롤 막기
    $('body').addClass('load-ready'); // 로딩화면 불러오기



    /* ===== VISUAL SLIDER ===== */
    var visualSlider = new Swiper(".visual-slider", {
        slidesPerView: 1,
        spaceBetween: 0,
        centeredSlides: true,
        observer: true,
        observeParents: true,
        noSwiping: true,
        noSwipingClass: 'swiper-slide',
    });



    /* ===== VISUAL MOTION ===== */
    var visualTl = gsap.timeline({
        scrollTrigger: {
            trigger: '.visual',
            start: 'bottom bottom',
            end: 'bottom 50%',
            pin:true,
            pinSpacing:false,
            scrub: true,
        }
    });
    visualTl.fromTo('.visual', { opacity:1 },{ opacity:0 });



    /* ===== METABUS MOTION ===== */
    ScrollTrigger.create({
        trigger: '.metabus',
        start: 'top 50%',
        end: 'bottom top',
        scrub: true,
        toggleClass: { targets: ".metabus", className: "line-active" },
        //markers:true
    });

    var metabusCoverTl = gsap.timeline({
        scrollTrigger: {
            trigger: '.metabus-cover',
            start: 'top bottom',
            end: 'bottom 80%',
            scrub: true,
            //markers:true
        }
    });
    metabusCoverTl.fromTo('.metabus-cover', { width:'75%' },{ width:'100%' }, 'metaLabel');
    metabusCoverTl.fromTo('.metabus-thumb', { scale:1.12, top:'20%' },{ scale:1, top:'0' }, 'metaLabel');



    /* ===== COLLECT MOTION ===== */
    var collectBgTl = gsap.timeline({
        scrollTrigger: {
            trigger: '.collect',
            start: 'top 70%',
            end: 'top 70%',
            scrub: true,
            //markers:true
        }
    });
    collectBgTl.to('body', { backgroundColor: 'rgba(255,255,255,1)',duration:0.7});



    /* ===== COLLECT MOTION ===== */
    var collectElm = gsap.utils.toArray(".collect-item");
    var collectNum = $('.collect-item').length;
    var listLeftWidth = (window.innerWidth - $('.collect-list').innerWidth()) / 2;

    var getMaxWidth = function getMaxWidth() {
        return collectElm.reduce(function (val, section) {
            return val + section.offsetWidth;
        }, 0);
    };
    var maxWidth = getMaxWidth();
    var elmWidth = maxWidth / collectNum;
    var elmSpace = $('.last-item').outerWidth(true) - elmWidth;
    var moveWidth = listLeftWidth + elmWidth * (collectNum - 1) + elmSpace * (collectNum - 1) - window.innerWidth / 2 + elmWidth / 2;

    // ANIMATION SETTING
    var scrollSpeed = 1;
    var collectTl = gsap.timeline();

    // ANIMATION 1. HORIZONTAL SCROLL
    collectTl.to(collectElm, {
        x: function x() {
            return -moveWidth;
        },
        duration: 1,
        ease: "none",
        onUpdate: function() {
            if($('.collect').hasClass('reached')){
                $('.collect').removeClass('reached');
            }
        },
        onComplete: function() {
            if($('.collect').hasClass('reached')){
                $('.collect').removeClass('reached');
            }
            $('.collect').addClass('reached');
        },
    });

    // ANIMATION 2. SEARCH WRAP OPEN
    collectTl.to('.collect-search-wrap', {
        display:'block',
        width:'100vw',
        delay:0.4
    },'searchLabel');
    collectTl.to('.collect-search-wrap .formbox', { opacity:1, duration:0.6, delay:0.4},'searchLabel');
    collectTl.to('.collect-search-wrap .collect-search-sm .txt-con .txt', { fontSize:'2.4rem'},'searchLabel');
    collectTl.set({}, {}, "+=0.2"); // add end time

    // ANIMATION TRIGGER
    ScrollTrigger.create({
        animation: collectTl,
        trigger: ".collect .inner",
        pin: true,
        scrub: 1,
        end: function end() {
            return "+=" + maxWidth / scrollSpeed;
        },
        invalidateOnRefresh: true
    });



    /* ===== PROMOTION ===== */
    var promoBgTl = gsap.timeline({
        scrollTrigger: {
            trigger: '.promotion',
            start: 'top 70%',
            end: 'top 70%',
            scrub: true,
            //markers:true
        }
    });
    promoBgTl.to('body', { backgroundColor: 'rgba(38,37,36,1)' ,duration:0.7});
});



/* ===== LOADING MOTION ===== */
window.onload = function() {
    if($('.intro-wrap').length > 0){
        $('.loading-wrap').remove(); 

        //bg line
        var introBgLineTl = gsap.timeline();
        introBgLineTl.fromTo('.intro-line .line-left01', {opacity:0, x:'-50%'}, {opacity:1, x:0, y:0, delay:1, duration:6},'introBg');
        introBgLineTl.fromTo('.intro-line .line-left02', {opacity:0, x:'-30%'}, {opacity:1, x:0, y:0, duration:6},'introBg');
        introBgLineTl.fromTo('.intro-line .line-right01', {opacity:0, x:'50%'}, {opacity:1, x:0, y:0, delay:1, duration:6},'introBg');
        introBgLineTl.fromTo('.intro-line .line-right02', {opacity:0, x:'30%', y:'-20%'}, {opacity:1, x:0, y:0, duration:6},'introBg');

        // word
        var introWordTl = gsap.timeline();
        introWordTl.fromTo('.intro-word01 .word01 .word', {opacity:0, x:'10%'}, {opacity:1, x:0, duration:1.5},'intro');
        introWordTl.fromTo('.intro-word01 .word02 .word', {opacity:0}, {opacity:1, duration:0.5},'intro');
        introWordTl.set({},{},'+=1');
        introWordTl.to('.intro-word01 .word01 .word', {opacity:0, x:'-10%', duration:1.5},'intro01');
        introWordTl.to('.intro-word01 .word02 .word', {opacity:0, duration:0.5},'intro01');
        introWordTl.fromTo('.intro-word02 .word01 .word', {opacity:0, x:'10%'}, {opacity:1, x:0, duration:1.5},'intro01-01');
        introWordTl.fromTo('.intro-word02 .word02 .word', {opacity:0}, {opacity:1, duration:0.5},'intro01-01');
        introWordTl.set({},{},'+=1');
        introWordTl.to('.intro-word02 .word01 .word', {opacity:0, x:'-10%', duration:1.5},'intro02');
        introWordTl.to('.intro-word02 .word02 .word', {opacity:0, duration:0.5},'intro02');
        introWordTl.fromTo('.intro-word03 .word01 .word', {opacity:0, x:'10%'}, {opacity:1, x:0, duration:1.5},'intro02-01');
        introWordTl.fromTo('.intro-word03 .word02 .word', {opacity:0}, {opacity:1, duration:0.5,},'intro02-01');

        //bar 
        var introBarTl = gsap.timeline();
        introBarTl.fromTo('.intro-bar .bar', {width:'0%'}, {width:'33.3333%', duration:4});
        introBarTl.fromTo('.intro-bar .bar', {width:'33.3333%'}, {width:'66.6666%', duration:4});
        introBarTl.fromTo('.intro-bar .bar', {width:'66.6666%'}, {width:'100%', duration:4,
            /* bar 완료시 body 진입 */
            onComplete:function(){
                gsap.fromTo($('.intro-wrap'),{opacity:1, scale:1}, {opacity:0, scale:0.95, duration:0.6},'intro-last');
                gsap.fromTo($('.visual'),{opacity:0, scale:0.9}, {opacity:1, scale:1, duration:1},'intro-last');
                $('body').removeClass('load-ready');

                setTimeout(function(){
                    $('body').removeClass('load-prevent-scroll');
                    $('.intro-wrap').remove();
                    $('#header').removeClass('st-black st-white');
                }, 1000);
            }
        });
    }else {
        setTimeout(function(){
            $('body').removeClass('load-prevent-scroll load-ready');
            $('#header').removeClass('st-black st-white');
            $('.loading-wrap').fadeOut(500); 
        }, 2000);
    
        setTimeout(function(){
            $('.loading-wrap').remove(); 
        }, 2500); // fadeout 시간 만큼+
    }
}
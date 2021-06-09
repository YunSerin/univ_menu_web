$(document).ready(function(){
    $('.starRating span').click(function(){
        $(this).parent().children('span').removeClass('on');
        $(this).addClass('on').prevAll('span').addClass('on');
        var starValue = '별점 : '+ $(this).attr("value");
        $('.starRating p').html(starValue);
        return false;
    });
});

document.getElementById('currentDate').value = new Date().toISOString().substring(0, 10);;
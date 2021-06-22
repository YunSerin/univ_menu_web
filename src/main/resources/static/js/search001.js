// $(document).ready(function(){
//     $('.starRating span').click(function(){
//         $(this).parent().children('span').removeClass('on');
//         $(this).addClass('on').prevAll('span').addClass('on');
//         var starValue = $(this).attr("value");
//        // console.log(starValue);
//         $('.starRating p').html(starValue);
//         return false;
//     });
// });

document.getElementById('reviewDate').value = new Date().toISOString().substring(0, 10);
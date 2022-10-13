$(document).ready(function(){
    let rating = $("#user-rate").text();
    console.log(rating);
    let starId = "star-rating-" + rating;
    console.log(starId);
    $("#"+starId).prop('checked', true);

    let form = $('#change-rating-form');
    $('.star-rating__input').each(function(i, element){
        let radio = $(element);
        radio.on('change', function(){
            form.submit();
        });
    });
});
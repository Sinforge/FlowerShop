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
    let deleteButtons = $(".delete-user-comment");
    deleteButtons.each((i, element)=> {
       let button = $(element);
       let name = "#" + "form-" + button.name;
        console.log($(name).serialize());
        button.on("click", function () {
          $.ajax({
             url: "/deleteComment",
              type: "POST",
              data: $(name).serialize(),
              success: (response) => {
                  console.log(response);
                  $(".list-comments").html(response);
              }
          });
       });
    });
});
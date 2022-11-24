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
    let deleteButtons = $(".delete-button");
    deleteButtons.each((i, element)=> {
       let button = $(deleteButtons[i]);
       let name = "#" + "form-" + button.attr('name');
       console.log(name);
       console.log($(name));

        console.log($(name).serialize());
        button.on("click", function () {
          $.ajax({
             url: "/deleteComment",
              type: "POST",
              data: $(name).serialize(),
              success: (response) => {
                  console.log(response);
                  refreshCommentsAdmin(response);
              }
          });
       });
    });
    let sendButton = $("#send-button");
    sendButton.on("click", ()=> {
       $.ajax( {
           url: "/LeaveComment",
           type: "POST",
           data: $("#send-comment").serialize(),
           success: (response) => {
               console.log(response);
               refreshCommentsUser(response);
           }
       }) ;
    });
    function refreshCommentsAdmin(dataMassive) {
        let listComments = $("#list-comments");
        listComments.html('');
        let template;
        for(let i =0; i< dataMassive.length;i++) {
            template = '<div class="author-comment"><div class="inline-author"><img src="/static/img/DefaultUserImg.png" width="100px" height="100px" alt="user_img"/>';
            template += '<form id="' + 'form-' + dataMassive[i].id + '">' ;
            template += '<input value="' + dataMassive[i].id +
                '"  name="commentId"  hidden>' +
                '<input value="' + dataMassive[i].flowerid +
                '" name="flowerId" hidden>' + '</form>' +
                '<button class="delete-button" name="' + dataMassive[i].id + '">Delete</button>' +
                '<p>' + dataMassive[i].username + '</p><br>' +
                '</div>' +
                '<p>' + dataMassive[i].text +
                '</p>' + '</div>';
            listComments.append(template);
        }



    }
    function  refreshCommentsUser(dataMassive) {
        let listComments = $("#list-comments");
        let template = '<div class="author-comment"><div class="inline-author"><img src="/static/img/DefaultUserImg.png" width="100px" height="100px" alt="user_img"/>';
        template +=
            '<p>' + dataMassive[dataMassive.length-1].username + '</p><br>' +
            '</div>' +
            '<p>' + dataMassive[dataMassive.length-1].text +
            '</p>' + '</div>';
        listComments.append(template);
    }
});
function register() {
$(document).ready(function(){
    $.get('/register', function(data,status){
        $("#main_container").html(data);
    });
  });
}

function login() {
$(document).ready(function(){
    $.get('/login', function(data,status){
        $("#main_container").html(data);
    });
  });
}

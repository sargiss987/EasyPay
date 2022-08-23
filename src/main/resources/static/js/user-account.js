function createAccount() {
$(document).ready(function(){
    $.get('/api/v1/bank/account', function(data,status){
         $("#mainContainer").html(data);
    });
  });
}

function logout() {
$(document).ready(function(){
    $.get('/logout',function(data,status){
        $("#mainContainer").html(data);
    });
  });
}

function accountPage(id) {
$(document).ready(function(){
    $.get('/api/v1/bank/account/page/' + id,function(data,status){
        $("#mainContainer").html(data);
    });
  });
}
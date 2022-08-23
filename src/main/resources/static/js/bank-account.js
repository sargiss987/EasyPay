class TransactionDto {
  constructor(amount,accountId,email) {
    this.amount = amount;
    this.accountId = accountId;
    this.email = email;
  }
}

const errorMessageEmpty = 'The amount can not be empty';
const errorMessageZeroOrNegative = 'The amount can not be zero or negative';


function deposit(amount,accountId,email) {
  if(!amount) {
     return $('#depositAmountError').text(errorMessageEmpty);
  } else if(amount <= 0){
     return $('#depositAmountError').text(errorMessageZeroOrNegative);
  } else {
     $('#depositAmountError').text('');
  }
  let transactionDto = new TransactionDto(amount,accountId,email);
  let csrfToken = $("meta[name='_csrf']").attr("content");
  let csrfHeader = $("meta[name='_csrf_header']").attr("content");
  $.ajax({
    url: '/api/v1/account/deposit',
    type: 'POST',
    data: JSON.stringify(transactionDto),
    contentType: 'application/json',
    success: function(data) {
        return $("#balance").text(data);
    },
    beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    }
  });
}

function withdraw(amount,accountId,email) {
  if(!amount) {
     return $('#withdrawAmountError').text(errorMessageEmpty);
  }else if(amount <= 0){
     return $('#withdrawAmountError').text(errorMessageZeroOrNegative);
  }else {
     $('#withdrawAmountError').text('');
  }
  let transactionDto = new TransactionDto(amount,accountId,email);
  let csrfToken = $("meta[name='_csrf']").attr("content");
  let csrfHeader = $("meta[name='_csrf_header']").attr("content");
  $.ajax({
    url: '/api/v1/account/withdraw',
    type: 'POST',
    data: JSON.stringify(transactionDto),
    contentType: 'application/json',
    success: function(data) {
       return $("#balance").text(data);
    },
    error: function($xhr){
       var data = $xhr.responseJSON;
       $("#transactionFailedHeader").text('Transaction failed');
       $("#transactionFailedMessage").text(data.errorMessage);
       return;
    },
    beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
    }
  });
}
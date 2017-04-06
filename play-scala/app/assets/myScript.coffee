$ ->
  $('#submit-button').on 'click', ->
    $('#myForm').submit ->
      data = undefined
      data = $('#myForm').serialize()

      toValidate = $('#myForm').serializeArray()


      if toValidate[0].value.length < 32
        $("#errorSpan").css("visibility", "visible")
        $("#errorSpan").textContent(nameError)

      console.log toValidate[1].value
      console.log toValidate[2].value
      console.log toValidate[3].value

      nameError = "Please enter a name with containing only letters shorter than 32 characters"
      ageError = "Please enter a numeric age between 12 and 130"
      numberError = "Please enter a 10 digit numeric contact number"
      emailError = "Please enter a valid email address"



      $.post '/formsubmit', data
      false

#      <script type="application/javascript">
#        $(function() {
#          $('#myForm').on('submit', function(e) {
#            e.preventDefault();
#    var data = $('#myForm').serialize();
#    $.post("/formsubmit", data, function(myData, status){
#           alert("Data: " + data + "\nStatus: " + status);
#    })
#    });
#    });
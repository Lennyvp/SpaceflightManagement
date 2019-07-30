//pobieramy wszystkie niezbędne elementy
$form = $('.form');
$inputBody1 = $('#inpBody1');
$inputBody2 = $('#inpBody2');
$inputBody3 = $('#inpBody3');
$inputBody4 = $('#inpBody4');
$inputBody5 = $('#inpBody5');
$inputBody6 = $('#inpBody6');
$submitBtn = $form.find(":submit");

//podpinamy się pod wysłany formularz
$form.on("submit", function(e) {
    e.preventDefault();

//wysyłamy dane
    $.ajax({
        url: "http://localhost:8080/addTourist",
        method : "POST",
        data: JSON.stringify({
            name : $inputBody1.val(),
            surname : $inputBody2.val(),
            sex : $inputBody3.val(),
            country : $inputBody4.val(),
            notes : $inputBody5.val(),
            localDate : $inputBody6.val()}),
        headers:{"Content-Type":"application/json; charset=utf-8"}

    })
});
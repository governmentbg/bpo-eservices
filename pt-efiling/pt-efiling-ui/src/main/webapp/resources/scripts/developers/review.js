
function printPage(){
	window.print();
}

$('#printButton').live("click", function() {
	printPage();
});

$('button[data-val="doSubmit"]').live("click", function(event) {

    event.preventDefault();
    showConfirmModal($("#applicationSubmitConfirmation").html(),
        function() {
            submitMainForm($('button[data-val="doSubmit"]').attr("data-val"), true);
        },
        function() {
            $("#applicationSubmitConfirmation").hide();
        });

});
$(document).ready(function() {

    $(document).on('click', 'a.search-icon[id^=thumbnail-list]', function(ev) {
        var designId = $(this).attr("data-val");
        var rowNumber = $(this).attr("data-rownum");

        showLoadingScreen();
        $('#viewThumbnailList'+rowNumber ).modal('show');
        hideLoadingScreen();
    });

});


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
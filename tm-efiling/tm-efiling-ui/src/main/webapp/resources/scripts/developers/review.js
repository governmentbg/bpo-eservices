$(document).ready(function() {

	$(document).on('click', 'button[id^=thumbnail-list]', function(ev) {
		$('#viewThumbnailList').modal('show');
	});

});
function printPage(){
	window.print();
}

$('#printButton').live("click", function() {
	printPage();
});
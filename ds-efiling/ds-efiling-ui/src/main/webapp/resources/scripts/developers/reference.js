$(function() {
	$('#useReference').on('click', function() {
		if (this.checked) {
			$('#reference-container').removeClass('hidden');
		} else {
			$inputReference = $('input#reference');
			$inputReference.focus();
			$inputReference.val('');
			$inputReference.blur();
			$('#reference-container').addClass('hidden');
		}
	});
});
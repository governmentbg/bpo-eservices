var DesignsLink = {};

!function() {

	function selectOptionsLists(partialId) {
		$('#linkDesignsFrom' + partialId + ' option, #linkDesignsTo' + partialId + ' option').prop('selected', 'selected');
	}
	
	this.selectOptionsDesignerLists = function() { // Call in designer.js
		selectOptionsLists('Designer');
	};
	
	this.selectOptionsDivisionalApplicationLists = function() { // Call in divisionalApplication.js
		selectOptionsLists('DivisionalApplication');
	};
	
	this.selectOptionsExhPriorityLists = function() { // Call in claimsWiz.js
		selectOptionsLists('ExhPriority');
	};
	
	this.selectOptionsPriorityLists = function() { // Call in claimsWiz.js
		selectOptionsLists('Priority');
	};
	
}.apply(DesignsLink);

$(document).ready(function() {
	
	function getPartialId(from) {
		return $(this).prop('id').substring(from);
	}
	
	$(document).on('click', 'button[id^="linkAllDesigns"]', function() {
		var partialId = getPartialId.call(this, "linkAllDesigns".length);
		var result = !$('#linkDesignsFrom' + partialId + ' option').remove().appendTo('#linkDesignsTo' + partialId);
        $('#linkDesignsTo' + partialId).trigger('change');
        return result;
	});
	
	$(document).on('click', 'button[id^="linkDesign"]', function() {
		var partialId = getPartialId.call(this, "linkDesign".length);
		var result = !$('#linkDesignsFrom' + partialId + ' option:selected').remove().appendTo('#linkDesignsTo' + partialId);
        $('#linkDesignsTo' + partialId).trigger('change');
        return result;
	});
	
	$(document).on('click', 'button[id^="unlinkDesign"]', function() {
		var partialId = getPartialId.call(this, "unlinkDesign".length);
        var result = !$('#linkDesignsTo' + partialId + ' option:selected').remove().appendTo('#linkDesignsFrom' + partialId);
        $('#linkDesignsFrom' + partialId).trigger('change');
        return result;
	});
	
	$(document).on('click', 'button[id^="unlinkAllDesigns"]', function() {
		var partialId = getPartialId.call(this, "unlinkAllDesigns".length);
        var result = !$('#linkDesignsTo' + partialId + ' option').remove().appendTo('#linkDesignsFrom' + partialId);
        $('#linkDesignsFrom' + partialId).trigger('change');
        return result;
	});

});


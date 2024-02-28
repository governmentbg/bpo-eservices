function getFeesInformation() {
	$.ajax({
		url : "getFeesInformation.htm",
		cache : false,
		/*data : "id=" + id,*/
		success : function(html) {
			$("#fees_content").html(html);
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			$("#fees_content").html(errorThrown);
		}
	});
}

function updateFeesInformation() {
	$.ajax({
		url : "updateFeesInformation.htm",
		cache : false,
		/*data : "id=" + id,*/
		success : function(html) {
			$("#fees_content").html(html);
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			$("#fees_content").html(errorThrown);
		}
	});
}
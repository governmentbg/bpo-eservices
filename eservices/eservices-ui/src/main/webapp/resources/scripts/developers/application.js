$(document).ready(function(){
	$.removeCookie("sessionExpired");
});

$(document).ajaxComplete(function( event, xhr, settings ) {
	if($.cookie("sessionExpired")!==undefined) {
		var dest = $.cookie("sessionExpired");
		showModal($("#sessionExpired").html(), function () {
			$.removeCookie("sessionExpired");
			window.location = dest;
		});
	}
});

$(".saveButton").live("click", function() {
	$.ajax({
		url : "saveApplication.htm",
		type : "POST",
		cache : false,
		success : function(html) {
			showMessageModal(html);
		}
	});
});

$(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
	if (jqXHR.status==902) {
		//alert($("#session_time_out").html());
		submitMainForm('Refresh');
	}
});

function alertSessionTimeOut (){
	alert($("#session_time_out").html());
	window.location.href=$("#inputUrlResponse").val();
}

$(".saveToPCButton").live("click", function() {
	submitMainForm('SaveLocally');
//	$("#saveToPCForm").submit();
	
});

$(".printButton").live("click", function() {
	$.ajax({
		url : "hasLanguage.htm",
		type : "GET",
		cache : false,
		success : function(html) {
			if (!(html.indexOf("true")>-1)) {
				showMessageModal($("#goodsServices_selectLanguageFirst").html());				
			}  else {
				submitMainForm('PrintDraftReceipt');
			}
		}
	});	
});

$(".uploadLoadApplication").live("mousedown", function (event) {
    event.preventDefault();
    $(this).trigger("click");
});

$(".uploadLoadApplication").live("click", function () {
	$(this).fileupload({
	    dataType:"iframe",
	    fileInput:$(this),
	    done:function (e, data) {
            $.each(data.result, function (index, file) {
				var text = file.body.innerHTML;
				if(text == null || text == "") {
					text = file.body.innerText;
				}
				if(text.indexOf(".htm?") != -1) {
					text = file.body.innerText;
				}
            	if (text.indexOf("flMessageError") != -1) {
					if(text.toLowerCase().indexOf("sessionexpired") == -1) {
						showMessageModal(text);
						$("#messageModal .flMessageError").removeClass("flMessageError");
					}
            	} else if (text.indexOf("Failed") != -1) {
            		showMessageModal($("#importApplicationXml_contentNotValid").html());
            	} else {
            		document.location = text.replace(/&amp;/g, '&');
            	}
            	
            	
            });
	    }
	});
});

$(document).on("click", "#processInitiatedBeforePublication", function(){
	$.ajax({
		url : "processInitiatedBeforePublicationChange.htm",
		type : "POST",
		data : "processInitiatedValue="+$(this).is(":checked"),
		success : function(response) {
			updateFeesInformation();
		},
		error: function(){
			showMessageModal($("#generic_errors_service_fail").html());
		}
	});
});

$(document).on("click", ".unpublishedAppSelectButton", function(){
	$.ajax({
		url : "unpublishedAppsAutocomplete.htm",
		type : "GET",
		data : "flowModeId="+$(this).attr("data-flow"),
		success : function(response) {
			if(response.indexOf("flMessageError") != -1){
				showMessageModal($("#generic_errors_service_fail").html());
				return;
			} else if(response.length >0){
				$("#filterUnpublishedForm").show();
				for(var i =0; i< response.length; i++) {
					var jsonVal = response[i];
					var cloned = $("#unpublishedAppsTable").find("tr.unpublishedTemplate").clone();
					$(cloned).find(".dataFiling").text(jsonVal.filingId);
					$(cloned).find(".dataId").text(jsonVal.applicationId);
					$(cloned).find(".dataTitle").text(jsonVal.title != null ? jsonVal.title:"");
					$(cloned).find(".unpublishedImportBtn").attr("data-id", jsonVal.applicationId);
					$(cloned).removeClass("unpublishedTemplate");
					$(cloned).show();
					$("#unpublishedAppsTable").find("tbody").append(cloned);
				}
			} else {
				$("#filterUnpublishedForm").hide();
				$("#noUnpublishedWarn").show();
			}
			$("#unpublishedAppsModal").modal("show");
		},
		error: function(){
			showMessageModal($("#generic_errors_service_fail").html());
		}
	});
});

$(document).on("click", "#unpublishedFilterBtn", function(e){
	e.preventDefault();
	var text = $("#unpublishedFilter").val();
	if(text && text.length >0){
		$('#unpublishedAppsTable').find('tbody tr:not(.unpublishedTemplate)').each(function(i, el){
			var elDataId = $(el).find(".dataId").text().toLowerCase();
			var elDataTitle = $(el).find(".dataTitle").text().toLowerCase();
			if(elDataId.indexOf(text.toLowerCase().trim()) != -1 || elDataTitle.indexOf(text.toLowerCase().trim()) != -1){
				$(el).show();
			} else {
				$(el).hide();
			}
		});
	} else {
		$('#unpublishedAppsTable').find('tbody tr:not(.unpublishedTemplate)').each(function(i, el){
			$(el).show();
		});
	}

	var visibles = $('#unpublishedAppsTable').find('tbody tr:not(.unpublishedTemplate):visible').length;
	if(visibles == 0){
		$("#noUnpublishedWarn").show();
	} else {
		$("#noUnpublishedWarn").hide();
	}

});

$(document).on('hide.bs.modal','#unpublishedAppsModal', function() {
	$('#unpublishedAppsTable').find('tbody tr:not(.unpublishedTemplate)').remove();
	$("#filterUnpublishedForm").hide();
	$("#noUnpublishedWarn").hide();
	$("#unpublishedFilter").val("");
});

$(document).on("change", "input[name='selectedUserdoc']", function(){
	if($(this).attr("id") && $(this).attr("id") == "selectedUserdocObject"){
		$.ajax({
			url : "addUserdocFromObject.htm",
			type : "POST",
			success : function(html) {
				$("#requestRelationDiv").html(html);
			},
			error: function(){
				showMessageModal($("#generic_errors_service_fail").html());
			}
		});
	} else {
		$.ajax({
			url : "addUserdocFromUserdoc.htm",
			type : "POST",
			data: "id="+$(this).val(),
			success : function(html) {
				$("#requestRelationDiv").html(html);
			},
			error: function(){
				showMessageModal($("#generic_errors_service_fail").html());
			}
		});
	}
});
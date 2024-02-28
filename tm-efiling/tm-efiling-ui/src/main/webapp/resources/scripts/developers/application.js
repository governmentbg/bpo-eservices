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
		alert($("#session_time_out").html());
		submitMainForm('Refresh');
	}
});

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

$(document).on("change","#certificateRequestedIndicator",  function() {
	$.ajax({
		url : "changeCertificateRequestedIndicator.htm",
		data: "indicatorValue=" + $(this).is(":checked"),
		type : "POST",
		success : function(html) {
			updateFeesInformation();
		}
	});
});

jQuery(window).load(function(){
    showHideDeclaration();
});

$("#attachDocType1").live("change", function(){
   if($(this).val() == "OTHER_DOCUMENT") {
       $("#typeOtherWarning").show();
   } else {
       $("#typeOtherWarning").hide();
   }
});

$("#attachmentType").live("change", function() {
    $("#documentTypeHolder").hide();
    $("#attachDescription1").val("");
    if($("#attachmentType").val() == "GENERAL_POWER_OF_ATTORNEY") {
        $("#documentTypeHolder").show();
    }
});

$(".fileinput-button:not(.disabled) .uploadInput").live("click", function () {
    var parentHolder = $(this).closest("div.fileuploadInfo");
    var isIE = navigator.userAgent.indexOf("MSIE") != -1;
	$("#modal-file-description .errorMessage").html('');

    var uploadDataType = "iframe";
    if (isIE)
    {
        uploadDataType = "iframe";
    }
    var intVal = "";
    $(this).fileupload({
        dataType:uploadDataType,
        drop:function (e, data) {
            alert("test");
            $.each(data.files, function (index, file)
            {
                alert(file.name);
                $(this).val("filename", file.name);
            });
        },
        forceIframeTransport:isIE,
        formData:function (form, fileInput) {
			disableFileUpload(parentHolder);
	
            var sizeOfArray = 0;
            var fileProperties = new Array();
            var fileInputName = $(fileInput).attr("name");
            $(parentHolder).find("#formUpload").find("inputmock").each(function (index) {
                fileProperties[sizeOfArray] = new Object();
                fileProperties[sizeOfArray].name = $(this).attr("name");
                fileProperties[sizeOfArray].value = $(this).attr("value");
                sizeOfArray++;
            });
            //special handling for text area
            $(parentHolder).find("textarea").each(function (index) {
                fileProperties[sizeOfArray] = new Object();
                fileProperties[sizeOfArray].name = $(this).attr("name");
                fileProperties[sizeOfArray].value = $(this).attr("value");
                sizeOfArray++;
            });

            $(parentHolder).find("select").each(function (index) {
                fileProperties[sizeOfArray] = new Object();
                fileProperties[sizeOfArray].name = $(this).attr("name");
                fileProperties[sizeOfArray].value = $(this).attr("value");
                sizeOfArray++;
            });

            return fileProperties;
        },
        fileInput:$(this),
        done:function (e, data) {
            if (isIE) {
            	fspLog("it's IE")
                fspLog("upload done !");
                if (intVal != "")
                {
                    clearInterval(intVal);
                }
                $.each(data.result, function (index, file)
                {
                    var innerHTML = file.body.innerHTML;
                    if (innerHTML.indexOf("flMessageError") < 0)
                    {
                        $(parentHolder).html(innerHTML);
                        if($(parentHolder).attr("id") == "otherAttachments") {
                            showHideDeclaration();
                            callGetFastTrackFails();
                        }
                    } else
                    {
                        $(parentHolder).find(".errorMessage").html(innerHTML);
                    }
                });
                $(".modal-backdrop").hide();
            } else {
            	fspLog("upload done !");
                $.each(data.result, function (index, file) {
                    var innerHTML = file.body.innerHTML;
                    if (innerHTML.indexOf("flMessageError") < 0) {
                    	if ($(parentHolder).find('#modal-file-description').length > 0) {
                    		$(parentHolder).find('#modal-file-description').on('hidden.bs.modal', function (e) {
          						$(parentHolder).html(innerHTML);
                                if($(parentHolder).attr("id") == "otherAttachments") {
                                    showHideDeclaration();
                                    callGetFastTrackFails();
                                }
        					});
                    		$(parentHolder).find("#modal-file-description").modal('hide');
                    	} else {
                    		$(parentHolder).html(innerHTML);
                            if($(parentHolder).attr("id") == "otherAttachments") {
                                showHideDeclaration();
                                callGetFastTrackFails();
                            }
                    	}
                    } else {
                        $(parentHolder).find(".errorMessage").html(innerHTML);
                    }
                });
            }
			enableFileUpload(parentHolder);

            $(parentHolder).find(".fileProgressBar .bar").hide();
            $(parentHolder).find(".fileProgressBar .bar").css("width", "0%");
            $(parentHolder).find(".fileProgressBar .bar").html("");
            $(parentHolder).find(".fileProgressBar .bar").show();
        },
        fail:function (e, data)
        {
            if (isIE) {
            	fspLog("it's IE - clearing interval.")
                if (intVal != "") {
                    clearInterval(intVal);
                }
            } else {
            	fspLog("calling fail...");
                var innerHTML = data.jqXHR.responseText;
                if (innerHTML.indexOf("flMessageError") < 0) {
                	if ($(parentHolder).find('#modal-file-description').length > 0) {
                		$(parentHolder).find('#modal-file-description').on('hidden.bs.modal', function (e) {
      						$(parentHolder).html(innerHTML);
                            if($(parentHolder).attr("id") == "otherAttachments") {
                                showHideDeclaration();
                            }
    					});
                		$(parentHolder).find("#modal-file-description").modal('hide');
                	} else {
                		$(parentHolder).html(innerHTML);
                        if($(parentHolder).attr("id") == "otherAttachments") {
                            showHideDeclaration();
                            callGetFastTrackFails();
                        }
                	}
                } else {
                    $(parentHolder).find(".errorMessage").html(innerHTML);
                }
            }
			enableFileUpload(parentHolder);

            $(parentHolder).find(".fileProgressBar .bar").hide();
            $(parentHolder).find(".fileProgressBar .bar").css("width", "0%");
            $(parentHolder).find(".fileProgressBar .bar").html("");
            $(parentHolder).find(".fileProgressBar .bar").show();
        },
        progress:function (e, data)
        {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $(parentHolder).find(".fileProgressBar .bar").css("width", progress + "%");
            fspLog("upload progress: " + progress + "%");
            if (progress != 100)
            {
                $(parentHolder).find(".fileProgressBar .bar").html(progress + "%");
            }
            else
            {
                $(parentHolder).find(".fileProgressBar .bar").html("99%");
            }
        },
        start:function (e, data)
        {
            if (isIE)
            {
                var progress = 0;
                intVal = setInterval(function ()
                {
                    if (progress >= 90)
                    {
                        if (intVal != "")
                        {
                            clearInterval(intVal);
                        }
                    }
                    progress = progress + 10;
                    if (progress >= 100)
                    {
                        progress = 99;
                    }
                    $(parentHolder).find(".fileProgressBar .bar").html(progress + "%");
                    $(parentHolder).find(".fileProgressBar .bar").css("width", progress + "%");
                }, 1000)
            }
            else
            {
                $(parentHolder).find(".fileProgressBar .bar").hide();
                $(parentHolder).find(".fileProgressBar .bar").css("width", "0%");
                $(parentHolder).find(".fileProgressBar .bar").html("");
                $(parentHolder).find(".fileProgressBar .bar").show();
            }
        }
    });
});

$(".file-upload-radio input").live("click", function () {
    var parentNode = $(this).closest("div.row").find(".fileAttachedButton");
    if ($(this).val()=="true") {
        $(parentNode).show();
    } else {
        $(parentNode).hide();
    }
});

	function disableFileUpload(parentHolder) {
		$(parentHolder).find(".errorMessage, .flMessageError").html("");
		$(parentHolder).find("textarea").attr("disabled", "disabled");
		$(parentHolder).find(".fileinput-button").addClass("disabled");
		$(parentHolder).find("input").attr("disabled", "disabled");
		$(parentHolder).find("select").attr("disabled", "disabled");
	}

	function enableFileUpload(parentHolder) {
		$(parentHolder).find("textarea").removeAttr("disabled");
		$(parentHolder).find(".fileinput-button").removeClass("disabled");
		$(parentHolder).find("input").removeAttr("disabled");
		$(parentHolder).find("select").removeAttr("disabled");
	}

$('.toBeRemovedFile:not(.disabled)').live("click", function ()
{
    var parentNode = $(this).closest("div.fileuploadInfo");
    var toBeRemovedFile = $(this).find("span").attr("id");
    var idValue = $(this).parents("form").find(".idFileuploadForm").attr("value");
    var id = "documentId=" + toBeRemovedFile;
    if (idValue != null && idValue != "")
    {
        id = "&id=" + $(this).parents("form").find(".idFileuploadForm").attr("value");
    }
    var fileWrapper = id + "";
    $(parentNode).find("#formUpload").find("inputmock").each(function (index)
    {
        fileWrapper += "&" + $(this).attr("name") + "=";
        fileWrapper += $(this).attr("value");
    });
    showConfirmModal($("#fileMessage_removeFile").html(), function()
    {
        $.ajax({
            url:"removeUploadedFile.htm",
            type:"POST",
            data:fileWrapper,
            cache:false,
            success:function (html)
            {
                $(parentNode).html(html);
                if($(parentNode).attr("id") == "otherAttachments") {
                    showHideDeclaration();
                    callGetFastTrackFails();
                }
            }
        });
    });
    return false;
});

function showHideDeclaration() {
    if($(".tableFileupload").length != 0) {
        $("#otherAttachmentsDeclaration").attr("class", "");
    } else {
        $("#otherAttachmentsDeclaration").attr("class", "hidden");
        $("#trueDocumentsIndicator").removeAttr("checked");
    }
}

$('#trueDocumentsIndicator').live('change', function(){
    $.ajax({
        url:"changeDocumentTrueIndicator.htm",
        type:"POST",
        success : function(html) {
            if ($('#trueDocumentsIndicator').is(':checked')){
                showMessageModal(html);
            }
        }
    });
});

$('#modal-file-description').on('hidden.bs.modal', function (e) {
	$("#modal-file-description .errorMessage").html('');
	$("#modal-file-description #attachDescription1").val('');
});

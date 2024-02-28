$(document).ready(function() {
    $(".signature #date").each(function() {
        $(this).val(new Date().format($("#dateFormat").html()));
    });

    if($("#hasSecondSignature").val() == "true") {
        showSecondSignature();
    } else {
        hideSecondSignature();
    }
});

function showSecondSignature() {
    //$("#addSignatureButton").addClass("active");	
	$("#addSignatureButton").hide();
	$("#removeSignatureButton").addClass("active");		
	$("#removeSignatureButton").show();
	
	$("#addSecondSign").val("on");
    $("#secondSignatureDetails").show();
}

function hideSecondSignature() {

	$("#removeSignatureButton").hide();
	$("#addSignatureButton").show();
    $("#addSignatureButton").removeClass("active");
    $("#addSecondSign").val("off");    
    clearAditionalSignatureFields();
    $("#secondSignatureDetails").hide();
}

$("#addSignatureButton").live("click", function() {
    if($(this).hasClass("active")) {
        showSecondSignature();
    }
})

$("#removeSignatureButton").live("click", function() {
    if(!$(this).hasClass("active")) {
        hideSecondSignature();		
	}
})

function clearAditionalSignatureFields() {
	$("#secondSignatureDetails  input[id='fullName']").val("");
	$("#secondSignatureDetails  input[id='taxIdNumber']").val("");
	$("#secondSignatureDetails  select[id='personCapacity']").val("");	
	$("#secondSignatureDetails  input[id='signaturePlace']").val("");	
	$("#secondSignatureDetails  input[id='position']").val("");
	$("#secondSignatureDetails  input[id='signatureAssociatedText']").val("");	
	$("#secondSignatureDetails  input[id='userId']").val("");	
	
}
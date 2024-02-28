$('#closePaymentPopup').live("click", function() {
	showConfirmModal($("#payment_closePopup").html(), closePopup);
});

function closePopup() {
	$('#paymentContainer').modal("hide");
}

function setMessageError(error, paymentLabel, errorTitle, closeTitle) {
	//Add error message in the error box
	$('#errorBoxForm').append('<div id="errorBox"><p class="header">' + errorTitle + '</p><p class="closeButton"><a href="#">' + closeTitle + '</a></p><dl></dl></div>');
	$('#errorBox').find('dl').append('<dt><a>' + paymentLabel + '</a></dt>');
	$('#errorBox').find('dl').append('<dd>' + error + '</dd>');
}

function setMessageInfo(info) {
	$('#paymentintegrationinfo').append('<p>' + info + '</p>');
	$('#paymentintegrationinfo').show();
	$('#paymentintegrationtext').hide();
}

function removeMessageError() {
	//Close error box
	$('#errorBoxForm .closeButton').trigger('click');	
}

$(window).load(function() {
	$('#paymentOptions #payer-method').live('change', function(event) {
		$('#hiddenCheckPayerDetails').val($('#payer-method').find(':selected').attr('data-aux'));
		showHidePayerDetailsDiv();
	});
	
	$('#paymentOptions #payer-type').live('change', function(event) {
		$('#hiddenPayerType').val($('#payer-type').find(':selected').attr('data-aux'));
		clearPaymentFields(false);
		var paymentData = $('#paymentForm').serialize();
		$.get('getPaymentOptions.htm', paymentData, function(data) {
			$('#paymentOptions').html(data);
			$('#paymentDetails').show();
		});
	});
	
	showHidePayerDetailsDiv();
	
	//Load modal pop up with the external environment if this page was reached with the Payment button
	if($('#doPayment').length > 0 && $('#doPayment').val() == "true") {
		$('#paymentContainer').modal("show");
		var paymentIFrameURL = 'paymentIFrame.htm?' + $('#flowKey').val() + '&' + $('#flowMode').val();
		$('#paymentIFrame').attr('src', paymentIFrameURL );
	}
	
	//Set the text and the action of the button
	if($('#buttonMessage').length > 0) {
		$('button#paymentSubmitButton').html($('#buttonMessage').val() + '<i class="next-icon"></i>');
		$('button#paymentSubmitButton').attr('data-val',$('#buttonAction').val());
	}
	
	//Show error box if there is a paymentMessage
	if($('#paymentMessage').length > 0 && $('#paymentMessage').val() != "") {
		setMessageError($("#paymentMessage").val(), $("#paymentLabel").val(), $("#errorTitle").val(), $("#closeTitle").val());
	}
});

$("#payer-method").live("change", function()
{
    clearPaymentFields(true);
});

function clearPaymentFields(clearAll)
{
	if(clearAll) {
		$('#paymentOptions #payer-type').val("");
		$('#hiddenPayerType').val("");
	}
    $("#paymentDetails input[type='text']").val("");
    $("#paymentDetails select").val("");
}

function showHidePayerDetailsDiv() {
	showPayerDetails = $('#payer-method').find(':selected').attr('data-aux');
	if(showPayerDetails == 'true') {
		$('#payerDiv').show();
		$('#paymentDetails').show();		
	}else{
		$('#payerDiv').hide();
		$('#paymentDetails').hide();
	}
}

$('#whenPayment').live("change", function() {
	showPayLaterDetails = $('#whenPayment').find(':selected').val();
	if(showPayLaterDetails == 'true') {
		$('#paymentLater').removeClass('hidden');
		$('#paymentOptions').addClass('hidden');	
		$('button#paymentSubmitButton').html($('#submitMessage').val() + '<i class="next-icon"></i>');
		$('button#paymentSubmitButton').attr('data-val',$('#submitAction').val());
	}else{
		$('#paymentOptions').removeClass('hidden');
		$('#paymentLater').addClass('hidden');	
		$('button#paymentSubmitButton').html($('#payMessage').val() + '<i class="next-icon"></i>');
		$('button#paymentSubmitButton').attr('data-val',$('#payAction').val());
	}
});

$('#paymentSubmitButton').live("click", function() {

	showConfirmModal($("#applicationSubmitConfirmation").html(),
        function() {
            showLoadingScreen();
            submitMainForm($("#paymentSubmitButton").attr("data-val"), true);
        },
        function() {
            $("#applicationSubmitConfirmation").hide();
        });

});
var OB = {};

//Main button to add a new trademark, open the form to be fill
$('#oppositionBasisTrigger').live('click', function()
{
    toggleOppositionBasisAdd();
});

$('#trademarkIdEarlier').live('focus', function ()
{
	if ($('#previousCtmConfig_officeCode').val()!=''){
		initAutocomplete(this, 'autocompleteEarlierTrademark.htm?previousCTM=false&office=' + $('#previousCtmConfig_officeCode').val());

		tradeMarkAutocomplete(this, '#previousCtmConfig_searchUrlEnabled', '#previousCtmConfig_viewMessage',
			'#previousCtmConfig_searchUrl', '#priorityForm #country', '#priorityTradeMark_importId', true,
			'#priorityForm #country');
	}
});

//Button to add a new trademark to the list of trademarks
$('.addOB').live('click', function (event, ignoreMatches)
{
    ignoreMatches = 'true';
    var countries = $('#inputCountries').val();
    $('#categoryTradeMark-input').attr('disabled','disabled');
    var inputs = $('.file-upload-radio input');
    var hasToFollow = false;
    var textMessage ='';
    var warningId ='';
    if (inputs.length>0){
	    $.each(inputs,function(){
			var actualInput = $(this);
			var actualInputId = $(actualInput).attr('id');
			warningId = '[id=\''+ actualInputId+'-warning\']';
			if ($(warningId).length>0 && actualInputId.indexOf('copy-tofollow-')==0){
                if (actualInputId.indexOf('gReputationClaimDetails')>0
                        && !$(actualInput).is(':hidden') && $(actualInput).is(':checked')){
                    hasToFollow = true;
                    textMessage = textMessage  + $(warningId).val() + '<br><br>';
                } else {
                    if ($(actualInput).is(':checked')){
                        hasToFollow = true;
                        textMessage = textMessage  + $(warningId).val() + '<br><br>';
                    }
                }
			}
		});
    }

    if (hasToFollow){
        var onConfirmYes = partial(OB.ajax.addOBPost, this, OB.nav.addOB, ignoreMatches, countries);
        showConfirmModal($("#tofollow_confirmation_ob").html(textMessage), onConfirmYes);
    }
    else{
        OB.ajax.addOBPost(this, OB.nav.addOB, ignoreMatches, countries);
    }
});

$('.cancelButton.oppositionBasis').live('click', function ()
{
    $('#oppositionBasisSection').html('');
    toggleOppositionBasisAdd();
    scrollToOppositionBasisTop();
    
    //Clean Goods and services 
    var langId = $('#goodsandservices').find('ul.language-tabs').find('li.active').find('a').attr('href');
    cleanGS(langId);
});

$('#editOBButton').live('click', function ()
{
	
    OB.ajax.getOBForm($(this).attr('data-val'), $(this).attr('data-rownum'));
	
	$('#legalActVersionSelectedInput').attr('disabled','disabled');
	$('#divLegalActVersionAbsoluteSelect').hide();
	$('#divLegalActVersionRelativeSelect').hide();
	$('#divLegalActVersionRevocationSelect').hide();
	$('#confirmWarningLAV').hide();
	//$('#legalActVersionSelected').show();
	var groundCategory= $('#groundCategoryMain').val();
	$('#buttonOpposition').show();
	var showImport = false;
	if (groundCategory == 'RELATIVE_GROUNDS'){
	// earlier right
		var eE = $('#eEntitlementRightTypeCodeInput').val();
		
		
		
		if (eE==''){
			$('#divEarlierRight').html('');
			$('#divTradeMarkInfo').html(''); 
		} else if(eE == 'otherRights') {
			$('#eEntitlementRightTypeInput').attr('disabled','disabled');
			$('#eEntitlementRightType').val(eE);
			//$('#divEarlierEntitlementRightTypeHidden').show();
			OB.ajax.getEditEarlierEntitlement();
			$('#expandRelativeGrounds').click();
		}
		else {
			$('#eEntitlementRightTypeInput').attr('disabled','disabled');
			$('#eEntitlementRightType').val(eE);
			//$('#divEarlierEntitlementRightTypeHidden').show();
			OB.ajax.getEditEarlierEntitlement();
			
			var showTM= false;
			$.each($('.relativeCheckbox'), function(i, el){
				if(($(el).attr('value') == 'ds.invalidityGround.29.2.2a' ) && $(el).is(':checked')){
					showTM = true;
				}
			});
			if(eE == 'personLegalInterest' && showTM == false){
				$('#eEntitlementRightTypeInput').attr('disabled','disabled');
				$('#eEntitlementRightType').val(eE);
				//$('#divEarlierEntitlementRightTypeHidden').show();
				OB.ajax.getEditEarlierEntitlement();
                $('#importTMEarlier').show();
				$('#expandRelativeGrounds').click();

			} else {
			
                var catselect = $('#categoryInputHidden').val();
                $('#categoryTradeMark-select').val(catselect);
                $('#categoryTradeMark-input').val($('#categoryTradeMark-select option:selected').text());

                $('#categoryTradeMark-input').attr('disabled','disabled');
                //$('#divCategoryInsert').hide();
                //$('#divCategoryHidden').show();
                $('#categoryTradeMark-aux').val(catselect);

                if ($('#reputationClaimedYes').is(':checked')){
                    $('#fileDocumentAttachmentReputation').show();
                    $('#divExplanationsClaim').show();
                    $('#divReputationCountriesSection').show();

                } else {
                    $('#fileDocumentAttachmentReputation').hide();
                    $('#divExplanationsClaim').hide();
                    $('#divReputationCountriesSection').hide();

                }
                //categoryInputHidden.
                updateGroundsSection('');
                updateRepCountriesSection(true);
                var divShow = $('#categoryTradeMark-aux :selected').attr('label');
                if (divShow != 'divUniqueCountry'){
                    loadEditCountries(false);
                }



                // earlier right details

                OB.ajax.getEditEarlierTM();
                //$('#divEarlierEntitlementRightType').hide();
                $('#tabTMEarlier').show();
                $('#divTradeMarkInfo').show();

                if ($('#countryForeign').length>0){
                    $('#divCountryForeign').hide();
                }

                //Showing the type right details when required
                var inputId='inputHiddenTypeRight';
                var itemValue = $('#earlierEntitlementRightTypes-select').val();
                showTypeRightDescription(itemValue,inputId);

                if ($('#inputImported').length>0 && $('#inputImported').val()=='true'){
                    $('#categoryTradeMark-select').attr('disabled','disabled');
                }

                if($('#importedId').val() == 'true'){
                    $('#manuallyGS').hide();
                    //$('#gsSection .term-list').addClass('notRemovableClass');
                    //$('#gsSection .term-options').addClass('notRemovableClass');
                    $('a').removeClass('term-close');
                    $('a').removeClass('terms-refresh');


                    var someGS =$('.someGS');
                    if (someGS.is(':checked')) {
                        $('#gsSection .term-list').removeClass('notRemovableClass');
                        $('#gsSection .term-options').removeClass('notRemovableClass');
                        $('#gsSection .terms-header').removeClass('notRemovableClass');
                        $('.term-list a').addClass('term-close');
                        $('.term-options a').addClass('term-close');
                        $('.terms-header a').addClass('terms-refresh');
                    }

                     if($('#eEntitlementRightTypeCodeInput').val() != ''){
                         if($('#extent').size() != 0){
                             $('#fame').insertBefore('#extent');
                         }
                         $('#relatedApplications').show();
                         $('#fame').show();
                     }
                }
                else if($('#importedId').val() == 'false'){
                    $('#manuallyGS').show();
                    $('#gsSection .term-list').removeClass('notRemovableClass');
                    $('#gsSection .term-options').removeClass('notRemovableClass');
                    $('.term-list a').addClass('term-close');
                    $('.term-options a').addClass('term-close');
                    $('.terms-header a').addClass('terms-refresh');

                    $('#relatedApplications').show();
                    $('#fame').show();
                }

                if ($('#entitlementOpponet-select').length>0){
                    var inputId='inputHiddenEntitlement';
                    var itemValue = $('#entitlementOpponet-select').val();
                    showEntitlementDetails(itemValue,inputId);
                }

                $('#divEarlierGSRows').show();
                $('#expandEarlierRight').click();
                //$('#expandEarlierGS').click();
                $('#expandEntitlement').click();
                $('#expandRelativeGrounds').click();

                checkGrounds();
                $('#expandEarlierDSRight').click();
			}
		} 
	}
	if (groundCategory == 'REVOCATION_GROUNDS'){
		updateGroundsSection('');
	}
	
    
});

$('#deleteOBButton').live('click', function ()
{
	$('.cancelButton.oppositionBasis').click();
	var obId = $(this).attr('data-val');
    var onConfirmYes = partial(OB.ajax.removeOB, obId);
    showConfirmModal($('#delete_confirmation_ob').html(), onConfirmYes);
});

$('#applicationRegistrationAP').live('click', function()
{
	var type = $('#applicationRegistrationAP').val();
	if (type=='Application'){
		$('#divRegistrationNo').hide();
		$('#divApplicationNo').show();
		}
	else{
		$('#divRegistrationNo').show();
		$('#divApplicationNo').hide();
		}
		
});
$('#applicationRegistrationRE').live('click', function()
		{
			var type = $('#applicationRegistrationRE').val();
			if (type=='Registration'){
				$('#divRegistrationNo').show();
				$('#divApplicationNo').hide();
				
				}
			else{
				$('#divRegistrationNo').hide();
				$('#divApplicationNo').show();
				}
				
		});


$('#categoryTradeMark-select').live('change', function()
{	
	$('#categoryTradeMark-input').val($('#categoryTradeMark-select').val());
	$('#categoryTradeMark-input').attr('disabled','disabled');
	$('#categoryInputHidden').val($('#categoryTradeMark-select').val());
	var officeCode = 'previousCtmConfig_officeCode'+ $('#categoryTradeMark-select').val();
	if (officeCode != 'previousCtmConfig_officeCode'){
		$('#previousCtmConfig_officeCode').val($('#'+officeCode).val());
	}
	else{
		$('#previousCtmConfig_officeCode').val('NONE');
	}
	
	updateRepCountriesSection(false);
	
});

$('#countryForeign').live('change', function()
		{	
			$('#categoryTradeMark-input').val($('#categoryTradeMark-select').val());
			$('#categoryTradeMark-input').attr('disabled','disabled');
			$('#categoryInputHidden').val($('#categoryTradeMark-select').val());
			var officeCode =  $('#countryForeign').val();
			if (officeCode != ''){
				$('#previousCtmConfig_officeCode').val(officeCode);
			}
			else{
				$('#previousCtmConfig_officeCode').val('NONE');
			}
		});

$('#legalactversionAbsolute-select').live('change', function()
{	
	var lavs = $('#legalactversionAbsolute-select').val();
	if (lavs==''){

		$('#confirmWarningLAV').hide();
		
	}
	else{
		$('#codeLegalActVersionSelected').val($('#legalactversionAbsolute-select').val()); 
		$('#legalActVersionSelectedInput').val($('#legalactversionAbsolute-select :selected').text());
		$('#confirmWarningLAV').show();	
	}
		
	
});

$('#legalactversionRelative-select').live('change', function()
		{	
			var lavs = $('#legalactversionRelative-select').val();
			if (lavs==''){

				$('#confirmWarningLAV').hide();
			}
			else{
				$('#codeLegalActVersionSelected').val($('#legalactversionRelative-select').val()); 
				$('#legalActVersionSelectedInput').val($('#legalactversionRelative-select :selected').text());
				$('#confirmWarningLAV').show();	
			}
				
			
		});

$('#legalactversionRevocation-select').live('change', function()
		{	
			var lavs = $('#legalactversionRevocation-select').val();
			if (lavs==''){

				$('#confirmWarningLAV').hide();
			}
			else{
				$('#codeLegalActVersionSelected').val($('#legalactversionRevocation-select').val()); 
				$('#legalActVersionSelectedInput').val($('#legalactversionRevocation-select :selected').text());
				$('#confirmWarningLAV').show();	
			}
				
			
		});

$('#eEntitlementRightType').live('change', function()
		{	
			var eE = $('#eEntitlementRightType').val();
			var eEText = $('#eEntitlementRightType option:selected').text();
			var codeLegal ='text';
			if (eE==''){
				$('#divEarlierRight').html('');
				$('#buttonOpposition').hide();

			}
			else{
				codeLegal = $('#lavCodeHidden').val();
				OB.ajax.changeEarlierEntitlement(eE,eEText,codeLegal);
				$('#buttonOpposition').show();
				$('#eEntitlementRightTypeInput').attr('disabled','disabled');
				$('#eeHidden').val(eEText);
				$('#eEntitlementRightTypeCodeInput').val(eE);
				
				 //Clean Goods and services 
			    var langId = $('#goodsandservices').find('ul.language-tabs').find('li.active').find('a').attr('href');
			    cleanGS(langId);
			    
				if($('#importable').val() == 'true'){
					$('#manuallyGS').hide();
					$('#gsSection .term-list').addClass('notRemovableClass');
					$('#gsSection .term-options').addClass('notRemovableClass');
					$('a').removeClass('term-close');
					$('a').removeClass('terms-refresh');
				}
				else if($('#importable').val() == 'false'){
					$('#manuallyGS').show();
					$('#gsSection .term-list').removeClass('notRemovableClass');
					$('#gsSection .term-options').removeClass('notRemovableClass');
					$('.term-list a').addClass('term-close');
					$('.term-options a').addClass('term-close');
					$('.terms-header a').addClass('terms-refresh');
				}
				
				if ($('#countryForeign').length>0){
					$('#countryForeign').val('AT');
					var officeCode =  $('#countryForeign').val();
					if (officeCode != ''){
						$('#previousCtmConfig_officeCode').val(officeCode);
					}
					else{
						$('#previousCtmConfig_officeCode').val('NONE');
					}
				}
			}
			
		});

$('#gcategory').live('change', function()
		{	
	
	
			var gcat = $('#gcategory').val();
			if (gcat==''){
				$('#divLegalActVersionAbsoluteSelect').hide();
				$('#divLegalActVersionRelativeSelect').hide();			
			}
			else{
				if (gcat == 'ABSOLUTE_GROUNDS'){
					
					$('#divLegalActVersionRelativeSelect').hide();
					$('#divLegalActVersionAbsoluteSelect').show();
					refreshLegalActVersionSection('ABSOLUTE_GROUNDS');
				} 
				else
					{
					
					$('#divLegalActVersionAbsoluteSelect').hide();
				
					$('#divLegalActVersionRelativeSelect').show();
					refreshLegalActVersionSection('RELATIVE_GROUNDS');
					}
				
			}
			$('#groundCategoryMain').val(gcat);	
			
		});

$('#earlierEntitlementRightTypes-select').live('change', function()
		{
		var inputId='inputHiddenTypeRight';
		var itemValue = $('#earlierEntitlementRightTypes-select').val();
		showTypeRightDescription(itemValue,inputId);
		});

$('#entitlementOpponet-select').live('change', function()
		{
		var inputId='inputHiddenEntitlement';
		var itemValue = $('#entitlementOpponet-select').val();
		showEntitlementDetails(itemValue,inputId);
		});


$('#applicantConfirmLegalActVersion').live('click', function ()
		{
	 
	 		

			var valueLegal = $('#legalActVersionSelectedInput').val();  
			var codeLegal= $('#codeLegalActVersionSelected').val();
			$('#categoryTradeMark-input').attr('disabled','disabled');
			var gcat='';
			if (!$('#divLegalActVersionRelativeSelect').is(':hidden')){
				gcat='RELATIVE_GROUNDS';
			}
			else
				if (!$('#divLegalActVersionAbsoluteSelect').is(':hidden')){
					gcat='ABSOLUTE_GROUNDS';
				}
				else
					if (!$('#divLegalActVersionRevocationSelect').is(':hidden')){
						gcat='REVOCATION_GROUNDS';
					}
			
			$('#groundCategoryMain').val(gcat);
			
			
		    if ($('#applicantConfirmLegalActVersion').is(':checked'))
		    {	
		    	selectedLAV(valueLegal,codeLegal);
		    	$('#groundCategoryMain').val(gcat);
		    	if (gcat!='RELATIVE_GROUNDS'){
		    		$('#buttonOpposition').show();
		    	}
		    }
		    
		   
		});

$('#expandEarlierRight').live('click', function()
		{
	
		    $('#divEarlierRightInfoRow').show();
		    $('#collapseEarlierRight').show();
		    $('#expandEarlierRight').hide();
		});

$('#collapseEarlierRight').live('click', function()
		{
		    $('#divEarlierRightInfoRow').hide();
		    $('#expandEarlierRight').show();
		    $('#collapseEarlierRight').hide();
		   
		    
		});

$('#expandEntitlement').live('click', function()
		{
	
		    $('#divEntitlementRows').show();
		    $('#collapseEntitlement').show();
		    $('#expandEntitlement').hide();
		});

$('#collapseEntitlement').live('click', function()
		{
		    $('#divEntitlementRows').hide();
		    $('#expandEntitlement').show();
		    $('#collapseEntitlement').hide();
		   
		    
		});



$('#expandRelativeGrounds').live('click', function()
		{
		    $('#divRelativeGroundsRows').show();
            // DEVIMPFO-110
            if ($('#divRelativeGroundsRows #relativeGrounds input:checkbox').length == 1) {
                $('#divRelativeGroundsRows #relativeGrounds input:checkbox').prop('checked', true);
            }
		    
		    $('#collapseRelativeGrounds').show();
		    $('#expandRelativeGrounds').hide();
		});

$('#collapseRelativeGrounds').live('click', function()
		{
		    $('#divRelativeGroundsRows').hide();
		    $('#expandRelativeGrounds').show();
		    $('#collapseRelativeGrounds').hide();
		});

$('#reputationClaimedYes').live('click', function(){
		if ($('#reputationClaimedYes').is(':checked')){
			
			$('#fileDocumentAttachmentReputation').show();
			$('#divExplanationsClaim').show();
			$('#divReputationCountriesSection').show();
			
			}
		else{
			$('#fileDocumentAttachmentReputation').hide();
			$('#divExplanationsClaim').hide();
			$('#divReputationCountriesSection').hide();
		}
	
}
		

);

$('#reputationClaimedNot').live('click', function(){
	if ($('#reputationClaimedNot').is(':checked')){
		
		$('#fileDocumentAttachmentReputation').hide();
		$('#divExplanationsClaim').hide();
		$('#divReputationCountriesSection').hide();
		}
	else{
		$('#fileDocumentAttachmentReputation').show();
		$('#divExplanationsClaim').show();
		$('#divReputationCountriesSection').show();
	}

}
	

);

$('.importTMEarlier').live('click', function() {
	var importId = getIdToImportEarlier();
	var officeImport = $('#previousCtmConfig_officeCode').val();
	var eE = $('#eEntitlementRightTypeCodeInput').val();
	
	if ($('#countryForeign').length>0){
		$('#divCountryForeign').show();
	}
	
	if ((importId) && officeImport!='' && officeImport != 'NONE') {
		OB.ajax.showTMEarlierDetails(importId, eE, officeImport);
		if ($('#inputImported').length>0){
			if ($('#inputImported').val()=='true'){
				$('#categoryTradeMark-select').attr('disabled','disabled');
				if ($('#countryForeign').length>0){
					$('#countryForeign').attr('disabled','disabled');
				}
					
				if (($('#addressCountryReg').length>0) && ($('#addressCountryReg').val()=='')){
					$('#addressCountryReg').val(officeImport);
					
				}
			}
		}
	}
});

$('#createEarlierManualDetails').live('click', function()
		{
			$('#fame').insertAfter('#divTradeMarkInfo');			
			var eE = $('#eEntitlementRightTypeCodeInput').val();
			var langId = $('#goodsandservices').find('ul.language-tabs').find('li.active').find('a').attr('href');
	        cleanGS(langId);
	        showTMEarlierTab('add',eE);
	        $('#relatedApplications').show();
	        $('#fame').show();
	        if ($('#countryForeign').length>0){
				$('#divCountryForeign').hide();
			}
		    //toggleTMEarlierAdd(eE);
		    //$('#applicantCurrentNumber').html(Applicant.getApplicantCount() + 1);
		});


function getIdToImportEarlier() {
	// Checks if the user has selected a value using the autocomplete feature
    var importId = $('#auto_trademarkId').val();
    if (importId == null || importId.trim() == '')
    {
        // if not, make the call to the import service using the value in the IDpreviousCTM text box
        importId = $('#trademarkIdEarlier').val();
    }
    if (importId.trim() == '')
    {
        showWarningModal($('#previousCtm_missingInputText').html());
        return;
    }
    return importId;
}

$('.earlier_dessign_choose').live('change', function (){
	var checked = $(this).attr('checked') == 'checked';
	var id= $(this).attr('data-target');
	OB.ajax.changeEarlierDesignChecked(checked, id);
});

$(".importEarlierDesignByAppIdBtn").live("click", function(){
	var appId = $(this).attr("data-app");
	$("#multipleDesignAppsModal").modal("hide");
	OB.ajax.showDSEarlierDetailsForAppId(appId);
});


OB.nav =
{
		addOB:'addOppositionBasis.htm',
	    getOB:'getOppositionBasisForEdit.htm',
	    removeOB:'removeOppositionBasis.htm',
	    selectLAV: 'selectLegalActVersion.htm',
	    confirmLAV: 'confirmLegalActVersion.htm',
	    changeEE:'changeEarlierEntitlement.htm',
	    importTMEarlier:'importTrademarkEarlier.htm',
	    addTMEarlier:'addTMEarlierDetails.htm',
	    getEditEE:'getEditEarlierEntitlement.htm',
	    getEditETM:'getEditEarlierTM.htm',
	    getErrorEE:'getErrorEarlierEntitlement.htm',
		getErrorETM:'getErrorEarlierTM.htm',
		importDSEarlier: 'importDesignEarlier.htm',
		importDSEarlierByAppId: 'importDesignEarlierByApplicationId.htm',
		getEditEDS:'getEditEarlierDS.htm',
		changeEarlierDSChecked: 'changeEarlierDesignChecked.htm'
	
};

OB.ajax =
{
		changeEarlierDesignChecked: function(selected, id){
			$.ajax({
				url:OB.nav.changeEarlierDSChecked,
				async:false,
				type:'POST',
				data:'selected='+selected+'&id='+id,
				success:function (html)
	            {
	            },
				error:function (error)
				{
					genericHandleError('An error occured while processing your request. Please try again later.',
							'#dsSection', true);
				}
			});
		},
		
		/**
		 * Display the form with the details of trademark to be edited
		 * */
	    displayEarlierTM: function(eE)
	    {
            if($("#earlierTMGSCommentDiv").length != 0) {
                $('#earlierTMGSCommentDiv').hide();
                $('#earlierTMGSCommentDiv').appendTo('#oppositionBasisSection form');
            }
	    	var dataParam='earlierEntitleMent='+eE;
	        removeTMErrors();
	        $.ajax({
	            url:OB.nav.addTMEarlier,
	            data:dataParam,
	            type:'GET',
	            async:false,
	            cache : false,
	            success:function (html)
	            {
	                updateTMEarlierSection(html, 'false');

	                //postUpdateApplicantSection();
	            },
	            error:function (error)
	            {
	                genericHandleError('An error occured while processing your request. Please try again later.',
	                    '#divTradeMarkInfo', true);
	            }
	        });
	    },
	    
	    /**
		 * Display the form with the details of trademark to be edited
		 * */
	    getEditEarlierTM: function()
	    {
	    	var dataToSend = $('#oppositionBasisSection form').serialize();
	        removeTMErrors();
	        $.ajax({
	            url:OB.nav.getEditETM,
	            data:dataToSend,
	            type:'POST',
	            async:false,
	            cache : false,
	            success:function (html)
	            {

	                updateTMEarlierSection(html, 'false');

	                //postUpdateApplicantSection();
	            },
	            error:function (error)
	            {
	                genericHandleError('An error occured while processing your request. Please try again later.',
	                    '#divTradeMarkInfo', true);
	            }
	        });
	    },
	    
	    getEditEarlierDS: function(dsId)
	    {
	    	var dataToSend = 'id='+dsId;
	        $.ajax({
	            url:OB.nav.getEditEDS,
	            data:dataToSend,
	            type:'GET',
	            async:false,
	            cache : false,
	            success:function (html)
	            {

	                $('#tabDSEarlierDetails').html(html);
	                $('#tabDSEarlierDetails').show();

	            },
	            error:function (error)
	            {
	                genericHandleError('An error occured while processing your request. Please try again later.',
	                    '#divTradeMarkInfo', true);
	            }
	        });
	    },
	    
	    getErrorEarlierTM: function()
	    {
	    	var dataToSend = $('#oppositionBasisSection form').serialize();
	        
	        $.ajax({
	            url:OB.nav.getErrorETM,
	            data:dataToSend,
	            type:'POST',
	            async:false,
	            cache : false,
	            success:function (html)
	            {
	                updateTMEarlierSection(html, false);
	                //postUpdateApplicantSection();
	            },
	            error:function (error)
	            {
	                genericHandleError('An error occured while processing your request. Please try again later.',
	                    '#divTradeMarkInfo', true);
	            }
	        });
	    },
	
		/**
	     * Recovers the information relate to a Trademark imported from TMView.
	     * @param importId
	     */
	    showTMEarlierDetails: function(importId, eE, officeImport) {
	    	if($('#earlierTMGSCommentDiv').length >0) {
                $('#earlierTMGSCommentDiv').hide();
                $('#earlierTMGSCommentDiv').appendTo('#oppositionBasisSection form');
            }
	    	var languagesFilter = 'languagesFilter='+ $('#gs_Languages').val();
	    	var dataToSend = $('#oppositionBasisSection form').serialize();
	    	var dataEE = 'earlierEntitleMent='+eE;
	    	var dataOffice = 'officeImport='+officeImport;
	    	dataParam = 'importId='+importId + '&' +dataEE+'&'+dataOffice+'&'+languagesFilter+'&';
	
	    	dataToSend=dataParam + dataToSend;
	    	removeOBErrors();
	    	showLoadingScreen();
	    	$.ajax({
	    		url : OB.nav.importTMEarlier,
	    		data : dataToSend,
	    		type : 'POST',
	    		async:false,
	    		cache : false,
	    		success : function(html)
	    		{
	    			hideLoadingScreen();
	    			var $html = $(html).html();
	    			if ($(html).hasClass('importTrademarkWarning')) {
	    				showWarningModal($html);
	    			} else {
	    				if ($html) {
	    					if($('#appLang') != null){
	    						languagesFilter = languagesFilter + ','+$('#appLang').val();
	    					}
	    					 $('#fame').insertBefore('#relatedApplications');
	    					 updateTMEarlierSection(html, 'true');
	    					 if($('#eEntitlementRightTypeCodeInput').val() != ''){
	    						 if($('#extent').size() != 0){
	    							 $('#fame').insertBefore('#extent');
	    						 }
	    						 $('#relatedApplications').show();
		    				     $('#fame').show();
	    					 }
	    					 $('#tableGoodsAndServices').refreshGS({langId: languagesFilter, languageInfo : getLanguageInfo()});
	    				}
	    				showTMEarlierTab('edit', eE);
	    				//showTabGandS();
	    				
	    				
    					var $warning = $('#customWarningMessageTM').html();
	    				if($warning) {
	    					showWarningModal($warning);
	    				}
	    				
	    			}
	    		},
	    		error:function (error)
	            {
	            	hideLoadingScreen();
	            	showWarningModal($('#generic_errors_service_fail').html());
	            }

	    	});
	    },
	    
	    showDSEarlierDetails: function(importId) {
	    	
	    	var dataToSend = $('#oppositionBasisSection form').serialize();

	    	dataParam = 'importId='+importId+'&';
	
	    	dataToSend=dataParam + dataToSend;
	    	removeOBErrors();
	    	showLoadingScreen();
	    	$.ajax({
	    		url : OB.nav.importDSEarlier,
	    		data : dataToSend,
	    		type : 'POST',
	    		async:false,
	    		cache : false,
	    		success : function(html)
	    		{
	    			hideLoadingScreen();
	    			if ($(html).hasClass('importDesignWarning')) {
						showWarningModal(html);
					} else if($(html).hasClass('designChooseApplication')) {
						showMultipleDesignAppsModal(html);
	    			} else if ($(html).hasClass('importDesignExistsWarning')) {
	    				showWarningModal(html);
	    			} else if (html) {
	    				$('#tabDSImported').html(html);
	    				
	    			}	    				
	    		},
	    		error:function (error)
	            {
	            	hideLoadingScreen();
	            	showWarningModal($('#generic_errors_service_fail').html());
	            }

	    	});
	    },
	showDSEarlierDetailsForAppId: function(applicationId) {

		var dataToSend = $('#oppositionBasisSection form').serialize();

		dataParam = 'applicationId='+applicationId+'&';

		dataToSend=dataParam + dataToSend;
		removeOBErrors();
		showLoadingScreen();
		$.ajax({
			url : OB.nav.importDSEarlierByAppId,
			data : dataToSend,
			type : 'POST',
			async:false,
			cache : false,
			success : function(html)
			{
				hideLoadingScreen();
				if ($(html).hasClass('importDesignWarning')) {
					showWarningModal(html);
				} else if ($(html).hasClass('importDesignExistsWarning')) {
					showWarningModal(html);
				} else if (html) {
					$('#tabDSImported').html(html);

				}
			},
			error:function (error)
			{
				hideLoadingScreen();
				showWarningModal($('#generic_errors_service_fail').html());
			}

		});
	},

		
		
	/**
	 * Display the form with the details of the selected trademark to be edited
	 * */
    displayChooseOB: function()
    {
        removeOBErrors();
        $.ajax({
            url:OB.nav.addOB,
            data:'',
            async:false,
            success:function (html)
            {
                updateOBSection(html, false);
                               
            },
            error:function (error)
            {
                genericHandleError('An error occured while processing your request. Please try again later.',
                    '#oppositionBasisSection', true);
            }
        });
        
    },
    
    
    changeLegalActVersion: function(legalActVar, groundCategory)
    {
    	
    	var dataParam = 'legalAct=' + legalActVar + '&groundCategory=' + groundCategory;
    	
        var selectedUrl = OB.nav.confirmLAV;
    	removeOBErrors();
        $.ajax({
            url:selectedUrl,
            type:'GET',
            async:false,
            data:dataParam,
            success:function (html)
            {
                updateOBSection(html, false);
                postUpdateOBSection(legalActVar);
                
            },
            
            error:function (error)
            {
                genericHandleError('An error occured while processing your request. Please try again later.',
                    '#OppositionBasisSection', true);
            }
        });

    		
    	
    
        },
    
        changeEarlierEntitlement: function(earlierEntitlementVar, earlierEntitlementTextVar, legalActVersionParam)
        {
        	var dataParam = 'earlierEntitleMent=' + earlierEntitlementVar + '&legalActVersionParam=' + legalActVersionParam ;
            var selectedUrl = OB.nav.changeEE;
        	removeOBErrors();
        	var showImport = false;
            $.ajax({
                url:selectedUrl,
                type:'GET',
                async:false,
                data:dataParam,
                success:function (html)
                {
                    updateEESection(html, false);

                    
                   
                    
                },
                
                error:function (error)
                {
                    genericHandleError('An error occured while processing your request. Please try again later.',
                        '#OppositionBasisSection', true);
                }
            });

           /* if ($('#createEarlierManualDetails').length){
            	showImport=true;
            }*/
            
            showImportTMEarlier(true);	
        	
        
            },   
        
            getEditEarlierEntitlement: function()
            {
            	var dataToSend = $('#oppositionBasisSection form').serialize();
            	
                var selectedUrl = OB.nav.getEditEE;
            	removeOBErrors();
            	var showImport = false;
                $.ajax({
                    url:selectedUrl,
                    type:'POST',
                    async:false,
                    data:dataToSend,
                    success:function (html)
                    {
                    	updateEESection(html, false);
                    	refreshExplanations();
                    	$('#tableGoodsAndServices').refreshGS({langId: getLanguageInfo().first, languageInfo : getLanguageInfo()});
 
                    },
                    
                    error:function (error)
                    {
                        genericHandleError('An error occured while processing your request. Please try again later.',
                            '#OppositionBasisSection', true);
                    }
                });

             },             
          
                getErrorEarlierEntitlement: function()
                {
                	var dataToSend = $('#oppositionBasisSection form').serialize();
                	
                    var selectedUrl = OB.nav.getErrorEE;
                	var showImport = false;
                    $.ajax({
                        url:selectedUrl,
                        type:'POST',
                        async:false,
                        data:dataToSend,
                        success:function (html)
                        {
                            updateEESection(html, false);
     
                        },
                        
                        error:function (error)
                        {
                            genericHandleError('An error occured while processing your request. Please try again later.',
                                '#OppositionBasisSection', true);
                        }
                    });

                    if ($('#createEarlierManualDetails').length){
                    	showImport=true;
                    }
                    
                    showImportTMEarlier(showImport);	
                	
                
                    },             
            
        
    /**
     * Posts trademark details to the server
     * @param sender
     * @param address
     * @param ignoreMatches
     */
    addOBPost: function(sender, address, ignoreMatches, countries)
    {
    	$('#tabDSEarlierDetails').html('');
        if($('#earlierTMGSCommentDiv').length >0) {
            $('#earlierTMGSCommentDiv').hide();
            $('#earlierTMGSCommentDiv').appendTo('#oppositionBasisSection form');
        }

        var dataToSend = $('#oppositionBasisSection form').serialize();
        if (ignoreMatches)
        {
            dataToSend = dataToSend + '&ignoreMatches=true';
        }
        if (countries!=null) 
        {
        	dataToSend = dataToSend + '&countries='+countries;
        }
        removeOBErrors();
        $.ajax(
        {
        	url:address,
            type:'POST',
            data:dataToSend,
            success:function (html)
            {        	
            	if ($('input#formReturned', $(html)).val() == 'true')
                {
                	// then display the errors
                    displayOBValidationErrors(html);
                    
                    if ($('#tableGoodsAndServices').length){
						$('#tableGoodsAndServices').refreshGS({langId: getLanguageInfo().first, languageInfo : getLanguageInfo()});
					}
                    $('#inputCountries').val(countries);
                    refreshExplanations();
                    
                    getViewsErrors();
                    $('#previousCtmConfig_officeCode').val($('#previousCtmConfig_officeCode'+$('#categoryTradeMark-select option:selected').val()).val());
                    return;
               	} else {
            	refreshOBCards(html);
               	}
                $('.cancelButton.oppositionBasis').click();

            },
            error:function (error)
            {
            	genericHandleError('An error occured while processing your request. Please try again later.', '#oppositionBasisSection', true);
            }
        });
    },
    
    
   
    
    /**
     * Gets filled trade mark details form to edit
     * @param applicantId
     */
    getOBForm: function(obId, rowNumber)
    {
    	removeOBErrors();
        $.ajax({
            url:OB.nav.getOB,
            type:'GET',
            async:false,
            data:'id=' + obId,
            success:function (html)
            {
            	updateOBSection(html, true);

                //$('#applicantCurrentNumber').html(rowNumber);
                // set 'save' text for editing
                $('#oppositionBasisSection .addOB').each(function()
                {
                    $(this).html($('#personEditText').html());
                })
                refreshExplanations();
                showOBTab('edit');
               

                //postUpdateApplicantSection();
            },
            error:function (error)
            {
                genericHandleError('An error occured while processing your request. Please try again later.',
                    '#applicantCardList', true);
            }
        });
    },
    /**
     * Removes trademark with id embedded in data-val attribute then refreshes applicant cards.
     * @param applicantId
     */
    removeOB: function(tmId)
    {
        removeOBErrors();
        $.ajax({
                url:OB.nav.removeOB,
                data:'id=' + tmId,
                type:'GET',
                success:function (html)
                {
                    refreshOBCards(html);
                    updateFeesInformation();
                    
                },
                error:function (error)
                {
                    genericHandleError('An error occured while processing your request. Please try again later.',
                        '#obCardList', true);
                }
            }
        );
    }
};





/**
 * <p>
 * Function called each time the fields for the applicant need to be shown.
 * It fills the #applicantSection div with the value of the html parameter.
 * </p>
 * <p>
 * The forEditing parameter specifies whether the applicant is being added or edited.
 * If the applicant is being edited, the correspondence Address template needs to be created
 * from the existing address objects.
 * </p>
 */
function updateOBSection(html, forEditing)
{
    $('#oppositionBasisSection').html(html);
}

function displayOBValidationErrors(html)
{
    if($("#earlierTMGSCommentDiv").length != 0) {
        $("#earlierTMGSCommentDiv").remove();
    }
	$('#oppositionBasisSection').html(html);
    if($("#earlierTMGSCommentDiv").length != 0) {
        $('#earlierTMGSCommentDiv').insertAfter('#divEarlierGSRows');
        $('#earlierTMGSCommentDiv').show();
    }
	if($('#importedId').val() == 'true'){
		if($('#eEntitlementRightTypeCodeInput').val() != ''){
			 if($('#extent').size() != 0){
				 $('#fame').insertBefore('#extent');
			 }
			 $('#relatedApplications').show();
		     $('#fame').show();
		 }
	}
	else if($('#importedId').val() == 'false'){
		$('#relatedApplications').show();
        $('#fame').show();
	}
}

function updateEESection(html, forEditing)
{
    $('#divEarlierRight').html(html);
    
}

function scrollToOppositionBasisTop()
{
    scrollToContainer('#obCardList');
}

function toggleOppositionBasisAdd()
{
    if($('#tabOppositionBasis').is(':hidden'))
    {
        $('#oppositionBasisTrigger').addClass('active');
        showOBTab();
        //$('#confirmWarningLAV').show();	
    }
    else
    {
        $('#oppositionBasisTrigger').removeClass('active');
        hideOBTab();
    }
    
}

function showOBTab(forEdit)
{
    $('#tabOppositionBasis').show();
    /*if(($('#applicantCreateNew').size() == 0 || $('#applicantCreateNew').is(':hidden'))
        && forEdit != 'edit')
    {*/
    if(forEdit != 'edit'){
    	
    	OB.ajax.displayChooseOB();
    	refreshLegalActVersionSection('INIT'); 
    }

    $('#oppositionBasisTrigger').addClass('active');
    
}


function hideOBTab()
{
   
    $('#tabOppositionBasis').hide();
    $('#oppositionBasisTrigger').removeClass('active');
}
function selectedLAV(legalAct,codeLegalAct)
{
	var legalActParam = codeLegalAct;
	var groundCategory = $('#groundCategoryMain').val();
	OB.ajax.changeLegalActVersion(legalActParam, groundCategory);
	
	$('#legalActVersionSelectedInput').attr('disabled','disabled');
	$('#lavHidden').val(legalAct);
	$('#lavCodeHidden').val(codeLegalAct);
	$('#legalActVersionSelectedInput').val(legalAct);
	$('#codeLegalActVersionSelected').val(codeLegalAct);
	$('#divLegalActVersionAbsoluteSelect').hide();
	$('#divLegalActVersionRevocationSelect').hide();
	$('#divLegalActVersionRelativeSelect').hide();
	$('#confirmWarningLAV').hide();
	//$('#legalActVersionSelected').show();


}

function refreshGroundCategory(html)
{

          $('#divGrounds').html(html);
 

	
	/* $('#div1').html(div1);
    var div1 = $('#divGroundCategory').html();
    $('#divGroundCategory').html(div1);
*/
	

}

function refreshOBCards(html)
{
	$('#obCardList').html(html);
	
	scrollToOppositionBasisTop();
    
    checkMaxOBs();
}



function checkMaxOBs(){
	var maxOBs=$('#maxOBs').val();
	
	var rowCount = $('#obCardList tr').length-1;
	
	if(rowCount==maxOBs){
		$('#oppositionBasisTrigger').hide();
	}else{
		$('#oppositionBasisTrigger').show();
	}
	
		
}


function removeOBErrors()
{
	removePreviousErrors('#obCardList');
    removePreviousErrors('#oppositionBasisSection');
}


function postUpdateOBSection(legalActVar)
{	
	if( legalActVar == '') {
		$('#applicantConfirmLegalActVersion').attr('disabled', 'disabled');
	} else {
		$('#applicantConfirmLegalActVersion').removeAttr('disabled');
	}
}

function showTypeRightDescription(itemValue,inputId){
	if ($('#'+inputId).length>0){
	var cadena =  $('#'+inputId).val();
	var listValuesSplit = cadena.split(';');
	var isShowDescription= false;


	
	for(var i=0;i<listValuesSplit.length;i++) {
		var actualValue=listValuesSplit[i];
		if (actualValue==itemValue){
			isShowDescription=true;
		}	
	}
	if (isShowDescription){
		$('#divTypeRightDescription').show();
	}
	else{
		$('#divTypeRightDescription textarea').val('');
		$('#divTypeRightDescription').hide();
		
	}
			
	}
				
}

function showEntitlementDetails(itemValue,inputId){
	
	
	if (itemValue == 'Licensee'){
		$('#divEntitlementDetails').show();
	}
	else{
		$('#divEntitlementDetails').hide();
		var entText = $('#inputHiddenEntitlement');
		if(entText != null){
			entText.val('');
		}
	}
}

function uncheck(checks, id) {
	for(i in checks) {
		if(checks[i].attr("id") !== id) {
			checks[i].attr('checked', false);
		}
	}
}

function updateGroundsSection(checkboxId){

	var nonexclusives = $("#relativeGrounds").find("tr td input:checkbox[dir*='exclusive: false']").map(function () {
		return $(this);
	}).get();
	var exclusives = $("#relativeGrounds").find("tr td input:checkbox[dir*='exclusive: true']").map(function () {
		return $(this);
	}).get();

	if($("#"+checkboxId).attr("dir") === 'exclusive: true') {
		uncheck(nonexclusives);
		uncheck(exclusives, checkboxId);
	} else {
		uncheck(exclusives);
	}

	var cadena =  $('#listExclLaw').val();
	if (cadena) {
	var listChecksSplit = cadena.split(';');

	var cadenaInput = '';
	var listExcl = '';
	var isRepClaim= false;
	var cancelRepClaim = false;
	for(var i=0;i<listChecksSplit.length;i++) {

		var actualCheck=listChecksSplit[i];

		$('#'+actualCheck).removeAttr('disabled');
		if ($('#nonUse'+actualCheck).length) {
			$('#nonUse'+actualCheck).hide();
		}

		if ($('#'+actualCheck).is(':checked')){

			

			cadenaInput = $('#input_excLaw_'+actualCheck).val();
			

			listExcl=listExcl+cadenaInput;

			if (($('#input_repClaim_'+actualCheck).val()=='true') && (cancelRepClaim==false))
				{
				isRepClaim=true;
				}
			else
				{
				cancelRepClaim=true;
				isRepClaim = false;
				}
			
			if ($('#nonUse'+actualCheck).length) {
				$('#nonUse'+actualCheck).show();
				
			}
			
		}	

		
			
	}
	listChecksSplit = listExcl.split(';');
	
	for(i=0;i<listChecksSplit.length;i++) {
		actualCheck=listChecksSplit[i];
		$('#'+actualCheck).attr('checked', false);
		$('#'+actualCheck).attr('disabled', 'disabled');
		
		
		
	}
	if (isRepClaim){
		$('#divReputationClaimed').show();
	}
	else{
		$('#divReputationClaimed').hide();
	}	
	
	}
	
	
}

function updateRepCountriesSection(edit){
	if ($('#divReputationCountriesSection').length>0){
		var ctm = $('#categoryInputHidden').val();
		$('#divManualCountries').hide();
		$('#divEUCountries').hide();
		$('#divAllCountries').hide();
		$('#divButtonCountries').hide();
		$('#divUniqueCountry').hide();
		$('#divTitleCountry').hide();
		
		
		removeAllCountries(edit);
		if (ctm!=''){
			$('#categoryTradeMark-aux').val(ctm);
			var divShow = $('#categoryTradeMark-aux :selected').attr('label');
			var countries = $('#categoryTradeMark-aux :selected').attr('title');
			
			switch (divShow){
				case 'divUniqueCountry':
					$('#divUniqueCountry').show();
					var uniqueCode='';
					var uniqueValue='';
					countries = countries.replace('[','').replace(']','');
					uniqueCode=countries;
					uniqueCode=uniqueCode.replace(' ','');
					$('#repcountries').val(uniqueCode);
					uniqueValue=$('#repcountries :selected').text();
					$('#inputCountries').val(uniqueValue);
					$('#divTitleCountry').show();
					
					addCountryRep(true); 	
					
					break;
				case 'divEUCountries':
					$('#divTitleCountry').show();
					$('#divEUCountries').show();
					$('#divButtonCountries').show();
					break;
				case 'divAllCountries':
					$('#divTitleCountry').show();
					$('#divAllCountries').show();
					$('#divButtonCountries').show();
					break;
				case 'divManualCountries':
					var htmlManual ='<select id=\'selectcountriesManual\'>';
					countries= countries.replace('[','').replace(']','');
					var listCountriesSplit = countries.split(',');
					var itemCode='';
					var itemValue='';
					for(var i=0;i<listCountriesSplit.length;i++) {
						itemCode=listCountriesSplit[i];
						itemCode=itemCode.replace(' ','');
						$('#repcountries').val(itemCode);
						itemValue=$('#repcountries :selected').text();
						var cadOption = '<option label=\''+itemValue+'\' value=\''+itemCode+'\'/>';
						htmlManual = htmlManual + cadOption;
					}
					htmlManual = htmlManual + '</select>';
					$('#divTitleCountry').show();
					$('#divManualCountries').html(htmlManual);
					$('#divManualCountries').show();
					$('#divButtonCountries').show();
					break;
			
			}
		}
	}
}


function updateTMEarlierSection(html, imported)
{
	if(imported == 'true'){
		$('#manuallyGS').hide();
		$('#gsSection .term-list').addClass('notRemovableClass');
		$('#gsSection .term-options').addClass('notRemovableClass');
		$('a').removeClass('term-close');
		$('a').removeClass('terms-refresh');		
	}
	else if(imported == 'false'){
		$('#manuallyGS').show();
		$('#gsSection .term-list').removeClass('notRemovableClass');
		$('#gsSection .term-options').removeClass('notRemovableClass');
		$('.term-list a').addClass('term-close');
		$('.term-options a').addClass('term-close');
		$('.terms-header a').addClass('terms-refresh');
	}

    if($('#earlierTMGSCommentDiv').length >0){
        $('#earlierTMGSCommentDiv').remove();
	}

    $('#divTradeMarkInfo').html(html);
    $('#earlierTMGSCommentDiv').insertAfter('#divEarlierGSRows');
    
}

function showTMEarlierTab(forEdit,eE)
{
	$('#tabTMEarlier').show();
    
    if(forEdit != 'edit'){
    	
    	OB.ajax.displayEarlierTM(eE);
    
    }
    
    
    if (forEdit != 'notImport'){
    $('#createEarlierManualDetails').addClass('active');
    }
    $('#divEarlierGSRows').show();
}

function hideTMEarlierTab()
{
    $('#tabTMEarlier').hide();
    $('#createEarlierManualDetails').removeClass('active');
}

function toggleImportTMEarlier()
{
    if($('#importTMEarlier').is(':hidden'))
    {
        
    	showImportTMEarlier(true);
    }
    else
    {
       
    	hideImportTMEarlier();
    }
}

function showImportTMEarlier(showImport)
{
	if (showImport) {
		$('#importTMEarlier').show();
	}
//	else{
//		var eE = $('#eEntitlementRightType').val();
//		var forEdit = 'add';
//		if (!showImport){
//			forEdit = 'notImport';
//		}
//		showTMEarlierTab(forEdit,eE);
//	}
   
}

function hideImportTMEarlier(){
	$('#tabTMEarlier').hide();
	$('#importTMEarlier').hide();

}

function getViewsErrors(){
			
			$('#legalActVersionSelectedInput').attr('disabled','disabled');
			$('#divLegalActVersionAbsoluteSelect').hide();
			$('#divLegalActVersionRelativeSelect').hide();
			$('#divLegalActVersionRevocationSelect').hide();
			$('#confirmWarningLAV').hide();
			//$('#legalActVersionSelected').show();
			$('#buttonOpposition').show();
			var groundCategory= $('#groundCategoryMain').val();
			var showImport = false;
			if (groundCategory == 'RELATIVE_GROUNDS'){
			// earlier right
				var eE = $('#eEntitlementRightTypeCodeInput').val();
				
				
				
				if (eE==''){
					$('#divEarlierRight').html('');
					$('#divTradeMarkInfo').html(''); 
				}
				else{
					$('#eEntitlementRightTypeInput').attr('disabled','disabled');
					$('#eEntitlementRightType').val(eE);
					//$('#divEarlierEntitlementRightTypeHidden').show();
					var catselect = $('#categoryInputHidden').val();
					if (catselect != ''){
						$('#categoryTradeMark-select').val(catselect);
						$('#categoryTradeMark-input').val($('#categoryTradeMark-select option:selected').text());
						
						$('#categoryTradeMark-input').attr('disabled','disabled');
					//$('#divCategoryInsert').hide();
					//$('#divCategoryHidden').show();
						$('#categoryTradeMark-aux').val(catselect);
						
					}
					
					
					if ($('#reputationClaimedYes').is(':checked')){
						
						$('#fileDocumentAttachmentReputation').show();
						$('#divExplanationsClaim').show();
						$('#divReputationCountriesSection').show();
						
						}
					else{
						$('#fileDocumentAttachmentReputation').hide();
						$('#divExplanationsClaim').hide();
						$('#divReputationCountriesSection').hide();
					}
					//categoryInputHidden.
					
					updateGroundsSection('');
					updateRepCountriesSection(true);
					var divShow = $('#categoryTradeMark-aux :selected').attr('label');
					if (divShow != 'divUniqueCountry'){
						loadEditCountries(false);
					}
					
					
					if($('#importedId').val() == 'true'){
						$('#manuallyGS').hide();
						$('#gsSection .term-list').addClass('notRemovableClass');
						$('#gsSection .term-options').addClass('notRemovableClass');
						$('a').removeClass('term-close');
						$('a').removeClass('terms-refresh');
						
					}
					else if($('#importedId').val() == 'false'){
						$('#manuallyGS').show();
						$('#gsSection .term-list').removeClass('notRemovableClass');
						$('#gsSection .term-options').removeClass('notRemovableClass');
						$('.term-list a').addClass('term-close');
						$('.term-options a').addClass('term-close');
						$('.terms-header a').addClass('terms-refresh');
					}
					
					//if ($('#createEarlierManualDetails').length){
					if((eE!='personLegalInterest') || (eE=='personLegalInterest' && $('#importedId').val() == 'true')){
		            	
						showImport=true;
		            	showTMEarlierTab('edit',eE);
					} else if(eE=='personLegalInterest' && $('#importedId').val() == 'false'){
						$('#importTMEarlier').show();
					}
		            //}
					
					if ($('#inputImported').length>0){
						if ($('#inputImported').val()=='true'){
							$('#categoryTradeMark-select').attr('disabled','disabled');
						}
					}
					
					if ($('#entitlementOpponet-select').length>0){
						var inputId='inputHiddenEntitlement';
						var itemValue = $('#entitlementOpponet-select').val();
						showEntitlementDetails(itemValue,inputId);
					}
					
					if ($('#countryForeign').length>0){
						$('#divCountryForeign').hide();
					}
					
					showImportTMEarlier(showImport);	
					// earlier right details
					//$('#divEarlierEntitlementRightType').hide();
					//$('#tabTMEarlier').show();
					$('#divTradeMarkInfo').show();
					$('#expandEarlierRight').click();
					//$('#expandEarlierGS').click();
					$('#expandEntitlement').click();
					$('#expandRelativeGrounds').click();
					
					//Showing the type right details when required
					var inputId='inputHiddenTypeRight';
					var itemValue = $('#earlierEntitlementRightTypes-select').val();
					showTypeRightDescription(itemValue,inputId);
					
					checkGrounds();
					$('#expandEarlierDSRight').click();
					$('#importDSEarlier').show();

                    var someGS =$('#tabTMEarlier .someGS');
                    if(someGS.length >0) {
                        if (someGS.is(':checked')) {
                            $('#gsSection .term-list').removeClass('notRemovableClass');
                            $('#gsSection .term-options').removeClass('notRemovableClass');
                            $('#gsSection .terms-header').removeClass('notRemovableClass');
                            $('.term-list a').addClass('term-close');
                            $('.term-options a').addClass('term-close');
                            $('.terms-header a').addClass('terms-refresh');
                        }
                    }

				}
			}
			
			if (groundCategory == 'REVOCATION_GROUNDS'){
				updateGroundsSection('');
			}

		}

function toggleTMEarlierAdd(eE){
    if($('#tabTMEarlier').is(':hidden'))
    {
        $('#createEarlierManualDetails').addClass('active');
        showTMEarlierTab('add',eE);
        
        //showTabGandS();
    }
    else
    {
        $('#createEarlierManualDetails').removeClass('active');
        hideTMEarlierTab();
        var langId = $('#goodsandservices').find('ul.language-tabs').find('li.active').find('a').attr('href');
        cleanGS(langId);
        //hideTabGandS();
    }
}

function refreshLegalActVersionSection(groundCat){
	var countAbsolute = 0;
	var countRelative = 0;
	var countRevocation = 0;
	var valueLegal ='';
	var codeLegal= '';
	var gcat='';
	countAbsolute = $('#countAbsolute').val();
	countRelative = $('#countRelative').val();
	countRevocation = $('#countRevocation').val();
	var oneElement = false;
	if (countAbsolute == 0 && countRelative == 0 && countRevocation == 0){
		$('#divOppositionBasisData').hide();
		$('#divNotLegalActVersion').show();
	}else{
		if (groundCat=='INIT'){
			if(countAbsolute == 1 && countRelative == 0 && countRevocation == 0){
				valueLegal = $('#inputLegalActAbNameVersion').val();  
				codeLegal = $('#inputLegalActAbCode').val();
				oneElement= true;
			}
			if(countAbsolute == 0 && countRelative == 1 && countRevocation == 0){
				valueLegal = $('#inputLegalActReNameVersion').val();  
				codeLegal = $('#inputLegalActReCode').val();
				oneElement= true;
			}
			if(countAbsolute == 0 && countRelative == 0 && countRevocation == 1){
				valueLegal = $('#inputLegalActRevNameVersion').val();  
				codeLegal = $('#inputLegalActRevCode').val();
				oneElement= true;
			}
		}
		else{
			if (groundCat=='ABSOLUTE_GROUNDS' && countAbsolute == 1 ){
				valueLegal = $('#inputLegalActAbNameVersion').val();  
				codeLegal = $('#inputLegalActAbCode').val();
				oneElement= true;
			}
			else 
				if(groundCat=='RELATIVE_GROUNDS' && countRelative == 1){
					valueLegal = $('#inputLegalActReNameVersion').val();  
					codeLegal = $('#inputLegalActReCode').val();
					oneElement= true;
				}
				else 
					if(groundCat=='REVOCATION_GROUNDS' && countRevocation == 1){
						valueLegal = $('#inputLegalActReNameVersion').val();  
						codeLegal = $('#inputLegalActReCode').val();
						oneElement= true;
					}
		}
			
		if (oneElement){
			$('#categoryTradeMark-input').attr('disabled','disabled');
			
			if (!$('#divLegalActVersionRelativeSelect').is(':hidden')){
				gcat='RELATIVE_GROUNDS';
			}
			else
				if (!$('#divLegalActVersionAbsoluteSelect').is(':hidden')){
					gcat='ABSOLUTE_GROUNDS';
				}
				else
					if (!$('#divLegalActVersionRevocationSelect').is(':hidden')){
						gcat='REVOCATION_GROUNDS';
					}	
			$('#groundCategoryMain').val(gcat);
	    	selectedLAV(valueLegal,codeLegal);
	    	$('#groundCategoryMain').val(gcat);
	    	if (gcat!='RELATIVE_GROUNDS'){
	    		$('#buttonOpposition').show();
	    	}
		}
	}
	
}

function refreshExplanations(){
	var explTArea = $('#explanationCommentDiv').find('textarea');
    if(explTArea != null && explTArea.val() != ''){
    	$('.file-upload-radio input').each(function(){
    		if($(this).attr('id').indexOf('explanation') != -1){
    			$(this).click();
    		}
    	});
    }
}


function checkGrounds() {
	var showDS = false;
	var showTM= false;
	$.each($('.relativeCheckbox'), function(i, el){
		if(($(el).attr('value') == 'ds.invalidityGround.29.1.1.12' ||$(el).attr('value') == 'ds.invalidityGround.29.1.1.13'
			|| $(el).attr('value') == 'ds.invalidityGround.29.1.1.13a' || $(el).attr('value') == 'ds.invalidityGround.29.1.3a'
				|| $(el).attr('value') == 'ds.invalidityGround.29.1.3b') && $(el).is(':checked')){
			showDS = true;
		}
		if(($(el).attr('value') == 'ds.invalidityGround.29.2.2a' ) && $(el).is(':checked')){
			showTM = true;
		}
	});
	if(showDS){
		$('#dsEarlierSubsection').show();
	} else {
		$('#dsEarlierSubsection').hide();
	}
	if(showTM){
		$('#tmEarlierSubsection').show();
	} else {
		$('#tmEarlierSubsection').hide();
	}
}

$('#expandEarlierDSRight').live('click', function(){
	$('#divEarlierDSRightInfoRow').show();
	$('#importDSEarlier').show();

	$('#collapseEarlierDSRight').show();
	$('#expandEarlierDSRight').hide();
});

$('#collapseEarlierDSRight').live('click', function(){
	$('#divEarlierDSRightInfoRow').hide();
	$('#collapseEarlierDSRight').hide();
	$('#expandEarlierDSRight').show();
});

$('.importDSEarlier').live('click', function(){
	var importId = getIdDSToImport();
	if (importId) {
		if(importId.indexOf('-') != -1){
			var onConfirmYes = partial(OB.ajax.showDSEarlierDetails, importId);
			showConfirmModal($('#design_import_only_application').html(), onConfirmYes);
		} else {
			OB.ajax.showDSEarlierDetails(importId);
		}
	}
});

$('.editDSEarlierButton').live('click', function(){
	var dsId = $(this).attr('data-val');
	OB.ajax.getEditEarlierDS(dsId);
});
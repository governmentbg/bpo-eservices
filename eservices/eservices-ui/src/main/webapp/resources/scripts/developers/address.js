$("#addressCountry").live("change", function(){
	
	if($("#service_address_autocomplete").val() == "true"){
		var selectedCountry = $(this).val();
		var office = $("#office").val();
		if(selectedCountry != office){
			$("#autocompleteAdddress").hide();
			setEditableFields();
		}
		else if(selectedCountry.trim() == office.trim()){
			$("#autocompleteAdddress").show();
			setNotEditableFields();
		}
	}
});


$("#addressAutocompleteTextfield").live("keyup.autocomplete", function ()
{
    handleDisplayOfAddressTable(this);

//    $(this).bind("autocompleteselect", function(event, ui)
//    {
//        $("#representativeImportTextfield").val(ui.item.rid);
//        return false;
//    }),
    $(this).bind("autocompleteselect", function (event, ui)
    {
        setTimeout(function ()
        {
            if (ui.item == null)
            {
                return;
            }
        
            //$("#representativeImportTextfield").val(ui.item.rnm);
            $("#street").val(ui.item.street);
            $("#street").attr("readonly",true);
            $("#houseNumber").val(ui.item.houseNumber);
            $("#houseNumber").attr("readonly",true);
            $("#city").val(ui.item.city);
            $("#city").attr("readonly",true);
            $("#postalCode").val(ui.item.postalCode);
            $("#postalCode").attr("readonly",true);
            $("#stateprovince").val(ui.item.stateprovince);
            $("#stateprovince").attr("readonly",true);
            
            // set imported form field
            //$("#importedAddress").attr("value", "true");
            
            return false;
        }, 100);
    });
});


function handleDisplayOfAddressTable(object)
{
    if ($(object).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(object).data("autocomplete")._renderItem = function (ul, item)
    {
        return $("<li></li>")
            .data("item.autocomplete", item)
            .append("<a><span class='two-col'><span class='selectable-col'><span class='first-col'>" + item.street + "</span>" +
            "<span class='second-col'>" + item.city + "</span></span></span></a>").appendTo(ul);
//            .append("<div><a>" + item.aid + "<br>" + item.anm + ", " + item.anc + "</a></div>")
    }
}

function setEditableFields()
{
	//$("#street").val("");
    $("#street").attr("readonly", false);
    $("#street").removeClass("readonlyImport");
    
    //$("#houseNumber").val("");
    $("#houseNumber").attr("readonly", false);
    $("#houseNumber").removeClass("readonlyImport");
    
   // $("#city").val("");
    $("#city").attr("readonly", false);
    $("#city").removeClass("readonlyImport");
    
    //$("#postalCode").val("");       
    $("#postalCode").attr("readonly", false);
    $("#postalCode").removeClass("readonlyImport");
    
    //$("#stateprovince").val("");
    $("#stateprovince").attr("readonly", false);
    $("#stateprovince").removeClass("readonlyImport");
}

function setNotEditableFields()
{
	$("#street").val("");
	$("#street").attr("readonly", true);
	$("#street").addClass("readonlyImport");
	
    $("#houseNumber").val("");
    $("#houseNumber").attr("readonly", true);
    $("#houseNumber").addClass("readonlyImport");
    
    $("#city").val("");
    $("#city").attr("readonly", true);
    $("#city").addClass("readonlyImport");
    
    $("#postalCode").val("");       
    $("#postalCode").attr("readonly", true);
    $("#postalCode").addClass("readonlyImport");
    
    $("#stateprovince").val("");
    $("#stateprovince").attr("readonly", true);
    $("#stateprovince").addClass("readonlyImport");
}


////////////*****************CORRESPONDANCE ADDRESS***********////////////////////


$("#correspondenceAddressCountry").live("change", function(){
	if($("#service_address_autocomplete").val() == "true"){	
		var selectedCountry = $(this).val();
		var office = $("#office").val();
		if(selectedCountry != office){
			$("#autocompleteCorrAdddress").hide();
			setEditableCorrFields();
		}
		else if(selectedCountry.trim() == office.trim()){
			$("#autocompleteCorrAdddress").show();
			setNotEditableCorrFields();
		}
	}
});


$("#corrAddressAutocompleteTextfield").live("keyup.autocomplete", function ()
{
    handleDisplayOfCorrAddressTable(this);

//    $(this).bind("autocompleteselect", function(event, ui)
//    {
//        $("#representativeImportTextfield").val(ui.item.rid);
//        return false;
//    }),
    $(this).bind("autocompleteselect", function (event, ui)
    {
        setTimeout(function ()
        {
            if (ui.item == null)
            {
                return;
            }
        
            //$("#representativeImportTextfield").val(ui.item.rnm);
            $("#corrStreet").val(ui.item.street);
            $("#corrStreet").attr("readonly",true);
            $("#corrHouseNumber").val(ui.item.houseNumber);
            $("#corrHouseNumber").attr("readonly",true);
            $("#corrCity").val(ui.item.city);
            $("#corrCity").attr("readonly",true);
            $("#corrPostalCode").val(ui.item.postalCode);
            $("#corrPostalCode").attr("readonly",true);
            $("#corrStateprovince").val(ui.item.stateprovince);
            $("#corrStateprovince").attr("readonly",true);
            
            // set imported form field
            //$("#importedAddress").attr("value", "true");
            
            return false;
        }, 100);
    });
});


function handleDisplayOfCorrAddressTable(object)
{
    if ($(object).data("autocomplete") == null)
    {
        // autocomplete not enabled
        return;
    }
    $(object).data("autocomplete")._renderItem = function (ul, item)
    {
        return $("<li></li>")
            .data("item.autocomplete", item)
            .append("<a><span class='two-col'><span class='selectable-col'><span class='first-col'>" + item.street + "</span>" +
            "<span class='second-col'>" + item.city + "</span></span></span></a>").appendTo(ul);
//            .append("<div><a>" + item.aid + "<br>" + item.anm + ", " + item.anc + "</a></div>")
    }
}

function setEditableCorrFields()
{
	//$("#corrStreet").val("");
    $("#corrStreet").attr("readonly", false);
    $("#corrStreet").removeClass("readonlyImport");
    
    //$("#corrHouseNumber").val("");
    $("#corrHouseNumber").attr("readonly", false);
    $("#corrHouseNumber").removeClass("readonlyImport");
    
    //$("#corrCity").val("");
    $("#corrCity").attr("readonly", false);
    $("#corrCity").removeClass("readonlyImport");
    
    //$("#corrPostalCode").val("");       
    $("#corrPostalCode").attr("readonly", false);
    $("#corrPostalCode").removeClass("readonlyImport");
    
    //$("#corrStateprovince").val("");
    $("#corrStateprovince").attr("readonly", false);
    $("#corrStateprovince").removeClass("readonlyImport");
}

function setNotEditableCorrFields()
{
	$("#corrStreet").val("");
	$("#corrStreet").attr("readonly", true);
	$("#corrStreet").addClass("readonlyImport");
	
    $("#corrHouseNumber").val("");
    $("#corrHouseNumber").attr("readonly", true);
    $("#corrHouseNumber").addClass("readonlyImport");
    
    $("#corrCity").val("");
    $("#corrCity").attr("readonly", true);
    $("#corrCity").addClass("readonlyImport");
    
    $("#corrPostalCode").val("");       
    $("#corrPostalCode").attr("readonly", true);
    $("#corrPostalCode").addClass("readonlyImport");
    
    $("#corrStateprovince").val("");
    $("#corrStateprovince").attr("readonly", true);
    $("#corrStateprovince").addClass("readonlyImport");
}
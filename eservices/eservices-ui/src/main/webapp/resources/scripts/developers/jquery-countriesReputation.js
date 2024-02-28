var repCs = new RepCs();

function RepCs() {

	
	this.removeCountry=removeCountry;
	this.containsCountries=containsCountries;
	this.removeAllCountries=removeAllCountries;
	this.addCountryRep=addCountryRep;
	

	function isDataSaved() {
		return dataSaved;
	}
		
	function getNumberOfCountries() {
		var size = 0;
		for (var key in selectedCountries) {
			size++;
		}
		$(".count-countries").html(size);
		return size;
	}	
	

	

}

	function addCountryRep(uniqueItem){
		var country="";
		var str="";
		if (!$("#divManualCountries").is(":hidden")) {
			country=$("#selectcountriesManual :selected").attr("label");
		}
		if (uniqueItem) {
			country=$("#inputCountries").val();
			$("#inputCountries").val("");
		}
		if (!$("#divEUCountries").is(":hidden")) {
			country=$("#repcountriesEU  :selected").text();
		}
		if (!$("#divAllCountries").is(":hidden")) {
			country=$("#repcountries :selected").text();
		}
		
		str = $("#inputCountries").val();
		if (!containsCountries(country,str)){
			if (str == "" || str==undefined || str==null){
				str=country;
			}
			else
				{
				str=str+";"+country;
				}
			
			$("#inputCountries").val(str);
			
			
			$("#divReputationCountries").refreshRC({scroll: false, uniqueItem: uniqueItem});
		}
	}
	
	function loadEditCountries(uniqueItem){
		
		$("#divReputationCountries").refreshRC({scroll: false, uniqueItem: uniqueItem});
	}

	function RepC(value, code) {
		this.value = value;
		this.code = code;
	}
	
	function removeAllCountries(edit){
			if (!edit){
				$("#inputCountries").val("");
			}
		$("#divCountries").html("");
	}
	
	function containsCountries(country,str) {
		if (str=="" || str==undefined || str==null)
			{
				return false;
			}
		else{
			var listCountries = str.split(";");
			var exists = false;
			for (var i=0; i<listCountries.length; i++) {
		        if (listCountries[i].match(country)) exists = true;
		    }
			return exists;
		}
		
	}
	
	
	function removeCountry(country, code) {

		var str = $("#inputCountries").val();
		var listStrings = str.split(";");
		var strFinal = "";
		var added = false;
		for (var i=0;i<listStrings.length;i++){
			if (listStrings[i]!=country)
				{
					if (added){
						strFinal=strFinal +";"+ listStrings[i];
					}
					else{
						strFinal=strFinal + listStrings[i];
						added=true;
					}
				}
				
		}
		
		
		
		$("#inputCountries").val(strFinal);
		$("#divReputationCountries").refreshRC();
		//borrar de inputCountries.
}

$(".country-list .country-close").live('click', function (e) {
	var code = $(this).closest("li").find(".id").html();
	var country = $(this).closest("li").find(".name").html();
	
	removeCountry(country, code);
});






$(".add-countries-button").live("click", function(event) {
	
	addCountryRep(false);
	//repCs = new RepCs();
});

/**
//* Refreshs the section of Goods and Services providing the GS selected for the given
* language
*/
jQuery.fn.refreshRC = function() {
	var args = arguments[0] || {}; // It's your object of arguments
	var invoker = $(this);
	var scroll = args.scroll;
	var uniqueItem = args.uniqueItem;
	
	
	if (scroll) {
		var position = $(this).offset().top-50;
		$('html, body').animate({scrollTop:position}, 'slow');
	}
	
	if ($(invoker).exists()) {

		var initiator = $(invoker).find("div");
		
		
		
		
		$(invoker).find(".notTemplateRep").remove();
		var listTemp = $("#inputCountries").val();
		
		if (listTemp != "") {
		var countries = listTemp.split(';');
		

//			$("#divTemplate").hide();
			var cloned;
			var divTemplate="";
			if (uniqueItem) 
				divTemplate="divTemplateNotRemovable";
			else
				divTemplate="divTemplate";
			cloned=$("#"+divTemplate ).clone();
			$(cloned).find(".country-list").find("li").remove();
			for (var i=0;i<countries.length;i++) {
					var createdCountry = $("#"+divTemplate ).find("li.country-valid").clone();
					$(createdCountry).find(".name").html(countries[i]);
					$(createdCountry).appendTo($(cloned).find(".country-list"));
				
			}
			
			$(cloned).removeClass("templateRep");
			$(cloned).addClass("notTemplateRep");
			$(cloned).appendTo(initiator);
			$(cloned).show();
//			$("#divTemplate").show();
			
			
		
		}


		
	}
};


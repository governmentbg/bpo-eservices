var repCs = new RepCs();

function RepCs() {

	

	this.removeCountry=removeCountry;
	

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
	

	function containsCountries(country) {
		return (selectedCountries[country]!=null?true:false);
	}
}

	function RepC(value, code) {
		this.value = value;
		this.code = code;
	}
	
	
	function removeCountry(country, code) {
		var str = "country=" + country + "&code=" + code;
		$("#divReputationCountries").refreshRC();
		//borrar de inputCountry.
}


$(".country-list:not(.notRemovableClass) .country-close").live('click', function (e) {
	var code = $(this).closest("li").find(".id").html();
	var country = $(this).closest("li").find(".name").html();

	removeCountry(country, code);
});





$(".add-countries-button").live("click", function(event) {
	var country=$("#repcountries :selected").text();
	var code=$("#repcountries :selected").val();
	var str = $("#inputCountry").val();
	str=str+country+";";
	
	$("#divReputationCountries").refreshRC({scroll: true});
	
	//repCs = new RepCs();
});



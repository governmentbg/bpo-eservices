'use strict';

pt.efiling.application.entitlement = {};

!function() {
	
	function EntitlementKind(idDiv) {
		this.idDiv = idDiv;
	}
	
	EntitlementKind.prototype.hideDiv = function() {
		if (!!this.idDiv) {
			$('#' + this.idDiv).hide();
		}
	};
	
	EntitlementKind.prototype.showDiv = function() {
		if (!!this.idDiv) {
			$('#' + this.idDiv).show();
		}
	};
	
	var options = [];
	
	var manipulations = {
		clickInOption: function() {
			if ($(this).is(':checked')) {
				var optionChecked = this;
				$('input[type="checkbox"][id^="entitlement_"]').each(function(index, option){
					if (optionChecked.id != option.id) {
						$(option).prop('checked', false);
						options[option.id].hideDiv();
					} else {
						options[option.id].showDiv();
					}
				});
			} else {
				options[this.id].hideDiv();
			}
		}
	};

	var initializations = {
		initOptions: function() {
			options['entitlement_patentOfficiary'] = new EntitlementKind('entitlement_patentOfficiary_div');
			options['entitlement_patentNotOfficiary'] = new EntitlementKind('entitlement_patentNotOfficiary_div');
			options['entitlement_transferContract'] = new EntitlementKind('entitlement_transferContract_div');
			options['entitlement_otherGrounds'] = new EntitlementKind('entitlement_otherGrounds_div');
		},
		initCheckboxes: function() {
			$('input[type="checkbox"][id^="entitlement_"]').click(manipulations.clickInOption);
		}
	};
	
	this.init = function() {
		fspLog('   init entitlement to apply...');
		initializations.initOptions();
		initializations.initCheckboxes();
		fspLog('   ...entitlement to apply initiated');
	};

}.apply(pt.efiling.application.entitlement);


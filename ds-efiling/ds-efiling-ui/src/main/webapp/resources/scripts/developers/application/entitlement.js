'use strict';

ds.efiling.application.entitlement = {};

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
			options['entitlement_designOfficiary'] = new EntitlementKind('entitlement_designOfficiary_div');
			options['entitlement_designNotOfficiary'] = new EntitlementKind('entitlement_designNotOfficiary_div');
			options['entitlement_notApplicantsWithWaived'] = new EntitlementKind();
			options['entitlement_dueToSucession'] = new EntitlementKind();
			options['entitlement_accordingToAssociationToCompany'] = new EntitlementKind();
			options['entitlement_transferOfRights'] = new EntitlementKind('entitlement_transferOfRights_div');
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

}.apply(ds.efiling.application.entitlement);


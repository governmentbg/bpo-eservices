/*global Locarno*/
'use strict';
var MyTerms = {
	locarnoClasses: $('#locarnoModalEnterMyTerms select#locarnoClasses'),
	locarnoSubclasses: $('#locarnoModalEnterMyTerms select#locarnoSubclasses'),
    setErrorMessage: function(responseHtml) {
        $('#locarnoModalEnterMyTerms .genericErrorMyTerms').html(responseHtml);
    },
    getClassCode: function() {
        return MyTerms.locarnoClasses.val();
    },
    getClassText: function() {
        return $('option:selected', MyTerms.locarnoClasses).html();
    },
    getSubclassCode: function() {
        return MyTerms.locarnoSubclasses.val();
    },
    getProductIndicationTerms: function() {
        return $('#locarnoModalEnterMyTerms .enterMyProductIndications').val();
    },
    acceptMyOwnTerms: function(e) {
        MyTerms.cleanMyTermsErrors();
        var classCode = MyTerms.getClassCode();
        var subclassCode = MyTerms.getSubclassCode();
        var firstLang = $('#firstLang').val() || 'en';
        var separator = ';';
        if ( firstLang === 'el') {
            separator = '·';
        }
        var productIndicationTerms = MyTerms.getProductIndicationTerms();
        productIndicationTerms = _.compact(productIndicationTerms.split(separator));
        if (MyTerms.validate(classCode, subclassCode, productIndicationTerms)) {
            Locarno.ajax.addNewProduct(function(responseHtml) {
                if (_.size($(responseHtml).find('.flMessageError')) !== 0) {
                    MyTerms.setErrorMessage(responseHtml);
                } else {
                    Locarno.setResponseInTable(responseHtml);
					$('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
                    $('#locarnoModalEnterMyTerms').modal('hide');
                }
            });
        } else {
            e.preventDefault();
            e.stopPropagation();
        }
    },

    cleanInputMyTerms: function() {
        $('#locarnoModalEnterMyTerms .enterMyProductIndications').val('');
       	MyTerms.locarnoClasses.val('-1');
       	MyTerms.locarnoSubclasses.val('-1');
    },
    validate: function(classCode, subclassCode, productIndicationTerms) {
        var isValid = true;
        if (Locarno.isTermsMaxReached(productIndicationTerms.length)) {
            $('#locarnoModalEnterMyTerms .myTermsErrorTermsExceed').show();
            isValid = false;
        } else if (classCode === '-1') {
            $('#locarnoModalEnterMyTerms .myTermsErrorNotAllowedClass').show();
            isValid = false;
        } else if (subclassCode === '-1') {
            $('#locarnoModalEnterMyTerms .myTermsErrorClassesNotChoosen').show();
            isValid = false;
        } else if (productIndicationTerms.length === 0) {
            $('#locarnoModalEnterMyTerms .myTermsErrorTermsEmpty').show();
            isValid = false;
        }
        return isValid;
    },
    cleanMyTermsErrors: function() {
        MyTerms.setErrorMessage('');
        $('#locarnoModalEnterMyTerms .myTermsErrorTermsExceed').hide();
        $('#locarnoModalEnterMyTerms .myTermsErrorClassesNotChoosen').hide();
        $('#locarnoModalEnterMyTerms .myTermsErrorTermsEmpty').hide();
        $('#locarnoModalEnterMyTerms .myTermsErrorNotAllowedClass').hide();
    },
    selectFirstOption: function(select) {
    	$(select).val($('option[value!=-1]:first', select).attr('value'));
    }
};

var MyComplexTerms = {
    locarnoComplexClasses: $('#locarnoModalEnterMyComplexTerms select#complexLocarnoClass'),
    locarnoComplexSubclasses: $('#locarnoModalEnterMyComplexTerms select#complexLocarnoSubClass'),
    setErrorMessage: function(responseHtml) {
        $('#locarnoModalEnterMyComplexTerms .genericErrorMyTerms').html(responseHtml);
    },
    getClassCode: function() {
        return MyComplexTerms.locarnoComplexClasses.val();
    },
    getClassText: function() {
        return $('option:selected', MyComplexTerms.locarnoComplexClasses).html();
    },
    getSubclassCode: function() {
        return MyComplexTerms.locarnoComplexSubclasses.val();
    },
    getProductIndicationTerms: function() {
        return $('#locarnoModalEnterMyComplexTerms #enterMyComplexProductIndications').val();
    },
    acceptMyOwnTerms: function(e) {
        MyComplexTerms.cleanMyTermsErrors();
        var productIndicationTerms = MyComplexTerms.getProductIndicationTerms();
        var locarnoClasses = $("#locarnoModalEnterMyComplexTerms input[name=classes]");
        var type = $("#locarnoModalEnterMyComplexTerms input[name=type]:checked");
        if (MyComplexTerms.validate(locarnoClasses, type, productIndicationTerms)) {
            Locarno.ajax.addNewComplexProduct(function(responseHtml) {
                if (_.size($(responseHtml).find('.flMessageError')) !== 0) {
                    MyComplexTerms.setErrorMessage(responseHtml);
                } else {
                    Locarno.setResponseInTable(responseHtml);
                    $('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
                    $('#locarnoModalEnterMyComplexTerms').modal('hide');
                }
            });
        } else {
            e.preventDefault();
            e.stopPropagation();
        }
    },

    cleanInputMyTerms: function() {
        MyComplexTerms.locarnoComplexClasses.val('-1');
        MyComplexTerms.locarnoComplexSubclasses.val('-1');

        $('#locarnoModalEnterMyComplexTerms #listComplexLocarnoClasses').html("");
        $('#locarnoModalEnterMyComplexTerms input[name=type]').attr("checked", false);
        $('#locarnoModalEnterMyComplexTerms input[name=indication]').val("");
    },
    validate: function(classes, type, productIndicationTerms) {
        var isValid = true;
        if (classes.length == 0) {
            $('#locarnoModalEnterMyComplexTerms .myTermsErrorClassesNotChosen').show();
            isValid = false;
        } else if (type.length ==0 ) {
            $('#locarnoModalEnterMyComplexTerms .myTermsErrorTypeNotChosen').show();
            isValid = false;
        } else if (productIndicationTerms.length === 0) {
            $('#locarnoModalEnterMyComplexTerms .myTermsErrorTermsEmpty').show();
            isValid = false;
        }
        return isValid;
    },
    cleanMyTermsErrors: function() {
        MyComplexTerms.setErrorMessage('');
        $('#locarnoModalEnterMyComplexTerms .myTermsErrorClassesNotChosen').hide();
        $('#locarnoModalEnterMyComplexTerms .myTermsErrorTypeNotChosen').hide();
        $('#locarnoModalEnterMyComplexTerms .myTermsErrorTermsEmpty').hide();
    },
    selectFirstOption: function(select) {
        $(select).val($('option[value!=-1]:first', select).attr('value'));
    },
    appendLocarnoComplexProductClass: function() {
        var classValue = MyComplexTerms.locarnoComplexClasses.val();
        if (classValue != '-1') {
            var subclassValue = MyComplexTerms.locarnoComplexSubclasses.val();
            if (subclassValue != '-1') {
                var classAndSubclass = classValue + "-" + subclassValue;
                var input = "<input type='hidden' name='classes' value='" + classAndSubclass + "' />";
                var li = "<li>" + input + classAndSubclass + "<a class='remove-icon'></a></li>";
                $('#listComplexLocarnoClasses:last').append(li);
            }
        }
    },
};

$('#locarnoModalEnterMyTerms').on('click', '.acceptMyOwnTerms', MyTerms.acceptMyOwnTerms);
$(document).on('show.bs.modal','#locarnoModalEnterMyTerms', function() {
    MyTerms.cleanMyTermsErrors();
	var callback = function() {
		MyTerms.selectFirstOption(MyTerms.locarnoClasses);
		Locarno.ajax.loadLocarnoSubclassCodes(MyTerms.locarnoClasses.val(), MyTerms.locarnoSubclasses, function() {
			MyTerms.selectFirstOption(MyTerms.locarnoSubclasses);
			$('#locarnoModalEnterMyTerms .enterMyProductIndications').prop('disabled', false);
		});
	};
	$('#locarnoModalEnterMyTerms .enterMyProductIndications').prop('disabled', true);
	Locarno.ajax.loadLocarnoClassCodes(MyTerms.locarnoClasses, callback);
});
$(document).on('hide.bs.modal','#locarnoModalEnterMyTerms', function() {
    MyTerms.cleanInputMyTerms();
    MyTerms.cleanMyTermsErrors();
});
MyTerms.locarnoClasses.on('change', function(e) {
    Locarno.ajax.loadLocarnoSubclassCodes(e.target.value, MyTerms.locarnoSubclasses, function() {
    	MyTerms.selectFirstOption(MyTerms.locarnoSubclasses);
    });
});

$('#locarnoModalEnterMyComplexTerms').on('click', '.acceptMyOwnComplexTerms', MyComplexTerms.acceptMyOwnTerms);
$(document).on('show.bs.modal','#locarnoModalEnterMyComplexTerms', function() {
    MyComplexTerms.cleanMyTermsErrors();
    MyComplexTerms.cleanInputMyTerms();
    Locarno.ajax.loadLocarnoClassCodes(MyComplexTerms.locarnoComplexClasses, null);
});
$(document).on('hide.bs.modal','#locarnoModalEnterMyComplexTerms', function() {
    MyComplexTerms.cleanMyTermsErrors();
    MyComplexTerms.cleanInputMyTerms();
});
MyComplexTerms.locarnoComplexClasses.on('change', function(e) {
    Locarno.ajax.loadLocarnoSubclassCodes(e.target.value, MyComplexTerms.locarnoComplexSubclasses, function() {
        MyComplexTerms.selectFirstOption(MyComplexTerms.locarnoComplexSubclasses);
    });
});
$(document).on('click', '#addComplexLocarnoClass', MyComplexTerms.appendLocarnoComplexProductClass);

$(document).on('click', '#listComplexLocarnoClasses a[class="remove-icon"]', function() {
    $(this).closest('li').remove();
});

jQuery.fn.exists = function(){return this.length>0;}

jQuery.fn.editableTerm = function(langId) {
	var args = arguments[0] || {};
	var $editables = $(this);
	var oldVal = '';
	$editables.on('click', function () {
		var $this = $(this);
		oldVal=$this.html();
		if($this.hasClass('editing')) {return false;};
		$this.attr('contenteditable','true').addClass('editing');
		$this.focus();
		$this.unbind('blur');
				
		$this.blur(fireEvent);
		$this.keypress(function(e){
			if(e.keyCode === 13){
				e.preventDefault();				
				$this.trigger('blur');
				return false;
			}
			return true;
		});

		function fireEvent(){
			var currentName = $this.html().replace(/&nbsp;/g, '');
			var locarnoId = $(this).attr('data-val');
			var data = {
				id: locarnoId,
				newDescription: currentName
			};
			$this.attr('contenteditable','false');
			$this.removeClass('editing');
			var tmpname = currentName;
			if(tmpname && tmpname.length > 60){
				tmpname = tmpname.substring(0,60)+'...';
			}
			$this.html(tmpname);
			$this.parent().addClass('loading-element-before');

			if(oldVal!==currentName){
				renameTerm(data,function(result){
					$this.parent().removeClass('loading-element-before');
					Locarno.setResponseInTable(result);
					$('#productIndicationTable td .name.editable').editableTerm($('#firstLang').val());
				});	
			}else{
				$this.parent().removeClass('loading-element-before');
			}	
		}
	});
	
	
	function renameTerm(data, onsuccess) {
		$.ajax({
			url: 'editTerm.htm',
			type: 'POST',
			data: data,
			cache: false,
			async: true,
			success: function(result) {
				onsuccess(result);
			}
		});
	}
};

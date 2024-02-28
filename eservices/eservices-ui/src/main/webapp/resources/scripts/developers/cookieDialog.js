/*
 * file:cookieDialog.js
 */

var defaultCookieDomain = window.location.hostname;

var $cookieDialog;

function getCookieDialog() {
	if ($cookieDialog == null) {
		$cookieDialog = $('#cookie-dialog');
	}
	return $cookieDialog;
}

$(document).ready(function(){
	
	var currentCookie = $.cookie('cookieSP');
	var cookieDialogPath = getCookieDialog().attr("cookieDialogPath");
	getCookieDialog().hide();	
	if (currentCookie == null && cookieDialogPath != null) {
		$.ajax({
			"url": cookieDialogPath,
			"async": true,
			"type": "GET",
			"contentType": "text/html",
			"success": function(responseHtml) {				
				if ( responseHtml.match(/<div class="cookie-text">/i)) {
					getCookieDialog().html(responseHtml);
					getCookieDialog().show();
				}

			}
		});
	}
	
});

function allowCookie(allow) {
		
	$.cookie('cookieSP', allow.toString(), { expires : 730 , path: '/', domain: defaultCookieDomain });
	if (allow) {
		location.reload(true);
	} else {
		getCookieDialog().hide('slow');
		getCookieDialog().remove('slow');		
	}
}
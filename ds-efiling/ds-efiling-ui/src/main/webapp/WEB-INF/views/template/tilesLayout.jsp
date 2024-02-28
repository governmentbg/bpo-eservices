<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="/WEB-INF/tags/component/fsp-tags.tld" prefix="tags" %>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="ie7" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="ie8" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
	<head>
	    <meta charset="UTF-8">
	    <title><tiles:insertAttribute name="title" ignore="true"/></title>
 		
		<meta name="_csrf" id="_csrf" content="${_csrf.token}"/>
		<!-- default header name is X-CSRF-TOKEN -->
		<meta name="_csrf_header" id="_csrf_header" content="${_csrf.headerName}"/>
 		
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">

		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/OpenSansOnly.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fsp.css">
		<!-- DS Class Integration Changes Start -->
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fwork-styles.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/locarno.css">
		<!-- DS Class Integration Changes End -->
	    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/ext.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/datepicker.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gallery/blueimp-gallery.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gallery/blueimp-gallery-indicator.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gallery/blueimp-gallery-video.css">

        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.11.3.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-migrate-1.4.1.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-ui-1.8.23.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.cookie.js"></script>
                
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.validate.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.validate.internationalization.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.position.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/additional-methods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.treeview.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/bootstrap.js"></script>
		<!-- DS Class Integration Changes Start -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/underscore-min.js"></script>
		<!-- DS Class Integration Changes End -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/load-image.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/moment.min.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/blueimp-helper.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/blueimp-gallery.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/blueimp-gallery-fullscreen.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/blueimp-gallery-indicator.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/blueimp-gallery-video.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/blueimp-gallery-vimeo.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/blueimp-gallery-youtube.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gallery/jquery.blueimp-gallery.js"></script>

        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/bootstrap-datepicker-gh.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/bootstrap3-typeahead.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/dateFunctions.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.i18n.properties.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/cookieDialog.js"></script>	    
	    
  		<script type="text/javascript">
			$(document).ready(function() {
				ds.efiling.application.init({oneForm: true});
			});
		</script>
		
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/app.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/fees.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/common.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/person.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/designApplication.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/ajax.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/entitlement.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/languages.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/divisionalApplication.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/designsLink.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/claimsWiz.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/applicant.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/representative.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/fileupload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/dragdrop_fileupload.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/review.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/reference.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/designs.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/designsViews.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/importDesign.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/locarno.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/designer.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/applicationCA.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/signature_multiple.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/fasttrack.js"></script>
        
	    <script type="text/javascript">
	        $.ajaxPrefilter(function (options)
	        {
	            if (options.dataType != "json" && options.dataType != "iframe json")
	            {
	                options.data = options.data + "&flowKey=<%= request.getParameter("execution") %>";
	            } 
	        });
            $.ajaxSetup({cache: false});
	    </script>

	    <script type="text/javascript">
		$(function () {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
            if (header != "") {
                $(document).ajaxSend(function(e, xhr, options) {
                    xhr.setRequestHeader(header, token);
                });
            }

		 });			
		</script>

		<script>
			var i18nApplicationMessages = <tags:messages />;
		</script>

        <%--VERY IMPORTANT FOR IE8 COMPATIBILITY DO NOT REMOVE--%>
        <!-- Make ie recognize html5 elements -->
        <!--[if lt IE 9]>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/html5.js"></script>
        <![endif]-->
        <%--END OF > VERY IMPORTANT FOR IE8 COMPATIBILITY DO NOT REMOVE--%>




	</head>
	
	<body id="normal" data-spy="scroll" data-target="aside" class="specificClass">
        <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
        <span id="dateFormat" class="hidden">${dateFormat}</span>

		<!-- remove this hardcoded values -->
		<span class="modal fade" id="ajaxSearchResults">
		</span>
		<!-- remove this hardcoded values -->
		<div id="ajaxBlock" class="ajaxBlockDiv"></div>
	
		<jsp:include page="/WEB-INF/views/template/modal_message.jsp"/>

		<!-- DS Class Integration Changes Start -->
		<jsp:include page="/WEB-INF/views/locarno/dsclass/locarno_Modal.jsp"/>
		<!-- DS Class Integration Changes End -->
		
		<!-- European Cookie -->
		<div id="cookie-dialog" style="display:none" cookieDialogPath="<%=request.getContextPath()%>/resources/cookie/cookie-dialog.html"></div>
			

        <tiles:insertAttribute name="header"/>

	<div class="main">
			<input type="hidden" id="storedFirstLang"
				value="${flowBean.firstLang}" />
			<tiles:insertAttribute name="main" />
	</div>

	<tiles:insertAttribute name="footer"/>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/dropzone.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/tmpl.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/vendor/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.fileupload.js"></script>

		<!-- DS Class Integration Changes Start -->
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/locarnoAddPI.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/locarnoModal.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/locarnoTermsEdit.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/modalLocarnoBrowser.js"></script>

		<!-- DS Class Integration Changes End  -->
	</body>
</html>
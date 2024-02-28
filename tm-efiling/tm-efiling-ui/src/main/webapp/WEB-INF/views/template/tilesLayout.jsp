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
	    <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/ext.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/fsp.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/datepicker.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gallery/blueimp-gallery.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gallery/blueimp-gallery-indicator.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gallery/blueimp-gallery-video.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gs.css">

        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-1.11.3.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-migrate-1.4.1.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery-ui-1.8.23.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.cookie.js"></script>
        
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.validate.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.validate.internationalization.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.position.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/additional-methods.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.treeview.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.dataTables.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/underscore-min.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/bootstrap.js"></script>
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
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/app.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/dateFunctions.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.i18n.properties.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/fees.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/marks.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/markdetails.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/colorpicker.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/ajax.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/marksViews.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/common.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/claims.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/colour.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/person.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/applicant.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/languages.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/fileupload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/dragdrop_fileupload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/termsedit.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/representative.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/priority.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/seniority.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/similarmark_common.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/similarmarks_oneform.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/transformation.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/exhibition.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/previousCTM.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/divisionalApplication.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/review.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/nationalSearchReport.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/reference.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/cookieDialog.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/applicationCA.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/addons.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/fasttrack.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/foreign_registration.js"></script>
       
	    <script type="text/javascript">
			$(document).ready(function() {
				tm.application.marks.views.init();
			});
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
			$(function() {
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
	
	<body id="normal" data-spy="scroll" data-target="aside">
        <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
        <span id="dateFormat" class="hidden">${dateFormat}</span>

		<!-- remove this hardcoded values -->
		<span class="modal fade" id="ajaxSearchResults">
		</span>
		<!-- remove this hardcoded values -->
		<div id="ajaxBlock" class="ajaxBlockDiv"></div>
	
		<jsp:include page="/WEB-INF/views/template/modal_message.jsp"/>
		
		<!-- European Cookie -->
		<div id="cookie-dialog" style="display:none" cookieDialogPath="<%=request.getContextPath()%>/resources/cookie/cookie-dialog.html"></div>		

        <tiles:insertAttribute name="header"/>

	<div class="main">
			<input type="hidden" id="storedFirstLang"
				value="${flowBean.firstLang}" />
			<tiles:insertAttribute name="main" />
	</div>

		<jsp:include page="/WEB-INF/views/goods_services_redesign/modal-gs.jsp"/>
		<jsp:include page="/WEB-INF/views/goods_services/terms-modal.jsp"/>
		<jsp:include page="/WEB-INF/views/goods_services_redesign/popOverClassInformation.jsp"/>
		<jsp:include page="/WEB-INF/views/goods_services_redesign/modal-gs-hdbInformation.jsp"/>

	<tiles:insertAttribute name="footer"/>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/goodsservices.js"></script>
	    <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/jquery-goodsservices.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/dropzone.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/tmpl.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.fileupload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gs/integration.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/gs/app.js"></script>
		<script type="text/javascript"
		        src="<%=request.getContextPath()%>/resources/scripts/vendor/jquery.ui.widget.js"></script>
		<script type="text/javascript"
		        src="<%=request.getContextPath()%>/resources/scripts/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.fileupload.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/vendor/xregexp.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/vendor/unicode-base.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/vendor/unicode-categories.js"></script>
	</body>
</html>

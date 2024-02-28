<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<!doctype html>
<!--[if lt IE 7]> <html class="ie6" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="ie7" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="ie8" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en">
<!--<![endif]-->
	<head>
	    <meta charset="utf-8">
	    <title><tiles:insertAttribute name="title" ignore="true"/></title>
	    <meta name="_csrf" id="_csrf" content="${_csrf.token}"/>
 		<!-- default header name is X-CSRF-TOKEN -->
 		<meta name="_csrf_header" id="_csrf_header" content="${_csrf.headerName}"/> 
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">

		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/OpenSansOnly.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/sp.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/ext.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/datepicker.css">

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
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/load-image.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/moment.min.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/bootstrap-datepicker-gh.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/bootstrap3-typeahead.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/dateFunctions.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.i18n.properties.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/cookieDialog.js"></script>

		<script type="text/javascript">
			$(document).ready(function() {
				pt.efiling.application.init({oneForm: true});
			});
		</script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/app.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/fees.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/common.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/person.js"></script>

		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/patentApplication.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/ajax.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/patentViews.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/languages.js"></script>
		<%--<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/entitlement.js"></script>--%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/patent.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/applicant.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/claims.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/representative.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/fileupload.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/review.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/inventor.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/applicationCA.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/signature_multiple.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/entitlement.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/application/declarations.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/developers/previousPatent.js"></script>

	    <script type="text/javascript">
			$.ajaxPrefilter(function (options)
			{
				if(options.url.indexOf('?')==-1) {
					options.url = options.url + "?flowKey=<%= request.getParameter("execution") %>";
				}else{
					options.url = options.url + "&flowKey=<%= request.getParameter("execution") %>";
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

        <%--VERY IMPORTANT FOR IE8 COMPATIBILITY DO NOT REMOVE--%>
        <!-- Make ie recognize html5 elements -->
        <!--[if lt IE 9]>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/html5.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/excanvas.js"></script>
        <![endif]-->
        <%--END OF > VERY IMPORTANT FOR IE8 COMPATIBILITY DO NOT REMOVE--%>

	</head>

	<body id="wizard" data-spy="scroll" data-target="aside" class="specificClass">
	
        <spring:eval var="dateFormat" expression="@propertyConfigurer.getProperty('iu.template.dateFormat')" />
        <span id="dateFormat" class="hidden">${dateFormat}</span>

		<span class="modal fade" id="ajaxSearchResults">
		</span>

		<div id="ajaxBlock" class="ajaxBlockDiv"></div>

		<jsp:include page="/WEB-INF/views/template/modal_message.jsp"/>
		
	
		<!-- European Cookie -->
		<div id="cookie-dialog" style="display:none" cookieDialogPath="<%=request.getContextPath()%>/resources/cookie/cookie-dialog.html"></div>
		  		

        <tiles:insertAttribute name="header"/>

	    <div class="main">
		   	<input type="hidden" id="storedFirstLang" value="${flowBean.firstLang}" />
		    <tiles:insertAttribute name="main"/>
	    </div>

		<tiles:insertAttribute name="footer"/>

		<script type="text/javascript">
            function getDomain(){
                var hostname = window.location.hostname;
                var dotLocation = hostname.indexOf(".");
                var domainname = hostname.substring(dotLocation);
                return domainname;
            }

            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
            var trackString = window.location.pathname.replace(/(\?.*)|(#.*)|(\!.*)/g, "");
            ga('create', 'UA-67521017-3', getDomain());
            $(function(){
                var hasLogged = false;
                $('.wizard-steps-analytics').each(function(index) {
                    if ($(this).attr('data-ignore-parent') && $(this).attr('data-ignore-parent') == 'true' ) {
                        ga('send', 'pageview', {
                            page: $(this).text() + '/${localeComponent.getLocale(pageContext.request).value}'
                        });
                    } else {
                        ga('send', 'pageview', {
                            page: trackString + $(this).text() + '/${localeComponent.getLocale(pageContext.request).value}'
                        });
                    } hasLogged = true;
                });
                if (!hasLogged) {
                    ga('send', 'pageview', {
                        page: trackString + '/${localeComponent.getLocale(pageContext.request).value}'
                    });
                }
            });
		</script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/tmpl.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/vendor/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.iframe-transport.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.fileupload.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.ui.mouse.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.ui.sortable.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/scripts/jquery.knob.js"></script>
	</body>
</html>
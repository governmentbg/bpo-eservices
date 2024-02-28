<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<component:file fileWrapperPath="${fileWrapperPath}" fileWrapper="${fileWrapper}" pathFilename="${fileWrapper.pathFilename}" helpMessageKey="${helpMessageKey}"/>

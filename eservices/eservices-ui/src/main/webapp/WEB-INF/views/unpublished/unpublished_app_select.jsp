<%--
  Created by IntelliJ IDEA.
  User: Raya
  Date: 2/2/2021
  Time: 5:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<component:generic path="selectUnpublished" checkRender="true">
    <button style="margin-top:10px" type="button" class="add-button fileinput-button unpublishedAppSelectButton ${param.objectType}" data-flow="${flowModeId}">
        <i class="list-icon"></i> <spring:message code="select.unpublished.application.${param.mainObject}"/>
    </button>

    <div id="unpublishedAppsModal" class="modal modal-standard fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <header>
                    <h1>
                        <spring:message code="select.unpublished.application.header"/>
                    </h1>
                    <a class="close-icon" data-dismiss="modal"></a>
                </header>
                <section class="modal-body">
                    <form class="form-inline" id="filterUnpublishedForm" style="display: none">
                        <div class="form-group">
                            <label for="unpublishedFilter"><spring:message code="select.unpublished.application.label.filter"/></label>
                            <input id="unpublishedFilter" type="text" class="form-control">
                            <button class="btn btn-default" id="unpublishedFilterBtn"><spring:message code="select.unpublished.application.btn.filter"/></button>
                        </div>
                    </form>
                    <div class="alert alert-info" id="noUnpublishedWarn" style="display: none">
                        <spring:message code="select.unpublished.application.no.results"/>
                    </div>
                    <table id="unpublishedAppsTable" class="table">
                        <thead>
                            <tr>
                                <th style="width: 20%"><spring:message code="unpublished.title.filingId"/></th>
                                <th style="width: 20%"><spring:message code="unpublished.title.applicationId"/></th>
                                <th class="opts" style="width: 50%"><spring:message code="unpublished.title.title"/></th>
                                <th class="opts" style="width: 10%"><spring:message code="unpublished.title.options"/></th>
                            </tr>
                        </thead>
                        <tbody>
                        <tr class="unpublishedTemplate" style="display: none">
                            <td class="dataFiling"></td>
                            <td class="dataId"></td>
                            <td class="dataTitle"></td>
                            <td>
                                <button class="btn btn-primary unpublishedImportBtn ${param.objectType}"><spring:message code="general.message.import"/></button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </section>
                <footer>
                    <ul class="controls">
                        <li><a class="btn btn-default" data-dismiss="modal"><spring:message code="layout.button.cancel"/></a></li>
                    </ul>
                </footer>
            </div>
        </div>
    </div>
</component:generic>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags/component" prefix="component" %>

<div id="viewImageModalDiv" class="modal fade messagePopup modal-patent-view-image">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1 id="modalViewImageTitle">
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>

			<section>
				<img alt="" src="" id="modalViewImageImg" class="modal-thumb" />
			</section>

			<footer>
				<ul>
					<li>
						<a data-dismiss="modal">
							<spring:message code="patent.view.image.button.close" />
						</a>
					</li>
				</ul>
			</footer>
		</div>
	</div>
</div>
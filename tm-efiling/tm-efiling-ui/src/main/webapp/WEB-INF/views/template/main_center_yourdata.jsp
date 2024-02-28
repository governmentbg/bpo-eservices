<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<h3 data-i18n="labels.yourdata"></h3>
<div class="flFormRow w85 alignCenter">
		<ul class="bigButtonsList">
				<li><a rel="" data-i18n="labels.applicanttxt"></a></li>
				<li><a rel="" data-i18n="labels.representativetxt" class="active"></a></li>
		</ul>
</div>

<div id="representativeList">
	<tiles:insertAttribute name="representative" />
</div>

<p class="">You are representing:</p>

<div id="applicantList">
		<tiles:insertAttribute name="applicant" />
</div>
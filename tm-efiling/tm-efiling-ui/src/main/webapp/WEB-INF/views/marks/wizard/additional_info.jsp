<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="moreword" class="modal fade modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1><spring:message code="mark.names.word" /></h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_1.png">
		<p>
			<spring:message code="mark.description.word.second" />
		</p>
	</div>
</div>
</div>
</div>

<div id="morefigurative" class="modal fade modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
		<h1>
			<spring:message code="mark.names.figurative" />
		</h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_2.png">

		<p>
			<spring:message code="mark.description.figurative.second" />
		</p>
	</div>
</div>
</div>
</div>

<div id="morefigword" class="modal fade modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1><spring:message code="mark.names.figurativeContainingWords" /></h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_3.png">

		<p>
			<spring:message code="mark.description.figurativeContainingWords.second" />
		</p>
	</div>
</div>
</div>
</div>

<div id="more3d" class="modal fade modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="mark.names.threeD" />
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_4.png">

		<p>
			<spring:message code="mark.description.threeD.second" />
		</p>
	</div>
</div>
</div>
</div>

<div id="morecolour" class="modal fade modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="mark.names.colour" />
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_5.png">

		<p>
			<spring:message code="mark.description.colour.second" />
		</p>
	</div>
</div>
</div>
</div>

<div id="moresound" class="modal fade modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="mark.names.sound" />
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_6.png">

		<p>
			<spring:message code="mark.description.sound.second" />
		</p>
	</div>
</div>
</div>
</div>

<div id="moreothers" class="modal fade modal-standard">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="mark.names.other" />
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_7.png">

		<p>
			<span><spring:message code="mark.description.other.second" /></span>
		</p>
	</div>
</div>
</div>
</div>

<div id="more3dword" class="modal fade modal-standard" style="display: none;">
<div class="modal-dialog">
<div class="modal-content">
    <header>
        <h1>
            <spring:message code="mark.names.threeDword" />
        </h1>
        <a class="close-icon" data-dismiss="modal"></a>
    </header>
	<div class="modal-body">
		<img
			src="<%=request.getContextPath()%>/resources/img/img_marktype_8.png">

		<p>
			<span><spring:message code="mark.description.threeDword.second" /></span>
		</p>
	</div>
</div>
</div>
</div>

<div id="moreShape" class="modal fade modal-standard" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.shape" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/img_marktype_8.png">

				<p>
					<span><spring:message code="mark.description.shape.second" /></span>
				</p>
			</div>
		</div>
	</div>
</div>

<div id="morePosition" class="modal fade modal-standard" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.position" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/Position.bw.png">

				<p>
					<span><spring:message code="mark.description.position.second" /></span>
				</p>
			</div>
		</div>
	</div>
</div>

<div id="moreMotion" class="modal fade modal-standard" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.motion" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/Motion.bw.png">

				<p>
					<span><spring:message code="mark.description.motion.second" /></span>
				</p>
			</div>
		</div>
	</div>
</div>

<div id="moreHologram" class="modal fade modal-standard" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.hologram" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/Hologram.bw.png">

				<p>
					<span><spring:message code="mark.description.hologram.second" /></span>
				</p>
			</div>
		</div>
	</div>
</div>

<div id="moreMultimedia" class="modal fade modal-standard" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.multimedia" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/Multimedia.bw.png">

				<p>
					<span><spring:message code="mark.description.multimedia.second" /></span>
				</p>
			</div>
		</div>
	</div>
</div>

<div id="morePattern" class="modal fade modal-standard" style="display: none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.pattern" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/Pattern.bw.png">

				<p>
					<span><spring:message code="mark.description.pattern.second" /></span>
				</p>
			</div>
		</div>
	</div>
</div>


<div id="moreGeoindication" class="modal fade modal-standard">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.geoindication" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/img_marktype_1.png">
				<p>
					<spring:message code="mark.description.geoindication.second" />
				</p>
			</div>
		</div>
	</div>
</div>

<div id="moreOriname" class="modal fade modal-standard">
	<div class="modal-dialog">
		<div class="modal-content">
			<header>
				<h1>
					<spring:message code="mark.names.oriname" />
				</h1>
				<a class="close-icon" data-dismiss="modal"></a>
			</header>
			<div class="modal-body">
				<img
						src="<%=request.getContextPath()%>/resources/img/img_marktype_1.png">
				<p>
					<spring:message code="mark.description.oriname.second" />
				</p>
			</div>
		</div>
	</div>
</div>
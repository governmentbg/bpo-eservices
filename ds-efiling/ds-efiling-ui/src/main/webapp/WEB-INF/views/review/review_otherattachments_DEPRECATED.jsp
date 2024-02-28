<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="section" id="otherAttach">
    <a id="otherAttachments" class="anchorLink"></a>

    <h3><spring:message code="review.otherAttachments.title"/></h3>

    <div class="row">

        <div class="span7">
            <label for="attachDescription1"><spring:message code="review.otherAttachments.description"/></label>
            <textarea id="attachDescription1" name="attachDescription1" class="" data-section="claim"></textarea>

        </div>
        <div class="span7 uploader" id="soundUploader">
            <div class="fileupload-buttonbar" id="uploader">
                <div class="dropZone">
                    <span><spring:message code="review.otherAttachments.dropZone"/></span>
                    <span class="note"><spring:message code="review.otherAttachments.dropZone.description"/></span>
                </div>
                <div class="navUploader"><em><spring:message code="review.otherAttachments.or"/></em>
        <span class="btn fileinput-button">
            <i class="icon-plus icon"></i>
            <span><spring:message code="review.otherAttachments.addfilesmanually"/></span>
            <input type="file" name="" multiple>
        </span>
                </div>
                <div class=" fileupload-progress fade">
                    <div class="progress progress-success progress-striped active" role="progressbar" aria-valuemin="0"
                         aria-valuemax="100">
                        <div class="bar" style="width:0%;"></div>
                    </div>
                    <div class="progress-extended"></div>
                </div>
            </div>
            <div class="fileupload-loading"></div>
            <div class="row fileupload-uploads">
                <div class="span3">
                    <table role="presentation" class="table table-striped">
                        <tbody class="files" data-toggle="modal-gallery" data-target="#modal-gallery"></tbody>
                    </table>
                </div>
            </div>

        </div>
    </div>
    <!--row-->
    <div class="noOhim">
        <label for="attachTrue1" class="checkbox">
            <input type="checkbox" id="attachTrue1" name="follow_1">
            <span data-i18n="labels.attachTrue"></span>
        </label>
        <label for="attachData1" class="checkbox">
            <input type="checkbox" id="attachData1" name="follow_1">
            <span data-i18n="labels.attachData"></span>
        </label>
    </div>
</div>
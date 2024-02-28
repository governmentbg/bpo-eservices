/*******************************************************************************
 * * $Id::                                                                       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.ui.tmefiling.adapter;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.adapter.design.ContactDetailsFactory;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.common.ui.form.resources.ColourForm;
import eu.ohim.sp.common.ui.form.resources.FileWrapper;
import eu.ohim.sp.common.ui.form.resources.StoredFile;
import eu.ohim.sp.common.ui.service.constant.AppConstants;
import eu.ohim.sp.core.domain.contact.ContactDetails;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.*;
import eu.ohim.sp.ui.tmefiling.flow.FlowBeanImpl;
import eu.ohim.sp.ui.tmefiling.form.MainForm;
import eu.ohim.sp.ui.tmefiling.util.FactoryUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MainFormFactory implements UIFactory<TradeMark, FlowBeanImpl> {

	@Autowired
	private ListAttachedDocumentFactory listAttachedDocumentFactory;

	@Autowired
	private ContactDetailsFactory contactDetailsFactory;

	/**
	 *
	 * @param corressAddress
	 * @param core
	 */
	private void correspondanceAddressFormToCore(List<ApplicationCAForm> corressAddress, TradeMark core) {

		core.setContactDetails(
				corressAddress.stream().map(a -> contactDetailsFactory.convertTo(a.getCorrespondenceAddressForm()))
						.collect(Collectors.toList()));
	}

	/**
	 *
	 * @param contactsDetails
	 * @param form
	 */
	private void coreToCorrespondanceAddressForm(List<ContactDetails> contactsDetails, FlowBeanImpl form) {
		if (contactsDetails == null || CollectionUtils.isEmpty(contactsDetails))
			return;

		contactsDetails.stream().forEach(c -> {
			ApplicationCAForm appCAForm = new ApplicationCAForm();
			appCAForm.setCorrespondenceAddressForm(contactDetailsFactory.convertFrom(c));
			form.addObject(appCAForm);
		});
	}

	@Override
	public TradeMark convertTo(FlowBeanImpl flowBean) {
		MainForm mainForm = flowBean.getMainForm();

		if (mainForm == null) {
			return new TradeMark();
		}

		TradeMark mark = new TradeMark();

		mark = addMarkDescriptions(flowBean, mainForm, mark);
		mark = addMarkDisclaimers(flowBean, mainForm, mark);

		mark.setGoodsFactorsDescription(mainForm.getGoodsFactorsDescription());
		mark.setGoodsCharacteristicsDescription(mainForm.getGoodsCharacteristicsDescription());
		mark.setGoodsGeographyDescription(mainForm.getGoodsGeographyDescription());
		mark.setGoodsProductionDescription(mainForm.getGoodsProductionDescription());

		mark.setMarkKind(getMarkType(mainForm.getMarkType()));
		WordSpecification wordSpecification = new WordSpecification();
		wordSpecification.setCharacterSet(mainForm.getCharset());
		wordSpecification.setTranscriptionDetails(mainForm.getTranscriptionDetails());
		wordSpecification.setTransliterationDetails(mainForm.getTranscriptionDetails());
		wordSpecification.setWordElements(mainForm.getWordRepresentation());
		mark.setWordSpecifications(new ArrayList<WordSpecification>());
		mark.getWordSpecifications().add(wordSpecification);
		correspondanceAddressFormToCore(mainForm.getCorrespondanceAddresses(), mark);

		boolean addImageSpecification = false;
		ImageSpecification imageSpecification = new ImageSpecification();

		if (mainForm.getColourList() != null && mainForm.getColourList().size()>0) {
			imageSpecification.setColours(new ArrayList<Colour>());
			addImageSpecification = true;

			imageSpecification.getColours().addAll(mainForm.getColourList().stream().map(colourForm -> {
				Colour colour = new Colour();
				if (!colourForm.getFormat().equalsIgnoreCase("DESCRIPTION")) {
					colour.setFormat(colourForm.getFormat());
				}
				colour.setValue(colourForm.getValue());
				return colour;
			}).collect(Collectors.toList()));

		}

		imageSpecification.setColourClaimedIndicator(mainForm.getColourChecked());

		if (!mainForm.getFileWrapperImage().getStoredFiles().isEmpty()) {
			addImageSpecification = true;
			imageSpecification.setRepresentation(new Document());
			imageSpecification.setRepresentation(
					listAttachedDocumentFactory.convertTo(mainForm.getFileWrapperImage()).get(0).getDocument());
		}

		if (addImageSpecification) {
			mark.setImageSpecifications(new ArrayList<ImageSpecification>());
			mark.getImageSpecifications().add(imageSpecification);
		}

		if (!mainForm.getFileWrapperMultimedia().getStoredFiles().isEmpty()) {
			MediaRepresentation multimedia = new MediaRepresentation();
			multimedia.setRepresentation(new Document());
			multimedia.setRepresentation(
					listAttachedDocumentFactory.convertTo(mainForm.getFileWrapperMultimedia()).get(0).getDocument());
			mark.setMediaRepresentations(new ArrayList<>());
			mark.getMediaRepresentations().add(multimedia);
		}

		if (!mainForm.getSoundFile().getStoredFiles().isEmpty()) {
			SoundSpecification soundSpecification = new SoundSpecification();
			soundSpecification
					.setDocument(listAttachedDocumentFactory.convertTo(mainForm.getSoundFile()).get(0).getDocument());
			mark.setSoundRepresentations(new ArrayList<SoundSpecification>());
			mark.getSoundRepresentations().add(soundSpecification);
		}

		if (mainForm.getCollectiveMark() != null && mainForm.getCollectiveMark()) {
			mark.setMarkRightKind(MarkKind.COLLECTIVE);
		} else if(mainForm.getCertificateMark() != null && mainForm.getCertificateMark()){
			mark.setMarkRightKind(MarkKind.CERTIFICATE);
		} else {
			mark.setMarkRightKind(MarkKind.INDIVIDUAL);
		}

		mark.setSeriesIndicator(mainForm.getSeriesPresent());
		mark.setSeriesNumber(mainForm.getSeriesNumber());

		mark.setTradeMarkDocuments(new ArrayList<AttachedDocument>());
		mark.getTradeMarkDocuments()
				.addAll(listAttachedDocumentFactory.convertTo(
						addTradeMarkDocumentsDescription(mainForm.getTrademarkRegulationDocuments(),"Trademark certificate attachment")));
		mark.getTradeMarkDocuments()
				.addAll(listAttachedDocumentFactory.convertTo(
						addTradeMarkDocumentsDescription(mainForm.getTrademarkTranslationDocuments(),"Trademark translation attachment")));
		mark.getTradeMarkDocuments()
				.addAll(listAttachedDocumentFactory.convertTo(
						addTradeMarkDocumentsDescription(mainForm.getTrademarkApplicantDocuments(),"Trademark application attachment")));

		return mark;
	}

	private TradeMark addMarkDescriptions(FlowBeanImpl flowBean, MainForm mainForm, TradeMark mark) {
		List<MarkDescription> markDescriptions = new ArrayList<MarkDescription>();
		if (StringUtils.isNotBlank(mainForm.getMarkDescription())) {
			MarkDescription markDescription = new MarkDescription();
			markDescription.setValue(mainForm.getMarkDescription());
			markDescription.setLanguage(flowBean.getFirstLang());
			markDescriptions.add(markDescription);
		}
		if (StringUtils.isNotBlank(mainForm.getMarkDescriptionSecond())) {
			MarkDescription markDescription = new MarkDescription();
			markDescription.setValue(mainForm.getMarkDescriptionSecond());
			markDescription.setLanguage(flowBean.getSecLang());
			markDescriptions.add(markDescription);
		}
		if (!markDescriptions.isEmpty()) {
			mark.setMarkDescriptions(markDescriptions);
		}
		return mark;
	}

	private TradeMark addMarkDisclaimers(FlowBeanImpl flowBean, MainForm mainForm, TradeMark mark) {
		List<MarkDisclaimer> markDisclaimers = new ArrayList<MarkDisclaimer>();
		if (StringUtils.isNotBlank(mainForm.getFirstDisclaimer())) {
			MarkDisclaimer markDisclaimer = new MarkDisclaimer();
			markDisclaimer.setValue(mainForm.getFirstDisclaimer());
			markDisclaimer.setLanguage(flowBean.getFirstLang());
			markDisclaimers.add(markDisclaimer);
		}
		if (StringUtils.isNotBlank(mainForm.getSecondDisclaimer())) {
			MarkDisclaimer markDisclaimer = new MarkDisclaimer();
			markDisclaimer.setValue(mainForm.getSecondDisclaimer());
			markDisclaimer.setLanguage(flowBean.getSecLang());
			markDisclaimers.add(markDisclaimer);
		}
		if (!markDisclaimers.isEmpty()) {
			mark.setMarkDisclaimers(markDisclaimers);
		}
		return mark;
	}

	// Added to avoid validation of documents without description
	private FileWrapper addTradeMarkDocumentsDescription(FileWrapper documents, String description){
		if(documents != null && documents.getStoredFiles() != null){
			for(StoredFile file : documents.getStoredFiles()){
				if(StringUtils.isEmpty(file.getDescription())){
					file.setDescription(description);
				}
			}
		}
		return documents;
	}

	@Override
	public FlowBeanImpl convertFrom(TradeMark core) {
		if (core == null) {
			return new FlowBeanImpl();
		}
		FlowBeanImpl flowBean = new FlowBeanImpl();

		MainForm form = flowBean.getMainForm();

		form.setGoodsFactorsDescription(core.getGoodsFactorsDescription());
		form.setGoodsCharacteristicsDescription(core.getGoodsCharacteristicsDescription());
		form.setGoodsGeographyDescription(core.getGoodsGeographyDescription());
		form.setGoodsProductionDescription(core.getGoodsProductionDescription());

		coreToCorrespondanceAddressForm(core.getContactDetails(), flowBean);

		if (core.getMarkDescriptions() != null && !core.getMarkDescriptions().isEmpty()) {
			for (MarkDescription markDescription : core.getMarkDescriptions()) {
				if (StringUtils.equals(markDescription.getLanguage(), core.getApplicationLanguage())) {
					form.setMarkDescription(markDescription.getValue());
				}
				if (StringUtils.equals(markDescription.getLanguage(), core.getSecondLanguage())) {
					form.setMarkDescriptionSecond(markDescription.getValue());
				}
			}
		}
		if (core.getMarkDisclaimers() != null && !core.getMarkDisclaimers().isEmpty()) {
			for (MarkDisclaimer markDisclaimer : core.getMarkDisclaimers()) {
				if (StringUtils.equals(markDisclaimer.getLanguage(), core.getApplicationLanguage())) {
					form.setFirstDisclaimer(markDisclaimer.getValue());
				}
				if (StringUtils.equals(markDisclaimer.getLanguage(), core.getSecondLanguage())) {
					form.setSecondDisclaimer(markDisclaimer.getValue());
				}
			}
		}
		if (core.getWordSpecifications() != null && !core.getWordSpecifications().isEmpty()) {
			for (WordSpecification wordSpecification : core.getWordSpecifications()) {
				form.setTranscriptionDetails(wordSpecification.getTranscriptionDetails());
				form.setCharset(wordSpecification.getCharacterSet());
				form.setWordRepresentation(wordSpecification.getWordElements());
			}
		}

        form.setSeriesPresent(core.isSeriesIndicator());
		form.setSeriesNumber(core.getSeriesNumber());

		if (core.getMarkKind() != null) {
			form.setMarkType(getMainFormTradeMark(core.getMarkKind()));
		}

		form.setColourList(new ArrayList<ColourForm>());
		if (core.getImageSpecifications() != null && !core.getImageSpecifications().isEmpty()) {
			for (ImageSpecification imageSpecification : core.getImageSpecifications()) {
				if (imageSpecification.getColours() != null && !imageSpecification.getColours().isEmpty()) {
					form.getColourList()
						.addAll(imageSpecification.getColours().stream()
							.map(colour -> new ColourForm(colour.getValue(), (colour.getFormat() == null || colour.getFormat().isEmpty()) ? "DESCRIPTION" : colour.getFormat()))
							.filter(colourForm -> !form.getColourList().contains(colourForm))
							.collect(Collectors.toList()));
				}

				if(!FactoryUtil.markContainsViews(form.getMarkType())) {
					if (imageSpecification.getRepresentation() != null) {
						form.setFileWrapperImage(coreDocumentToFileWrapper(imageSpecification.getRepresentation()));
					}
				}
			}
		}

		if (core.getMediaRepresentations() != null && !core.getMediaRepresentations().isEmpty()) {
			for (MediaRepresentation multimedia : core.getMediaRepresentations()) {
				Document document = multimedia.getRepresentation();
				if (document != null && document.getCustomProperties()!=null && document.getFileName()!=null) {
					form.setFileWrapperMultimedia(coreDocumentToFileWrapper(document));
				}
			}
		}

		form.setCollectiveMark(core.getMarkRightKind() == MarkKind.COLLECTIVE ? Boolean.TRUE : Boolean.FALSE);
		form.setCertificateMark(core.getMarkRightKind() == MarkKind.CERTIFICATE ? Boolean.TRUE : Boolean.FALSE);

		if (core.getSoundRepresentations() != null && !core.getSoundRepresentations().isEmpty()) {
			form.setSoundFile(coreMarkSoundToFileWrapper(core.getSoundRepresentations().get(0)));
		}

		if(core.getTradeMarkDocuments() != null && core.getTradeMarkDocuments().size()>0){
			FileWrapper fileWrapper = listAttachedDocumentFactory.convertFrom(core.getTradeMarkDocuments());
			form.setTrademarkRegulationDocuments(fileWrapper);
		}

		return flowBean;
	}

	private FileWrapper coreMarkSoundToFileWrapper(SoundSpecification soundSpecification) {

		if (soundSpecification == null) {
			return new FileWrapper();
		}
		FileWrapper form = new FileWrapper();

		if (soundSpecification.getDocument() != null) {
			form.setFilename(soundSpecification.getDocument().getFileName());
			form.setAttachment(Boolean.TRUE);
			StoredFile storedFile = new StoredFile();
			storedFile.setDocumentId(soundSpecification.getDocument().getDocumentId());
			storedFile.setFilename(soundSpecification.getDocument().getFileName());
			storedFile.setOriginalFilename(soundSpecification.getDocument().getFileName());
			storedFile.setContentType("audio/mp3");

			form.getStoredFiles().add(storedFile);
		}
		return form;
	}

	private FileWrapper coreDocumentToFileWrapper(Document representation) {

		if (representation == null) {
			return new FileWrapper();
		}
		FileWrapper form = new FileWrapper();

		form.setFilename(representation.getFileName());
		form.setAttachment(Boolean.TRUE);
		StoredFile storedFile = new StoredFile();
		storedFile.setDocumentId(representation.getDocumentId());
		storedFile.setFilename(representation.getFileName());
		storedFile.setOriginalFilename(representation.getUri());

		Map<String, String> mappingType = new HashMap<String, String>();
		mappingType.put("image/jpeg", "image/jpeg");
		mappingType.put("image/gif", "image/gif");
		mappingType.put("image/tiff", "image/tiff");
		mappingType.put("application/pdf", "application/pdf");
		mappingType.put("image/png", "image/png");
		mappingType.put("video/mp4", "video/mp4");

		if (StringUtils.isNotBlank(representation.getFileFormat())) {
			storedFile.setContentType(mappingType.get(representation.getFileFormat()));
		}
		form.getStoredFiles().add(storedFile);

		return form;
	}

	private String getMainFormTradeMark(MarkFeature markFeature) {
		switch (markFeature) {
		case COLOUR:
			return AppConstants.ColorMarkType;
		case FIGURATIVE:
			return AppConstants.FigurativeMarkType;
		case STYLIZED_CHARACTERS:
			return AppConstants.Mark3DWord;
		case COMBINED:
			return AppConstants.FigurativeWordMark;
		case SOUND:
			return AppConstants.SoundMarkType;
		case WORD:
			return AppConstants.WordMarkType;
		case OTHER:
			return AppConstants.OtherMarkType;
		case THREE_D:
			return AppConstants.Mark3DType;
		case HOLOGRAM:
			return AppConstants.MarkHologram;
		case PATTERN:
			return AppConstants.MarkPattern;
		case POSITION:
			return AppConstants.MarkPosition;
		case THREE_D_SHAPE:
			return AppConstants.MarkShape;
		case MULTIMEDIA:
			return AppConstants.MarkMultimedia;
		case MOTION:
			return AppConstants.MarkMotion;
		case GEOGRAPHIC_INDICATION:
			return AppConstants.GeoIndication;
		case ORIGIN_NAME:
			return AppConstants.OriName;
		default:
			break;
		}
		return AppConstants.MarkTypeSelect;
	}

	private MarkFeature getMarkType(String markType) {
		if (markType.equals(AppConstants.WordMarkType)) {
			return MarkFeature.WORD;
		}
		if (markType.equals(AppConstants.FigurativeMarkType)) {
			return MarkFeature.FIGURATIVE;
		}
		if (markType.equals(AppConstants.FigurativeWordMark)) {
			return MarkFeature.COMBINED;
		}
		if (markType.equals(AppConstants.Mark3DType)) {
			return MarkFeature.THREE_D;
		}
		if (markType.equals(AppConstants.ColorMarkType)) {
			return MarkFeature.COLOUR;
		}
		if (markType.equals(AppConstants.SoundMarkType)) {
			return MarkFeature.SOUND;
		}
		if (markType.equals(AppConstants.Mark3DWord)) {
			return MarkFeature.STYLIZED_CHARACTERS;
		}
		if (markType.equals(AppConstants.OtherMarkType)) {
			return MarkFeature.OTHER;
		}
		if (markType.equals(AppConstants.MarkShape)) {
			return MarkFeature.THREE_D_SHAPE;
		}
		if (markType.equals(AppConstants.MarkHologram)) {
			return MarkFeature.HOLOGRAM;
		}
		if (markType.equals(AppConstants.MarkPosition)) {
			return MarkFeature.POSITION;
		}
		if (markType.equals(AppConstants.MarkMotion)) {
			return MarkFeature.MOTION;
		}
		if (markType.equals(AppConstants.MarkPattern)) {
			return MarkFeature.PATTERN;
		}
		if (markType.equals(AppConstants.MarkMultimedia)) {
			return MarkFeature.MULTIMEDIA;
		}
		if(markType.equals(AppConstants.GeoIndication)){
			return MarkFeature.GEOGRAPHIC_INDICATION;
		}
		if(markType.equals(AppConstants.OriName)){
			return MarkFeature.ORIGIN_NAME;
		}
		return MarkFeature.UNDEFINED;
	}

}

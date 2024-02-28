/*
 *  FspDomain:: MarkImageConverter 15/10/13 13:28 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.ImageSpecification;
import eu.ohim.sp.filing.domain.ds.*;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;
import java.util.List;


public class MarkImageConverter implements CustomConverter, Converter, MapperAware {
	
	private Mapper mapper;

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof ImageSpecification) {
			MarkImageType image = new MarkImageType();
	
			ImageSpecification imageSpecification = ((ImageSpecification) value);
			
			if (imageSpecification.getColourClaimedText() != null) {
				List<eu.ohim.sp.filing.domain.ds.Text> textMarkImageList = new ArrayList<eu.ohim.sp.filing.domain.ds.Text>();
				
				for (Text imageSpecificationText : imageSpecification.getColourClaimedText()) {
					eu.ohim.sp.filing.domain.ds.Text text = new eu.ohim.sp.filing.domain.ds.Text();
					text.setLanguage(imageSpecificationText.getLanguage());
					text.setValue(imageSpecificationText.getValue());
					textMarkImageList.add(text);
				}
				
				image.getMarkImageColourClaimedText().addAll(textMarkImageList);
			}
			
			if (imageSpecification.getRepresentation() != null) {
				
				image.setMarkImageIdentifier(new Identifier());
				image.getMarkImageIdentifier().setValue(imageSpecification.getRepresentation().getDocumentId());
				
				image.setMarkImageFilename(imageSpecification.getRepresentation().getFileName());
	
				if (imageSpecification.getRepresentation().getFileFormat() != null) {
					image.setMarkImageFileFormat(new FileFormat());
					image.getMarkImageFileFormat().setValue(imageSpecification.getRepresentation().getFileFormat());
				}
				
				if (imageSpecification.getRepresentation().getUri() != null) {
					URI uri = new URI();
					uri.setValue(imageSpecification.getRepresentation().getUri());
					image.setMarkImageURI(uri);
				}
				
				if (imageSpecification.getColours() != null) {
					ColourDetailsType colourDetailsType = new ColourDetailsType();
					colourDetailsType.getColour().addAll(new ArrayList<ColourType>());
					ColourType colourType = new ColourType();
					colourType.getColourCode().addAll(new ArrayList<ColourCodeType>());
					
					List<Colour> colourList = imageSpecification.getColours();
					for (Colour colour : colourList) {
						ColourCodeType colourCodeType = new ColourCodeType();
						colourCodeType.setValue(colour.getValue());
						if (StringUtils.isNotEmpty(colour.getFormat())) {
							ColourCodeFormatType colourCodeFormatType = ColourCodeFormatType.fromValue(colour.getFormat());
							colourCodeType.setColourCodeFormat(colourCodeFormatType);
						}
						colourType.getColourCode().add(colourCodeType);
					}
					
					colourDetailsType.getColour().add(colourType);
					
					image.setMarkImageColourDetails(colourDetailsType);
				}
				
				image.setMarkImageColourIndicator(imageSpecification.isColourClaimedIndicator());
				
			}

			return image;
		} else if (value instanceof MarkImageType) {
			ImageSpecification image = new ImageSpecification();
			
			MarkImageType markImage = ((MarkImageType) value);
			
			if (markImage.getMarkImageColourClaimedText() != null) {
				
				List<Text> textImageSpecificationList = new ArrayList<Text>();
				for (eu.ohim.sp.filing.domain.ds.Text markImageText : markImage.getMarkImageColourClaimedText()) {
					Text text = new Text();
					text.setLanguage(markImageText.getLanguage());
					text.setValue(markImageText.getValue());
					textImageSpecificationList.add(text);
				}
				image.setColourClaimedText(textImageSpecificationList);
			}
			
			image.setRepresentation(new Document());
			
			if (markImage.getMarkImageIdentifier() != null) {
				image.getRepresentation().setDocumentId(markImage.getMarkImageIdentifier().getValue());
			}
			
            if (markImage.getMarkImageFileFormat() != null) {
                image.getRepresentation().setFileFormat(markImage.getMarkImageFileFormat().getValue());
            }
			image.getRepresentation().setFileName(markImage.getMarkImageFilename());
            if (markImage.getMarkImageURI() != null) {
    			image.getRepresentation().setUri(markImage.getMarkImageURI().getValue());
            }
			image.setColours(new ArrayList<Colour>());
			
			if (markImage.getMarkImageColourDetails() != null) {
				if (markImage.getMarkImageColourDetails().getColour() != null) {
					List<Colour> colourList = new ArrayList<Colour>();
					
					List<ColourType> colourTypeList = markImage.getMarkImageColourDetails().getColour();
					for (ColourType colourType2 : colourTypeList) {
						List<ColourCodeType> colourCodeList = colourType2.getColourCode();
						for (ColourCodeType colourCodeType : colourCodeList) {
							Colour colour = new Colour();
							if (colourCodeType.getColourCodeFormat() != null) {
								colour.setFormat(colourCodeType.getColourCodeFormat().value());
							}
							colour.setValue(colourCodeType.getValue());
							colourList.add(colour);
						}
					}
					
					image.setColours(colourList);
				}
			}
			
			image.setColourClaimedIndicator(markImage.isMarkImageColourIndicator() != null ? markImage.isMarkImageColourIndicator() : false);
			
			return image;
		}
		return null;
	}

	@Override
	public Object convert(Object existingDestinationFieldValue,
			Object sourceFieldValue, Class<?> destinationClass,
			Class<?> sourceClass) {
		return convert(destinationClass, sourceFieldValue);
	}

	@Override
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

}

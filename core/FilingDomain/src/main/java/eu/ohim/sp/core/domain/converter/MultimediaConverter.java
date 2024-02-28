/*
 *  FspDomain:: MarkMediaConverter 15/10/13 13:28 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.MediaRepresentation;
import eu.ohim.sp.filing.domain.tm.Identifier;
import eu.ohim.sp.filing.domain.tm.MarkMedia;
import eu.ohim.sp.filing.domain.tm.MediaFileFormatType;
import eu.ohim.sp.filing.domain.tm.URI;
import org.apache.commons.beanutils.Converter;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;

public class MultimediaConverter implements CustomConverter, Converter, MapperAware {

	private Mapper mapper;

	@Override
	public Object convert(Class type, Object value) {
		if (value instanceof MediaRepresentation) {
			MarkMedia media = new MarkMedia();

			MediaRepresentation MediaRepresentation = ((MediaRepresentation) value);

			if (MediaRepresentation.getRepresentation() != null) {

				media.setIdentifier(new Identifier());
				media.getIdentifier().setValue(MediaRepresentation.getRepresentation().getDocumentId());

				media.setMediaFilename(MediaRepresentation.getRepresentation().getFileName());

				if (MediaRepresentation.getRepresentation().getFileFormat() != null) {
					media.setMediaFileFormat(MediaFileFormatType.fromValue(MediaRepresentation.getRepresentation().getFileFormat()));
				}

				if (MediaRepresentation.getRepresentation().getUri() != null) {
					URI uri = new URI();
					uri.setValue(MediaRepresentation.getRepresentation().getUri());
					media.setMediaURI(uri);
				}
			}

			return media;
		} else if (value instanceof MarkMedia) {
			MediaRepresentation media = new MediaRepresentation();

			MarkMedia markMedia = ((MarkMedia) value);

			media.setRepresentation(new Document());

			if (markMedia.getIdentifier() != null) {
				media.getRepresentation().setDocumentId(markMedia.getIdentifier().getValue());
			}

            if (markMedia.getMediaFileFormat() != null) {
				media.getRepresentation().setFileFormat(markMedia.getMediaFileFormat().value());
            }
			media.getRepresentation().setFileName(markMedia.getMediaFilename());
            if (markMedia.getMediaURI() != null) {
				media.getRepresentation().setUri(markMedia.getMediaURI().getValue());
            }
			media.setColours(new ArrayList<Colour>());

			return media;
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

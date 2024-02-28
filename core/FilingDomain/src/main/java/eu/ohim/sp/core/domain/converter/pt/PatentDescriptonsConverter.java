package eu.ohim.sp.core.domain.converter.pt;

import eu.ohim.sp.core.domain.patent.Patent;
import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.filing.domain.pt.PatentDescriptionKind;
import eu.ohim.sp.filing.domain.pt.PatentDescriptionSheet;
import eu.ohim.sp.filing.domain.pt.PatentDescriptionSheetDetails;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by Raya
 * 05.07.2019
 */
public class PatentDescriptonsConverter extends DozerConverter<Patent, PatentDescriptionSheetDetails> implements MapperAware{

    private Mapper mapper;

    public PatentDescriptonsConverter()
    {
        super(Patent.class, PatentDescriptionSheetDetails.class);
    }

    @Override
    public PatentDescriptionSheetDetails convertTo(Patent patent, PatentDescriptionSheetDetails descriptions) {
        if(descriptions == null) {
           descriptions = new PatentDescriptionSheetDetails();
        }
        if(descriptions.getPatentDescriptionSheet() == null){
            descriptions.setPatentDescriptionSheet(new ArrayList<>());
        }
        if(patent != null){
            if(patent.getPatentDescriptions() != null){
                descriptions.getPatentDescriptionSheet().addAll(
                        patent.getPatentDescriptions().stream().map(e -> createPatentDescriptionSheet(PatentDescriptionKind.DESCRIPTION, e))
                                .collect(Collectors.toList())
                );
            }
            if(patent.getPatentClaims() != null){
                descriptions.getPatentDescriptionSheet().addAll(
                        patent.getPatentClaims().stream().map(e -> createPatentDescriptionSheet(PatentDescriptionKind.CLAIMS, e))
                                .collect(Collectors.toList())
                );
            }
            if(patent.getPatentDrawings() != null){
                descriptions.getPatentDescriptionSheet().addAll(
                        patent.getPatentDrawings().stream().map(e -> createPatentDescriptionSheet(PatentDescriptionKind.DRAWINGS, e))
                                .collect(Collectors.toList())
                );
            }
            if(patent.getSequencesListing() != null){
                descriptions.getPatentDescriptionSheet().addAll(
                        patent.getSequencesListing().stream().map(e -> createPatentDescriptionSheet(PatentDescriptionKind.SEQUENCES_LISTING, e))
                                .collect(Collectors.toList())
                );
            }
        }
        return descriptions;
    }

    private PatentDescriptionSheet createPatentDescriptionSheet(PatentDescriptionKind kind, AttachedDocument descriptionAttachedDocument){
        PatentDescriptionSheet patentDescriptionSheet = new PatentDescriptionSheet();
        mapper.map(descriptionAttachedDocument, patentDescriptionSheet);
        patentDescriptionSheet.setPatentDescriptionKind(kind);
        return patentDescriptionSheet;
    }

    @Override
    public Patent convertFrom(PatentDescriptionSheetDetails descriptions, Patent patent) {
        if(patent == null){
            patent = new Patent();
        }
        if(patent.getPatentDescriptions() == null){
            patent.setPatentDescriptions(new ArrayList<>());
        }
        if(patent.getPatentClaims() == null){
            patent.setPatentClaims(new ArrayList<>());
        }
        if(patent.getPatentDrawings() == null){
            patent.setPatentDrawings(new ArrayList<>());
        }
        if(patent.getSequencesListing() == null){
            patent.setSequencesListing(new ArrayList<>());
        }

        if(descriptions != null && descriptions.getPatentDescriptionSheet() != null){
           for(PatentDescriptionSheet descriptionSheet: descriptions.getPatentDescriptionSheet()) {
               AttachedDocument attachedDescriptionDoc = new AttachedDocument();
               mapper.map(descriptionSheet, attachedDescriptionDoc);
               setDescriptionToProperPatentCollection(descriptionSheet.getPatentDescriptionKind(), patent, attachedDescriptionDoc);
           }
        }

        return patent;
    }

    private void setDescriptionToProperPatentCollection(PatentDescriptionKind kind, Patent patent, AttachedDocument attachedDescriptionDoc){
        switch (kind){
            case DESCRIPTION:
                patent.getPatentDescriptions().add(attachedDescriptionDoc); break;
            case CLAIMS:
                patent.getPatentClaims().add(attachedDescriptionDoc); break;
            case DRAWINGS:
                patent.getPatentDrawings().add(attachedDescriptionDoc); break;
            case SEQUENCES_LISTING:
                patent.getSequencesListing().add(attachedDescriptionDoc); break;
        }
    }

    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}

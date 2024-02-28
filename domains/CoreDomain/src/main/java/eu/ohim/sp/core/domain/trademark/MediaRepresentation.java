package eu.ohim.sp.core.domain.trademark;

import eu.ohim.sp.core.domain.common.Text;
import eu.ohim.sp.core.domain.model.Vienna;
import eu.ohim.sp.core.domain.resources.Colour;
import eu.ohim.sp.core.domain.resources.Document;

import java.io.Serializable;
import java.util.List;

/**
 * The Class ImageSpecification.
 */
public class MediaRepresentation implements Serializable {

    private static final long serialVersionUID = 1L;

    /** The representation. */
    private Document representation;

    /** The colour claimed indicator. */
    private boolean colourClaimedIndicator;

    /** The colour claimed text. */
    private List<Text> colourClaimedText;

    /** The colours. */
    private List<Colour> colours;

    /** The seriesIdentifier */
    private short seriesIdentifier;

    /** The vienna list */
    private List<Vienna> vienna;

    /**
     * Gets the representation.
     *
     * @return the representation
     */
    public Document getRepresentation() {
        return representation;
    }

    /**
     * Sets the representation.
     *
     * @param representation the new representation
     */
    public void setRepresentation(Document representation) {
        this.representation = representation;
    }

    /**
     * Checks if is colour claimed indicator.
     *
     * @return true, if is colour claimed indicator
     */
    public boolean isColourClaimedIndicator() {
        return colourClaimedIndicator;
    }

    /**
     * Sets the colour claimed indicator.
     *
     * @param colourClaimedIndicator the new colour claimed indicator
     */
    public void setColourClaimedIndicator(boolean colourClaimedIndicator) {
        this.colourClaimedIndicator = colourClaimedIndicator;
    }

    /**
     * Gets the colour claimed text.
     *
     * @return the colour claimed text
     */
    public List<Text> getColourClaimedText() {
        return colourClaimedText;
    }

    /**
     * Sets the colour claimed text.
     *
     * @param colourClaimedText the new colour claimed text
     */
    public void setColourClaimedText(List<Text> colourClaimedText) {
        this.colourClaimedText = colourClaimedText;
    }

    /**
     * Gets the colours.
     *
     * @return the colours
     */
    public List<Colour> getColours() {
        return colours;
    }

    /**
     * Sets the colours.
     *
     * @param colours the new colours
     */
    public void setColours(List<Colour> colours) {
        this.colours = colours;
    }

    /**
     * Gets the seriesIdentifier.
     *
     * @return the seriesIdentifier
     */
    public short getSeriesIdentifier() {
        return seriesIdentifier;
    }

    /**
     * Sets the seriesIdentifier
     *
     * @param seriesIdentifier the new seriesIdentifier
     */
    public void setSeriesIdentifier(short seriesIdentifier) {
        this.seriesIdentifier = seriesIdentifier;
    }

    /**
     * Gets the vienna
     *
     * @return the vienna list
     */
    public List<Vienna> getVienna() {
        return vienna;
    }

    /**
     * Sets the vienna
     *
     * @param vienna the new vienna
     */
    public void setVienna(List<Vienna> vienna) {
        this.vienna = vienna;
    }

}

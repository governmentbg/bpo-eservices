/*******************************************************************************
 * * $Id:: AppConstants.java 53086 2012-12-14 09:01:44Z virgida $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util.constants;

public class AppConstants {

    /**
     * values for claim priorities
     */
    public static final int DO_NOT_CLAIM_PRIORITIES = 1;
    public static final int CLAIM_PRIORITIES_LATER = 2;
    public static final int CLAIM_PRIORITIES_NOW = 3;
    
    // mark type constants
    public static final String MarkTypeSelect = "0";
    public static final String WordMarkType = "wordmark";
    public static final String FigurativeMarkType = "figurative";
    public static final String FigurativeWordMark = "figwordmark";
    public static final String Mark3DType = "3dmark";
    public static final String ColorMarkType = "colourmark";
    public static final String SoundMarkType = "soundmark";
    public static final String OtherMarkType = "other";
    public static final String Mark3DWord = "3dwordmark";

    public static final int ERROR_CODE_901 = 901;
    public static final String ERROR_CODE_901_DESC = "Session timeout";
    // custom 902 error code that will be thrown if an ajax calls fails due to time out error
    public static final int ERROR_CODE_902 = 902;
    public static final String ERROR_CODE_902_DESC = "No such conversation";
}

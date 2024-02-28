/**
 *  eu.ohim.sp.dsefiling.ui.domain.KeyTextUIClass.java
 *       . * .
 *     * RRRR  *    Copyright (c) 2018 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 * Created By schaturv
 * Date Aug 24, 2018
 */
package eu.ohim.sp.core.domain.design;

/**
 * @author schaturv
 *
 */
public class KeyTextUIClass {

	/**  KEY */
    private Object key;
    /** TEXT */
    private String text;

    /**
     * Empty constructor
     */
    public KeyTextUIClass(){
        this.key=null;
        this.text=null;
    }

    /**
     * Constructor with parameters
     * @param key
     * @param text
     */
    public KeyTextUIClass(Object key, String text){
        this.key=key;
        this.text=text;
    }

    /**
     * gets the key.
     * @return
     */
    public Object getKey() {
        return key;
    }

    /**
     * sets the key.
     * @param key
     */
    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * gets the text.
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * sets the text.
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

}

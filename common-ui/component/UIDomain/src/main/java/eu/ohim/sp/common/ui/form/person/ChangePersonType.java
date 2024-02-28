/*******************************************************************************
 * * $Id:: AssigneeKindForm.java 49264 2012-10-29 13:23:34Z karalch             $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.person;

/**
 * @author velosma
 */
public enum ChangePersonType
{
	ADD_NEW_REPRESENTATIVE,
    REPLACE_REPRESENTATIVE,
	REMOVE_REPRESENTATIVE,
	CHANGE_REPRESENTATIVE_ADDRESS,
	CHANGE_REPRESENTATIVE_CORRESPONDENCE_ADDRESS,
	ADD_NEW_CORRESPONDENT,
	REPLACE_CORRESPONDENT,
	REMOVE_CORRESPONDENT,
	CHANGE_CORRESPONDENT_ADDRESS
    ;

}

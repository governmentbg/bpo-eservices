/*******************************************************************************
 * * $Id:: Status.java 14330 2012-10-29 13:07:33Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.core.application;

import java.io.Serializable;

/**
 * Entity bean that holds the information about the status of a draft
 * application
 * 
 * @author karalch
 * 
 */
public class Status implements Serializable {

	private static final long serialVersionUID = 1L;

    // lformstatus - status constants
	private Long id;
	private Integer code;
	private String description;
	
	public Status() {}
	
	public Status(Integer code) {
		this.code = code;
	}
	
    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

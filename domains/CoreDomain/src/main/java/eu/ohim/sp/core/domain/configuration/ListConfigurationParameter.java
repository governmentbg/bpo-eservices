/*
 *  SystemConfigurationServiceDomain:: ListConfigurationParameter 07/10/13 21:21 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.configuration;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: KARALCH
 * Date: 07/10/13
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public class ListConfigurationParameter extends  ConfigurationParameter {

    /** The values. */
    private List<String> value;

    public ListConfigurationParameter() {
        setParamtype(ConfigParamType.LIST);
    }

    public ListConfigurationParameter(String name, String component, List<String> value) {
        super(name, component, ConfigParamType.LIST);
        this.value = value;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

}

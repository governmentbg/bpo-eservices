/*******************************************************************************
 * * $Id:: FileUploadControllerTest.java 50771 2012-11-14 15:10:27Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import eu.ohim.sp.common.ui.FileUploadConfigurationFactory;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.resources.MultipartFileForm;
import eu.ohim.sp.common.ui.service.interfaces.FileServiceInterface;

public class FileUploadControllerTest {
    @Mock
    FileServiceInterface fileService;

    @Mock
    FileUploadConfigurationFactory fileUploadConfigurationFactory;

    @Mock
    FlowBean flowBean;

    @InjectMocks
    FileUploadController fileUploadController = new FileUploadController();

    MultipartFileForm fileForm;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fileForm = new MultipartFileForm();
    }

}

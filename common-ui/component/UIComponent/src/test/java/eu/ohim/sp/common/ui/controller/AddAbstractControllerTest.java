/*******************************************************************************
 * * $Id:: AddAbstractControllerTest.java 49264 2012-10-29 13:23:34Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.controller;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.exception.MaximumEntitiesException;
import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import eu.ohim.sp.common.ui.util.ListHelper;
import eu.ohim.sp.common.ui.util.StubForm;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.common.ui.validator.Validatable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * @author ionitdi
 */
public class AddAbstractControllerTest
{
    AddAbstractController addAbstractController;

    FlowBean flowBean;

    AddParameters defaultAddParameters;

    BindingResult bindingResult;

    @Before
    public void setUp()
    {
        // initialize abstract class by telling mockito to create a wrapper around it and call its real methods
        // this way one can test the abstract class by itself
        addAbstractController = Mockito.mock(AddAbstractController.class, Mockito.CALLS_REAL_METHODS);

        flowBean = Mockito.mock(FlowBean.class);

        defaultAddParameters = new AddParameters(StubForm.class, "object name", "success view", "form view", 1);

        bindingResult = Mockito.mock(BindingResult.class);
    }

    @Test(expected = SPException.class)
    public void innerFormBackingObject_nullFlowBean_returnsFormView()
    {
        /// Arrange
        AddParameters addParams = new AddParameters(null, "someName", "someView", "someForm", 1);

        /// Act
        ModelAndView result = addAbstractController.innerFormBackingObject("some id", null, addParams);
    }

    @Test(expected = SPException.class)
    public void innerFormBackingObject_nullAddParameters()
    {
        /// Arrange

        /// Act
        addAbstractController.innerFormBackingObject("some id", flowBean, null);
    }

    @Test
    public void innerFormBackingObject_nullId_returnsFormView()
    {
        /// Arrange

        // initialize flow bean collection return with an empty list
        Answer<List<AbstractForm>> answer = ListHelper.setupDummyListAnswer(null);
        when(flowBean.getCollection(defaultAddParameters.getCommandClass())).thenAnswer(answer);

        /// Act
        ModelAndView result = addAbstractController.innerFormBackingObject(null, flowBean, defaultAddParameters);

        /// Assert
        assertEquals(defaultAddParameters.getFormView(), result.getViewName());
    }

    @Test
    public void innerFormBackingObject_validId_returnsCorrectModelAndView()
    {
        /// Arrange
        StubForm model = new StubForm();
        model.setId("some id");        
        doReturn(model).when(flowBean).getObject(defaultAddParameters.getCommandClass(), "some id");

        /// Act
        ModelAndView result = addAbstractController.innerFormBackingObject("some id", flowBean, defaultAddParameters);

        /// Assert

        // check view name
        assertEquals(defaultAddParameters.getFormView(), result.getViewName());

        // check model
        assertEquals(model, result.getModel().get(defaultAddParameters.getCommandName()));
    }

    @Test(expected = SPException.class)
    public void innerFormBackingObject_addClassNotCloneable_throwsException() throws Exception
    {
        /// Arrange
        AbstractForm notCloneable = mock(AbstractForm.class);
        when(notCloneable.clone()).thenThrow(new CloneNotSupportedException());

        doReturn(notCloneable).when(flowBean).getObject(defaultAddParameters.getCommandClass(), "some id");
        /// Act
        addAbstractController.innerFormBackingObject("some id", flowBean, defaultAddParameters);
    }

    @Test(expected = MaximumEntitiesException.class)
    public void innerFormBackingObject_maxEntitiesReached_throwsException() throws Exception
    {
        /// Arrange
        when(flowBean.getCollection(defaultAddParameters.getCommandClass())).thenAnswer(
                ListHelper.setupDummyListAnswer(new StubForm(), new StubForm()));

        /// Act
        addAbstractController.innerFormBackingObject(null, flowBean, defaultAddParameters);
    }

    @Test(expected = SPException.class)
    public void onSubmit_nullCommand_throwsException()
    {
        /// Arrange

        /// Act
        addAbstractController.onSubmit(null, flowBean, defaultAddParameters, bindingResult);
    }

    @Test(expected = SPException.class)
    public void onSubmit_nullFlowBean_throwsException()
    {
        addAbstractController.onSubmit(new StubForm(), null, defaultAddParameters, bindingResult);
    }

    @Test(expected = SPException.class)
    public void onSubmit_nullAddParams_throwsException()
    {
        addAbstractController.onSubmit(new StubForm(), flowBean, null, bindingResult);
    }

    @Test(expected = SPException.class)
    public void onSubmit_nullBindingResult_throwsException()
    {
        addAbstractController.onSubmit(new StubForm(), flowBean, defaultAddParameters, null);
    }

    @Test
    public void onSubmit_nonImportedObjectNotValid_returnsFormView()
    {
        /// Arrange
        StubForm stub = new StubForm();
        stub.setImported(false);

        addAbstractController = mock(AddAbstractController.class);
        when(addAbstractController.onSubmit(any(AbstractImportableForm.class),
                                            any(FlowBean.class),
                                            any(AddParameters.class),
                                            any(BindingResult.class))).thenCallRealMethod();
        when(addAbstractController.validateCommand(stub, bindingResult, defaultAddParameters)).thenReturn(false);

        /// Act
        ModelAndView result = addAbstractController.onSubmit(stub, flowBean, defaultAddParameters, bindingResult);

        /// Assert
        assertEquals(defaultAddParameters.getFormView(), result.getViewName());
    }

    @Test
    public void onSubmit_nonImportedObjectValid_addsToFlow()
    {
        /// Arrange
        StubForm stub = new StubForm();
        stub.setId("stub id");
        stub.setImported(false);
        addAbstractController = mock(AddAbstractController.class);
        when(addAbstractController.onSubmit(any(AbstractImportableForm.class),
                                            any(FlowBean.class),
                                            any(AddParameters.class),
                                            any(BindingResult.class))).thenCallRealMethod();
        when(addAbstractController.validateCommand(stub, bindingResult, defaultAddParameters)).thenReturn(true);
        when(flowBean.existsObject(defaultAddParameters.getCommandClass(), "stub id")).thenReturn(false);

        /// Act
        ModelAndView result = addAbstractController.onSubmit(stub, flowBean,  defaultAddParameters,  bindingResult);

        /// Assert
        assertEquals(defaultAddParameters.getSuccessView(), result.getViewName());
        verify(flowBean, times(1)).addObject(stub);
    }

    @Test
    public void onSubmit_nonImportedObjectValid_replacesInFlow()
    {
        /// Arrange
        StubForm stub = new StubForm();
        stub.setId("stub id");
        stub.setImported(false);
        addAbstractController = mock(AddAbstractController.class);
        when(addAbstractController.onSubmit(any(AbstractImportableForm.class),
                                            any(FlowBean.class),
                                            any(AddParameters.class),
                                            any(BindingResult.class))).thenCallRealMethod();
        when(addAbstractController.validateCommand(stub, bindingResult, defaultAddParameters)).thenReturn(true);
        when(flowBean.existsObject(defaultAddParameters.getCommandClass(), "stub id")).thenReturn(true);

        /// Act
        ModelAndView result = addAbstractController.onSubmit(stub, flowBean,  defaultAddParameters,  bindingResult);

        /// Assert
        assertEquals(defaultAddParameters.getSuccessView(), result.getViewName());
        verify(flowBean, times(1)).replaceObject(stub, "stub id");
    }

    @Test
    public void onSubmit_importedObject_addInFlowWithoutValidationCheck()
    {
        /// Arrange
        StubForm stub = new StubForm();
        stub.setId("stub id");
        stub.setImported(true);
        addAbstractController = mock(AddAbstractController.class);
        when(addAbstractController.onSubmit(any(AbstractImportableForm.class),
                                            any(FlowBean.class),
                                            any(AddParameters.class),
                                            any(BindingResult.class))).thenCallRealMethod();
        when(flowBean.existsObject(defaultAddParameters.getCommandClass(), "stub id")).thenReturn(false);

        /// Act
        ModelAndView result = addAbstractController.onSubmit(stub, flowBean,  defaultAddParameters,  bindingResult);

        /// Assert
        assertEquals(defaultAddParameters.getSuccessView(), result.getViewName());
        verify(flowBean, times(1)).addObject(stub);
        verify(addAbstractController, times(0))
                .validateCommand(any(Validatable.class), any(BindingResult.class), any(AddParameters.class));
    }

    @Test
    public void validateCommand_triggerEnabled_callsValidator()
    {
        /// Arrange
        Validatable command = mock(Validatable.class);
        AddParameters addParameters = new AddParameters(null, null, null, null, null, true);

        FormValidator validator = mock(FormValidator.class);
        addAbstractController.setValidator(validator);

        /// Act
        boolean result = addAbstractController.validateCommand(command, bindingResult, addParameters);

        /// Assert
        verify(validator, times(1)).validate(eq(command), eq(bindingResult), any(FlowScopeDetails.class));
        assertEquals(true, result);
    }

    @Test
    public void validateCommand_triggerDisabled_doesNotCallValidator()
    {
        /// Arrange
        Validatable command = mock(Validatable.class);
        AddParameters addParameters = new AddParameters(null, null, null, null, null, false);

        FormValidator validator = mock(FormValidator.class);
        addAbstractController.setValidator(validator);

        /// Act
        boolean result = addAbstractController.validateCommand(command, bindingResult, addParameters);

        /// Assert
        verify(validator, times(0)).validate(eq(command), eq(bindingResult), any(FlowScopeDetails.class));
        assertEquals(true, result);
    }

    @Test
    public void validateCommand_whenHasErrors_returnsFalse()
    {
        /// Arrange
        Validatable command = mock(Validatable.class);
        AddParameters addParameters = new AddParameters(null, null, null, null, null, true);

        FormValidator validator = mock(FormValidator.class);
        addAbstractController.setValidator(validator);
        when(bindingResult.hasErrors()).thenReturn(true);

        /// Act
        boolean result = addAbstractController.validateCommand(command, bindingResult, addParameters);

        /// Assert
        assertEquals(false, result);
    }
}

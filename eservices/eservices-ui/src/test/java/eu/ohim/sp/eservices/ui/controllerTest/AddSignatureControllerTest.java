package eu.ohim.sp.eservices.ui.controllerTest;

import eu.ohim.sp.common.ui.interceptors.FlowScopeDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import eu.ohim.sp.common.security.authorisation.domain.SPUser;
import eu.ohim.sp.common.ui.controller.parameters.AddParameters;
import eu.ohim.sp.common.ui.form.application.SignatureForm;
import eu.ohim.sp.common.ui.service.PersonService;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.common.ui.util.AuthenticationUtil;
import eu.ohim.sp.common.ui.validator.FormValidator;
import eu.ohim.sp.core.domain.user.User;
import eu.ohim.sp.core.domain.user.UserPersonDetails;
import eu.ohim.sp.core.user.UserSearchService;
import eu.ohim.sp.common.ui.controller.AddSignatureController;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;

public class AddSignatureControllerTest {
	@Mock
	private FlowScopeDetails flowScopeDetails;

	@Mock
	private ESFlowBean flowBean;

	@Mock
	private FormValidator validator;
	
	@Mock
    private UserSearchService userService;
	
	@Mock
    private PersonService personService;
	
	@Mock
    private ConfigurationServiceDelegatorInterface configurationService;

	@InjectMocks
	AddSignatureController addSignatureController = new AddSignatureController();

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void onSubmitSignature() {

		// command , result, ignoreMatches

		SignatureForm command = new SignatureForm();
		BindingResult result = Mockito.mock(BindingResult.class);
		Boolean ignoreMatches = true;

		Object target = new Object();

		Errors errors = Mockito.mock(Errors.class);

		// Mockito.when(validator.validate(target, errors)).

		AddParameters addParameters = new AddParameters(null, null, null, null,
				null);

		// validator

		// Mockito.when(validator.validate(new Object(),
		// errors)).thenReturn(true);

		Object target1 = new Object();

		validator.validate(target1, errors);

		addSignatureController
				.onSubmitSignature(command, result, ignoreMatches);

	}

	@Test
	public void formBackingSignature() {

		addSignatureController.formBackingSignature("id");

	}

	@Test
	public void getSignature() {

		addSignatureController.getSignature("id");

	}

	@Test
	public void importSignature() {

		Authentication authentication = Mockito.mock(Authentication.class);
		SPUser principal = Mockito.mock(SPUser.class);
		Mockito.when(authentication.getPrincipal()).thenReturn(principal);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		SPUser userBean = AuthenticationUtil.getAuthenticatedUser();
		
		SignatureForm command = new SignatureForm();
		BindingResult result = Mockito.mock(BindingResult.class);
		Boolean ignoreMatches = null;
		User user = Mockito.mock(User.class);
		Mockito.when(userService.getUser(null, null,null)).thenReturn(user);
		UserPersonDetails details=Mockito.mock(UserPersonDetails.class);
		Mockito.when(personService.getUserPersonDetails(flowScopeDetails.getFlowModeId())).thenReturn(details);
		addSignatureController.importSignature(command, result);

	}

}

package eu.ohim.sp.core.utils;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.SystemConfigurationServiceRemote;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.jackrabbit.rmi.repository.URLRemoteRepository;
import org.apache.log4j.Logger;

import javax.jcr.*;
import javax.jcr.Repository;
import javax.jcr.Session;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: jaraful
 * Date: 12/08/13
 * Time: 12:07
 */
public class JcrUtil {

	private static final Logger logger = Logger.getLogger(JcrUtil.class);
	SystemConfigurationServiceRemote configurationService = null;

	public JcrUtil(SystemConfigurationServiceRemote scsr){
		configurationService = scsr;
	}

	// Log in the repository
	private Session loginJCR() {
		Repository repository = null;
		try {
			String repositoryUrl = configurationService.getValue("repository.url", "general");
			String repositoryUsername = configurationService.getValue("repository.username", "general");
			String repositoryPassword = configurationService.getValue("repository.password", "general");
			if(repositoryUrl == null || repositoryUsername == null || repositoryPassword == null){
				throw new SPException("There's missing information for the repository. Please set up properties correctly");
			}

			repository = new URLRemoteRepository(repositoryUrl);
			return repository.login(new SimpleCredentials(repositoryUsername, repositoryPassword.toCharArray()));
		} catch (MalformedURLException e) {
			throw new SPException("Couldn't connect to the JCR repository. The URL is not correct.");
		} catch (LoginException e) {
			throw new SPException("Couldn't connect to the JCR repository. The login information is not correct");
		} catch (RepositoryException e) {
			throw new SPException("Couldn't connect to the JCR repository. Generic repository error");
		}

	}

	// Log in the repository
	private org.apache.chemistry.opencmis.client.api.Session loginCMIS() {
		SessionFactory factory = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		String repositoryUrl = configurationService.getValue("repository.url", "general");
		String repositoryUsername = configurationService.getValue("repository.username", "general");
		String repositoryPassword = configurationService.getValue("repository.password", "general");
		String repositoryName = configurationService.getValue("repository.name", "general");
		if(repositoryUrl == null || repositoryUsername == null || repositoryPassword == null || repositoryName == null){
			throw new SPException("There's missing information for the repository. Please set up properties correctly");
		}

		parameter.put(SessionParameter.USER, repositoryUsername);
		parameter.put(SessionParameter.PASSWORD, repositoryPassword);
		parameter.put(SessionParameter.ATOMPUB_URL, repositoryUrl);
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameter.put(SessionParameter.REPOSITORY_ID, repositoryName);

		org.apache.chemistry.opencmis.client.api.Session session = factory.createSession(parameter);

		if(session != null){
			return session;
		} else {
			throw new SPException("Couldn't create a repository session");
		}
	}

			/**
			 * Removes all the information about the test branch. This method is called after
			 * the tests has finished.
			 */
	public void wipeTestsJCR(){
		Session session = loginJCR();

		Node testNode = null;
		try {
			testNode = session.getRootNode().getNode("TEST");
			testNode.remove();

			session.save();
			session.logout();
		} catch (RepositoryException e) {
			logger.debug("		> Path TEST doesn't exist");
		}
	}

	public void wipeTestsCMIS(){
		org.apache.chemistry.opencmis.client.api.Session session = loginCMIS();

		try {
			Folder folder = (Folder) session.getObjectByPath("/TEST");
			folder.deleteTree(true, UnfileObject.DELETE, true);
		} catch (CmisObjectNotFoundException confe) {
			logger.debug("		> Path TEST doesn't exist");
		}
	}
}

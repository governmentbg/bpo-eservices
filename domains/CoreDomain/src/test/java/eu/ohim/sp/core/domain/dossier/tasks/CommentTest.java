package eu.ohim.sp.core.domain.dossier.tasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.util.Date;

import eu.ohim.sp.core.util.JavaBeanTester;
import org.junit.Test;

import eu.ohim.sp.core.domain.person.Applicant;

public class CommentTest {

	private static final String BO_USER_ID = "boUserId";
	private static final String COMMENT_TEXT = "commentText";
	private static final String STATUS = "status";
	private static final String TASK_ID = "taskId";
	private static final String USER = "user";

	@Test
	public void bean_testProperties() throws IntrospectionException {
		JavaBeanTester.test(Comment.class);
	}

	@Test
	public void testEquals() {
		Date date = new Date();

		Comment comment1 = new Comment();
		comment1.setBoUserId(BO_USER_ID);
		comment1.setCommentText(COMMENT_TEXT);
		comment1.setCreationDate(date);
		comment1.setLastUpdateDate(date);
		comment1.setStatus(STATUS);
		comment1.setTaskId(TASK_ID);
		comment1.setUser(USER);

		Comment comment2 = new Comment();
		comment2.setBoUserId(BO_USER_ID);
		comment2.setCommentText(COMMENT_TEXT);
		comment2.setCreationDate(date);
		comment2.setLastUpdateDate(date);
		comment2.setStatus(STATUS);
		comment2.setTaskId(TASK_ID);
		comment2.setUser(USER);

		assertTrue(comment1.equals(comment2));
		assertTrue(comment1.equals(comment1));
		assertEquals(comment1.hashCode(), comment2.hashCode());
	}
	
	@Test
	public void testNotEquals() {
		Date date = new Date();

		Comment comment1 = new Comment();
		comment1.setBoUserId(BO_USER_ID);
		comment1.setCommentText(COMMENT_TEXT);
		comment1.setCreationDate(date);
		comment1.setLastUpdateDate(date);
		comment1.setStatus(STATUS);
		comment1.setTaskId(TASK_ID);
		comment1.setUser(USER);
		
		assertEquals(comment1.equals(null), false);
		assertEquals(comment1.equals(new Applicant()), false);
	}

}

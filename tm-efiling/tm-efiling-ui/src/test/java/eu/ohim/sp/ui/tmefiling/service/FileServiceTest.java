package eu.ohim.sp.ui.tmefiling.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.ohim.sp.ui.tmefiling.service.FileService;

public class FileServiceTest {

	@Test
	public void testGetModule() {
		FileService fileService = new FileService();
		assertEquals(fileService.getModule(), null);
	}

}

package eu.ohim.sp.core.report;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.*;
import org.mockito.Matchers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

public class TestHelper {

	public static String generateRandomFilingNumber() {
		return Long.toHexString(Double.doubleToLongBits(Math.random()));
	}

	public static void trademarkSetup(TradeMark tradeMark) {
		WordSpecification wordSpecification = getWordSpecification();
		when(tradeMark.getMarkKind()).thenReturn(MarkFeature.WORD);
		when(tradeMark.getWordSpecifications()).thenReturn(Arrays.asList(wordSpecification));
		when(tradeMark.getMarkRightKind()).thenReturn(MarkKind.COLLECTIVE);
	}

	private static WordSpecification getWordSpecification() {
		WordSpecification wordSpecification = new WordSpecification();
		wordSpecification.setWordElements("Test name");
		return wordSpecification;
	}

	public static void trademarkApplicationSetup(TradeMarkApplication trademarkApplication, TradeMark tradeMark) {
		when(trademarkApplication.getApplicationFilingNumber()).thenReturn(TestHelper.generateRandomFilingNumber());
		when(trademarkApplication.getTradeMark()).thenReturn(tradeMark);
	}

	public static void limitedTrademarkSetup(LimitedTradeMark limitedTradeMark) {
		when(limitedTradeMark.getCurrentStatus()).thenReturn("Registered");
		when(limitedTradeMark.getRegistrationNumber()).thenReturn(TestHelper.generateRandomFilingNumber());
		when(limitedTradeMark.getApplicationNumber()).thenReturn(TestHelper.generateRandomFilingNumber());
		when(limitedTradeMark.getApplicationLanguage()).thenReturn("BG");

		when(limitedTradeMark.getMarkKind()).thenReturn(MarkFeature.WORD);
		when(limitedTradeMark.getMarkRightKind()).thenReturn(MarkKind.COLLECTIVE);
		when(limitedTradeMark.getWordSpecifications()).thenReturn(Arrays.asList(getWordSpecification()));
	}

	public static void tmEserviceApplicationSetup(TMeServiceApplication tmeserviceApplication,
												  LimitedTradeMark limitedTradeMark) {

		when(tmeserviceApplication.getTradeMarks()).thenReturn(Arrays.asList(limitedTradeMark));
		when(tmeserviceApplication.getApplicationFilingNumber()).thenReturn(TestHelper.generateRandomFilingNumber());
		when(tmeserviceApplication.getApplicationNumber()).thenReturn(TestHelper.generateRandomFilingNumber());

		when(tmeserviceApplication.getApplicationLanguage()).thenReturn("bg");
		when(tmeserviceApplication.getComment()).thenReturn(
			"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

	}

	public static void documentServiceSetup(DocumentService documentService) {
		Document documentImage = new Document();
		Map<String, String> customProperties = new HashMap<>();
		customProperties.put("fileFormat", "image/");
		documentImage.setCustomProperties(customProperties);
		try {
			documentImage.setData(
				TestHelper.getBytesFromInputStream(TestHelper.class.getClassLoader().getResourceAsStream("a.png")));
		} catch (IOException e) {
			throw new SPException(e);
		}

		Document documentAudio = new Document();
		Map<String, String> customProperties2 = new HashMap<>();
		customProperties2.put("fileFormat", "audio/");
		documentAudio.setCustomProperties(customProperties2);
		documentAudio.setFileName("sound_" + TestHelper.generateRandomFilingNumber() + ".mp3");

		when(documentService.getDocument(Matchers.startsWith("IMAGE_"))).thenReturn(documentImage);
		when(documentService.getDocument(Matchers.startsWith("AUDIO_"))).thenReturn(documentAudio);

	}

	public static byte[] getBytesFromInputStream(java.io.InputStream is) throws IOException {
		try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
			byte[] buffer = new byte[0xFFFF];

			for (int len; (len = is.read(buffer)) != -1;)
				os.write(buffer, 0, len);

			os.flush();

			return os.toByteArray();
		}
	}

}

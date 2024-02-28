package eu.ohim.sp.external.injectors;

import eu.ohim.sp.common.mime.utils.MimeTypeUtil;
import eu.ohim.sp.external.domain.design.Design;
import eu.ohim.sp.external.domain.resource.Document;
import eu.ohim.sp.external.domain.trademark.ImageSpecification;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

public class ImageInjector implements Serializable{

	private static final long serialVersionUID = -2078092999276280610L;
	private static final int TIMEOUT_IMAGE=2000;
	private static final String TMVIEW_IMAGE_DATA = System.getProperty("url.tmview.image.data");
	private static final String DSVIEW_IMAGE_DATA = System.getProperty("url.dsview.image.data");
	/** The Constant LOGGER. */
	private static final Logger logger = Logger.getLogger(ImageInjector.class);

	public static Design inject(Design design) {
		if((design!=null) && (!CollectionUtils.isEmpty(design.getViews()))) {
			design.getViews().stream()
					.filter(v -> Objects.nonNull(v))
					.filter(v -> Objects.nonNull(v.getView()))
					.filter(v -> Objects.nonNull(v.getView().getDocument()))
					.filter(v -> Objects.nonNull(v.getView().getDocument().getUri()))
					.map(v -> {
						Document document = v.getView().getDocument();
						//mount real URL
						String imageRelativeID=document.getUri();
						document.setUri(DSVIEW_IMAGE_DATA+"/"+imageRelativeID);
						//set data
						document.setData(decode(document.getUri()));
						//put file extension
						if (document.getData()!=null){
							String fileFormat= MimeTypeUtil.getMimeType(document.getData(), document.getFileName());
							String fileExtension= MimeTypeUtil.getFileExtensionFromMimeType(fileFormat);
							document.setFileName(imageRelativeID+"."+ fileExtension);
							document.setFileFormat(fileFormat);
						}
						return v;
					})
					.collect(Collectors.toList());
		}
		return design;
	}

	public static TradeMark inject(TradeMark trademark) {
		if ((trademark!=null) && (!CollectionUtils.isEmpty(trademark.getImageSpecifications())) ){
			for (ImageSpecification image: trademark.getImageSpecifications()){
				if ((image.getRepresentation()!=null) ){

					Document document= image.getRepresentation();

					//mount real URL
					String imageRelativeID=document.getUri();
					document.setUri(TMVIEW_IMAGE_DATA+"/"+imageRelativeID);

					//set data
					document.setData(decode(document.getUri()));

					//put file extension
					if (document.getData()!=null){
						String fileFormat= MimeTypeUtil.getMimeType(document.getData(), document.getFileName());
						String fileExtension= MimeTypeUtil.getFileExtensionFromMimeType(fileFormat);
						document.setFileName(imageRelativeID+"."+ fileExtension);
						document.setFileFormat(fileFormat);
					}
				}
			}
		}
		return trademark;
	}

	private static byte[] decode(String uri) {
		byte[] out =null;

		try{

			if (StringUtils.isNotEmpty(uri)) {
				URL url = new URL(uri);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(TIMEOUT_IMAGE);
				conn.connect();
				InputStream in = conn.getInputStream();
				out = IOUtils.toByteArray(in);

				if (out != null && out.length > 0){
					return out;
				}
			}
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return out;
	}
}

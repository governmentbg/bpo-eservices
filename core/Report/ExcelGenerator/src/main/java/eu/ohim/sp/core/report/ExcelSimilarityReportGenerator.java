package eu.ohim.sp.core.report;

import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.WordSpecification;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static java.util.stream.Collectors.joining;
import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;

public class ExcelSimilarityReportGenerator {

    private static final Logger LOGGER = Logger.getLogger(ExcelSimilarityReportGenerator.class);

    private static final int XLSX_NUM_COL_MARK_NUMBER = 0;
    private static final int XLSX_NUM_COL_MARK_DESCRIPTION = 1;
    private static final int XLSX_NUM_COL_MARK_TYPE = 2;
    private static final int XLSX_NUM_COL_OFFICE = 3;
    private static final int XLSX_NUM_COL_OWNER_NAME = 4;
    private static final int XLSX_NUM_COL_INPUT_TERM = 5;
    private static final int XLSX_NUM_ROW_HEADERS = 2;

    private static final String HEADING_LABEL = "headingLabel";
    private static final String TITLE_LABEL = "titleLabel";
    private static final String JPG = "jpg";
    private static final String ERROR_LOADING_LOGO_MESSAGE = "Error loading logo image";
    private static final String MARK_NUMBER_LABEL = "markNumberLabel";
    private static final String MARK_DESCRIPTION_LABEL = "markDescriptionLabel";
    private static final String MARK_TYPE_LABEL = "markTypeLabel";
    private static final String OFFICE_LABEL = "officeLabel";
    private static final String OWNER_NAME_LABEL = "ownerNameLabel";
    private static final String INPUT_TERM_LABEL = "inputTermLabel";
    private static final String DELIMITER = ", ";
    private static final String DELIMITER_SEMI_COLON = "; ";
    private static final String CREATE_REPORT_ERROR_MESSAGE = "Error creating xlsx report";
    private static final String IMG_LOGO_JPG = "/img/logo.jpg";
    private static final String DOT = ".";
    private static final String TEXT_LABEL = "textLabel";

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private CellStyle style;
    private XSSFFont boldFont;
    private int dataRowIndex;
    private Properties messages;
    private ByteArrayOutputStream outputStream;


    public ExcelSimilarityReportGenerator(String resourceFolder) {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
        style = workbook.createCellStyle();
        boldFont = workbook.createFont();
        this.messages = ExcelSimilarityReportUtil.loadProperties(resourceFolder);
        outputStream = new ByteArrayOutputStream();
    }

    public ByteArrayOutputStream generate(List<TradeMark> tmList) {
        setStyles();
        addHeaders();
        addData(tmList);
        addDisclaimer();
        //addLogo();
        writeToOutputStream();

        return outputStream;
    }

    private void setStyles() {
        boldFont.setBold(true);
        style.setFont(boldFont);
        style.setAlignment(HorizontalAlignment.CENTER);
    }

    private void addHeaders() {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFont(boldFont);

        XSSFRow rowHeader2 = sheet.createRow(XLSX_NUM_ROW_HEADERS - 2);
        XSSFCell cellHeader2 = rowHeader2.createCell(0);
        cellHeader2.setCellValue(messages.getProperty(HEADING_LABEL));
        cellHeader2.setCellStyle(headerStyle);

        XSSFRow rowHeader3 = sheet.createRow(XLSX_NUM_ROW_HEADERS - 1);
        XSSFCell cellHeader3 = rowHeader3.createCell(0);
        cellHeader3.setCellValue(messages.getProperty(TITLE_LABEL));
        cellHeader3.setCellStyle(headerStyle);
    }

    private void addLogo() {

        XSSFRow rowLogo = sheet.createRow(0);
        rowLogo.createCell(0);
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            write(
                    read(getClass().getResourceAsStream(IMG_LOGO_JPG)),
                    JPG,
                    baos
            );

            int pictureIdx = workbook.addPicture(baos.toByteArray(), Workbook.PICTURE_TYPE_JPEG);

            XSSFDrawing drawing = sheet.createDrawingPatriarch();
            XSSFCreationHelper helper = workbook.getCreationHelper();
            XSSFClientAnchor anchor = helper.createClientAnchor();

            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);
            anchor.setCol1(0);
            anchor.setRow1(0);
            drawing.createPicture(anchor, pictureIdx).resize();

        } catch (IOException e) {
            LOGGER.error(ERROR_LOADING_LOGO_MESSAGE);
            LOGGER.error(e.getMessage());
        }
    }

    private void addData(List<TradeMark> tmList) {

        XSSFRow row = sheet.createRow(XLSX_NUM_ROW_HEADERS);
        XSSFCell cell = row.createCell(XLSX_NUM_COL_MARK_NUMBER);
        cell.setCellValue(messages.getProperty(MARK_NUMBER_LABEL));
        cell.setCellStyle(style);

        cell = row.createCell(XLSX_NUM_COL_MARK_DESCRIPTION);
        cell.setCellValue(messages.getProperty(MARK_DESCRIPTION_LABEL));
        cell.setCellStyle(style);

        cell = row.createCell(XLSX_NUM_COL_MARK_TYPE);
        cell.setCellValue(messages.getProperty(MARK_TYPE_LABEL));
        cell.setCellStyle(style);

        cell = row.createCell(XLSX_NUM_COL_OFFICE);
        cell.setCellValue(messages.getProperty(OFFICE_LABEL));
        cell.setCellStyle(style);

        cell = row.createCell(XLSX_NUM_COL_OWNER_NAME);
        cell.setCellValue(messages.getProperty(OWNER_NAME_LABEL));
        cell.setCellStyle(style);

        cell = row.createCell(XLSX_NUM_COL_INPUT_TERM);
        cell.setCellValue(messages.getProperty(INPUT_TERM_LABEL));
        cell.setCellStyle(style);


        while (dataRowIndex < tmList.size()) {
            XSSFRow dataRow = sheet.createRow(dataRowIndex + XLSX_NUM_ROW_HEADERS + 1);
            XSSFCell dataCell = dataRow.createCell(XLSX_NUM_COL_MARK_NUMBER);
            dataCell.setCellValue(tmList.get(dataRowIndex).getApplicationNumber());

            dataCell = dataRow.createCell(XLSX_NUM_COL_MARK_DESCRIPTION);
            dataCell.setCellValue(
                    tmList.get(dataRowIndex)
                            .getWordSpecifications()
                            .stream()
                            .map(WordSpecification::getWordElements)
                            .collect(joining(DELIMITER)
                    )
            );

            dataCell = dataRow.createCell(XLSX_NUM_COL_MARK_TYPE);
            dataCell.setCellValue(messages.getProperty("similarMarks.table.mark.type."+tmList.get(dataRowIndex).getMarkKind().name()));

            dataCell = dataRow.createCell(XLSX_NUM_COL_OFFICE);
            dataCell.setCellValue(tmList.get(dataRowIndex).getRegistrationOffice());

            dataCell = dataRow.createCell(XLSX_NUM_COL_OWNER_NAME);
            dataCell.setCellValue(
                    tmList.get(dataRowIndex)
                            .getApplicants()
                            .stream()
                            .map(applicant -> getMarkOwnerName(applicant))
                            .collect(joining(DELIMITER)
                    )
            );

            dataCell = dataRow.createCell(XLSX_NUM_COL_INPUT_TERM);
            dataCell.setCellValue(
                    tmList.get(dataRowIndex)
                            .getClassDescriptions()
                            .stream()
                            .map(ClassDescription::getClassNumber)
                            .collect(joining(DELIMITER_SEMI_COLON))
                            + DOT
            );

            dataRowIndex++;
        }

        sheet.autoSizeColumn(XLSX_NUM_COL_MARK_NUMBER);
        sheet.autoSizeColumn(XLSX_NUM_COL_MARK_DESCRIPTION);
        sheet.autoSizeColumn(XLSX_NUM_COL_MARK_TYPE);
        sheet.autoSizeColumn(XLSX_NUM_COL_OFFICE);
        sheet.autoSizeColumn(XLSX_NUM_COL_OWNER_NAME);
        sheet.autoSizeColumn(XLSX_NUM_COL_INPUT_TERM);
    }

    private String getMarkOwnerName(Applicant owner) {
        switch (owner.getKind()) {
            case NATURAL_PERSON:
                return owner.getName().getFirstName();
            case LEGAL_ENTITY:
                return owner.getName().getOrganizationName();
            default:
                return "";
        }

    }

    private void addDisclaimer() {
        XSSFCell cell = sheet.createRow(dataRowIndex + 8).createCell(0);
        cell.setCellValue(messages.getProperty(TEXT_LABEL));
    }

    private void writeToOutputStream() {
        try {
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            LOGGER.error(CREATE_REPORT_ERROR_MESSAGE);
            LOGGER.error(e.getMessage());
        }
    }
}

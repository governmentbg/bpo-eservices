package bg.duosoft.taxcalculator.rest;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailDateDeserializer extends JsonDeserializer<Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailDateDeserializer.class);

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String val = jsonParser.getText();
        if (val != null && !val.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            try {
                return sdf.parse(val);
            } catch (ParseException e) {
                LOGGER.error("Error deserializing date object", e);
            }
        }
        return null;
    }
}

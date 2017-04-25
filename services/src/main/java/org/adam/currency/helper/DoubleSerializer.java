package org.adam.currency.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class DoubleSerializer extends JsonSerializer<Double> {
    @Override
    public void serialize(Double aDouble, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(aDouble == null) {
            jsonGenerator.writeString("");
            return;
        }
        jsonGenerator.writeString(aDouble.toString());
    }
}

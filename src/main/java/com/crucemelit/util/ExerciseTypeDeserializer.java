package com.crucemelit.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crucemelit.model.ExerciseType;
import com.crucemelit.service.ExerciseTypeService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Component
public class ExerciseTypeDeserializer extends JsonDeserializer<ExerciseType> {

    @Autowired
    private ExerciseTypeService exerciseTypeService;

    @Override
    public ExerciseType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        return exerciseTypeService.getExerciseType(node.asLong());
    }

}

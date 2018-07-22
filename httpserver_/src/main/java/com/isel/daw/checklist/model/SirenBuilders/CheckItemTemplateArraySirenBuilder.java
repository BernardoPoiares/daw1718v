package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;

import java.io.IOException;
import java.io.InputStream;

public class CheckItemTemplateArraySirenBuilder {
    private static ObjectMapper mapper = new ObjectMapper();

    private static JsonNode createRoot(){
        JsonNode rootNode=null;
        try {
            InputStream checkitem_streamstruct= com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder.class.getClassLoader()
                    .getResourceAsStream("SirenBaseStructs/CheckItemTemplate_SirenStruct.json");

            rootNode = mapper.readTree(checkitem_streamstruct);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return rootNode;
    }

    public static JsonNode build(CheckItemTemplateRequestDto[] checkitemtemplates){
        JsonNode root=createRoot();
        ArrayNode array = mapper.valueToTree(checkitemtemplates);
        ((ObjectNode)root).putArray("properties").addAll(array);
        return root;
    }


}


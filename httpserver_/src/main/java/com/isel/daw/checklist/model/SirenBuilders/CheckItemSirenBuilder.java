package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CheckItemSirenBuilder {
    private static ObjectMapper mapper = new ObjectMapper();

    private static JsonNode createRoot(){
        JsonNode rootNode=null;
        try {
           InputStream checkitem_streamstruct=CheckItemSirenBuilder.class.getClassLoader()
                            .getResourceAsStream("SirenBaseStructs/checkitem_sirenstruct.json");

        rootNode = mapper.readTree(checkitem_streamstruct);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return rootNode;
    }

    public static JsonNode build(long id,String name,String description,String state){
        JsonNode root=createRoot();
        ((ObjectNode)(root.path("properties"))).put("id",id);
        ((ObjectNode)(root.path("properties"))).put("name",name);
        ((ObjectNode)(root.path("properties"))).put("description",description);
        ((ObjectNode)(root.path("properties"))).put("state",state);
        return root;
    }

    public static JsonNode build(CheckItemRequestDto checkitem){
        JsonNode root=createRoot();
        JsonNode node = mapper.valueToTree(checkitem);
        ((ObjectNode)root).set("properties",node);
        return root;
    }


}

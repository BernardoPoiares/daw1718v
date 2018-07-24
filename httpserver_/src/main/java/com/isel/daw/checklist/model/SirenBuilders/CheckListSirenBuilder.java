package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class CheckListSirenBuilder {
    private static ObjectMapper mapper = new ObjectMapper();

    private static JsonNode createRoot(){
        JsonNode rootNode=null;
        try {
            InputStream checklist_streamstruct=CheckListSirenBuilder.class.getClassLoader()
                    .getResourceAsStream("SirenBaseStructs/checklist_sirenstruct.json");

            rootNode = mapper.readTree(checklist_streamstruct);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return rootNode;
    }

    public static JsonNode build(long id, String name, Date completionDate){
        JsonNode root=createRoot();
        ((ObjectNode)(root.path("properties"))).put("id",id);
        ((ObjectNode)(root.path("properties"))).put("name",name);
        ((ObjectNode)(root.path("properties"))).put("completionDate", String.valueOf(completionDate));
        return root;
    }

    public static JsonNode build(CheckListRequestDto checklist){
        JsonNode root=createRoot();
        JsonNode node = mapper.valueToTree(checklist);
        ((ObjectNode)root).set("properties",node);
        return root;
    }
}

package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class CheckListTemplateSirenBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    private static JsonNode createRoot(){
        JsonNode rootNode=null;
        try {
            InputStream checklist_streamstruct=CheckListSirenBuilder.class.getClassLoader()
                    .getResourceAsStream("SirenBaseStructs/checklisttemplate_sirenstruct.json");

            rootNode = mapper.readTree(checklist_streamstruct);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return rootNode;
    }

    public static JsonNode build(long id, String name){

        JsonNode root=createRoot();
        ((ObjectNode)(root.path("properties"))).put("id",id);
        ((ObjectNode)(root.path("properties"))).put("name",name);
        return root;
    }
}

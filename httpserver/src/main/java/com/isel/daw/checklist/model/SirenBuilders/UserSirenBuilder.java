package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.InputStream;

public class UserSirenBuilder {

    private static ObjectMapper mapper = new ObjectMapper();

    private static JsonNode createRoot(){
        JsonNode rootNode=null;
        try {
            InputStream user_streamstruct=CheckListSirenBuilder.class.getClassLoader()
                    .getResourceAsStream("SirenBaseStructs/user_sirenstruct.json");
            rootNode = mapper.readTree(user_streamstruct);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return rootNode;
    }

    public static JsonNode build(long id,String username){
        JsonNode root=createRoot();
        ((ObjectNode)(root.path("properties"))).put("id",id);
        ((ObjectNode)(root.path("properties"))).put("username",username);
        return root;
    }
}

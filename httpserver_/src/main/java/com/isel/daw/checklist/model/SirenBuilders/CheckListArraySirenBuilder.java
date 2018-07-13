package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;

import java.io.IOException;
import java.io.InputStream;

public class CheckListArraySirenBuilder {
    private static ObjectMapper mapper = new ObjectMapper();

    private static JsonNode createRoot(){
        JsonNode rootNode=null;
        try {
            InputStream checkitem_streamstruct= com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder.class.getClassLoader()
                    .getResourceAsStream("SirenBaseStructs/checklistarray_sirenstruct.json");

            rootNode = mapper.readTree(checkitem_streamstruct);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        return rootNode;
    }

    public static JsonNode build(CheckListRequestDto[] checklists){
        JsonNode root=createRoot();
           /* for(CheckItem checkItem:checkItems){
                JsonNode jsonnode=mapper.createObjectNode();
                ((ObjectNode)jsonnode).put("id",checkItem.getId());
                ((ObjectNode)jsonnode).put("name",checkItem.getCheckitem_itemtemplate().getName());
                ((ObjectNode)jsonnode).put("description",checkItem.getCheckitem_itemtemplate().getDescription());
                ((ObjectNode)jsonnode).put("state",checkItem.getState());
                mapper.convertValue(checkItem, JsonNode.class);

                ((ObjectNode)(root)).set("properties",jsonnode);*/

        //List<CheckItemRequestDto> e = Arrays.asList(checkItems);
        ArrayNode array = mapper.valueToTree(checklists);
        ((ObjectNode)root).putArray("properties").addAll(array);
        return root;
    }


}


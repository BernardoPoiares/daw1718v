package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class CheckItemArraySirenBuilder {
        private static ObjectMapper mapper = new ObjectMapper();

        private static JsonNode createRoot(){
            JsonNode rootNode=null;
            try {
                InputStream checkitem_streamstruct= com.isel.daw.checklist.model.SirenBuilders.CheckItemSirenBuilder.class.getClassLoader()
                        .getResourceAsStream("SirenBaseStructs/checkitem_sirenstruct.json");

                rootNode = mapper.readTree(checkitem_streamstruct);
            }catch (IOException ex){
                System.out.println(ex.getMessage());
            }
            return rootNode;
        }

        public static JsonNode build(CheckItemRequestDto[] checkItems){
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
            ArrayNode array = mapper.valueToTree(checkItems);
            ((ObjectNode)root).putArray("properties").addAll(array);
            return root;
        }


}

package com.isel.daw.checklist.model.SirenBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemTemplateRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckList;
import com.isel.daw.checklist.model.RequestsDTO.CheckItemRequestDto;
import com.isel.daw.checklist.model.RequestsDTO.CheckListRequestDto;

import java.io.IOException;
import java.io.InputStream;

public class CheckItemTemplateSirenBuilder {
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

    public static JsonNode build(CheckItemTemplateRequestDto checkitemtemplate){
        JsonNode root=createRoot();
        JsonNode node = mapper.valueToTree(checkitemtemplate);
        ((ObjectNode)root).set("properties",node);
        return root;
    }


}


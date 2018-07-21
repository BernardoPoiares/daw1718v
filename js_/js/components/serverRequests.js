import request from './request'

module.exports={
    CreateCheckItem,
    UpdateCheckItem,
    DeleteCheckItems,
    SearchCheckItems,
    CreateCheckList,
    DeleteCheckLists,
    GetCheckList,
    UpdateCheckList,
    SearchCheckLists,
    GetCheckItemsFromList,
    CreateCheckItem_AddCheckList,
    RemoveCheckItemsFromCheckList
}

const CHECKITEM_CREATE_PATH='/checkItemTemplate_checkitem/create'
const CHECKITEM_UPDATE_PATH='/checkItemTemplate_checkitem/update'
const CHECKITEM_DELETE_PATH='/checkItemTemplate_checkitem/various'
const CHECKITEM_SEARCH_PATH='/checkItem/search'
const CHECKITEM_SEARCH_BY_LIST_PATH='/checkItem/searchByList'

const CHECKITEM_CHECKLIST_CREATEANDADD='/checkItem_CheckList/createAndAdd'

const CHECKLIST_CREATE_PATH="/checkList/create"
const CHECKLIST_DELETE_PATH="/checkList/various"
const CHECKLIST_UPDATE_PATH="/checkList/update"
const CHECKLIST_SEARCH_PATH="/checkList/search"
const CHECKLIST_GET_PATH="/checkList"

const CHECKITEMS_CHECKLISTS_REMOVE_PATH='/checkItem_CheckList/deleteCheckItems'

function CreateCheckItem(checkitem){
    return request(CHECKITEM_CREATE_PATH,'POST',{
        checkitemtemplate : {
            name:checkitem.name,
            description: checkitem.description
        }
    })
}

function UpdateCheckItem(checkitem){
    return request(CHECKITEM_UPDATE_PATH,'POST',{id:checkitem.id,state:checkitem.state,
        checkitemtemplate:{name:checkitem.name,description:checkitem.description}
    })
}

function DeleteCheckItems(checkitems){
    const array=[]
    checkitems.map(ci=>
            array.push({id:ci})
    )
    return request(CHECKITEM_DELETE_PATH,'DELETE',array)
}

function SearchCheckItems(search){
    return request(CHECKITEM_SEARCH_PATH+"?name="+search,'GET')
}

function GetCheckItemsFromList(checklist_id){
    return request(CHECKITEM_SEARCH_BY_LIST_PATH+"?id="+checklist_id,'GET')
}

function CreateCheckList(checklist){
    return request(CHECKLIST_CREATE_PATH,'POST',checklist)
}


function DeleteCheckLists(checklists){
    const array=[]
    checklists.map(cl=>
            array.push({id:cl})
    )
    return request(CHECKLIST_DELETE_PATH,'DELETE',array)
}

function GetCheckList(checklist){
    return request(CHECKLIST_GET_PATH+"/"+checklist,'GET')
}

function UpdateCheckList(checklist){
    return request(CHECKLIST_UPDATE_PATH,'POST',checklist)
}

function SearchCheckLists(search){
    return request(CHECKLIST_SEARCH_PATH+"?name="+search,'GET')
}
function CreateCheckItem_AddCheckList(checklist,checkitem){
    return request(CHECKITEM_CHECKLIST_CREATEANDADD,'POST',{id:checklist,
        checkitems:[{checkitemtemplate:{
            name:checkitem.name,
            description:checkitem.description}
        }
        ]
    }
    )
}

function RemoveCheckItemsFromCheckList(checklist,checkitems){
    const req={id:checklist,checkitems:[]}
    checkitems.map(ci=>{
        req.checkitems.push({id:ci})
    })
    console.log(req)
    return request(CHECKITEMS_CHECKLISTS_REMOVE_PATH,'DELETE',req)
}


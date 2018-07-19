import request from './request'

module.exports={
    UpdateCheckItem,
    DeleteCheckItems,
    SearchCheckItems,
    CreateCheckList,
    DeleteCheckLists,
    GetCheckList,
    UpdateCheckList
}


const CHECKITEM_UPDATE_PATH='/checkItemTemplate_checkitem/update'
const CHECKITEM_DELETE_PATH='/checkItemTemplate_checkitem/various'
const CHECKITEM_SEARCH_PATH='/checkItem/search'

const CHECKLIST_CREATE_PATH="/checkList/create"
const CHECKLIST_DELETE_PATH="/checkList/various"
const CHECKLIST_UPDATE_PATH="/checkList/update"
const CHECKLIST_GET_PATH="/checkList"

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


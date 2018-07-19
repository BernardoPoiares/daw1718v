import request from './request'

module.exports={
    UpdateCheckItem,
    DeleteCheckItems,
    CreateCheckList
}


const CHECKITEM_UPDATE_PATH='/checkItemTemplate_checkitem/update'
const CHECKITEM_DELETE_PATH='/checkItemTemplate_checkitem/various'


const CHECKLIST_CREATE_PATH="/checkList/create"

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

function CreateCheckList(checklist){
    return request(CHECKLIST_CREATE_PATH,'POST',checklist)
}
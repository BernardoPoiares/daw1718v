import request from './request'

module.exports={
    UpdateCheckItem,
    DeleteCheckItems
}


const CHECKITEM_UPDATE_PATH='/checkItemTemplate_checkitem/update'
const CHECKITEM_DELETE_PATH='/checkItemTemplate_checkitem/various'

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
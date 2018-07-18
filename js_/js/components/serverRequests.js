import request from './request'

module.exports={
    UpdateCheckItem
}


const CHECKITEM_UPDATE_PATH='/checkItemTemplate_checkitem/update'


function UpdateCheckItem(checkitem){
    return request(CHECKITEM_UPDATE_PATH,'POST',{id:checkitem.id,
        checkitemtemplate:{name:checkitem.name,description:checkitem.description}
    })
    .then((resp=>{
        return resp.json().then(json=>{
            
        })
        }))
}
'use strict'

module.exports = CheckItem

function CheckItem(checkitem) {
    console.log(checkitem)
    this.id=checkitem.id
    this.name=checkitem.checkitemtemplate.name
    this.description=checkitem.checkitemtemplate.description
    this.state=checkitem.state
}
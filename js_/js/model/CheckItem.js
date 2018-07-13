'use strict'

module.exports = CheckItem

function CheckItem(checkitem) {
    this.id=checkitem.id
    this.name=checkitem.checkitemtemplate.name
    this.description=checkitem.checkitemtemplate.description
    this.state=checkitem.state
}
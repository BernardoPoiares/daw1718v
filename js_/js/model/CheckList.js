'use strict'

module.exports = CheckList

function CheckList(checklist) {
    this.id=checklist.id
    this.name=checklist.name
    this.completionDate=new Date(checklist.completionDate)
}
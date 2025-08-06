package com.example.realm

import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Maleta: RealmObject {
    @PrimaryKey
    var Idm: String = ""
    var peso: String = ""
    var typo: String = ""
    var transporte: String = ""
    var noViaje: String = ""

    override fun toString(): String{
        return "(ID: ${Idm}), $typo"
    }


}
package com.example.realm

import io.realm.kotlin.types.annotations.PrimaryKey
import io.realm.kotlin.types.RealmObject

class Usuario: RealmObject{
    @PrimaryKey
    var id: Int = 0

    var nombre: String = ""
    var edad: Int = 0
}
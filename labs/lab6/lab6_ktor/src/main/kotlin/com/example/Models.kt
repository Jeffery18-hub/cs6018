package com.example

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.sql.Timestamp


object Post: IntIdTable(){ // object for singletons
    val text = varchar("text", 1000)
    val postTime = long("postTime")
}

package com.example

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.firstValue
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.Table.Dual.entityId
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Application.configureResources() {
    install(Resources)
    routing{

        get("/posts") {
            //handler for /posts
            call.respond(
                newSuspendedTransaction(Dispatchers.IO) {
                    Post.selectAll()
                        .map { PostData2( it[Post.id].value,it[Post.text], it[Post.postTime]) }
                }
            )
        }


        get("/posts/{id}") {
            val idParam = call.parameters["id"]
            // Convert idParam to Int:
            val intId = idParam?.toIntOrNull()
            if (intId != null) {
                call.respond(
                    newSuspendedTransaction(Dispatchers.IO) {
                        Post.select { Post.id eq EntityID(intId, Post) }
                            .map { PostData2( it[Post.id].value,it[Post.text], it[Post.postTime]) }
                    }
                )
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }
        }

        get("/posts/post") {
            val timeParam = call.request.queryParameters["since"]
            // Convert timeParam to Long:
            val longTime = timeParam?.toLongOrNull()
            println("Parsed time: $longTime")

            if (longTime != null) {
                call.respond(
                    newSuspendedTransaction(Dispatchers.IO) {
                        Post.select{ Post.postTime greaterEq longTime }
                            .map { PostData2( it[Post.id].value,it[Post.text], it[Post.postTime]) }
                    }
                )
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid time")
            }
        }

        post("/posts/") {
            val textInput = call.receive<PostData>().text
            val timeInput = System.currentTimeMillis()
            var postId = -1
            newSuspendedTransaction(Dispatchers.IO, DBSettings.db) { // explicitly pass DB, it doesn't matter here
                postId = Post.insert {
                    it[text] = textInput
                    it[postTime] = timeInput
                }[Post.id].value // get the id, because insert returns a row
            }

            call.respondText("Post successfully on unix epoch time $timeInput 's with post id $postId")
        }

        delete("/posts/{id}") {
            val idParam = call.parameters["id"]
            val intId = idParam?.toIntOrNull()
            var deleteCount = 0
            if (intId != null) {
                newSuspendedTransaction(Dispatchers.IO, DBSettings.db) {
                    deleteCount = Post.deleteWhere { Post.id eq EntityID(intId, Post) }
                }
            }else{
                call.respond(HttpStatusCode.BadRequest, "Invalid ID")
            }

            if (deleteCount > 0) {
                call.respondText("Post deleted with id $intId")
            }else{
                call.respond(HttpStatusCode.NotFound, "Post not found")
            }

        }

    }
}


@Serializable data class PostData(val text: String) // for post method
@Serializable data class PostData2(val id: Int, val text: String, val time: Long) // for get method
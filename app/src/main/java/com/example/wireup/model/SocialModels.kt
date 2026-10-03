package com.example.wireup.model

import java.util.UUID

data class Tweet(
    val id: String = UUID.randomUUID().toString(),
    val description: String = "",
    val userId: String = "",
    val comments: MutableList<Comment> = mutableListOf(),
    val likeCount: Int = 0,
    val retweetCount: Int = 0,
    val bookmarkCount: Int = 0,
    val imageUrl: String? = null,
    val timeStamp: Long = System.currentTimeMillis()
)

data class Comment(
    val id: String = UUID.randomUUID().toString(),
    val description: String = "",
    val userId: String = "",
    val imageUrl: String? = null,
    val likeCount: Int = 0,
    val likes: MutableList<String> = mutableListOf(),
    val timeStamp: Long = System.currentTimeMillis()
)

data class VideoPodcast(
    val id: String = "",
    val imageUrl: String = "",
    val title: String = "",
    val videoLink: String = ""
)

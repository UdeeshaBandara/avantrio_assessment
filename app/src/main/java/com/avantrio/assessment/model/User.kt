package com.avantrio.assessment.model

class User : ArrayList<UserItem>()

data class UserItem(
    val id: Int,
    val name: String
)
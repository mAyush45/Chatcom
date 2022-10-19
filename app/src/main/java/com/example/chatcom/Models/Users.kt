package com.example.chatcom.Models

class Users {

    var profilePic:String? = null
    var username:String? = null
    var mail:String? = null
    var password:String? = null
    var userId:String? = null
    var lastMessage:String? = null

    constructor(
        profilePic: String?,
        username: String?,
        mail: String?,
        password: String?,
        userId: String?,
        lastMessage: String?
    ) {
        this.profilePic = profilePic
        this.username = username
        this.mail = mail
        this.password = password
        this.userId = userId
        this.lastMessage = lastMessage
    }

    constructor(){}

    //SignUp Constructor
    constructor(
        username: String?,
        mail: String?,
        password: String?
    ) {
        this.username = username
        this.mail = mail
        this.password = password
    }



}
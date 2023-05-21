package com.example.searchplant.model

data class Articles ( private var titleArticles:String ?= null,
                      private var imageArticles: String?= null,
                      private var Description:String ?= null,
                      private var properties:String ?= null,
                      private var type:String ?= null,){
    fun getTitleArticles(): String? {
        return this.titleArticles
    }
    fun getImageArticles(): String? {
        return this.imageArticles
    }
    fun getDescription(): String? {
        return this.Description
    }
    fun getProperties(): String? {
        return this.properties
    }
    fun getType(): String? {
        return this.type
    }
}
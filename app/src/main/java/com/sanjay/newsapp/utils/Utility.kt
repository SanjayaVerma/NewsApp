package com.sanjay.newsapp.utils

import org.json.JSONObject

class Utility {
    companion object{
        fun getErrorResponseMessage(errorBody: String): String {
            return JSONObject(errorBody).getString("msg")
        }
    }

}
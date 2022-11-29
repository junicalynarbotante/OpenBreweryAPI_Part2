package com.example.openbreweryapi.services.helper

class NullHelper {
    companion object {
        fun checkNull(variable: Any?): String {
            if (variable == null) {
                return "N/A"
            } else {
                return variable as String
            }
        }
    }
}
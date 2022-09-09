package com.avijit.avijitassignment

import java.io.Serializable

data class Data(var src   : String?          = null,
                  var clips : ArrayList<Clips> = arrayListOf()): Serializable

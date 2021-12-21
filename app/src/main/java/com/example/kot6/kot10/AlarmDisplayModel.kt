package com.example.kot6.kot10

data class AlarmDisplayModel (
    val hour:Int,
    val min : Int,
    var onOff: Boolean
){
    val timeText:String
        get(){
            val h = "%02d".format(if(hour<12)hour else hour -12) //스트링 포매팅
            val m = "%02d".format(min)
            return "$h : $m"
        }

    val ampmText:String
        get(){
            return if(hour<12)"AM" else "PM"
        }

    val onOffText:String
        get(){
            return if (onOff)"알람 끄기" else "알람 켜기"
        }

    fun makeDataForDB():String{
        return "$hour:$min"
    }
}

package com.example.kot6

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutinesTest01 {

    @Test
    fun test01() = runBlocking{
        val time= measureTimeMillis {
            val fn = getFirstName()
            val ln = getLastName()
            println("Hello, $fn $ln")
        }
        println("t: "+time)
    }

    @Test
    fun test02() = runBlocking{
        val time= measureTimeMillis {
            val fn = async{getFirstName()}
            val ln = async{getLastName()}
            println("Hello, ${fn.await()} ${ln.await()}")
        }
        println("t: "+time)
    }
    suspend fun getFirstName():String{
        delay(1000)
        return "이"
    }

    suspend fun getLastName():String{
        delay(1000)
        return "기정"
    }

}
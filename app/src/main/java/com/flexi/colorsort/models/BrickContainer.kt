package com.flexi.colorsort.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color

class BrickContainer(val id:Int, val initialBricks:List<Color>, selected:Boolean){
    var selected by mutableStateOf(selected)
    var bricks = initialBricks.toMutableStateList()

    fun canPop():Boolean{
        return bricks.isNotEmpty()
    }

    fun canPush(maxSize:Int, color: Color):Boolean{
        if(bricks.isEmpty())
        {
            return true
        }

        return bricks.size < maxSize && color == bricks[0]
    }
}
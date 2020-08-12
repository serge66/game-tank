package com.github.serge66.business

import com.github.serge66.enums.Direction
import com.github.serge66.model.View

/**
 * 自动移动的能力
 */
interface AutoMove :View{
    //具备方向
    val currentDirection: Direction
    //具备速度
    val speed: Int

    //具备移动能力
    fun autoMove()
}
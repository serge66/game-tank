package com.github.serge66.business

import com.github.serge66.enums.Direction
import com.github.serge66.model.View

/**
 * 移动运动的能力
 */
interface Movable : View {
    /**
     * 可移动的物体存在方向
     */
    val currentDirection: Direction

    /**
     * 可移动的物体存在运动的速度
     */
    val speed: Int

    /**
     *判断移动的物体是否和阻塞物发生碰撞
     * @return 要碰撞的方向，如果为 null，说明没有碰撞
     */
    fun willCollision(block: Blockable): Direction?

    /**
     *通知发生碰撞
     */
    fun notityCollision(direction: Direction?, block: Blockable?)
}
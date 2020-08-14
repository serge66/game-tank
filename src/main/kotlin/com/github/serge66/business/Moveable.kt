package com.github.serge66.business

import com.github.serge66.Config
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
    fun willCollision(block: Blockable): Direction? {
        //提前判断是否碰撞
        //未来坐标
        var x = this.x
        var y = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        //屏幕边界检测
        //防止坦克越界
        if (x < 0) return Direction.LEFT
        if (x > Config.gameWidth - width) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.gameHeight - height) return Direction.DOWN

        //检测碰撞
        var collision = checkCollision(x, y, width, height, block.x, block.y, block.width, block.height)
        return if (collision) currentDirection else null
    }

    /**
     *通知发生碰撞
     */
    fun notityCollision(direction: Direction?, block: Blockable?)
}
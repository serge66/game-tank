package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.AutoMove
import com.github.serge66.enums.Direction
import org.itheima.kotlin.game.core.Painter


/**
 * 子弹对象
 */
class Bullet(override var currentDirection: Direction, creat: (width: Int, height: Int) -> Pair<Int, Int>) : AutoMove {
    override var x: Int = 0
    override var y: Int = 0
    override val height: Int
    override val width: Int
    override val speed: Int = 8
    private val imgPath = when (currentDirection) {
        Direction.UP -> "img/shot_top.gif"
        Direction.LEFT -> "img/shot_left.gif"
        Direction.RIGHT -> "img/shot_right.gif"
        Direction.DOWN -> "img/shot_bottom.gif"
    }

    init {
        //获得图标大小
        val size = Painter.size(imgPath)
        width = size[0]
        height = size[1]
        val pair = creat.invoke(width, height)
        x = pair.first
        y = pair.second
    }

    override fun draw() {
        //绘制子弹
        Painter.drawImage(imgPath, x, y)
    }

    override fun autoMove() {
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }
}
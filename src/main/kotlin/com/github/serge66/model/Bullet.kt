package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.enums.Direction
import org.itheima.kotlin.game.core.Painter


/**
 * 子弹对象
 */
class Bullet(private var direction: Direction, creat: () -> Pair<Int, Int>) : View {
    override val x: Int
    override val y: Int
    override val height: Int
    override val width: Int
    private val imgPath = when (direction) {
        Direction.UP -> "img/shot_up.gif"
        Direction.LEFT -> "img/shot_left.gif"
        Direction.RIGHT -> "img/shot_right.gif"
        Direction.DOWN -> "img/shot_down.gif"
    }
    init {
        //获得图标大小
        val size = Painter.size(imgPath)
        width = size[0]
        height = size[1]
        val pair = creat.invoke()
        x = pair.first
        y = pair.second
    }
    override fun draw() {
        //绘制子弹
        Painter.drawImage(imgPath, x, y)
    }
}
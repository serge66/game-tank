package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.Attackable
import com.github.serge66.business.AutoMove
import com.github.serge66.business.Destoryable
import com.github.serge66.business.Sufferable
import com.github.serge66.enums.Direction
import com.github.serge66.ext.checkCollision
import org.itheima.kotlin.game.core.Painter


/**
 * 子弹对象
 */
class Bullet(override var currentDirection: Direction, creat: (width: Int, height: Int) -> Pair<Int, Int>) : AutoMove,
    Destoryable, Attackable {

    override var x: Int = 0
    override var y: Int = 0
    override val height: Int
    override val width: Int
    override val speed: Int = 8
    override val attackPower: Int = 1
    //子弹是否已经被销毁
    private var isDestoryed = false
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

    override fun isDestory(): Boolean {
        if (isDestoryed) return true
        //子弹是否越界屏幕 是否被销毁
        if (x < -width) return true
        if (x > Config.gameWidth) return true
        if (y < -height) return true
        if (y > Config.gameHeight) return true
        return false
    }

    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notityAttack(sufferable: Sufferable) {
//        println("子弹发生碰撞了。。。。")
        //子弹攻击墙体后，自动销毁
        isDestoryed = true
    }
}
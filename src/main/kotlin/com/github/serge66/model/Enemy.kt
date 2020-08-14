package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.AutoMove
import com.github.serge66.business.Blockable
import com.github.serge66.business.Movable
import com.github.serge66.enums.Direction
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

/**
 * 敌方坦克
 * 具备移动能力
 * 具备自动移动能力
 * 具备阻塞能力
 */
class Enemy(override var x: Int, override var y: Int) : Movable, AutoMove ,Blockable{
    override val height: kotlin.Int = Config.block
    override val width: Int = Config.block
    override var currentDirection: Direction = Direction.DOWN
    override val speed: Int = 8

    //当前的发生碰撞的方向
    private var badDirection: Direction? = null

    override fun draw() {
        val imgPath = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(imgPath, x, y)
    }

    fun move(direction: Direction) {
        //如果当前移动的方向是要发生碰撞的方向 不执行
        if (this.badDirection == direction) {
            return
        }
        if (this.currentDirection != direction) {
            this.currentDirection = direction
            return
        }
        //坦克运动方向
        when (direction) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        //防止坦克越界
        if (x < 0) x = 0
        if (x > Config.gameWidth - width) x = Config.gameWidth - width
        if (y < 0) y = 0
        if (y > Config.gameHeight - height) y = Config.gameHeight - height
    }

    override fun notityCollision(direction: Direction?, block: Blockable?) {
        //收到碰撞通知
        this.badDirection = direction
    }

    override fun autoMove() {

        //判断当前方向和阻挡方向是否一致，一致则换方向
        if (currentDirection == badDirection) {
            currentDirection = rdmDirection(currentDirection)
            return
        }

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        //防止坦克越界
        if (x < 0) x = 0
        if (x > Config.gameWidth - width) x = Config.gameWidth - width
        if (y < 0) y = 0
        if (y > Config.gameHeight - height) y = Config.gameHeight - height

    }

    //随机转换方向
    private fun rdmDirection(direction: Direction): Direction {
        var index = (0 until 5).random()
        println("随机数：${index}")
        var d = when (index) {
            1 -> Direction.UP
            2 -> Direction.DOWN
            3 -> Direction.LEFT
            0 -> Direction.RIGHT
            else -> Direction.RIGHT
        }
        if (d == direction) {
            rdmDirection(d)
        }
        return d
    }
}
package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.enums.Direction
import org.itheima.kotlin.game.core.Painter
import java.awt.Paint

/**
 * 我方坦克
 */
class Tank(override var x: Int, override var y: Int) : View {
    override var height: Int = Config.block
    override var width: Int = Config.block
    private var currentDirection: Direction = Direction.UP
    //坦克速度
    private var speed: Int = 8

    override fun draw() {
        /*when (currentDirection) {
            Direction.UP -> Painter.drawImage("img/tank_u.gif", x, y)
            Direction.DOWN -> Painter.drawImage("img/tank_d.gif", x, y)
            Direction.LEFT -> Painter.drawImage("img/tank_l.gif", x, y)
            Direction.RIGHT -> Painter.drawImage("img/tank_r.gif", x, y)
        }*/
        val imgPath = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        Painter.drawImage(imgPath, x, y)
    }

    fun move(direction: Direction) {
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

}
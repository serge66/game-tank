package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.enums.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * 我方坦克
 */
class Tank(override var x: Int, override var y: Int) : View {
    override var height: Int = Config.block
    override var width: Int = Config.block
    private var currentDirection: Direction = Direction.UP
    override fun draw() {
        when (currentDirection) {
            Direction.UP -> Painter.drawImage("img/tank_u.gif", x, y)
            Direction.DOWN -> Painter.drawImage("img/tank_d.gif", x, y)
            Direction.LEFT -> Painter.drawImage("img/tank_l.gif", x, y)
            Direction.RIGHT -> Painter.drawImage("img/tank_r.gif", x, y)
        }
    }

}
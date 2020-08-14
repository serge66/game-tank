package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.Blockable
import com.github.serge66.business.Movable
import com.github.serge66.enums.Direction
import com.github.serge66.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

/**
 * 我方坦克
 * 具备运动能力
 */
class Tank(override var x: Int, override var y: Int) : Movable {
    override var height: Int = Config.block
    override var width: Int = Config.block
    override var currentDirection: Direction = Direction.UP
    //坦克速度
    override var speed: Int = 8
    //当前的发生碰撞的方向
    var badDirection: Direction? = null

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

    override fun willCollision(block: Blockable): Direction? {
        //提前判断是否碰撞
        var x = this.x
        var y = this.y

        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        //检测碰撞
        var collision = checkCollision(x, y, width, height, block.x, block.y, block.width, block.height)
        return if (collision) currentDirection else null
    }

    override fun notityCollision(direction: Direction?, block: Blockable?) {
        //收到碰撞通知
        this.badDirection = direction
    }

    //发射子弹的方法
    fun shot(): Bullet {
        //计算子弹真实的位置

        var bulletX: Int
        var bulletY: Int

        return Bullet(currentDirection) { bulletWidth, bulletHeight ->
            when (currentDirection) {
                Direction.UP -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = x - bulletWidth / 2
                    bulletY = y + (height - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = x - bulletWidth / 2 + width
                    bulletY = y + (height - bulletHeight) / 2
                }
                Direction.DOWN -> {
                    bulletX = x + (width - bulletWidth) / 2
                    bulletY = y + height - bulletHeight / 2
                }
            }
            Pair(bulletX, bulletY)
        }
    }
}
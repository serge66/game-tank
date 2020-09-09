package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.*
import com.github.serge66.enums.Direction
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

/**
 * 敌方坦克
 * 具备移动能力
 * 具备自动移动能力
 * 具备阻塞能力
 * 自动射击
 * 被击打
 */
class Enemy(override var x: Int, override var y: Int) : Movable, AutoMove, Blockable, AutoShot, Sufferable,
    Destoryable {

    override val height: kotlin.Int = Config.block
    override val width: Int = Config.block
    override var currentDirection: Direction = Direction.DOWN
    override val speed: Int = 8

    //当前的发生碰撞的方向
    private var badDirection: Direction? = null
    //子弹上次射击时间
    private var lastShotTime = 0L
    //子弹射击频率
    private var shotFrequency = 800
    //坦克上次移动时间
    private var lastMoveTime = 0L
    //坦克移动频率
    private var moveFrequency = 100
    //敌方坦克血量
    override var blood: Int = 6

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
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastMoveTime < moveFrequency) return
        lastMoveTime = currentTimeMillis

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
//        println("随机数：${index}")
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

    override fun shot(): View? {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastShotTime < shotFrequency) return null
        lastShotTime = currentTimeMillis

        //计算子弹真实的位置
        var bulletX: Int
        var bulletY: Int

        return Bullet(this, currentDirection) { bulletWidth, bulletHeight ->
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

    override fun notitySuffer(attackable: Attackable): Array<View>? {
        //如果攻击方是其他敌方，则不掉血
        if (attackable.owner is Enemy) {
            return null
        }
        blood -= attackable.attackPower
        return arrayOf(Blast(x, y))
    }

    override fun isDestory(): Boolean {
        return blood <= 0;
    }
}
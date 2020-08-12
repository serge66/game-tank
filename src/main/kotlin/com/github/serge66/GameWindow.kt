package com.github.serge66

import com.github.serge66.business.AutoMove
import com.github.serge66.business.Blockable
import com.github.serge66.business.Movable
import com.github.serge66.enums.Direction
import com.github.serge66.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.File

class GameWindow : Window(
    title = Config.gameName, icon = Config.gameIcon,
    height = Config.gameHeight, width = Config.gameWidth
) {

    //视图的集合
    private val views = arrayListOf<View>()
    private lateinit var tank: Tank;

    override fun onCreate() {
        var file = File(javaClass.getResource("/map/2.map").path)
        val readLines = file.readLines()
        var lineNum = 0
        readLines.forEach { line ->
            var columnNum = 0
            //空砖空砖空砖空砖空砖空砖空
            line.toCharArray().forEach { char ->

                when (char) {
                    '砖' -> views.add(Wall(columnNum * Config.block, lineNum * Config.block))
                    '铁' -> views.add(Steel(columnNum * Config.block, lineNum * Config.block))
                    '草' -> views.add(Grass(columnNum * Config.block, lineNum * Config.block))
                    '水' -> views.add(Water(columnNum * Config.block, lineNum * Config.block))
                }
                columnNum++
            }
            lineNum++
        }
        //绘制坦克
        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)


    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        when (event.code) {
            KeyCode.W -> {
                tank.move(Direction.UP)
            }
            KeyCode.A -> {
                tank.move(Direction.LEFT)
            }
            KeyCode.S -> {
                tank.move(Direction.DOWN)
            }
            KeyCode.D -> {
                tank.move(Direction.RIGHT)
            }
            KeyCode.ENTER -> {
                //发射子弹
                views.add(tank.shot())
            }
        }
    }

    override fun onRefresh() {
        //业务逻辑 耗时操作这里完成
        //判断运动的物体和阻塞物体是否发生碰撞
        //找到运动的物体
        views.filter { it is Movable }.forEach { move ->
            move as Movable
            //碰撞的方向
            var badDirection: Direction? = null
            //碰撞的 block
            var badBlock: Blockable? = null
            //找到阻塞的物体
            views.filter { it is Blockable }.forEach BlockableTag@{ block ->
                //遍历集合，判断是否发生碰撞
                block as Blockable
                val direction = move.willCollision(block)
                direction?.let {
                    badBlock = block
                    badDirection = direction
                    return@BlockableTag;
                }
            }
            move.notityCollision(badDirection, badBlock);
        }

        //让子弹自动移动
        views.filter { it is AutoMove }.forEach {

            (it as AutoMove).autoMove()
        }

    }
}
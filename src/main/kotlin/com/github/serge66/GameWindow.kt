package com.github.serge66

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
        var file = File(javaClass.getResource("/map/1.map").path)
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
        }
    }

    override fun onRefresh() {
    }
}
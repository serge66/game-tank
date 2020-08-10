package com.github.serge66

import com.github.serge66.model.*
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.File

class GameWindow : Window(
        title = Config.gameName, icon = Config.gameIcon,
        height = Config.gameHeight, width = Config.gameWidth
) {

    //视图的集合
    val views = arrayListOf<View>()

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

    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
    }

    override fun onRefresh() {
    }
}
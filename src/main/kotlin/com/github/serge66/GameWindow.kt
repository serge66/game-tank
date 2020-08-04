package com.github.serge66

import com.github.serge66.model.Grass
import com.github.serge66.model.Wall
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

class GameWindow : Window(
    title = Config.gameName, icon = Config.gameIcon,
    height = Config.gameHeight, width = Config.gameWidth
) {
    var wall = Wall()
    var grass = Grass()

    override fun onCreate() {

    }

    override fun onDisplay() {
        wall.draw()
        grass.draw()
    }

    override fun onKeyPressed(event: KeyEvent) {
    }

    override fun onRefresh() {
    }
}
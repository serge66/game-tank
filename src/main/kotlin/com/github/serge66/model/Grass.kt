package com.github.serge66.model

import com.github.serge66.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 草坪
 */
class Grass(override var x: Int, override var y: Int) : View {
    /*//位置
    override var x = 200
    override var y = 200*/
    //宽高
    override var height = Config.block
    override var width = Config.block

    //绘制
    override fun draw() {
        Painter.drawImage("img/grass.gif", x, y)
    }
}
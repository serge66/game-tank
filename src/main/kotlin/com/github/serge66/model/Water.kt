package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.Blockable
import org.itheima.kotlin.game.core.Painter

/**
 * 水墙
 */
class Water(override var x: Int, override var y: Int) : Blockable {
    /*//位置
    override var x: Int = 100
    override var y: Int = 100*/
    //宽高
    override var height: Int = Config.block
    override var width: Int = Config.block
    //绘制
    override fun draw() {
        Painter.drawImage("img/water.gif", x, y)
    }
}
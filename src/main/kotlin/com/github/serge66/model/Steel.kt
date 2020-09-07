package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.Attackable
import com.github.serge66.business.Blockable
import com.github.serge66.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 铁墙
 * 具备阻塞能力
 * 具备挨打能力
 */
class Steel(override var x: Int, override var y: Int) : Blockable, Sufferable {
    override val blood: Int = 1

    /*//位置
    override var x: Int = 100
    override var y: Int = 100*/
    //宽高
    override var height: Int = Config.block
    override var width: Int = Config.block
    //绘制
    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }

    override fun notitySuffer(attackable: Attackable): Array<View>? {
        return null
    }
}
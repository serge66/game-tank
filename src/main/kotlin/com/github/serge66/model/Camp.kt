package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.Attackable
import com.github.serge66.business.Blockable
import com.github.serge66.business.Destoryable
import com.github.serge66.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * 大本营
 * 具备阻挡功能
 * 具备被攻击功能
 * 具备被销毁的能力
 */
class Camp(override var x: Int, override var y: Int) : Blockable, Sufferable, Destoryable {

    override var blood: Int = 12

    override var height: Int = Config.block * 2
    override var width: Int = Config.block + 32

    override fun draw() {

        if (blood <= 3) {
            height = Config.block
            width = Config.block
            x = (Config.gameWidth - Config.block) / 2
            y = Config.gameHeight - Config.block
            Painter.drawImage("img/camp.gif", x, y)
        } else if (blood <= 6) {
            Painter.drawImage("img/wall_small.gif", x, y)
            Painter.drawImage("img/wall_small.gif", x + 32, y)
            Painter.drawImage("img/wall_small.gif", x + 32 * 2, y)
            Painter.drawImage("img/wall_small.gif", x + 32 * 3, y)
            Painter.drawImage("img/wall_small.gif", x, y + 32)
            Painter.drawImage("img/wall_small.gif", x, y + 32 * 2)
            Painter.drawImage("img/wall_small.gif", x + 32 * 3, y + 32)
            Painter.drawImage("img/wall_small.gif", x + 32 * 3, y + 32 * 2)
            Painter.drawImage("img/camp.gif", x + 32, y + 32)
        } else {
            Painter.drawImage("img/steel_small.gif", x, y)
            Painter.drawImage("img/steel_small.gif", x + 32, y)
            Painter.drawImage("img/steel_small.gif", x + 32 * 2, y)
            Painter.drawImage("img/steel_small.gif", x + 32 * 3, y)
            Painter.drawImage("img/steel_small.gif", x, y + 32)
            Painter.drawImage("img/steel_small.gif", x, y + 32 * 2)
            Painter.drawImage("img/steel_small.gif", x + 32 * 3, y + 32)
            Painter.drawImage("img/steel_small.gif", x + 32 * 3, y + 32 * 2)
            Painter.drawImage("img/camp.gif", x + 32, y + 32)
        }
    }

    override fun notitySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attackPower
        if (blood == 3 || blood == 6) {
            val x = x - 32
            val y = y - 32
            return arrayOf(
                Blast(x, y),
                Blast(x + 32, y),
                Blast(x + Config.block, y),
                Blast(x + Config.block + 32, y),
                Blast(x + Config.block * 2, y),
                Blast(x, y + 32),
                Blast(x, y + Config.block),
                Blast(x, y + Config.block + 32),
                Blast(x + Config.block * 2, y + 32),
                Blast(x + Config.block * 2, y + Config.block),
                Blast(x + Config.block * 2, y + Config.block + 32)
            )
        }
        return null
    }

    override fun isDestory(): Boolean = blood <= 0
    override fun showDestory(): Array<View>? {
        return arrayOf(
            Blast(x - 32, y - 32),
            Blast(x, y - 32),
            Blast(x + 32, y - 32),

            Blast(x - 32, y),
            Blast(x, y),
            Blast(x + 32, y),

            Blast(x - 32, y + 32),
            Blast(x, y + 32),
            Blast(x + 32, y + 32)
        )
    }
}
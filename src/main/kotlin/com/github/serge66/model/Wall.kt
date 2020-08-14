package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.Attackable
import com.github.serge66.business.Blockable
import com.github.serge66.business.Destoryable
import com.github.serge66.business.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

/**
 * 砖墙
 * 具备阻塞能力
 * 具备挨打能力
 * 具备销毁能力
 */
class Wall(override var x: Int, override var y: Int) : Blockable, Sufferable, Destoryable {

    /*//位置
    override var x: Int = 100
    override var y: Int = 100*/
    //宽高
    override var height: Int = Config.block
    override var width: Int = Config.block
    override var blood: Int = 3
    //绘制
    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }

    override fun notitySuffer(attackable: Attackable): Array<View> {
        println("砖墙被挨打了。。。。。。")
        //砖墙被攻击后，血量生命值会逐渐减少
        blood -= attackable.attackPower
        //被攻击后，喊疼
        Composer.play("snd/hit.wav")
        //展现爆炸效果
        return arrayOf(Blast(x, y))
    }

    //血量小于等于0时，被销毁
    override fun isDestory() = blood <= 0

}
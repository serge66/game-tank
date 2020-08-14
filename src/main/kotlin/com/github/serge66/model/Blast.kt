package com.github.serge66.model

import com.github.serge66.Config
import com.github.serge66.business.Destoryable
import org.itheima.kotlin.game.core.Painter
import javax.swing.ImageIcon

/**
 * 爆炸视图
 */
class Blast(override val x: Int, override val y: Int) : Destoryable {
    override val width: Int = Config.block
    override val height: Int = Config.block
    //爆炸物集合
    private var imgPaths = arrayListOf<String>()
    //爆炸物视图结合下标
    private var index: Int = 0

    init {
        //循环把32个爆炸视图加入集合
        (1..32).forEach {
            imgPaths.add("img/blast_${it}.png")
        }
    }

    override fun draw() {
        //超过集合数量，从0开始
        var i = index % imgPaths.size
        Painter.drawImage(imgPaths[i], x, y)
        index++
    }

    override fun isDestory(): Boolean {
        //当爆炸物 爆炸一遍时 销毁
        return index >= imgPaths.size
    }
}
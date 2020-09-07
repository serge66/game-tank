package com.github.serge66.business

import com.github.serge66.model.View

/**
 * 具备攻击的能力
 */
interface Attackable : View {
    //所有者
    val owner: View

    //具备攻击力
    val attackPower: Int

    //将要攻击
    fun isCollision(sufferable: Sufferable): Boolean

    //通知攻击
    fun notityAttack(sufferable: Sufferable)
}
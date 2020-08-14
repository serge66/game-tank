package com.github.serge66.business

import com.github.serge66.model.View

/**
 * 具备被攻击的能力
 */
interface Sufferable : View {
    //具备生命值
    val blood: Int

    //通知被攻击
    fun notitySuffer(attackable: Attackable):Array<View>?
}
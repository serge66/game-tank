package com.github.serge66.business

import com.github.serge66.model.View

/**
 * 销毁的能力
 */
interface Destoryable : View {
    fun isDestory(): Boolean
    //死给你看
    fun showDestory(): Array<View>? {
        return null
    }
}
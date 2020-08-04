package com.github.serge66.model

/*
* 视图绘制规范
* */
interface View {
    //位置
    var x: Int
    var y: Int

    //宽高
    var height: Int
    var width: Int

    //绘制
    fun draw()
}
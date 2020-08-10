package com.github.serge66.model

/*
* 视图绘制规范
* */
interface View {
    //位置
    val x: Int
    val y: Int

    //宽高
    val height: Int
    val width: Int

    //绘制
    fun draw()
}
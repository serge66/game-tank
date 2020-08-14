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

    //检测碰撞
    fun checkCollision(
        x1: Int, y1: Int, w1: Int, h1: Int,
        x2: Int, y2: Int, w2: Int, h2: Int
    ): Boolean {
        //比较两个物体的 x,y,w,h

        return when {
            y1 + h1 <= y2 -> {
                //如果阻挡物在运动物的上方时，不碰撞
                false
            }
            y2 + h2 <= y1 -> {
                //如果阻挡物在运动物的下方时，不碰撞
                false
            }
            x1 + w1 <= x2 -> {
                //如果阻挡物在运动物的左方时，不碰撞
                false
            }
            else -> x2 + w2 > x1
        }
    }
}
package com.github.serge66.ext

import com.github.serge66.model.View

/**
 * view 的扩展方法
 */
fun View.checkCollision(view: View): Boolean {
    return checkCollision(x, y, width, height, view.x, view.y, view.width, view.height)
}
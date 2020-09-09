package com.github.serge66

import com.github.serge66.business.*
import com.github.serge66.enums.Direction
import com.github.serge66.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window(
    title = Config.gameName, icon = Config.gameIcon,
    height = Config.gameHeight, width = Config.gameWidth
) {

    //管理视图的集合
//    private val views = arrayListOf<View>()
    //线程安全的视图集合
    private val views = CopyOnWriteArrayList<View>()
    private lateinit var tank: Tank;

    //游戏是否结束
    private var gameOver: Boolean = false

    //游戏中总共有多少敌方
    private var enemyTotalSize = 20

    //游戏中最多同时出现的敌方数量
    private var enemyActiveSize = 6

    //敌方出生点坐标
    private var enemyBornLocation = arrayListOf<Pair<Int, Int>>()

    //敌方出生点下标
    private var enemyIndex = 0

    override fun onCreate() {
//        var file = File(javaClass.getResource("/map/2.map").path)
        val resource = javaClass.getResourceAsStream("/map/2.map")
        val bufferedReader = BufferedReader(InputStreamReader(resource, "utf-8"))
        val readLines = bufferedReader.readLines()
        var lineNum = 0
        readLines.forEach { line ->
            var columnNum = 0
            //空砖空砖空砖空砖空砖空砖空
            line.toCharArray().forEach { char ->

                when (char) {
                    '砖' -> views.add(Wall(columnNum * Config.block, lineNum * Config.block))
                    '铁' -> views.add(Steel(columnNum * Config.block, lineNum * Config.block))
                    '草' -> views.add(Grass(columnNum * Config.block, lineNum * Config.block))
                    '水' -> views.add(Water(columnNum * Config.block, lineNum * Config.block))
                    '敌' -> enemyBornLocation.add(Pair(columnNum * Config.block, lineNum * Config.block))
                }
                columnNum++
            }
            lineNum++
        }
        //绘制坦克
        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)
        //绘制大本营
        var camp = Camp(Config.block * 6 - 32, Config.block * 12 - 32)
        views.add(camp)

    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
//        println(views.size)
    }

    override fun onKeyPressed(event: KeyEvent) {
        if (!gameOver) {
            when (event.code) {
                KeyCode.W -> {
                    tank.move(Direction.UP)
                }
                KeyCode.A -> {
                    tank.move(Direction.LEFT)
                }
                KeyCode.S -> {
                    tank.move(Direction.DOWN)
                }
                KeyCode.D -> {
                    tank.move(Direction.RIGHT)
                }
                KeyCode.ENTER -> {
                    //发射子弹
                    views.add(tank.shot())
                }
            }
        }
    }

    override fun onRefresh() {
        //业务逻辑 耗时操作这里完成
        //判断子弹是否需要回收，已经被销毁的子弹需要回收
        views.filter { it is Destoryable }.forEach { destory ->
            destory as Destoryable
            if (destory.isDestory()) {
                views.remove(destory)

                val showDestory = destory.showDestory();
                showDestory?.let {
                    views.addAll(showDestory)
                }

                if (destory is Enemy) {
                    enemyTotalSize--
                }
            }
        }

        if (gameOver) {
            return
        }
        //判断运动的物体和阻塞物体是否发生碰撞
        //找到运动的物体
        views.filter { it is Movable }.forEach { move ->
            move as Movable
            //碰撞的方向
            var badDirection: Direction? = null
            //碰撞的 block
            var badBlock: Blockable? = null
            //找到阻塞的物体
            //运动的物体不和自己比较
            views.filter { (it is Blockable) and (it != move) }.forEach blockTag@{ block ->
                //遍历集合，判断是否发生碰撞
                block as Blockable
                val direction = move.willCollision(block)
                direction?.let {
                    badBlock = block
                    badDirection = direction
                    return@blockTag;
                }
            }
            move.notityCollision(badDirection, badBlock);
        }

        //让子弹自动移动
        views.filter { it is AutoMove }.forEach {
            (it as AutoMove).autoMove()
        }

        //判断具备攻击能力和被攻击能力的物体间是否发生碰撞
        //找到具备攻击能力的物体
        views.filter { it is Attackable }.forEach { attack ->
            attack as Attackable
            //找到具备被攻击能力的物体(攻击方的源不可以是发射方)
            //攻击方如果也是受攻击方时 是不可以打自己的
            views.filter { (it is Sufferable) and (attack.owner != it) and (attack != it) }
                .forEach sufferTag@{ suffer ->
                    //判断是否进行碰撞
                    suffer as Sufferable
                    val collision = attack.isCollision(suffer)
                    if (collision) {
                        //如果发生碰撞，则通知两者
                        attack.notityAttack(suffer)
                        val blastList = suffer.notitySuffer(attack)
                        blastList?.let {
                            views.addAll(blastList)
                        }
                        return@sufferTag
                    }
                }
        }
        views.filter { it is AutoShot }.forEach {
            val shot = (it as AutoShot).shot();
            shot?.let {
                views.add(shot)
            }
        }
        //检测游戏是否结束
        if ((views.filter { it is Camp }.isEmpty()) or (enemyTotalSize <= 0)) {
            gameOver = true
        }
        //生成敌方坦克
        if ((enemyTotalSize >= enemyActiveSize) and (views.filter { it is Enemy }.size < enemyActiveSize)) {
            var index = enemyIndex % enemyBornLocation.size
            val pair = enemyBornLocation[index];
            //即将生成的坦克是否要发生坦克碰撞
            var c =false
            views.filter { (it is Enemy) or (it is Tank) }.forEach {
                //检测碰撞
                var collision = it.checkCollision(it.x, it.y, it.width, it.height, pair.first, pair.second, Config.block, Config.block)
                if(collision){
                    c =collision
                    return
                }
                /*if ((Math.abs(it.x - pair.first) < 150) or (Math.abs(it.y - pair.second) < 150)) {
                }*/
            }
            //即将碰撞
            if(!c){
                views.add(Enemy(pair.first, pair.second))
            }
            enemyIndex++
        }
    }
}
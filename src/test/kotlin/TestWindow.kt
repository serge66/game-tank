import javafx.application.Application
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

class TestWindow : Window() {
    override fun onCreate() {
        println("  onCreate  ")
    }

    override fun onDisplay() {
        println("  onDisplay  ")
    }

    override fun onKeyPressed(event: KeyEvent) {
        println("  onKeyPressed  ")
    }

    override fun onRefresh() {
        println("  onRefresh  ")
    }


}

fun main(args:Array<String>) {
    Application.launch(TestWindow::class.java)
}
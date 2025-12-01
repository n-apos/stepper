import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.napos.stepper.example.StepperExample
import com.napos.stepper.example.initializeKoin


@OptIn(ExperimentalComposeUiApi::class)
fun main() {

    initializeKoin()

    ComposeViewport(
        viewportContainerId = "root",
    ) {
        StepperExample()
    }

}
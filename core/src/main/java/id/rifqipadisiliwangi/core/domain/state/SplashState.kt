package id.rifqipadisiliwangi.core.domain.state

sealed class SplashState<out R> {
    data object OnBoarding: SplashState<Nothing>()
    data object Main: SplashState<Nothing>()
    data object Login: SplashState<Nothing>()
    data object Profile: SplashState<Nothing>()
}

fun <T>SplashState <T>.runOnboarding(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.OnBoarding){
        println("Condition OnBoarding")
        execute()
    }
}

fun <T>SplashState <T>.runMain(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.Main){
        println("Condition Main")
        execute()
    }
}

fun <T>SplashState <T>.runLogin(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.Login){
        println("Condition Login")
        execute()
    }
}

fun <T>SplashState <T>.runProfile(
    execute: () -> Unit
): SplashState<T> =apply {
    if (this is SplashState.Profile){
        println("Condition Profile")
        execute()
    }
}

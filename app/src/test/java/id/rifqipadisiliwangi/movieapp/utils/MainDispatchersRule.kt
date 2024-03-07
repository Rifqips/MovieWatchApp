package id.rifqipadisiliwangi.movieapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatchersRule: TestWatcher(), TestCoroutineScope by TestCoroutineScope(){
    private val testDispatchersRule: TestDispatcher = StandardTestDispatcher()
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatchersRule)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
}
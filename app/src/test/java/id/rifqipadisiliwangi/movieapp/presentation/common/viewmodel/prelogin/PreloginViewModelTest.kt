package id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.rifqipadisiliwangi.core.domain.usecase.AppPreferencesInteractor
import id.rifqipadisiliwangi.core.domain.usecase.FirebaseInteractor
import id.rifqipadisiliwangi.movieapp.utils.MainDispatchersRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PreloginViewModelTest{

    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()

    @get:Rule
    val coroutineRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PreloginViewModel
    private val preferences = Mockito.mock<AppPreferencesInteractor>()
    private val interactor = Mockito.mock<FirebaseInteractor>()

    @Before
    fun setUp(){
        viewModel = PreloginViewModel(preferences,interactor)
    }
    @Test
    fun `test login success`() = runTest {
        val email = "rifqipadi@gmail.com,"
        val password = "rifqips"
        var stateStatus: Boolean

        val onComplete: (Boolean, String?) -> Unit = { isSuccess, _ ->
            stateStatus = isSuccess
            assertTrue(stateStatus)

        }
        viewModel.loginWithEmailAndPassword(email, password, onComplete)
    }
    @Test
    fun `test register success`() = runTest {
        val email = "rifqipadi@gmail.com,"
        val password = "rifqips"
        var stateStatus: Boolean
        val onComplete: (Boolean, String?) -> Unit = { isSuccess, _ ->
            stateStatus = isSuccess
            assertTrue(stateStatus)
        }
        viewModel.registerWithEmailAndPassword(email, password, onComplete)
    }
}
package id.rifqipadisiliwangi.movieapp.presentation.feature.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.state.onError
import id.rifqipadisiliwangi.core.domain.state.onLoading
import id.rifqipadisiliwangi.core.domain.state.onSuccess
import id.rifqipadisiliwangi.core.utils.launchAndCollectIn
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentLoginBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin.PreloginViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment :
    BaseFragment<FragmentLoginBinding, PreloginViewModel>(FragmentLoginBinding::inflate) {

    override val viewModel: PreloginViewModel by viewModel()
    private var firebaseToken = ""

    override fun initView() {
        fcmFetch()
        doLogin()
        with(binding) {
            tvLogin.text = resources.getString(R.string.string_sign_in)
            etEmailInput.hint = resources.getString(R.string.string_email)
            etPasswordInput.hint = resources.getString(R.string.string_password)
            btnLogin.text = resources.getString(R.string.string_sign_in)
            tvRegister.text = resources.getString(R.string.string_sign_up)
            ivBackgroundLogin.load(R.drawable.bg_gradient)
        }
    }

    override fun initListener() {
        with(binding) {
            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

        }
    }

    override fun observeData() {
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            binding.etEmailInput.error = getString(R.string.string_email_tidak_valid)
            false
        } else {
            binding.etEmailInput.error = null
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.length <= 5) {
            binding.etPasswordInput.error = getString(R.string.string_password_tidak_valid)
            false
        } else {
            binding.etPasswordInput.error = null
            true
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmailEditLogin.text.toString().trim()
        val password = binding.etPasswordEditLogin.text.toString().trim()

        return validateEmail(email) && validatePassword(password)
    }

    private fun doLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailEditLogin.text.toString().trim()
            val password = binding.etPasswordEditLogin.text.toString().trim()
            viewModel.loginUser(email, password)
            viewModel.loginUser.launchAndCollectIn(viewLifecycleOwner){ state->
                state.onLoading {
                    Log.d("loginFragment", "onLoading")
                }
                    .onSuccess {
                        Log.d("loginFragment", "from fragment success state $it")
                        when(it){
                            true -> findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                            else -> Toast.makeText(context,resources.getString(R.string.string_failed_login), Toast.LENGTH_SHORT).show()
                        }
                    }
                    .onError {
                        Log.d("loginFragment", "onError")
                    }
            }
        }
    }

    private fun fcmFetch() {
        Firebase.messaging.token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val token = task.result
                firebaseToken = token
            },
        )
    }

    private fun analyticsFirebase(message: String, bundle: Bundle) {
        viewModel.logEvent(message, bundle)
        viewModel.debugSreenView(message)
    }

    override fun onResume() {
        super.onResume()
        val message = resources.getString(R.string.string_sign_in)
        val bundle =
            Bundle().apply { putString(resources.getString(R.string.string_screenname), message) }
        analyticsFirebase(message, bundle)
    }

}

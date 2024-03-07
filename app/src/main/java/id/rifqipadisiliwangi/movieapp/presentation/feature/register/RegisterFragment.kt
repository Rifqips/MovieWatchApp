package id.rifqipadisiliwangi.movieapp.presentation.feature.register

import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.state.onLoading
import id.rifqipadisiliwangi.core.domain.state.onSuccess
import id.rifqipadisiliwangi.core.utils.launchAndCollectIn
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentRegisterBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin.PreloginViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.REAL_LOCATION
import id.rifqipadisiliwangi.movieapp.utils.Constant.REGISTER_PARSE
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment :
    BaseFragment<FragmentRegisterBinding, PreloginViewModel>(FragmentRegisterBinding::inflate) {

    override val viewModel: PreloginViewModel by viewModel()
    private var sLocation: String = ""
    private var bundle = Bundle()

    override fun initView() {
        getBundle()
        with(binding) {
            tvTitleRegister.text = resources.getString(R.string.string_sign_up)
            etEmailInputRegister.hint = resources.getString(R.string.string_email)
            etPasswordInputRegister.hint = resources.getString(R.string.string_password)
            btnSignUp.text = resources.getString(R.string.string_sign_up)
            tvSignIn.text = resources.getString(R.string.string_sign_in)
            tvAddress.hint = resources.getString(R.string.string_address)
            ivBackgroundRegister.load(R.drawable.bg_gradient)
        }
    }

    private fun getBundle() {
        arguments.let {
            it?.getString(REAL_LOCATION).let { location ->
                binding.tvAddress.text = location
            }
        }
    }

    override fun initListener() {
        with(binding) {
            btnSignUp.setOnClickListener {
                doRegister()
            }
            tvSignIn.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            cvAddress.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_mapFragment)
            }
        }
    }

    override fun observeData() {
        with(viewModel) {
            getLocation()
            isLocation.observe(viewLifecycleOwner) {
                binding.tvAddress.text = it
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmailInputRegister.error = getString(R.string.string_email_tidak_valid)
            false
        } else {
            binding.etEmailInputRegister.error = null
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.length <= 5) {
            binding.etPasswordInputRegister.error = getString(R.string.string_password_tidak_valid)
            false
        } else {
            binding.etPasswordInputRegister.error = null
            true
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.etEmailEditRegister.text.toString().trim()
        val password = binding.etPasswordEditRegister.text.toString().trim()

        return validateEmail(email) && validatePassword(password)
    }

    private fun doRegister() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailEditRegister.text.toString()
            val password = binding.etPasswordEditRegister.text.toString()
            viewModel.register(email, password)
            viewModel.registerUser.launchAndCollectIn(viewLifecycleOwner){ state->
                state.onLoading {  }
                    .onSuccess {
                        when(it){
                            true ->{
                                navigateToProfile(sLocation, bundle)
                                findNavController().navigate(
                                    R.id.action_registerFragment_to_profileFragment,
                                    bundle
                                )
                            }
                            false -> Toast.makeText(context, resources.getString(R.string.string_failed_register), Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun analyticsFirebase(message: String, bundle: Bundle) {
        viewModel.logEvent(message, bundle)
        viewModel.debugSreenView(message)
    }

    override fun onResume() {
        super.onResume()
        val message = resources.getString(R.string.string_sign_up)
        val bundle =
            Bundle().apply { putString(resources.getString(R.string.string_screenname), message) }
        analyticsFirebase(message, bundle)
    }

    companion object {
        private fun navigateToProfile(sLocation: String, bun: Bundle) {
            bun.putString(REGISTER_PARSE, sLocation)
        }
    }
}
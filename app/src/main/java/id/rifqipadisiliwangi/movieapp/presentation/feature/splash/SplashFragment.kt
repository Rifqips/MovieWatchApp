package id.rifqipadisiliwangi.movieapp.presentation.feature.splash

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentSplashBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin.PreloginViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseFragment<FragmentSplashBinding, PreloginViewModel>(FragmentSplashBinding::inflate) {
    override val viewModel: PreloginViewModel by viewModel()

    override fun initView() {
        binding.ivBackgroundSplash.load("https://img.freepik.com/free-vector/dark-gradient-background-with-copy-space_53876-99548.jpg?w=900&t=st=1709105083~exp=1709105683~hmac=82a5b42ce1b9c91f23754e83776de8c46466f0ac8570348325b7f64ff6a02b34")

    }

    override fun initListener() {
        with(binding){
            tvTitleTheater.text = getString(R.string.string_title)
            tvSplash.text = getString(R.string.streing_streem_it)
        }
    }

    override fun observeData() {
        with(viewModel){
            lifecycleScope.launch {
                delay(2000)
                checkLoginStatus()
                stateLoggedin.observe(viewLifecycleOwner){state ->
                    when(state){
                        true -> findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                        false -> findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                    }
                }
            }

        }
    }

}
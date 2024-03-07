package id.rifqipadisiliwangi.movieapp.presentation.feature.setting

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentSettingBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingFragment : BaseFragment<FragmentSettingBinding, HomeViewModel>(FragmentSettingBinding::inflate) {

    override val viewModel: HomeViewModel by viewModel()

    override fun initView() {
        with(binding){
            tvSetting.text = resources.getString(R.string.string_settings)
            tvEn.text = resources.getString(R.string.string_en)
            tvId.text = resources.getString(R.string.string_id)
            btnLogout.text = resources.getString(R.string.string_logout)
            tvUsername.text = resources.getString(R.string.string_username_is_empty)
            tvUsermail.text = resources.getString(R.string.string_email_is_empty)
            tvLocation.text = resources.getString(R.string.string_email_is_empty)
        }
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener { findNavController().navigateUp() }
        binding.swLanguage.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setLocale(isChecked)
        }
    }

    override fun observeData() {
        with(viewModel){
            getUrl()
            fetchUserEmail()
            getLocation()
            getUsername()
            logoutUser()
            binding.btnLogout.setOnClickListener {
                stateLoggOut.observe(viewLifecycleOwner){state ->
                    if (state){ findNavController().navigate(R.id.action_settingFragment_to_loginFragment)}
                }
            }
            isUrl.observe(viewLifecycleOwner){  binding.ivUser.load(it) }
            isLocation.observe(viewLifecycleOwner){
                binding.tvLocation.text  = it
            }
            isUsername.observe(viewLifecycleOwner){ binding.tvUsername.text = it }
            userEmail.observe(viewLifecycleOwner){ email ->
                if (email != null) {
                    binding.tvUsermail.text = email
                } else{
                    binding.tvUsermail.text = resources.getString(R.string.string_dummy_email)
                }
            }
            appLocaleLiveData.observe(viewLifecycleOwner){ isActive ->
                binding.swLanguage.isChecked = isActive
            }
        }
    }
}
package id.rifqipadisiliwangi.movieapp.presentation.feature.profile

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentProfileBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin.PreloginViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.IMAGE_PARSE
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, PreloginViewModel>(FragmentProfileBinding::inflate) {

    override val viewModel: PreloginViewModel by viewModel()

    private var uriString : String = ""
    lateinit var storageReference : StorageReference
    lateinit var filePath: Uri

    override fun initView() {
        storageReference = Firebase.storage.reference
        getBundle()
        with(binding){
            ivUser.load(R.drawable.ic_person)
            tvRegister.text = resources.getString(R.string.string_set_up_profile)
            etNameInput.hint = resources.getString(R.string.string_your_name)
            btnProfile.text = resources.getString(R.string.string_create_account)
            ivBgProfile.load(R.drawable.bg_gradient)
            if (uriString.isNotEmpty()){
                val imageUri = Uri.parse(uriString)
                filePath = imageUri
                ivUser.load(imageUri)
            }
        }
    }

    private fun getBundle(){
        with(binding){
            arguments?.let {
                it.getString(IMAGE_PARSE)?.let { uri -> uriString = uri }
            }
        }
    }
    override fun initListener() {
        with(binding){
            ivUser.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_cameraFragment)
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                    ),
                    REQUEST_CODE_GALLERY
                )
            }
            btnProfile.setOnClickListener {
                val path : Uri = Uri.parse(uriString)
                val username = binding.etNameEditProfile.text.toString()
                viewModel.uploadImage(path)
                viewModel.addUsername(username)
            }
        }
    }

    override fun observeData() {
        viewModel.loading.observe(viewLifecycleOwner){ state->
            binding.pbProfile.isGone = false
            lifecycleScope.launch {
                delay(2000)
                if (!state) { findNavController().navigate(R.id.action_profileFragment_to_homeFragment) }
                binding.pbProfile.isGone = true
            }
        }
    }
    private fun analyticsFirebase(message : String, bundle : Bundle){
        viewModel.logEvent(message, bundle)
        viewModel.debugSreenView(message)
    }

    override fun onResume() {
        super.onResume()
        val message = resources.getString(R.string.string_set_up_profile)
        val bundle = Bundle().apply { putString(resources.getString(R.string.string_screenname), message) }
        analyticsFirebase(message, bundle)
    }

    companion object{
        private val REQUEST_CODE_GALLERY = 999
    }
}
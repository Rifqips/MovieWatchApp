package id.rifqipadisiliwangi.movieapp.presentation.feature.topup

import android.annotation.SuppressLint
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.google.gson.Gson
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.data.remote.data.payment.ResponsePaymentItem
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentTopUpBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.topup.TopUpAdapter
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.topup.TopUpViewModel
import id.rifqipadisiliwangi.movieapp.utils.Constant.toJson
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopUpFragment : BaseFragment<FragmentTopUpBinding, TopUpViewModel>(FragmentTopUpBinding::inflate) {

    override val viewModel: TopUpViewModel by viewModel()
    private val adapterTopUp: TopUpAdapter by lazy { TopUpAdapter(viewModel){ sTokenBalance = it } }
    private var itemList = mutableListOf<ResponsePaymentItem.ResponsePaymentItemItem>()
    private var sTokenBalance = "0"
    private var etResult = 0
    override fun initView() {
        with(binding){
            ivBack.load(R.drawable.ic_back)
            tvtopUp.text = resources.getString(R.string.string_top_up)
            btnTopUp.text = resources.getString(R.string.string_top_up)
            adapterTopUp.setData(itemList)
            rvTopUp.layoutManager = GridLayoutManager(context,3)
            rvTopUp.adapter = adapterTopUp
            etAmount.hint = resources.getString(R.string.string_enter_amount)
        }
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.etAmount.doOnTextChanged { text, start, before, count ->
            val length = text?.length ?: 0
            if (length >= 1) {
                etResult = text.toString().toInt()
                viewModel.updateStateMethod(false)
            } else{
                viewModel.updateStateMethod(true)
            }
        }
        binding.btnTopUp.setOnClickListener {
             updateToken()
            findNavController().navigate(R.id.action_topUpFragment_to_homeFragment)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeData() {
        with(viewModel) {
            fetchData()
            fetchUpdate()
            remoteResult.observe(viewLifecycleOwner) { paymentResult ->
                val gson = Gson()
                if (paymentResult.toJson().isNotEmpty()) {
                    val jsonModel = gson.fromJson(
                        paymentResult.toJson(),
                        Array<ResponsePaymentItem.ResponsePaymentItemItem>::class.java
                    ).toList()
                    itemList.clear()
                    itemList.addAll(jsonModel)
                    adapterTopUp.notifyDataSetChanged()
                }
            }
            remoteResultUpdate.observe(viewLifecycleOwner) { paymentResult ->
                val gson = Gson()
                if (paymentResult.toJson().isNotEmpty()) {
                    val jsonModel = gson.fromJson(
                        paymentResult.toJson(),
                        Array<ResponsePaymentItem.ResponsePaymentItemItem>::class.java
                    ).toList()
                    itemList.clear()
                    itemList.addAll(jsonModel)
                    adapterTopUp.notifyDataSetChanged()
                }
            }
        }
    }

    private fun updateToken(){
        viewModel.getTokenBalance()
        viewModel.isTokenBalance.observe(viewLifecycleOwner){
            if (it != ""){
                val result =  it.toInt() + sTokenBalance.toInt() + etResult.toString().toInt()
                addToken(result.toString())
            }
        }
    }

    private fun addToken(token : String){
        viewModel.saveTokenBalance(token)
    }

}
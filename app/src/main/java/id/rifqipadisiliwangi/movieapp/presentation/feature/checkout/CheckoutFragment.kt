package id.rifqipadisiliwangi.movieapp.presentation.feature.checkout

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionKey
import id.rifqipadisiliwangi.core.domain.model.checkout.ListCheckout
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentCheckoutBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.checkout.CheckoutAdapter
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutFragment :
    BaseFragment<FragmentCheckoutBinding, CartViewModel>(FragmentCheckoutBinding::inflate) {

    override val viewModel: CartViewModel by viewModel()
    private val args: CheckoutFragmentArgs by navArgs()
    private var productCheckout: ListCheckout = ListCheckout(emptyList())
    private var resultPrice = 0
    private var idProduct = ""
    private var items: ListCheckout = ListCheckout(emptyList())

    override fun initView() {
        setUpRv()
        updateToken()
        with(binding){
            ivBack.load(R.drawable.ic_back)
            tvCheckout.text = resources.getString(R.string.string_checkout)
            tvTitleTotalPayment.text = resources.getString(R.string.string_total)
            btnBuy.text = resources.getString(R.string.string_buy_now)
        }
    }

    override fun observeData() {
        viewModel.getTokenBalance()
        viewModel.isTokenBalance.observe(viewLifecycleOwner){ token -> }
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnBuy.setOnClickListener {
            addTransaction()
            findNavController().navigate(R.id.action_checkoutFragment_to_homeFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpRv(){
        productCheckout = args.listCheckoutItem
        binding.rvCheckout.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CheckoutAdapter(
            productCheckout.listCheckout,
            itemPrice = { item ->
                resultPrice += item.popularity.toInt()
                binding.tvTitleTotalPayment.text = resultPrice.toString()
                idProduct = item.id.toString()
            }, viewModel){}
        binding.rvCheckout.adapter = adapter

    }

    private fun addTransaction() {
        viewModel.insertTransaction(productCheckout.listCheckout.map { TransactionKey(
            it.id,
            it.backdropPath,
            it.originalLanguage,
            it.originalTitle,
            it.overview,
            it.popularity,
            it.posterPath,
            it.releaseDate,
            false,
        )
        })
        /**
         * need research
         * deleted only last id detected
         */
        viewModel.deleteById(idProduct)
    }
    private fun updateToken(){
        viewModel.getTokenBalance()
        viewModel.isTokenBalance.observe(viewLifecycleOwner){
            if (it.toInt() <= resultPrice){
                binding.btnBuy.isEnabled = false
                val color = ContextCompat.getColor(requireContext(), R.color.colorGray)
                binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
            }else{
                binding.btnBuy.isEnabled = true
                val color = ContextCompat.getColor(requireContext(), R.color.colorYellow)
                binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                if (it != ""){
                    val result =  it.toInt() - resultPrice
                    addToken(result.toString())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val bun = Bundle()
        bun.apply { putString(resources.getString(R.string.string_screenname), resources.getString(R.string.string_checkout)) }
        analyticsFirebase(resources.getString(R.string.string_checkout), bun)
    }

    private fun analyticsFirebase(message : String, bundle : Bundle){
        viewModel.logEvent(message, bundle)
        viewModel.debugSreenView(message)
    }

    private fun addToken(token : String){
        viewModel.saveTokenBalance(token)
    }
}
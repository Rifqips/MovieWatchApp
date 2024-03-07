package id.rifqipadisiliwangi.movieapp.presentation.feature.cart

import android.content.res.ColorStateList
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.core.domain.model.cart.KeyCart
import id.rifqipadisiliwangi.core.domain.model.checkout.CheckoutDataClass
import id.rifqipadisiliwangi.core.domain.model.checkout.ListCheckout
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentCartBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.cart.CartAdapter
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import id.rifqipadisiliwangi.movieapp.presentation.feature.checkout.CheckoutFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment :
    BaseFragment<FragmentCartBinding, CartViewModel>(FragmentCartBinding::inflate) {

    override val viewModel: CartViewModel by viewModel()
    private val itemList = listOf<KeyCart>()
    private lateinit var adapterCart: CartAdapter
    private lateinit var listCheckout: ArrayList<CheckoutDataClass>
    private var productCheckout: ListCheckout = ListCheckout(emptyList())
    private var resultPrice = 0

    override fun initView() {
        observeData()
        listCheckout = ArrayList()
        adapterCart = CartAdapter(
            itemDelete = {
                if (it.isChecked) {
                    resultPrice -= it.popularity.toInt()
                    viewModel.deleteById(it.id.toString())
                    binding.tvTotalPayment.text = resultPrice.toString()
                }
            },
            itemChecked = { item, isChecked ->
                when (isChecked) {
                    true -> {
                        if (updateToken()){
                            binding.btnBuy.isEnabled = true
                            val color = ContextCompat.getColor(requireContext(), R.color.colorYellow)
                            binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                            resultPrice += item.popularity.toInt()
                            binding.tvTotalPayment.text = resultPrice.toLong().toString()
                        }
                    }
                    false -> {
                        val color = ContextCompat.getColor(requireContext(), R.color.colorGray)
                        binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                        binding.btnBuy.isEnabled = false
                        resultPrice -= item.popularity.toInt()
                        binding.tvTotalPayment.text = resultPrice.toString()
                    }
                }
                viewModel.isChecked(item.id.toString(), isChecked)
            }, itemList
        )

        with(binding) {
            cbAddAll.text = getString(R.string.string_add_all)
            tvCartList.text = getString(R.string.string_cart)
            tvDeleteAll.text = getString(R.string.string_delete)
            tvTitleTotalPayment.text = getString(R.string.string_total)
            tvTotalPayment.text = resultPrice.toString()
            btnBuy.text = getString(R.string.string_buy_now)
            rvCart.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = adapterCart
            }
            rvCart.postDelayed({
                binding.rvCart.smoothScrollToPosition(0)
            }, 1000)
        }
    }

    override fun initListener() {
        with(binding) {
            ivBack.setOnClickListener {
                findNavController().navigateUp()
                resetPrice()
            }
            cbAddAll.setOnCheckedChangeListener { _, selected ->
                viewModel.updateBooleanColumnAll(selected)
                when (selected) {
                    true -> tvDeleteAll.setOnClickListener {
                        cbAddAll.isChecked = false
                        viewModel.deleteAllcart()
                    }

                    false -> tvDeleteAll.isClickable = false
                }
            }
        }
    }

    override fun observeData() {
        viewModel.getCartList()
        viewModel.cartList.observe(viewLifecycleOwner) { cartList ->
            adapterCart.setlistCart(cartList)
            if (cartList?.isNotEmpty() == true) {
                val selectedItem = cartList.filter { it.isChecked }
                binding.btnBuy.setOnClickListener {
                    resetPrice()
                    val selectedCheckoutList = selectedItem.map {
                        val id: Int = it.id
                        val backdropPath: String = it.backdropPath
                        val originalLanguage: String = it.originalLanguage
                        val originalTitle: String = it.originalTitle
                        val overview: String = it.overview
                        val popularity: String = it.popularity
                        val posterPath: String = it.posterPath
                        val releaseDate: String = it.releaseDate
                        val price = resultPrice.toString()
                        CheckoutDataClass(
                            id,
                            backdropPath,
                            originalLanguage,
                            originalTitle,
                            overview,
                            popularity,
                            posterPath,
                            releaseDate,
                            price
                        )
                    }
                    listCheckout.addAll(selectedCheckoutList)
                    productCheckout = ListCheckout(listCheckout)
                    findNavController().navigate(
                        R.id.action_cartFragment_to_checkoutFragment,
                        CheckoutFragmentArgs(productCheckout).toBundle(),
                        navOptions = null
                    )
                }

            }
        }
    }
    private fun updateToken() : Boolean{
        viewModel.getTokenBalance()
        viewModel.isTokenBalance.observe(viewLifecycleOwner){
            if (it.toInt() < resultPrice){
                binding.btnBuy.isEnabled = false
                val color = ContextCompat.getColor(requireContext(), R.color.colorGray)
                binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
            }else{
                binding.btnBuy.isEnabled = true
                val color = ContextCompat.getColor(requireContext(), R.color.colorYellow)
                binding.btnBuy.backgroundTintList = ColorStateList.valueOf(color)
                if (it != ""){
                    val result =  it.toInt() - resultPrice
                }
            }
        }
        return true
    }

    private fun resetPrice(){
        binding.cbAddAll.isChecked = false
        viewModel.updateBooleanColumnAll(false)
        resultPrice = 0
    }
}

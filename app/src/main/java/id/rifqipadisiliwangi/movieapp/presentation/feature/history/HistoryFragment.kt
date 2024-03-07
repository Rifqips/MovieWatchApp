package id.rifqipadisiliwangi.movieapp.presentation.feature.history

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import id.rifqipadisiliwangi.core.base.BaseFragment
import id.rifqipadisiliwangi.movieapp.R
import id.rifqipadisiliwangi.movieapp.databinding.FragmentHistoryBinding
import id.rifqipadisiliwangi.movieapp.presentation.common.adapter.transaction.TransactionAdapter
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment<FragmentHistoryBinding, CartViewModel>(FragmentHistoryBinding::inflate) {

    override val viewModel: CartViewModel by viewModel()

    private val adapterTransaction : TransactionAdapter by lazy {
        TransactionAdapter(itemDelete = {viewModel.deleteByIdTransaction(it.id.toString())},emptyList(), viewModel){}
    }

    override fun initView() {
        with(binding){
            tvTransactionHistory.text = resources.getString(R.string.string_transaction_history)
            ivBack.load(R.drawable.ic_back)
        }
    }

    override fun initListener() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun observeData() {
        viewModel.getTransactionList()
        viewModel.transactionList.observe(viewLifecycleOwner){ it ->
            binding.rvTransaction.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = adapterTransaction
                adapterTransaction.setlistWishlist(it)
            }
        }
        binding.rvTransaction.postDelayed({
            binding.rvTransaction.smoothScrollToPosition(0)
        }, 1000)
    }

}
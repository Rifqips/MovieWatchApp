package id.rifqipadisiliwangi.movieapp.di

import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.cart.CartViewModel
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.home.HomeViewModel
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.notification.NotificationViewModel
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.prelogin.PreloginViewModel
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.search.SearchViewModel
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.topup.TopUpViewModel
import id.rifqipadisiliwangi.movieapp.presentation.common.viewmodel.wishlist.WishListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::PreloginViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::WishListViewModel)
        viewModelOf(::TopUpViewModel)
        viewModelOf(::SearchViewModel)
        viewModelOf(::NotificationViewModel)
    }

    private val utilsModule = module {}

    val modules: List<Module> = listOf(
        viewModelModule,
        utilsModule,
    )
}

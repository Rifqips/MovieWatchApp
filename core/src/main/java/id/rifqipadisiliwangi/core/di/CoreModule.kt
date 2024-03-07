package id.rifqipadisiliwangi.core.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.ktx.storage
import id.rifqipadisiliwangi.core.data.datasource.AppPreferencesDataSource
import id.rifqipadisiliwangi.core.data.datasource.AppPreferencesDataSourceImpl
import id.rifqipadisiliwangi.core.data.datasource.MovieDataSourceImpl
import id.rifqipadisiliwangi.core.data.datasource.PagingDataSource
import id.rifqipadisiliwangi.core.data.local.database.ApplicationDatabase
import id.rifqipadisiliwangi.core.data.local.datastore.appDataSource
import id.rifqipadisiliwangi.core.data.remote.client.NetworkClient
import id.rifqipadisiliwangi.core.data.remote.interceptor.ApplicationInterceptor
import id.rifqipadisiliwangi.core.data.remote.service.ApiEndpoint
import id.rifqipadisiliwangi.core.domain.paging.nowplaying.NowPlayingPagingSource
import id.rifqipadisiliwangi.core.domain.paging.search.SearchPagingSource
import id.rifqipadisiliwangi.core.domain.repository.AppPreferencesRepositoryImpl
import id.rifqipadisiliwangi.core.domain.repository.AppRoomRepository
import id.rifqipadisiliwangi.core.domain.repository.AppRoomRepositoryImpl
import id.rifqipadisiliwangi.core.domain.repository.FirebaseRepositoryImpl
import id.rifqipadisiliwangi.core.domain.repository.MovieRepository
import id.rifqipadisiliwangi.core.domain.repository.MovieRepositoryImpl
import id.rifqipadisiliwangi.core.domain.usecase.AppPreferencesInteractor
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomInteractor
import id.rifqipadisiliwangi.core.domain.usecase.FirebaseInteractor
import id.rifqipadisiliwangi.core.domain.usecase.MovieInteractor
import id.rifqipadisiliwangi.core.utils.PreferenceDataStoreHelper
import id.rifqipadisiliwangi.core.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object CoreModule{
    private val utils = module {
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val firebaseModule = module{
        single { Firebase.analytics }
        single { Firebase.remoteConfig }
        single { Firebase.messaging }
        single { Firebase.database }
        single { Firebase.storage.reference }
        single { Firebase.auth }
        single { FirebaseRepositoryImpl(get(), get()) }
        single { (FirebaseInteractor(get())) }
    }
    private val networkModules = module{
        single { ApplicationInterceptor() }
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { NetworkClient(get(), get()) }
        single<ApiEndpoint>{get<NetworkClient>().create()}
    }

    private val sourceModule = module{
        single{ MovieDataSourceImpl(get()) }
        single { PagingDataSource(get(), get()) }
        single<AppPreferencesDataSource> { AppPreferencesDataSourceImpl(get()) }
    }
    private val repositoryModule = module{
        single<AppRoomRepository> { AppRoomRepositoryImpl(get(), get(), get(), get()) }
        single<MovieRepository>{ MovieRepositoryImpl(get(),get()) }
        single{ AppPreferencesRepositoryImpl(get()) }
    }
    private val interactorModule = module{
        single{ MovieInteractor(get())}
        single { AppRoomInteractor(get()) }
        single { AppPreferencesInteractor(get()) }
    }
    private val localModule = module {
        single { androidContext().appDataSource }
        single { ApplicationDatabase.getInstance(get()) }
        single { get<ApplicationDatabase>().wishlistDao() }
        single { get<ApplicationDatabase>().notificationDao() }
        single { get<ApplicationDatabase>().cartDao() }
        single { get<ApplicationDatabase>().transactionDao() }
    }

    private val pagingModule = module {
        single { NowPlayingPagingSource(get()) }
        single { SearchPagingSource(get()) }
    }

    val coreModule: List<Module> = listOf(
        networkModules, sourceModule, repositoryModule, interactorModule, localModule,firebaseModule, utils,pagingModule
    )
}
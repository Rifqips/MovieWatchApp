package id.rifqipadisiliwangi.core.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.rifqipadisiliwangi.core.data.local.database.ApplicationDatabase
import id.rifqipadisiliwangi.core.data.local.database.movie.MovieKey
import id.rifqipadisiliwangi.core.data.local.mediator.ApplicationRemoteMediator
import id.rifqipadisiliwangi.core.data.remote.service.ApiEndpoint
import kotlinx.coroutines.flow.Flow


@OptIn(ExperimentalPagingApi::class)
class PagingDataSource (private val apiEndpoint: ApiEndpoint, private val database: ApplicationDatabase){
    fun fetchProducts(): Flow<PagingData<MovieKey>> = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = 10,
            initialLoadSize = 10,
            prefetchDistance = 1
        ),
        remoteMediator = ApplicationRemoteMediator(apiEndpoint, database),
        pagingSourceFactory = {
            database.cartDao().retrieveAllMovies()
        }
    ).flow

}

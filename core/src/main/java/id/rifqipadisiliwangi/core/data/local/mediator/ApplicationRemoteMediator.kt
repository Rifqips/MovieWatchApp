package id.rifqipadisiliwangi.core.data.local.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import id.rifqipadisiliwangi.core.data.local.database.ApplicationDatabase
import id.rifqipadisiliwangi.core.data.local.database.movie.MovieKey
import id.rifqipadisiliwangi.core.data.local.database.movie.RemoteKeys
import id.rifqipadisiliwangi.core.data.remote.service.ApiEndpoint
import id.rifqipadisiliwangi.core.utils.DataMapper.toLocalListData

@OptIn(ExperimentalPagingApi::class)
class ApplicationRemoteMediator(
    private val apiEndPoint: ApiEndpoint,
    private val database: ApplicationDatabase
): RemoteMediator<Int, MovieKey>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieKey>
    ): MediatorResult {
        val page = when(loadType){
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        return try {
            val responseData = apiEndPoint.getPopular(page = page)
            val endOfPaginationReached = responseData.results.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH){
                    database.cartDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page -1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.results.map {
                    RemoteKeys(id = it.id.toString(), prevKey = prevKey, nextKey=nextKey)
                }
                database.cartDao().insertAllKey(keys)
                database.cartDao().insertMovies(responseData.toLocalListData())
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (exception: Exception){
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieKey>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty()}?.data?.firstOrNull()?.let { data ->
            database.cartDao().getRemoteKeysId(data.id.toString())
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieKey>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty()}?.data?.lastOrNull()?.let { data ->
            database.cartDao().getRemoteKeysId(data.id.toString())
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieKey>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.cartDao().getRemoteKeysId(id.toString())
            }
        }
    }

    companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

}
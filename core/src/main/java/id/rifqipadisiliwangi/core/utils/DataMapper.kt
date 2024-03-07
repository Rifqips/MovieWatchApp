package id.rifqipadisiliwangi.core.utils

import id.rifqipadisiliwangi.core.data.local.database.cart.CartKey
import id.rifqipadisiliwangi.core.data.local.database.movie.MovieKey
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionKey
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKey
import id.rifqipadisiliwangi.core.data.remote.data.nowplaying.ResponseNowPlaying
import id.rifqipadisiliwangi.core.data.remote.data.nowplaying.ResultNowPlaying
import id.rifqipadisiliwangi.core.data.remote.data.payment.ResponsePaymentItem
import id.rifqipadisiliwangi.core.data.remote.data.popular.ResponsePopular
import id.rifqipadisiliwangi.core.data.remote.data.popular.ResultPopular
import id.rifqipadisiliwangi.core.data.remote.data.search.ResponseSearchItem
import id.rifqipadisiliwangi.core.data.remote.data.search.ResultSearchItem
import id.rifqipadisiliwangi.core.domain.model.cart.KeyCart
import id.rifqipadisiliwangi.core.domain.model.checkout.CheckoutDataClass
import id.rifqipadisiliwangi.core.domain.model.checkout.ListCheckout
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResponse
import id.rifqipadisiliwangi.core.domain.model.nowplaying.NowPlayingResult
import id.rifqipadisiliwangi.core.domain.model.payment.PaymentResponseItem
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResponse
import id.rifqipadisiliwangi.core.domain.model.popular.PopularResult
import id.rifqipadisiliwangi.core.domain.model.popular.toResultPopular
import id.rifqipadisiliwangi.core.domain.model.search.SearchItemResponse
import id.rifqipadisiliwangi.core.domain.model.search.SearchItemResult
import id.rifqipadisiliwangi.core.domain.model.wishlist.KeyWishlist

object DataMapper {

    fun ResponseNowPlaying.toNowPlayingMapper() = NowPlayingResponse(
        dates = this.dates, page = this.page, results = this.results.toNowPlayingResultList(), totalPages = this.totalPages, totalResults = this.totalResults
    )
    fun Collection<ResponseNowPlaying>.toNowPlayingList() = this.map { it.toNowPlayingMapper() }


    fun ResultNowPlaying.toNowPlayingResultMapper() = NowPlayingResult(
        adult = this.adult, backdropPath = this.backdropPath, genreIds = this.genreIds, id = this.id, originalLanguage = this.originalLanguage, originalTitle = this.originalTitle, overview = this.overview, popularity = this.popularity, posterPath = this.posterPath, releaseDate = this.releaseDate, title = this.title, video = this.video, voteAverage = this.voteAverage, voteCount = this.voteCount
    )

    fun Collection<ResultNowPlaying>.toNowPlayingResultList() = this.map { it.toNowPlayingResultMapper() }

    fun ResponsePopular.toPopularMapper() = PopularResponse(
        page = this.page, results = this.results, totalPages = this.totalPages, totalResults = this.totalResults
    )


    fun Collection<ResultPopular>.toListResultPopular() = this.map { it.toResultPopular() }.toList()
    fun ResponsePopular.toResponsePopularList() = results.map { popularResult -> popularResult.toResultPopular() }.toList()

    fun ResultPopular.toPopulerRemote() = MovieKey(
        id = this.id, adult = this.adult, backdropPath = this.backdropPath, originalLanguage = this.originalLanguage, originalTitle = this.originalTitle, overview = this.overview, popularity = this.popularity, posterPath = this.posterPath, releaseDate = this.releaseDate, title = this.title, video = this.video, voteAverage = this.voteAverage, voteCount = this.voteCount
    )
    fun ResponsePopular.toLocalListData() = results.map { item -> item.toPopulerRemote()  }.toList()

    fun MovieKey.toLocaleUiData()= PopularResult(
        id = this.id, adult = this.adult, backdropPath = this.backdropPath, originalLanguage = this.originalLanguage, originalTitle = this.originalTitle, overview = this.overview, popularity = this.popularity, posterPath = this.posterPath, releaseDate = this.releaseDate, title = this.title, video = this.video, voteAverage = this.voteAverage, genreIds = listOf(), voteCount = this.voteCount
    )

    fun CartKey.toCartKeyMap() = KeyCart(
       id = this.id, backdropPath = this.backdropPath, originalLanguage = this.originalLanguage, originalTitle = this.originalTitle, overview = this.overview, popularity = this.popularity, posterPath = this.posterPath, releaseDate = this.releaseDate, isChecked = this.isChecked
    )

    fun Collection<CartKey>.toCartKeyList() = this.map { it.toCartKeyMap() }

    fun ResponsePaymentItem.ResponsePaymentItemItem.toPaymentMap() = PaymentResponseItem.PaymentResponseItemItem(
        price = this.price, token = this.token
    )

    fun Collection<ResponsePaymentItem.ResponsePaymentItemItem>.toPaymentList() = this.map { it.toPaymentMap() }

    fun WishlistKey.toWishlistMap() = KeyWishlist(
        id = this.id, backdropPath = this.backdropPath, originalLanguage = this.originalLanguage, originalTitle = this.originalTitle, overview = this.overview, popularity = this.popularity, posterPath = this.posterPath, releaseDate = this.releaseDate, isChecked = this.isChecked
    )

    fun Collection<WishlistKey>.toWishlistList() = this.map { it.toWishlistMap() }

    fun ResponseSearchItem.toSearchMapper() = SearchItemResponse(
        page = this.page, results = this.results.toResultSearctList(), totalPages = this.totalPages, totalResults = this.totalResults
    )

    fun ResultSearchItem.toResulstSearchMapper() = SearchItemResult(
        adult = this.adult, backdropPath = this.backdropPath, genreIds = this.genreIds, id = this.id, originalLanguage = this.originalLanguage, originalTitle = this.originalTitle, overview = this.overview, popularity = this.popularity, posterPath = this.posterPath, releaseDate = this.releaseDate, title = this.title, video = this.video, voteAverage = this.voteAverage, voteCount = this.voteCount
    )

    fun Collection<ResultSearchItem>.toResultSearctList() = this.map { it.toResulstSearchMapper() }


    fun TransactionKey.toCheckOutMapper() = CheckoutDataClass(
        id = this.id ?: 0,
        backdropPath = this.backdropPath.orEmpty(),
        originalLanguage = this.originalLanguage.orEmpty(),
        originalTitle = this.originalTitle.orEmpty(),
        overview = this.overview.orEmpty(),
        popularity = this.popularity.orEmpty(),
        posterPath = this.posterPath.orEmpty(),
        releaseDate = this.releaseDate.orEmpty(),
        price = "",
    )

}
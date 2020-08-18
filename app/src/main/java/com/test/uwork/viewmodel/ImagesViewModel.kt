package com.test.uwork.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.uwork.datasource.DataSourceFactory
import com.test.uwork.datasource.PicturesDataSource
import com.test.uwork.models.Picture
import com.test.uwork.models.State
import com.test.uwork.net.ApiService
import com.test.uwork.utils.Contants.PAGE_SIZE
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.dsl.module.module

val viewModelImageModule = module {

    factory { ImagesViewModel(get()) }
}

class ImagesViewModel(private val apiService: ApiService):ViewModel() {
    var picturesPagedList: LiveData<PagedList<Picture>>
    var liveDataSource: LiveData<PicturesDataSource>
    private val itemDataSourceFactory: DataSourceFactory

    init {
        itemDataSourceFactory =
           DataSourceFactory(
            apiService
            )

        liveDataSource = itemDataSourceFactory.picturesLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()

        picturesPagedList = LivePagedListBuilder(itemDataSourceFactory, config).build()
    }

    fun getState(): LiveData<State> = Transformations.switchMap<PicturesDataSource,
            State>(itemDataSourceFactory.picturesLiveDataSource, PicturesDataSource::state)

    fun listIsEmpty(): Boolean {
        return picturesPagedList.value?.isEmpty() ?: true
    }
}
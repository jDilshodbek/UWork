package com.test.uwork.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.test.uwork.models.Picture
import com.test.uwork.net.ApiService
import io.reactivex.rxjava3.disposables.CompositeDisposable

class DataSourceFactory(private val apiService: ApiService):
    DataSource.Factory<Int, Picture>() {

    val picturesLiveDataSource = MutableLiveData<PicturesDataSource>()
    private val compositeDisposable=CompositeDisposable()
    override fun create(): DataSource<Int, Picture> {
        val picturesDataSource = PicturesDataSource(apiService,compositeDisposable)
        picturesLiveDataSource.postValue(picturesDataSource)
        return  picturesDataSource
    }
}
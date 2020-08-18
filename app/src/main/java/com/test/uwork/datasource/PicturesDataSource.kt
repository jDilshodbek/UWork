package com.test.uwork.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.test.uwork.models.Picture
import com.test.uwork.models.State
import com.test.uwork.net.ApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class PicturesDataSource(private val apiService: ApiService,private val cd: CompositeDisposable) : PageKeyedDataSource<Int, Picture>() {

    var state: MutableLiveData<State> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Picture>
    ) {

        val currentPage = 1
        val nextPage = currentPage + 1

       apiService.getPictures(currentPage,100)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .doOnSubscribe{updateState(State.LOADING)}
           .subscribe({
               val items=it
               updateState(State.DONE)
               callback.onResult(items,null,nextPage)
           },{
               updateState(State.ERROR)
           }).let { cd.add(it) }




    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Picture>) {
        val currentPage = params.key
        val nextPage = currentPage + 1
        apiService.getPictures(currentPage,100)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{updateState(State.LOADING)}
            .subscribe({
                val items=it
                updateState(State.DONE)
                callback.onResult(items,nextPage)
            },{
                updateState(State.ERROR)
            }).let { cd.add(it) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Picture>) {

        val currentPage = 1
        val nextPage = currentPage + 1

        apiService.getPictures(currentPage,100)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{updateState(State.LOADING)}
            .subscribe({
                val items=it
                updateState(State.DONE)
                callback.onResult(items,nextPage)
            },{
                updateState(State.ERROR)
            }).let { cd.add(it) }
       
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}
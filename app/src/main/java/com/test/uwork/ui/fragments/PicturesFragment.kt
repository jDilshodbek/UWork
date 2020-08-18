package com.test.uwork.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.uwork.R
import com.test.uwork.adapters.PicturesAdapter
import com.test.uwork.models.State
import com.test.uwork.viewmodel.ImagesViewModel
import kotlinx.android.synthetic.main.fragment_pictures.*
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [PicturesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class PicturesFragment : Fragment(R.layout.fragment_pictures) {


    private val imagesViewModel:ImagesViewModel by viewModel()
    private lateinit var picturesAdapter:PicturesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initData()
        initState()


    }

    private fun initData(){
        picturesAdapter= PicturesAdapter()
        rvPictures.layoutManager=LinearLayoutManager(requireContext())
        rvPictures.adapter=picturesAdapter
        imagesViewModel.picturesPagedList.observe(viewLifecycleOwner, Observer {
            picturesAdapter.submitList(it)
        })
    }

    private fun initState(){
        imagesViewModel.getState().observe(viewLifecycleOwner, Observer {
            if(it==State.LOADING){
                progressPictures?.isVisible=true
                rvPictures?.isVisible=false
            } else if(it==State.DONE){
                progressPictures?.isVisible=false
                rvPictures?.isVisible=true
            }
        })
    }
}
package com.example.firebasesocial.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.firebasesocial.R
import com.example.firebasesocial.database.PostRoomDatabase
import com.example.firebasesocial.databinding.FragmentViewPhotosBinding


class ViewPhotosFragment : Fragment() {
    private lateinit var viewModel:ViewViewModel
    private lateinit var binding: FragmentViewPhotosBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_view_photos,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = PostRoomDatabase.getInstance(application).postRoomDao

        val viewModelFactory = ViewViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(ViewViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter=PhotoAdapter()
        binding.recyclerView.adapter = adapter

        viewModel.allPost.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.submitList(it)
            }
        })

        return binding.root
    }


}
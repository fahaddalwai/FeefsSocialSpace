package com.example.firebasesocial.upload

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.firebasesocial.R
import com.example.firebasesocial.database.Post
import com.example.firebasesocial.database.PostRoomDatabase
import com.example.firebasesocial.databinding.FragmentUploadBinding
import com.squareup.picasso.Picasso


class UploadFragment : Fragment() {

    private val pickImageRequest = 1
    private lateinit var viewModel: UploadViewModel


    private lateinit var binding: FragmentUploadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload, container, false)


        val application = requireNotNull(this.activity).application
        val dataSource = PostRoomDatabase.getInstance(application).postRoomDao

        val viewModelFactory = UploadViewModelFactory(dataSource)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            ).get(UploadViewModel::class.java)


        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        binding.SelectImageButton.setOnClickListener { openFileChooser() }


        viewModel.showToast.observe(viewLifecycleOwner,{
            if(it) {
                Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                viewModel.showToastFalse()
            }
        })


        viewModel.buttonClicked.observe(viewLifecycleOwner,{
            if(it==true){
                viewModel.uploadImage()
            }
        })

        viewModel.repository.currentPost.observe(viewLifecycleOwner,{
            if(it!=null){
                viewModel.uploadToDatabase()
                viewModel.repository.currentPost.value=null
            }
        })

        viewModel.currentPostValue.observe(viewLifecycleOwner,{
            if(it!=null){
                viewModel.uploadToDatabase()
                viewModel.setCurrentPostValueToFalse()
            }
        })




        return binding.root
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, pickImageRequest)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImageRequest && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            viewModel.photo.value = data.data
            LoadIntoImageView()
        }
    }

    private fun LoadIntoImageView() {
        Picasso.get().load(viewModel.photo.value).into(binding.ImageView)
    }


}
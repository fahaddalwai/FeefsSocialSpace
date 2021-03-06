package com.example.firebasesocial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.firebasesocial.upload.UploadFragment
import com.example.firebasesocial.view.ViewPhotosFragment


class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return ViewPhotosFragment()
        }
        return UploadFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
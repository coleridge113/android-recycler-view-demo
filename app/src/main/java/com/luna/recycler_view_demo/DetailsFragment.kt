package com.luna.recycler_view_demo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.luna.recycler_view_demo.databinding.FragmentBinding
import androidx.core.net.toUri

class DetailsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBinding.inflate(inflater, container, false)
        val textView = binding.detailsTextView
        val item = arguments?.getString("item")
        textView.text = item

        binding.closeFragmentButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.intentButton.setOnClickListener {
            val youTubeIntent = Intent(Intent.ACTION_VIEW).apply {
                setPackage("com.google.android.youtube")
                data = "https://www.youtube.com".toUri()
            }
            
            try {
                startActivity(youTubeIntent)
                parentFragmentManager.popBackStack()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "No YouTube app found", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    companion object {
        fun newInstance(item: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putString("item", item)
            fragment.arguments = args
            return fragment
        }
    }
}
package com.luna.recycler_view_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luna.recycler_view_demo.databinding.FragmentBinding

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

        binding.button.setOnClickListener {
            parentFragmentManager.popBackStack()
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
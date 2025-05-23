package com.luna.recycler_view_demo

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.luna.recycler_view_demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createFragment()
        with(binding){
            recyclerView.apply {
                adapter = mainAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }

            newsBanner.isSelected = true
        }


    }

    private fun createFragment(){
        mainAdapter = MainAdapter(viewModel.repositoryItems) { item ->
            if(supportFragmentManager.findFragmentByTag("Details Fragment") != null) return@MainAdapter
            val fragment = DetailsFragment.newInstance(item)
            with(supportFragmentManager){
                beginTransaction()
                    .setCustomAnimations(
                        R.anim.slide_in_bottom,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out_bottom
                    )
                    .replace(binding.fragmentContainer.id, fragment, "Details Fragment")
                    .addToBackStack(null)
                    .commit()

                addOnBackStackChangedListener {
                    val isFragmentVisible = supportFragmentManager.backStackEntryCount > 0
                    with(binding){
                        if (isFragmentVisible) {
                            shrinkAndFadeOut(recyclerView)
                            fragmentContainer.visibility = View.VISIBLE
                            textView2.text = "Fragment View Demo"
                        } else {
                            shrinkAndFadeIn(recyclerView)
                            textView2.text = "Recycler View Demo"
                        }
                    }
                }
            }
        }
    }

    private fun shrinkAndFadeOut(view: View, duration: Long = 500, endVisibility: Int = View.GONE){
        view.animate()
            .alpha(0f)
            .scaleX(0.8f)
            .scaleY(0.8f)
            .setDuration(duration)
            .withEndAction { view.visibility = endVisibility }
            .start()
    }

    private fun shrinkAndFadeIn(view: View, duration: Long = 500, startVisibility: Int = View.VISIBLE){
        view.alpha = 0f
        view.scaleX = 0.8f
        view.scaleY = 0.8f
        view.visibility = startVisibility
        view.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(duration)
            .start()
    }
}
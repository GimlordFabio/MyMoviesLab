package be.bf.android.mymovies.lists.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.bf.android.mymovies.databinding.FragmentInTheatresBinding
import be.bf.android.mymovies.details.DetailsActivity
import be.bf.android.mymovies.entities.Movie
import be.bf.android.mymovies.lists.MainViewModel
import be.bf.android.mymovies.lists.MainViewModelFactory
import be.bf.android.mymovies.lists.MovieListAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [InTheatresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class InTheatresFragment : Fragment() {

    private var _binding : FragmentInTheatresBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels { MainViewModelFactory(requireContext()) }

    private lateinit var adapter: MovieListAdapter

    private val movies : MutableList<Movie> = mutableListOf()

    private var page : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInTheatresBinding.inflate(inflater, container, false)

        setupRv()
        bindViewModel()

        return binding.root
    }


    private fun setupRv() {

        adapter = MovieListAdapter(requireContext(), movies) {
            Log.d("TheatreFrag", "click")
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            intent.putExtra("movie", it)
            startActivity(intent)
        }
        binding.rcInTheatres.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcInTheatres.adapter = adapter
        binding.rcInTheatres.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("Intheatrefrag", "End of RV")
                    page++
                    viewModel.getInTheatresMovie(page)
                }
            }
        })

    }

    private fun bindViewModel() {

        viewModel.MoviesInTheatre.observe(viewLifecycleOwner){
            this.movies.clear()
            this.movies.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = InTheatresFragment()

            }

}
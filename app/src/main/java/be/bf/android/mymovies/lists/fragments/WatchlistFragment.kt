
package be.bf.android.mymovies.lists.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.mymovies.R
import be.bf.android.mymovies.databinding.FragmentWatchlistBinding
import be.bf.android.mymovies.details.DetailsActivity
import be.bf.android.mymovies.entities.Movie
import be.bf.android.mymovies.lists.MainViewModel
import be.bf.android.mymovies.lists.MainViewModelFactory
import be.bf.android.mymovies.lists.MovieListAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [WatchlistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels { MainViewModelFactory(requireContext()) }

    private lateinit var adapter: MovieListAdapter

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)

        setupRv()
        bindViewModel()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupRv() {

        adapter = MovieListAdapter(requireContext(), movies){

            val intent = Intent(requireContext(), DetailsActivity::class.java)
            intent.putExtra("movie", it)
            startActivity(intent)
        }
    }

    private fun bindViewModel() {

        viewModel.MoviesWatchList.observe(viewLifecycleOwner) {
            Log.d("WL", it.toString())
            adapter.update(it)
        }

        binding.rcWatchlist.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcWatchlist.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = WatchlistFragment()

    }
}
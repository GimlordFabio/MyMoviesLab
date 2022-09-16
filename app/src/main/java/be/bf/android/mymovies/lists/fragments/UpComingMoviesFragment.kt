package be.bf.android.mymovies.lists.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.mymovies.R
import be.bf.android.mymovies.databinding.FragmentUpComingMoviesBinding
import be.bf.android.mymovies.entities.Movie
import be.bf.android.mymovies.lists.MainViewModel
import be.bf.android.mymovies.lists.MovieListAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [UpComingMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class UpComingMoviesFragment : Fragment() {

    private var _binding : FragmentUpComingMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()

    private lateinit var adapter : MovieListAdapter

    private val movies : MutableList<Movie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpComingMoviesBinding.inflate(inflater, container, false)

        setupRv()
        bindViewModel()
        return binding.root
    }

    private fun setupRv() {

        adapter = MovieListAdapter(requireContext(), movies)
        binding.rcUpcoming.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcUpcoming.adapter = adapter
    }

    private fun bindViewModel() {

        viewModel.MoviesUpcoming.observe(viewLifecycleOwner) {

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
        fun newInstance() = UpComingMoviesFragment()
    }
}




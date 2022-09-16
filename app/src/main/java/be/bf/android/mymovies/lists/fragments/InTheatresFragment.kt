package be.bf.android.mymovies.lists.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.mymovies.databinding.FragmentInTheatresBinding
import be.bf.android.mymovies.entities.Movie
import be.bf.android.mymovies.lists.MainViewModel
import be.bf.android.mymovies.lists.MovieListAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [InTheatresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class InTheatresFragment : Fragment() {

    private var _binding : FragmentInTheatresBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: MovieListAdapter

    private val movies : MutableList<Movie> = mutableListOf()

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

        adapter = MovieListAdapter(requireContext(), movies)
        binding.rcInTheatres.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcInTheatres.adapter = adapter
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
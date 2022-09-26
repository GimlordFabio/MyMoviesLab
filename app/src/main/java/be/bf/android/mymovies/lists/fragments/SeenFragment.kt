package be.bf.android.mymovies.lists.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import be.bf.android.mymovies.R
import be.bf.android.mymovies.databinding.FragmentInTheatresBinding
import be.bf.android.mymovies.databinding.FragmentSeenBinding
import be.bf.android.mymovies.details.DetailsActivity
import be.bf.android.mymovies.entities.Movie
import be.bf.android.mymovies.lists.MainViewModel
import be.bf.android.mymovies.lists.MainViewModelFactory
import be.bf.android.mymovies.lists.MovieListAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [SeenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeenFragment : Fragment() {

    private var _binding : FragmentSeenBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels { MainViewModelFactory(requireContext()) }

    private lateinit var  adapter: MovieListAdapter

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSeenBinding.inflate(inflater, container, false)

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

        viewModel.MoviesSeen.observe(viewLifecycleOwner){
            this.movies.clear()
            this.movies.addAll(it)
            adapter.notifyDataSetChanged()
        }

        binding.rcSeen.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcSeen.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = SeenFragment()

    }
}
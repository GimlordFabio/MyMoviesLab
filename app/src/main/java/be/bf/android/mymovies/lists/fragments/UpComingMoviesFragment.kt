package be.bf.android.mymovies.lists.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import be.bf.android.mymovies.R
import be.bf.android.mymovies.databinding.FragmentUpComingMoviesBinding
import be.bf.android.mymovies.lists.MainViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UpComingMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class UpComingMoviesFragment : Fragment() {

    private var _binding : FragmentUpComingMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpComingMoviesBinding.inflate(inflater, container, false)

//        viewModel.Counter.observe(viewLifecycleOwner) { counter ->
//            binding.tvCounter.text = counter.toString()
//        }

        bindViewModel()
        return binding.root
    }

    private fun bindViewModel() {
        viewModel.MoviesUpcoming.observe(viewLifecycleOwner) {
            Log.d("UpcoFrag", it.toString())
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




package be.bf.android.mymovies.lists

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.bf.android.mymovies.R

/**
 * A simple [Fragment] subclass.
 * Use the [UpComingMoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class UpComingMoviesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up_coming_movies, container, false)
    }


    companion object {

        @JvmStatic
        fun newInstance() = UpComingMoviesFragment()
    }
}




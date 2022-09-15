package be.bf.android.mymovies.lists.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import be.bf.android.mymovies.R



/**
 * A simple [Fragment] subclass.
 * Use the [SeenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SeenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seen, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SeenFragment()

    }
}
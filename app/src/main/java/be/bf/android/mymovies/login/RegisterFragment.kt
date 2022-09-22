package be.bf.android.mymovies.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.bf.android.mymovies.R
import be.bf.android.mymovies.dal.UserDAO
import be.bf.android.mymovies.databinding.FragmentRegisterBinding
import be.bf.android.mymovies.entities.User
import be.bf.android.mymovies.lists.MainActivity

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var userDao : UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        userDao = UserDAO(requireContext())

        binding.btnRegfragLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegfragRegister.setOnClickListener {

            if (binding.etRegfragUsername.text.toString() == ""){

                // TODO: toast "Choose a username plz"
                return@setOnClickListener
            }

            userDao.openWritable()

            if(userDao.findIfUserExist(binding.etRegfragUsername.text.toString())) {
                // TODO: toast "User already exists, choose another Username plz "
            }
            else {
                userDao.insert(User(null, binding.etRegfragUsername.text.toString()))
                userDao.close()
                val intent : Intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                // TODO: "Logged in as (username)"
            }
            userDao.close()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}



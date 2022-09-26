package be.bf.android.mymovies.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import be.bf.android.mymovies.R
import be.bf.android.mymovies.dal.UserDAO
import be.bf.android.mymovies.databinding.FragmentLoginBinding
import be.bf.android.mymovies.lists.MainActivity

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get () = _binding!!

    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        userDAO = UserDAO(requireContext())

        val preferences: SharedPreferences = requireContext().getSharedPreferences("userSharedPref", Context.MODE_PRIVATE)

        binding.btnLogfragRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogfragLogin.setOnClickListener {

            if (binding.etLogfragUsername.text.toString() != "") {

                // TODO: "You must enter a username to be able to login"
                userDAO.openWritable()
                val user = userDAO.findUserByUsername(binding.etLogfragUsername.text.toString())
                if (user != null) {

                    if (binding.cbLogfragRm.isChecked) {
                        with(preferences.edit()) {
                            putString(
                                getString(R.string.user_pref),
                                binding.etLogfragUsername.text.toString()
                            )
                            apply()
                        }
                    }
                    with(preferences.edit()) {
                        user.id?.let { it1 -> putInt("id", it1) }
                        apply()
                    }
                    // Voyage vers l'activité
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    // TODO: toast "User does Not exist, plz go to register"
                }
                userDAO.close()
            }
            else {
                // TODO: toast "Enter a valid username or plz go to register"
            }
        }

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        val preferences: SharedPreferences = requireContext().getSharedPreferences("userSharedPref", Context.MODE_PRIVATE)
        val userName : String? = preferences.getString(getString(R.string.user_pref), null)

        if (userName != null) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("LoginFrag", "Puase")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LoginFrag", "Stop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}
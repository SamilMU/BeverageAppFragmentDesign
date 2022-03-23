package com.example.eticaret.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.navigation.Navigation
import com.example.eticaret.R
import com.example.eticaret.Shared.SharedEntity
import com.example.eticaret.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        var window : Window = requireActivity().window
        window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.toolbar)

        binding.btnLogin.setOnClickListener {
            if(binding.etTelefon.text.toString().equals(SharedEntity.mainUser.tel_number) && binding.etSifre.text.toString().equals(SharedEntity.mainUser.password)){
                SharedEntity.loggedIn = true
                Navigation.findNavController(binding.btnLogin).navigate(R.id.login2menu)
            }else{
                SharedEntity.loggedIn = false
                Toast.makeText(requireContext(),"Girilen telefon numarası veya şifre bilgisi yanlıştır.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGuest.setOnClickListener {
            SharedEntity.loggedIn = false
            Navigation.findNavController(binding.btnLogin).navigate(R.id.login2menu)
        }

        return binding.root
    }


}
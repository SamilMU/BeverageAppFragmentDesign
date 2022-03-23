package com.example.eticaret.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.eticaret.Shared.SharedEntity
import com.example.eticaret.Shared.SharedEntity.currentItem
import com.example.eticaret.databinding.FragmentItemBinding
import com.example.eticaret.entities.MenuItem
import com.google.android.material.snackbar.Snackbar

class ItemFragment : Fragment() {

    private lateinit var binding: FragmentItemBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentItemBinding.inflate(inflater, container, false)

        loggedIn(binding)

        // Views
        binding.tvDetayMarkaUrun.text = "${SharedEntity.currentItem.item_brand} \n ${SharedEntity.currentItem.item_name}"
        binding.tvDetayFiyat.text = "${SharedEntity.currentItem.item_price}₺"
        val currentItemPicID = requireContext().resources.getIdentifier(currentItem.item_pic_name,"drawable",requireContext().packageName)
        binding.ivDetayUrun.setImageResource(currentItemPicID)

        binding.cardDetayEkle.setOnClickListener {
            val tempItemList = ArrayList<MenuItem>()
            tempItemList.add(currentItem)
            SharedEntity.mainUser.member_cart = SharedEntity.mainUser.member_cart + tempItemList
            SharedEntity.cartSum.value = SharedEntity.cartSum.value!! + currentItem.item_price
        }

        binding.ivGeri.setOnClickListener {
            findNavController(binding.ivGeri).popBackStack()
        }

        SharedEntity.cartSum.observe(viewLifecycleOwner){
            binding.tvSepetToplamDetay.text = "$it₺"
        }

        return binding.root
    }

    private fun loggedIn(binding : FragmentItemBinding){
        if(!SharedEntity.loggedIn){
            binding.ivSepet.visibility = View.GONE
            binding.tvSepetToplamDetay.visibility = View.GONE
            binding.cardDetayEkle.visibility = View.GONE
        }else{
            var totalCart = 0.0
            SharedEntity.mainUser.member_cart.forEach {
                totalCart += it.item_price
            }
            binding.tvSepetToplamDetay.text = "$totalCart₺"
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

}
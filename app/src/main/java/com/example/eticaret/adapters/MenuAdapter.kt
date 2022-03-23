package com.example.eticaret.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.eticaret.R
import com.example.eticaret.Shared.SharedEntity
import com.example.eticaret.databinding.MenuCardItemBinding
import com.example.eticaret.entities.MenuItem
import com.google.android.material.snackbar.Snackbar

class MenuAdapter(var mContext: Context, var itemList: ArrayList<MenuItem>) : RecyclerView.Adapter<MenuAdapter.menuCardViewHolder>() {


    inner class menuCardViewHolder(binding: MenuCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        var binding : MenuCardItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): menuCardViewHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val binding = MenuCardItemBinding.inflate(layoutInflater,parent,false)
        return menuCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: menuCardViewHolder, position: Int) {
        val currentItem = itemList.get(position)
        val binding = holder.binding

        // Views
        binding.tvMarka.text = currentItem.item_brand
        binding.tvFiyat.text = "${currentItem.item_price}₺"
        binding.tvUrunAdi.text = currentItem.item_name

        // Image
        val currentItemPicID = mContext.resources.getIdentifier(currentItem.item_pic_name,"drawable",mContext.packageName)
        binding.ivUrunResim.setImageResource(currentItemPicID)

        // Buttons
        if(!SharedEntity.loggedIn){
            binding.cardEkleButon.visibility = View.GONE
        }else{
            binding.btnEkle.setOnClickListener {
                val tempItemList = ArrayList<MenuItem>()
                tempItemList.add(currentItem)
                SharedEntity.mainUser.member_cart = SharedEntity.mainUser.member_cart + tempItemList
                SharedEntity.cartSum.value = SharedEntity.cartSum.value!! + currentItem.item_price
            }
        }

        binding.cardResim.setOnClickListener {
            SharedEntity.currentItem = currentItem
            Navigation.findNavController(binding.cardResim).navigate(R.id.menu2detail)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}
package com.example.eticaret.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eticaret.Shared.SharedEntity
import com.example.eticaret.adapters.MenuAdapter
import com.example.eticaret.categories.Categories
import com.example.eticaret.databinding.FragmentMenuBinding
import com.example.eticaret.entities.MenuItem


class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(inflater,container,false)

        loggedCheck(binding)

        val testItemList = createTestList()
        var tempList4Adapter = ArrayList<MenuItem>()
//        addChip2Categories(testItemList, localCategoryList)

        // Adapter
        var adap = MenuAdapter(requireContext(),testItemList)
        val lm = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvMenu.layoutManager = lm
        binding.rvMenu.adapter = adap

        // Buttons
        binding.menuChipGroup.setOnCheckedChangeListener { group, checkedId ->
            var tempCategory = Categories.SU
            when(checkedId){
                binding.chipSu.id -> tempCategory = Categories.SU
                binding.chipMeyve.id -> tempCategory = Categories.MEYVE
                binding.chipGaz.id -> tempCategory = Categories.GAZLI
                binding.chipMaden.id -> tempCategory = Categories.MADEN
                binding.chipAyran.id -> tempCategory = Categories.AYRAN
            }
            testItemList.forEach {
                if(it.item_category.name == tempCategory.name){
                    tempList4Adapter.add(it)
                }
            }
            val adapterNew = MenuAdapter(
                requireContext(),
                tempList4Adapter
            )

            // TODO A full list is being sent but it does not update. Nothing is shown.
            updateAdapter(binding, adapterNew)
            tempList4Adapter = arrayListOf()
        }

        adap.cartCounter.observe(viewLifecycleOwner){
            binding.tvSepetToplam.text = it.toString()
        }

        binding.ivGeri.setOnClickListener {
            Navigation.findNavController(binding.ivGeri).popBackStack()
        }

        return binding.root
    }

    private fun loggedCheck(binding: FragmentMenuBinding){
        if(!SharedEntity.loggedIn){
            binding.ivSepet.visibility = View.GONE
            binding.tvSepetToplam.visibility = View.GONE
        }else{
            var totalCart = 0.0
            SharedEntity.mainUser.member_cart.forEach {
                totalCart += it.item_price
            }
            binding.tvSepetToplam.text = "$totalCart₺"
        }
    }

// Automatic category adder for later use
/*    private fun addChip2Categories(testItemList : ArrayList<MenuItem>, definedCategories: ArrayList<String>){
        testItemList.forEach {
            if(!definedCategories.contains(it.item_category.name)){
                val chip = Chip(requireContext())
                chip.text = "${it.item_category}"
                chip.setChipBackgroundColorResource(R.color.white)
                chip.setTextColor(resources.getColor(R.color.black))
                chip.isCheckable = true
                chip.isCloseIconVisible = false

                binding.menuChipGroup.addView(chip)
                definedCategories.add(it.item_category.name)
            }
        }
    }*/

    private fun updateAdapter(binding: FragmentMenuBinding, adapter: MenuAdapter){
        Toast.makeText(requireContext(),adapter.itemList.toString(),Toast.LENGTH_SHORT).show()
        binding.rvMenu.adapter = adapter
    }

    private fun createTestList() : ArrayList<MenuItem>{
        var tempList = ArrayList<MenuItem>()
        val item1 = MenuItem("Küçük Su","Erikli",1.35,Categories.SU,"500ml Su","eriklisu")
        val item2 = MenuItem("Meyve Suyu","Dimes",14.35,Categories.MEYVE,"Karışık Meyve Nektaro","meyvesuyu")
        val item3 = MenuItem("Su","Hayat",14.35,Categories.SU,"Hayat Su Büyük","hayatbuyuksu")
        val item5 = MenuItem("Gazoz","Sprite",9.35,Categories.GAZLI,"2L Gazlı içecek","spritegaz")
        val item7 = MenuItem("Soda","Beypazarı",4.35,Categories.MADEN,"350ml Maden Suyu","beypazarisoda")
        val item8 = MenuItem("Ayran","Sütaş",3.35,Categories.AYRAN,"350ml İçimlik Ayran","ayran")
        tempList.add(item1)
        tempList.add(item2)
        tempList.add(item3)
        tempList.add(item5)
        tempList.add(item7)
        tempList.add(item8)

        return tempList
    }


}
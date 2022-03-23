package com.example.eticaret.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.eticaret.Shared.SharedEntity
import com.example.eticaret.adapters.MenuAdapter
import com.example.eticaret.categories.Categories
import com.example.eticaret.databinding.FragmentMenuBinding
import com.example.eticaret.entities.MenuItem


class MenuFragment : Fragment() {

    var testItemList = createTestList()
    private lateinit var binding: FragmentMenuBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMenuBinding.inflate(inflater,container,false)

        loggedCheck(binding)

        var tempList4Adapter = ArrayList<MenuItem>()
//        addChip2Categories(testItemList, localCategoryList)

        // Adapter
        var adap = MenuAdapter(requireContext(),testItemList)
        val lm = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvMenu.layoutManager = lm
        binding.rvMenu.adapter = adap

        SharedEntity.cartSum.observe(viewLifecycleOwner){
            binding.tvSepetToplam.text = "${it}₺"
        }

        // Buttons
        binding.menuChipGroup.setOnCheckedChangeListener { group, checkedId ->
            testItemList = createTestList()
            var emptyChecker = false
            var tempCategory = Categories.SU
            when(checkedId){
                binding.chipSu.id -> tempCategory = Categories.SU
                binding.chipMeyve.id -> tempCategory = Categories.MEYVE
                binding.chipGaz.id -> tempCategory = Categories.GAZLI
                binding.chipMaden.id -> tempCategory = Categories.MADEN
                binding.chipAyran.id -> tempCategory = Categories.AYRAN
                else -> emptyChecker = true
            }
            if(!emptyChecker){
                testItemList.forEach {
                    if(it.item_category.name == tempCategory.name){
                        tempList4Adapter.add(it)
                    }
                }
            }else{
                tempList4Adapter = testItemList
            }


            testItemList = tempList4Adapter
            tempList4Adapter = arrayListOf()
            onResume()
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

    override fun onResume() {
        super.onResume()
        var adap = MenuAdapter(requireContext(),testItemList)
        val lm = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL)
        binding.rvMenu.layoutManager = lm
        binding.rvMenu.adapter = adap
        binding.tvSepetToplam.text = "${SharedEntity.cartSum.value}₺"
    }

    fun createTestList() : ArrayList<MenuItem>{
        val tempList = ArrayList<MenuItem>()
        val item1 = MenuItem("Küçük Su","Erikli",1.35,Categories.SU,"500ml Su","eriklisu")
        val item2 = MenuItem("Meyve Suyu","Dimes",14.35,Categories.MEYVE,"Karışık Meyve Nektaro","meyvesuyu")
        val item3 = MenuItem("Su","Hayat",14.35,Categories.SU,"Hayat Su Büyük","hayatbuyuksu")
        val item4 = MenuItem("Gazoz","Sprite",9.35,Categories.GAZLI,"2L Gazlı içecek","spritegaz")
        val item5 = MenuItem("Soda","Beypazarı",4.35,Categories.MADEN,"350ml Maden Suyu","beypazarisoda")
        val item6 = MenuItem("Ayran","Sütaş",3.35,Categories.AYRAN,"350ml İçimlik Ayran","ayran")
        tempList.add(item1)
        tempList.add(item2)
        tempList.add(item3)
        tempList.add(item4)
        tempList.add(item5)
        tempList.add(item6)

        return tempList
    }


}
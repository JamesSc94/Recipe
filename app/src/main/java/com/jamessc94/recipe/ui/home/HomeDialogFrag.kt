package com.jamessc94.recipe.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jamessc94.recipe.databinding.FragHomeDialogBinding
import com.jamessc94.recipe.model.asRecipeTypeList
import com.jamessc94.recipe.ui.adapter.AdapHomeCate
import com.jamessc94.recipe.ui.adapter.AdapHomeCateClickListener
import com.jamessc94.recipe.utils.setWidthPercent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDialogFrag : DialogFragment() {

    companion object {
        fun newInstance(): HomeDialogFrag {
            return HomeDialogFrag()
        }
    }

    internal val vm : HomeVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragHomeDialogBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.adapter = AdapHomeCate(AdapHomeCateClickListener{ type ->
            vm.recipeType.value?.filter { it.id == type.id }?.let {
                it.first().let { t ->
                    t.isSelected = !t.isSelected
                    vm.updateCategory(t)
                }
            }


        })

        vm.recipeType.observe(viewLifecycleOwner, Observer {
            if(it.isNullOrEmpty()) {
                vm.insertCategory()

            }else{
                (binding.adapter as AdapHomeCate).submitList(it.asRecipeTypeList())
            }
        })

        binding.fragHomeDialogClose.setOnClickListener {
            dismiss()

        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        setWidthPercent(90, 80)

    }
}
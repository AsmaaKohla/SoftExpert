package com.example.petfinder.presentation.petsList

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petfinder.R
import com.example.petfinder.data.appData.PetData
import com.example.petfinder.data.appData.PetDataList
import com.example.petfinder.databinding.ActivityPetsBinding
import com.example.petfinder.presentation.base.BaseActivity
import com.example.petfinder.presentation.petDetails.PetDetailsActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsActivity : BaseActivity(), PetTypeAdapter.OnPetTypeClickListener,
    PetsAdapter.OnPetClickListener {

    private val _viewModel: PetsViewModel by viewModels()
    private lateinit var _binding: ActivityPetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityPetsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        super.onCreate(savedInstanceState)

        initializeTypesAdapter()
        initializePetsAdapter()

        observeViewModels()

        _viewModel.authUser()
    }

    private fun observeViewModels() {
        _viewModel.showProgressBarLiveData.observe(this, ::handleProgressBar)
        _viewModel.toastLiveData.observe(this, ::showToast)
        _viewModel.userAuthorizedLiveData.observe(this, ::handleAuthorization)
        _viewModel.petListLiveData.observe(this, ::handlePetList)
    }

    private fun handleAuthorization(isAuth: Boolean) {
        if (isAuth)
            _viewModel.loadPets()
    }

    private fun initializeTypesAdapter() {
        val animalsTypes = listOf("all", "Cat", "Horse", "Bird", "Rabbit")
        val adapter = PetTypeAdapter(animalsTypes, this)
        _binding.petTypeRecyclerView.adapter = adapter
        _binding.petTypeRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        adapter.notifyDataSetChanged()
    }

    private fun initializePetsAdapter() {
        val adapter = PetsAdapter(ArrayList(), this)
        _binding.petsRecyclerView.adapter = adapter
        _binding.petsRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        adapter.notifyDataSetChanged()
        _binding.petsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                _viewModel.petListLiveData.value?.pagination?.let { pagination ->
                    if (pagination.currentPage < pagination.totalPages) {
                        if (recyclerView.canScrollVertically(-1) &&
                            !recyclerView.canScrollVertically(1) &&
                            newState == RecyclerView.SCROLL_STATE_IDLE
                        ) {
                            _viewModel.loadPets(page = pagination.currentPage + 1)
                        }
                    }
                }
            }
        })
    }

    private fun handlePetList(petList: PetDataList) {
        val adapter = (_binding.petsRecyclerView.adapter as PetsAdapter)
        adapter.addItems(petList.animals)
    }

    override fun onPetTypeClick(type: String) {
        (_binding.petsRecyclerView.adapter as PetsAdapter).clear()
        _viewModel.setSelectedType(type)
        _viewModel.loadPets()
    }

    override fun onPetClick(pet: PetData) {
        val intent = Intent(this, PetDetailsActivity::class.java)
        intent.putExtra("pet_details", pet)
        startActivity(intent)
    }
}
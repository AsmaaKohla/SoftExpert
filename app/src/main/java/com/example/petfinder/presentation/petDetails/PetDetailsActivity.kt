package com.example.petfinder.presentation.petDetails

import android.content.Intent
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.petfinder.R
import com.example.petfinder.data.appData.PetData
import com.example.petfinder.databinding.ActivityPetDetailsBinding
import com.example.petfinder.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PetDetailsActivity : BaseActivity() {

    private lateinit var _binding: ActivityPetDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityPetDetailsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        super.onCreate(savedInstanceState)
        setTitle(R.string.pet_details_title)

        intent?.extras?.let {
            val petDetails = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("pet_details", PetData::class.java)
            } else {
                intent.getParcelableExtra("pet_details")
            }

            loadPetDetails(petDetails!!)

            //_binding.toolbarTitle.text = orderNumberTitle
        }
    }

    private fun loadPetDetails(petDetails: PetData) {
        _binding.petNameText.text = petDetails.name
        _binding.petSizeText.text = petDetails.size
        _binding.petColorText.text = petDetails.color
        _binding.petAddressText.text = petDetails.address

        Glide.with(this)
            .load(petDetails.largeImage)
            .placeholder(R.drawable.pet_placeholder)
            .error(R.drawable.pet_placeholder)
            .fallback(R.drawable.pet_placeholder)
            .into(_binding.petImageView)

        petDetails.url?.let { url ->
            _binding.websiteButton.visibility = View.VISIBLE
            _binding.websiteButton.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(browserIntent)
            }
        }
    }
}
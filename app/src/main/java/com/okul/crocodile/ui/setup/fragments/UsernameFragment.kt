package com.okul.crocodile.ui.setup.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.okul.crocodile.R
import com.okul.crocodile.databinding.FragmentUsernameBinding
import com.okul.crocodile.ui.setup.SetupViewModel
import com.okul.crocodile.util.Constants.MAX_USERNAME_LENGTH
import com.okul.crocodile.util.Constants.MIN_USERNAME_LENGTH
import com.okul.crocodile.util.navigateSafely
import com.okul.crocodile.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UsernameFragment : Fragment(R.layout.fragment_username) {

    private var _binding: FragmentUsernameBinding? = null
    private val binding: FragmentUsernameBinding
        get() = _binding!!

    private val viewModel: SetupViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUsernameBinding.bind(view)

        listenToEvents()

        binding.btnNext.setOnClickListener {
            viewModel.validateUsernameAndNavigateToSelectRoom(
                binding.etUsername.text.toString()
            )
        }
    }

    private fun listenToEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.setupEvent.collect { event ->
                when (event) {
                    is SetupViewModel.SetupEvent.NavigateToSelectRoomEvent -> {
                        findNavController().navigateSafely(
                            R.id.action_usernameFragment_to_selectRoomFragment,
                            args = Bundle().apply { putString("username", event.userName) }
                        )
                    }
                    is SetupViewModel.SetupEvent.InputEmptyError -> {
                        snackbar(R.string.error_field_empty)
                    }
                    is SetupViewModel.SetupEvent.InputShortError -> {
                        snackbar(getString(R.string.error_username_too_short, MIN_USERNAME_LENGTH))
                    }
                    is SetupViewModel.SetupEvent.InputTooLongError -> {
                        snackbar(getString(R.string.error_username_too_long, MAX_USERNAME_LENGTH))
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
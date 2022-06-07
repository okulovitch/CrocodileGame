package com.okul.crocodile.ui.setup.fragments

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.okul.crocodile.R
import com.okul.crocodile.adapters.RoomAdapter
import com.okul.crocodile.databinding.FragmentSelectRoomBinding
import com.okul.crocodile.ui.setup.SetupViewModel
import com.okul.crocodile.util.Constants.SEARCH_DELAY
import com.okul.crocodile.util.navigateSafely
import com.okul.crocodile.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SelectRoomFragment : Fragment(R.layout.fragment_select_room) {

    private var _binding: FragmentSelectRoomBinding? = null
    private val binding: FragmentSelectRoomBinding
        get() = _binding!!

    private val viewModel: SetupViewModel by activityViewModels()

    private val args: SelectRoomFragmentArgs by navArgs()

    @Inject
    lateinit var roomAdapter: RoomAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectRoomBinding.bind(view)

        setupRecyclerView()
        subscribeToObservers()
        listenToEvents()

        viewModel.getRooms("")

        var searchJob: Job? = null
        binding.etRoomName.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(SEARCH_DELAY)
                viewModel.getRooms(it.toString())
            }
        }

        roomAdapter.setOnRoomClickListener {
            viewModel.joinRoom(args.username, it.name)
        }

        binding.ibReload.setOnClickListener {
            binding.roomsProgressBar.isVisible = true
            binding.ivNoRoomsFound.isVisible = false
            binding.tvNoRoomsFound.isVisible = false
            viewModel.getRooms(binding.etRoomName.text.toString())
        }

        binding.btnCreateRoom.setOnClickListener {
            findNavController().navigateSafely(
                R.id.action_selectRoomFragment_to_createRoomFragment,
                Bundle().apply { putString("username", args.username) }
            )
        }
    }

    private fun listenToEvents() = lifecycleScope.launchWhenStarted {
        viewModel.setupEvent.collect { event ->
            when (event) {
                is SetupViewModel.SetupEvent.JoinRoomEvent -> {
                    findNavController().navigateSafely(
                        R.id.action_selectRoomFragment_to_drawingActivity,
                        args = Bundle().apply {
                            putString("userName", args.username)
                            putString("roomName", event.roomName)
                        }
                    )
                }
                is SetupViewModel.SetupEvent.JoinRoomErrorEvent -> {
                    snackbar(event.error)
                }
                is SetupViewModel.SetupEvent.GetRoomErrorEvent -> {
                    binding.apply {
                        roomsProgressBar.isVisible = false
                        tvNoRoomsFound.isVisible = false
                        ivNoRoomsFound.isVisible = false
                    }
                    snackbar(event.error)
                }
                else -> Unit
            }
        }
    }

    private fun subscribeToObservers() = lifecycleScope.launchWhenStarted {
        viewModel.rooms.collect { event ->
            when (event) {
                is SetupViewModel.SetupEvent.GetRoomLoadingEvent -> {
                    binding.roomsProgressBar.isVisible = true
                }
                is SetupViewModel.SetupEvent.GetRoomEvent -> {
                    binding.roomsProgressBar.isVisible = false
                    val isRoomsEmpty = event.rooms.isEmpty()
                    binding.tvNoRoomsFound.isVisible = isRoomsEmpty
                    binding.ivNoRoomsFound.isVisible = isRoomsEmpty
                    lifecycleScope.launch {
                        roomAdapter.updateDataset(event.rooms)
                    }
                }
                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.rvRooms.apply {
            adapter = roomAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}
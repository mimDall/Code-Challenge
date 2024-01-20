package com.example.codechallenge.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.codechallenge.data.model.Book
import com.example.codechallenge.network.Resource
import com.example.codechallenge.util.snackbar
import com.example.codechallenge.util.visibile
import com.example.codechallenge.viewmodels.BookViewModel
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.FragmentAddBookBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBookBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAddBookBottomSheetBinding
    private val bookViewModel : BookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBookBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addCancelBtn.setOnClickListener{
            hideBottomSheet()
        }

        binding.addConfirmBtn.setOnClickListener{

            val currentBook = Book()
            currentBook.title = binding.addTitle.text.toString()
            currentBook.author = binding.addAuthor.text.toString()
            currentBook.genre = binding.addGenre.text.toString()
            currentBook.yearPublished = binding.addYearPublished.text?.trim().toString().toInt()
            currentBook.checkedOut = binding.addCheckedOut.isChecked
            bookViewModel.addBook(currentBook)

            bookViewModel.addBookResponse.observe(viewLifecycleOwner, {
                when (it) {

                    is Resource.Loading -> {
                        binding.pbAdd.visibile(true)
                    }

                    is Resource.Success -> {
                        requireView().snackbar("book is added successfully")
                        closeAddDialog(true)

                    }

                    is Resource.Failure -> {
                        requireView().snackbar("book add is failed")
                        hideBottomSheet()

                    }
                }
            })
        }

    }

    private fun hideBottomSheet() {
        val d = dialog as BottomSheetDialog
        d.cancel()
    }

    private fun closeAddDialog(flag: Boolean){
        val bundle = Bundle()
        bundle.putBoolean("isAdded", true)
        (requireActivity() as MainActivity).navController.navigate(R.id.action_addBookBottomSheetFragment_to_home, bundle)
    }
}
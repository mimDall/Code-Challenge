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
import com.example.codingchallenge.databinding.FragmentBookDetailsBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBookDetailsBottomSheetBinding
    private val bookViewModel : BookViewModel by viewModels()
    private var id : String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookDetailsBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments?.getString("bookId") != null){
            id = arguments?.getString("bookId")
        }

        bookViewModel.getBook(id!!)

        bookViewModel.bookResponse.observe(viewLifecycleOwner, {
            when (it) {

                is Resource.Loading -> {
                    binding.pbBookDetails.visibile(true)
                }

                is Resource.Success -> {
                    binding.pbBookDetails.visibile(false)
                    setDataToFields(it.value)

                }

                is Resource.Failure -> {
                    binding.pbBookDetails.visibile(false)
                    requireView().snackbar("book updating is failed")
                    hideBottomSheet()

                }
            }
        })


    }

    private fun setDataToFields(book: Book?) {
        binding.detailsTitle.setText(book?.title)
        binding.detailsAuthor.setText(book?.author)
        binding.detailsGenre.setText(book?.genre)
        binding.detailsYearPublished.setText(book?.yearPublished.toString())
        book?.checkedOut?.let { binding.detailsCheckedOut.setChecked(it) }
        binding.imgLetter.text = book?.title?.get(0)?.toUpperCase().toString()

    }
    private fun hideBottomSheet() {
        val d = dialog as BottomSheetDialog
        d.cancel()
    }


}
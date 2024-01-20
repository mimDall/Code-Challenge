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
import com.example.codingchallenge.databinding.FragmentUpdateBookBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditBookBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentUpdateBookBottomSheetBinding
    private val bookViewModel : BookViewModel by viewModels()
    private var book : Book? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBookBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(arguments?.getParcelable<Book>("book") != null){
            book = arguments?.getParcelable("book")
        }

        setDataToFields(book)

        binding.editCancelBtn.setOnClickListener{
            hideBottomSheet()
        }

        binding.editConfirmBtn.setOnClickListener{

            val currentBook = Book()
            currentBook.title = binding.edtTitle.text.toString()
            currentBook.author = binding.edtAuthor.text.toString()
            currentBook.genre = binding.edtGenre.text.toString()
            currentBook.yearPublished = binding.edtYearPublished.text?.trim().toString().toInt()
            currentBook.checkedOut = binding.edtCheckedOut.isChecked
            currentBook.createdAt = book?.createdAt
            bookViewModel.updateBook(book?.id!!, currentBook)

            bookViewModel.updateBookResponse.observe(viewLifecycleOwner, {
                when (it) {

                    is Resource.Loading -> {
                        binding.pbUpdate.visibile(true)
                    }

                    is Resource.Success -> {
                        requireView().snackbar("book is updated successfully")
                        closeEditDialog(true)

                    }

                    is Resource.Failure -> {
                        requireView().snackbar("book updating is failed")
                        hideBottomSheet()

                    }
                }
            })
        }

    }

    private fun setDataToFields(book: Book?) {
        binding.edtTitle.setText(book?.title)
        binding.edtAuthor.setText(book?.author)
        binding.edtGenre.setText(book?.genre)
        binding.edtYearPublished.setText(book?.yearPublished.toString())
        book?.checkedOut?.let { binding.edtCheckedOut.setChecked(it) }
        binding.imgLetter.text = book?.title?.get(0)?.toUpperCase().toString()

    }

    private fun hideBottomSheet() {
        val d = dialog as BottomSheetDialog
        d.cancel()
    }

    private fun closeEditDialog(flag: Boolean){
        val bundle = Bundle()
        bundle.putBoolean("isEdited", true)
        (requireActivity() as MainActivity).navController.navigate(R.id.action_editBookBottomSheetFragment_to_home, bundle)
    }
}
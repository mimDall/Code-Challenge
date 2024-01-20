package com.example.codechallenge.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.codechallenge.network.Resource
import com.example.codechallenge.util.snackbar
import com.example.codechallenge.util.visibile
import com.example.codechallenge.viewmodels.BookViewModel
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.FragmentDeleteBookBottomSheetBinding
import com.example.codingchallenge.databinding.FragmentSearchBinding
import com.example.codingchallenge.databinding.FragmentSearchResultBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteBookBottomSheetFragment: BottomSheetDialogFragment() {

    private lateinit var binding : FragmentDeleteBookBottomSheetBinding
    private val bookViewModel : BookViewModel by viewModels()
    private var bookId : String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteBookBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments?.getInt("bookId")!=null){
            bookId = arguments?.getString("bookId", null).toString()
        }

        binding.deleteCancelBtn.setOnClickListener{
            hideBottomSheet()
        }

        binding.deleteConfirmBtn.setOnClickListener{
            bookId?.let {
                bookViewModel.deleteBook(it)
            }

            bookViewModel.deleteBookResponse.observe(viewLifecycleOwner, {
                when (it) {

                    is Resource.Loading -> {
//                        binding.pbBookList.visibility = View.VISIBLE
                        binding.pbConfirmDelete.visibile(true)
                    }

                    is Resource.Success -> {
                        //show snack
                        requireView().snackbar("book is deleted successfully")
                        closeDeleteDialog(true)

                    }

                    is Resource.Failure -> {
                        requireView().snackbar("book  deletion is failed")
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

    private fun closeDeleteDialog(flag: Boolean){
        val bundle = Bundle()
        bundle.putBoolean("isDeleted", true)
        (requireActivity() as MainActivity).navController.navigate(R.id.action_deleteBookBottomSheetFragment_to_home, bundle)
    }

}
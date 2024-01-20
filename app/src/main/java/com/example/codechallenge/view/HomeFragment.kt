package com.example.codechallenge.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.adapter.BookListRecyclerAdapter
import com.example.codechallenge.data.model.Book
import com.example.codechallenge.network.Resource
import com.example.codechallenge.util.snackbar
import com.example.codechallenge.util.visibile
import com.example.codingchallenge.R
import com.example.codingchallenge.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val TAG = "HomeFragment"
    lateinit var bookListAdapter: BookListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.show() // show toolbar
        (requireActivity() as MainActivity).binding.bottomNav.visibile(true) // show bottom nav

        setBookListAdapter()

        if((arguments?.getBoolean("isDeleted") == true) || (arguments?.getBoolean("isEdited") == true) ||
            (arguments?.getBoolean("isAdded") == true)){
            bookListAdapter.notifyDataSetChanged()
        }
        (requireActivity() as MainActivity).sharedBookViewModel.getBookList()
        (requireActivity() as MainActivity).sharedBookViewModel.bookListResponse.observe(viewLifecycleOwner, {
            when (it) {

                is Resource.Loading -> {
                    Log.d(TAG, "Resource.Loading")
                    binding.pbBookList.visibile(true)

                }

                is Resource.Success -> {
                   bookListAdapter.updateData(it.value)
                    binding.pbBookList.visibile(false)

                }

                is Resource.Failure -> {
                    binding.pbBookList.visibile(false)
                    handleApiError(it)

                }
            }
        })

        binding.fabAddBook.setOnClickListener{
            openAddDialog()
        }
    }

    private fun handleApiError(failure: Resource.Failure) {
        when {
            failure.networkError -> requireView().snackbar(
                "please check your internet connection"){
                (requireActivity() as MainActivity).sharedBookViewModel.getBookList()
            }

            else -> {
                requireView().snackbar(failure.errorBody?.string().toString(), null)
            }
        }

    }

    private fun setBookListAdapter() {
        bookListAdapter = BookListRecyclerAdapter({book-> openDeleteDialog(book.id!!)}, {book->openUpdateDialog(book)},{bookId->openBookDetails(bookId)})
        binding.bookListRecycler.apply {
            adapter = bookListAdapter
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)

        }
    }

    private fun openDeleteDialog(bookId:String){
        val bundle = Bundle()
        bundle.putString("bookId", bookId)
        (requireActivity() as MainActivity).navController.navigate(R.id.action_home_to_deleteBookBottomSheetFragment, bundle)
    }

    private fun openUpdateDialog(book:Book){
        val bundle = Bundle()
        bundle.putParcelable("book", book)
        (requireActivity() as MainActivity).navController.navigate(R.id.action_home_to_editBookBottomSheetFragment, bundle)
    }


    private fun openAddDialog(){
        (requireActivity() as MainActivity).navController.navigate(R.id.action_home_to_addBookBottomSheetFragment)

    }
    private fun openBookDetails(bookId: String) {
        val bundle = Bundle()
        bundle.putString("bookId", bookId)
        (requireActivity() as MainActivity).navController.navigate(
            R.id.action_home_to_bookDetailsBottomSheetFragment,
            bundle
        )
    }

}
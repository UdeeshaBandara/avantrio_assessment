package com.avantrio.assessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avantrio.assessment.repositories.UserRepository

@Suppress("UNCHECKED_CAST")
class UserViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}
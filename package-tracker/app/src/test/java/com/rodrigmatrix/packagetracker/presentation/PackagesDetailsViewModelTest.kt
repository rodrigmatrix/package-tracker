package com.rodrigmatrix.packagetracker.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.rodrigmatrix.packagetracker.CoroutineTestRule
import com.rodrigmatrix.domain.entity.StatusAddress
import com.rodrigmatrix.domain.entity.StatusUpdate
import com.rodrigmatrix.domain.entity.UserPackage
import com.rodrigmatrix.domain.usecase.GetPackageStatusUseCase
import com.rodrigmatrix.packagetracker.presentation.details.PackageStatusViewEffect
import com.rodrigmatrix.packagetracker.presentation.details.PackageStatusViewState
import com.rodrigmatrix.packagetracker.presentation.details.PackagesDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test

private const val PACKAGE_ID = "ABC123"

@ExperimentalCoroutinesApi
class PackagesDetailsViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getPackageStatusUseCase = mockk<GetPackageStatusUseCase>()

    private val viewState = mockk<Observer<PackageStatusViewState?>>()

    private val viewEffect = mockk<Observer<PackageStatusViewEffect>>()

    private val userPackage = UserPackage(
        PACKAGE_ID,
        "name",
        "sedex",
        "20/10/2021",
        listOf(
            StatusUpdate(PACKAGE_ID, "update", "update", StatusAddress(), StatusAddress()),
            StatusUpdate(PACKAGE_ID, "update", "update", StatusAddress(), StatusAddress())
        )
    )

//    @Test
//    fun `when getPackage is success should return list`() {
//        coroutineTestRule.runBlockingTest {
//            // when
//            val viewModel = PackagesDetailsViewModel(getPackageStatusUseCase)
//
//            coEvery {
//                getPackageStatusUseCase(PACKAGE_ID, false)
//            } returns flow { emit(userPackage) }
//
//            viewModel.viewState.observeForever(viewState)
//            viewModel.viewEffect.observeForever(viewEffect)
//
//            viewModel.getPackageStatus(PACKAGE_ID)
//
//            verifyOrder {
//                viewState.onChanged(PackageStatusViewState(isLoading = true))
//            }
//        }
//    }

}
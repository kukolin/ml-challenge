package com.anezin.melichallenge.presentation.search_screen

import com.anezin.melichallenge.presentation.screens.search_screen.SearchScreenViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchScreenViewModelTest {
    private lateinit var viewModel: SearchScreenViewModel

    @Before
    fun setup() {
        viewModel = SearchScreenViewModel()
    }

    @Test
    fun `when doing nothing, then initialize with empty query and can not search`() {
        thenTextToSearchIs("")
        thenCanSearchIs(false)
    }

    @Test
    fun `when update search query, then update textToSearch`() {
        whenUpdateSearchQuery("new text")

        thenTextToSearchIs("new text")
    }

    @Test
    fun `when update search query to empty, then can Not Search`() {
        whenUpdateSearchQuery("")

        thenCanSearchIs(false)
    }

    //WHEN methods
    private fun whenUpdateSearchQuery(query: String){
        viewModel.updateSearchQuery(query)
    }
    //THEN methods

    private fun thenTextToSearchIs(text: String) {
        assertEquals(viewModel.textToSearch, text)
    }

    private fun thenCanSearchIs(state: Boolean) {
        assertEquals(viewModel.canSearch, state)
    }
}
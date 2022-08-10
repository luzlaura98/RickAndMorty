package com.luz.rickmorty.data.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Luz on 10/8/2022.
 */
class InfoTest {

    lateinit var infoBase: Info

    @Before
    fun onBefore(){
        infoBase = Info(42, null, null)
    }

    // PREV PAGE

    @Test
    fun `when prev page is null`(){
        val info = infoBase.copy(prev = null)
        assertEquals(null,info.prevPage)
    }

    @Test
    fun `when prev page is empty`(){
        val info = infoBase.copy(prev = "https://rickandmortyapi.com/api/character/?page=")
        assertEquals(null,info.prevPage)
    }

    @Test
    fun `when prev page has value 1 digit`(){
        val info = infoBase.copy(prev = "https://rickandmortyapi.com/api/character/?page=3")
        assertEquals(3,info.prevPage)
    }

    @Test
    fun `when prev page has value digits`(){
        val info = infoBase.copy(prev = "https://rickandmortyapi.com/api/character/?page=120")
        assertEquals(120,info.prevPage)
    }

    @Test
    fun `when prev page has wrong value`(){
        val info = infoBase.copy(prev = "error")
        assertEquals(null,info.prevPage)
    }

    @Test
    fun `when prev page has invalid value`(){
        val info = infoBase.copy(prev = "https://rickandmortyapi.com/api/character/?page=-10")
        assertEquals(null,info.prevPage)
    }

    @Test
    fun `when prev page is zero`(){
        val info = infoBase.copy(prev = "https://rickandmortyapi.com/api/character/?page=0")
        assertEquals(null,info.prevPage)
    }


    // NEXT PAGE

    @Test
    fun `when next page is null`(){
        val info = infoBase.copy(next = null)
        assertEquals(null,info.nextPage)
    }

    @Test
    fun `when next page is empty`(){
        val info = infoBase.copy(next = "https://rickandmortyapi.com/api/character/?page=")
        assertEquals(null,info.nextPage)
    }

    @Test
    fun `when next page has value 1 digit`(){
        val info = infoBase.copy(next = "https://rickandmortyapi.com/api/character/?page=3")
        assertEquals(3,info.nextPage)
    }

    @Test
    fun `when next page has value digits`(){
        val info = infoBase.copy(next = "https://rickandmortyapi.com/api/character/?page=120")
        assertEquals(120,info.nextPage)
    }

    @Test
    fun `when next page has wrong value`(){
        val info = infoBase.copy(next = "error")
        assertEquals(null,info.nextPage)
    }

    @Test
    fun `when next page has invalid value`(){
        val info = infoBase.copy(next = "https://rickandmortyapi.com/api/character/?page=-10")
        assertEquals(null,info.nextPage)
    }

    @Test
    fun `when next page is zero`(){
        val info = infoBase.copy(next = "https://rickandmortyapi.com/api/character/?page=0")
        assertEquals(null,info.nextPage)
    }
}
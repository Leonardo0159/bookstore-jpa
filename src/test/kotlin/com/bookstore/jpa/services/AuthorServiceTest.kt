package com.bookstore.jpa.services

import com.bookstore.jpa.exceptions.AuthorNotFoundException
import com.bookstore.jpa.models.AuthorModel
import com.bookstore.jpa.models.BookModel
import com.bookstore.jpa.repositories.AuthorRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockitoExtension::class)
class AuthorServiceTest {

    @Mock
    private lateinit var authorRepository: AuthorRepository

    @InjectMocks
    private lateinit var authorService: AuthorService

    private lateinit var author: AuthorModel

    @BeforeEach
    fun setUp() {
        author = AuthorModel(id = 1L, name = "Test Author")
    }

    @Test
    @DisplayName("Test Author Not Found Exception In FindById")
    fun testAuthorNotFoundExceptionInFindById() {
        `when`(authorRepository.findById(any())).thenReturn(Optional.empty())

        val thrown = Assertions.assertThrows(AuthorNotFoundException::class.java){
            authorService.findById(1L)
        }

        Assertions.assertEquals("Author id 1 not found", thrown.message)
    }

    @Test
    @DisplayName("Test Save Author")
    fun testSaveAuthor() {
        `when`(authorRepository.save(any())).thenReturn(author)

        val savedAuthor = authorService.save(author)

        assert(savedAuthor.id == 1L)
        assert(savedAuthor.name == "Test Author")

        verify(authorRepository, times(1)).save(any())
    }

    @Test
    @DisplayName("Test Update Author")
    fun testUpdateAuthor() {
        val existingBook = AuthorModel(id = 1L, name = "Old Author")

        `when`(authorRepository.findById(any())).thenReturn(Optional.of(existingBook))

        val updatedAuthor = existingBook.copy(
            name = "New Author"
        )

        `when`(authorRepository.save(any())).thenReturn(updatedAuthor)

        val result = authorService.update(1L, author)

        assert(result?.name == "New Author")

        verify(authorRepository, times(1)).save(any())
    }

    @Test
    @DisplayName("Test Author Not Found Exception In Update")
    fun testAuthorNotFoundExceptionInUpdate() {
        `when`(authorRepository.findById(any())).thenReturn(Optional.empty())

        val thrown = Assertions.assertThrows(AuthorNotFoundException::class.java){
            authorService.update(1L, author)
        }

        Assertions.assertEquals("Author id 1 not found", thrown.message)

        verify(authorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test Author Not Found Exception In Delete")
    fun testAuthorNotFoundExceptionInDelete() {
        `when`(authorRepository.existsById(any())).thenReturn(false)

        val thrown = Assertions.assertThrows(AuthorNotFoundException::class.java){
            authorService.deleteById(1L)
        }

        Assertions.assertEquals("Author id 1 not found", thrown.message)

        verify(authorRepository, never()).deleteById(any());
    }
}
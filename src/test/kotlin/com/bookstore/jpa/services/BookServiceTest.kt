package com.bookstore.jpa.services

import com.bookstore.jpa.dto.BookDTO
import com.bookstore.jpa.exceptions.AuthorNotFoundException
import com.bookstore.jpa.exceptions.PublisherNotFoundException
import com.bookstore.jpa.models.AuthorModel
import com.bookstore.jpa.models.BookModel
import com.bookstore.jpa.models.PublisherModel
import com.bookstore.jpa.repositories.AuthorRepository
import com.bookstore.jpa.repositories.BookRepository
import com.bookstore.jpa.repositories.PublisherRepository
import com.bookstore.jpa.services.BookService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    @Mock
    private lateinit var bookRepository: BookRepository

    @Mock
    private lateinit var publisherRepository: PublisherRepository

    @Mock
    private lateinit var authorRepository: AuthorRepository

    @InjectMocks
    private lateinit var bookService: BookService

    private lateinit var bookDTO: BookDTO
    private lateinit var publisher: PublisherModel
    private lateinit var author1: AuthorModel
    private lateinit var author2: AuthorModel

    @BeforeEach
    fun setUp() {
        bookDTO = BookDTO(
            title = "Test Book",
            publisherId = 1L,
            authorIds = listOf(1L, 2L),
            review = "Great book!"
        )

        publisher = PublisherModel(id = 1L, name = "Test Publisher")
        author1 = AuthorModel(id = 1L, name = "Test Author 1")
        author2 = AuthorModel(id = 2L, name = "Test Author 2")
    }

    @Test
    @DisplayName("Test Save Book")
    fun testSaveBook() {
        // Simula o comportamento de encontrar o Publisher e os Authors
        `when`(publisherRepository.findById(any())).thenReturn(Optional.of(publisher))
        `when`(authorRepository.findAllById(any())).thenReturn(listOf(author1, author2))

        // Configura o BookModel para ser salvo
        val bookToSave = BookModel(
            title = bookDTO.title,
            publisher = publisher,
            authors = listOf(author1, author2),
            review = null
        )

        // Simula o comportamento do repository.save
        `when`(bookRepository.save(any())).thenReturn(bookToSave)

        // Chama o método save do BookService
        val savedBook = bookService.save(bookDTO)

        // Verifica se o livro foi salvo corretamente
        assert(savedBook.title == bookDTO.title)
        assert(savedBook.publisher == publisher)
        assert(savedBook.authors.contains(author1))
        assert(savedBook.authors.contains(author2))

        // Verifica se o book repository foi chamado uma vez
        verify(bookRepository, times(1)).save(any())
    }

    @Test
    @DisplayName("Test Publisher Not Found Exception")
    fun testPublisherNotFoundException() {
        // Simula o comportamento de não encontrar o Publisher
        `when`(publisherRepository.findById(any())).thenReturn(Optional.empty())

        // Captura a exceção lançada
        val thrown = Assertions.assertThrows(PublisherNotFoundException::class.java) {
            bookService.save(bookDTO)
        }

        // Verifica se a exceção capturada contém a mensagem correta
        Assertions.assertEquals("Publisher not found!", thrown.message)

        // Verifica se o método save do repository não foi chamado
        verify(bookRepository, never()).save(any())
    }

    @Test
    @DisplayName("Test Author Not Found Exception")
    fun testAuthorNotFoundException() {
        // Simula o comportamento de encontrar o Publisher, mas não encontrar todos os Authors
        `when`(publisherRepository.findById(any())).thenReturn(Optional.of(publisher))
        `when`(authorRepository.findAllById(any())).thenReturn(listOf(author1))

        // Captura a exceção lançada
        val thrown = Assertions.assertThrows(AuthorNotFoundException::class.java) {
            bookService.save(bookDTO)
        }

        // Verifica se a exceção capturada contém a mensagem correta
        Assertions.assertEquals("Author not found!", thrown.message)

        // Verifica se o método save do repository não foi chamado
        verify(bookRepository, never()).save(any())
    }
}

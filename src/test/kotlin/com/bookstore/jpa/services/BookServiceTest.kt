package com.bookstore.jpa.services

import com.bookstore.jpa.dto.BookDTO
import com.bookstore.jpa.exceptions.AuthorNotFoundException
import com.bookstore.jpa.exceptions.BookNotFoundException
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

    private lateinit var bookModel: BookModel
    private lateinit var bookDTO: BookDTO
    private lateinit var publisher: PublisherModel
    private lateinit var author1: AuthorModel
    private lateinit var author2: AuthorModel

    @BeforeEach
    fun setUp() {
        bookDTO = BookDTO(
            title = "Test BookDTO",
            publisherId = 1L,
            authorIds = listOf(1L, 2L),
            review = "Great book!"
        )

        publisher = PublisherModel(id = 1L, name = "Test Publisher")
        author1 = AuthorModel(id = 1L, name = "Test Author 1")
        author2 = AuthorModel(id = 2L, name = "Test Author 2")

        bookModel = BookModel(
            title = "Test BookModel",
            publisher = publisher,
            authors = mutableListOf(author1, author2),
            review = null
        )
    }

    @Test
    @DisplayName("Test Save Book")
    fun testSaveBook() {
        // Simula o comportamento de encontrar o Publisher e os Authors
        `when`(publisherRepository.findById(any())).thenReturn(Optional.of(publisher))
        `when`(authorRepository.findAllById(any())).thenReturn(mutableListOf(author1, author2))

        // Configura o BookModel para ser salvo
        val bookToSave = BookModel(
            title = bookDTO.title,
            publisher = publisher,
            authors = mutableListOf(author1, author2),
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
    @DisplayName("Test Publisher Not Found Exception in Save")
    fun testPublisherNotFoundExceptionInSave() {
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
    @DisplayName("Test Author Not Found Exception in Save")
    fun testAuthorNotFoundExceptionInSave() {
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

    @Test
    @DisplayName("Test Update Book")
    fun testUpdateBook() {
        // Configura os dados do livro existente
        val existingBook = BookModel(
            id = 1L,
            title = "Old Test Book",
            publisher = publisher,
            authors = mutableListOf(author1, author2),
            review = null
        )

        // Simula o comportamento de encontrar o livro existente
        `when`(bookRepository.findById(any())).thenReturn(Optional.of(existingBook))

        // Simula o comportamento de encontrar o Publisher e os Authors
        `when`(publisherRepository.findById(1L)).thenReturn(Optional.of(publisher))
        `when`(authorRepository.findAllById(listOf(1L, 2L))).thenReturn(mutableListOf(author1, author2))

        // Configura o livro atualizado
        val updatedBook = existingBook.copy(
            title = bookDTO.title,
            publisher = publisher,
            authors = mutableListOf(author1, author2),
            review = null
        )

        // Simula o comportamento do repository.save para salvar o livro atualizado
        `when`(bookRepository.save(any(BookModel::class.java))).thenReturn(updatedBook)

        // Chama o método update do BookService
        val result = bookService.update(1L, bookDTO)

        // Verifica se o livro foi atualizado corretamente
        assert(result?.title == bookDTO.title)
        assert(result?.publisher == publisher)
        assert(result?.authors?.contains(author1) == true)
        assert(result?.authors?.contains(author2) == true)

        // Verifica se o book repository foi chamado para salvar o livro atualizado
        verify(bookRepository, times(1)).save(any(BookModel::class.java))
    }

    @Test
    @DisplayName("Test Book Not Found Exception in Update")
    fun testBookNotFoundExceptionInUpdate() {
        // Simula o comportamento de não encontrar o livro
        `when`(bookRepository.findById(any())).thenReturn(Optional.empty())

        // Captura a exceção lançada
        val thrown = Assertions.assertThrows(BookNotFoundException::class.java) {
            bookService.update(1L, bookDTO)
        }

        // Verifica se a exceção capturada contém a mensagem correta
        Assertions.assertEquals("Book id 1 not found", thrown.message)

        // Verifica se o método save do repository não foi chamado
        verify(bookRepository, never()).save(any())
    }

    @Test
    @DisplayName("Test Publisher Not Found Exception in Update")
    fun testPublisherNotFoundExceptionInUpdate() {
        // Simula o comportamento de não encontrar o Publisher
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.of(bookModel))
        `when`(publisherRepository.findById(anyLong())).thenReturn(Optional.empty())

        // Captura a exceção lançada
        val thrown = Assertions.assertThrows(PublisherNotFoundException::class.java) {
            bookService.update(1L, bookDTO)
        }

        // Verifica se a exceção capturada contém a mensagem correta
        Assertions.assertEquals("Publisher not found!", thrown.message)

        // Verifica se o método save do repository não foi chamado
        verify(bookRepository, never()).save(any())
    }

    @Test
    @DisplayName("Test Author Not Found Exception in Update")
    fun testAuthorNotFoundExceptionInUpdate() {
        // Simula o comportamento de encontrar o Book e Publisher, mas não encontrar todos os Authors
        `when`(bookRepository.findById(anyLong())).thenReturn(Optional.of(bookModel))
        `when`(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher))
        `when`(authorRepository.findAllById(any())).thenReturn(listOf(author1)) // Simula apenas um autor encontrado

        // Captura a exceção lançada
        val thrown = Assertions.assertThrows(AuthorNotFoundException::class.java) {
            bookService.update(1L, bookDTO)
        }

        // Verifica se a exceção capturada contém a mensagem correta
        Assertions.assertEquals("Author not found!", thrown.message)

        // Verifica se o método save do repository não foi chamado
        verify(bookRepository, never()).save(any())
    }

    @Test
    @DisplayName("Test Book Not Found Exception in Delete")
    fun testBookNotFoundExceptionInDelete() {
        // Simula o comportamento de não encontrar o livro
        `when`(bookRepository.existsById(any())).thenReturn(false)

        // Captura a exceção lançada
        val thrown = Assertions.assertThrows(BookNotFoundException::class.java) {
            bookService.deleteById(1L)
        }

        // Verifica se a exceção capturada contém a mensagem correta
        Assertions.assertEquals("Book id 1 not found", thrown.message)

        // Verifica se o método delete do repository não foi chamado
        verify(bookRepository, never()).deleteById(any())
    }

    @Test
    @DisplayName("Test Book Not Found Exception in FindById")
    fun testBookNotFoundExceptionInFindById() {
        // Simula o comportamento de não encontrar o livro
        `when`(bookRepository.findById(any())).thenReturn(Optional.empty())

        // Captura a exceção lançada
        val thrown = Assertions.assertThrows(BookNotFoundException::class.java) {
            bookService.findById(1L)
        }

        // Verifica se a exceção capturada contém a mensagem correta
        Assertions.assertEquals("Book id 1 not found", thrown.message)
    }
}

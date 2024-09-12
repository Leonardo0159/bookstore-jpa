package com.bookstore.jpa.repositories

import com.bookstore.jpa.models.AuthorModel
import com.bookstore.jpa.models.BookModel
import com.bookstore.jpa.models.PublisherModel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookRepositoryTest {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var publisherRepository: PublisherRepository

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    private lateinit var publisher: PublisherModel
    private lateinit var author1: AuthorModel
    private lateinit var author2: AuthorModel

    @BeforeEach
    fun setUp() {
        // Configura as entidades relacionadas, como Publisher e Author
        publisher = PublisherModel(id = 1L, name = "Test Publisher")
        publisherRepository.save(publisher)

        author1 = AuthorModel(id = 1L, name = "Test Author 1")
        author2 = AuthorModel(id = 2L, name = "Test Author 2")
        authorRepository.saveAll(mutableListOf(author1, author2))
    }

    @Test
    @DisplayName("Test Save Book")
    fun testSaveBook() {
        // Cria o livro
        val book = BookModel(
            title = "Test Book",
            publisher = publisher,
            authors = mutableListOf(author1, author2),
            review = null
        )

        // Salva o livro no banco de dados
        val savedBook = bookRepository.save(book)

        // Verifica se o livro foi salvo corretamente
        assert(savedBook.id != null)
        assert(savedBook.title == "Test Book")
        assert(savedBook.publisher == publisher)
        assert(savedBook.authors.contains(author1))
        assert(savedBook.authors.contains(author2))
    }

    @Test
    @DisplayName("Test Update Book")
    fun testUpdateBook() {
        // Cria o livro
        val book = BookModel(
            title = "Test Book",
            publisher = publisher,
            authors = mutableListOf(author1, author2),
            review = null
        )

        // Salva o livro no banco de dados
        val savedBook = bookRepository.save(book)

        val newPublisher = PublisherModel(id = 10L, name = "Test New Publisher")
        val savedNewPublisher = publisherRepository.save(newPublisher)

        val updatedBook = savedBook.copy(
            title = "Test Book Update",
            publisher = savedNewPublisher,
            authors = mutableListOf(author1, author2)
        )

        // Salva o livro atualizado
        val savedUpdatedBook = bookRepository.save(updatedBook)

        // Verifica se o livro foi atualizado corretamente
        assert(savedUpdatedBook.id == savedBook.id)
        assert(savedUpdatedBook.title == "Test Book Update")
        assert(savedUpdatedBook.publisher == savedNewPublisher)
        assert(savedUpdatedBook.authors.contains(author1))
        assert(savedUpdatedBook.authors.contains(author2))
        assert(savedUpdatedBook.publisher != publisher)
    }
}

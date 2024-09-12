package com.bookstore.jpa.services

import com.bookstore.jpa.exceptions.PublisherNotFoundException
import com.bookstore.jpa.models.PublisherModel
import com.bookstore.jpa.repositories.PublisherRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class PublisherServiceTest {

    @Mock
    private lateinit var publisherRepository: PublisherRepository

    @InjectMocks
    private lateinit var publisherService: PublisherService

    private lateinit var publisher: PublisherModel

    @BeforeEach
    fun setup() {
        publisher = PublisherModel(1L, "Test Publisher")
    }

    @Test
    @DisplayName("Test Publisher Not Found Exception In FindById")
    fun testPublisherNotFoundExceptionInFindById() {
        `when`(publisherRepository.findById(any())).thenReturn(Optional.empty())

        val thrown = Assertions.assertThrows(PublisherNotFoundException::class.java){
            publisherService.findById(1L)
        }

        Assertions.assertEquals("Publisher id 1 not found", thrown.message)
    }

    @Test
    @DisplayName("Test Save Publisher")
    fun testSavePublisher() {
        `when`(publisherRepository.save(any())).thenReturn(publisher)

        val savedPublishe = publisherService.save(publisher)

        assert(savedPublishe.id == 1L)
        assert(savedPublishe.name == "Test Publisher")

        verify(publisherRepository, times(1)).save(any())
    }

    @Test
    @DisplayName("Test Update Publisher")
    fun testUpdatePublisher() {
        `when`(publisherRepository.findById(any())).thenReturn(Optional.of(publisher))

        val updatedPublisher = publisher.copy(
            name = "New Publisher"
        )

        `when`(publisherRepository.save(any())).thenReturn(updatedPublisher)

        val result = publisherService.update(1L, publisher)

        assert(result?.name == "New Publisher")

        verify(publisherRepository, times(1)).save(any())
    }

    @Test
    @DisplayName("Test Publisher Not Found Exception In Update")
    fun testPublisherNotFoundExceptionInUpdate() {
        `when`(publisherRepository.findById(any())).thenReturn(Optional.empty())

        val thrown = Assertions.assertThrows(PublisherNotFoundException::class.java){
            publisherService.update(1L, publisher)
        }

        Assertions.assertEquals("Publisher id 1 not found", thrown.message)

        verify(publisherRepository, never()).save(any())
    }

    @Test
    @DisplayName("Test Publisher Not Found Exception In Delete")
    fun testPublisherNotFoundExceptionInDelete() {
        `when`(publisherRepository.existsById(any())).thenReturn(false)

        val thrown = Assertions.assertThrows(PublisherNotFoundException::class.java){
            publisherService.deleteById(1L)
        }

        Assertions.assertEquals("Publisher id 1 not found", thrown.message)

        verify(publisherRepository, never()).deleteById(any())
    }
}
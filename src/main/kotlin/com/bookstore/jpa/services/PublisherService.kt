package com.bookstore.jpa.services

import com.bookstore.jpa.models.PublisherModel
import com.bookstore.jpa.repositories.PublisherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class PublisherService(
    private val publisherRepository: PublisherRepository
) {

    fun findAll(): List<PublisherModel> {
        return publisherRepository.findAll()
    }

    fun findById(id: Long): Optional<PublisherModel> {
        return publisherRepository.findById(id)
    }

    @Transactional
    fun save(publisher: PublisherModel): PublisherModel {
        return publisherRepository.save(publisher)
    }

    @Transactional
    fun update(id: Long, publisher: PublisherModel): PublisherModel? {
        val existingPublisher = publisherRepository.findById(id)
        return if (existingPublisher.isPresent) {
            val updatedPublisher = existingPublisher.get().copy(
                name = publisher.name
            )
            publisherRepository.save(updatedPublisher)
        } else {
            null
        }
    }

    @Transactional
    fun deleteById(id: Long) {
        if (publisherRepository.existsById(id)) {
            publisherRepository.deleteById(id)
        }
    }
}
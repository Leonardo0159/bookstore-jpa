package com.bookstore.jpa.controllers

import com.bookstore.jpa.models.PublisherModel
import com.bookstore.jpa.services.PublisherService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/publishers")
class PublisherController(
    private val publisherService: PublisherService
) {

    @GetMapping
    fun getAllPublishers(): ResponseEntity<List<PublisherModel>> {
        val publishers = publisherService.findAll()
        return ResponseEntity.ok(publishers)
    }

    @GetMapping("/{id}")
    fun getPublisherById(@PathVariable id: Long): ResponseEntity<PublisherModel> {
        val publisher = publisherService.findById(id)
        return if (publisher.isPresent) {
            ResponseEntity.ok(publisher.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createPublisher(@RequestBody publisher: PublisherModel): ResponseEntity<PublisherModel> {
        val createdPublisher = publisherService.save(publisher)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPublisher)
    }

    @PutMapping("/{id}")
    fun updatePublisher(
        @PathVariable id: Long,
        @RequestBody publisher: PublisherModel
    ): ResponseEntity<PublisherModel> {
        val updatedPublisher = publisherService.update(id, publisher)
        return if (updatedPublisher != null) {
            ResponseEntity.ok(updatedPublisher)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletePublisher(@PathVariable id: Long): ResponseEntity<Void> {
        return if (publisherService.findById(id).isPresent) {
            publisherService.deleteById(id)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

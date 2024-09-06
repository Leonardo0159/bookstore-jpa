package com.bookstore.jpa.repositories

import com.bookstore.jpa.models.PublisherModel
import org.springframework.data.jpa.repository.JpaRepository

interface PublisherRepository : JpaRepository<PublisherModel, Long>{
}
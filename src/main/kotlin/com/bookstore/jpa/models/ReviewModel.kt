package com.bookstore.jpa.models

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "TB_REVIEW")
data class ReviewModel(

    @Id
    @Column(name = "book_id")
    val id: Long? = null,

    @Column
    var comment: String,

    // Um review para um Livro
    @JsonBackReference
    @OneToOne
    @MapsId
    @JoinColumn(name = "book_id")
    var book: BookModel?
)
package com.bookstore.jpa.models

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "TB_BOOK")
data class BookModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var title: String,

    // Muitos Livros para um Publicador
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    var publisher: PublisherModel,

    // Muito Livros para muitos Autores
    @ManyToMany
    @JoinTable(
        name = "TB_BOOK_AUTHOR",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    var authors: MutableList<AuthorModel> = mutableListOf(),

    // Um livro para um Review
    @JsonManagedReference
    @OneToOne(mappedBy = "book", cascade = [CascadeType.ALL])
    @PrimaryKeyJoinColumn
    var review: ReviewModel? = null
)
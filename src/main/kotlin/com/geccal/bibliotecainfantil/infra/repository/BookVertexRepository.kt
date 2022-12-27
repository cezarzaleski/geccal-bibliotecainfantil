package com.geccal.bibliotecainfantil.infra.repository

import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.core.domain.vo.Origin
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import com.geccal.bibliotecainfantil.core.domain.vo.StatusBook
import com.geccal.bibliotecainfantil.infra.database.Connection
import io.vertx.sqlclient.Row

class BookVertexRepository(
    private val connection: Connection
) : BookRepository {

    override suspend fun create(book: Book): Book {
        val params = mapOf(
            "id" to book.id.value,
            "name" to book.name,
            "exemplary" to book.exemplary,
            "status" to book.status,
            "edition" to book.edition,
            "year" to book.year,
            "publisher" to book.publisher.value,
            "origin" to book.origin.name,
            "authors" to book.authors,
            "createdAt" to book.createdAt,
            "updatedAt" to book.updatedAt,
            "deletedAt" to book.deletedAt,
        )
        connection.persist<Row>(
            "insert into books (id, name, exemplary, status, edition, year, publisher, origin, authors, " +
                    "createdAt, updatedAt, deletedAt) " +
                    "values (#{id}, #{name}, #{exemplary}, #{status}, #{edition}, #{year}, #{publisher}, #{origin}, " +
                    "#{authors}, #{createdAt}, #{updatedAt}, #{deletedAt})",
            params = params
        )
        return book
    }

    private fun Row.toBook(): Book {
        return Book.from(
            id = BookID.from(getString("id")),
            name = getString("name"),
            exemplary = getInteger("exemplary"),
            status = StatusBook.valueOf(getString("status")),
            edition = getString("edition"),
            year = getInteger("year"),
            publisher = Publisher.from(getString("publisher")),
            origin = Origin.valueOf(getString("origin")),
            createdAt = getLocalDateTime("createdAt"),
            updatedAt = getLocalDateTime("updatedAt"),
            deletedAt = getLocalDateTime("deletedAt"),
            authors = arrayListOf(),
        )
    }

//    private fun Row.toItem(): Item {
//        return Item(
//            id = getInteger("id_item"),
//            category = getString("category"),
//            description = getString("description"),
//            price = getBigDecimal("price"),
//            weight = getInteger("weight"),
//            dimension = Dimension(
//                getInteger("width"),
//                getInteger("height"),
//                getInteger("length"),
//            )
//        )
//    }
}

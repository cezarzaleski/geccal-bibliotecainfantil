package com.geccal.bibliotecainfantil.infra.repository

import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.exception.NotFoundException
import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.core.domain.vo.Origin
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import com.geccal.bibliotecainfantil.core.domain.vo.StatusBook
import com.geccal.bibliotecainfantil.infra.database.Connection
import com.geccal.bibliotecainfantil.infra.extension.toJson
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet

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
            "authors" to book.authors.toJson(),
            "createdAt" to book.createdAt,
            "updatedAt" to book.updatedAt,
            "deletedAt" to book.deletedAt,
        )
        connection.persist(
            "insert into books (id, name, exemplary, status, edition, year, publisher, origin, authors, " +
                    "createdAt, updatedAt, deletedAt) " +
                    "values (#{id}, #{name}, #{exemplary}, #{status}, #{edition}, #{year}, #{publisher}, #{origin}, " +
                    "#{authors}, #{createdAt}, #{updatedAt}, #{deletedAt})",
            params = params
        )
        return book
    }

    override suspend fun findById(id: BookID): Book {
        val bookDataList = connection.query<RowSet<Row>>("SELECT id, name, exemplary, status, edition, " +
                "year, publisher, origin, authors, createdAt, updatedAt, deletedAt " +
                "FROM books " +
                "WHERE id = #{id}", mapOf("id" to id.value))
        if (bookDataList.size() == 0) throw NotFoundException.from("Book", id)
        return bookDataList.first().toBook()
    }

    override suspend fun findAll(query: SearchQuery): Pagination<Book> {
        val (page, perPage, terms, sort, direction) = query

        var statement = "SELECT id, name, exemplary, status, edition, " +
                "year, publisher, origin, authors, createdAt, updatedAt, deletedAt " +
                "FROM books " +
                "WHERE 1=1 "
        if (terms.isNotEmpty()) {
            statement += "AND ((LOWER(name) LIKE #{terms}) OR (LOWER(publisher) LIKE #{terms})) "
        }
        val statementCount = "SELECT count(1) as total from ($statement) as query"
        statement += "ORDER BY $sort $direction LIMIT $perPage OFFSET $page"

        val params = mapOf(
            "terms" to "%${terms.lowercase()}%"
        )
        val bookDataList = connection.query<RowSet<Row>>(statement, params)
        val countResult = connection.query<RowSet<Row>>(statementCount, params)
        if (bookDataList.size() == 0) return Pagination.empty(page, perPage)
        return Pagination(
            currentPage = page,
            perPage = perPage,
            total = countResult.first().getLong("total"),
            items = bookDataList.map { it.toBook() }
        )
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
}

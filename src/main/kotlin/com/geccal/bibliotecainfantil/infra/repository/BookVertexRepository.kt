package com.geccal.bibliotecainfantil.infra.repository

import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.exception.NotFoundException
import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.core.domain.vo.Author
import com.geccal.bibliotecainfantil.core.domain.vo.Origin
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import com.geccal.bibliotecainfantil.core.domain.vo.StatusBook
import com.geccal.bibliotecainfantil.infra.database.Connection
import com.geccal.bibliotecainfantil.infra.extension.toJson
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet
import java.time.temporal.ChronoUnit

class BookVertexRepository(
    private val connection: Connection
) : BookRepository {

    override suspend fun create(book: Book): Book {
        connection.persist(
            """INSERT INTO books (id, name, exemplary, status, edition, year, publisher, origin, authors, 
                createdAt, updatedAt, deletedAt)
                values (#{id}, #{name}, #{exemplary}, #{status}, #{edition}, #{year}, #{publisher}, #{origin},
                #{authors}, #{createdAt}, #{updatedAt}, #{deletedAt})""",
            params = book.toParams()
        )
        return book
    }

    override suspend fun findById(id: BookID): Book {
        val bookDataList = connection.query<RowSet<Row>>(
            """SELECT id, name, exemplary, status, edition,
                year, publisher, origin, authors, createdAt, updatedAt, deletedAt
                FROM books
                WHERE id = #{id}""",
            mapOf("id" to id.value)
        )
        if (bookDataList.size() == 0) throw NotFoundException.from("Book", id)
        return bookDataList.first().toBook()
    }

    override suspend fun findAll(query: SearchQuery): Pagination<Book> {
        val (page, perPage, terms, sort, direction) = query
        var statement = """SELECT id, name, exemplary, status, edition, 
            year, publisher, origin, authors, createdAt, updatedAt, deletedAt
            FROM books
            WHERE 1=1 """
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

    override suspend fun findAllAuthors(query: SearchQuery): Pagination<String> {
        val (page, perPage, terms, sort, direction) = query

        var statement = """
            SELECT DISTINCT author
            from
                books,
                json_table(
                        authors,
                        "${'$'}[*]"
                        columns (author varchar(50) PATH "${'$'}")
                    ) a WHERE 1=1"""
        if (terms.isNotEmpty()) {
            statement += " AND LOWER(author) LIKE #{terms} "
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
            items = bookDataList.map { it.getString("author") }
        )
    }

    override suspend fun update(book: Book): Book {
        connection.persist(
            "UPDATE books " +
                "SET name = #{name}, " +
                "exemplary = #{exemplary}, " +
                "status = #{status}, " +
                "edition = #{edition}, " +
                "year = #{year}, " +
                "publisher = #{publisher}, " +
                "origin = #{origin}, " +
                "authors = #{authors}, " +
                "createdAt = #{createdAt}, " +
                "updatedAt = #{updatedAt}, " +
                "deletedAt = #{deletedAt} " +
                "WHERE id = #{id}",
            params = book.toParams()
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
            createdAt = getLocalDateTime("createdAt").truncatedTo(ChronoUnit.MICROS),
            updatedAt = getLocalDateTime("updatedAt").truncatedTo(ChronoUnit.MICROS),
            deletedAt = getLocalDateTime("deletedAt")?.truncatedTo(ChronoUnit.MICROS),
            authors = (getJson("authors") as Iterable<*>).map { Author.create(it as String) }.toMutableList()
        )
    }

    private fun Book.toParams(): Map<String, Any?> {
        return mapOf(
            "id" to this.id.value,
            "name" to this.name,
            "exemplary" to this.exemplary,
            "status" to this.status,
            "edition" to this.edition,
            "year" to this.year,
            "publisher" to this.publisher.value,
            "origin" to this.origin.name,
            "authors" to this.authors.map { it.value }.toJson(),
            "createdAt" to this.createdAt,
            "updatedAt" to this.updatedAt,
            "deletedAt" to this.deletedAt
        )
    }
}

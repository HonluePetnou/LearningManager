package com.example.librarymanager.Database;

import java.util.List;

/**
 * Generic repository interface for basic CRUD operations.
 *
 * This interface defines the standard methods for creating, updating, deleting,
 * listing, and counting entities in the database.
 *
 * Main features:
 * - Provides generic CRUD operations for any entity type.
 * - Ensures consistency across all DAO (Data Access Object) implementations.
 *
 * Type Parameters:
 * - T: the type of the entity managed by the repository.
 *
 * Methods:
 * - create(T entity): Adds a new entity to the database.
 * - Update(T entity): Updates an existing entity in the database.
 * - Delete(int id): Deletes an entity by its ID.
 * - listAll(): Retrieves all entities from the database.
 * - Count(): Returns the total number of entities in the database.
 */
public interface Repository<T> {
    public void create(T entity) throws Exception;

    public void Update(T entity) throws Exception;

    public void Delete(int id) throws Exception;

    public List<T> listAll() throws Exception;

    public int Count() throws Exception;
}

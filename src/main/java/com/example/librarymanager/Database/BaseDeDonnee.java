package com.example.librarymanager.Database;

public interface BaseDeDonnee<T> {
    public void create(T entity) ;
    public void Update(T entity) ;
    public void Delete(int id);
}

package com.example.librarymanager.Database;

import java.util.List;

public interface Repository<T> {
    public void create(T entity) ;
    public void Update(T entity) ;
    public void Delete(int id);
    public List<T> listAll() ;
}

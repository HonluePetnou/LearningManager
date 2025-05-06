package com.example.librarymanager.Database;

import java.util.List;

public interface Repository<T> {
    public void create(T entity) throws Exception ;
    public void Update(T entity) throws Exception;
    public void Delete(int id) throws Exception;
    public List<T> listAll() throws Exception; 
}

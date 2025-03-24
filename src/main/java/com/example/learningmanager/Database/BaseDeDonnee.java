package repository;

public interface BaseDeDonnee<T> {
    public void create(T entity) ;
    public T Read(int id);
    public void Update(T entity) ;
    public void Delete(int id);
}

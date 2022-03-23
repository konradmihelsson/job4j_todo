package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;

public class HbnStore implements AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbnStore() {
    }

    private static final class Lazy {
        private static final HbnStore INST = new HbnStore();
    }

    public static HbnStore instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = this.sf.openSession();
        final Transaction tr = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tr.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void add(Item item) {
        this.tx(session -> session.save(item));
    }

    public void changeIsDoneFlag(int id) {
        this.tx(
                session -> {
                    Query query = session.
                            createQuery("update ru.job4j.todo.model.Item i set i.done = true where i.id = :id");
                    query.setParameter("id", id);
                    return query.executeUpdate();
                }
        );
    }

    public List<Item> findAll(User user) {
        return this.tx(
                session -> {
                    Query query = session.
                            createQuery("from ru.job4j.todo.model.Item i where i.user.id = :id ORDER BY i.id");
                    query.setParameter("id", user.getId());
                    return query.list();
                }
        );
    }

    public List<Item> findNotDone(User user) {
        return this.tx(
                session -> {
                    Query query = session.
                            createQuery("from ru.job4j.todo.model.Item i "
                                    + "where i.user.id = :id AND done=false ORDER BY i.id");
                    query.setParameter("id", user.getId());
                    return query.list();
                }
        );
    }

    public void add(User user) {
        this.tx(session -> session.save(user));
    }

    public User findUserByEmail(String email) {
        return this.tx(
                session -> {
                    Query query = session.
                            createQuery("from ru.job4j.todo.model.User where email = :email");
                    query.setParameter("email", email);
                    return (User) query.uniqueResult();
                }
        );
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

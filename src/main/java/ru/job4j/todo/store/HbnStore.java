package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

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
        this.tx(session -> {
            Item persistentInstance = session.get(Item.class, id);
            if (persistentInstance != null) {
                persistentInstance.setDone(!persistentInstance.isDone());
                session.update(persistentInstance);
            }
            return  persistentInstance;
        });
    }

    public List<Item> findAll() {
        return this.tx(
                session -> session.
                        createQuery("from ru.job4j.todo.model.Item i ORDER BY i.id").list()
        );
    }

    public List<Item> findNotDone() {
        return this.tx(
                session -> session
                        .createQuery("from ru.job4j.todo.model.Item i where done=false ORDER BY i.id").list()
        );
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

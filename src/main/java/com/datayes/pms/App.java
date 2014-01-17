package com.datayes.pms;

import com.datayes.pms.entity.Book;
import com.datayes.pms.entity.Reader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * Hello world!
 *
 */
public class App {

    private EntityManagerFactory entityManagerFactory;

    private List<Reader> readers = new LinkedList<>();

    private ExecutorService executorService = Executors.newFixedThreadPool(1000, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Flusher");
        }
    });

    private List<Future<?>> futures = new LinkedList<>();

    public void start()
    {
        // Set up
        entityManagerFactory = Persistence.createEntityManagerFactory("persistUnit");

        flushReaders();

        try {
            Thread.sleep(2000);
            //Thread.sleep(10000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        flushBook();
        waitForCompletion();
    }

    public void insert50000Readers() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            for ( int i = 0; i < 10; i++) {
                Reader reader = new Reader("Reader"+i);
                readers.add(reader);
                entityManager.persist(reader);

                try {
                    //Thread.sleep(1);
                    Thread.sleep(10000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error " + System.currentTimeMillis());
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
    }

    public void insertOneBook() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        try {
            Book book = new Book(readers.get(0).getId(), "Book1");
            entityManager.persist(book);

            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("error " + System.currentTimeMillis());
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
    }

    public void flushReaders() {
        futures.add(executorService.submit(new Runnable() {
            @Override
            public void run () {
                System.out.println("Start flush Readers " + System.currentTimeMillis());
                insert50000Readers();
            }
        }));
    }

    public void flushBook() {
        futures.add(executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start flush Book " + System.currentTimeMillis());
                insertOneBook();
            }
        }));
    }

    public void waitForCompletion() {
        System.out.println("Wait for thread to complete flush " + System.currentTimeMillis());

        for ( Future<?> future : futures ) {
            try {
                future.get();
            }
            catch (Exception e) {
                System.out.println("error " + System.currentTimeMillis());
                e.printStackTrace();
            }
        }

        System.out.println("Flush threads all finished");
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }


}

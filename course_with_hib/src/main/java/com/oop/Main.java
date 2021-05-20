package com.oop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);


        /*EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
        EntityManager em = emf.createEntityManager();

        System.out.println("Start test");

        em.getTransaction().begin();


        HitParade Hit = new HitParade();
        MyComposition compFirst = new MyComposition();
        MyRepertoire repertoireFirst = new MyRepertoire();
        repertoireFirst.addTrack("Hello!");

        compFirst.addName("Nick");
        Group firstGroup = new Group("Hell", 1986, repertoireFirst, compFirst, 111, 12, 2000);
        Hit.addGroupInTop(firstGroup);




        em.persist(firstGroup);
        em.getTransaction().commit();

        System.out.println("New group ID is "+ firstGroup.getId());*/



        /*SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Group.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Concert.class)
                .addAnnotatedClass(Track.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        try {

			System.out.println("Creating new object...");

            HitParade Hit = new HitParade();
            Person firstPerson = new Person("Nick");
            Person secondPerson = new Person("Rick");

            Track firstTrack = new Track("Hello!");

            Concert firstConcert = new Concert("12.01", "Moscow");
            Concert secondConcert = new Concert("14.01", "Saint-Petersburg");

            Group firstGroup = new Group("Hell", 1986,111,2000, 12);
            Hit.addGroupInTop(firstGroup);
            //firstGroup.addName(firstPerson);
            //firstGroup.addName(secondPerson);

			session.beginTransaction();

			// save
            System.out.println("Saving groups...");
            /*session.save(firstGroup);
            session.save(firstPerson);
            session.save(secondPerson);
            session.save(firstConcert);
            session.save(secondConcert);

			// commit transaction
			session.getTransaction().commit();

			System.out.println("Done!");
        }
        finally {
            factory.close();
        }*/

    }
}

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named
@ApplicationScoped
public class Shop implements Serializable {

    private final String[][] users =
            new String[][]{
                    // password hash obtained with java LoginController koch i-am-the-boss
                    new String[]{"koch",
                            "+INdDt2JaxoJLHzD4iAlWPYMJA0uJhusP37DvMHBKmen15EMj1Vn7BAxWS1TYFniKFKjuSyIEFbxy9jSx4d8Tw==",
                            "admin"},
                    // password hash obtained with java LoginController you you-are-the-client
                    new String[]{"you",
                            "dNw2o1ZcCW+Ge/n/yfYpMLbUZ9fbxqLXEuxTa6ilzXLgmr1imFH27T6q9ZNzlqBeAdKIHDf5SopFt0ttbDybEg==",
                            "client"}
            };
    private final List<Artikel> sortiment = Arrays.asList(new Artikel[]{
            new Artikel("Pantoffeln \"Rudolph\"",
                    "Wunderschöne Filzpantoffeln, in beige Farbe mit einem braunen und schwarzen Kringel. Sehr angenehm für kalte Wintertage.",
                    "filzschuhe.jpg", (new GregorianCalendar(2012, 11, 23).getTime())),
            new Artikel("Handtasche \"Cosmopolita\"",
                    "Modische Filz-Handtasche mit praktischer Cocktailglas-Halterung. Irgendwie kommen wir nie aus dem Haus ohne solchen nützlichen Accessoire.",
                    "handtasche.jpg", (new GregorianCalendar(2010, 10, 3).getTime())),
            new Artikel("Filz-Hase \"Moe\"",
                    "Ein putziger Hase aus Filz zur Dekoration. Er lässt sich gern am Rand seines Büros stellen, um Mut zu geben.", "hase.jpg",
                    (new GregorianCalendar(2013, 11, 31).getTime()))
    });

    EntityManager entityManager;

    public Shop() {
        entityManager = Persistence.createEntityManagerFactory("onlineshop").createEntityManager();
        long count = 0;


        count = entityManager.createQuery("SELECT a FROM Artikel a").getResultList().size();
        System.err.println("Wir haben " + count+ " Artikeln.");

        if(count == 0) {
            System.err.println("Initialisierung der Daten.");
            EntityTransaction t = entityManager.getTransaction();
            t.begin();
            for(Artikel art : sortiment) {
                entityManager.persist(art);
            }
            t.commit();
        }
    }

    public List<Artikel> getSortiment() {
        return (List<Artikel>) entityManager.createQuery("SELECT a FROM Artikel a").getResultList();
    }


    static String hashPassword(String name, String pass, String salt) {
        try {
            MessageDigest digester = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = digester.digest((name + pass + salt)
                    .getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(hashBytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void validateUsernameAndPassword(CurrentUser currentUser, String name, String pass, String salt) {
        String passHash = hashPassword(name, pass, salt);
        currentUser.reset();
        for (String[] user : users) {
            if (user[0].equals(name)) {
                if (user[1].equals(passHash)) {
                    if (user[2].equals("admin")) {
                        currentUser.admin = true;
                        return;
                    } else if (user[2].equals("client")) {
                        currentUser.client = true;
                        return;
                    } else throw new RuntimeException("Benutzer " + name + " ist falsch angelegt.");
                }
            }
        }
    }
}

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transaction;

@Named
@ViewScoped
public class ArtikelController implements Serializable
{
    private int index = 0;

    @Inject
    Shop shop;

    @Inject
    CurrentUser currentUser;

    Artikel artikel;

    public Artikel getArtikel()
    {
        return artikel = shop.getSortiment().get(index);
    }

    public void vor()
    {
        System.err.println("Saving Artikel " + artikel.getName());
        EntityTransaction t = shop.entityManager.getTransaction();
        t.begin();
        shop.entityManager.merge(artikel);
        t.commit();
      if (index < shop.getSortiment().size() - 1) {
        index++;
      }
    }

    public void zurueck()
    {
        System.err.println("Saving Artikel " + artikel.getName());
        shop.entityManager.merge(artikel);
      if (index > 0) {
        index--;
      }
    }

    public void removeArtikel() {
        if(!shop.getSortiment().isEmpty())
            shop.getSortiment().remove(index);
    }

    public int getIndex()
    {
        return index;
    }

    public int getMaxIndex() {
    	return shop.getSortiment().size()-1;
    }


}

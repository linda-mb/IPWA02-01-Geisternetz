import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Artikel implements Serializable
{
    @Id
    @GeneratedValue
    private int nr;

    private String name;

    private String text;

    private String bild;

    private Date verfuegbarAb;

    public Artikel()
    {
    }

    public Artikel(String name, String text, String bild)
    {
        this(name, text, bild, new Date(0));
    }

    public Artikel(String name, String text, String bild, Date verfuegbarAb)
    {
        this.name = name;
        this.text = text;
        this.bild = bild;
        this.verfuegbarAb = verfuegbarAb;

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNr()
    {
        return nr;
    }

    public void setNr(int nr)
    {
        this.nr = nr;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getBild()
    {
        return bild;
    }

    public void setBild(String bild)
    {
        this.bild = bild;
    }

    public Date getVerfuegbarAb()
    {
        return verfuegbarAb;
    }

    public void setVerfuegbarAb(Date verfuegbarAb)
    {
        this.verfuegbarAb = verfuegbarAb;
    }


}

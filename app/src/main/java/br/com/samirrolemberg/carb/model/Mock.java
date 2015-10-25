package br.com.samirrolemberg.carb.model;

/**
 * Created by samir on 15/04/2015.
 */
public class Mock {

    private int id;
    private String text;

    public Mock(int id, String text){
        super();
        this.id=id;
        this.text=text;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}

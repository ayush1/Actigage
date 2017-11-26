package model;

import java.util.ArrayList;

/**
 * Created by ayushgarg on 25/11/17.
 */

public class Photos {

    public ArrayList<Photo> photo;
    public int page;
    public String pages;
    public int perpage;
    public String total;

    public ArrayList<Photo> getPhoto() {
        return photo;
    }

    public Photos setPhoto(ArrayList<Photo> photo) {
        this.photo = photo;
        return this;
    }

    public String getPages() {
        return pages;
    }

    public Photos setPages(String pages) {
        this.pages = pages;
        return this;
    }


    public String getTotal() {
        return total;
    }

    public Photos setTotal(String total) {
        this.total = total;
        return this;
    }

    public int getPage() {
        return page;
    }

    public Photos setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPerpage() {
        return perpage;
    }

    public Photos setPerpage(int perpage) {
        this.perpage = perpage;
        return this;
    }
}

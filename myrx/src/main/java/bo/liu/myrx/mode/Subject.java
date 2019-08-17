package bo.liu.myrx.mode;

/**
 * Created by Administrator on 2017/12/21 0021.
 */

public class Subject {
    private String title;
    private int img;

    public Subject(String title, int img) {
        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}

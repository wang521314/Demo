package bo.liu.myviewpager.bean;


import java.util.ArrayList;

import bo.liu.myviewpager.util.StringUtils;

/**
 * Created by Administrator on 2017/11/16 0016.
 * 图片加载的实体类
 */

public class Folder {
    private String name;
    private ArrayList<Image> images;

    public Folder(String name) {
        this.name = name;
    }

    public Folder(String name, ArrayList<Image> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        if (image != null && StringUtils.isNotEmptyString(image.getPath())) {
            if (images == null) {
                images = new ArrayList<>();
            }
            images.add(image);
        }
    }



}

package bo.liu.myrx.mode;

import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class Student {
    String name;
    String sClass;

    public List<Curse> getMyCurse() {
        return myCurse;
    }

    public void setMyCurse(List<Curse> myCurse) {
        this.myCurse = myCurse;
    }

    List<Curse> myCurse;

    public Student(String nam, List myCurse) {
        this.name = nam;
        this.myCurse = myCurse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }
    public static class Curse{

        public Curse (String cur){
            this.cur=cur;
        }
        public String getCur() {
            return cur;
        }

        public void setCur(String cur) {
            this.cur = cur;
        }

        String cur;
    }
}

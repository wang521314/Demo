package bo.liu.myrx.mode;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5 0005.
 */

public class FamousInfo {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FamousInfo.content getContent() {
        return content;
    }

    public void setContent(FamousInfo.content content) {
        this.content = content;
    }

    private content content;
    public static class content {
        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public int getErrNo() {
            return errNo;
        }

        public void setErrNo(int errNo) {
            this.errNo = errNo;
        }

        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }


}

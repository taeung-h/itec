package notice.noticespring.web.domain;

import java.util.List;

public class EduCheckBox {


    private List<Long> closeEduList;

    private List<Long> openedEduList;

    public void setCloseEduList(List<Long> educationList) {
        this.closeEduList = educationList;
    }

    public List<Long> getCloseEduList() {
        return closeEduList;
    }

    public List<Long> getOpenedEduList() {
        return openedEduList;
    }

    public void setOpenedEduList(List<Long> openedEduList) {
        this.openedEduList = openedEduList;
    }
}

package notice.noticespring.app.dto;

import notice.noticespring.app.component.PaginationParams;
import notice.noticespring.app.domain.Report;

import java.util.List;

public class PaginationDto {
    private PaginationParams meta;

    public PaginationParams getMeta() {
        return meta;
    }

    public void setMeta(PaginationParams paginationParams) {
        this.meta = paginationParams;
    }

    public List<Report> getData() {
        return data;
    }

    public void setData(List<Report> data) {
        this.data = data;
    }

    private List<Report> data;




}

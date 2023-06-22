package src.com.dombarbeiro;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class CalendarActivity {
    private LocalDateTime date;
    private String clientName;
    private Integer serviceNo;

    public CalendarActivity(LocalDateTime date, String clientName, Integer serviceNo) {
        this.date = date;
        this.clientName = clientName;
        this.serviceNo = serviceNo;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(Integer serviceNo) {
        this.serviceNo = serviceNo;
    }

    @Override
    public String toString() {
        return "CalenderActivity{" +
                "date=" + date +
                ", clientName='" + clientName + '\'' +
                ", serviceNo=" + serviceNo +
                '}';
    }
}
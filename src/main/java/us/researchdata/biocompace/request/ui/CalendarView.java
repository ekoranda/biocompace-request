package us.researchdata.biocompace.request.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;


@ManagedBean
public class CalendarView {
 
    private Date date2;
    
    private List<Date> multi;
    private List<Date> range;
    private List<Date> invalidDates;
    private List<Integer> invalidDays;
    private Date minDate;
    private Date maxDate;
 
    @PostConstruct
    public void init() {
        invalidDates = new ArrayList<>();
        Date today = new Date();
        invalidDates.add(today);
        long oneDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < 5; i++) {
            invalidDates.add(new Date(invalidDates.get(i).getTime() + oneDay));
        }
 
        invalidDays = new ArrayList<>();
        invalidDays.add(0); /* the first day of week is disabled */

        invalidDays.add(3);
 
        minDate = new Date(today.getTime() - (365 * oneDay));
        maxDate = new Date(today.getTime() + (365 * oneDay));
    }
 
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
 
    public void click() {
        PrimeFaces.current().ajax().update("form:display");
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }

 
    public Date getDate2() {
        return date2;
    }
 
    public void setDate2(Date date2) {
        this.date2 = date2;
    }
 
    public List<Date> getMulti() {
        return multi;
    }
 
    public void setMulti(List<Date> multi) {
        this.multi = multi;
    }
 
    public List<Date> getRange() {
        return range;
    }
 
    public void setRange(List<Date> range) {
        this.range = range;
    }
 
    public List<Date> getInvalidDates() {
        return invalidDates;
    }
 
    public void setInvalidDates(List<Date> invalidDates) {
        this.invalidDates = invalidDates;
    }
 
    public List<Integer> getInvalidDays() {
        return invalidDays;
    }
 
    public void setInvalidDays(List<Integer> invalidDays) {
        this.invalidDays = invalidDays;
    }
 
    public Date getMinDate() {
        return minDate;
    }
 
    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
 
    public Date getMaxDate() {
        return maxDate;
    }
 
    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
}


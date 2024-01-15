import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStamp {

    private long unixDate =-1;
    private String orthodoxDate="";
    private Date date;

    public DateStamp(Date date){
        this.orthodoxDate= new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public DateStamp(long unixTime) {
        this.unixDate =unixTime;
    }

    public DateStamp(String orthodoxDate) {
        this.orthodoxDate = orthodoxDate;
    }

    public long getUnixDate() {
        if (this.unixDate ==-1){
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = null;
            try {
                date = dateFormat.parse(this.orthodoxDate);
            }catch (ParseException e){
                e.printStackTrace();
            }
            assert date != null;
            this.unixDate = date.getTime() /1000;
        }
        return this.unixDate;
    }

    public String getOrthodoxDate() {
        if (this.orthodoxDate.equals("")){
            Date date = new Date(this.unixDate *1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
            this.orthodoxDate=sdf.format(date);
        }
        return this.orthodoxDate;
    }

    public Date toDateConvert() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy hh:mm");
        return format.parse(getOrthodoxDate());
    }
}

package person.liuxx.util.bean;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** 
* @author  刘湘湘 
* @version 1.1.2<br>创建时间：2018年9月14日 下午12:36:22
* @since 1.1.2 
*/
public class TestObject2
{
    private LocalDate s1;
    private String s2;
    private String d1;
    private LocalDate d2;
    private String t1;
    private LocalDateTime t2;

    public LocalDate getS1()
    {
        return s1;
    }

    public void setS1(LocalDate s1)
    {
        this.s1 = s1;
    }

    public String getS2()
    {
        return s2;
    }

    public void setS2(String s2)
    {
        this.s2 = s2;
    }

    public String getD1()
    {
        return d1;
    }

    public void setD1(String d1)
    {
        this.d1 = d1;
    }

    public LocalDate getD2()
    {
        return d2;
    }

    public void setD2(LocalDate d2)
    {
        this.d2 = d2;
    }

    public String getT1()
    {
        return t1;
    }

    public void setT1(String t1)
    {
        this.t1 = t1;
    }

    public LocalDateTime getT2()
    {
        return t2;
    }

    public void setT2(LocalDateTime t2)
    {
        this.t2 = t2;
    }

    @Override
    public String toString()
    {
        return "TestObject2 [s1=" + s1 + ", s2=" + s2 + ", d1=" + d1 + ", d2=" + d2 + ", t1=" + t1
                + ", t2=" + t2 + "]";
    }
}

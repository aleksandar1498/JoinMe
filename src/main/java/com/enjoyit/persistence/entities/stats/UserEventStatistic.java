package com.enjoyit.persistence.entities.stats;

public class UserEventStatistic {
    private int year;
    private String month;
    private long count;

    public UserEventStatistic() {
    }

    public UserEventStatistic(final int year, final String month, final long count) {
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(final String month) {
        this.month = month;
    }

    public long getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }



}

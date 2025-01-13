package com.example.qrabsence.DTO;

public class Session {
    private Long id;
    private String intitule;
    private String date;
    private int period;
    private Long user_id;
    private int attendance_records_count;
    private String created_at;
    private String updated_at;

    // Generate getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    
    public int getPeriod() { return period; }
    public void setPeriod(int period) { this.period = period; }
    
    public Long getUser_id() { return user_id; }
    public void setUser_id(Long user_id) { this.user_id = user_id; }
    
    public int getAttendance_records_count() { return attendance_records_count; }
    public void setAttendance_records_count(int attendance_records_count) { 
        this.attendance_records_count = attendance_records_count; 
    }
    
    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }
    
    public String getUpdated_at() { return updated_at; }
    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }
}
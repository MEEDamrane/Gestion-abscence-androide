package com.example.qrabsence.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qrabsence.DTO.Session;
import com.example.qrabsence.R;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {
    private List<Session> sessions;
    private Context context;

    public SessionAdapter(List<Session> sessions) {
        this.sessions = sessions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Session session = sessions.get(position);
        holder.sessionTitle.setText(session.getIntitule());
        holder.sessionDate.setText(session.getDate());
        holder.attendanceCount.setText("Présences: " + session.getAttendance_records_count());
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void updateSessions(List<Session> newSessions) {
        this.sessions = newSessions;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView sessionTitle, sessionDate, attendanceCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sessionTitle = itemView.findViewById(R.id.sessionTitle);
            sessionDate = itemView.findViewById(R.id.sessionDate);
            attendanceCount = itemView.findViewById(R.id.attendanceCount);
        }
    }
}
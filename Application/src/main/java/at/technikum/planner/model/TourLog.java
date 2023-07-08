package at.technikum.planner.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourLog implements Serializable {

        public TourLog(double duration, double distance, String comment) {

        }

        private int id;

        private Date date;

        private double duration;

        private double distance;

        private String comment;

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }

        public double getDuration() {
                return duration;
        }

        public void setDuration(double duration) {
                this.duration = duration;
        }

        public double getDistance() {
                return distance;
        }

        public void setDistance(double distance) {
                this.distance = distance;
        }

        public String getComment() {
                return comment;
        }

        public void setComment(String comment) {
                this.comment = comment;
        }
}

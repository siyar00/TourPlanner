package at.technikum.planner.model.dal;

import at.technikum.planner.model.Tour;

public class DAL {

    private Dao<Tour> tourDao;

    private DAL() {
        tourDao = new TourDao();
    }

    public Dao<Tour> tourDao() {
        return tourDao;
    }

    //
    // Singleton-Pattern for DAL with early-binding
    //
    private static DAL instance = new DAL();

    public static DAL getInstance() {
        return instance;
    }
}

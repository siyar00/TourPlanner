package at.technikum.planner.dal;

import at.technikum.planner.dal.model.Tour;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TourDao implements Dao<Tour>{
    public TourDao() {

    }

    @Override
    public Optional<Tour> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Tour> getAll() {
        return null;
    }

    @Override
    public Tour create() {
        return null;
    }

    @Override
    public void update(Tour tour, List<?> params) {

    }

    @Override
    public void update(Tour tour) throws Exception, IOException {

    }

    @Override
    public void delete(Tour tour) {

    }

    @Override
    public void add(Tour tour) {

    }
}

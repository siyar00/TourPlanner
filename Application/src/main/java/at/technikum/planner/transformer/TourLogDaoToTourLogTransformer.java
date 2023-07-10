package at.technikum.planner.transformer;

import at.technikum.dal.dao.TourLogsDao;
import at.technikum.planner.model.TourLog;

import java.util.function.Function;

public class TourLogDaoToTourLogTransformer implements Function<TourLogsDao, TourLog> {
    @Override
    public TourLog apply(TourLogsDao tourLogDao) {
        return TourLog.builder()
                .tourId(tourLogDao.getTourDao().getId())
                .logId(tourLogDao.getLogId())
                .date(tourLogDao.getDate())
                .comment(tourLogDao.getComment())
                .rating(tourLogDao.getRating())
                .duration(tourLogDao.getDuration())
                .difficulty(tourLogDao.getDifficulty()).build();
    }
}

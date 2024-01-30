package notice.noticespring.web.service;

import notice.noticespring.web.domain.Location;
import notice.noticespring.web.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Long join(Location location) {

        validateDuplicateMember(location);
        locationRepository.save(location);
        return location.getId();

    }

    public Long modify(Location location)
    {

        locationRepository.findById(location.getId())
                .ifPresent(m -> {
                    locationRepository.save(location);
                });
        return location.getId();

    }

    public Location findOne(Long id)
    {
        return locationRepository.findByid(id);
    }

    public List<Location> findOillist(){return locationRepository.findAll();}


    /**
     * 중복 테스트
     * @param location
     */
    private void validateDuplicateMember(Location location) {
        locationRepository.findByname(location.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 기름명입니다.");
                });
    }
}

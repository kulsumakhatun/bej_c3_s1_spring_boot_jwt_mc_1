package com.niit.repository;

import com.niit.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends MongoRepository<Track,Integer> {
    @Query("{'trackRating':{$gt:4}}")
    public List<Track> findALlTrackByRating();

    @Query("{'trackArtist.artistName':{$in:[?0]}}")
    public List<Track> findAllTrackByTrackArtist(String trackArtist);
}
